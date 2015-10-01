package com.ihg.me2.archive.reportutil;
import com.ihg.me2.archive.service.model.ArchiveContent;
import com.ihg.me2.archive.service.service.ArchiveContentLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import java.util.Date;
import java.util.List;

import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
public class ReportingUtil {
	public static List<ArchiveContent> getRecords(String status,Date from , Date to)
			throws SystemException {
		if(status==null || from==null|| to==null)
			throw new IllegalArgumentException();
		
		 DynamicQuery d = DynamicQueryFactoryUtil.forClass(ArchiveContent.class);
		 Criterion criterion = null;
		 	if(status.equalsIgnoreCase("all"))
		 		criterion = RestrictionsFactoryUtil.between("CREAT_TS", from, to);
		  
		 	if(!(status.equalsIgnoreCase("all")))
		 	{
		 		
		 			String statusfield=status.equalsIgnoreCase("expired")?"CONTENT_STAT_EXPIRED_DT":(status.equalsIgnoreCase("notified"))?"CONTENT_STAT_NOTIFIED_DT":"CONTENT_STAT_DEL_DT";
		 			criterion = RestrictionsFactoryUtil.eq("CONTENT_STAT_CD",status);
		 			criterion = RestrictionsFactoryUtil.and(criterion,
		 					RestrictionsFactoryUtil.between(statusfield,
		 							from, to));
	  	 	}
		 	d.add(criterion);
		 	return ArchiveContentLocalServiceUtil.dynamicQuery(d);
	}

	public static HSSFWorkbook getExcelWorkBook(List<ArchiveContent>  records,String header) {
	System.out.println("in generating exel method");
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("ArchivalReport");
		HSSFCellStyle boldStyle = workbook.createCellStyle();
		HSSFFont boldFont = workbook.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldStyle.setFont(boldFont);
		
		boldStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		boldStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		boldStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFCellStyle WrapTextStyle = workbook.createCellStyle();
		WrapTextStyle.setWrapText(true);
		
		HSSFCellStyle boldStyle1 = workbook.createCellStyle();
		HSSFFont boldFont2 = workbook.createFont();
		boldFont2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldStyle1.setFont(boldFont2);
		int rownum=1;
		Row row = sheet.createRow(rownum++);
		Cell cell = row.createCell(0);
		cell.setCellValue(header);
		cell.setCellStyle(boldStyle1);
		
		row = sheet.createRow(rownum++);
		row = sheet.createRow(rownum++);
		 cell = row.createCell(0);
		cell.setCellValue("Action");
		cell.setCellStyle(boldStyle);
		cell = row.createCell(1);
		cell.setCellValue("Action Date");
		cell.setCellStyle(boldStyle);
		cell = row.createCell(2);
		cell.setCellValue("Conent Title");
		cell.setCellStyle(boldStyle);
		cell = row.createCell(3);
		cell.setCellValue("Location");
		cell.setCellStyle(boldStyle);
		cell = row.createCell(4);
		cell.setCellValue("URL");
		cell.setCellStyle(boldStyle);
		cell = row.createCell(5);
		cell.setCellValue("Author");
		cell.setCellStyle(boldStyle);
		cell = row.createCell(6);
		cell.setCellValue("Author Email");
		cell.setCellStyle(boldStyle);
		cell = row.createCell(7);
		cell.setCellValue("Delete Date");
		cell.setCellStyle(boldStyle);
			for(ArchiveContent a:records ){
				 row = sheet.createRow(rownum++);
				 cell = row.createCell(0);
				 cell.setCellValue((a.getCONTENT_STAT_CD()== null || a.getCONTENT_STAT_CD().trim().equals(""))? "Not available": a.getCONTENT_STAT_CD());
				 cell = row.createCell(1);
				 cell.setCellValue((a.getLST_UPDT_TS()== null || a.getLST_UPDT_TS().toString().trim().equals(""))? "Not available": a.getLST_UPDT_TS().toString());
				 cell.setCellStyle(WrapTextStyle);
				 cell = row.createCell(2);
				 cell.setCellValue(a.getCONTENT_NM());
				 cell.setCellStyle(WrapTextStyle);
				 cell = row.createCell(3);
				 cell.setCellValue(a.getCONTENT_TYP());
				 cell.setCellStyle(WrapTextStyle);
				 cell = row.createCell(4);
				 HSSFHyperlink url_link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
				 url_link.setAddress((a.getCONTENT_URL_TXT()== null || a.getCONTENT_URL_TXT().trim().equals(""))? "Not available": a.getCONTENT_URL_TXT());
				 cell.setCellValue((a.getCONTENT_URL_TXT()== null || a.getCONTENT_URL_TXT().trim().equals(""))? "Not available": a.getCONTENT_URL_TXT());
				 cell.setHyperlink(url_link);
				 cell.setCellStyle(WrapTextStyle);
				 cell = row.createCell(5);
				 cell.setCellValue(a.getAUTHOR_NM());
				 cell = row.createCell(6);
				 HSSFHyperlink email_link=new HSSFHyperlink(HSSFHyperlink.LINK_EMAIL);
				 email_link.setAddress((a.getAUTHOR_EMAIL_ID()== null || a.getAUTHOR_EMAIL_ID().trim().equals(""))? "Not available": a.getAUTHOR_EMAIL_ID());
				 cell.setCellValue((a.getAUTHOR_EMAIL_ID()== null || a.getAUTHOR_EMAIL_ID().trim().equals(""))? "Not available": a.getAUTHOR_EMAIL_ID());
				 cell.setHyperlink(email_link);
				 cell.setCellStyle(WrapTextStyle);
				 cell = row.createCell(7);
				 cell.setCellValue((a.getCONTENT_STAT_DEL_DT()== null || a.getCONTENT_STAT_DEL_DT().toString().trim().equals(""))? "Not available": a.getCONTENT_STAT_DEL_DT().toString());
			}
	//	sheet.autoSizeColumn(0,true); 
	    sheet.autoSizeColumn(1,true);
	    sheet.autoSizeColumn(2,true); 
	    sheet.autoSizeColumn(3,true);
	    sheet.autoSizeColumn(4,true);
	    sheet.autoSizeColumn(5,true);
	    sheet.autoSizeColumn(6,true);
	    sheet.autoSizeColumn(7,true);
		return workbook;
	}
		public static String getPortletPath(String url){
		
	       String tmp = url.substring(0, url.indexOf("?"));
	  
	       return tmp;
	       }
}
