package com.dao.securities;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ejb.generic.GenericDaoImpl;
import com.jpa.model.Tsegcatalogue;

@Stateless
public class CatalogueDao<Entity> extends GenericDaoImpl<Entity> {

	final static Logger logger = Logger.getLogger(CatalogueDao.class);

	@PersistenceContext(unitName = "skeletonJPA")
	private EntityManager entityManager;

	/**
	 * Metodo para consultar los catalogos
	 * 
	 * @param catalogueName
	 *            * @param catalogueDescription
	 * @return Lista de catalogos List<Tsegcatalogue>
	 */
	@SuppressWarnings("unchecked")
	public List<Tsegcatalogue> getCatalogues(String catalogueName, String catalogueDescription) throws Exception {

		List<Tsegcatalogue> catalogues = null;
		String preQuery = "SELECT u FROM Tsegcatalogue as u " + "WHERE 1=1 ";

		if (!catalogueName.isEmpty()) {
			preQuery = preQuery.concat("and  u.name like :cataloguename ");
		}
		if (!catalogueDescription.isEmpty()) {
			preQuery = preQuery.concat("and  u.description like :cataloguedescription ");
		}

		Query query = entityManager.createQuery(preQuery);

		if (!catalogueName.isEmpty()) {
			query.setParameter("cataloguename", "%" + catalogueName + "%");
		}

		if (!catalogueDescription.isEmpty()) {
			query.setParameter("cataloguedescription", "%" + catalogueDescription + "%");
		}

		catalogues = query.getResultList();

		return catalogues;
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
