package com.dao.securities;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ejb.generic.GenericDaoImpl;
import com.jpa.model.Tsegcataloguedetail;

@Stateless
public class CatalogueDetailDao<Entity> extends GenericDaoImpl<Entity> {

	final static Logger logger = Logger.getLogger(CatalogueDetailDao.class);

	@PersistenceContext(unitName = "skeletonJPA")
	private EntityManager entityManager;

	/**
	 * Metodo para consultar la data dados ciertos parametros
	 * 
	 * @param catalogueId
	 * @param catalogueDesc
	 * @param itemName
	 * @return List<Tsegcataloguedetail>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Tsegcataloguedetail> getCataloguesDetail(Integer catalogueId, String catalogueDesc, String itemName)
			throws Exception {

		List<Tsegcataloguedetail> catalogueDetail = null;
		String preQuery = "SELECT up FROM Tsegcataloguedetail as up " + "WHERE 1=1 ";

		if (catalogueId != null) {
			preQuery = preQuery.concat("and  up.tsegcatalogue.id = :catalogueid ");
		}

		if (catalogueDesc != null) {
			preQuery = preQuery.concat("and  up.tsegcatalogue.description like :cataloguedesc ");

		}

		if (itemName != null) {
			preQuery = preQuery.concat("and  up.name like :itemname ");

		}

		Query query = entityManager.createQuery(preQuery);

		if (catalogueId != null) {
			query.setParameter("catalogueid", catalogueId);
		}

		if (catalogueDesc != null) {
			query.setParameter("cataloguedesc", "%" + catalogueDesc + "%");
		}

		if (itemName != null) {
			query.setParameter("itemname", "%" + itemName + "%");

		}

		catalogueDetail = query.getResultList();

		return catalogueDetail;
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
