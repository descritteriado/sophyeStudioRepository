package com.controller.securities;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.ejb.services.GeneralsService;
import com.jpa.model.Tsegcatalogue;
import com.jpa.model.Tsegcataloguedetail;
import com.util.general.UtilsX;
import com.utils.Constants;

@ManagedBean
@ViewScoped
public class CatalogueDetailAdministrationController {

	private Integer catalogueId;
	private String catalogueDescription;
	private String catalogueDetailName;

	private List<Tsegcataloguedetail> cataloguesDetail;
	private List<Tsegcatalogue> catalogues;
	private Tsegcataloguedetail catalogoDetalle;
	private boolean edition;
	private Integer actionType;

	final static Logger logger = Logger.getLogger(CatalogueDetailAdministrationController.class);

	@EJB
	GeneralsService generalsService;

	@PostConstruct
	public void init() {

		try {
			catalogoDetalle = new Tsegcataloguedetail();
			catalogoDetalle.setTsegcatalogue(new Tsegcatalogue());
			edition = true;
			catalogues = generalsService.getCatalogues("", "");
		} catch (Exception e) {
			logger.error(e);
		}

	}

	public void newCatalogueDetail() {
		catalogoDetalle = new Tsegcataloguedetail();
		catalogoDetalle.setTsegcatalogue(new Tsegcatalogue());
		edition = true;
	}

	/**
	 * Metodo para obtener catalogos detalle
	 */
	public void consultCataloguesDetail(String origin) {

		boolean result = true;

		try {
			cataloguesDetail = generalsService.getCataloguesDetail(catalogueId, catalogueDescription,
					catalogueDetailName);
			result = true;

		} catch (Exception e) {
			logger.error(e);
			result = false;

		}

		if (origin.equals("front")) {
			if (result) {
				UtilsX.addInfoMessage("B" + Constants.u_acentuada + "queda Exitosa", null);
			} else {
				UtilsX.addErrorMessage("Se produjo un error al realizar la b" + Constants.u_acentuada + "squeda", null);
			}
		}

	}

	/**
	 * Metodo para realizar las operaciones CRUD
	 */
	public void processCatalogueDetail() {
		this.mergeCatalogueDetail(actionType);
		this.consultCataloguesDetail("back");
	}

	/**
	 * Metodo para borrar un catalogo detalle
	 */
	public void deleteCatalogueDetail() {
		this.removeCatalogueDetail();
		this.consultCataloguesDetail("back");
	}

	/**
	 * Metodo para realizar persistencia de catalogos detalle
	 * 
	 * @param action
	 */

	public void mergeCatalogueDetail(Integer action) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean confirmacion = false;
		String messageOK = "";
		String messageERROR = "";

		if (action.equals(Integer.parseInt(Constants.IN))) {
			messageOK = "Registro guardado exitosamente";
			messageERROR = "Se produjo un error al guardar el registro";
		}
		if (action.equals(Integer.parseInt(Constants.UP))) {
			messageOK = "Registro actualizado exitosamente";
			messageERROR = "Se produjo un error al actualizar el registro";
		}
		if (action.equals(Integer.parseInt(Constants.DE))) {
			messageOK = "Registro eliminado exitosamente";
			messageERROR = "Se produjo un error al eliminar el registro";
			// parametro.setDeleted(true);
		}

		confirmacion = generalsService.mergeCatalogueDetail(this.catalogoDetalle);

		if (confirmacion) {
			UtilsX.addInfoMessage(messageOK, null);
		} else {
			UtilsX.addErrorMessage(messageERROR, null);
		}

		context.addCallbackParam("confirmation", confirmacion);
	}

	public void removeCatalogueDetail() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean confirmacion = false;
		String messageOK = "";
		String messageERROR = "";

		messageOK = "Registro eliminado exitosamente";
		messageERROR = "Se produjo un error al eliminar el registro";

		try {
			generalsService.deleteCatalogueDetail(this.catalogoDetalle);
			confirmacion = true;
		} catch (Exception e) {
			logger.error(e);
			confirmacion = false;
		}

		if (confirmacion) {
			UtilsX.addInfoMessage(messageOK, null);
		} else {
			UtilsX.addErrorMessage(messageERROR, null);
		}

		context.addCallbackParam("confirmation", confirmacion);
	}

	public boolean isEdition() {
		return edition;
	}

	public void setEdition(boolean edition) {
		this.edition = edition;
	}

	public Integer getActionType() {
		return actionType;
	}

	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}

	public String getCatalogueDescription() {
		return catalogueDescription;
	}

	public void setCatalogueDescription(String catalogueDescription) {
		this.catalogueDescription = catalogueDescription;
	}

	public Integer getCatalogueId() {
		return catalogueId;
	}

	public void setCatalogueId(Integer catalogueId) {
		this.catalogueId = catalogueId;
	}

	public String getCatalogueDetailName() {
		return catalogueDetailName;
	}

	public void setCatalogueDetailName(String catalogueDetailName) {
		this.catalogueDetailName = catalogueDetailName;
	}

	public List<Tsegcataloguedetail> getCataloguesDetail() {
		return cataloguesDetail;
	}

	public void setCataloguesDetail(List<Tsegcataloguedetail> cataloguesDetail) {
		this.cataloguesDetail = cataloguesDetail;
	}

	public Tsegcataloguedetail getCatalogoDetalle() {
		return catalogoDetalle;
	}

	public void setCatalogoDetalle(Tsegcataloguedetail catalogoDetalle) {
		this.catalogoDetalle = catalogoDetalle;
	}

	public List<Tsegcatalogue> getCatalogues() {
		return catalogues;
	}

	public void setCatalogues(List<Tsegcatalogue> catalogues) {
		this.catalogues = catalogues;
	}

}
