package com.controller.securities;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.ejb.services.SecuritiesService;
import com.jpa.model.Tsegprofile;
import com.util.general.UtilsX;
import com.utils.Constants;

@ManagedBean
@ViewScoped
public class ProfileAdministrationController {

	private String description;

	private List<Tsegprofile> profiles;
	private Tsegprofile perfil;
	private boolean edition;
	private Integer actionType;

	final static Logger logger = Logger.getLogger(ProfileAdministrationController.class);

	@EJB
	SecuritiesService securitiesService;

	@PostConstruct
	public void init() {
		perfil = new Tsegprofile();
		edition = true;
	}

	public void newProfile() {
		perfil = new Tsegprofile();
		edition = true;
	}

	/**
	 * Metodo para obtener los perfiles
	 */
	public void consultProfiles(String origin) {

		boolean result = true;

		try {
			profiles = securitiesService.getProfiles(description);
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
	public void processProfile() {
		this.mergeProfile(actionType);
		this.consultProfiles("back");
	}

	/**
	 * Metodo para borrar un perfil
	 */
	public void deleteProfile() {
		this.removeProfile();
		this.consultProfiles("back");
	}

	/**
	 * Metodo para realizar persistencia de perfiles
	 * 
	 * @param action
	 */

	public void mergeProfile(Integer action) {
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

		confirmacion = securitiesService.mergeProfile(this.perfil);

		if (confirmacion) {
			UtilsX.addInfoMessage(messageOK, null);
		} else {
			UtilsX.addErrorMessage(messageERROR, null);
		}

		context.addCallbackParam("confirmation", confirmacion);
	}

	public void removeProfile() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean confirmacion = false;
		String messageOK = "";
		String messageERROR = "";

		messageOK = "Registro eliminado exitosamente";
		messageERROR = "Se produjo un error al eliminar el registro";

		try {
			securitiesService.deleteProfile(this.perfil);
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

	public Tsegprofile getperfil() {
		return perfil;
	}

	public void setperfil(Tsegprofile perfil) {
		this.perfil = perfil;
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

	public List<Tsegprofile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Tsegprofile> profiles) {
		this.profiles = profiles;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
