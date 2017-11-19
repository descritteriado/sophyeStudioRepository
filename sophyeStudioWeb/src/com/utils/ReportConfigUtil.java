package com.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

public class ReportConfigUtil {

	final static Logger logger = Logger.getLogger(ReportConfigUtil.class);

	/**
	 * PRIVATE METHODS
	 */
	private static void setCompileTempDir(ServletContext context, String uri) {
		System.setProperty("jasper.reports.compile.temp", context.getRealPath(uri));
	}

	/**
	 * PUBLIC METHODS
	 */
	public static boolean compileReport(ServletContext context, String compileDir, String filename) throws JRException {
		String jasperFileName = context.getRealPath(compileDir + filename + ".jasper");
		File jasperFile = new File(jasperFileName);

		if (jasperFile.exists()) {
			return true;
		}
		try {

			setCompileTempDir(context, compileDir);

			String xmlFileName = jasperFileName.substring(0, jasperFileName.indexOf(".jasper")) + ".jrxml";
			JasperCompileManager.compileReportToFile(xmlFileName);

			return true;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	public static JasperPrint fillReport(File reportFile, Map<String, Object> parameters, Connection conn)
			throws JRException {
		parameters.put("BaseDir", reportFile.getParentFile());

		JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), parameters, conn);

		return jasperPrint;
	}

	public static String getJasperFilePath(ServletContext context, String compileDir, String jasperFile) {
		return context.getRealPath(compileDir + jasperFile);
	}

	public static void PDF(JasperPrint jasperPrint) throws JRException, IOException {

		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

	}

	public static void DOCX(JasperPrint jasperPrint) throws JRException, IOException {

		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.docx");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JRDocxExporter docxExporter = new JRDocxExporter();
		docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
		docxExporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, servletOutputStream);
		docxExporter.exportReport();
	}

	public static void XLSX(JasperPrint jasperPrint) throws JRException, IOException {

		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.xlsx");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JRXlsxExporter docxExporter = new JRXlsxExporter();
		docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
		docxExporter.exportReport();
	}

	public static void ODT(JasperPrint jasperPrint) throws JRException, IOException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.odt");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JROdtExporter docxExporter = new JROdtExporter();
		docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
		docxExporter.exportReport();
	}

	public static void PPT(JasperPrint jasperPrint) throws JRException, IOException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pptx");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JRPptxExporter docxExporter = new JRPptxExporter();
		docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
		docxExporter.exportReport();
	}

}
