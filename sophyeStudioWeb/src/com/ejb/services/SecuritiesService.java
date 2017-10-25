package com.ejb.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.dao.securities.CatalogueDao;
import com.dao.securities.CatalogueDetailDao;
import com.dao.securities.ModuleDao;
import com.dao.securities.ParameterDao;
import com.dao.securities.ProfileDao;
import com.dao.securities.ProfileTransactionDao;
import com.dao.securities.TransactionDao;
import com.dao.securities.UserDao;
import com.dao.securities.UserProfileDao;
import com.jpa.model.Tsegcatalogue;
import com.jpa.model.Tsegcataloguedetail;
import com.jpa.model.Tsegmodule;
import com.jpa.model.Tsegparameter;
import com.jpa.model.Tsegprofile;
import com.jpa.model.Tsegprofiletransaction;
import com.jpa.model.Tsegtransaction;
import com.jpa.model.Tseguser;
import com.jpa.model.Tseguserprofile;

@Stateless
public class SecuritiesService {

	final static Logger logger = Logger.getLogger(SecuritiesService.class);

	@EJB
	UserDao<Tseguser> userDao;

	@EJB
	ProfileDao<Tsegprofile> profileDao;

	@EJB
	ModuleDao<Tsegmodule> moduleDao;

	@EJB
	TransactionDao<Tsegtransaction> transactionDao;

	@EJB
	UserProfileDao<Tseguserprofile> userProfileDao;

	@EJB
	ProfileTransactionDao<Tsegprofiletransaction> profileTransactionDao;

	@EJB
	ParameterDao<Tsegparameter> parameterDao;

	@EJB
	CatalogueDao<Tsegcatalogue> catalogueDao;

	@EJB
	CatalogueDetailDao<Tsegcataloguedetail> catalogueDetailDao;

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
	 * Metodo para consultar las transacciones dados ciertos parametros
	 * 
	 * @param transactionName
	 * @return Lista de modulos List<Tsegtransaction>
	 */
	public List<Tsegtransaction> getTransactions(String transactionName) throws Exception {

		List<Tsegtransaction> transactions = null;

		transactions = transactionDao.getTransactions(transactionName);

		return transactions;
	}

	/**
	 * Metodo para consultar la data dados ciertos parametros
	 * 
	 * @param userId
	 * @param profileId
	 * @return List<Tseguserprofile>
	 */
	public List<Tseguserprofile> getUsersProfiles(Integer userId, Integer profileId) throws Exception {

		List<Tseguserprofile> userProfile = null;

		userProfile = userProfileDao.getUsersProfiles(userId, profileId);

		return userProfile;
	}

	/**
	 * Metodo para consultar la data dados ciertos parametros
	 * 
	 * @param profileId
	 * @param transactionId
	 * @return List<Tsegprofiletransaction>
	 */
	public List<Tsegprofiletransaction> getProfilesTransactions(Integer profileId, Integer transactionId)
			throws Exception {

		List<Tsegprofiletransaction> profileTransactions = null;

		profileTransactions = profileTransactionDao.getProfilesTransactions(profileId, transactionId);

		return profileTransactions;
	}

	/**
	 * Metodo para consultar los parametros
	 * 
	 * @param parameterName
	 *            * @param parameterDescription
	 * @return Lista de parametros List<Tsegparameter>
	 */
	public List<Tsegparameter> getParameters(String parameterName, String parameterDescription) throws Exception {

		List<Tsegparameter> parameters = null;

		parameters = parameterDao.getParameters(parameterName, parameterDescription);

		return parameters;
	}

	/**
	 * Metodo para consultar los catalogos
	 * 
	 * @param catalogueName
	 *            * @param catalogueDescription
	 * @return Lista de catalogos List<Tsegcatalogue>
	 */
	public List<Tsegcatalogue> getCatalogues(String catalogueName, String catalogueDescription) throws Exception {

		List<Tsegcatalogue> catalogues = null;

		catalogues = catalogueDao.getCatalogues(catalogueName, catalogueDescription);

		return catalogues;
	}

	/**
	 * Metodo para consultar los catalogos detalle
	 * 
	 * @param catalogueName
	 *            * @param catalogueDescription
	 * @return Lista de catalogos List<Tsegcatalogue>
	 */
	public List<Tsegcataloguedetail> getCataloguesDetail(Integer catalogueId, String catalogueDesc, String itemName)
			throws Exception {

		List<Tsegcataloguedetail> cataloguesDetail = null;

		cataloguesDetail = catalogueDetailDao.getCataloguesDetail(catalogueId, catalogueDesc, itemName);

		return cataloguesDetail;
	}

