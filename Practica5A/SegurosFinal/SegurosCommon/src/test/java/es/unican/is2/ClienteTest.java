package es.unican.is2;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase Cliente
 * Técnica de prueba: Partición Equivalente y AVL
 */
public class ClienteTest {

	private Cliente cliente;

	@Before
	public void setUp() {
		cliente = new Cliente();
	}

	// Pruebas para el método totalSeguros() - Partición Equivalente

	/**
	 * Prueba para un cliente sin seguros
	 */
	@Test
	public void testTotalSegurosClienteSinSeguros() {
		// Arrange
		cliente.setDni("12345678A");
		cliente.setNombre("Juan");
		cliente.setSeguros(new LinkedList<>());

		// Act
		double total = cliente.totalSeguros();

		// Assert
		assertThat(total).isEqualTo(0);
	}

	/**
	 * Prueba para un cliente con un seguro
	 */
	@Test
	public void testTotalSegurosClienteConUnSeguro() {
		// Arrange
		cliente.setDni("12345678A");
		cliente.setNombre("Juan");
		
		Seguro seguro = new Seguro();
		seguro.setId(1);
		seguro.setMatricula("1234ABC");
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(100);
		seguro.setFechaInicio(LocalDate.now().minusDays(30));
		
		List<Seguro> seguros = new LinkedList<>();
		seguros.add(seguro);
		cliente.setSeguros(seguros);

		// Act
		double total = cliente.totalSeguros();

		// Assert
		assertThat(total).isGreaterThanOrEqualTo(0);
	}

	/**
	 * Prueba para un cliente con múltiples seguros
	 */
	@Test
	public void testTotalSegurosClienteConMultiplesSeguros() {
		// Arrange
		cliente.setDni("12345678A");
		cliente.setNombre("Juan");
		
		List<Seguro> seguros = new LinkedList<>();
		
		// Agregar primer seguro
		Seguro seguro1 = new Seguro();
		seguro1.setId(1);
		seguro1.setMatricula("1234ABC");
		seguro1.setCobertura(Cobertura.TERCEROS);
		seguro1.setPotencia(100);
		seguro1.setFechaInicio(LocalDate.now().minusDays(30));
		seguros.add(seguro1);
		
		// Agregar segundo seguro
		Seguro seguro2 = new Seguro();
		seguro2.setId(2);
		seguro2.setMatricula("5678DEF");
		seguro2.setCobertura(Cobertura.TODO_RIESGO);
		seguro2.setPotencia(150);
		seguro2.setFechaInicio(LocalDate.now().minusDays(60));
		seguros.add(seguro2);
		
		cliente.setSeguros(seguros);

		// Act
		double total = cliente.totalSeguros();

		// Assert
		assertThat(total).isGreaterThanOrEqualTo(0);
	}

	/**
	 * Prueba para un cliente con seguros que no están en vigor
	 */
	@Test
	public void testTotalSegurosClienteConSegurosEnVigencia() {
		// Arrange
		cliente.setDni("12345678A");
		cliente.setNombre("Juan");
		
		List<Seguro> seguros = new LinkedList<>();
		
		// Seguro que está en vigor
		Seguro seguoEnVigencia = new Seguro();
		seguoEnVigencia.setId(1);
		seguoEnVigencia.setMatricula("1234ABC");
		seguoEnVigencia.setCobertura(Cobertura.TERCEROS);
		seguoEnVigencia.setPotencia(100);
		seguoEnVigencia.setFechaInicio(LocalDate.now().minusDays(30));
		seguros.add(seguoEnVigencia);
		
		// Seguro que no está en vigor (fecha futura)
		Seguro seguroFuturo = new Seguro();
		seguroFuturo.setId(2);
		seguroFuturo.setMatricula("5678DEF");
		seguroFuturo.setCobertura(Cobertura.TODO_RIESGO);
		seguroFuturo.setPotencia(150);
		seguroFuturo.setFechaInicio(LocalDate.now().plusDays(10));
		seguros.add(seguroFuturo);
		
		cliente.setSeguros(seguros);

		// Act
		double total = cliente.totalSeguros();

		// Assert
		assertThat(total).isGreaterThanOrEqualTo(0);
	}

	// Pruebas para diferentes tipos de clientes (Partición Equivalente)

	/**
	 * Prueba para un cliente sin minusvalía
	 */
	@Test
	public void testClienteSinMinusvalia() {
		// Arrange
		cliente.setDni("12345678A");
		cliente.setNombre("Juan");
		cliente.setMinusvalia(false);
		
		Seguro seguro = new Seguro();
		seguro.setId(1);
		seguro.setMatricula("1234ABC");
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(100);
		seguro.setFechaInicio(LocalDate.now().minusDays(30));
		
		List<Seguro> seguros = new LinkedList<>();
		seguros.add(seguro);
		cliente.setSeguros(seguros);

		// Act
		double total = cliente.totalSeguros();

		// Assert
		assertThat(total).isGreaterThanOrEqualTo(0);
	}

