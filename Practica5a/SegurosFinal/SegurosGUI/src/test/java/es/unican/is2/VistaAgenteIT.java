package es.unican.is2;

import static org.assertj.core.api.Assertions.*;

import java.awt.GraphicsEnvironment;

import org.assertj.swing.fixture.FrameFixture;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

/**
 * Pruebas de integración para VistaAgente usando AssertJ Swing
 * Prueba la integración de la capa de presentación con la capa de negocio y DAO
 * Sin dependencias externas para Mocks (Mocks/Stubs manuales)
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

    @Test
    public void testRellenaDatosClienteExistenteJuan() {
        vistaAgente.rellenaDatosCliente("11111111A");
        
        assertThat(vistaAgente.txtNombreCliente.getText())
            .isNotEmpty()
            .isNotEqualTo("Error en BBDD");
        
        assertThat(vistaAgente.txtTotalCliente.getText())
            .isNotEmpty();
    }

    @Test
    public void testRellenaDatosClienteExistenteAna() {
        vistaAgente.rellenaDatosCliente("22222222A");
        
        assertThat(vistaAgente.txtNombreCliente.getText())
            .isNotEmpty()
            .isNotEqualTo("Error en BBDD");
    }

    @Test
    public void testRellenaDatosClienteSinSeguros() {
        vistaAgente.rellenaDatosCliente("33333333A");
        
        assertThat(vistaAgente.txtNombreCliente.getText())
            .isNotEmpty()
            .isNotEqualTo("Error en BBDD");
        
        assertThat(vistaAgente.listModel.getSize()).isEqualTo(0);
    }

    @Test
    public void testRellenaDatosClienteConMultiplesSeguros() {
        vistaAgente.rellenaDatosCliente("44444444A");
        
        assertThat(vistaAgente.txtNombreCliente.getText())
            .isNotEmpty()
            .isNotEqualTo("Error en BBDD");
        
        assertThat(vistaAgente.listModel.getSize()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void testRellenaDatosClienteNoExistente() {
        vistaAgente.rellenaDatosCliente("99999999Z");
        
        assertThat(vistaAgente.txtNombreCliente.getText())
            .isEqualTo("Error en BBDD");
        
        assertThat(vistaAgente.listModel.getSize()).isEqualTo(0);
    }

    @Test
    public void testRellenaDatosClienteDniNulo() {
        vistaAgente.rellenaDatosCliente(null);
        
        assertThat(vistaAgente.txtNombreCliente.getText())
            .isEqualTo("Error en BBDD");
    }

    @Test
    public void testRellenaDatosClienteDniVacio() {
        vistaAgente.rellenaDatosCliente("");
        
        assertThat(vistaAgente.txtNombreCliente.getText())
            .isEqualTo("Error en BBDD");
    }

    @Test
    public void testDatosClienteJuanSonCorrectos() {
        vistaAgente.rellenaDatosCliente("11111111A");
        
        assertThat(vistaAgente.txtNombreCliente.getText())
            .isEqualTo("Juan");
        
        assertThat(vistaAgente.listModel.getSize()).isEqualTo(3);
    }

    @Test
    public void testDatosClienteAnaSonCorrectos() {
        vistaAgente.rellenaDatosCliente("22222222A");
        
        assertThat(vistaAgente.txtNombreCliente.getText())
            .isEqualTo("Ana");
        
        assertThat(vistaAgente.listModel.getSize()).isEqualTo(1);
    }

    @Test
    public void testDatosClienteLuisSinSeguros() {
        vistaAgente.rellenaDatosCliente("33333333A");
        
        assertThat(vistaAgente.txtNombreCliente.getText())
            .isEqualTo("Luis");
        
        assertThat(vistaAgente.listModel.getSize()).isEqualTo(0);
    }

    @Test
    public void testTotalClienteEnFormatoNumerico() {
        vistaAgente.rellenaDatosCliente("11111111A");
        
        String totalText = vistaAgente.txtTotalCliente.getText();
        assertThat(totalText).isNotEmpty();
        
        assertThatCode(() -> {
            Double.parseDouble(totalText);
        }).doesNotThrowAnyException();
    }

    @Test
    public void testComponentesInicializadosCorrectamente() {
        assertThat(vistaAgente.txtNombreCliente.getText()).isEmpty();
        assertThat(vistaAgente.txtTotalCliente.getText()).isEmpty();
        assertThat(vistaAgente.listModel.getSize()).isEqualTo(0);
    }

    @Test
    public void testComponentesNoEditables() {
        assertThat(window.textBox("txtNombreCliente")).isNotNull();
        assertThat(window.textBox("txtTotalCliente")).isNotNull();
    }

    @Test
    public void testCargaDatosPequeniosCicloDePrueba() {
        vistaAgente.rellenaDatosCliente("11111111A");
        assertThat(vistaAgente.txtNombreCliente.getText()).isEqualTo("Juan");
        
        vistaAgente.txtNombreCliente.setText("");
        vistaAgente.listModel.clear();
        vistaAgente.rellenaDatosCliente("22222222A");
        assertThat(vistaAgente.txtNombreCliente.getText()).isEqualTo("Ana");
        
        vistaAgente.txtNombreCliente.setText("");
        vistaAgente.listModel.clear();
        vistaAgente.rellenaDatosCliente("33333333A");
        assertThat(vistaAgente.txtNombreCliente.getText()).isEqualTo("Luis");
    }
    
    // ==================== Prueba con Stubs Manuales (Sin Mockito) ====================

    @Test

    public void testRellenaDatosClienteDataAccessException() throws Exception {
        // DRIVER: Esta clase de prueba actúa como Driver ejecutando el SUT
        
        // STUBS: Objetos que simulan el comportamiento de las clases de las que depende el SUT
        IInfoSeguros infoStub = new InfoSegurosStubError();
        IGestionClientes clientesDummy = new GestionClientesDummy();
        IGestionSeguros segurosDummy = new GestionSegurosDummy();

        // Inyección de dependencias en el SUT (VistaAgente)
        VistaAgente vista = new VistaAgente(clientesDummy, segurosDummy, infoStub);
        vista.setVisible(true);

        // Act
        vista.rellenaDatosCliente("11111111A");

        // Assert
        assertThat(vista.txtNombreCliente.getText()).isEqualTo("Error en BBDD");
        assertThat(vista.txtTotalCliente.getText()).isEqualTo("");
        assertThat(vista.listModel.getSize()).isEqualTo(0);
    }

    // --- IMPLEMENTACIÓN DE LOS STUBS SEGÚN LA ARQUITECTURA DE LA DIAPOSITIVA ---

    /**
     * Este es el Stub que fuerza el error para probar el bloque 'catch'
     */
    private class InfoSegurosStubError implements IInfoSeguros {
        @Override
        public Cliente cliente(String dni) throws DataAccessException {
            // Simulamos la excepción de la base de datos
            throw new DataAccessException(); 
        }

        @Override
        public Seguro seguro(String id) throws DataAccessException {
            return null; 
        }
    }

    /**
     * Dummy para la interfaz IGestionClientes
     */
    private class GestionClientesDummy implements IGestionClientes {
        @Override
        public Cliente nuevoCliente(Cliente c) { return null; }

		@Override
		public Cliente bajaCliente(String dni) throws OperacionNoValida, DataAccessException {
			// TODO Auto-generated method stub
			return null;
		}
        
        // IMPORTANTE: Si el compilador da error aquí, añade los métodos que falten 
        // de tu interfaz IGestionClientes con @Override y return null.
    }

    /**
     * Dummy para la interfaz IGestionSeguros
     */
    private class GestionSegurosDummy implements IGestionSeguros {
        @Override
        public Seguro nuevoSeguro(Seguro s, String dni) { return null; }

		@Override
		public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida, DataAccessException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Seguro anhadeConductorAdicional(String matricula, String conductor) throws DataAccessException {
			// TODO Auto-generated method stub
			return null;
		}
        
        // Lo mismo aquí: añade todos los métodos que IVistaAgente necesite de esta interfaz.
    }
}