package com.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.ejb.services.SecuritiesService;
import com.jpa.model.Tseguser;
import com.util.general.UtilsX;
import com.utils.Constants;

@ManagedBean
@ViewScoped
public class UserAdministrationController {

	private String userName;
	private String name;
	private String lastName;
	private boolean status;
	private List<Tseguser> users;
	private Tseguser usuario;
	private boolean edition;
	private Integer actionType;

	final static Logger logger = Logger.getLogger(UserAdministrationController.class);
	@EJB
	SecuritiesService securitiesService;

	@PostConstruct
	public void init() {
		usuario = new Tseguser();
		usuario.setKeyInCourse("");
		edition = true;
	}

	public void newUser() {
		usuario = new Tseguser();
		usuario.setKeyInCourse("");
		usuario.setIstemporal(false);
		usuario.setStatus(false);
		usuario.setDeleted(false);
		edition = true;
	}

	/**
	 * Metodo para obtener los usuarios
	 */
	public void consultUsers(String origin) {
		
		boolean result = true;

		try {
			users = securitiesService.getUsers(userName, name, lastName, status);
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
			result = false;

		}

		if (origin.equals("front")) {
			if (result) {
				UtilsX.addInfoMessage("B"+Constants.u_acentuada+"squeda Exitosa", null);
			} else {
				UtilsX.addErrorMessage("Se produjo un error al realizar la b"+Constants.u_acentuada+"squeda", null);
			}
		}		

	}

	public void processUser() {
		this.mergeUser(actionType);
		this.consultUsers("back");
	}

	/**
	 * Metodo para realizar persistencia de Usuarios
	 * @param action
	 */
	
	public void mergeUser(Integer action) {
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
			usuario.setDeleted(true);
		}

		confirmacion = securitiesService.mergeUser(usuario);

		if (confirmacion) {
			UtilsX.addInfoMessage(messageOK, null);
		} else {
			UtilsX.addErrorMessage(messageERROR, null);
		}

		context.addCallbackParam("confirmation", confirmacion);
	}

	/**
	 * Metodo que se ejecuta en al marcar al usuario como temporal
	 */
	public void isTemporal() {
		if (this.usuario.getIstemporal().equals(1)) {

			Date fecha = new Date();

			Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			cal.add(Calendar.MONTH, 1);
			fecha = cal.getTime();

			this.usuario.setExpirationdate(fecha);
		} else {
			this.usuario.setExpirationdate(null);
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<Tseguser> getUsers() {
		return users;
	}

	public void setUsers(List<Tseguser> users) {
		this.users = users;
	}

	public Tseguser getUsuario() {
		return usuario;
	}

	public void setUsuario(Tseguser usuario) {
		this.usuario = usuario;
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

}
