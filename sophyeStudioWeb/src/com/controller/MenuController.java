package com.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.ejb.services.SecuritiesService;
import com.utils.Constants;

@ManagedBean
@SessionScoped
public class MenuController {

	@EJB
	SecuritiesService securitiesService;

	private MenuModel model;
	
	 @ManagedProperty("#{loginController}")
	    private LoginController loginController;

	@PostConstruct
	public void init() {
	
		this.construcMenu();
	}

	/**
	 * Metodo para obtener el menu dado el codigo
	 * 
	 * @param Codigo
	 *            de Modulo
	 * @return List <Object>
	 */
	public List<Object> getMenu(String module) {

		return securitiesService.getMenu(loginController.getUsername(), module);

	}

	/**
	 * Metodo para consultar y armar el menu en base a la parametrización de la base de datos 
	 */
	private void construcMenu() {
		
		model = new DefaultMenuModel();

		List<Object> trxContabilidad = this.getMenu(Constants.MODULO_CONTABILIDAD);
		List<Object> trxBancos = this.getMenu(Constants.MODULO_BANCOS);
		List<Object> trxFacturacion = this.getMenu(Constants.MODULO_FACTURACION);
		List<Object> trxSeguridades = this.getMenu(Constants.MODULO_SEGURIDADES);

		if (trxContabilidad.size() > 0) {
			model = this.addItems(model, trxContabilidad);
		}

		if (trxBancos.size() > 0) {
			model = this.addItems(model, trxBancos);
		}

		if (trxFacturacion.size() > 0) {
			model = this.addItems(model, trxFacturacion);
		}
		if (trxSeguridades.size() > 0) {
			model = this.addItems(model, trxSeguridades);
		}
	}

	/**
	 * Metodo para aderir las transacciones a los submenus
	 * @param MenuModel
	 * @param Arreglo con las transacciones
	 * @return MenuModel modificado
	 */
	private MenuModel addItems(MenuModel model, List<Object> items) {
		MenuModel localModel = model;
		DefaultSubMenu submenu = null;

		for (Object auto : items) {

			Object[] val = (Object[]) auto;

			submenu = new DefaultSubMenu();
			submenu.setIcon(null);
			submenu.setLabel(val[0].toString());
			model.addElement(submenu);
			break;
		}
		for (Object auto : items) {

			Object[] val = (Object[]) auto;

			DefaultMenuItem item = new DefaultMenuItem();
			item.setValue(val[1]);
			item.setUrl(val[2].toString());
			submenu.addElement(item);

		}

		return localModel;
	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

}