	/**
	 * Prueba para un cliente con minusvalía
	 */
	@Test
	public void testClienteConMinusvalia() {
		// Arrange
		cliente.setDni("33333333A");
		cliente.setNombre("Luis");
		cliente.setMinusvalia(true);
		
		Seguro seguro = new Seguro();
		seguro.setId(1);
		seguro.setMatricula("3333CCC");
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(100);
		seguro.setFechaInicio(LocalDate.now().minusDays(30));
		
		List<Seguro> seguros = new LinkedList<>();
		seguros.add(seguro);
		cliente.setSeguros(seguros);

		// Act
		double total = cliente.totalSeguros();

		// Assert
		assertThat(total).isGreaterThanOrEqualTo(0);
	}

	// Pruebas de límites (Análisis de Valores Límite - AVL)

	/**
	 * Prueba de límite: cliente con exactamente 1 seguro
	 */
	@Test
	public void testClienteConExactamenteUnSeguro() {
		// Arrange
		cliente.setDni("11111111A");
		cliente.setNombre("Juan");
		
		List<Seguro> seguros = new LinkedList<>();
		Seguro seguro = new Seguro();
		seguro.setId(1);
		seguro.setMatricula("1111AAA");
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(15);
		seguro.setFechaInicio(LocalDate.of(2002, 1, 15));
		seguros.add(seguro);
		cliente.setSeguros(seguros);

		// Act
		double total = cliente.totalSeguros();

		// Assert
		assertThat(total).isGreaterThanOrEqualTo(0);
	}

	/**
	 * Prueba de límite: cliente con muchos seguros
	 */
	@Test
	public void testClienteConMuchosSeguros() {
		// Arrange
		cliente.setDni("22222222A");
		cliente.setNombre("Ana");
		
		List<Seguro> seguros = new LinkedList<>();
		for (int i = 0; i < 5; i++) {
			Seguro seguro = new Seguro();
			seguro.setId(i + 1);
			seguro.setMatricula((i + 1) + "ABC");
			seguro.setCobertura(i % 2 == 0 ? Cobertura.TERCEROS : Cobertura.TODO_RIESGO);
			seguro.setPotencia(100 + i * 10);
			seguro.setFechaInicio(LocalDate.now().minusDays(i * 10));
			seguros.add(seguro);
		}
		cliente.setSeguros(seguros);

		// Act
		double total = cliente.totalSeguros();

		// Assert
		assertThat(total).isGreaterThanOrEqualTo(0);
	}

	// Pruebas para verificación de getters y setters

	@Test
	public void testSetGetDni() {
		// Arrange
		String dni = "12345678A";

		// Act
		cliente.setDni(dni);

		// Assert
		assertThat(cliente.getDni()).isEqualTo(dni);
	}

	@Test
	public void testSetGetNombre() {
		// Arrange
		String nombre = "Juan García";

		// Act
		cliente.setNombre(nombre);

		// Assert
		assertThat(cliente.getNombre()).isEqualTo(nombre);
	}

	@Test
	public void testSetGetMinusvalia() {
		// Arrange
		boolean minusvalia = true;

		// Act
		cliente.setMinusvalia(minusvalia);

		// Assert
		assertThat(cliente.getMinusvalia()).isEqualTo(minusvalia);
	}

	@Test
	public void testSetGetSeguros() {
		// Arrange
		List<Seguro> seguros = new LinkedList<>();
		Seguro seguro = new Seguro();
		seguro.setId(1);
		seguro.setMatricula("1234ABC");
		seguros.add(seguro);

		// Act
		cliente.setSeguros(seguros);

		// Assert
		assertThat(cliente.getSeguros()).isEqualTo(seguros);
		assertThat(cliente.getSeguros()).hasSize(1);
		assertThat(cliente.getSeguros().get(0).getMatricula()).isEqualTo("1234ABC");
	}

	@Test
	public void testGetSegurosDevuelveLista() {
		// Arrange
		cliente.setDni("12345678A");
		cliente.setNombre("Juan");

		// Act
		List<Seguro> seguros = cliente.getSeguros();

		// Assert
		assertThat(seguros).isNotNull();
		assertThat(seguros).isEmpty();
	}

	// Pruebas para casos especiales

	/**
	 * Prueba para verificar que la lista de seguros se puede modificar
	 */
	@Test
	public void testAgregarSegurosACliente() {
		// Arrange
		cliente.setDni("12345678A");
		cliente.setNombre("Juan");
		List<Seguro> seguros = cliente.getSeguros();

		Seguro seguro = new Seguro();
		seguro.setId(1);
		seguro.setMatricula("1234ABC");
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(100);
		seguro.setFechaInicio(LocalDate.now().minusDays(30));

		// Act
		seguros.add(seguro);

		// Assert
		assertThat(cliente.getSeguros()).hasSize(1);
		assertThat(cliente.getSeguros().get(0).getMatricula()).isEqualTo("1234ABC");
	}

