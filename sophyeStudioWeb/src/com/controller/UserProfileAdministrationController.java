package com.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.ejb.services.SecuritiesService;
import com.jpa.model.Tsegprofile;
import com.jpa.model.Tseguser;
import com.jpa.model.Tseguserprofile;
import com.util.general.UtilsX;
import com.utils.Constants;

@ManagedBean
@ViewScoped
public class UserProfileAdministrationController {

	private Integer userId;
	private Integer profileId;
	private List<Tseguserprofile> usersProfiles;
	private Tseguserprofile usuarioPerfil;
	private boolean edition;
	private Integer actionType;
	private List<Tseguser> users;
	private List<Tsegprofile> profiles;

	final static Logger logger = Logger.getLogger(UserProfileAdministrationController.class);

	@EJB
	SecuritiesService securitiesService;

	@PostConstruct
	public void init() {

		try {
			users = securitiesService.getUsers("", "", "", false);
			profiles = securitiesService.getProfiles("");
		} catch (Exception e) {
			logger.error(e);
		}

		usuarioPerfil = new Tseguserprofile();
		usuarioPerfil.setTsegprofile(new Tsegprofile());
		usuarioPerfil.setTseguser(new Tseguser());
		edition = true;
	}

	public void newUserProfile() {
		usuarioPerfil = new Tseguserprofile();
		usuarioPerfil.setTsegprofile(new Tsegprofile());
		usuarioPerfil.setTseguser(new Tseguser());
		edition = true;
	}

	/**
	 * Metodo para obtener la Data
	 */
	public void consultUserProfile(String origin) {

		boolean result = true;

		try {
			usersProfiles = securitiesService.getUsersProfiles(userId, profileId);
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
	public void processUserProfile() {
		this.mergeUserProfile(actionType);
		this.consultUserProfile("back");
	}

	/**
	 * Metodo para borrar un registro
	 */
	public void deleteUserProfile() {
		this.removeUserProfile();
		this.consultUserProfile("back");
	}

	/**
	 * Metodo para realizar persistencia
	 * 
	 * @param action
	 */

	public void mergeUserProfile(Integer action) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean confirmacion = false;
		boolean exist = false;
		String messageOK = "";
		String messageERROR = "";

		if (action.equals(Integer.parseInt(Constants.IN))) {
			messageOK = "Registro guardado exitosamente";
			messageERROR = "Se produjo un error al guardar el registro";

			try {
				exist = securitiesService.getUsersProfiles(this.usuarioPerfil.getTseguser().getId(),
						this.usuarioPerfil.getTsegprofile().getId()).size() == 0 ? false : true;
			} catch (Exception e) {
				logger.error(e);
			}
		}
		if (action.equals(Integer.parseInt(Constants.UP))) {
			messageOK = "Registro actualizado exitosamente";
			messageERROR = "Se produjo un error al actualizar el registro";
		}
		if (action.equals(Integer.parseInt(Constants.DE))) {
			messageOK = "Registro eliminado exitosamente";
			messageERROR = "Se produjo un error al eliminar el registro";
		}

		if (!exist) {
			confirmacion = securitiesService.mergeUsersProfiles(this.usuarioPerfil);
		}else{
			messageERROR = "La relaci"+Constants.o_acentuada+"n Usuario-Perfil ya existe";
		}
		if (confirmacion) {
			UtilsX.addInfoMessage(messageOK, null);
		} else {
			UtilsX.addErrorMessage(messageERROR, null);
		}

		context.addCallbackParam("confirmation", confirmacion);
	}

	public void removeUserProfile() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean confirmacion = false;
		String messageOK = "";
		String messageERROR = "";

		messageOK = "Registro eliminado exitosamente";
		messageERROR = "Se produjo un error al eliminar el registro";

		confirmacion = securitiesService.deleteUsersProfiles(this.usuarioPerfil);

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

	public List<Tseguserprofile> getUsersProfiles() {
		return usersProfiles;
	}

	public void setUsersProfiles(List<Tseguserprofile> usersProfiles) {
		this.usersProfiles = usersProfiles;
	}

	public Tseguserprofile getUsuarioPerfil() {
		return usuarioPerfil;
	}

	public void setUsuarioPerfil(Tseguserprofile usuarioPerfil) {
		this.usuarioPerfil = usuarioPerfil;
	}

	public List<Tseguser> getUsers() {
		return users;
	}

	public void setUsers(List<Tseguser> users) {
		this.users = users;
	}

	public List<Tsegprofile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Tsegprofile> profiles) {
		this.profiles = profiles;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

}
