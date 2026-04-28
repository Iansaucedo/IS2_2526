package es.unican.is2;

import static org.assertj.core.api.Assertions.*;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import es.unican.is2.ClientesDAO;
import es.unican.is2.SegurosDAO;
import es.unican.is2.GestionSeguros;
import es.unican.is2.VistaAgente;
import es.unican.is2.H2ServerConnectionManager;

/**
 * Pruebas de integración para VistaAgente
 * Prueba la integración de la capa de presentación con la capa de negocio y DAO
 */
public class VistaAgenteIT {

	private VistaAgente vistaAgente;
	private IGestionClientes gestionClientes;
	private IGestionSeguros gestionSeguros;
	private IInfoSeguros infoSeguros;

	@BeforeClass
	public static void setUpDatabase() throws DataAccessException {
		// Inicializa la conexión con la base de datos
		H2ServerConnectionManager.getConnection();
	}

	@Before
	public void setUp() throws DataAccessException {
		// Crear las DAOs
		ClientesDAO clientesDAO = new ClientesDAO();
		SegurosDAO segurosDAO = new SegurosDAO();
		
		// Crear la implementación de negocio
		gestionClientes = new GestionSeguros(clientesDAO, segurosDAO);
		gestionSeguros = (IGestionSeguros) gestionClientes;
		infoSeguros = (IInfoSeguros) gestionClientes;
		
		// Crear la vista con las dependencias
		vistaAgente = new VistaAgente(gestionClientes, gestionSeguros, infoSeguros);
	}

	// Pruebas para el método rellenaDatosCliente

	/**
	 * Prueba de caso positivo: buscar un cliente existente (Juan)
	 * Verifica que se carguen correctamente los datos del cliente
	 */
	@Test
	public void testRellenaDatosClienteExistenteJuan() {
		// Arrange
		String dniCliente = "11111111A";
		
		// Act
		vistaAgente.rellenaDatosCliente(dniCliente);
		
		// Assert
		// Verificar que el campo de nombre se ha llenado correctamente
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isNotEmpty()
			.isNotEqualTo("Error en BBDD");
		
		// Verificar que el campo de total no está vacío
		assertThat(vistaAgente.txtTotalCliente.getText())
			.isNotEmpty()
			.isNotEqualTo("");
		
		// Verificar que la lista de seguros se ha llenado
		assertThat(vistaAgente.listModel.getSize()).isGreaterThan(0);
	}

	/**
	 * Prueba de caso positivo: buscar otro cliente existente (Ana)
	 */
	@Test
	public void testRellenaDatosClienteExistenteAna() {
		// Arrange
		String dniCliente = "22222222A";
		
		// Act
		vistaAgente.rellenaDatosCliente(dniCliente);
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isNotEmpty()
			.isNotEqualTo("Error en BBDD");
		
		assertThat(vistaAgente.txtTotalCliente.getText())
			.isNotEmpty()
			.isNotEqualTo("");
		
		// Ana tiene 1 seguro
		assertThat(vistaAgente.listModel.getSize()).isGreaterThanOrEqualTo(1);
	}

	/**
	 * Prueba de caso positivo: buscar cliente sin seguros (Luis)
	 */
	@Test
	public void testRellenaDatosClienteSinSeguros() {
		// Arrange
		String dniCliente = "33333333A";
		
		// Act
		vistaAgente.rellenaDatosCliente(dniCliente);
		
		// Assert
		// El cliente debe existir aunque no tenga seguros
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isNotEmpty()
			.isNotEqualTo("Error en BBDD");
		
		// La lista de seguros debe estar vacía
		assertThat(vistaAgente.listModel.getSize()).isEqualTo(0);
	}

	/**
	 * Prueba de caso positivo: buscar cliente con múltiples seguros (Pepe)
	 */
	@Test
	public void testRellenaDatosClienteConMultiplesSeguros() {
		// Arrange
		String dniCliente = "44444444A";
		
		// Act
		vistaAgente.rellenaDatosCliente(dniCliente);
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isNotEmpty()
			.isNotEqualTo("Error en BBDD");
		
		assertThat(vistaAgente.txtTotalCliente.getText())
			.isNotEmpty()
			.isNotEqualTo("");
		
		// Pepe tiene 2 seguros
		assertThat(vistaAgente.listModel.getSize()).isGreaterThanOrEqualTo(2);
	}

	/**
	 * Prueba de caso negativo: buscar un cliente que no existe
	 */
	@Test
	public void testRellenaDatosClienteNoExistente() {
		// Arrange
		String dniCliente = "99999999Z";
		
		// Act
		vistaAgente.rellenaDatosCliente(dniCliente);
		
		// Assert
		// Cuando no existe el cliente, el nombre debe mostrar error
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isEqualTo("Error en BBDD");
		
		// El total debe estar vacío
		assertThat(vistaAgente.txtTotalCliente.getText())
			.isEmpty();
		
		// La lista de seguros debe estar vacía
		assertThat(vistaAgente.listModel.getSize()).isEqualTo(0);
	}

