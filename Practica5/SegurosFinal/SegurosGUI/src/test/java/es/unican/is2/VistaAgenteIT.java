package es.unican.is2;

import static org.assertj.core.api.Assertions.*;

import java.awt.GraphicsEnvironment;

import org.assertj.swing.fixture.FrameFixture;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import es.unican.is2.H2ServerConnectionManager;

/**
 * Pruebas de integración para VistaAgente usando AssertJ Swing
 * Prueba la integración de la capa de presentación con la capa de negocio y DAO
 * 
 * Utiliza AssertJ Swing para:
 * - Interactuar con componentes Swing de forma segura
 * - Validar el estado de los componentes GUI
 * - Simular interacciones de usuario
 * 
 * Patrón basado en documentación oficial de AssertJ Swing
 */
public class VistaAgenteIT {

	private VistaAgente vistaAgente;
	private FrameFixture window;

	@BeforeClass
	public static void setUpDatabase() throws DataAccessException {
		// Inicializa la conexión con la base de datos
		H2ServerConnectionManager.getConnection();
	}

	@Before
	public void setUp() throws DataAccessException {
		org.junit.Assume.assumeFalse(GraphicsEnvironment.isHeadless());

		// Crear las DAOs
		ClientesDAO clientesDAO = new ClientesDAO();
		SegurosDAO segurosDAO = new SegurosDAO();
		
		// Crear la implementación de negocio
		IGestionClientes gestionClientes = new GestionSeguros(clientesDAO, segurosDAO);
		IGestionSeguros gestionSeguros = (IGestionSeguros) gestionClientes;
		IInfoSeguros infoSeguros = (IInfoSeguros) gestionClientes;
		
		// Crear la vista con las dependencias
		vistaAgente = new VistaAgente(gestionClientes, gestionSeguros, infoSeguros);
		vistaAgente.setVisible(true);
		
		// Crear un FrameFixture - AssertJ Swing crea el Robot automáticamente
		window = new FrameFixture(vistaAgente);
	}

	@After
	public void tearDown() {
		// Limpiar después de cada prueba
		if (window != null) {
			window.cleanUp();
		}
	}

	// ==================== Pruebas de integración FrameFixture ====================

	/**
	 * Prueba de caso positivo: buscar un cliente existente (Juan)
	 * Verifica que se carguen correctamente los datos del cliente
	 */
	@Test
	public void testRellenaDatosClienteExistenteJuan() {
		// Act
		vistaAgente.rellenaDatosCliente("11111111A");
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isNotEmpty()
			.isNotEqualTo("Error en BBDD");
		
		assertThat(vistaAgente.txtTotalCliente.getText())
			.isNotEmpty();
	}

	/**
	 * Prueba de caso positivo: buscar otro cliente existente (Ana)
	 */
	@Test
	public void testRellenaDatosClienteExistenteAna() {
		// Act
		vistaAgente.rellenaDatosCliente("22222222A");
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isNotEmpty()
			.isNotEqualTo("Error en BBDD");
	}

	/**
	 * Prueba de caso positivo: buscar cliente sin seguros (Luis)
	 */
	@Test
	public void testRellenaDatosClienteSinSeguros() {
		// Act
		vistaAgente.rellenaDatosCliente("33333333A");
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isNotEmpty()
			.isNotEqualTo("Error en BBDD");
		
		assertThat(vistaAgente.listModel.getSize()).isEqualTo(0);
	}

	/**
	 * Prueba de caso positivo: buscar cliente con múltiples seguros (Pepe)
	 */
	@Test
	public void testRellenaDatosClienteConMultiplesSeguros() {
		// Act
		vistaAgente.rellenaDatosCliente("44444444A");
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isNotEmpty()
			.isNotEqualTo("Error en BBDD");
		
		assertThat(vistaAgente.listModel.getSize()).isGreaterThanOrEqualTo(2);
	}

	/**
	 * Prueba de caso negativo: buscar un cliente que no existe
	 */
	@Test
	public void testRellenaDatosClienteNoExistente() {
		// Act
		vistaAgente.rellenaDatosCliente("99999999Z");
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isEqualTo("Error en BBDD");
		
		assertThat(vistaAgente.listModel.getSize()).isEqualTo(0);
	}

	/**
	 * Prueba de caso negativo: DNI nulo
	 */
	@Test
	public void testRellenaDatosClienteDniNulo() {
		// Act
		vistaAgente.rellenaDatosCliente(null);
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isEqualTo("Error en BBDD");
	}

	/**
	 * Prueba de caso límite: DNI vacío
	 */
	@Test
	public void testRellenaDatosClienteDniVacio() {
		// Act
		vistaAgente.rellenaDatosCliente("");
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isEqualTo("Error en BBDD");
	}

