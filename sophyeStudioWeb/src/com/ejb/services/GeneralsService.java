package com.ejb.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.dao.securities.CantonDao;
import com.dao.securities.CatalogueDao;
import com.dao.securities.CatalogueDetailDao;
import com.dao.securities.ParameterDao;
import com.dao.securities.ParroquiaDao;
import com.dao.securities.ProvinceDao;
import com.jpa.model.Tsegcanton;
import com.jpa.model.Tsegcatalogue;
import com.jpa.model.Tsegcataloguedetail;
import com.jpa.model.Tsegparameter;
import com.jpa.model.Tsegparroquia;
import com.jpa.model.Tsegprovince;

@Stateless
public class GeneralsService {

	final static Logger logger = Logger.getLogger(GeneralsService.class);

	@EJB
	ParroquiaDao<Tsegparroquia> parroquiaDao;

	@EJB
	CantonDao<Tsegcanton> cantonDao;

	@EJB
	ProvinceDao<Tsegprovince> provinceDao;

	@EJB
	ParameterDao<Tsegparameter> parameterDao;

	@EJB
	CatalogueDao<Tsegcatalogue> catalogueDao;

	@EJB
	CatalogueDetailDao<Tsegcataloguedetail> catalogueDetailDao;

	/**
	 * Metodo para consultar provincias dados ciertos parametros
	 * 
	 * @param provinceId
	 * @param provinceName
	 * @return Lista de provincias List<Tgenprovince>
	 */
	public List<Tsegprovince> getProvinces(Integer provinceId, String provinceName) throws Exception {

		List<Tsegprovince> provinces = null;

		provinces = provinceDao.getProvince(provinceId, provinceName);

		return provinces;
	}

	/**
	 * Metodo para guardar o actualizar una Provincia
	 * 
	 * @param Tsegprovince
	 *            province
	 * @return boolean
	 */
	public boolean mergeProvince(Tsegprovince province) {

		return provinceDao.merge(province);
	}

	/**
	 * Metodo para eliminar una provincia
	 * 
	 * @param Tsegprovince
	 *            province
	 */
	public void deleteProvince(Tsegprovince province) throws Exception {

		provinceDao.remove(province);
	}

	/**
	 * Metodo para consultar cantones dados ciertos parametros
	 * 
	 * @param provinceId
	 * @param cantonId
	 * @param cantonName
	 * @return Lista de cantones List<Tgencanton>
	 */
	public List<Tsegcanton> getCantones(Integer provinceId, Integer cantonId, String cantonName) throws Exception {

		List<Tsegcanton> cantones = null;

		cantones = cantonDao.getCanton(provinceId, cantonId, cantonName);

		return cantones;
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
	 * Metodo para guardar o actualizar un Canton
	 * 
	 * @param Tsegcanton
	 *            canton
	 * @return boolean
	 */
	public boolean mergeCanton(Tsegcanton canton) {

		return cantonDao.merge(canton);
	}

	/**
	 * Metodo para eliminar un canton
	 * 
	 * @param Tsegcanton
	 *            canton
	 */
	public void deleteCanton(Tsegcanton canton) throws Exception {

		cantonDao.remove(canton);
	}

	/**
	 * Metodo para consultar parroquias dados ciertos parametros
	 * 
	 * @param cantonId
	 * @param parroquiaId
	 * @param parroquiaName
	 * @return Lista de parroquias List<Tgenparroquia>
	 */
	public List<Tsegparroquia> getParroquias(Integer cantonId, Integer parroquiaId, String parroquiaName)
			throws Exception {

		List<Tsegparroquia> parroquias = null;

		parroquias = parroquiaDao.getParroquia(cantonId, parroquiaId, parroquiaName);

		return parroquias;
	}

	/**
	 * Metodo para guardar o actualizar una Parroquia
	 * 
	 * @param Tsegparroquia
	 *            parroquia
	 * @return boolean
	 */
	public boolean mergeParroquia(Tsegparroquia parroquia) {

		return parroquiaDao.merge(parroquia);
	}

	/**
	 * Metodo para eliminar una Parroquia
	 * 
	 * @param Tsegparroquia
	 *            parroquia
	 */
	public void deleteParroquia(Tsegparroquia parroquia) throws Exception {

		parroquiaDao.remove(parroquia);
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
