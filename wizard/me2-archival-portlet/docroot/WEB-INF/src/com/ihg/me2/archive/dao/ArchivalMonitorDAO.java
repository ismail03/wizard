package com.ihg.me2.archive.dao;

import com.ihg.me2.archive.service.model.ArchiveContent;
import com.ihg.me2.archive.service.service.ArchiveContentLocalServiceUtil;
import com.ihg.me2.archive.util.ArchivalMonitorConstants;
import com.ihg.me2.archive.util.ArchivalMonitorUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.util.portlet.PortletProps;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArchivalMonitorDAO {

	private static final Log LOG = LogFactoryUtil.getLog(ArchivalMonitorDAO.class);
	
	private void addOrUpdateLastMonitorSchedulerRun()
	{
		System.out.println("ArchiveContentSearch.addLastMonitorSchedulerRun()");
		String monitorSchedulerID = PortletProps.get(ArchivalMonitorConstants.PORTLET_MONITOR_SCHEDULER_ID);
		System.out.println("monito Scheduler ID:-->"+monitorSchedulerID);
		try {
				if(null==lastMonitorSchedulerRunDate){
	            	System.out.println("lastMonitorSchedulerRunDate is null");
					ArchiveContent archiveContent = ArchiveContentLocalServiceUtil.createArchiveContent(monitorSchedulerID);
					archiveContent.setAUTHOR_EMAIL_ID("sandipm.patel@cignex.com");
					archiveContent.setAUTHOR_NM("Sandip Patel");
					archiveContent.setCONTENT_TYP("Archival");
					archiveContent.setGRP_ID(0);
					archiveContent.setCONTENT_NM("ArchivalMonitor");
					archiveContent.setCONTENT_URL_TXT("Archival Monitor");
					archiveContent.setCONTENT_STAT_NOTIFIED_DT(currentDate);
					archiveContent.setCONTENT_STAT_MOD_DT(currentDate);
					archiveContent.setCONTENT_STAT_EXPIRED_DT(currentDate);
					archiveContent.setCONTENT_STAT_DEL_DT(currentDate);
					archiveContent.setCREAT_TS(currentDate);
					archiveContent.setCREAT_USR_ID(ArchivalMonitorConstants.USER_ARCHIVAL_MONITOR);
					archiveContent.setLST_UPDT_TS(currentDate);
					archiveContent.setLST_UPDT_USR_ID(ArchivalMonitorConstants.USER_ARCHIVAL_MONITOR);
					archiveContent.setCONTENT_STAT_CD("init");
					ArchiveContent archiveContent2 = ArchiveContentLocalServiceUtil.addArchiveContent(archiveContent);
				}
				else{
					ArchiveContent archiveContent = ArchiveContentLocalServiceUtil.getArchiveContent(monitorSchedulerID);
					archiveContent.setCONTENT_STAT_NOTIFIED_DT(currentDate);
					archiveContent.setCREAT_TS(currentDate);
					archiveContent.setLST_UPDT_TS(currentDate);
					ArchiveContentLocalServiceUtil.updateArchiveContent(archiveContent);
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static Date isLastMonitorSchedulerRun(){
		Date lastMonitorSchedulerRunDate = null;
		try {
			ArchiveContent archiveContents = ArchiveContentLocalServiceUtil.getArchiveContent(PortletProps.get(ArchivalMonitorConstants.PORTLET_MONITOR_SCHEDULER_ID));
			if(null!=archiveContents)
			{
				lastMonitorSchedulerRunDate = archiveContents.getCONTENT_STAT_NOTIFIED_DT();
			}
		} catch (PortalException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
		return lastMonitorSchedulerRunDate;
	}
	Date lastMonitorSchedulerRunDate;
	Date toArchivalCheckDate;
	Date fromArchivalCheckDate;
	Date expiredDate, deletedDate;
	Date currentDate;
	Timestamp currentTimeStamp;
	long companyId;
	String portalURL;
	int from=0, to=10;
	public ArchivalMonitorDAO(long companyId, String portalURL){
		lastMonitorSchedulerRunDate = isLastMonitorSchedulerRun();
		toArchivalCheckDate = null;
		Calendar cal = Calendar.getInstance();
		currentDate = cal.getTime();
		int archivalYear = Integer.parseInt(PortletProps.get(ArchivalMonitorConstants.PORTLET_ARCHIVAL_YEAR));
		if(null!=lastMonitorSchedulerRunDate)
		{
			// Means scheduler is not running first time.
			fromArchivalCheckDate = ArchivalMonitorUtil.getNYearBeforeDate(lastMonitorSchedulerRunDate, archivalYear);
			toArchivalCheckDate = ArchivalMonitorUtil.getNYearBeforeDate(currentDate, archivalYear);
		}
		else
		{
			// scheduler is running first time.
			fromArchivalCheckDate = ArchivalMonitorUtil.getMinimumAssetModifiedDate();
			toArchivalCheckDate = ArchivalMonitorUtil.getNYearBeforeDate(null, archivalYear);
		}
		int expiredWeek = Integer.parseInt(PortletProps.get(ArchivalMonitorConstants.PORTLET_EXPIRED_WEEK));
		int deletedMonth = Integer.parseInt(PortletProps.get(ArchivalMonitorConstants.PORTLET_DELETED_MONTH));
		expiredDate = ArchivalMonitorUtil.getNWeeksLaterDate(null, expiredWeek);
		deletedDate = ArchivalMonitorUtil.getNMonthLaterDate(expiredDate, deletedMonth);
		this.companyId = companyId;
		this.portalURL = portalURL;
		System.out.println("fromArchivalCheckDate:-->"+fromArchivalCheckDate);
		System.out.println("toArchivalCheckDate:-->"+toArchivalCheckDate);
	}
	public Query getMerlin() 
	{
		Query finalQuery = null;
		try 
		{
			System.out.println("getMerlin() ===================================================");
			String COMPONENT_NEWSROOM = PortletProps.get(ArchivalMonitorConstants.PORTLET_COMPONENT_NEWSROOM);
			String COMPONENT_APPLICATION = PortletProps.get(ArchivalMonitorConstants.PORTLET_COMPONENT_APPLICATIONS);
			String COMPONENT_DEPARTMENT = PortletProps.get(ArchivalMonitorConstants.PORTLET_COMPONENT_DEPARTMENTS);
			String COMPONENT_INITIATIVE = PortletProps.get(ArchivalMonitorConstants.PORTLET_COMPONENT_INITIATIVES);
			String COMPONENT_CONFERENCE = PortletProps.get(ArchivalMonitorConstants.PORTLET_COMPONENT_CONFERENCES);
			BooleanQuery compositeQuery = BooleanQueryFactoryUtil.create();
			String className = Group.class.getName();
			
			/*long classNameID = ClassNameLocalServiceUtil.getClassNameId(className);
			System.out.println("ClassNameLocalServiceUtil.getClassNameId():-->"+classNameID);*/
			
			System.out.println("@!this is newsroom compponents whether its on or not :-->"+COMPONENT_NEWSROOM);
			BooleanQuery modelQuery = BooleanQueryFactoryUtil.create();
			modelQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, className);
			modelQuery.addRequiredTerm(Field.ASSET_TAG_NAMES, "merlin");
			compositeQuery.add(modelQuery, BooleanClauseOccur.MUST);
			System.out.println("Group compositeQuery:-->"+compositeQuery.toString());
			
			Hits merlinCommunityHits;
			List<Document> docsList;
			try {
				/**
				 * It will fetch merlin tag communities of Portal.
				 */
				System.out.println("ArchivalMonitorDAO.getMerlin() companyId:-->"+companyId);
				merlinCommunityHits = SearchEngineUtil.search(companyId, compositeQuery, from, to);
//				renderRequest.setAttribute(SearchConstants.TOTAL_COUNT, hits.getLength());
	            System.out.println("Total pages:-->"+merlinCommunityHits.getLength());
	            docsList = merlinCommunityHits.toList();
	            ArchiveContent archiveContent;
	            List<ArchiveContent> archiveContents = new ArrayList<ArchiveContent>();
	            User user;
	            String contentId;
	            long groupId;
//	            Date currentDateTime=CalendarUtil.get;
	            if(Validator.isNotNull(docsList)){
	               for (Document document : docsList) {
					groupId = Long.valueOf(document.get("groupId")).longValue();
					Group community = GroupLocalServiceUtil.getGroup(groupId);
					String communityName = document.get("name");
					System.out.println("=====================	Group component wise searching =============");
//	            	System.out.println("1)documents:->"+document.toString());
					System.out.println("2)documents name:->"+document.getUID());
					System.out.println("3)documents.get(title):->"+communityName);
					System.out.println("4) fetching modified:->"+document.get(Field.MODIFIED));
					System.out.println("=====================	Ends of Group component Artical	=============");
					
					/**
					 * If Community is Newsroom then it will process for Newsroom Webcontent.
					 */
					if(Validator.isNotNull(communityName) && communityName.equalsIgnoreCase(ArchivalMonitorConstants.PORTLET_COMPONENT_NEWSROOM) && COMPONENT_NEWSROOM.equalsIgnoreCase(ArchivalMonitorConstants.ARCHIVAL_COMPONENT_ON))
					{
						System.out.println("========= Now Resources for Group:-->"+groupId);
						archiveContents.addAll(getNewsRoomData(groupId, community));
					}
					/**
					 * If Community is Department then it will process for Department Webcontent, Documents and Bookmark.
					 */
					if(Validator.isNotNull(communityName) && communityName.equalsIgnoreCase(ArchivalMonitorConstants.PORTLET_COMPONENT_DEPARTMENTS) && COMPONENT_DEPARTMENT.equalsIgnoreCase(ArchivalMonitorConstants.ARCHIVAL_COMPONENT_ON))
					{
						System.out.println("========= Now Resources for Group:-->"+groupId);
						archiveContents.addAll(getDepartmentData());
					}
					/**
					 * If Community is Initiatives then it will process for Initiatives Communities and their nested content.
					 */
					if(Validator.isNotNull(communityName) && communityName.equalsIgnoreCase(ArchivalMonitorConstants.PORTLET_COMPONENT_INITIATIVES) && COMPONENT_INITIATIVE.equalsIgnoreCase(ArchivalMonitorConstants.ARCHIVAL_COMPONENT_ON))
					{
						archiveContents.addAll(getInitiativesData());
					}
					/**
					 * If Community is Conference then it will process for Initiatives Communities and their nested content.
					 */
					if(Validator.isNotNull(communityName) && communityName.equalsIgnoreCase(ArchivalMonitorConstants.PORTLET_COMPONENT_CONFERENCES) && COMPONENT_CONFERENCE.equalsIgnoreCase(ArchivalMonitorConstants.ARCHIVAL_COMPONENT_ON))
					{
						archiveContents.addAll(getConferenceData());
					}
					/**
					 * If Community is Application then it will process for Application Web Content.
					 */
					if(Validator.isNotNull(communityName) && communityName.equalsIgnoreCase(ArchivalMonitorConstants.PORTLET_COMPONENT_APPLICATIONS) && COMPONENT_APPLICATION.equalsIgnoreCase(ArchivalMonitorConstants.ARCHIVAL_COMPONENT_ON))
					{
						archiveContents.addAll(getApplicationData(groupId, community));
					}
				  } // Merlin Coummunity list iteration through for loop
	              
	              
	               if(null!=archiveContents && archiveContents.size()>0){
	            	   /**
	            	    * call a insert batch query here.
	            	    */
	            	   List<ArchiveContent> aContents = ArchiveContentLocalServiceUtil.addBatchArchiveContent(archiveContents);
	            	   
	            	   /**
	            	    * If last scheduler run date is not exist into archive_content table then insert it first time.
	            	    */
	            		   addOrUpdateLastMonitorSchedulerRun();
	               } 
	           } // Merlin Community list is not null
			} catch (SearchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (Exception e) 
		{
			LOG.error("getMerlin() Error making query for DL : " + e.getMessage(), e);
			System.out.println("ArchiveContentSearch.getMerlin() Error making query for DL : " + e.getMessage());
		}
		
		return finalQuery;
	}
	public List<ArchiveContent> getDepartmentData()
	{
		List<ArchiveContent> archiveContents = new ArrayList<ArchiveContent>();
		try {
//			+(CONTENT_SOURCE:MERLIN) + (PARENT_CONTENT_TYPE:DEPARTMENT) + (CONTENT_TYPE:HOT_TOPICS CONTENT_TYPE:RESOURCE CONTENT_TYPE:LINK)
			BooleanQuery departmentModelQuery = BooleanQueryFactoryUtil.create();
			/**
			 * get documents of all organizations and their sub organizations of Custom type Department.
			 */
			BooleanQuery departmentDocumentQuery = BooleanQueryFactoryUtil.create();
//			Query departmentDocumentModelQuery = getDocuments(groupId, companyId);
			String dlClassName = DLFileEntry.class.getName();
			departmentModelQuery = BooleanQueryFactoryUtil.create();
			departmentModelQuery.addRequiredTerm("CONTENT_SOURCE", "MERLIN");
			departmentModelQuery.addRequiredTerm("PARENT_CONTENT_TYPE", "DEPARTMENT");
			departmentModelQuery.addRequiredTerm("CONTENT_TYPE", "RESOURCE");
			departmentModelQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, dlClassName);
			departmentModelQuery.addRangeTerm(ArchivalMonitorConstants.MODIFIED_DATE, fromArchivalCheckDate.getTime(), toArchivalCheckDate.getTime());
			String groupFriendlyURL = ""; //community.getFriendlyURL()
			if(Validator.isNotNull(departmentModelQuery)){
				departmentDocumentQuery.add(departmentModelQuery, BooleanClauseOccur.MUST);
				Hits departmentDocumentHits = SearchEngineUtil.search(companyId, departmentDocumentQuery, from, to);
				List<Document> departmentDocumentList = departmentDocumentHits.toList();
				for (Document document2 : departmentDocumentList) {
					groupFriendlyURL=document2.get("CONTENT_LINK");
					archiveContents.add(setDocument(document2, ArchivalMonitorConstants.CONTENT_TYPE_DEPARTMENT_DOCUMENTS, ArchivalMonitorConstants.PORTLET_COMPONENT_DEPARTMENTS, groupFriendlyURL));
				}
			}
			
			/**
			 * get JournalArticle of all organizations and their sub organizations of Custom type Department.
			 */
			BooleanQuery departmentArticleQuery = BooleanQueryFactoryUtil.create();
			String articleClassName = JournalArticle.class.getName();
			departmentModelQuery = BooleanQueryFactoryUtil.create();
			departmentModelQuery.addRequiredTerm("CONTENT_SOURCE", "MERLIN");
			departmentModelQuery.addRequiredTerm("PARENT_CONTENT_TYPE", "DEPARTMENT");
			departmentModelQuery.addRequiredTerm("CONTENT_TYPE", "HOT_TOPICS");
			departmentModelQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, articleClassName);
			departmentModelQuery.addRangeTerm(ArchivalMonitorConstants.MODIFIED_DATE, fromArchivalCheckDate.getTime(), toArchivalCheckDate.getTime());
			if(Validator.isNotNull(departmentModelQuery)){
				departmentArticleQuery.add(departmentModelQuery, BooleanClauseOccur.MUST);
				departmentArticleQuery.add(getStructureIDQuery(ArchivalMonitorConstants.STRUCTURE_ID_DEPARTMENT_WEB_CONTENT), BooleanClauseOccur.MUST);
				Hits departmentArticleHits = SearchEngineUtil.search(companyId, departmentArticleQuery, from, to);
				List<Document> departmentArticleList = departmentArticleHits.toList();
				for (Document document2 : departmentArticleList) {
					groupFriendlyURL=document2.get("CONTENT_LINK");
					archiveContents.add(setDocument(document2, ArchivalMonitorConstants.CONTENT_TYPE_DEPARTMENT_WEB_CONTENT, ArchivalMonitorConstants.PORTLET_COMPONENT_DEPARTMENTS, groupFriendlyURL));
				}
			}
			
			/**
			 * get Bookmark of all organizations and their sub organizations of Custom type Department.
			 */
			BooleanQuery departmentBookmarkQuery = BooleanQueryFactoryUtil.create();
			String bookmarkClassName = BookmarksEntry.class.getName();
			departmentModelQuery = BooleanQueryFactoryUtil.create();
			departmentModelQuery.addRequiredTerm("CONTENT_SOURCE", "MERLIN");
			departmentModelQuery.addRequiredTerm("PARENT_CONTENT_TYPE", "DEPARTMENT");
			departmentModelQuery.addRequiredTerm("CONTENT_TYPE", "LINK");
			departmentModelQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, bookmarkClassName);
			departmentModelQuery.addRangeTerm(ArchivalMonitorConstants.MODIFIED_DATE, fromArchivalCheckDate.getTime(), toArchivalCheckDate.getTime());
			if(Validator.isNotNull(departmentModelQuery)){
				departmentBookmarkQuery.add(departmentModelQuery, BooleanClauseOccur.MUST);
				Hits departmentBookmarkHits = SearchEngineUtil.search(companyId, departmentBookmarkQuery, from, to);
				List<Document> departmentBookmarkList = departmentBookmarkHits.toList();
				for (Document document2 : departmentBookmarkList) {
					groupFriendlyURL=document2.get("CONTENT_LINK");
					archiveContents.add(setDocument(document2, ArchivalMonitorConstants.CONTENT_TYPE_DEPARTMENT_BOOKMARK, ArchivalMonitorConstants.PORTLET_COMPONENT_DEPARTMENTS, groupFriendlyURL));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return archiveContents;
	}
	public List<ArchiveContent> getDepartmentCommunityData(long groupId, Group community)
	{
		List<ArchiveContent> archiveContents = new ArrayList<ArchiveContent>();
		try {
			BooleanQuery departmentDocumentQuery = BooleanQueryFactoryUtil.create();
			Query departmentDocumentModelQuery = getDocuments(groupId, companyId);
			if(Validator.isNotNull(departmentDocumentModelQuery)){
				departmentDocumentQuery.add(departmentDocumentModelQuery, BooleanClauseOccur.MUST);
				Hits departmentDocumentHits = SearchEngineUtil.search(companyId, departmentDocumentQuery, from, to);
				List<Document> departmentDocumentList = departmentDocumentHits.toList();
				for (Document document2 : departmentDocumentList) {
					archiveContents.add(setDocument(document2, ArchivalMonitorConstants.CONTENT_TYPE_DEPARTMENT_DOCUMENTS, ArchivalMonitorConstants.PORTLET_COMPONENT_DEPARTMENTS, community.getFriendlyURL()));
				}
			}
			BooleanQuery departmentArticleQuery = BooleanQueryFactoryUtil.create();
			Query departmentModelQuery = getWebContents(groupId, companyId, ArchivalMonitorConstants.STRUCTURE_ID_DEPARTMENT_WEB_CONTENT);
			if(Validator.isNotNull(departmentModelQuery)){
				departmentArticleQuery.add(departmentModelQuery, BooleanClauseOccur.MUST);
				Hits departmentArticleHits = SearchEngineUtil.search(companyId, departmentArticleQuery, from, to);
				List<Document> departmentArticleList = departmentArticleHits.toList();
				for (Document document2 : departmentArticleList) {
					archiveContents.add(setWebContent(document2, ArchivalMonitorConstants.CONTENT_TYPE_DEPARTMENT_WEB_CONTENT, ArchivalMonitorConstants.PORTLET_COMPONENT_DEPARTMENTS, community.getFriendlyURL()));
				}
			}
			BooleanQuery departmentBookmarkQuery = BooleanQueryFactoryUtil.create();
			Query departmentBookmarkModelQuery = getBookmarks(groupId, companyId);
			if(Validator.isNotNull(departmentBookmarkModelQuery)){
				departmentBookmarkQuery.add(departmentBookmarkModelQuery, BooleanClauseOccur.MUST);
				Hits departmentBookmarkHits = SearchEngineUtil.search(companyId, departmentBookmarkQuery, from, to);
				List<Document> departmentBookmarkList = departmentBookmarkHits.toList();
				for (Document document2 : departmentBookmarkList) {
					archiveContents.add(setBookmark(document2, ArchivalMonitorConstants.CONTENT_TYPE_DEPARTMENT_BOOKMARK, ArchivalMonitorConstants.PORTLET_COMPONENT_DEPARTMENTS, community.getFriendlyURL()));
				}
			}
		} catch (com.liferay.portal.kernel.search.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SearchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return archiveContents;
	}
	private static Query getStructureIDQuery(String[] structureIds) {
		BooleanQuery structureIDQuery = BooleanQueryFactoryUtil.create();
		for(String structureId:structureIds){
			try {
				structureIDQuery.addTerm(ArchivalMonitorConstants.Field_STRUCTURE_ID, structureId);
			}
			catch (Exception e) {
				LOG.error("Unable to create Category Query, " + e.getMessage());
			}
		}
		return structureIDQuery;
	}
	public List<ArchiveContent> getNewsRoomData(long groupId, Group community)
	{
		List<ArchiveContent> archiveContents = new ArrayList<ArchiveContent>();
		try {
			BooleanQuery newsRoomArticleQuery = BooleanQueryFactoryUtil.create();
			Query newsRoomArticleModelQuery = getWebContents(groupId, companyId, ArchivalMonitorConstants.STRUCTURE_ID_NEWSROOM_WEB_CONTENT);
			if(Validator.isNotNull(newsRoomArticleModelQuery)){
				newsRoomArticleQuery.add(newsRoomArticleModelQuery, BooleanClauseOccur.MUST);
				System.out.println("ArchivalMonitorDAO.getNewsRoomData() newsRoomArticleQuery:-->"+newsRoomArticleQuery);
				Hits newsroomArticleHits = SearchEngineUtil.search(companyId, newsRoomArticleQuery, from, to);
				List<Document> newsroomArticleList = newsroomArticleHits.toList();
				for (Document document2 : newsroomArticleList) {
					archiveContents.add(setWebContent(document2, ArchivalMonitorConstants.CONTENT_TYPE_NEWSROOM_WEB_CONTENT, ArchivalMonitorConstants.PORTLET_COMPONENT_NEWSROOM, community.getFriendlyURL()));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return archiveContents;
	}
	public List<ArchiveContent> getInitiativesData()
	{
		List<ArchiveContent> archiveContents = new ArrayList<ArchiveContent>();
		try {
			BooleanQuery initiativeCommunitiesQuery = BooleanQueryFactoryUtil.create();
			Query initiativeModelQuery = getInitiativeCommunities();
			if(Validator.isNotNull(initiativeModelQuery)){
				initiativeCommunitiesQuery.add(initiativeModelQuery, BooleanClauseOccur.MUST);
				Hits initiativeCommunityHits = SearchEngineUtil.search(companyId, initiativeCommunitiesQuery, from, to);
				List<Document> initiativeCommunitiesList = initiativeCommunityHits.toList();
				for (Document document : initiativeCommunitiesList) {
					long groupId = Long.valueOf(document.get(Field.GROUP_ID)).longValue();
					Group initiativeCommunity = GroupLocalServiceUtil.getGroup(groupId);
					
					long classNameID = ClassNameLocalServiceUtil.getClassNameId(Group.class.getName());
					System.out.println("ClassNameLocalServiceUtil.getClassNameId():-->"+classNameID);
					
					// classNameID, classPK, modifiedDate
//					AssetEntryLocalServiceUtil.geta
					DynamicQuery dynamicAssetEntryQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class, PortalClassLoaderUtil.getClassLoader());
					Criterion criterion = null;
					criterion = RestrictionsFactoryUtil.between("modifiedDate", fromArchivalCheckDate, toArchivalCheckDate); 
					criterion = RestrictionsFactoryUtil.eq("classPK", groupId);
					criterion = RestrictionsFactoryUtil.eq("classNameId", classNameID);
					
					if(Validator.isNull(criterion))
						dynamicAssetEntryQuery.add(criterion);
//					dynamicQuery.setProjection(ProjectionFactoryUtil.min("modifiedDate"));

					List assetEntries = AssetEntryLocalServiceUtil.dynamicQuery(dynamicAssetEntryQuery);
					
					if(null!=assetEntries && assetEntries.size()>0){
						boolean isInitiativeDocsNotModified = false, isInitiativeArticlesNotModified = false, isInitiativeBookmarksNotModified = false;
						Hits initiativeDocumentHits = null, initiativeArticleHits = null, initiativeBookmarkHits = null;
						BooleanQuery departmentDocumentQuery = BooleanQueryFactoryUtil.create();
						Query departmentDocumentModelQuery = getDocuments(groupId, companyId);
						if(Validator.isNotNull(departmentDocumentModelQuery)){
							departmentDocumentQuery.add(departmentDocumentModelQuery, BooleanClauseOccur.MUST);
							initiativeDocumentHits = SearchEngineUtil.search(companyId, departmentDocumentQuery, from, to);
							Hits initiativeTotalDocumentHits = SearchEngineUtil.search(companyId, getTotalDocuments(groupId, companyId), from, to);
							if(initiativeTotalDocumentHits.getLength()==initiativeDocumentHits.getLength())
								isInitiativeDocsNotModified = true;
						}
						BooleanQuery departmentArticleQuery = BooleanQueryFactoryUtil.create();
						Query departmentModelQuery = getWebContents(groupId, companyId, ArchivalMonitorConstants.STRUCTURE_ID_INITIATIVES_WEB_CONTENT);
						if(Validator.isNotNull(departmentModelQuery)){
							departmentArticleQuery.add(departmentModelQuery, BooleanClauseOccur.MUST);
							initiativeArticleHits = SearchEngineUtil.search(companyId, departmentArticleQuery, from, to);
							Hits initiativeTotalArticleHits = SearchEngineUtil.search(companyId, getTotalWebContents(groupId, ArchivalMonitorConstants.STRUCTURE_ID_INITIATIVES_WEB_CONTENT), from, to);
							if(initiativeTotalArticleHits.getLength()==initiativeArticleHits.getLength())
								isInitiativeArticlesNotModified = true;
						}
						BooleanQuery departmentBookmarkQuery = BooleanQueryFactoryUtil.create();
						Query departmentBookmarkModelQuery = getBookmarks(groupId, companyId);
						if(Validator.isNotNull(departmentBookmarkModelQuery)){
							departmentBookmarkQuery.add(departmentBookmarkModelQuery, BooleanClauseOccur.MUST);
							initiativeBookmarkHits = SearchEngineUtil.search(companyId, departmentBookmarkQuery, from, to);
							Hits initiativeTotalBookmarkHits = SearchEngineUtil.search(companyId, getTotalBookmarks(groupId, companyId), from, to);
							if(initiativeTotalBookmarkHits.getLength()==initiativeBookmarkHits.getLength())
								isInitiativeDocsNotModified = true;
						}
	//					archiveContents.add(setDocument(document2, ArchivalMonitorConstants.CONTENT_TYPE_INITIATIVES_DOCUMENTS, ArchivalMonitorConstants.PORTLET_COMPONENT_INITIATIVES, community.getFriendlyURL()));
						if(isInitiativeDocsNotModified || isInitiativeArticlesNotModified || isInitiativeBookmarksNotModified)
						{
							if(null!=initiativeDocumentHits && initiativeDocumentHits.getLength()>0){
								List<Document> departmentDocumentList = initiativeDocumentHits.toList();
								for (Document document2 : departmentDocumentList) {
									archiveContents.add(setDocument(document2, ArchivalMonitorConstants.CONTENT_TYPE_INITIATIVES_DOCUMENTS, ArchivalMonitorConstants.PORTLET_COMPONENT_INITIATIVES, initiativeCommunity.getFriendlyURL()));
								}
							}
							if(null!=initiativeArticleHits && initiativeArticleHits.getLength()>0){
								List<Document> departmentArticleList = initiativeArticleHits.toList();
								for (Document document2 : departmentArticleList) {
									archiveContents.add(setWebContent(document2, ArchivalMonitorConstants.CONTENT_TYPE_INITIATIVES_WEB_CONTENT, ArchivalMonitorConstants.PORTLET_COMPONENT_INITIATIVES, initiativeCommunity.getFriendlyURL()));
								}
							}
							if(null!=initiativeBookmarkHits && initiativeBookmarkHits.getLength()>0){
								List<Document> departmentBookmarkList = initiativeBookmarkHits.toList();
								for (Document document2 : departmentBookmarkList) {
									archiveContents.add(setBookmark(document2, ArchivalMonitorConstants.CONTENT_TYPE_INITIATIVES_BOOKMARK, ArchivalMonitorConstants.PORTLET_COMPONENT_INITIATIVES, initiativeCommunity.getFriendlyURL()));
								}
							}
							if(isInitiativeDocsNotModified && isInitiativeArticlesNotModified && isInitiativeBookmarksNotModified)
							{
								archiveContents.add(setGroup(document, ArchivalMonitorConstants.CONTENT_TYPE_INITIATIVES_COMMUNITY, ArchivalMonitorConstants.PORTLET_COMPONENT_INITIATIVES, initiativeCommunity.getFriendlyURL()));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return archiveContents;
	}
	public List<ArchiveContent> getConferenceData()
	{
		List<ArchiveContent> archiveContents = new ArrayList<ArchiveContent>();
		try {
			BooleanQuery conferenceCommunitiesQuery = BooleanQueryFactoryUtil.create();
			Query conferenceModelQuery = getConferenceCommunities();
			if(Validator.isNotNull(conferenceModelQuery)){
				conferenceCommunitiesQuery.add(conferenceModelQuery, BooleanClauseOccur.MUST);
				Hits conferenceCommunityHits = SearchEngineUtil.search(companyId, conferenceCommunitiesQuery, from, to);
				List<Document> conferenceCommunitiesList = conferenceCommunityHits.toList();
				for (Document document : conferenceCommunitiesList) {
					long groupId = Long.valueOf(document.get(Field.GROUP_ID)).longValue();
					Group conferenceCommunity = GroupLocalServiceUtil.getGroup(groupId);
					
					long classNameID = ClassNameLocalServiceUtil.getClassNameId(Group.class.getName());
					System.out.println("ClassNameLocalServiceUtil.getClassNameId():-->"+classNameID);
					
					// classNameID, classPK, modifiedDate
//					AssetEntryLocalServiceUtil.geta
					DynamicQuery dynamicAssetEntryQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class, PortalClassLoaderUtil.getClassLoader());
					Criterion criterion = null;
					criterion = RestrictionsFactoryUtil.between("modifiedDate", fromArchivalCheckDate, toArchivalCheckDate); 
					criterion = RestrictionsFactoryUtil.eq("classPK", groupId);
					criterion = RestrictionsFactoryUtil.eq("classNameId", classNameID);
					
					if(Validator.isNull(criterion))
						dynamicAssetEntryQuery.add(criterion);
//					dynamicQuery.setProjection(ProjectionFactoryUtil.min("modifiedDate"));

					List assetEntries = AssetEntryLocalServiceUtil.dynamicQuery(dynamicAssetEntryQuery);
					
					if(null!=assetEntries && assetEntries.size()>0){
						boolean isConferenceDocsNotModified = false, isConferenceArticlesNotModified = false;
						Hits conferenceDocumentHits = null, conferenceArticleHits = null;
						BooleanQuery conferenceDocumentQuery = BooleanQueryFactoryUtil.create();
						Query conferenceDocumentModelQuery = getDocuments(groupId, companyId);
						if(Validator.isNotNull(conferenceDocumentModelQuery)){
							conferenceDocumentQuery.add(conferenceDocumentModelQuery, BooleanClauseOccur.MUST);
							conferenceDocumentHits = SearchEngineUtil.search(companyId, conferenceDocumentQuery, from, to);
							Hits conferenceTotalDocumentHits = SearchEngineUtil.search(companyId, getTotalDocuments(groupId, companyId), from, to);
							if(conferenceTotalDocumentHits.getLength()==conferenceDocumentHits.getLength())
								isConferenceDocsNotModified = true;
						}
						BooleanQuery conferenceArticleQuery = BooleanQueryFactoryUtil.create();
						Query departmentModelQuery = getWebContents(groupId, companyId, ArchivalMonitorConstants.STRUCTURE_ID_CONFERENCE_WEB_CONTENT);
						if(Validator.isNotNull(departmentModelQuery)){
							conferenceArticleQuery.add(departmentModelQuery, BooleanClauseOccur.MUST);
							conferenceArticleHits = SearchEngineUtil.search(companyId, conferenceArticleQuery, from, to);
							Hits conferenceTotalArticleHits = SearchEngineUtil.search(companyId, getTotalWebContents(groupId, ArchivalMonitorConstants.STRUCTURE_ID_CONFERENCE_WEB_CONTENT), from, to);
							if(conferenceTotalArticleHits.getLength()==conferenceArticleHits.getLength())
								isConferenceArticlesNotModified = true;
						}
	//					archiveContents.add(setDocument(document2, ArchivalMonitorConstants.CONTENT_TYPE_INITIATIVES_DOCUMENTS, ArchivalMonitorConstants.PORTLET_COMPONENT_INITIATIVES, community.getFriendlyURL()));
						if(isConferenceDocsNotModified || isConferenceArticlesNotModified)
						{
							if(null!=conferenceDocumentHits && conferenceDocumentHits.getLength()>0){
								List<Document> departmentDocumentList = conferenceDocumentHits.toList();
								for (Document document2 : departmentDocumentList) {
									archiveContents.add(setDocument(document2, ArchivalMonitorConstants.CONTENT_TYPE_CONFERENCE_DOCUMENTS, ArchivalMonitorConstants.PORTLET_COMPONENT_CONFERENCES, conferenceCommunity.getFriendlyURL()));
								}
							}
							if(null!=conferenceArticleHits && conferenceArticleHits.getLength()>0){
								List<Document> departmentArticleList = conferenceArticleHits.toList();
								for (Document document2 : departmentArticleList) {
									archiveContents.add(setWebContent(document2, ArchivalMonitorConstants.CONTENT_TYPE_CONFERENCE_WEB_CONTENT, ArchivalMonitorConstants.PORTLET_COMPONENT_CONFERENCES, conferenceCommunity.getFriendlyURL()));
								}
							}
							if(isConferenceDocsNotModified && isConferenceArticlesNotModified)
							{
								archiveContents.add(setGroup(document, ArchivalMonitorConstants.CONTENT_TYPE_CONFERENCE_COMMUNITY, ArchivalMonitorConstants.PORTLET_COMPONENT_CONFERENCES, conferenceCommunity.getFriendlyURL()));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return archiveContents;
	}
	public List<ArchiveContent> getApplicationData(long groupId, Group community)
	{
		List<ArchiveContent> archiveContents = new ArrayList<ArchiveContent>();
		try {
			BooleanQuery applicationArticleQuery = BooleanQueryFactoryUtil.create();
			String articleClassName = JournalArticle.class.getName();
			BooleanQuery applicationModelQuery = BooleanQueryFactoryUtil.create();
			/*BooleanQuery applicationHotelModelQuery = BooleanQueryFactoryUtil.create();
			BooleanQuery applicationHotelQuery = BooleanQueryFactoryUtil.create();
			applicationHotelModelQuery.addRequiredTerm("CONTENT_TYPE", "Hotel_solution_id");
			if(Validator.isNotNull(applicationHotelModelQuery)){
				applicationHotelQuery.add(applicationHotelModelQuery, BooleanClauseOccur.MUST);
				Hits departmentArticleHits = SearchEngineUtil.search(companyId, applicationHotelQuery, from, to);
			}*/
			applicationModelQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, articleClassName);
			applicationModelQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			applicationModelQuery.addRangeTerm(ArchivalMonitorConstants.MODIFIED_DATE, fromArchivalCheckDate.getTime(), toArchivalCheckDate.getTime());
			if(Validator.isNotNull(applicationModelQuery)){
				applicationArticleQuery.add(applicationModelQuery, BooleanClauseOccur.MUST);
//				applicationArticleQuery.add(applicationHotelModelQuery, BooleanClauseOccur.MUST_NOT);
				applicationArticleQuery.add(getStructureIDQuery(ArchivalMonitorConstants.STRUCTURE_ID_APPLICATION_WEB_CONTENT), BooleanClauseOccur.MUST);
				Hits departmentArticleHits = SearchEngineUtil.search(companyId, applicationArticleQuery, from, to);
				List<Document> departmentArticleList = departmentArticleHits.toList();
				for (Document document2 : departmentArticleList) {
					archiveContents.add(setDocument(document2, ArchivalMonitorConstants.CONTENT_TYPE_DEPARTMENT_WEB_CONTENT, ArchivalMonitorConstants.PORTLET_COMPONENT_DEPARTMENTS, community.getFriendlyURL()));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return archiveContents;
	}
	public BooleanQuery getInitiativeCommunities() 
	{
		BooleanQuery compositeQuery = null;
		try 
		{
			System.out.println("ArchivalMonitorDAO.getInitiativeCommunities()");
//			System.out.println("getDocuments:- ===================================================groupId:-->"+groupId);
			compositeQuery = BooleanQueryFactoryUtil.create();
			String className = Group.class.getName();
			BooleanQuery modelQuery = BooleanQueryFactoryUtil.create();
			modelQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, className);
			modelQuery.addRequiredTerm("CONTENT_SOURCE", "merlin");
			modelQuery.addRequiredTerm("CONTENT_PARENT_NAME", "Initiative");
			modelQuery.addRequiredTerm("CONTENT_TYPE", "Initiative");
			modelQuery.addRangeTerm(ArchivalMonitorConstants.MODIFIED_DATE, fromArchivalCheckDate.getTime(), toArchivalCheckDate.getTime());
//			modelQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			compositeQuery.add(modelQuery, BooleanClauseOccur.MUST);
			System.out.println("compositeQuery:-->"+compositeQuery.toString());
		} 
		catch (Exception e) 
		{
			LOG.error("getInitiativeCommunities() Error making query for DL : " + e.getMessage(), e);
			System.out.println("ArchiveContentSearch.getInitiativeCommunities() Error making query for DL : " + e.getMessage());
		}
		return compositeQuery;
	}
	public BooleanQuery getConferenceCommunities() 
	{
		BooleanQuery compositeQuery = null;
		try 
		{
			System.out.println("ArchivalMonitorDAO.getConferenceCommunities()");
			compositeQuery = BooleanQueryFactoryUtil.create();
			String className = Group.class.getName();
			BooleanQuery modelQuery = BooleanQueryFactoryUtil.create();
			modelQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, className);
			modelQuery.addRequiredTerm("CONTENT_SOURCE", "merlin");
			modelQuery.addRequiredTerm("CONTENT_PARENT_NAME", "Conference");
			modelQuery.addRequiredTerm("CONTENT_TYPE", "Conference");
			modelQuery.addRangeTerm(ArchivalMonitorConstants.MODIFIED_DATE, fromArchivalCheckDate.getTime(), toArchivalCheckDate.getTime());
//			modelQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			compositeQuery.add(modelQuery, BooleanClauseOccur.MUST);
			System.out.println("compositeQuery:-->"+compositeQuery.toString());
		} 
		catch (Exception e) 
		{
			LOG.error("getConferenceCommunities() Error making query for DL : " + e.getMessage(), e);
			System.out.println("ArchiveContentSearch.getConferenceCommunities() Error making query for DL : " + e.getMessage());
		}
		return compositeQuery;
	}
	public ArchiveContent setGroup(Document document, String contentType, String archivalComponentType, String groupFriendlyURL){
		ArchiveContent archiveContent = null;
		try {
			final String SOURCE_DATE_FORMAT_PATTERN  = "MM/dd/yyyy";
			final SimpleDateFormat sdfSource = new SimpleDateFormat(SOURCE_DATE_FORMAT_PATTERN, Locale.getDefault());
			long groupId = Long.valueOf(document.get(Field.GROUP_ID)).longValue();
			String contentId=document.get(Field.ENTRY_CLASS_PK);
			Calendar cal = Calendar.getInstance();
			String contentUrl = null;
//			JournalArticle journalArticle = JournalArticleLocalServiceUtil.getArticle(groupId, contentId);
			long userId = Long.valueOf(document.get(Field.USER_ID)).longValue();
			User user = UserLocalServiceUtil.getUser(userId);
			//http://localhost:8080/web/newsroom/article?id=12634
			contentUrl = portalURL + ArchivalMonitorConstants.WEB + StringPool.SLASH + groupFriendlyURL + ArchivalMonitorConstants.HOME;
			System.out.println("article URL>:->"+contentUrl);
			System.out.println("cal.getTime():-"+cal.getTime());
//			Date modifiedDate = sdfSource.parse(document.get(Field.MODIFIED));
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.valueOf(document.get(Field.MODIFIED)));
			System.out.println("modified Date:-->"+calendar.getTime());
			Date modifiedDate = cal.getTime();
			
			archiveContent = setArchiveContent(user.getEmailAddress(), user.getFullName(), contentId, document.get(Field.TITLE), contentType, contentUrl, ArchivalMonitorConstants.USER_ARCHIVAL_MONITOR, archivalComponentType, groupId, modifiedDate, ArchivalMonitorConstants.ARCHIVE_CONTENT_STATUS_NOTIFIED, ArchivalMonitorConstants.USER_ARCHIVAL_MONITOR);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return archiveContent;
	}
	public BooleanQuery getSubCommunities(String groupId, long companyId) 
	{
		BooleanQuery compositeQuery = null;
		try 
		{
			System.out.println("getDocuments:- ===================================================groupId:-->"+groupId);
			compositeQuery = BooleanQueryFactoryUtil.create();
			String className = Group.class.getName();
			BooleanQuery modelQuery = BooleanQueryFactoryUtil.create();
			modelQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, className);
			modelQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			modelQuery.addRangeTerm(ArchivalMonitorConstants.MODIFIED_DATE, fromArchivalCheckDate.getTime(), toArchivalCheckDate.getTime());
			compositeQuery.add(modelQuery, BooleanClauseOccur.MUST);
//			System.out.println("compositeQuery:-->"+compositeQuery.toString());
		} 
		catch (Exception e) 
		{
			LOG.error("getGroups() Error making query for DL : " + e.getMessage(), e);
			System.out.println("ArchiveContentSearch.getGroups() Error making query for DL : " + e.getMessage());
		}
		return compositeQuery;
	}
	public ArchiveContent setArchiveContent(String authorEmail, String authorName, String contentId, String contentName, String contentType, String contentUrl, String createdBy, String archivalComponentType, long groupId, Date modifiedDate, String status, String updatedBy){
		ArchiveContent archiveContent = ArchiveContentLocalServiceUtil.createArchiveContent(contentId);
		
		try {
			archiveContent.setAUTHOR_NM(authorName);
			archiveContent.setCONTENT_ID(contentId);
			archiveContent.setCONTENT_NM(contentName);
			archiveContent.setCONTENT_TYP(contentType);
			archiveContent.setCONTENT_URL_TXT(contentUrl);
			archiveContent.setCREAT_TS(currentDate);
			archiveContent.setCREAT_USR_ID(createdBy);
			archiveContent.setCONTENT_STAT_NOTIFIED_DT(currentDate);
			Date expiredDate = this.expiredDate;
			Date deletedDate = this.deletedDate;
			int deletedMonth = Integer.parseInt(PortletProps.get(ArchivalMonitorConstants.PORTLET_DELETED_MONTH));
			int expiredWeekInvalidEmail = Integer.parseInt(PortletProps.get(ArchivalMonitorConstants.PORTLET_EXPIRED_WEEK_INVALIDEMAIL));
			if(archivalComponentType.equalsIgnoreCase(ArchivalMonitorConstants.PORTLET_COMPONENT_NEWSROOM))
			{
				expiredDate = currentDate;
				deletedDate = ArchivalMonitorUtil.getNMonthLaterDate(expiredDate, deletedMonth);
			}
			if(!Validator.isEmailAddress(authorEmail)){
				authorEmail = PortletProps.get(ArchivalMonitorConstants.PORTLET_MERLIN_INBOX);
				expiredDate = ArchivalMonitorUtil.getNWeeksLaterDate(currentDate, expiredWeekInvalidEmail);
				deletedDate = ArchivalMonitorUtil.getNMonthLaterDate(expiredDate, deletedMonth);
			}
			archiveContent.setAUTHOR_EMAIL_ID(authorEmail);
			archiveContent.setCONTENT_STAT_EXPIRED_DT(expiredDate);
			archiveContent.setCONTENT_STAT_DEL_DT(deletedDate);
			archiveContent.setGRP_ID(groupId);
			archiveContent.setCONTENT_STAT_MOD_DT(modifiedDate);
			archiveContent.setCONTENT_STAT_CD(status);
			archiveContent.setLST_UPDT_TS(currentDate);
			archiveContent.setCREAT_USR_ID(updatedBy);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return archiveContent;
	}
	public ArchiveContent setWebContent(Document document, String contentType, String archivalComponentType, String groupFriendlyURL){
		ArchiveContent archiveContent = null;
		try {
			final String SOURCE_DATE_FORMAT_PATTERN  = "MM/dd/yyyy";
			final SimpleDateFormat sdfSource = new SimpleDateFormat(SOURCE_DATE_FORMAT_PATTERN, Locale.getDefault());
			long groupId = Long.valueOf(document.get(Field.GROUP_ID)).longValue();
			String contentId=document.get(Field.ENTRY_CLASS_PK);
			Calendar cal = Calendar.getInstance();
			String contentUrl = null;
//			JournalArticle journalArticle = JournalArticleLocalServiceUtil.getArticle(groupId, contentId);
			long userId = Long.valueOf(document.get(Field.USER_ID)).longValue();
			User user = UserLocalServiceUtil.getUser(userId);
			//http://localhost:8080/web/newsroom/article?id=12634
			contentUrl = portalURL + ArchivalMonitorConstants.WEB + StringPool.SLASH + groupFriendlyURL + ArchivalMonitorConstants.WEB_CONTENT_ARTICLE_URL + document.get(Field.ROOT_ENTRY_CLASS_PK);
			System.out.println("article URL>:->"+contentUrl);
			System.out.println("cal.getTime():-"+cal.getTime());
//			Date modifiedDate = sdfSource.parse(document.get(Field.MODIFIED));
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.valueOf(document.get(Field.MODIFIED)));
			System.out.println("modified Date:-->"+calendar.getTime());
			Date modifiedDate = cal.getTime();
			
			archiveContent = setArchiveContent(user.getEmailAddress(), user.getFullName(), contentId, document.get(Field.TITLE), contentType, contentUrl, ArchivalMonitorConstants.USER_ARCHIVAL_MONITOR, archivalComponentType, groupId, modifiedDate, ArchivalMonitorConstants.ARCHIVE_CONTENT_STATUS_NOTIFIED, ArchivalMonitorConstants.USER_ARCHIVAL_MONITOR);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return archiveContent;
	}
	public BooleanQuery getWebContents(long groupId, long companyId, String[] structureIds) 
	{
		Query finalQuery = null;
		BooleanQuery compositeQuery = null;
		try 
		{
			System.out.println("getWebContents:- ===================================================groupId:-->"+groupId);
			compositeQuery = BooleanQueryFactoryUtil.create();
			String className = JournalArticle.class.getName();
			BooleanQuery modelQuery = BooleanQueryFactoryUtil.create();
			modelQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, className);
			modelQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			modelQuery.addRangeTerm(ArchivalMonitorConstants.MODIFIED_DATE, fromArchivalCheckDate.getTime(), toArchivalCheckDate.getTime());
			compositeQuery.add(modelQuery, BooleanClauseOccur.MUST);
			compositeQuery.add(getStructureIDQuery(structureIds), BooleanClauseOccur.MUST);
//			System.out.println("compositeQuery:-->"+compositeQuery.toString());
			/*Hits hits;
			try {
				hits = SearchEngineUtil.search(companyId, compositeQuery, -1, -1);
	            System.out.println("Total pages:-->"+hits.getLength());
	            Document[] docs = hits.getDocs();
	            List<Document> docsList = hits.toList();
	            System.out.println("docs.length:-->"+docs.length);
	            for (Document document : docsList) {
	            	System.out.println("=====================	WebContent Journal Artical wise searching =============");
//	            	System.out.println("1)documents:->"+document.toString());
					System.out.println("2)documents name:->"+document.getUID());
					System.out.println("3)documents.get(title):->"+document.get("title"));
					System.out.println("4) fetching modified:->"+document.get(Field.MODIFIED));
					System.out.println("=====================	Ends of WebContent Journal Artical	=============");
				}
			} catch (SearchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		} 
		catch (Exception e) 
		{
			LOG.error("getWebContents() Error making query for DL : " + e.getMessage(), e);
			System.out.println("ArchivalMonitorDAO.getWebContents() Error making query for DL : " + e.getMessage());
		}
		
		return compositeQuery;
	}
	public BooleanQuery getTotalWebContents(long groupId, String[] structureIds) 
	{
		Query finalQuery = null;
		BooleanQuery compositeQuery = null;
		try 
		{
			System.out.println("getTotalWebContents:- ===================================================groupId:-->"+groupId);
			compositeQuery = BooleanQueryFactoryUtil.create();
			String className = JournalArticle.class.getName();
			BooleanQuery modelQuery = BooleanQueryFactoryUtil.create();
			modelQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, className);
			modelQuery.addRequiredTerm(Field.GROUP_ID, groupId);
//			modelQuery.addRangeTerm(ArchivalMonitorConstants.MODIFIED_DATE, fromArchivalCheckDate.getTime(), toArchivalCheckDate.getTime());
			compositeQuery.add(modelQuery, BooleanClauseOccur.MUST);
			compositeQuery.add(getStructureIDQuery(structureIds), BooleanClauseOccur.MUST);
		} 
		catch (Exception e) 
		{
			LOG.error("getTotalWebContents() Error making query for DL : " + e.getMessage(), e);
			System.out.println("ArchivalMonitorDAO.getTotalWebContents() Error making query for DL : " + e.getMessage());
		}
		
		return compositeQuery;
	}
	public ArchiveContent setDocument(Document document, String contentType, String archivalComponentType, String groupFriendlyURL){
		ArchiveContent archiveContent = null;
		try {
			final String SOURCE_DATE_FORMAT_PATTERN  = "MM/dd/yyyy";
			final SimpleDateFormat sdfSource = new SimpleDateFormat(SOURCE_DATE_FORMAT_PATTERN, Locale.getDefault());
			long groupId = Long.valueOf(document.get(Field.GROUP_ID)).longValue();
			String contentId=document.get(Field.ENTRY_CLASS_PK);
			Calendar cal = Calendar.getInstance();
			String contentUrl = null;
//			JournalArticle journalArticle = JournalArticleLocalServiceUtil.getArticle(groupId, contentId);
			long userId = Long.valueOf(document.get(Field.USER_ID)).longValue();
			User user = UserLocalServiceUtil.getUser(userId);
			//http://localhost:8080/web/newsroom/article?id=12634
			contentUrl = portalURL + ArchivalMonitorConstants.WEB + StringPool.SLASH + groupFriendlyURL + ArchivalMonitorConstants.HOME;
			System.out.println("Document URL>:->"+contentUrl);
			System.out.println("cal.getTime():-"+cal.getTime());
//			Date modifiedDate = sdfSource.parse(document.get(Field.MODIFIED));
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.valueOf(document.get(Field.MODIFIED)));
			System.out.println("modified Date:-->"+calendar.getTime());
			Date modifiedDate = cal.getTime();
			
			archiveContent = setArchiveContent(user.getEmailAddress(), user.getFullName(), contentId, document.get(Field.TITLE), contentType, contentUrl, ArchivalMonitorConstants.USER_ARCHIVAL_MONITOR, archivalComponentType, groupId, modifiedDate, ArchivalMonitorConstants.ARCHIVE_CONTENT_STATUS_NOTIFIED, ArchivalMonitorConstants.USER_ARCHIVAL_MONITOR);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return archiveContent;
	}
	public BooleanQuery getDocuments(long groupId, long companyId) 
	{
		BooleanQuery compositeQuery = null;
		try 
		{
			System.out.println("getDocuments:- ===================================================groupId:-->"+groupId);
			compositeQuery = BooleanQueryFactoryUtil.create();
			String className = DLFileEntry.class.getName();
			BooleanQuery modelQuery = BooleanQueryFactoryUtil.create();
			modelQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, className);
			modelQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			modelQuery.addRangeTerm(ArchivalMonitorConstants.MODIFIED_DATE, fromArchivalCheckDate.getTime(), toArchivalCheckDate.getTime());
			compositeQuery.add(modelQuery, BooleanClauseOccur.MUST);
//			System.out.println("compositeQuery:-->"+compositeQuery.toString());
			/*Hits hits;
			try {
				hits = SearchEngineUtil.search(companyId, compositeQuery, -1, -1);
//				renderRequest.setAttribute(SearchConstants.TOTAL_COUNT, hits.getLength());
//	            int totalPage =  hits.getLength()/SearchConstants.PER_PAGE;
	            System.out.println("Total pages:-->"+hits.getLength());
	            Document[] docs = hits.getDocs();
	            List<Document> docsList = hits.toList();
	            System.out.println("docs.length:-->"+docs.length);
	            for (Document document : docsList) {
	            	System.out.println("=====================	component wise searching =============");
//	            	System.out.println("1)documents:->"+document.toString());
					System.out.println("2)documents name:->"+document.getUID());
					System.out.println("3)documents.get(title):->"+document.get("title"));
					System.out.println("4) fetching modified:->"+document.get(Field.MODIFIED));
					System.out.println("=====================	Ends of component Artical	=============");
				}
			} catch (SearchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		} 
		catch (Exception e) 
		{
			LOG.error("getMerlin() Error making query for DL : " + e.getMessage(), e);
			System.out.println("ArchiveContentSearch.getMerlin() Error making query for DL : " + e.getMessage());
		}
		
		return compositeQuery;
	}
	public BooleanQuery getTotalDocuments(long groupId, long companyId) 
	{
		BooleanQuery compositeQuery = null;
		try 
		{
			System.out.println("getTotalDocuments:- ===================================================groupId:-->"+groupId);
			compositeQuery = BooleanQueryFactoryUtil.create();
			String className = DLFileEntry.class.getName();
			BooleanQuery modelQuery = BooleanQueryFactoryUtil.create();
			modelQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, className);
			modelQuery.addRequiredTerm(Field.GROUP_ID, groupId);
//			modelQuery.addRangeTerm(ArchivalMonitorConstants.MODIFIED_DATE, fromArchivalCheckDate.getTime(), toArchivalCheckDate.getTime());
			compositeQuery.add(modelQuery, BooleanClauseOccur.MUST);
		} 
		catch (Exception e) 
		{
			LOG.error("getTotalDocuments() Error making query for DL : " + e.getMessage(), e);
			System.out.println("ArchiveContentSearch.getTotalDocuments() Error making query for DL : " + e.getMessage());
		}
		
		return compositeQuery;
	}
	public ArchiveContent setBookmark(Document document, String contentType, String archivalComponentType, String groupFriendlyURL){
		ArchiveContent archiveContent = null;
		try {
			final String SOURCE_DATE_FORMAT_PATTERN  = "MM/dd/yyyy";
			final SimpleDateFormat sdfSource = new SimpleDateFormat(SOURCE_DATE_FORMAT_PATTERN, Locale.getDefault());
			long groupId = Long.valueOf(document.get(Field.GROUP_ID)).longValue();
			String contentId=document.get(Field.ENTRY_CLASS_PK);
			Calendar cal = Calendar.getInstance();
			String contentUrl = null;
//			JournalArticle journalArticle = JournalArticleLocalServiceUtil.getArticle(groupId, contentId);
			long userId = Long.valueOf(document.get(Field.USER_ID)).longValue();
			User user = UserLocalServiceUtil.getUser(userId);
			//http://localhost:8080/web/newsroom/article?id=12634
			contentUrl = portalURL + ArchivalMonitorConstants.WEB + StringPool.SLASH + groupFriendlyURL + ArchivalMonitorConstants.HOME;
			System.out.println("article URL>:->"+contentUrl);
			System.out.println("cal.getTime():-"+cal.getTime());
//			Date modifiedDate = sdfSource.parse(document.get(Field.MODIFIED));
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.valueOf(document.get(Field.MODIFIED)));
			System.out.println("modified Date:-->"+calendar.getTime());
			Date modifiedDate = cal.getTime();
			
			archiveContent = setArchiveContent(user.getEmailAddress(), user.getFullName(), contentId, document.get(Field.TITLE), contentType, contentUrl, ArchivalMonitorConstants.USER_ARCHIVAL_MONITOR, archivalComponentType, groupId, modifiedDate, ArchivalMonitorConstants.ARCHIVE_CONTENT_STATUS_NOTIFIED, ArchivalMonitorConstants.USER_ARCHIVAL_MONITOR);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return archiveContent;
	}
	public BooleanQuery getBookmarks(long groupId, long companyId) 
	{
		BooleanQuery compositeQuery = null;
		try 
		{
			System.out.println("getDocuments:- ===================================================groupId:-->"+groupId);
			compositeQuery = BooleanQueryFactoryUtil.create();
			String className = BookmarksEntry.class.getName();
			BooleanQuery modelQuery = BooleanQueryFactoryUtil.create();
			modelQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, className);
			modelQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			modelQuery.addRangeTerm(ArchivalMonitorConstants.MODIFIED_DATE, fromArchivalCheckDate.getTime(), toArchivalCheckDate.getTime());
			compositeQuery.add(modelQuery, BooleanClauseOccur.MUST);
//			System.out.println("compositeQuery:-->"+compositeQuery.toString());
			Hits hits;
			try {
				hits = SearchEngineUtil.search(companyId, compositeQuery, -1, -1);
//				renderRequest.setAttribute(SearchConstants.TOTAL_COUNT, hits.getLength());
//	            int totalPage =  hits.getLength()/SearchConstants.PER_PAGE;
	            System.out.println("Total pages:-->"+hits.getLength());
	            Document[] docs = hits.getDocs();
	            List<Document> docsList = hits.toList();
	            System.out.println("docs.length:-->"+docs.length);
	            for (Document document : docsList) {
	            	System.out.println("=====================	component wise searching =============");
	            	System.out.println("1)documents:->"+document.toString());
					System.out.println("2)documents name:->"+document.getUID());
					System.out.println("3)documents.get(title):->"+document.get("title"));
					System.out.println("4) fetching modified:->"+document.get(Field.MODIFIED));
					System.out.println("=====================	Ends of component Artical	=============");
				}
			} catch (SearchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (Exception e) 
		{
			LOG.error("getMerlin() Error making query for DL : " + e.getMessage(), e);
			System.out.println("ArchiveContentSearch.getMerlin() Error making query for DL : " + e.getMessage());
		}
		
		return compositeQuery;
	}
	public BooleanQuery getTotalBookmarks(long groupId, long companyId) 
	{
		BooleanQuery compositeQuery = null;
		try 
		{
			System.out.println("getTotalBookmarks:- ===================================================groupId:-->"+groupId);
			compositeQuery = BooleanQueryFactoryUtil.create();
			String className = BookmarksEntry.class.getName();
			BooleanQuery modelQuery = BooleanQueryFactoryUtil.create();
			modelQuery.addRequiredTerm(Field.ENTRY_CLASS_NAME, className);
			modelQuery.addRequiredTerm(Field.GROUP_ID, groupId);
//			modelQuery.addRangeTerm(ArchivalMonitorConstants.MODIFIED_DATE, fromArchivalCheckDate.getTime(), toArchivalCheckDate.getTime());
			compositeQuery.add(modelQuery, BooleanClauseOccur.MUST);
//			System.out.println("compositeQuery:-->"+compositeQuery.toString());
		} 
		catch (Exception e) 
		{
			LOG.error("getTotalBookmarks() Error making query for DL : " + e.getMessage(), e);
			System.out.println("ArchiveContentSearch.getTotalBookmarks() Error making query for DL : " + e.getMessage());
		}
		
		return compositeQuery;
	}
public static void temp(){
		
//		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class, PortalClassLoaderUtil.getClassLoader());
//		Criterion criterion = null;
		
		/*if(Validator.isNull(archivalCheckDate))
		criterion = RestrictionsFactoryUtil.lt("modifiedDate", archivalCheckDate); 
	if(Validator.isNull(criterion))
		dynamicQuery.add(criterion);*/
//	dynamicQuery.setProjection(ProjectionFactoryUtil.min("modifiedDate"));
	
	
	/*BooleanQuery dlQuery = BooleanQueryFactoryUtil.create();
	dlQuery.addTerms(SearchConstants._KEYWORDS_FIELDS_FOR_DL, keywords);
                dlQuery.addExactTerm(Field.TITLE, keywords);
                dlQuery.addExactTerm(SearchConstants.RESOURCE_TITLE, keywords);
                dlQuery.addExactTerm(Field.DESCRIPTION, keywords);
                dlQuery.addExactTerm(Field.ASSET_TAG_NAMES, keywords);
	if (keywords.split("\\s+").length > 1) 
	{
		String exactKeyword = "\"" + keywords + "\"";
		dlQuery.addTerms(SearchConstants._KEYWORDS_FIELDS_FOR_DL_EXACT, exactKeyword);
		dlQuery.addExactTerm(SearchConstants.RESOURCE_TITLE, exactKeyword+KEYWORD_BOOST_UP_VAL);
	}*/
		
//		modelQuery.addRequiredTerm(Field., classNameID);
		/*Calendar fromDateCalendar = CalendarFactoryUtil.getCalendar();
        fromDateCalendar.setTime(archivalCheckDate);
		fromDateCalendar.add(Calendar.DATE, -6);
		Date fromDate = fromDateCalendar.getTime();*/
		
//		modelQuery.addRangeTerm("modifiedDate", fromDate.getTime(), archivalCheckDate.getTime());
		
		/*DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Group.class);
		Criterion criterion = null;
		if(Validator.isNull(archivalCheckDate))
			criterion = RestrictionsFactoryUtil.lt("modifiedDate", archivalCheckDate); 
//			criterion = RestrictionsFactoryUtil.eq(Field.ENTRY_CLASS_NAME, className);
			criterion = RestrictionsFactoryUtil.eq(Field.ASSET_TAG_NAMES, ArchivalMonitorConstants.MERLIN_TAG_NAME);
		if(Validator.isNull(criterion))
			dynamicQuery.add(criterion);
		
		System.out.println("========= using dynamic Query	===============");
		List<Group> groups = GroupLocalServiceUtil.dynamicQuery(dynamicQuery);
		for (Group group : groups) {
			System.out.println("1) group details:->"+group.getName());
		}
		System.out.println("========= End using dynamic Query	===============");*/
//		modelQuery.addRequiredTerm(Field.GROUP_ID, 10294);
//		modelQuery.addRequiredTerm("classNameId", 10012);
//		modelQuery.addRequiredTerm(Field.ASSET_TAG_NAMES, ArchivalMonitorConstants.MERLIN_TAG_NAME);
		
//		compositeQuery.add(dlQuery, BooleanClauseOccur.MUST);
		
		
		
		/*Date dt = new Date(doc.get(Field.MODIFIED));
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String date = DATE_FORMAT.format(dt);
        System.out.println("modified date is :-->"+date);
		Map<String, Field> fields = doc.getFields();*/
//		fields.entrySet();
	}
}
