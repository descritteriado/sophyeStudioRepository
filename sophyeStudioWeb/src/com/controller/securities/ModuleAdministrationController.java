package com.controller.securities;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.ejb.services.SecuritiesService;
import com.jpa.model.Tsegmodule;
import com.util.general.UtilsX;
import com.utils.Constants;

@ManagedBean
@ViewScoped
public class ModuleAdministrationController {

	private String description;
	private List<Tsegmodule> modules;
	private Tsegmodule modulo;
	private boolean edition;
	private Integer actionType;

	final static Logger logger = Logger.getLogger(ModuleAdministrationController.class);

	@EJB
	SecuritiesService securitiesService;

	@PostConstruct
	public void init() {
		modulo = new Tsegmodule();
		edition = true;
	}

	public void newModule() {
		modulo = new Tsegmodule();
		edition = true;
	}

	/**
	 * Metodo para obtener los modulos
	 */
	public void consultModules(String origin) {

		boolean result = true;

		try {
			modules = securitiesService.getModules(description);
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
	public void processModule() {
		this.mergeModule(actionType);
		this.consultModules("back");
	}

	/**
	 * Metodo para borrar un perfil
	 */
	public void deleteModule() {
		this.removeModule();
		this.consultModules("back");
	}

	/**
	 * Metodo para realizar persistencia de modulos
	 * 
	 * @param action
	 */

	public void mergeModule(Integer action) {
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
			// perfil.setDeleted(true);
		}

		confirmacion = securitiesService.mergeModule(this.modulo);

		if (confirmacion) {
			UtilsX.addInfoMessage(messageOK, null);
		} else {
			UtilsX.addErrorMessage(messageERROR, null);
		}

		context.addCallbackParam("confirmation", confirmacion);
	}

	public void removeModule() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean confirmacion = false;
		String messageOK = "";
		String messageERROR = "";

		messageOK = "Registro eliminado exitosamente";
		messageERROR = "Se produjo un error al eliminar el registro";

		try {
			securitiesService.deleteModule(this.modulo);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Tsegmodule> getModules() {
		return modules;
	}

	public void setModules(List<Tsegmodule> modules) {
		this.modules = modules;
	}

	public Tsegmodule getModulo() {
		return modulo;
	}

	public void setModulo(Tsegmodule modulo) {
		this.modulo = modulo;
	}

}
