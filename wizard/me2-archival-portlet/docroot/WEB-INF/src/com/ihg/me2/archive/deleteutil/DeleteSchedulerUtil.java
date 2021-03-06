package com.ihg.me2.archive.deleteutil;


import com.ihg.me2.archive.service.model.ArchiveContent;
import com.ihg.me2.archive.service.service.ArchiveContentLocalServiceUtil;
import com.liferay.documentlibrary.service.DLLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import sun.management.resources.agent;

public class DeleteSchedulerUtil {
	private static Logger logger = IndexerLogger
			.getLogger(DeleteSchedulerUtil.class);

	public static Set<String> newHashSet(String... strings) {
		HashSet<String> set = new HashSet<String>();

		for (String s : strings) {
			set.add(s.toLowerCase().trim());
		}
		return set;
	}

	public static int flag = 0; // flag used for tracking whether content is deleted by
							// author OR action is taken OR action is not taken

	public static boolean isWebContent(String type) {

		if (type == null || type.trim().equals("")) {
			return false;
		}
		if (newHashSet(SchedulerConstant.WEBCONTENTS).contains(
				type.toLowerCase().trim())) {
			return true;
		}

		return false;

	}

	public static boolean isMedia(String type) {
		if (type == null || type.trim().equals("")) {
			return false;
		}

		if (newHashSet(SchedulerConstant.DOC_IMAGE_VEDIO).contains(
				type.toLowerCase().trim())) {
			return true;
		}

		return false;
	}
	
	public static boolean isBookmark(String type) {
		if (type == null || type.trim().equals("")) {
			return false;
		}

		if (newHashSet(SchedulerConstant.BOOKMARKS).contains(
				type.toLowerCase().trim())) {
			return true;
		}

		return false;
	}

	public static boolean isCommunity(String type) {
		if (type == null || type.trim().equals("")) {
			return false;
		}

		if (newHashSet(SchedulerConstant.COMMUNITIES).contains(
				type.toLowerCase().trim())) {
			return true;
		}

		return false;

	}

	public static Date getModifiedDate(String type, Long id) {

		if (type == null || id == null) {
			throw new IllegalArgumentException();
		}

		Date modifieddate = null;
		if (type.trim().equalsIgnoreCase(
				SchedulerConstant.WEBCONTENT_IDENTIFIER)) {

			try {
				modifieddate = JournalArticleLocalServiceUtil
						.getJournalArticle(id).getModifiedDate();
System.out.println(modifieddate.toString());
			} catch (PortalException e) {
				logger.info(" web content with id " + id
						+ " has been deleted from the portal",e);
				flag = 1; // indicates content is deleted by author
				

			} catch (SystemException e) {
				logger.error(
						"Error occured while getting modified date for Webcontent ::"
								+ id, e);
				e.printStackTrace();
			}
		}

		if (type.trim().equalsIgnoreCase(SchedulerConstant.MEDIA_IDENTIFIER)) {
			try {
				modifieddate = DLFileEntryLocalServiceUtil.getDLFileEntry(id)
						.getModifiedDate();
			} catch (PortalException e) {
				logger.info("media with id " + id
						+ " has been deleted from the portal");
				flag = 1;// indicates content is deleted by owner
				e.printStackTrace();
			} catch (SystemException e) {
				logger.error(
						"Error occured while getting Modified Date for Media :: "
								+ id, e);

			}
		}
		
		if (type.trim().equalsIgnoreCase(SchedulerConstant.BOOKMARK_IDENTIFIER)) {
			try {
				modifieddate = BookmarksEntryLocalServiceUtil.getBookmarksEntry(id)
						.getModifiedDate();
			} catch (PortalException e) {
				logger.info("Bookmark with id " + id
						+ " has been deleted from the portal");
				flag = 1;// indicates content is deleted by owner
				e.printStackTrace();
			} catch (SystemException e) {
				logger.error(
						"Error occured while getting Modified Date for Bookmark :: "
								+ id, e);

			}
		}


		return modifieddate;
	}

	public static boolean isModifiedInExpiredStatus(Long id, String status,
			Date expireddate, Date deletedate, String processtype) {

		if (id == null || status == null || expireddate == null
				|| deletedate == null) {
			return false;
		}
		if (status.trim().equals("") || processtype.trim().equals("")
				|| !(status.equalsIgnoreCase(SchedulerConstant.STATUS_EXPIRED))) {
			return false;
		}
		Date modifieddate = getModifiedDate(processtype, id);

		if (modifieddate != null) {

			if ((modifieddate.equals(expireddate) || modifieddate
					.after(expireddate)) && modifieddate.before(deletedate)) {
				logger.info("content has been modified in Expired period");
				return true;
			} else {
				flag = 2; // not modified in expired period / action is not
							// taken
				return false;
			}
		}

		return false;
	}

