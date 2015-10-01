package com.ihg.me2.archive.deletescheduler;
 
import com.ihg.me2.archive.deleteutil.DeleteSchedulerUtil;
import com.ihg.me2.archive.deleteutil.IndexerLogger;
import com.ihg.me2.archive.deleteutil.SchedulerConstant;
import com.ihg.me2.archive.service.model.ArchiveContent;
import com.ihg.me2.archive.service.service.ArchiveContentLocalServiceUtil;
 

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.liferay.util.portlet.PortletProps;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.apache.log4j.Logger;

public class DeleteJobScheduler extends MVCPortlet implements MessageListener {
	private static Logger logger = IndexerLogger
			.getLogger(DeleteJobScheduler.class);
	public static List<ArchiveContent> webcontents = null;
	public static List<ArchiveContent> media = null;
	public static List<ArchiveContent> communities = null;
	public static List<ArchiveContent> bookmark = null;
	public DeleteJobScheduler() {
		webcontents = new ArrayList<ArchiveContent>();
		media = new ArrayList<ArchiveContent>();
		communities = new ArrayList<ArchiveContent>();
		bookmark= new ArrayList<ArchiveContent>();
	}

	
	public void receive(Message arg0) throws MessageListenerException {
				
		int frequency = Integer.parseInt(PortletProps.get("deleted-month"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		try {
	
			Date today = dateFormat.parse(dateFormat.format(new Date()));
			Calendar c = Calendar.getInstance();
			c.setTime(today);
			c.add(Calendar.MONTH, -frequency);

			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil
					.forClass(ArchiveContent.class);
			Criterion criterion = null;
			criterion = RestrictionsFactoryUtil.eq("CONTENT_STAT_CD",
					SchedulerConstant.STATUS_EXPIRED);

			criterion = RestrictionsFactoryUtil.and(criterion,
					RestrictionsFactoryUtil.between("CONTENT_STAT_DEL_DT",
							c.getTime(), today));

			dynamicQuery.add(criterion);

			List<ArchiveContent> deleteable = ArchiveContentLocalServiceUtil
					.dynamicQuery(dynamicQuery);

			logger.info(deleteable.size()
					+ " records found that needs to be archived");

			if (Validator.isNotNull(deleteable) && deleteable.size() > 0) {

				seperation(deleteable);
				logger.info("separation is performed");
				if (webcontents.size() > 0) {
					processWebContent();
					logger.info(webcontents.size()+" :: Webcontents are processed");
				}
				if (media.size() > 0) {
					processMedia();
					logger.info(media.size()+" :: documents,images,vedios are processed");
				}
				if (bookmark.size() > 0) {
					processBookmarks();
					logger.info(bookmark.size()+" :: Bookmarks are processed");
				}
				
				if (communities.size() > 0) {
					processCommunity();
					logger.info(communities.size()+" ::communities are processed");
				}

			} else {

				logger.info("there are no records found for delete archival between date "
						+ c.getTime().toString() + " and " + today.toString());

			}

		} catch (ParseException e) {
			logger.error("Error occured while parsing the date", e);
		} catch (SystemException e) {
			logger.error("Error ocuured while Executing the Query", e);
		}

	}

	public void seperation(List<ArchiveContent> deletable) {
		if (deletable == null || deletable.size() == 0) {
			throw new IllegalArgumentException();
		}

		for (ArchiveContent a : deletable) {
			if (DeleteSchedulerUtil.isWebContent(a.getCONTENT_TYP())) {
				webcontents.add(a);
				continue;
			}

			if (DeleteSchedulerUtil.isMedia(a.getCONTENT_TYP())) {
				media.add(a);
				continue;
			}
			if(DeleteSchedulerUtil.isBookmark(a.getCONTENT_TYP())){
				bookmark.add(a);
				continue;
			}
			if (DeleteSchedulerUtil.isCommunity(a.getCONTENT_TYP())) {
				communities.add(a);
				continue;
			}
			
		}

	}

	public void processWebContent() throws NumberFormatException {
		if (webcontents == null || webcontents.size() == 0) {
			throw new IllegalArgumentException();
		}
		for (ArchiveContent a : webcontents) {
											
			if (DeleteSchedulerUtil.isModifiedInExpiredStatus(
					Long.parseLong(a.getCONTENT_ID()), a.getCONTENT_STAT_CD(),
					a.getCONTENT_STAT_EXPIRED_DT(), a.getCONTENT_STAT_DEL_DT(),
					SchedulerConstant.WEBCONTENT_IDENTIFIER)) {
				// action is taken by the user
				
				DeleteSchedulerUtil.deleteFromArchiveContent(a.getCONTENT_ID()); // delete
																					// from
																				// archive
				// update the in list status, so that is useful while community
				logger.info("User has taken Action-> Web content entry is deleted from ArchiveContent table :: "+a.getCONTENT_NM()+"expired date-->"+a.getCONTENT_STAT_EXPIRED_DT()+" modified date--> "+a.getCONTENT_STAT_MOD_DT()+"delete date-->"+a.getCONTENT_STAT_DEL_DT());	
				a.setCONTENT_STAT_CD(SchedulerConstant.STATUS_UPDATE);

			} else if (DeleteSchedulerUtil.flag == 2) {
				// content is not modified in expired period
				a.setCONTENT_STAT_MOD_DT(DeleteSchedulerUtil.getModifiedDate(
						SchedulerConstant.WEBCONTENT_IDENTIFIER,
						Long.parseLong(a.getCONTENT_ID())));
				if (DeleteSchedulerUtil.deleteWebContentFromPortal(Long
						.parseLong(a.getCONTENT_ID()))) {
					
					a.setCONTENT_STAT_CD(SchedulerConstant.STATUS_DELETE);
					DeleteSchedulerUtil.updateArchive(a);
					logger.info("content is not modified in expired period--> Web content deleted from portal and updated in archive table:: "+a.getCONTENT_NM()+"expired date"+a.getCONTENT_STAT_EXPIRED_DT()+" modified date "+a.getCONTENT_STAT_MOD_DT()+"delete date"+a.getCONTENT_STAT_DEL_DT());
				}

			} else if (DeleteSchedulerUtil.flag == 1) {
				// content is deleted by user or admin
				a.setCONTENT_STAT_MOD_DT(DeleteSchedulerUtil.getModifiedDate(
						SchedulerConstant.WEBCONTENT_IDENTIFIER,
						Long.parseLong(a.getCONTENT_ID())));
				a.setCONTENT_STAT_CD(SchedulerConstant.STATUS_DELETED_FROM_PORTAL);
				DeleteSchedulerUtil.updateArchive(a);
							logger.info("content is deleted by user or admin--> web content entry updated in ArchiveContent table :: "+a.getCONTENT_NM());
			}

			DeleteSchedulerUtil.flag = 0;
		}
	}

	public void processMedia() throws NumberFormatException {
		if (media == null || media.size() == 0) {
			throw new IllegalArgumentException();
		}

		for (ArchiveContent a : media) {
			// set modified in the Archive content
						
			if (DeleteSchedulerUtil.isModifiedInExpiredStatus(
					Long.parseLong(a.getCONTENT_ID()), a.getCONTENT_STAT_CD(),
					a.getCONTENT_STAT_EXPIRED_DT(), a.getCONTENT_STAT_DEL_DT(),
					SchedulerConstant.MEDIA_IDENTIFIER)) {
				// action is taken by the author
				DeleteSchedulerUtil.removeExpiredTag(a,
						SchedulerConstant.MEDIA_IDENTIFIER);
				DeleteSchedulerUtil.deleteFromArchiveContent(a.getCONTENT_ID());
				a.setCONTENT_STAT_CD(SchedulerConstant.STATUS_UPDATE);

			} else if (DeleteSchedulerUtil.flag == 2) {
				// content is not modified in expired period
				a.setCONTENT_STAT_MOD_DT(DeleteSchedulerUtil.getModifiedDate(
						SchedulerConstant.MEDIA_IDENTIFIER,
						Long.parseLong(a.getCONTENT_ID())));
				if (DeleteSchedulerUtil.deleteMediaFromPortal(Long.parseLong(a
						.getCONTENT_ID()))) {
					a.setCONTENT_STAT_CD(SchedulerConstant.STATUS_DELETE);
					a.setCONTENT_STAT_DEL_DT(new Date());
					DeleteSchedulerUtil.updateArchive(a);
				}
			} else if (DeleteSchedulerUtil.flag == 1) {
				// content is deleted by user or admin ,in expired status
				a.setCONTENT_STAT_MOD_DT(DeleteSchedulerUtil.getModifiedDate(
						SchedulerConstant.MEDIA_IDENTIFIER,
						Long.parseLong(a.getCONTENT_ID())));
				a.setCONTENT_STAT_CD(SchedulerConstant.STATUS_DELETED_FROM_PORTAL);
				DeleteSchedulerUtil.updateArchive(a);
			}

			DeleteSchedulerUtil.flag = 0;
			
			
		}

	}
	public void processBookmarks(){
		if (bookmark == null ||bookmark.size() == 0) {
			throw new IllegalArgumentException();
		}

		for (ArchiveContent a :bookmark) {
			// set modified in the Archive content
							
			if (DeleteSchedulerUtil.isModifiedInExpiredStatus(
					Long.parseLong(a.getCONTENT_ID()), a.getCONTENT_STAT_CD(),
					a.getCONTENT_STAT_EXPIRED_DT(), a.getCONTENT_STAT_DEL_DT(),
					SchedulerConstant.BOOKMARK_IDENTIFIER)) {
				// action is taken by the author
				DeleteSchedulerUtil.removeExpiredTag(a,
						SchedulerConstant.BOOKMARK_IDENTIFIER);
				DeleteSchedulerUtil.deleteFromArchiveContent(a.getCONTENT_ID());
				

			} else if (DeleteSchedulerUtil.flag == 2) {
				// content is not modified in expired period
				a.setCONTENT_STAT_MOD_DT(DeleteSchedulerUtil.getModifiedDate(
						SchedulerConstant.BOOKMARK_IDENTIFIER,
						Long.parseLong(a.getCONTENT_ID())));
				if (DeleteSchedulerUtil.deleteBookmarkFromPortal(Long.parseLong(a
						.getCONTENT_ID()))) {
					a.setCONTENT_STAT_CD(SchedulerConstant.STATUS_DELETE);
					a.setCONTENT_STAT_DEL_DT(new Date());
					DeleteSchedulerUtil.updateArchive(a);
				}
			} else if (DeleteSchedulerUtil.flag == 1) {
				// content is deleted by user or admin ,in expired status
				a.setCONTENT_STAT_MOD_DT(DeleteSchedulerUtil.getModifiedDate(
						SchedulerConstant.BOOKMARK_IDENTIFIER,
						Long.parseLong(a.getCONTENT_ID())));
				a.setCONTENT_STAT_CD(SchedulerConstant.STATUS_DELETED_FROM_PORTAL);
				DeleteSchedulerUtil.updateArchive(a);
			}

			DeleteSchedulerUtil.flag = 0;
			
		}		
	}

	public void processCommunity() {
		if (communities == null || communities.size() == 0) {
			throw new IllegalArgumentException();
		}

		for (ArchiveContent a : communities) {
			List<ArchiveContent> communitywebcontents = DeleteSchedulerUtil
					.getCommuntiyWebcontent(webcontents,
							Long.parseLong(a.getCONTENT_ID()));

			List<ArchiveContent> communitymedia = DeleteSchedulerUtil
					.getCommuntiyWebcontent(media,
							Long.parseLong(a.getCONTENT_ID()));

			if (DeleteSchedulerUtil
					.isAnyWebContentUpdated(communitywebcontents)
					|| DeleteSchedulerUtil.isAnyMediaUpdated(communitymedia)) {
				a.setCONTENT_STAT_CD("active");
				DeleteSchedulerUtil.removeExpiredTag(a, SchedulerConstant.COMMUNITY_IDENTIFIER);
				continue;
			} else {

				try {
					GroupLocalServiceUtil.deleteGroup(a.getGRP_ID());
				} catch (PortalException e) {
					// TODO Auto-generated catch block
					logger.error("Communtiy cannot be deleted "
							+ a.getGRP_ID());
					e.printStackTrace();
				} catch (SystemException e) {

					logger.error("Error occured while communicating with database");
					e.printStackTrace();
				}
			}

		}

	}

}