	/**
	 * Prueba para verificar compatibilidad con datos reales de la BD
	 */
	@Test
	public void testClienteConDatosDelEjemplo() {
		// Arrange - Datos del cliente Juan de la tabla 1
		cliente.setDni("11111111A");
		cliente.setNombre("Juan");
		cliente.setMinusvalia(false);
		
		List<Seguro> seguros = new LinkedList<>();
		
		// Primer seguro
		Seguro seguro1 = new Seguro();
		seguro1.setId(1);
		seguro1.setMatricula("1111AAA");
		seguro1.setCobertura(Cobertura.TERCEROS);
		seguro1.setPotencia(15);
		seguro1.setFechaInicio(LocalDate.of(2002, 1, 15));
		seguros.add(seguro1);
		
		// Segundo seguro
		Seguro seguro2 = new Seguro();
		seguro2.setId(2);
		seguro2.setMatricula("1111BBB");
		seguro2.setCobertura(Cobertura.TODO_RIESGO);
		seguro2.setPotencia(20);
		seguro2.setFechaInicio(LocalDate.of(2016, 5, 20));
		seguro2.setConductorAdicional("Pepe");
		seguros.add(seguro2);
		
		// Tercer seguro
		Seguro seguro3 = new Seguro();
		seguro3.setId(3);
		seguro3.setMatricula("1111CCC");
		seguro3.setCobertura(Cobertura.TERCEROS);
		seguro3.setPotencia(100);
		seguro3.setFechaInicio(LocalDate.of(2022, 5, 21));
		seguros.add(seguro3);
		
		cliente.setSeguros(seguros);

		// Act
		double total = cliente.totalSeguros();

		// Assert
		assertThat(cliente.getDni()).isEqualTo("11111111A");
		assertThat(cliente.getNombre()).isEqualTo("Juan");
		assertThat(cliente.getSeguros()).hasSize(3);
		assertThat(total).isGreaterThanOrEqualTo(0);
	}

	// New tests to increase coverage: constructors, equals and hashCode

	@Test
	public void constructorWithParams_setsFieldsCorrectly() {
		Cliente c = new Cliente("11111111A", "Juan");
		assertThat(c.getDni()).isEqualTo("11111111A");
		assertThat(c.getNombre()).isEqualTo("Juan");
	}

	@Test
	public void constructorWithAllParams_setsFieldsCorrectly() {
		Cliente c = new Cliente("22222222B", "Ana", true);
		assertThat(c.getDni()).isEqualTo("22222222B");
		assertThat(c.getNombre()).isEqualTo("Ana");
		assertThat(c.getMinusvalia()).isTrue();
	}

	@Test
	public void equals_sameDni_areEqual_and_hashCodeEqual() {
		Cliente a = new Cliente("999Z", "One");
		Cliente b = new Cliente("999Z", "Two");
		assertThat(a).isEqualTo(b);
		assertThat(a.hashCode()).isEqualTo(b.hashCode());
	}

	@Test
	public void equals_differentDni_notEqual() {
		Cliente a = new Cliente("111A", "One");
		Cliente b = new Cliente("222B", "Two");
		assertThat(a).isNotEqualTo(b);
	}

	@Test
	public void equals_nullAndDifferentClass_behaviour() {
		Cliente a = new Cliente("333C", "X");
		assertThat(a.equals(null)).isFalse();
		assertThat(a.equals("not a cliente")).isFalse();
	}

	@Test
	public void equals_bothDniNull_areEqual() {
		Cliente a = new Cliente();
		Cliente b = new Cliente();
		// both dni null -> Objects.equals(null, null) => true
		assertThat(a).isEqualTo(b);
	}

	@Test
	public void totalSeguros_returnsZero_whenSegurosIsNull() {
        Cliente c = new Cliente("456B", "Luis");
        c.setSeguros(null);
        assertThat(c.totalSeguros()).isEqualTo(0.0);
    }

    @Test
    public void totalSeguros_ignoresNullEntries() {
        Cliente c = new Cliente("789C", "María");
        Seguro s1 = new Seguro("AAA-111", 100, Cobertura.TERCEROS, LocalDate.now().minusDays(2));
        List<Seguro> lista = new LinkedList<>();
        lista.add(null);
        lista.add(s1);
        c.setSeguros(lista);
        assertThat(c.totalSeguros()).isEqualTo(s1.precio());
    }

    @Test
    public void totalSeguros_listWithOnlyNulls_returnsZero() {
        Cliente c = new Cliente("555N", "Nulls");
        List<Seguro> lista = new LinkedList<>();
        lista.add(null);
        lista.add(null);
        c.setSeguros(lista);
        assertThat(c.totalSeguros()).isEqualTo(0.0);
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        Cliente c = new Cliente("321X", "Self");
        assertThat(c.equals(c)).isTrue();
    }

}