package com.util.general;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * utitilarios generales de manejo estático para trabajar con JSF
 * 
 * @author jguzman - Magmasoft
 * 
 */
public class JsfUtils {
	/**
	 * 
	 * @return {@link ExternalContext}
	 */
	private static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	/**
	 * Invalida la sesión JSF
	 */
	public static void invalidateSession() {
		getExternalContext().invalidateSession();
	}

	/**
	 * Retorna el ServletContext de la aplicación.
	 * 
	 * @return {@link ServletContext}
	 */
	public static ServletContext getServletContext() {
		return (ServletContext) getExternalContext().getContext();
	}

	/**
	 * Retorna la referencia a un ManagedBean
	 * 
	 * @param servletRequest
	 *            request
	 * @param nombreManagedBean
	 *            nombre del ManagedBean que se desea buscar
	 * @return Object la instancia del ManagedBean
	 */
	public static Object getManagedBean(ServletRequest servletRequest,
			String nombreManagedBean) {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		return httpRequest.getSession().getAttribute(nombreManagedBean);
	}

	/**
	 * Redirecciona la aplicación hacia el destino especficado
	 * 
	 * @param servletRequest
	 *            request
	 * @param servletResponse
	 *            response
	 * @param destino
	 *            Página a la cual se quiere redireccionar
	 * @throws IOException
	 */
	public static void redirect(ServletRequest servletRequest,
			ServletResponse servletResponse, String destino) throws IOException {
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		httpResponse.sendRedirect(httpRequest.getContextPath() + destino);
	}

	public static void redirect(String destino) {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		if ((destino.contains("http://")) || (destino.contains("www."))) {
			try {
				context.redirect(destino);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				context.redirect(context.getRequestContextPath() + destino);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Redireccciona la aplicación a la página de inicio
	 * 
	 * @throws IOException
	 *             Nota: Método llamado desde un contexto JSF
	 */
	public static void rootRedirect() throws IOException {
		JsfUtils.getExternalContext().redirect(
				JsfUtils.getExternalContext().getRequestContextPath());
	}

	/**
	 * Redireccciona la aplicación a la página de inicio
	 * 
	 * @param servletRequest
	 *            request
	 * @param servletResponse
	 *            response
	 * @throws IOException
	 *             Nota: Método llamado desde cualquier contexto web
	 */
	public static void rootRedirect(ServletRequest servletRequest,
			ServletResponse servletResponse) throws IOException {
		redirect(servletRequest, servletResponse, "/");
	}

	

	
	
	public static HttpSession getSession() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		HttpSession httpSession = (HttpSession) request.getSession(true);
		return httpSession;
	}
	
	
	

}
