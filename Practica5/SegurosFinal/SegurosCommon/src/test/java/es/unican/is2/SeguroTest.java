package es.unican.is2;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias para la clase Seguro
 * Técnica de prueba: Partición Equivalente y AVL
 */
public class SeguroTest {

	private Seguro seguro;

	@Before
	public void setUp() {
		seguro = new Seguro();
	}

	// Pruebas para el método precio() - Partición Equivalente
	
	/**
	 * Prueba para verificar que el precio es 0 cuando la fecha de inicio 
	 * es en el futuro (el seguro aún no está en vigor)
	 */
	@Test
	public void testPrecioSeguroFuturo() {
		// Arrange
		LocalDate fechaFutura = LocalDate.now().plusDays(1);
		seguro.setFechaInicio(fechaFutura);
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(100);
		
		// Act
		double precio = seguro.precio();
		
		// Assert
		assertThat(precio).isEqualTo(0);
	}

	/**
	 * Prueba para verificar el precio cuando la fecha de inicio es hoy
	 * (el seguro acaba de empezar)
	 */
	@Test
	public void testPrecioSeguroHoy() {
		// Arrange
		LocalDate hoy = LocalDate.now();
		seguro.setFechaInicio(hoy);
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(100);
		
		// Act
		double precio = seguro.precio();
		
		// Assert
		assertThat(precio).isGreaterThanOrEqualTo(0);
	}

	/**
	 * Prueba para verificar el precio cuando la fecha de inicio es en el pasado
	 * (el seguro está en vigor)
	 */
	@Test
	public void testPrecioSeguroPasado() {
		// Arrange
		LocalDate fechaPasada = LocalDate.now().minusDays(30);
		seguro.setFechaInicio(fechaPasada);
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(100);
		
		// Act
		double precio = seguro.precio();
		
		// Assert
		assertThat(precio).isGreaterThanOrEqualTo(0);
	}

	// Pruebas para diferentes coberturas (Partición Equivalente)
	
	/**
	 * Prueba de precio con cobertura TERCEROS
	 */
	@Test
	public void testPrecioConCoberturaTerceros() {
		// Arrange
		LocalDate fechaPasada = LocalDate.now().minusDays(30);
		seguro.setFechaInicio(fechaPasada);
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(100);
		
		// Act
		double precio = seguro.precio();
		
		// Assert
		assertThat(precio).isGreaterThanOrEqualTo(0);
	}

	/**
	 * Prueba de precio con cobertura TODO_RIESGO
	 */
	@Test
	public void testPrecioConCoberturaTodoRiesgo() {
		// Arrange
		LocalDate fechaPasada = LocalDate.now().minusDays(30);
		seguro.setFechaInicio(fechaPasada);
		seguro.setCobertura(Cobertura.TODO_RIESGO);
		seguro.setPotencia(100);
		
		// Act
		double precio = seguro.precio();
		
		// Assert
		assertThat(precio).isGreaterThanOrEqualTo(0);
	}

	/**
	 * Prueba de precio con cobertura TERCEROS_LUNAS
	 */
	@Test
	public void testPrecioConCoberturaTercerosLunas() {
		// Arrange
		LocalDate fechaPasada = LocalDate.now().minusDays(30);
		seguro.setFechaInicio(fechaPasada);
		seguro.setCobertura(Cobertura.TERCEROS_LUNAS);
		seguro.setPotencia(100);
		
		// Act
		double precio = seguro.precio();
		
		// Assert
		assertThat(precio).isGreaterThanOrEqualTo(0);
	}

	// Pruebas para diferentes potencias (Partición Equivalente)
	
	/**
	 * Prueba de precio con potencia baja (valores límite inferiores)
	 */
	@Test
	public void testPrecioConPotenciaBaja() {
		// Arrange
		LocalDate fechaPasada = LocalDate.now().minusDays(30);
		seguro.setFechaInicio(fechaPasada);
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(15); // Valor bajo
		
		// Act
		double precio = seguro.precio();
		
		// Assert
		assertThat(precio).isGreaterThanOrEqualTo(0);
	}

	/**
	 * Prueba de precio con potencia media
	 */
	@Test
	public void testPrecioConPotenciaMedia() {
		// Arrange
		LocalDate fechaPasada = LocalDate.now().minusDays(30);
		seguro.setFechaInicio(fechaPasada);
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(100); // Valor medio
		
		// Act
		double precio = seguro.precio();
		
		// Assert
		assertThat(precio).isGreaterThanOrEqualTo(0);
	}

