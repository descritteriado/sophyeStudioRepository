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
import com.util.general.UtilsX;
import com.utils.Constants;

@ManagedBean
@ViewScoped
public class CatalogueAdministrationController {

	private String catalogueName;
	private String catalogueDescription;

	private List<Tsegcatalogue> catalogues;
	private Tsegcatalogue catalogo;
	private boolean edition;
	private Integer actionType;

	final static Logger logger = Logger.getLogger(CatalogueAdministrationController.class);

	@EJB
	GeneralsService generalsService;

	@PostConstruct
	public void init() {
		catalogo = new Tsegcatalogue();
		edition = true;
	}

	public void newCatalogue() {
		catalogo = new Tsegcatalogue();
		edition = true;
	}

	/**
	 * Metodo para obtener los catalogos
	 */
	public void consultCatalogues(String origin) {

		boolean result = true;

		try {
			catalogues = generalsService.getCatalogues(catalogueName, catalogueDescription);
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
	public void processCatalogue() {
		this.mergeCatalogue(actionType);
		this.consultCatalogues("back");
	}

	/**
	 * Metodo para borrar un parametro
	 */
	public void deleteCatalogue() {
		this.removeCatalogue();
		this.consultCatalogues("back");
	}

	/**
	 * Metodo para realizar persistencia de catalogos
	 * 
	 * @param action
	 */

	public void mergeCatalogue(Integer action) {
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

		confirmacion = generalsService.mergeCatalogue(this.catalogo);

		if (confirmacion) {
			UtilsX.addInfoMessage(messageOK, null);
		} else {
			UtilsX.addErrorMessage(messageERROR, null);
		}

		context.addCallbackParam("confirmation", confirmacion);
	}

	public void removeCatalogue() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean confirmacion = false;
		String messageOK = "";
		String messageERROR = "";

		messageOK = "Registro eliminado exitosamente";
		messageERROR = "Se produjo un error al eliminar el registro";

		try {

			if (generalsService.getCataloguesDetail(this.catalogo.getId(), "", "").size() == 0) {
				generalsService.deleteCatalogue(this.catalogo);
				confirmacion = true;
			}else{
				messageERROR = "No se puede eliminar, existen " + Constants.I_acentuada + "tems dependientes del Cat" + Constants.a_acentuada+ "logo";
				confirmacion = false;				
			}
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

	public String getCatalogueName() {
		return catalogueName;
	}

	public void setCatalogueName(String catalogueName) {
		this.catalogueName = catalogueName;
	}

	public String getCatalogueDescription() {
		return catalogueDescription;
	}

	public void setCatalogueDescription(String catalogueDescription) {
		this.catalogueDescription = catalogueDescription;
	}

	public List<Tsegcatalogue> getCatalogues() {
		return catalogues;
	}

	public void setCatalogues(List<Tsegcatalogue> catalogues) {
		this.catalogues = catalogues;
	}

	public Tsegcatalogue getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(Tsegcatalogue catalogo) {
		this.catalogo = catalogo;
	}

}
