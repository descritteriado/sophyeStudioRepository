package com.dao.clients;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ejb.generic.GenericDaoImpl;
import com.jpa.model.Tcliclient;

@Stateless
public class ClientDao<Entity> extends GenericDaoImpl<Entity> {

	final static Logger logger = Logger.getLogger(ClientDao.class);

	@PersistenceContext(unitName = "skeletonJPA")
	private EntityManager entityManager;

	/**
	 * Metodo para consultar los clientes dados ciertos parametros
	 * 
	 * @param document
	 * @param name
	 * @param lastName
	 * @return Lista de clientes List<Tsegclient>
	 */
	@SuppressWarnings("unchecked")
	public List<Tcliclient> getClients(String document, String name, String lastName, boolean status) throws Exception {

		List<Tcliclient> clients = null;
		String preQuery = "SELECT u FROM Tcliclient as u " + "WHERE u.deleted= false and u.status= :status ";

		if (!document.isEmpty()) {

			preQuery = preQuery.concat("and  u.document like :document ");
		}
		if (!name.isEmpty()) {
			preQuery = preQuery.concat("and  u.names like :name ");
		}
		if (!lastName.isEmpty()) {
			preQuery = preQuery.concat("and u.lastnames like :lastName ");
		}

		Query query = entityManager.createQuery(preQuery);
		query.setParameter("status", status);

		if (!document.isEmpty()) {
			query.setParameter("document", "%" + document + "%");
		}
		if (!name.isEmpty()) {
			query.setParameter("name", "%" + name + "%");
		}
		if (!lastName.isEmpty()) {
			query.setParameter("lastName", "%" + lastName + "%");
		}

		clients = query.getResultList();

		return clients;
	}

	@Override
	public boolean merge(Entity entity) {
		try {
			this.entityManager.merge(entity);
			this.entityManager.flush();
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	@Override
	public void remove(Entity entity) throws Exception {

		this.entityManager.remove(this.entityManager.contains(entity) ? entity : this.entityManager.merge(entity));

	}

}
