package com.ihg.me2.archive.portlet;
import com.ihg.me2.archive.reportutil.ReportingUtil;
import com.ihg.me2.archive.service.model.ArchiveContent;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/**
 * Portlet implementation class ReportingPortlet
 */
public class ReportingPortlet extends MVCPortlet {
	
@Override
public void serveResource(ResourceRequest resourceRequest,
		ResourceResponse resourceResponse) throws IOException,
			PortletException {
		String resourceID = resourceRequest.getResourceID();
	 	String selectBy= resourceRequest.getParameter("selectby");
	 	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	Date startDate=ParamUtil.getDate(resourceRequest,"startDateHidden", df);
	Date enDate=ParamUtil.getDate(resourceRequest,"endDateHidden", df);
		List<ArchiveContent> a;
		try { 
			
				a = ReportingUtil.getRecords(selectBy,startDate, enDate);
			 if ((a!=null && a.size()!=0)  && "exportExcelURL".equalsIgnoreCase(resourceID) ) {
				resourceResponse.setContentType("application/vnd.ms-excel");
				resourceResponse.addProperty("Content-Disposition", "attachment; filename=ArchivalReport.xls");
				String header="Archival Report "+(selectBy.equalsIgnoreCase("all")?" ":"for Status "+selectBy)+"  From Date : "+startDate.toString()+" To Date : "+enDate.toString();
				HSSFWorkbook workbook = ReportingUtil.getExcelWorkBook(a,header);
				workbook.write(resourceResponse.getPortletOutputStream());
			}else{
								resourceResponse.setContentType("text/html");
								HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(resourceRequest);
				                HttpServletResponse httpResponse = PortalUtil.getHttpServletResponse(resourceResponse);       
				                String urlToRedirect = PortalUtil.getPortalURL(httpRequest) +ReportingUtil.getPortletPath(PortalUtil.getCurrentURL(httpRequest));
				                String htmlCodeRedirect = "<html><head><meta http-equiv=\"Refresh\" content=\"1;url=" + urlToRedirect + "\" /></head><body> Please Wait your Request is being processed......</body></html>";
				                OutputStream out = resourceResponse.getPortletOutputStream();
				                out.write(htmlCodeRedirect.getBytes());
				                out.flush();
				                out.close();
				                PortletSession session1 = resourceRequest.getPortletSession();
				                session1.setAttribute("flag","not", PortletSession.APPLICATION_SCOPE);
				          			}
		} catch (SystemException e) {
		
		e.printStackTrace();
		}
	
	}
	
	




}
