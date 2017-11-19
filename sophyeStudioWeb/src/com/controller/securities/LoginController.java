package com.controller.securities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ejb.services.GeneralsService;
import com.ejb.services.SecuritiesService;
import com.jpa.model.Tsegparameter;
import com.util.general.UtilsX;
import com.utils.Constants;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController {

	@EJB
	SecuritiesService securitiesService;

	@EJB
	GeneralsService generalsService;

	private boolean logueado;

	private static final Logger logger = Logger.getLogger(LoginController.class);

	private static final String URL_PAGINA_PRINCIPAL = "/pages/principal.jsf?faces-redirect=true";

	private Integer maxInactiveInterval = null;
	private String username;
	private String password;
	private List<Object> transacciones;

	@PostConstruct
	public void init() {
		maxInactiveInterval = this.getmaxInactiveInterval();
	}

	/**
	 * M&#233;todo para obtener el parametro de tiempo de sesi&#243;n activa
	 * 
	 * @return Integer tiempo de sesi&#243;n
	 */
	public Integer getmaxInactiveInterval() {
		Integer val = null;
		List<Tsegparameter> maxInactiveInterval = new ArrayList<>();
		try {
			maxInactiveInterval = generalsService.getParameters("seg.maxInactiveInterval", "");

			if (maxInactiveInterval.size() > 0) {
				val = Integer.parseInt(maxInactiveInterval.get(0).getValue());
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return val;
	}

	/**
	 * M&#233;todo para validar las credenciales en el login
	 * 
	 * @param user
	 * @param password
	 * @return boolean
	 * @throws Exception
	 */
	public boolean validateUser(String user, String password) throws Exception {

		boolean val = securitiesService.validateCredentials(user, UtilsX.cifra(password));

		return val;

	}

	/**
	 * M&#233;todo para realizar el login
	 * 
	 * @return String
	 */
	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try {
			if (username == null || "".equals(username.trim()) || password == null || "".equals(password.trim())) {
				return null;
			} else {

				if (this.validateUser(username, password)) {
					this.logueado = true;
					request.getSession().setAttribute("usuario", username);
				} else {
					this.logueado = false;
					context.addMessage("mensajePrincipal",
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Acceso", "Credenciales Invalidas"));
					username = "";
					return null;
				}
			}
		} catch (Exception e) {
			logger.error("Error al realizar la Autenticaci" + Constants.o_acentuada + "n." + e.getMessage());
			context.addMessage("mensajePrincipal", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error en Autenticaci" + Constants.o_acentuada + "n ", null));
			username = "";
			this.logueado = false;
			return null;
		}
		return URL_PAGINA_PRINCIPAL;
	}

	/**
	 * M&#233;todo que se ejecuta para cerrar la sesi&#243;n activa
	 */
	public void invalidarSesion() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		request.getSession().invalidate();
		this.logueado = false;
		try {
			request.logout();
		} catch (ServletException e) {
			logger.error("Error " + e.getMessage(), e);
		}
	}

	public void logout() {
		invalidarSesion();
	}

	/**
	 * M&#233;todo para borrar objetos de memoria al cerrar la sesi&#243;n
	 */
	public void timeout() {
		ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
		((HttpSession) ectx.getSession(false)).invalidate();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("#{LoginController}");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("#{MenuController}");
		this.logueado = false;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Object> getTransacciones() {
		return transacciones;
	}

	public void setTransacciones(List<Object> transacciones) {
		this.transacciones = transacciones;
	}

	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public void setMaxInactiveInterval(int maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}

	public boolean isLogueado() {
		return logueado;
	}

	public void setLogueado(boolean logueado) {
		this.logueado = logueado;
	}

}
