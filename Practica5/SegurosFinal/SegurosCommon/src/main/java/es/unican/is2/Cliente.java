package es.unican.is2;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import es.unican.is2.Seguro;

/**
 * Clase que representa un cliente de la empresa de seguros
 * Un cliente se identifica por su dni
 */
public class Cliente {

    private String dni;

    private String nombre;  
    
    private boolean minusvalia;

    private List<Seguro> seguros = new LinkedList<Seguro>();
    
    /**
     * Constructor por defecto
     */
    public Cliente() {
        super();
    }

    /**
     * Constructor con parámetros principales
     * @param dni DNI del cliente
     * @param nombre Nombre del cliente
     */
    public Cliente(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    /**
     * Constructor con todos los parámetros
     * @param dni DNI del cliente
     * @param nombre Nombre del cliente
     * @param minusvalia Indica si el cliente tiene minusvalía
     */
    public Cliente(String dni, String nombre, boolean minusvalia) {
        this.dni = dni;
        this.nombre = nombre;
        this.minusvalia = minusvalia;
    }
    
	/**
     * Retorna los seguros del cliente 
     */
    public List<Seguro> getSeguros() {
        return seguros;
    }
    
    /**
     * Asigna la lista de seguros
     */
    public void setSeguros(List<Seguro> seguros) {
        this.seguros = seguros;
    }

    /**
     * Retorna el nombre del cliente.   
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del cliente
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Retorna el dni del cliente.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Asigna el dni del cliente
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    /**
     * Indica si el cliente es minusvalido
     */
    public boolean getMinusvalia() {
    	return minusvalia;
    }

    /**
     * Asigna la minusvalia del cliente
     * @param minusvalia
     */
     public void setMinusvalia(boolean minusvalia) {
        this.minusvalia = minusvalia;
    }
    
    /**
     * Calcula el total a pagar por el cliente por 
     * todos los seguros a su nombre
     */
    public double totalSeguros() {
        double total = 0;
        
        if (seguros == null || seguros.isEmpty()) {
            return 0;
        }
        
        for (Seguro seguro : seguros) {
            if (seguro != null) {
                total += seguro.precio();
            }
        }
        
        return total;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cliente other = (Cliente) obj;
        return Objects.equals(dni, other.dni);
    }

}
