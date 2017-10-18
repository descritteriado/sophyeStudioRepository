package com.controller;

import java.util.List;

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

import com.ejb.services.SecuritiesService;
import com.util.general.UtilsX;
import com.utils.Constants;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController {

	@EJB
	SecuritiesService securitiesService;

	private static final Logger logger = Logger.getLogger(LoginController.class);

	private static final String URL_PAGINA_PRINCIPAL = "/pages/principal.jsf?faces-redirect=true";

	private int maxInactiveInterval = Integer.parseInt(Constants.MAXINACTIVEINTERVAL);
	private String username;
	private String password;
	private List<Object> transacciones;
	

	
	public boolean validateUser(String user, String password) throws Exception {

		boolean val = securitiesService.validateCredentials(user, UtilsX.cifra(password));
		
		return val;

	}
	
	 
		public String login() {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			try {
				if (username == null || "".equals(username.trim()) || password == null || "".equals(password.trim())) {
					return null;
				} else {
					
					if (this.validateUser(username, password)) {
						request.getSession().setAttribute("usuario", username);
					} else {
						context.addMessage("mensajePrincipal", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Acceso", "Credenciales Invalidas"));
						username = "";
						return null;
					}
				}
			} catch (Exception e) {
				logger.error("Error al realizar la Autenticaci"+Constants.o_acentuada+"n." + e.getMessage());
				context.addMessage("mensajePrincipal", new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error en Autenticaci"+Constants.o_acentuada+"n ", null));
				username = "";
				return null;
			}
			return URL_PAGINA_PRINCIPAL;
		}
		
		public void invalidarSesion() {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.getSession().invalidate();
			try {
				request.logout();
			} catch (ServletException e) {
				logger.error("Error " + e.getMessage(), e);
			}
		}

		public void timeout() {
			ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
			((HttpSession) ectx.getSession(false)).invalidate();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("#{LoginController}");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("#{MenuController}");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("#{UserAdministrationController}");
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



}
