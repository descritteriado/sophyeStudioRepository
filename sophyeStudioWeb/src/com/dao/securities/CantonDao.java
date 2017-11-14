package com.dao.securities;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ejb.generic.GenericDaoImpl;
import com.jpa.model.Tsegcanton;

@Stateless
public class CantonDao<Entity> extends GenericDaoImpl<Entity> {

	final static Logger logger = Logger.getLogger(CantonDao.class);

	@PersistenceContext(unitName = "skeletonJPA")
	private EntityManager entityManager;

	/**
	 * Metodo para consultar cantones
	 * 
	 * @param provinceId
	 * @param cantonId
	 * @param cantonName
	 * @return Lista de cantones List<Tsegcanton>
	 */
	@SuppressWarnings("unchecked")
	public List<Tsegcanton> getCanton(Integer provinceId, Integer cantonId, String cantonName) throws Exception {

		List<Tsegcanton> cantones = null;
		String preQuery = "SELECT u FROM Tsegcanton as u " + "WHERE 1=1 ";

		if (provinceId != null) {
			preQuery = preQuery.concat("and  u.province.id = :provinceId ");
		}
		if (cantonId != null) {
			preQuery = preQuery.concat("and  u.id = :cantonId ");
		}
		if (!cantonName.isEmpty()) {
			preQuery = preQuery.concat("and  u.name like :cantonName ");
		}
		preQuery = preQuery.concat(" ORDER BY u.name ASC ");
		Query query = entityManager.createQuery(preQuery);

		if (provinceId != null) {
			query.setParameter("provinceId", provinceId);
		}
		
		if (cantonId != null) {
			query.setParameter("cantonId", cantonId);
		}

		if (!cantonName.isEmpty()) {
			query.setParameter("cantonName", "%" + cantonName + "%");
		}

		cantones = query.getResultList();

		return cantones;
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
