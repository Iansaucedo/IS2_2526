package es.unican.is2;

import es.unican.is2.DataAccessException;
import es.unican.is2.IClientesDAO;
import es.unican.is2.IGestionSeguros;
import es.unican.is2.IInfoSeguros;
import es.unican.is2.ISegurosDAO;
import es.unican.is2.OperacionNoValida;
import es.unican.is2.Seguro;
import es.unican.is2.Cliente;

/**
 * Implementación de la interfaz de negocio para gestionar los seguros
 */
public class GestionSeguros implements IGestionSeguros, IInfoSeguros, IGestionClientes { // Added IGestionClientes

    private ISegurosDAO segurosDAO;
    private IClientesDAO clientesDAO;

    /**
     * Constructor que inicializa las DAOs necesarias
     * @param segurosDAO DAO para acceso a seguros
     * @param clientesDAO DAO para acceso a clientes
     */
    public GestionSeguros(IClientesDAO clientesDAO, ISegurosDAO segurosDAO) {
        this.segurosDAO = segurosDAO;
        this.clientesDAO = clientesDAO;
    }

    // Implement IGestionClientes methods here
    @Override
    public Cliente cliente(String dni) throws DataAccessException {
        return clientesDAO.cliente(dni);
    }

    @Override
    public Cliente nuevoCliente(Cliente c) throws DataAccessException {
        // Check if the client already exists
        if (clientesDAO.cliente(c.getDni()) != null) {
            throw new DataAccessException();
        }
        // Create the new client
        return clientesDAO.creaCliente(c);
    }

    @Override
    public Cliente bajaCliente(String dni) throws OperacionNoValida, DataAccessException {
        // Check if the client exists
        Cliente cliente = clientesDAO.cliente(dni);
        if (cliente == null) {
            throw new OperacionNoValida("El cliente no existe");
        }
        // Remove the client
        return clientesDAO.eliminaCliente(dni);
    }

    @Override
    public Seguro nuevoSeguro(Seguro s, String dni) throws OperacionNoValida, DataAccessException {
        // Verificar que el cliente existe
        Cliente cliente = clientesDAO.cliente(dni);
        if (cliente == null) {
            return null;
        }

        // Verificar que el seguro no existe ya
        Seguro existente = segurosDAO.seguroPorMatricula(s.getMatricula());
        if (existente != null) {
            throw new OperacionNoValida("El seguro ya existe");
        }

        // Crear el seguro
        return segurosDAO.creaSeguro(s);
    }

    @Override
    public Seguro bajaSeguro(String matricula, String dni) throws OperacionNoValida, DataAccessException {
        // Obtener el seguro
        Seguro seguro = segurosDAO.seguroPorMatricula(matricula);
        if (seguro == null) {
            return null;
        }

        // Verificar que pertenece al cliente indicado
        Cliente cliente = clientesDAO.cliente(dni);
        if (cliente == null) {
            return null;
        }

        // Eliminar el seguro
        return segurosDAO.eliminaSeguro(seguro.getId());
    }

    @Override
    public Seguro anhadeConductorAdicional(String matricula, String conductor) throws DataAccessException {
        // Obtener el seguro
        Seguro seguro = segurosDAO.seguroPorMatricula(matricula);
        if (seguro == null) {
            return null;
        }

        // Actualizar el conductor adicional
        seguro.setConductorAdicional(conductor);
        return segurosDAO.actualizaSeguro(seguro);
    }

    @Override
    public Seguro seguro(String matricula) throws DataAccessException {
        return segurosDAO.seguroPorMatricula(matricula);
    }
}