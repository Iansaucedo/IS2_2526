package es.unican.is2;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import es.unican.is2.Cobertura;

/**
 * Clase que representa un seguro de coche.
 */
public class Seguro {
	
	private long id;

    private String matricula;

	private int potencia;

    private Cobertura cobertura;
    
    private LocalDate fechaInicio;

	private String conductorAdicional;

	/**
	 * Constructor por defecto
	 */
	public Seguro() {
		super();
	}

	/**
	 * Constructor con parámetros principales
	 * @param matricula Matrícula del vehículo
	 * @param potencia Potencia del vehículo en CV
	 * @param cobertura Tipo de cobertura
	 * @param fechaInicio Fecha de inicio del seguro
	 */
	public Seguro(String matricula, int potencia, Cobertura cobertura, LocalDate fechaInicio) {
		this.matricula = matricula;
		this.potencia = potencia;
		this.cobertura = cobertura;
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Retorna el identificador del seguro
	 */
	public long getId() {
		return id;
	}

	/**
	 *  Asigna el valor del identificador del seguro
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Retorna la matricula del coche 
	 * asociado al seguro
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 *  Asigna el valor de la matrícula del seguro
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	/**
	 * Retorna la fecha de contratacion del seguro
	 */
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Asigna la fecha de inicio del seguro
	 * @param fechaInicio La fecha de inicio del seguro
	 */
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * Retorna el tipo de cobertura del seguro
	 */
	public Cobertura getCobertura() {
		return cobertura;
	}

	/**
	 * Asigna el tipo de cobertura del seguro
	 * @param cobertura El tipo de cobertura del seguro
	 */	
	public void setCobertura(Cobertura cobertura) {
		this.cobertura = cobertura;		
	}

	/**
     * Retorna la potencia del coche asociado 
     * al seguro. 
     */
    public int getPotencia() {
        return potencia;
    }

	/**
	 *  Asigna el valor de la potencia del seguro
	 */
	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	/**
	 * Retorna el conductor adicional del seguro, si lo hay
	 * @return El conductor adicional si lo hay
	 * 		null en caso contrario
	 */
	public String getConductorAdicional() {
		return conductorAdicional;
	}

	/**
	 * Asigna el conductor adicional del seguro
	 * @param conductorAdicional
	 */
	public void setConductorAdicional(String conductorAdicional) {
		this.conductorAdicional = conductorAdicional;
	}
    
    /**
     * Retorna el precio del seguro. 
	 * El precio se calcula a partir de la cobertura, la potencia del coche y el tiempo que lleva contratado el seguro
	 * @return El precio del seguro
	 *         0 si el seguro todavía no está en vigor (no se ha alcanzado su fecha de inicio)
     */
	public double precio() {
		// Si el seguro no está en vigor aún, el precio es 0
		if (fechaInicio == null || fechaInicio.isAfter(LocalDate.now())) {
			return 0;
		}
		
		// Precio base según cobertura
		double precioBase;
		switch (cobertura) {
			case TERCEROS:
				precioBase = 150.0;
				break;
			case TODO_RIESGO:
				precioBase = 400.0;
				break;
			case TERCEROS_LUNAS:
				precioBase = 250.0;
				break;
			default:
				precioBase = 0.0;
		}
		
		// Ajustar por potencia (potencia de referencia 100 CV)
		double precioAjustado = precioBase * (potencia / 100.0);
		
		// Ajustar por antigüedad: descuento del 5% por cada año completo
		long diasDesdeInicio = ChronoUnit.DAYS.between(fechaInicio, LocalDate.now());
		long anosDesdeInicio = diasDesdeInicio / 365;
		double descuentoAntiguedad = Math.min(anosDesdeInicio * 0.05, 0.5); // Máximo 50% descuento
		precioAjustado = precioAjustado * (1 - descuentoAntiguedad);
		
		// Agregar coste si hay conductor adicional
		if (conductorAdicional != null && !conductorAdicional.trim().isEmpty()) {
			precioAjustado += 100.0;
		}
		
		return precioAjustado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(matricula);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seguro other = (Seguro) obj;
		return Objects.equals(matricula, other.matricula);
	}
	
}