	/**
	 * Prueba de caso negativo: DNI nulo
	 */
	@Test
	public void testRellenaDatosClienteDniNulo() {
		// Arrange
		String dniCliente = null;
		
		// Act - No debe lanzar una excepción
		vistaAgente.rellenaDatosCliente(dniCliente);
		
		// Assert - El nombre debe mostrar error
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isEqualTo("Error en BBDD");
		
		assertThat(vistaAgente.txtTotalCliente.getText())
			.isEmpty();
		
		assertThat(vistaAgente.listModel.getSize()).isEqualTo(0);
	}

	/**
	 * Prueba de caso límite: DNI vacío
	 */
	@Test
	public void testRellenaDatosClienteDniVacio() {
		// Arrange
		String dniCliente = "";
		
		// Act
		vistaAgente.rellenaDatosCliente(dniCliente);
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isEqualTo("Error en BBDD");
		
		assertThat(vistaAgente.txtTotalCliente.getText())
			.isEmpty();
		
		assertThat(vistaAgente.listModel.getSize()).isEqualTo(0);
	}

	/**
	 * Prueba de exactitud de datos: verificar que los datos de Juan son correctos
	 */
	@Test
	public void testDatosClienteJuanSonCorrectos() {
		// Arrange
		String dniCliente = "11111111A";
		
		// Act
		vistaAgente.rellenaDatosCliente(dniCliente);
		
		// Assert - Verificar que el nombre es "Juan"
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isEqualTo("Juan");
		
		// Verificar que hay 3 seguros (según datos iniciales)
		assertThat(vistaAgente.listModel.getSize()).isEqualTo(3);
		
		// Verificar que los seguros contienen las matrículas correctas
		String primerSeguro = vistaAgente.listModel.getElementAt(0);
		assertThat(primerSeguro).contains("1111");
	}

	/**
	 * Prueba de exactitud de datos: verificar que los datos de Ana son correctos
	 */
	@Test
	public void testDatosClienteAnaSonCorrectos() {
		// Arrange
		String dniCliente = "22222222A";
		
		// Act
		vistaAgente.rellenaDatosCliente(dniCliente);
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isEqualTo("Ana");
		
		// Ana tiene 1 seguro
		assertThat(vistaAgente.listModel.getSize()).isEqualTo(1);
		
		// Verificar que el seguro es el correcto (2222AAA)
		String seguro = vistaAgente.listModel.getElementAt(0);
		assertThat(seguro).contains("2222");
	}

	/**
	 * Prueba de exactitud de datos: verificar que Luis no tiene seguros
	 */
	@Test
	public void testDatosClienteLuisSinSeguros() {
		// Arrange
		String dniCliente = "33333333A";
		
		// Act
		vistaAgente.rellenaDatosCliente(dniCliente);
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isEqualTo("Luis");
		
		// Luis no tiene seguros
		assertThat(vistaAgente.listModel.getSize()).isEqualTo(0);
	}

	/**
	 * Prueba de campo total: verificar que el total está en formato numérico
	 */
	@Test
	public void testTotalClienteEnFormatoNumerico() {
		// Arrange
		String dniCliente = "11111111A";
		
		// Act
		vistaAgente.rellenaDatosCliente(dniCliente);
		
		// Assert
		String totalText = vistaAgente.txtTotalCliente.getText();
		assertThat(totalText).isNotEmpty();
		
		// Verificar que el total puede convertirse a un número
		assertThatCode(() -> {
			Double.parseDouble(totalText);
		}).doesNotThrowAnyException();
	}

	/**
	 * Prueba de límite: búsqueda secuencial de múltiples clientes
	 */
	@Test
	public void testBusquedaSecuencialMultiplesClientes() {
		// Arrange y Act
		String[] dnis = {"11111111A", "22222222A", "33333333A", "44444444A"};
		
		for (String dni : dnis) {
			// Limpiar la vista
			vistaAgente.txtNombreCliente.setText("");
			vistaAgente.txtTotalCliente.setText("");
			vistaAgente.listModel.removeAllElements();
			
			// Buscar el cliente
			vistaAgente.rellenaDatosCliente(dni);
			
			// Assert - Verificar que se cargó algún dato
			assertThat(vistaAgente.txtNombreCliente.getText())
				.isNotEmpty()
				.isNotEqualTo("Error en BBDD");
		}
	}

	/**
	 * Prueba de exactitud de lista de seguros: verificar formato de los elementos
	 */
	@Test
	public void testFormatoListaSeguros() {
		// Arrange
		String dniCliente = "11111111A";
		
		// Act
		vistaAgente.rellenaDatosCliente(dniCliente);
		
		// Assert - Verificar que cada elemento contiene matrícula y cobertura
		for (int i = 0; i < vistaAgente.listModel.getSize(); i++) {
			String elemento = vistaAgente.listModel.getElementAt(i);
			// Debe contener matricula y cobertura separadas por espacio
			assertThat(elemento).contains(" ");
			String[] partes = elemento.split(" ");
			assertThat(partes).hasSizeGreaterThanOrEqualTo(2);
		}
	}
}
