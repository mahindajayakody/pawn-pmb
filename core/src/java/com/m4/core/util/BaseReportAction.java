package com.m4.core.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRElementsVisitor;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRVisitorSupport;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BaseReportAction extends MasterAction {

	protected static final String SUBREPORT_DIR = "SUBREPORT_DIR";
	public static final String BOOL = "Bool";
	public static final String LONG = "Long";
	public static final String INT = "Int";
	public static final String TIME_STAMP = "TimeStamp";
	public static final String DATE = "Date";
	public static final String REPORT_NAME = "reportName";
	public static final String EMPTY_REPORT = "<span class=\"bold\">Empty Report.</span>";
	public static final String JASPER_ERROR = "<span class=\"bnew\">JasperReports encountered this error :</span>";
	public static final String LINK_STYLE = "<link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\" title=\"Style\">";
	public static final String HTML_END = "</html>";
	public static final String PRE_END = "</pre>";
	public static final String PRE = "<pre>";
	public static final String TITLE_APPLICATION_ERROR_TITLE = "<title>Application - Error</title>";
	public static final String BODY = "<body bgcolor=\"white\">";
	public static final String BODY_END = "</body>";
	public static final String HEAD_END = "</head>";
	public static final String HEAD = "<head>";
	public static final String HTML = "<html>";
	public static final String APPLICATION_PDF = "application/pdf";
	public static final String TEXT_HTML = "text/html";
	public static final String REPORT_TITLE = "ReportTitle";
	public static final String BASE_DIR = "BaseDir";

	/** class level variable uses for synchronization */
	private String reportMutex = "reportMutex";

	private DataSource dataSource;

	/**
	 * Data source tobe used by Jasper reports
	 * 
	 * @return
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * Data source tobe used by Jasper reports provided through spring dbcp
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * This method is used to get compiled .Jasper Report if it is already
	 * available if not create new and return
	 * 
	 * @param jasperFilePath
	 *            name with real location to load the .jasper file
	 * @param jrxmlFilePath
	 *            name with real location to load the .jrxml file
	 * @return JasperReport instance
	 * @throws ReportAccessException
	 *             Is thrown when fails of report generation
	 */
	private JasperReport getComplitedJasperReport(String reportFilePath, boolean subReport) throws Exception {

		log.debug("Start method getComplitedJasperReport() in ReportServiceImpl class");

		String jasperFilePath;
		String jrxmlFilePath;

		if (subReport) {
			jrxmlFilePath = reportFilePath;
			jasperFilePath = reportFilePath.substring(0,reportFilePath.lastIndexOf(".")) + ".jasper";
		} else {
			jasperFilePath = reportFilePath + ".jasper";
			jrxmlFilePath = reportFilePath + ".jrxml";
		}

		// create instance of jasperReport
		JasperReport jasperReport;

		// if the .jasper file already has created then load it
		try {
			jasperReport = (JasperReport) JRLoader.loadObject(jasperFilePath);
		} catch (Exception e) {
			// if an exception catches, it means there is no already created
			// .jasper file
			jasperReport = null;
		}

		// check whether the jasperReport is null or not
		if (jasperReport == null) {
			// if it is null, it means we have compile jrxml file to create
			// .jasper
			synchronized (reportMutex) {
				// instance for the jasperFilePath file
				File file = new File(jasperFilePath);

				// checking whether it exists,
				if (!file.exists()) {
					try {
						JasperDesign jasperDesign = JRXmlLoader.load(jrxmlFilePath);
						JasperCompileManager.compileReportToFile(jasperDesign, jasperFilePath);

					} catch (JRException e) {
						throw new Exception(e);
					}
				}
			}
			// load the newly created .jasper
			try {
				jasperReport = (JasperReport) JRLoader.loadObject(jasperFilePath);

			} catch (JRException e) {
				throw new Exception(e);
			}
		}
		log.debug("End of method getComplitedJasperReport() in ReportServiceImpl class");

		return jasperReport;
	}

	/**
	 * Populate data for the report
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String inputReportPath = getServlet().getServletContext().getRealPath(request.getParameter(REPORT_NAME));
		final File reportFile = new File(inputReportPath);

		//if (!reportFile.exists()) {
		//	throw new JRRuntimeException("File " + reportFile + " not found.");
		//}

		Map<String, Object> parameters;
		if (request.getParameter("headless") == null) {
			parameters = new HashMap<String, Object>();
		} else {
			parameters = decodeJasperParameters(request);
		}
		// parameters.put(REPORT_TITLE, "Address Report");
		// parameters.put(BASE_DIR, reportFile.getParentFile());
		parameters.put(SUBREPORT_DIR, reportFile.getParent() + System.getProperty("file.separator"));

		JasperPrint jasperPrint = null;
		Connection dbConnection = null;

		try {

			// The JasperReport instance to pass fill the the report with data
			JasperReport jasperReport = getComplitedJasperReport(reportFile.getPath(), false);
			
			// Compile Sub reports
			JRElementsVisitor.visitReport(jasperReport, new JRVisitorSupport(){

				  @Override
				  public void visitSubreport(JRSubreport subreport){
				    String expression = subreport.getExpression().getText().replace(".jasper", ".jrxml");
				    StringTokenizer st = new StringTokenizer(expression, "\"/");
				    String subreportName = null;
				    while(st.hasMoreTokens()){
				    	subreportName = st.nextToken();
				    	try {
							getComplitedJasperReport(reportFile.getParent() + System.getProperty("file.separator") + subreportName, true);
						} catch (Exception e) {
							e.printStackTrace();
						}
				    }
				  }
				});

			// load specified report object
			//JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());

			dbConnection = dataSource.getConnection();

			// fill the report with data from dataSource connection
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dbConnection);
		} catch (JRException e) {
			genarateErrorPage(response, e);
			return null;
		}

		if (jasperPrint != null) {

			/*
			 * PrintWriter outStream = response.getWriter(); JRHtmlExporter exporter = new JRHtmlExporter();
			 * exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			 * exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, outStream);
			 * //exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI ,"../servlets/image?image=");
			 * request.getSession().setAttribute(ImageServlet .DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
			 * exporter.exportReport();
			 */

			/*
			 * JRHtmlExporter exporter = new JRHtmlExporter(); exporter.setParameter(JRExporterParameter.JASPER_PRINT,
			 * jasperPrint); exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME
			 * ,E:/projetcs/Pawn/reporting/report4.pdf"); exporter.exportReport();
			 */

			// Print PDF version
			response.setContentType(APPLICATION_PDF);
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			exporter.exportReport();

		} else {
			genarateErrorPage(response, null);
		}

		// give back the active connection to the pool
		if (dbConnection != null) {
			try {
				dbConnection.close();
			} catch (Exception e) {
				log.error("Connection closing error " + e.getLocalizedMessage());
			}
		}

		return null;
	}

	/**
	 * Populate error and empty report page
	 * 
	 * @param response
	 * @param e
	 * @throws IOException
	 */
	protected void genarateErrorPage(HttpServletResponse response, JRException e) throws IOException {

		response.setContentType(TEXT_HTML);
		PrintWriter out = response.getWriter();
		out.println(HTML);
		out.println(HEAD);
		out.println(TITLE_APPLICATION_ERROR_TITLE);
		out.println(LINK_STYLE);
		out.println(HEAD_END);
		out.println(BODY);

		if (e == null) {
			out.println(EMPTY_REPORT);
		} else {
			out.println(JASPER_ERROR);
			out.println(PRE);
			e.printStackTrace(out);
			out.println(PRE_END);
		}

		out.println(BODY_END);
		out.println(HTML_END);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.m4.core.util.MasterAction#getAuthorizeData(org.apache.struts.action
	 * .ActionMapping, org.apache.struts.action.ActionForm,
	 * javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward getAuthorizeData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}

	/**
	 * Build a java.util.Map from specially coded request parameters. Only
	 * request parameters starting with "jpXxx_" will be loaded into the map.
	 * Xxx represents the target type in which to parse the string. Valid
	 * choices are
	 * <ul>
	 * <li>Date - java.sql.Date formatted as M/d/yyyy</li>
	 * <li>Timestamp - java.sql.Timestamp formatted as M/d/yyyy H:mm:ss</li>
	 * <li>Int - java.lang.Integer</li>
	 * <li>Long - java.lang.Long</li>
	 * <li>Bool - java.lang.Boolean</li>
	 * <li>String - java.lang.String</li>
	 * </ul>
	 * Everything else will be placed in the map as a string.
	 * 
	 * @param request
	 * @return Map of parameters
	 */
	public Map<String, Object> decodeJasperParameters(HttpServletRequest request) {

		Map<String, Object> reportParameters = new HashMap<String, Object>();
		Enumeration<String> names = request.getParameterNames();

		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = request.getParameter(name);
			if (name.matches("^(jp)([A-Za-z0-9-]*)$")) {
				try {
					if (name.indexOf(DATE) > -1) {
						reportParameters.put(name, new java.sql.Date(DATEFORMAT.parse(value).getTime()));
					} else if (name.indexOf(TIME_STAMP) > -1) {
						reportParameters.put(name, new java.sql.Timestamp(TIMESTAMPFORMAT.parse(value).getTime()));
					} else if (name.indexOf(INT) > -1) {
						reportParameters.put(name, new Integer(value));
					} else if (name.indexOf(LONG) > -1) {
						reportParameters.put(name, new Long(value));
					} else if (name.indexOf(BOOL) > -1) {
						reportParameters.put(name, new Boolean(value));
					} else {
						reportParameters.put(name, request.getParameter(name));
					}
				} catch (ParseException e) {
					log.error(e);
				}
				if (log.isDebugEnabled()) {
					log.debug("Mapped parameter: " + name + "=" + reportParameters.get(name));
				}
			}
			/*
			 * else { if (log.isDebugEnabled()) {
			 * log.debug("Skipping parameter: " + name); } }
			 */
		}
		return reportParameters;
	}
}