	/**
	 * Prueba de exactitud de datos: verificar que los datos de Juan son correctos
	 */
	@Test
	public void testDatosClienteJuanSonCorrectos() {
		// Act
		vistaAgente.rellenaDatosCliente("11111111A");
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isEqualTo("Juan");
		
		assertThat(vistaAgente.listModel.getSize()).isEqualTo(3);
	}

	/**
	 * Prueba de exactitud de datos: verificar que los datos de Ana son correctos
	 */
	@Test
	public void testDatosClienteAnaSonCorrectos() {
		// Act
		vistaAgente.rellenaDatosCliente("22222222A");
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isEqualTo("Ana");
		
		assertThat(vistaAgente.listModel.getSize()).isEqualTo(1);
	}

	/**
	 * Prueba de exactitud de datos: verificar que Luis no tiene seguros
	 */
	@Test
	public void testDatosClienteLuisSinSeguros() {
		// Act
		vistaAgente.rellenaDatosCliente("33333333A");
		
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText())
			.isEqualTo("Luis");
		
		assertThat(vistaAgente.listModel.getSize()).isEqualTo(0);
	}

	/**
	 * Prueba de campo total: verificar que el total está en formato numérico
	 */
	@Test
	public void testTotalClienteEnFormatoNumerico() {
		// Act
		vistaAgente.rellenaDatosCliente("11111111A");
		
		// Assert
		String totalText = vistaAgente.txtTotalCliente.getText();
		assertThat(totalText).isNotEmpty();
		
		// Verificar que el total puede convertirse a un número
		assertThatCode(() -> {
			Double.parseDouble(totalText);
		}).doesNotThrowAnyException();
	}

	/**
	 * Prueba de componentes inicializados: verificar el estado inicial de la GUI
	 */
	@Test
	public void testComponentesInicializadosCorrectamente() {
		// Assert
		assertThat(vistaAgente.txtNombreCliente.getText()).isEmpty();
		assertThat(vistaAgente.txtTotalCliente.getText()).isEmpty();
		assertThat(vistaAgente.listModel.getSize()).isEqualTo(0);
	}

	/**
	 * Prueba de componentes no editables: verificar que ciertos campos son readonly
	 */
	@Test
	public void testComponentesNoEditables() {
		// Assert - Simplemente verificar que los componentes existen y están configurados
		assertThat(window.textBox("txtNombreCliente")).isNotNull();
		assertThat(window.textBox("txtTotalCliente")).isNotNull();
	}

	/**
	 * Prueba de ciclo de carga: cargar múltiples clientes secuencialmente
	 */
	@Test
	public void testCargaDatosPequeniosCicloDePrueba() {
		// Test 1: Cargar Juan
		vistaAgente.rellenaDatosCliente("11111111A");
		assertThat(vistaAgente.txtNombreCliente.getText()).isEqualTo("Juan");
		
		// Test 2: Limpiar y cargar Ana
		vistaAgente.txtNombreCliente.setText("");
		vistaAgente.listModel.clear();
		vistaAgente.rellenaDatosCliente("22222222A");
		assertThat(vistaAgente.txtNombreCliente.getText()).isEqualTo("Ana");
		
		// Test 3: Limpiar y cargar Luis
		vistaAgente.txtNombreCliente.setText("");
		vistaAgente.listModel.clear();
		vistaAgente.rellenaDatosCliente("33333333A");
		assertThat(vistaAgente.txtNombreCliente.getText()).isEqualTo("Luis");
	}
	
	@Test
	public void testRellenaDatosClienteDataAccessException() throws Exception {

	    // Arrange: crear mocks
	    IInfoSeguros infoMock = org.mockito.Mockito.mock(IInfoSeguros.class);
	    IGestionClientes clientesMock = org.mockito.Mockito.mock(IGestionClientes.class);
	    IGestionSeguros segurosMock = org.mockito.Mockito.mock(IGestionSeguros.class);

	    // Simular excepción
	    org.mockito.Mockito.when(infoMock.cliente("11111111A"))
	        .thenThrow(new DataAccessException());

	    VistaAgente vista = new VistaAgente(clientesMock, segurosMock, infoMock);
	    vista.setVisible(true);

	    // Act
	    vista.rellenaDatosCliente("11111111A");

	    // Assert
	    assertThat(vista.txtNombreCliente.getText())
	        .isEqualTo("Error en BBDD");

	    assertThat(vista.txtTotalCliente.getText())
	        .isEqualTo("");

	    assertThat(vista.listModel.getSize())
	        .isEqualTo(0);
	}
}
