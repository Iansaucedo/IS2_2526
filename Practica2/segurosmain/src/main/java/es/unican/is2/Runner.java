package es.unican.is2;

import es.unican.is2.VistaAgente;
import es.unican.is2.ClientesDAO;
import es.unican.is2.IClientesDAO;
import es.unican.is2.ISegurosDAO;
import es.unican.is2.SegurosDAO;
import es.unican.is2.GestionSeguros;

public class Runner {

    public static void main(String[] args) {
        IClientesDAO daoClientes = new ClientesDAO();
        ISegurosDAO daoSeguros = new SegurosDAO();
        GestionSeguros negocio = new GestionSeguros(daoClientes, daoSeguros);
        VistaAgente vista = new VistaAgente(negocio, negocio, negocio);
        vista.setVisible(true);
    }

}