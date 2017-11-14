package com.dao.securities;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ejb.generic.GenericDaoImpl;
import com.jpa.model.Tsegparroquia;

@Stateless
public class ParroquiaDao<Entity> extends GenericDaoImpl<Entity> {

	final static Logger logger = Logger.getLogger(ParroquiaDao.class);

	@PersistenceContext(unitName = "skeletonJPA")
	private EntityManager entityManager;

	/**
	 * Metodo para consultar cantones
	 * 
	 * @param provinceId
	 * @param cantonId
	 * @param cantonName
	 * @return Lista de cantones List<Tsegparroquia>
	 */
	@SuppressWarnings("unchecked")
	public List<Tsegparroquia> getParroquia(Integer cantonId, Integer parroquiaId, String parroquiaName) throws Exception {

		List<Tsegparroquia> parroquias = null;
		String preQuery = "SELECT u FROM Tsegparroquia as u " + "WHERE 1=1 ";

		if (cantonId != null) {
			preQuery = preQuery.concat("and  u.canton.id = :cantonId ");
		}
		if (parroquiaId != null) {
			preQuery = preQuery.concat("and  u.id = :parroquiaId ");
		}
		if (!parroquiaName.isEmpty()) {
			preQuery = preQuery.concat("and  u.name like :parroquiaName ");
		}
		preQuery = preQuery.concat(" ORDER BY u.name ASC ");
		Query query = entityManager.createQuery(preQuery);

		if (cantonId != null) {
			query.setParameter("cantonId", cantonId);
		}
		
		if (parroquiaId != null) {
			query.setParameter("parroquiaId", parroquiaId);
		}

		if (!parroquiaName.isEmpty()) {
			query.setParameter("parroquiaName", "%" + parroquiaName + "%");
		}

		parroquias = query.getResultList();

		return parroquias;
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
