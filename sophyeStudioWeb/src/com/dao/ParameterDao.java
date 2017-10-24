package com.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ejb.generic.GenericDaoImpl;
import com.jpa.model.Tsegparameter;

@Stateless
public class ParameterDao<Entity> extends GenericDaoImpl<Entity> {

	final static Logger logger = Logger.getLogger(ParameterDao.class);

	@PersistenceContext(unitName = "skeletonJPA")
	private EntityManager entityManager;

	/**
	 * Metodo para consultar los parametros
	 * 
	 * @param parameterName
	 *  * @param parameterDescription
	 * @return Lista de parametros List<Tsegparameter>
	 */
	@SuppressWarnings("unchecked")
	public List<Tsegparameter> getParameters(String parameterName, String parameterDescription) throws Exception {

		List<Tsegparameter> parameters = null;
		String preQuery = "SELECT u FROM Tsegparameter as u " + "WHERE 1=1 ";

		if (!parameterName.isEmpty()) {
			preQuery = preQuery.concat("and  u.name like :parametername ");
		}
		if (!parameterDescription.isEmpty()) {
			preQuery = preQuery.concat("and  u.description like :parameterdescription ");
		}
		
		Query query = entityManager.createQuery(preQuery);

		if (!parameterName.isEmpty()) {
			query.setParameter("parametername", "%" + parameterName + "%");
		}
		
		if (!parameterDescription.isEmpty()) {
			query.setParameter("parameterdescription", "%" + parameterDescription + "%");
		}

		parameters = query.getResultList();

		return parameters;
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
