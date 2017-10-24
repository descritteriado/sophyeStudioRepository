package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ejb.generic.GenericDaoImpl;
import com.jpa.model.Tsegprofiletransaction;

@Stateless
public class ProfileTransactionDao<Entity> extends GenericDaoImpl<Entity> {

	final static Logger logger = Logger.getLogger(ProfileTransactionDao.class);

	@PersistenceContext(unitName = "skeletonJPA")
	private EntityManager entityManager;

	/**
	 * Metodo para consultar la data dados ciertos parametros
	 * 
	 * @param profileName
	 * @param username
	 * @return List<Tsegprofiletransaction>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Tsegprofiletransaction> getProfilesTransactions(Integer profileId, Integer transactionId) throws Exception {

		List<Tsegprofiletransaction> userTransactions = null;
		String preQuery = "SELECT up FROM Tsegprofiletransaction as up " + "WHERE 1=1 ";

		if (profileId != null) {
			preQuery = preQuery.concat("and  up.tsegprofile.id = :profileId ");
		}

		if (transactionId != null) {
			preQuery = preQuery.concat("and  up.tsegtransaction.id = :transactionId ");

		}

		Query query = entityManager.createQuery(preQuery);
		
		if (profileId != null) {
			query.setParameter("profileId", profileId);
		}

		if (transactionId != null) {
			query.setParameter("transactionId", transactionId);
		}		

		userTransactions = query.getResultList();

		return userTransactions;
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
