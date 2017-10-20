package com.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.ejb.services.SecuritiesService;
import com.jpa.model.Tsegmodule;
import com.jpa.model.Tsegtransaction;
import com.util.general.UtilsX;
import com.utils.Constants;

@ManagedBean
@ViewScoped
public class TransactionAdministrationController {

	private String description;
	private List<Tsegtransaction> transactions;
	private Tsegtransaction transaccion;
	private List<Tsegmodule> modules;
	private boolean edition;
	private Integer actionType;

	final static Logger logger = Logger.getLogger(TransactionAdministrationController.class);

	@EJB
	SecuritiesService securitiesService;


	@PostConstruct
	public void init() {
		try {
			modules = securitiesService.getModules("");
		} catch (Exception e) {
			logger.error(e);

		}
		transaccion = new Tsegtransaction();
		transaccion.setTsegmodule(new Tsegmodule());
		edition = true;
	}

	public void newTransaction() {
		transaccion = new Tsegtransaction();
		transaccion.setTsegmodule(new Tsegmodule());
		edition = true;
	}

	/**
	 * Metodo para obtener los transaccions
	 */
	public void consultTransactions(String origin) {

		boolean result = true;

		try {
			transactions = securitiesService.getTransactions(description);
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
	public void processTransaction() {
		this.mergeTransaction(actionType);
		this.consultTransactions("back");
	}

	/**
	 * Metodo para borrar un perfil
	 */
	public void deleteTransaction() {
		this.removeTransaction();
		this.consultTransactions("back");
	}

	/**
	 * Metodo para realizar persistencia de transaccions
	 * 
	 * @param action
	 */

	public void mergeTransaction(Integer action) {
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

		confirmacion = securitiesService.mergeTransaction(this.transaccion);

		if (confirmacion) {
			UtilsX.addInfoMessage(messageOK, null);
		} else {
			UtilsX.addErrorMessage(messageERROR, null);
		}

		context.addCallbackParam("confirmation", confirmacion);
	}

	public void removeTransaction() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean confirmacion = false;
		String messageOK = "";
		String messageERROR = "";

		messageOK = "Registro eliminado exitosamente";
		messageERROR = "Se produjo un error al eliminar el registro";

		confirmacion = securitiesService.deleteTransaction(this.transaccion);

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

	public List<Tsegtransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Tsegtransaction> transactions) {
		this.transactions = transactions;
	}

	public Tsegtransaction getTransaction() {
		return transaccion;
	}

	public void setTransaction(Tsegtransaction transaccion) {
		this.transaccion = transaccion;
	}

	public List<Tsegmodule> getModules() {
		return modules;
	}

	public void setModules(List<Tsegmodule> modules) {
		this.modules = modules;
	}

}
