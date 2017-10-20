package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ejb.generic.GenericDaoImpl;
import com.jpa.model.Tsegtransaction;

@Stateless
public class TransactionDao<Entity> extends GenericDaoImpl<Entity> {

	final static Logger logger = Logger.getLogger(TransactionDao.class);

	@PersistenceContext(unitName = "skeletonJPA")
	private EntityManager entityManager;

	/**
	 * Metodo para consultar las transacciones dados ciertos parametros
	 * 
	 * @param transactionName
	 * @return Lista de transacciones List<Tsegtransaction>
	 */
	@SuppressWarnings("unchecked")
	public List<Tsegtransaction> getTransactions(String transactionName) throws Exception {

		List<Tsegtransaction> modules = null;
		String preQuery = "SELECT u FROM Tsegtransaction as u " + "WHERE 1=1 ";

		preQuery = preQuery.concat(!transactionName.isEmpty() ? "and  u.description like :description" : "");

		Query query = entityManager.createQuery(preQuery);

		if (!transactionName.isEmpty()) {
			query.setParameter("description", "%" + transactionName + "%");
		}

		modules = query.getResultList();

		return modules;
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
	public boolean remove(Entity entity) {
		try {
			this.entityManager.remove(this.entityManager.contains(entity) ? entity : this.entityManager.merge(entity));
			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}


}
