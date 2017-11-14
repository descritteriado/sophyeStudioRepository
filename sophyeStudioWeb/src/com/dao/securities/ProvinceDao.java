package com.dao.securities;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ejb.generic.GenericDaoImpl;
import com.jpa.model.Tsegprovince;

@Stateless
public class ProvinceDao<Entity> extends GenericDaoImpl<Entity> {

	final static Logger logger = Logger.getLogger(ProvinceDao.class);

	@PersistenceContext(unitName = "skeletonJPA")
	private EntityManager entityManager;

	/**
	 * Metodo para consultar provincias
	 * 
	 * @param provinceId
	 * @param provinceName
	 * @return Lista de provincias List<Tsegprovince>
	 */
	@SuppressWarnings("unchecked")
	public List<Tsegprovince> getProvince(Integer provinceId, String provinceName) throws Exception {

		List<Tsegprovince> provinces = null;
		String preQuery = "SELECT u FROM Tsegprovince as u " + "WHERE 1=1 ";

		if (provinceId != null) {
			preQuery = preQuery.concat("and  u.id = :provinceId ");
		}
		if (!provinceName.isEmpty()) {
			preQuery = preQuery.concat("and  u.name like :provinceName ");
		}
		preQuery = preQuery.concat(" ORDER BY u.name ASC ");

		Query query = entityManager.createQuery(preQuery);

		if (provinceId != null) {
			query.setParameter("provinceId", provinceId);
		}

		if (!provinceName.isEmpty()) {
			query.setParameter("provinceName", "%" + provinceName + "%");
		}

		provinces = query.getResultList();

		return provinces;
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
