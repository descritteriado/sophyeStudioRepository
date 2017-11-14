package com.ejb.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.dao.clients.ClientDao;
import com.jpa.model.Tcliclient;

@Stateless
public class ClientsService {

	final static Logger logger = Logger.getLogger(ClientsService.class);

	@EJB
	ClientDao<Tcliclient> clientDao;	

	/**
	 * Metodo para consultar los clientes dados ciertos parametros
	 * 
	 * @param document
	 * @param name
	 * @param lastName
	 * @param status
	 * @return Lista de clientes List<Tcliclient>
	 */
	public List<Tcliclient> getClients(String document, String name, String lastName, Boolean status) throws Exception {

		List<Tcliclient> clients = null;

		clients = clientDao.getClients(document, name, lastName, status);

		return clients;
	}
	
	/**
	 * Metodo para guardar o actualizar un Cliente
	 * 
	 * @param Tcliclient client
	 * @return boolean
	 */
	public boolean mergeClient(Tcliclient client) {

		return clientDao.merge(client);
	}
	/**
	 * Metodo para eliminar un cliente
	 * 
	 * @param Tcliclient client
	 */
	public void deleteClient(Tcliclient client) throws Exception {

		clientDao.remove(client);
	}

}