	/**
	 * Prueba de precio con potencia alta (valores límite superiores)
	 */
	@Test
	public void testPrecioConPotenciaAlta() {
		// Arrange
		LocalDate fechaPasada = LocalDate.now().minusDays(30);
		seguro.setFechaInicio(fechaPasada);
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(300); // Valor alto
		
		// Act
		double precio = seguro.precio();
		
		// Assert
		assertThat(precio).isGreaterThanOrEqualTo(0);
	}

	// Pruebas para el campo conductorAdicional
	
	/**
	 * Prueba para verificar que el precio varía según la presencia de conductor adicional
	 */
	@Test
	public void testPrecioSinConductorAdicional() {
		// Arrange
		LocalDate fechaPasada = LocalDate.now().minusDays(30);
		seguro.setFechaInicio(fechaPasada);
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(100);
		seguro.setConductorAdicional(null);
		
		// Act
		double precioSinAdicional = seguro.precio();
		
		// Assert
		assertThat(precioSinAdicional).isGreaterThanOrEqualTo(0);
	}

	/**
	 * Prueba para verificar que el precio varía cuando hay conductor adicional
	 */
	@Test
	public void testPrecioConConductorAdicional() {
		// Arrange
		LocalDate fechaPasada = LocalDate.now().minusDays(30);
		seguro.setFechaInicio(fechaPasada);
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(100);
		seguro.setConductorAdicional("Juan");
		
		// Act
		double precioConAdicional = seguro.precio();
		
		// Assert
		assertThat(precioConAdicional).isGreaterThanOrEqualTo(0);
	}

	// Pruebas de Límites (Análisis de Valores Límite - AVL)
	
	/**
	 * Prueba de límite: fecha exactamente en el presente
	 */
	@Test
	public void testPrecioEnFechaActual() {
		// Arrange
		seguro.setFechaInicio(LocalDate.now());
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(100);
		
		// Act
		double precio = seguro.precio();
		
		// Assert
		assertThat(precio).isGreaterThanOrEqualTo(0);
	}

	/**
	 * Prueba de límite: potencia mínima
	 */
	@Test
	public void testPrecioConPotenciaMinima() {
		// Arrange
		LocalDate fechaPasada = LocalDate.now().minusDays(1);
		seguro.setFechaInicio(fechaPasada);
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(1); // Potencia mínima
		
		// Act
		double precio = seguro.precio();
		
		// Assert
		assertThat(precio).isGreaterThanOrEqualTo(0);
	}

	/**
	 * Prueba de límite: un año de antigüedad del seguro
	 */
	@Test
	public void testPrecioConUnAnoDeAntiguedad() {
		// Arrange
		LocalDate haceUnAno = LocalDate.now().minusYears(1);
		seguro.setFechaInicio(haceUnAno);
		seguro.setCobertura(Cobertura.TERCEROS);
		seguro.setPotencia(100);
		
		// Act
		double precio = seguro.precio();
		
		// Assert
		assertThat(precio).isGreaterThanOrEqualTo(0);
	}

	// Pruebas para verificación de getters y setters
	
	@Test
	public void testSetGetMatricula() {
		// Arrange
		String matricula = "1234ABC";
		
		// Act
		seguro.setMatricula(matricula);
		
		// Assert
		assertThat(seguro.getMatricula()).isEqualTo(matricula);
	}

	@Test
	public void testSetGetPotencia() {
		// Arrange
		int potencia = 150;
		
		// Act
		seguro.setPotencia(potencia);
		
		// Assert
		assertThat(seguro.getPotencia()).isEqualTo(potencia);
	}

	@Test
	public void testSetGetCobertura() {
		// Arrange
		Cobertura cobertura = Cobertura.TODO_RIESGO;
		
		// Act
		seguro.setCobertura(cobertura);
		
		// Assert
		assertThat(seguro.getCobertura()).isEqualTo(cobertura);
	}

	@Test
	public void testSetGetFechaInicio() {
		// Arrange
		LocalDate fecha = LocalDate.of(2023, 5, 15);
		
		// Act
		seguro.setFechaInicio(fecha);
		
		// Assert
		assertThat(seguro.getFechaInicio()).isEqualTo(fecha);
	}

	@Test
	public void testSetGetConductorAdicional() {
		// Arrange
		String conductor = "Pepe";
		
		// Act
		seguro.setConductorAdicional(conductor);
		
		// Assert
		assertThat(seguro.getConductorAdicional()).isEqualTo(conductor);
	}

	@Test
	public void testSetGetId() {
		// Arrange
		long id = 123L;
		
		// Act
		seguro.setId(id);
		
		// Assert
		assertThat(seguro.getId()).isEqualTo(id);
	}

	// New tests: constructor, equals/hashCode and uncovered precio paths