	public static void deleteFromArchiveContent(String id) {

		try {
			ArchiveContentLocalServiceUtil.deleteArchiveContent(id);
		} catch (PortalException e) {
			logger.error(" Archive content ::" + id + "  cannot be deleted", e);
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			logger.error(
					"error occured while deleting from Archive content id ::"
							+ id, e);
		}

	}

	

	public static List<ArchiveContent> getCommuntiyWebcontent(
			List<ArchiveContent> webcontent, Long communityid) {
		if (webcontent == null || communityid == null) {
			return null;
		}
		List<ArchiveContent> communtiywebcontent = new ArrayList<ArchiveContent>();

		for (ArchiveContent a1 : webcontent) {
			if (a1.getGRP_ID() == communityid) {
				communtiywebcontent.add(a1);
			}
		}

		return communtiywebcontent;

	}

	public static List<ArchiveContent> getCommuntiyMedia(
			List<ArchiveContent> media, Long communityid) {

		if (media == null || communityid == null) {
			return null;
		}
		List<ArchiveContent> communtiymedia = new ArrayList<ArchiveContent>();

		for (ArchiveContent a1 : media) {
			if (a1.getGRP_ID() == communityid) {
				communtiymedia.add(a1);
			}
		}

		return communtiymedia;

	}

	public static boolean isAnyWebContentUpdated(List<ArchiveContent> webcontent) {
		if (webcontent == null || webcontent.size() == 0) {
			return false;
		}
		for (ArchiveContent a : webcontent) {
			if (a.getCONTENT_STAT_CD().equalsIgnoreCase(SchedulerConstant.STATUS_UPDATE)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isAnyMediaUpdated(List<ArchiveContent> media) {
		if (media == null || media.size() == 0) {
			return false;
		}
		for (ArchiveContent a : media) {
			if (a.getCONTENT_STAT_CD().equalsIgnoreCase(SchedulerConstant.STATUS_UPDATE)) {
				return true;
			}
		}
		return false;
	}

	public static void updateArchive(ArchiveContent a) {
		try {
			a.setLST_UPDT_TS(new Date());
			ArchiveContentLocalServiceUtil.updateArchiveContent(a);
		} catch (SystemException e) {
			logger.error(
					"Error occured while updating Archive table :: "
							+ a.getCONTENT_ID(), e);
		}
	}

	public static boolean deleteWebContentFromPortal(Long journalarticleid) {
		if (journalarticleid == null) {
			throw new IllegalArgumentException();
		}
		try {
			JournalArticle j = JournalArticleLocalServiceUtil
					.getJournalArticle(journalarticleid);
			AssetEntry a = AssetEntryLocalServiceUtil.getEntry(j.getGroupId(),
					j.getUuid());
			AssetEntryUtil.clearAssetTags(a.getClassPK());
			JournalArticleLocalServiceUtil.deleteJournalArticle(j);
			return true;
		} catch (PortalException e) {
			logger.info("Error while deleting web content whose id is :: "
					+ journalarticleid, e);

			return false;
		} catch (SystemException e) {
			logger.error("Error while deleting web content" + journalarticleid,
					e);
			return false;
		}

	}

	public static boolean deleteMediaFromPortal(Long entryid) {
		if (entryid == null) {
			throw new IllegalArgumentException();
		}
		try {
			DLFileEntry dl = DLFileEntryLocalServiceUtil
					.getDLFileEntry(entryid);
			// deletes from file system
			DLLocalServiceUtil.deleteFile(dl.getCompanyId(), null,
					dl.getRepositoryId(), dl.getName());

			AssetEntry a = AssetEntryLocalServiceUtil.getEntry(dl.getGroupId(),
					dl.getUuid());
			// clears tags associated with media
			AssetEntryUtil.clearAssetTags(a.getClassPK());
			//
			AssetEntryLocalServiceUtil.deleteEntry(ClassNameLocalServiceUtil
					.getClassName(a.getClassNameId()).getClassName(), entryid);
			// delete from DlFileEntry table
			DLFileEntryLocalServiceUtil.deleteDLFileEntry(entryid);
			logger.info("Media deleted");
			return true;
		} catch (PortalException e) {
			logger.error(
					"Could not delete media  " + entryid + "::"
							+ e.getMessage(), e);
			return false;
		} catch (SystemException e) {
			logger.error("Error occured while deleting media from portal : :"
					+ entryid + "::" + e.getMessage(), e);
			return false;
		}
	}
	public static boolean deleteBookmarkFromPortal(Long entryid) {
		if (entryid == null) {
			throw new IllegalArgumentException();
		}
		try {
			BookmarksEntry bookmarksEntry = BookmarksEntryLocalServiceUtil
					.getBookmarksEntry(entryid);
			
			AssetEntry a = AssetEntryLocalServiceUtil.getEntry(bookmarksEntry.getGroupId(),
					bookmarksEntry.getUuid());
			// clears tags associated with media
			AssetEntryUtil.clearAssetTags(a.getClassPK());
			//
			AssetEntryLocalServiceUtil.deleteEntry(ClassNameLocalServiceUtil
					.getClassName(a.getClassNameId()).getClassName(), entryid);
			// delete from BoomarksEntry table
			BookmarksEntryLocalServiceUtil.deleteBookmarksEntry(entryid);
			logger.info("Bookmark deleted :: "+entryid);
			return true;
		} catch (PortalException e) {
			logger.error(
					"Could not delete Bookmark  " + entryid + "::"
							+ e.getMessage(), e);
			return false;
		} catch (SystemException e) {
			logger.error("Error occured while deleting Bookamrk from portal : :"
					+ entryid + "::" + e.getMessage(), e);
			return false;
		}
	}

	public static void removeExpiredTag(ArchiveContent a, String type) {
		if (type.trim().equalsIgnoreCase(SchedulerConstant.MEDIA_IDENTIFIER)) {

			try {
				DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil
						.getFileEntry(Long.parseLong(a.getCONTENT_ID()));

				AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
						DLFileEntry.class.getName(),
						dlFileEntry.getFileEntryId());

				int noOfTags = assetEntry.getTagNames().length;
				String[] tagNames = {};
				Set<String> tags = null;
				if (noOfTags > 0) {

					tags = newHashSet(assetEntry.getTagNames());
					if (tags.contains(SchedulerConstant.STATUS_EXPIRED)) {
						tags.remove(SchedulerConstant.STATUS_EXPIRED);
					}
					int i = 0;
					for (String s : tags) {
						tagNames[i++] = s;
					}
				}

				AssetEntryLocalServiceUtil.updateEntry(dlFileEntry.getUserId(),
						dlFileEntry.getGroupId(), DLFileEntry.class.getName(),
						dlFileEntry.getFileEntryId(), assetEntry.getCategoryIds(), tagNames);
			} catch (PortalException e) {
				logger.error(
						"Error While getting DlfileEntry ::"
								+ a.getCONTENT_ID() + " not found error "
								+ e.getMessage(), e);
			} catch (SystemException e) {
				logger.error(
						"Error while removing expired tag" + e.getMessage(), e);
			}
		}
		if (type.trim().equalsIgnoreCase(SchedulerConstant.COMMUNITY_IDENTIFIER)){
			try {
				Group g = GroupLocalServiceUtil.getGroup(Long.parseLong(a.getCONTENT_ID()));
				AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(Group.class.getName(), g.getGroupId());
				int noOfTags = assetEntry.getTagNames().length;
				String[] tagNames = {};
				Set<String> tags = null;
				if (noOfTags > 0) {
					tags = newHashSet(assetEntry.getTagNames());
					if (tags.contains(SchedulerConstant.STATUS_EXPIRED)) {
						tags.remove(SchedulerConstant.STATUS_EXPIRED);
					}
					int i = 0;
					
					tagNames = new String[tags.size()];
					for (String s : tags) {						
					tagNames[i++] = s;
						}
				}			
				AssetEntryLocalServiceUtil.updateEntry(g.getCreatorUserId(), g.getGroupId(),assetEntry.getClassName(),assetEntry.getClassPK(), assetEntry.getCategoryIds(), tagNames);
				logger.info("Expired tag removed for group ::"+ a.getCONTENT_NM());
			} catch (PortalException e) {
				logger.error("error occured  while removing expired tag for content :: "+a.getCONTENT_NM()+e.getMessage(),e);
			} catch (SystemException e) {
				logger.error("error occured while removing expired tag "+e.getMessage(),e);
			}
		}

		if (type.trim().equalsIgnoreCase(SchedulerConstant.BOOKMARK_IDENTIFIER)){
			try {
				BookmarksEntry bookMark = BookmarksEntryLocalServiceUtil.getBookmarksEntry(Long.parseLong(a.getCONTENT_ID()));
				AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(BookmarksEntry.class.getName(), bookMark.getGroupId());
				int noOfTags = assetEntry.getTagNames().length;
				String[] tagNames = {};
				Set<String> tags = null;
				if (noOfTags > 0) {
					tags = newHashSet(assetEntry.getTagNames());
					if (tags.contains(SchedulerConstant.STATUS_EXPIRED)) {
						tags.remove(SchedulerConstant.STATUS_EXPIRED);
					}
					int i = 0;
					
					tagNames = new String[tags.size()];
					for (String s : tags) {						
					tagNames[i++] = s;
						}
				}			
				AssetEntryLocalServiceUtil.updateEntry(bookMark.getUserId(),bookMark.getGroupId(),assetEntry.getClassName(),assetEntry.getClassPK(), assetEntry.getCategoryIds(), tagNames);
				logger.info("Expired tag removed for Bookmark ::"+ a.getCONTENT_NM());
			} catch (PortalException e) {
				logger.error("error occured  while removing expired tag for content :: "+a.getCONTENT_NM()+e.getMessage(),e);
			} catch (SystemException e) {
				logger.error("error occured while removing expired tag "+e.getMessage(),e);
			}
		}

	}
}
