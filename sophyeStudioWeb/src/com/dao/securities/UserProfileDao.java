package com.dao.securities;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ejb.generic.GenericDaoImpl;
import com.jpa.model.Tseguserprofile;

@Stateless
public class UserProfileDao<Entity> extends GenericDaoImpl<Entity> {

	final static Logger logger = Logger.getLogger(UserProfileDao.class);

	@PersistenceContext(unitName = "skeletonJPA")
	private EntityManager entityManager;

	/**
	 * Metodo para consultar la data dados ciertos parametros
	 * 
	 * @param profileName
	 * @param username
	 * @return List<Tseguserprofile>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Tseguserprofile> getUsersProfiles(Integer userId, Integer profileId) throws Exception {

		List<Tseguserprofile> userProfiles = null;
		String preQuery = "SELECT up FROM Tseguserprofile as up " + "WHERE 1=1 ";

		if (userId != null) {
			preQuery = preQuery.concat("and  up.tseguser.id = :userId ");

		}
		if (profileId != null) {
			preQuery = preQuery.concat("and  up.tsegprofile.id = :profileId ");
		}

		Query query = entityManager.createQuery(preQuery);

		if (userId != null) {
			query.setParameter("userId", userId);
		}
		if (profileId != null) {
			query.setParameter("profileId", profileId);
		}

		userProfiles = query.getResultList();

		return userProfiles;
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
