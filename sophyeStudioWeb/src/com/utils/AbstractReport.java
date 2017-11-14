package com.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

public abstract class AbstractReport {

	final static Logger logger = Logger.getLogger(AbstractReport.class);

	public enum ExportOption {

		PDF_Preview, PDF, DOCX, EXCEL, ODT, PPT
	}

	private ExportOption exportOption;
	private final String COMPILE_DIR = "/resources/reports/";
	private String message;

	public AbstractReport() {
		super();
	}

	protected void prepareReport() throws JRException, IOException {

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		ServletContext context = (ServletContext) externalContext.getContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		ReportConfigUtil.compileReport(context, getCompileDir(), getCompileFileName());

		File reportFile = new File(
				ReportConfigUtil.getJasperFilePath(context, getCompileDir(), getCompileFileName() + ".jasper"));

		Connection conn = null;
		try {
			conn = Database.getConnection();
		} catch (Exception e) {
			logger.error(e);
		}

		JasperPrint jasperPrint = ReportConfigUtil.fillReport(reportFile, getReportParameters(), conn);

		if (getExportOption().equals(ExportOption.PDF)) {
			ReportConfigUtil.PDF(jasperPrint);
		} else if (getExportOption().equals(ExportOption.EXCEL)) {
			ReportConfigUtil.XLSX(jasperPrint);
		} else if (getExportOption().equals(ExportOption.DOCX)) {
			ReportConfigUtil.DOCX(jasperPrint);
		} else if (getExportOption().equals(ExportOption.ODT)) {
			ReportConfigUtil.ODT(jasperPrint);
		} else if (getExportOption().equals(ExportOption.PPT)) {
			ReportConfigUtil.PPT(jasperPrint);
		} else if (getExportOption().equals(ExportOption.PDF_Preview)) {
			request.getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
			response.sendRedirect(request.getContextPath() + "/servlets/report/PDF");
		}

		FacesContext.getCurrentInstance().responseComplete();

		Database.close(conn);
	}

	public ExportOption getExportOption() {
		return exportOption;
	}

	public void setExportOption(ExportOption exportOption) {
		this.exportOption = exportOption;
	}

	protected Map<String, Object> getReportParameters() {
		return new HashMap<String, Object>();
	}

	protected String getCompileDir() {
		return COMPILE_DIR;
	}

	protected abstract String getCompileFileName();

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}