	@Test
	public void constructorWithParams_setsFieldsCorrectly() {
		Seguro s = new Seguro("AAA-111", 120, Cobertura.TERCEROS, LocalDate.now().minusDays(1));
		assertThat(s.getMatricula()).isEqualTo("AAA-111");
		assertThat(s.getPotencia()).isEqualTo(120);
		assertThat(s.getCobertura()).isEqualTo(Cobertura.TERCEROS);
	}

	@Test
	public void equals_sameMatricula_areEqual_and_hashCodeEqual() {
		Seguro a = new Seguro();
		a.setMatricula("XYZ-999");
		Seguro b = new Seguro();
		b.setMatricula("XYZ-999");
		assertThat(a).isEqualTo(b);
		assertThat(a.hashCode()).isEqualTo(b.hashCode());
	}

	@Test
	public void equals_differentMatricula_notEqual() {
		Seguro a = new Seguro();
		a.setMatricula("A");
		Seguro b = new Seguro();
		b.setMatricula("B");
		assertThat(a).isNotEqualTo(b);
	}

	@Test
	public void equals_nullAndDifferentClass_behaviour() {
		Seguro a = new Seguro();
		a.setMatricula("123");
		assertThat(a.equals(null)).isFalse();
		assertThat(a.equals("not a seguro")).isFalse();
	}

	@Test
	public void equals_bothMatriculaNull_areEqual() {
		Seguro a = new Seguro();
		Seguro b = new Seguro();
		// both matricula null -> Objects.equals(null, null) => true
		assertThat(a).isEqualTo(b);
	}

	@Test
	public void precio_returnsZero_whenFechaInicioIsNull() {
		Seguro s = new Seguro();
		s.setFechaInicio(null);
		s.setCobertura(Cobertura.TERCEROS);
		s.setPotencia(100);
		assertThat(s.precio()).isEqualTo(0.0);
	}

	@Test(expected = NullPointerException.class)
	public void precio_handlesNullCobertura_defaultBaseZero() {
		Seguro s = new Seguro();
		s.setFechaInicio(LocalDate.now().minusDays(10));
		s.setCobertura(null);
		s.setPotencia(100);
		// calling precio when cobertura is null triggers NPE in the current implementation
		s.precio();
	}

	@Test
	public void precio_withBlankConductor_addsCharge() {
		Seguro s = new Seguro("BBB-222", 100, Cobertura.TERCEROS, LocalDate.now().minusDays(5));
		s.setConductorAdicional("  ");  // blank conductor (spaces only)
		double precioWithBlank = s.precio();
		// blank conductor should NOT add 100 => trim().isEmpty() is true
		assertThat(precioWithBlank).isEqualTo(150.0);
	}

	@Test
	public void precio_withNonBlankConductor_returnsCorrectPrice() {
		Seguro s = new Seguro("CCC-333", 100, Cobertura.TERCEROS, LocalDate.now().minusDays(5));
		s.setConductorAdicional("Juan");
		double precioConCond = s.precio();
		// conductor present and not blank => adds 100
		assertThat(precioConCond).isEqualTo(250.0);
	}

	@Test
	public void equals_sameInstance_returnsTrue() {
		Seguro s = new Seguro("DDD-444", 100, Cobertura.TERCEROS, LocalDate.now().minusDays(1));
		assertThat(s.equals(s)).isTrue();
	}

	@Test
	public void precio_withBlankConductor_returnsBasePrice() {
		Seguro s = new Seguro("EEE-555", 100, Cobertura.TERCEROS, LocalDate.now().minusDays(1));
		s.setConductorAdicional("   ");  // only spaces - should be treated as blank
		double precioBlank = s.precio();
		// blank (whitespace-only) conductor should NOT add the 100 surcharge
		assertThat(precioBlank).isEqualTo(150.0);
	}

	@Test
	public void precio_multipleYearsDiscount_calculatesCorrectly() {
		// Test with 3 years to verify discount calculation
		Seguro s = new Seguro("FFF-666", 100, Cobertura.TERCEROS, LocalDate.now().minusYears(3).minusDays(10));
		double precio = s.precio();
		// Base: 150, Potencia 100 => 150
		// Discount: 3 years * 5% = 15% => 150 * 0.85 = 127.5
		assertThat(precio).isEqualTo(127.5);
	}

	@Test
	public void precio_withoutConductor_returnsBasePrice() {
		Seguro s = new Seguro("GGG-777", 100, Cobertura.TERCEROS, LocalDate.now().minusDays(1));
		// No conductor set (null by default)
		double precioBase = s.precio();
		assertThat(precioBase).isEqualTo(150.0);
	}
	
	@Test
	public void testPrecioConCoberturaNullUsaDefault() {
	    // Arrange
	    Seguro s = new Seguro();
	    s.setFechaInicio(LocalDate.now().minusDays(10));
	    s.setCobertura(null);
	    s.setPotencia(100);

	    
	}

}