	/**
	 * Metodo para guardar o actualizar un Usuario
	 * 
	 * @param Tseguser
	 *            user
	 * @return boolean
	 */
	public boolean mergeUser(Tseguser user) {

		return userDao.merge(user);
	}

	/**
	 * Metodo para guardar o actualizar un Perfil
	 * 
	 * @param Tsegprofile
	 *            profile
	 * @return boolean
	 */
	public boolean mergeProfile(Tsegprofile profile) {

		return profileDao.merge(profile);
	}

	/**
	 * Metodo para eliminar un peril
	 * 
	 * @param Tsegprofile
	 *            profile
	 */
	public void deleteProfile(Tsegprofile profile) throws Exception {

		profileDao.remove(profile);
	}

	/**
	 * Metodo para guardar o actualizar un Modulo
	 * 
	 * @param Tsegmodule
	 *            module
	 * @return boolean
	 */
	public boolean mergeModule(Tsegmodule module) {

		return moduleDao.merge(module);
	}

	/**
	 * Metodo para eliminar un modulo
	 * 
	 * @param Tsegmodule
	 *            module
	 */
	public void deleteModule(Tsegmodule module)  throws Exception{

		moduleDao.remove(module);
	}

	/**
	 * Metodo para guardar o actualizar una Transacion
	 * 
	 * @param Tsegtransaction
	 *            transaction
	 * @return boolean
	 */
	public boolean mergeTransaction(Tsegtransaction transaction) {

		return transactionDao.merge(transaction);
	}

	/**
	 * Metodo para eliminar una Transaccion
	 * 
	 * @param Tsegtransaction
	 *            transaction
	 */
	public void deleteTransaction(Tsegtransaction transaction)  throws Exception {

		transactionDao.remove(transaction);
	}

	/**
	 * Metodo para guardar o actualizar Tseguserprofile
	 * 
	 * @param Tseguserprofile
	 *            userPofile
	 * @return boolean
	 */
	public boolean mergeUsersProfiles(Tseguserprofile userPofile) {

		return userProfileDao.merge(userPofile);
	}

	/**
	 * Metodo para eliminar Tseguserprofile
	 * 
	 * @param Tseguserprofile
	 *            userPofile
	 */
	public void deleteUsersProfiles(Tseguserprofile userPofile)  throws Exception {

		userProfileDao.remove(userPofile);
	}

	/**
	 * Metodo para guardar o actualizar Tsegprofiletransaction
	 * 
	 * @param Tsegprofiletransaction
	 *            profileTransaction
	 * @return boolean
	 */
	public boolean mergeProfilesTransactions(Tsegprofiletransaction profileTransaction) {

		return profileTransactionDao.merge(profileTransaction);
	}

	/**
	 * Metodo para eliminar Tsegprofiletransaction
	 * 
	 * @param Tsegprofiletransaction
	 *            profileTransaction
	 */
	public void deleteProfilesTransactions(Tsegprofiletransaction profileTransaction)  throws Exception {

		profileTransactionDao.remove(profileTransaction);
	}

	/**
	 * Metodo para guardar o actualizar un Parametro
	 * 
	 * @param Tsegparameter
	 *            parameter
	 */
	public boolean mergeParameter(Tsegparameter parameter) {

		return parameterDao.merge(parameter);
	}

	/**
	 * Metodo para eliminar un parametro
	 * 
	 * @param Tsegparameter
	 *            parameter
	 */
	public void deleteParameter(Tsegparameter parameter) throws Exception {

		parameterDao.remove(parameter);
	}

	/**
	 * Metodo para guardar o actualizar un Catalogo
	 * 
	 * @param Tsegcatalogue
	 *            catalogue
	 * @return boolean
	 */
	public boolean mergeCatalogue(Tsegcatalogue catalogue) {

		return catalogueDao.merge(catalogue);
	}

	/**
	 * Metodo para eliminar un catalogo
	 * 
	 * @param Tsegcatalogue
	 *            catalogue
	 */
	public void deleteCatalogue(Tsegcatalogue catalogue) throws Exception {

		catalogueDao.remove(catalogue);
	}

	/**
	 * Metodo para guardar o actualizar un Detalle Catalogo
	 * 
	 * @param Tsegcataloguedetail
	 *            catalogueDetail
	 * @return boolean
	 */
	public boolean mergeCatalogueDetail(Tsegcataloguedetail catalogueDetail) {

		return catalogueDetailDao.merge(catalogueDetail);
	}

	/**
	 * Metodo para eliminar un detalle catalogo
	 * 
	 * @param Tsegcataloguedetail
	 *            catalogueDetail
	 */
	public void deleteCatalogueDetail(Tsegcataloguedetail catalogueDetail) throws Exception {

		 catalogueDetailDao.remove(catalogueDetail);
	}

}
