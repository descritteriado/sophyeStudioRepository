package com.ejb.generic;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

@Stateless
public class GenericDaoImpl<Entity> implements GenericDao<Entity> {

	final static Logger logger = Logger.getLogger(GenericDaoImpl.class);
	
	@PersistenceContext(unitName = "skeletonJPA")
	protected EntityManager entityManager;

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
