package com.controller.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;

import com.ejb.services.ClientsService;
import com.ejb.services.GeneralsService;
import com.ejb.services.SecuritiesService;
import com.jpa.model.Tsegcanton;
import com.jpa.model.Tsegparroquia;
import com.jpa.model.Tsegprovince;
import com.utils.AbstractReport;

@ManagedBean
@ViewScoped
public class ClientsByCityReportController extends AbstractReport {

	private List<Tsegprovince> provinces;
	private List<Tsegcanton> cantones;
	private List<Tsegparroquia> parroquias;
	private boolean cantonDisabled;
	private boolean parroquiaDisabled;
	private Integer provinceId;
	private Integer cantonId;
	private Integer parroquiaId;
	private String reportFormat;
	private final String REPORT_NAME = "clientByCity";

	final static Logger logger = Logger.getLogger(ClientsByCityReportController.class);

	@EJB
	ClientsService clientsService;

	@EJB
	SecuritiesService securitiesService;

	@EJB
	GeneralsService generalsService;

	@PostConstruct
	public void init() {
		try {
			provinces = generalsService.getProvinces(null, "");
		} catch (Exception e) {
			logger.error(e);
		}

	}

	@Override
	protected Map<String, Object> getReportParameters() {
		Map<String, Object> reportParameters = new HashMap<String, Object>();

		reportParameters.put("provinceId", this.provinceId);
		reportParameters.put("cantonId", this.cantonId);
		reportParameters.put("parroquiaId", this.parroquiaId);

		return reportParameters;
	}

	public void generateReport() {
		try {
			super.prepareReport();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void consultCantones(AjaxBehaviorEvent event) {
		try {
			if (this.provinceId != null) {
				cantones = generalsService.getCantones(this.provinceId, null, "");
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
			parroquias = generalsService.getParroquias(this.cantonId, null, "");
			parroquiaDisabled = false;
		} catch (Exception e) {
			parroquiaDisabled = true;
			logger.error(e);
		}
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

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCantonId() {
		return cantonId;
	}

	public void setCantonId(Integer cantonId) {
		this.cantonId = cantonId;
	}

	public Integer getParroquiaId() {
		return parroquiaId;
	}

	public void setParroquiaId(Integer parroquiaId) {
		this.parroquiaId = parroquiaId;
	}

	@Override
	protected String getCompileFileName() {
		return REPORT_NAME;
	}

	public String getReportFormat() {
		return reportFormat;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

}
