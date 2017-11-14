package com.controller.securities;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.ejb.services.GeneralsService;
import com.jpa.model.Tsegparameter;
import com.util.general.UtilsX;
import com.utils.Constants;

@ManagedBean
@ViewScoped
public class ParameterAdministrationController {

	private String parameterName;
	private String parameterDescription;

	private List<Tsegparameter> parameters;
	private Tsegparameter parametro;
	private boolean edition;
	private Integer actionType;

	final static Logger logger = Logger.getLogger(ParameterAdministrationController.class);

	@EJB
	GeneralsService generalsService;

	@PostConstruct
	public void init() {
		parametro = new Tsegparameter();
		edition = true;
	}

	public void newParameter() {
		parametro = new Tsegparameter();
		edition = true;
	}

	/**
	 * Metodo para obtener los parametros
	 */
	public void consultParameters(String origin) {

		boolean result = true;

		try {
			parameters = generalsService.getParameters(parameterName, parameterDescription);
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
	public void processParameter() {
		this.mergeParameter(actionType);
		this.consultParameters("back");
	}

	/**
	 * Metodo para borrar un parametro
	 */
	public void deleteParameter() {
		this.removeParameter();
		this.consultParameters("back");
	}

	/**
	 * Metodo para realizar persistencia de parametros
	 * 
	 * @param action
	 */

	public void mergeParameter(Integer action) {
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

		confirmacion = generalsService.mergeParameter(this.parametro);

		if (confirmacion) {
			UtilsX.addInfoMessage(messageOK, null);
		} else {
			UtilsX.addErrorMessage(messageERROR, null);
		}

		context.addCallbackParam("confirmation", confirmacion);
	}

	public void removeParameter() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean confirmacion = false;
		String messageOK = "";
		String messageERROR = "";

		messageOK = "Registro eliminado exitosamente";
		messageERROR = "Se produjo un error al eliminar el registro";

		try {
			generalsService.deleteParameter(this.parametro);
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

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public List<Tsegparameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Tsegparameter> parameters) {
		this.parameters = parameters;
	}

	public Tsegparameter getParametro() {
		return parametro;
	}

	public void setParametro(Tsegparameter parametro) {
		this.parametro = parametro;
	}

	public String getParameterDescription() {
		return parameterDescription;
	}

	public void setParameterDescription(String parameterDescription) {
		this.parameterDescription = parameterDescription;
	}

}
