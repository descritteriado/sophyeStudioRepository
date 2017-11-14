package com.controller.clients;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.ejb.services.ClientsService;
import com.ejb.services.GeneralsService;
import com.ejb.services.SecuritiesService;
import com.jpa.model.Tcliclient;
import com.jpa.model.Tsegcanton;
import com.jpa.model.Tsegcataloguedetail;
import com.jpa.model.Tsegparroquia;
import com.jpa.model.Tsegprovince;
import com.util.general.UtilsX;
import com.utils.Constants;

@ManagedBean
@ViewScoped
public class ClientAdministrationController {

	private String document;
	private String name;
	private String lastName;
	private boolean status;
	private List<Tcliclient> clients;
	private List<Tsegcataloguedetail> documentTypes;
	private List<Tsegprovince> provinces;
	private List<Tsegcanton> cantones;
	private List<Tsegparroquia> parroquias;
	private Tcliclient cliente;
	private boolean edition;
	private Integer actionType;
	private boolean cantonDisabled;
	private boolean parroquiaDisabled;

	final static Logger logger = Logger.getLogger(ClientAdministrationController.class);

	@EJB
	ClientsService clientsService;

	@EJB
	SecuritiesService securitiesService;

	@EJB
	GeneralsService generalsService;

	@PostConstruct
	public void init() {

		status=true;
		
		

	}
	
	public void editCLient()
	{
		try {
		documentTypes = generalsService.getCataloguesDetail(Integer.parseInt(Constants.documentType), "", "");
		provinces = generalsService.getProvinces(null, "");
		cantones = generalsService.getCantones(this.cliente.getProvince().getId(), null, "");
		parroquias = generalsService.getParroquias(this.cliente.getCanton().getId(), null, "");
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void newClient() {
		try {
			documentTypes = generalsService.getCataloguesDetail(Integer.parseInt(Constants.documentType), "", "");
			provinces = generalsService.getProvinces(null, "");
			cliente = new Tcliclient();
			cliente.setDocumentType(new Tsegcataloguedetail());
			cliente.setProvince(new Tsegprovince());
			cliente.setCanton(new Tsegcanton());
			cliente.setParroquia(new Tsegparroquia());
			cliente.setStatus(true);
			cliente.setDeleted(false);
			edition = true;
			cantonDisabled = true;
			parroquiaDisabled = true;
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void consultCantones(AjaxBehaviorEvent event) {
		try {
			if (this.cliente.getProvince().getId() != null) {
				cantones = generalsService.getCantones(this.cliente.getProvince().getId(), null, "");
				parroquias = null;
				cantonDisabled = false;
			} else {
				cantones = null;
				parroquias = null;
				cantonDisabled = true;
				parroquiaDisabled = true;
			}
		} catch (Exception e) {
			cantonDisabled = true;
			parroquiaDisabled = true;
			logger.error(e);
		}
	}

	public void consultParroquias(AjaxBehaviorEvent event) {
		try {
			parroquias = generalsService.getParroquias(this.cliente.getCanton().getId(), null, "");
			parroquiaDisabled = false;
		} catch (Exception e) {
			parroquiaDisabled = true;
			logger.error(e);
		}
	}

	/**
	 * Metodo para obtener los clientes
	 */
	public void consultClients(String origin) {

		boolean result = true;

		try {
			clients = clientsService.getClients(document, name, lastName, status);
			result = true;

		} catch (Exception e) {
			logger.error(e);
			result = false;

		}

		if (origin.equals("front")) {
			if (result) {
				UtilsX.addInfoMessage("B" + Constants.u_acentuada + "squeda Exitosa", null);
			} else {
				UtilsX.addErrorMessage("Se produjo un error al realizar la b" + Constants.u_acentuada + "squeda", null);
			}
		}

	}

	public void processClient() {
		this.mergeClient(actionType);
		this.consultClients("back");
	}

	/**
	 * Metodo para realizar persistencia de Clientes
	 * 
	 * @param action
	 */

	public void mergeClient(Integer action) {
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
			cliente.setDeleted(true);
		}

		confirmacion = clientsService.mergeClient(cliente);

		if (confirmacion) {
			UtilsX.addInfoMessage(messageOK, null);
		} else {
			UtilsX.addErrorMessage(messageERROR, null);
		}

		context.addCallbackParam("confirmation", confirmacion);
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

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public List<Tcliclient> getClients() {
		return clients;
	}

	public void setClients(List<Tcliclient> clients) {
		this.clients = clients;
	}

	public Tcliclient getCliente() {
		return cliente;
	}

	public void setCliente(Tcliclient cliente) {
		this.cliente = cliente;
	}

	public List<Tsegcataloguedetail> getDocumentTypes() {
		return documentTypes;
	}

	public void setDocumentTypes(List<Tsegcataloguedetail> documentTypes) {
		this.documentTypes = documentTypes;
	}

	public List<Tsegprovince> getProvinces() {
		return provinces;
	}

	public void setProvinces(List<Tsegprovince> provinces) {
		this.provinces = provinces;
	}

	public List<Tsegcanton> getCantones() {
		return cantones;
	}

	public void setCantones(List<Tsegcanton> cantones) {
		this.cantones = cantones;
	}

	public List<Tsegparroquia> getParroquias() {
		return parroquias;
	}

	public void setParroquias(List<Tsegparroquia> parroquias) {
		this.parroquias = parroquias;
	}

	public boolean isCantonDisabled() {
		return cantonDisabled;
	}

	public void setCantonDisabled(boolean cantonDisabled) {
		this.cantonDisabled = cantonDisabled;
	}

	public boolean isParroquiaDisabled() {
		return parroquiaDisabled;
	}

	public void setParroquiaDisabled(boolean parroquiaDisabled) {
		this.parroquiaDisabled = parroquiaDisabled;
	}

}
