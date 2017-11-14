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
import com.jpa.model.Tsegprofiletransaction;
import com.jpa.model.Tsegtransaction;
import com.util.general.UtilsX;
import com.utils.Constants;

@ManagedBean
@ViewScoped
public class ProfileTransactionAdministrationController {

	private Integer profileId;
	private Integer transactionId;
	private List<Tsegprofiletransaction> profilesTransactions;
	private Tsegprofiletransaction perfilTransaction;
	private boolean edition;
	private Integer actionType;
	private List<Tsegtransaction> transactions;
	private List<Tsegprofile> profiles;

	final static Logger logger = Logger.getLogger(ProfileTransactionAdministrationController.class);

	@EJB
	SecuritiesService securitiesService;

	@PostConstruct
	public void init() {

		try {
			transactions = securitiesService.getTransactions("");
			profiles = securitiesService.getProfiles("");
		} catch (Exception e) {
			logger.error(e);
		}

		perfilTransaction = new Tsegprofiletransaction();
		perfilTransaction.setTsegprofile(new Tsegprofile());
		perfilTransaction.setTsegtransaction(new Tsegtransaction());
		edition = true;
	}

	public void newProfileTransaction() {
		perfilTransaction = new Tsegprofiletransaction();
		perfilTransaction.setTsegprofile(new Tsegprofile());
		perfilTransaction.setTsegtransaction(new Tsegtransaction());
		edition = true;
	}

	/**
	 * Metodo para obtener la Data
	 */
	public void consultProfileTransaction(String origin) {

		boolean result = true;

		try {
			profilesTransactions = securitiesService.getProfilesTransactions(profileId, transactionId);
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
	public void processProfileTransaction() {
		this.mergeProfileTransaction(actionType);
		this.consultProfileTransaction("back");
	}

	/**
	 * Metodo para borrar un registro
	 */
	public void deleteProfileTransaction() {
		this.removeProfileTransaction();
		this.consultProfileTransaction("back");
	}

	/**
	 * Metodo para realizar persistencia
	 * 
	 * @param action
	 */

	public void mergeProfileTransaction(Integer action) {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean confirmacion = false;
		boolean exist = false;
		String messageOK = "";
		String messageERROR = "";

		if (action.equals(Integer.parseInt(Constants.IN))) {
			messageOK = "Registro guardado exitosamente";
			messageERROR = "Se produjo un error al guardar el registro";

			try {
				exist = securitiesService.getProfilesTransactions(this.perfilTransaction.getTsegprofile().getId(),
						this.perfilTransaction.getTsegtransaction().getId()).size() == 0 ? false : true;
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
			confirmacion = securitiesService.mergeProfilesTransactions(this.perfilTransaction);
		} else {
			messageERROR = "La relaci" + Constants.o_acentuada + "n Perfil-Transacci" + Constants.o_acentuada
					+ "n ya existe";
		}
		if (confirmacion) {
			UtilsX.addInfoMessage(messageOK, null);
		} else {
			UtilsX.addErrorMessage(messageERROR, null);
		}

		context.addCallbackParam("confirmation", confirmacion);
	}

	public void removeProfileTransaction() {
		RequestContext context = RequestContext.getCurrentInstance();
		boolean confirmacion = false;
		String messageOK = "";
		String messageERROR = "";

		messageOK = "Registro eliminado exitosamente";
		messageERROR = "Se produjo un error al eliminar el registro";

		try {
			securitiesService.deleteProfilesTransactions(this.perfilTransaction);
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

	public List<Tsegprofile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Tsegprofile> profiles) {
		this.profiles = profiles;
	}

	public Integer getProfileId() {
		return profileId;
	}

	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public List<Tsegprofiletransaction> getProfilesTransactions() {
		return profilesTransactions;
	}

	public void setProfilesTransactions(List<Tsegprofiletransaction> profilesTransactions) {
		this.profilesTransactions = profilesTransactions;
	}

	public Tsegprofiletransaction getPerfilTransaction() {
		return perfilTransaction;
	}

	public void setPerfilTransaction(Tsegprofiletransaction perfilTransaction) {
		this.perfilTransaction = perfilTransaction;
	}

	public List<Tsegtransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Tsegtransaction> transactions) {
		this.transactions = transactions;
	}

}
