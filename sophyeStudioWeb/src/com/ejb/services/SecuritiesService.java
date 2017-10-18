package com.ejb.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.dao.ModuleDao;
import com.dao.ProfileDao;
import com.dao.UserDao;
import com.jpa.model.Tsegmodule;
import com.jpa.model.Tsegprofile;
import com.jpa.model.Tseguser;

@Stateless
public class SecuritiesService  {

	final static Logger logger = Logger.getLogger(SecuritiesService.class);

	@EJB
	UserDao<Tseguser> userDao;

	@EJB
	ProfileDao<Tsegprofile> profileDao;
	
	@EJB
	ModuleDao<Tsegmodule> moduleDao;

	/**
	 * Metodo que obtiene el Menu
	 * 
	 * @param String
	 *            username
	 * @param String
	 *            idModule
	 * @return List<Object> Menu
	 */
	public List<Object> getMenu(String username, String idModule) {

		List<Object> transacciones = userDao.getMenu(username, idModule);

		return transacciones;
	}

	/**
	 * Metodo para validar las credenciales para el proceso de Login
	 * 
	 * @param String
	 *            user
	 * @param String
	 *            password
	 * @return boolean
	 */
	public boolean validateCredentials(String user, byte[] password) {

		System.out.println("test");
		return userDao.validateCredentials(user, password);

	}

	/**
	 * Metodo para consultar los usuarios dados ciertos parametros
	 * 
	 * @param username
	 * @param name
	 * @param lastName
	 * @return Lista de usuarios List<Tseguser>
	 */
	public List<Tseguser> getUsers(String username, String name, String lastName, Boolean status) throws Exception {

		List<Tseguser> users = null;
		System.out.println("test");

		users = userDao.getUsers(username, name, lastName, status);

		return users;
	}

	/**
	 * Metodo para consultar los perfiles dados ciertos parametros
	 * 
	 * @param profileName
	 * @return Lista de perfiles List<Tsegprofile>
	 */
	public List<Tsegprofile> getProfiles(String profileName) throws Exception {

		List<Tsegprofile> profiles = null;

		profiles = profileDao.getProfiles(profileName);

		return profiles;
	}
	
	/**
	 * Metodo para consultar los modulos dados ciertos parametros
	 * 
	 * @param moduleName
	 * @return Lista de modulos List<Tsegmodule>
	 */
	public List<Tsegmodule> getModules(String moduleName) throws Exception {

		List<Tsegmodule> modules = null;

		modules = moduleDao.getModules(moduleName);

		return modules;
	}

	/**
	 * Metodo para guardar o actualizar un Usuario
	 * @param Tseguser user
	 * @return boolean 
	 */
	public boolean mergeUser(Tseguser user) {

		return userDao.merge(user);
	}

	/**
	 * Metodo para guardar o actualizar un Perfil
	 * @param Tsegprofile profile
	 * @return boolean 
	 */
	public boolean mergeProfile(Tsegprofile profile) {

		return profileDao.merge(profile);
	}

	/**
	 * Metodo para eliminar un peril 
	 * @param Tsegprofile profile
	 * @return boolean
	 */
	public boolean deleteProfile(Tsegprofile profile) {

		return profileDao.remove(profile);
	}
	
	/**
	 * Metodo para guardar o actualizar un Modulo
	 * @param Tsegmodule module
	 * @return boolean 
	 */
	public boolean mergeModule(Tsegmodule module) {

		return moduleDao.merge(module);
	}

	/**
	 * Metodo para eliminar un modulo 
	 * @param Tsegmodule module
	 * @return boolean
	 */
	public boolean deleteModule(Tsegmodule module) {

		return moduleDao.remove(module);
	}

}
