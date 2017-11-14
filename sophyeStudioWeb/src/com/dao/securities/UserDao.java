package com.dao.securities;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.ejb.generic.GenericDaoImpl;
import com.jpa.model.Tseguser;

@Stateless
public class UserDao<Entity> extends GenericDaoImpl<Entity> {

	final static Logger logger = Logger.getLogger(UserDao.class);

	@PersistenceContext(unitName = "skeletonJPA")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Object> getMenu(String username, String idModule) {

		Query query = entityManager.createQuery("SELECT m.description as modDesc, t.description as traDesc, t.url "
				+ "FROM Tseguser as u, Tseguserprofile as up, Tsegprofiletransaction as pt, "
				+ "Tsegtransaction as t, Tsegmodule as m " + "WHERE u.deleted= false and u.id = up.tseguser.id "
				+ "and up.tsegprofile.id = pt.tsegprofile.id " + "and pt.tsegtransaction.id = t.id "
				+ "and t.tsegmodule.id = m.id " + "and u.username= :userName  and m.id= :idModule");
		query.setParameter("userName", username);
		query.setParameter("idModule", Integer.parseInt(idModule));
		List<Object> transacciones = query.getResultList();

		return transacciones;
	}

	public boolean validateCredentials(String user, byte[] password) {
		try {
			Query query = entityManager.createQuery(
					"SELECT u.username FROM Tseguser as u WHERE u.deleted= false and u.username= :userName and u.password= :password");
			query.setParameter("userName", user);
			query.setParameter("password", password);
			String validacion = (String) query.getSingleResult();
			return validacion != null ? true : false;
		} catch (NoResultException e) {
			return false;
		}

	}

	/**
	 * Metodo para consultar los usuarios dados ciertos parametros
	 * 
	 * @param username
	 * @param name
	 * @param lastName
	 * @return Lista de usuarios List<Tseguser>
	 */
	@SuppressWarnings("unchecked")
	public List<Tseguser> getUsers(String username, String name, String lastName, Boolean status) throws Exception {

		List<Tseguser> users = null;
		String preQuery = "SELECT u FROM Tseguser as u " + "WHERE u.deleted= false and u.status= :status ";

		if (!username.isEmpty()) {

			preQuery = preQuery.concat("and  u.username like :userName ");
		}
		if (!name.isEmpty()) {
			preQuery = preQuery.concat("and  u.names like :name ");
		}
		if (!lastName.isEmpty()) {
			preQuery = preQuery.concat("and u.lastnames like :lastName ");
		}

		Query query = entityManager.createQuery(preQuery);
		query.setParameter("status", status);

		if (!username.isEmpty()) {
			query.setParameter("userName", "%" + username + "%");
		}
		if (!name.isEmpty()) {
			query.setParameter("name", "%" + name + "%");
		}
		if (!lastName.isEmpty()) {
			query.setParameter("lastName", "%" + lastName + "%");
		}

		users = query.getResultList();

		return users;
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
