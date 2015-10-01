package com.ihg.me2.archive.scheduler;

import com.ihg.me2.archive.dao.ArchivalMonitorDAO;
import com.ihg.me2.archive.service.model.ArchiveContent;
import com.ihg.me2.archive.service.service.ArchiveContentLocalServiceUtil;
import com.ihg.me2.archive.util.ArchivalMonitorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.util.List;

/**
 * Portlet implementation class ArchivalMonitorScheduler
 */
public class ArchivalMonitorScheduler extends MVCPortlet implements MessageListener {

private static final Log log = LogFactoryUtil.getLog(ArchivalMonitorScheduler.class);
	
	
	public void receive(Message message) throws MessageListenerException {
		// TODO Auto-generated method stub
		try {
			System.out.println("ArchivalScheduler.receive()");
			log.info("This is ArchivalScheduler.receive() method gets executed..");
			//				PortletRequest pr = new PortletRequest();
//				new LuceneSearchUtil().getFullQuery(searchContext);
//			LuceneSearchUtil.getDLQuery(null, 0, 0, false);
			Company company = CompanyLocalServiceUtil.getCompanies().get(0);
			System.out.println("company id:-->"+company.getCompanyId());
			long companyId = company.getCompanyId();
			 System.out.println("company id:-->"+company.toString());
			 String portalURL = PortalUtil.getPortalURL(company.getVirtualHost(),PortalUtil.getPortalPort(), false);
			 System.out.println("portalURL:-->"+portalURL);
			 String pathContext = PortalUtil.getPathContext();
			 System.out.println("pathContext:-->"+pathContext);
			 
			new ArchivalMonitorDAO(companyId, portalURL).getMerlin();
//			ArchiveContentSearch.getDocuments();
			System.out.println("Listing of archive_content table");
			List<ArchiveContent> ac = ArchiveContentLocalServiceUtil.getArchiveContents(0, 10);
			for (ArchiveContent archiveContent : ac) {
				System.out.println("Archive content list 1)"+archiveContent.getAUTHOR_NM());
				System.out.println("Archive content list 2)"+archiveContent.getCONTENT_NM());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("There is exception while scheduling is .. :->"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	

}
