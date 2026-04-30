package es.unican.is2;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import  es.unican.is2.H2ServerConnectionManager;
import  es.unican.is2.Cliente;
import es.unican.is2.DataAccessException;
import es.unican.is2.IClientesDAO;
import es.unican.is2.Seguro;


public class ClientesDAO implements IClientesDAO {

	@Override
	public Cliente creaCliente(Cliente c) throws DataAccessException {
		String insertStatement = String.format(
				"insert into Clientes(dni, nombre, minusvalia) values ('%s', '%s', '%b')",
				c.getDni(),
				c.getNombre(),
				c.getMinusvalia());
		H2ServerConnectionManager.executeSqlStatement(insertStatement);
		return c;
	}

	@Override
	public Cliente cliente(String dni) throws DataAccessException {
		Cliente result = null; 
		Connection con = H2ServerConnectionManager.getConnection();
		try (PreparedStatement statement = con.prepareStatement("select * from Clientes where dni = ?")) {
			statement.setString(1, dni);
			try (ResultSet results = statement.executeQuery()) {
				if (results.next()) { 
					result = procesaCliente(con,results);
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException();
		}
		return result;
	}

	@Override
	public Cliente actualizaCliente(Cliente nuevo) throws DataAccessException {
		Cliente cliente = null;
		Cliente old = cliente(nuevo.getDni());
		String statementText;

		Connection con = H2ServerConnectionManager.getConnection();

		statementText = String.format(
				"update Clientes set nombre = '%s', minusvalia = '%b' where dni = '%s'", 
				nuevo.getNombre(), nuevo.getMinusvalia(), nuevo.getDni());
			H2ServerConnectionManager.executeSqlStatement(statementText);
			for(Seguro s: old.getSeguros()) {
				if (!nuevo.getSeguros().contains(s)) {
					statementText = String.format(
						"update Seguros set cliente_FK = null where id = '%d'",
						s.getId());
					H2ServerConnectionManager.executeSqlStatement(statementText);
				}
			}
			cliente = cliente(nuevo.getDni());
		
		return cliente;
	}

	@Override
	public Cliente eliminaCliente(String dni) throws DataAccessException {
		Cliente cliente = cliente(dni);
		Connection con = H2ServerConnectionManager.getConnection();
		String statementText = "delete from Clientes where dni = " + dni;
		H2ServerConnectionManager.executeSqlStatement(statementText);
		return cliente;
	}

	@Override
	public List<Cliente> clientes() throws DataAccessException {
		List<Cliente> clientes = new LinkedList<Cliente>();
		Connection con = H2ServerConnectionManager.getConnection(); 
		String statementText = "select dni, nombre, minusvalia from Clientes";
		try (Statement statement = con.createStatement(); ResultSet results = statement.executeQuery(statementText)) {
			// Procesamos cada fila como vehiculo independiente
			while (results.next()) {
				clientes.add(procesaCliente(con, results)); 
			}
		} catch (SQLException e) {
			// System.out.println(e);
			throw new DataAccessException();
		}

		return clientes;
	}

	private Cliente procesaCliente(Connection con, ResultSet results) throws SQLException, DataAccessException {
		Cliente result = ClienteMapper.toCliente(results);
		// Cargamos los seguros del cliente
		String statementText = "select * from Seguros where cliente_FK = ?";
		try (PreparedStatement statement = con.prepareStatement(statementText)) {
			statement.setString(1, result.getDni());
			try (ResultSet segurosResult = statement.executeQuery()) {
				while (segurosResult.next()) {
					result.getSeguros().add(SeguroMapper.toSeguro(segurosResult));
				}
			}
		}
		return result;
	}
	
}
