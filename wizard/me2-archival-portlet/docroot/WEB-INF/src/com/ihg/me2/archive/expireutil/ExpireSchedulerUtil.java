package com.ihg.me2.archive.expireutil;

import com.ihg.me2.archive.service.model.ArchiveContent;
import com.ihg.me2.archive.service.service.ArchiveContentLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class ExpireSchedulerUtil  {
	private final static Logger log = IndexerLogger
			.getLogger(ExpireSchedulerUtil.class);

	static List<ArchiveContent> webContetList = new ArrayList<ArchiveContent>();
	static List<ArchiveContent> documentList = new ArrayList<ArchiveContent>();
	static List<ArchiveContent> bookMarkList = new ArrayList<ArchiveContent>();
	static List<ArchiveContent> communityList = new ArrayList<ArchiveContent>();
	static List<ArchiveContent> deletedFromPortalList = new ArrayList<ArchiveContent>();

	public static void separateContents(
			List<ArchiveContent> checkExpriedArchives) throws PortalException,
			SystemException {
		SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");

		JournalArticle journalArticle = null;
		DLFileEntry dlFileEntry = null;
		BookmarksEntry bookMarksEntry = null;
		Group group = null;
		try {
			for (ArchiveContent archive : checkExpriedArchives) {
				if (!archive.getCONTENT_ID().equals(null)) {
					int id = Integer.parseInt(archive.getCONTENT_ID());

					if (ExpireSchedulerUtil.newHashSet(
							SchedulerConstant.WEBCONTENTS).contains(
							archive.getCONTENT_TYP().toLowerCase())) {
						journalArticle = getJournalArticleById(id);
						if (Validator.isNotNull(journalArticle)) {
							archive.setCONTENT_STAT_MOD_DT(formatDate
									.parse(formatDate.format(journalArticle
											.getModifiedDate())));
							webContetList.add(archive);
						} else {
							deletedFromPortalList.add(archive);
						}
						continue;
					}

					if (ExpireSchedulerUtil.newHashSet(
							SchedulerConstant.DOCUMENTS).contains(
							archive.getCONTENT_TYP().toLowerCase())) {
						dlFileEntry = getDlFileEntryById(id);

						if (Validator.isNotNull(dlFileEntry)) {
							archive.setCONTENT_STAT_MOD_DT(formatDate
									.parse(formatDate.format(dlFileEntry
											.getModifiedDate())));
							documentList.add(archive);
						} else {
							deletedFromPortalList.add(archive);
						}
						continue;
					}

					if (ExpireSchedulerUtil.newHashSet(
							SchedulerConstant.BOOKMARKS).contains(
							archive.getCONTENT_TYP().toLowerCase())) {
						bookMarksEntry = getBookMarksEntryById(id);

						if (Validator.isNotNull(bookMarksEntry)) {
							archive.setCONTENT_STAT_MOD_DT(formatDate
									.parse(formatDate.format(bookMarksEntry
											.getModifiedDate())));
							bookMarkList.add(archive);
						} else {
							deletedFromPortalList.add(archive);
						}
						continue;
					}

					if (ExpireSchedulerUtil.newHashSet(
							SchedulerConstant.COMMUNITIES).contains(
							archive.getCONTENT_TYP().toLowerCase())) {
						group = getGroupById(id);
						if (Validator.isNotNull(group)) {
							communityList.add(archive);
						} else {
							deletedFromPortalList.add(archive);
						}
						continue;
					}
				}
			}
		} catch (ParseException e) {
			log.info("Not able to parse the given date");
			log.error(e);
			e.printStackTrace();
		}
		if (webContetList.size() > 0) {
			expireWebcontents(webContetList);
		}
		if (documentList.size() > 0) {
			expireDocuments(documentList);
		}
		if (bookMarkList.size() > 0) {
			expireBookMarks(bookMarkList);
		}
		if (communityList.size() > 0) {
			expireCommunities(communityList);
		}
		if (deletedFromPortalList.size() > 0) {
			changeStatus(deletedFromPortalList);
		}
	}

	public static JournalArticle getJournalArticleById(int id) {
		JournalArticle journalArticle = null;
		try {
			journalArticle = JournalArticleLocalServiceUtil
					.getJournalArticle(id);
		} catch (SystemException e) {
			log.info("Error while retrieving Journal Article from Database");
			log.error(e);
		} catch (PortalException e) {
			log.info("Error while retrieving Journal Article from Database");
			log.error(e);
		}
		return journalArticle;
	}

	public static DLFileEntry getDlFileEntryById(int fileEntryId) {
		DLFileEntry dLFileEntry = null;
		try {
			dLFileEntry = DLFileEntryLocalServiceUtil
					.getDLFileEntry(fileEntryId);
		} catch (SystemException e) {
			log.info("Error while retrieving DlFileEntry from Database");
			log.error(e);
		} catch (PortalException e) {
			log.info("Error while retrieving DlFileEntry from Database");
			log.error(e);
		}
		return dLFileEntry;
	}

	public static BookmarksEntry getBookMarksEntryById(long bookMarksEntryId) {
		BookmarksEntry bookMarksEntry = null;
		try {
			bookMarksEntry = BookmarksEntryLocalServiceUtil
					.getBookmarksEntry(bookMarksEntryId);
		} catch (PortalException e) {
			log.info("Error while retrieving DlFileEntry from Database");
			log.error(e);
		} catch (SystemException e) {
			log.info("Error while retrieving DlFileEntry from Database");
			log.error(e);
		}
		return bookMarksEntry;
	}

	public static Group getGroupById(int groupId) {
		Group group = null;
		try {
			group = GroupLocalServiceUtil.getGroup(groupId);
		} catch (PortalException e) {
			log.info("Error while retrieving Community from Database");
			log.error(e);
		} catch (SystemException e) {
			log.info("Error while retrieving Community from Database");
			log.error(e);
		}
		return group;
	}

	// To update expired Journal articles in Archive Content
	public static void expireWebcontents(List<ArchiveContent> archives) {
		for (ArchiveContent archive : archives) {
			Date modifiedDate = archive.getCONTENT_STAT_MOD_DT();
			Date expiredDate = archive.getCONTENT_STAT_EXPIRED_DT();
			Date notifiedDate = archive.getCONTENT_STAT_NOTIFIED_DT();

			// if the Journal Article modified date is between the
			// notified and
			// expired date then change the status to Updated and also
			// update the modified date in the Archive
			// Content table.
			try {
				if ((modifiedDate.before(expiredDate) || modifiedDate
						.equals(expiredDate))
						&& (modifiedDate.after(notifiedDate) || modifiedDate
								.equals(notifiedDate))) {
					ArchiveContentLocalServiceUtil
							.deleteArchiveContent(archive);
					// Updating the webcontent list which will help to expire
					// community
					archive.setCONTENT_STAT_CD(SchedulerConstant.UPDATED);
				} else {
					archive.setCONTENT_STAT_CD(SchedulerConstant.EXPIRED);
					archive.setLST_UPDT_TS(new Date());
					archive.setLST_UPDT_USR_ID("1");
					ArchiveContentLocalServiceUtil
							.updateArchiveContent(archive);
					if (!(ExpireSchedulerUtil
							.newHashSet(SchedulerConstant.DONT_SEND_MAIL_COMPONENTS)
							.contains(archive.getCONTENT_TYP().toLowerCase()))) {
						ExpireSchedulerUtil.sendMail(
								SchedulerConstant.MAIL_TMPL_PATH, archive);
					}
				}
			} catch (SystemException e) {
				log.info("Error while updating data in the Database");
				log.error(e);
			}

		}
	}

	// To update expired Document in Archive Content and also tag documents with
	// the Expired keyword

	public static void expireDocuments(List<ArchiveContent> archives) {
		for (ArchiveContent archive : archives) {
			Date modifiedDate = archive.getCONTENT_STAT_MOD_DT();
			Date expiredDate = archive.getCONTENT_STAT_EXPIRED_DT();
			Date notifiedDate = archive.getCONTENT_STAT_NOTIFIED_DT();

			try {
				if ((modifiedDate.before(expiredDate) || (modifiedDate
						.equals(expiredDate)))
						&& (modifiedDate.after(notifiedDate) || (modifiedDate
								.equals(notifiedDate)))) {
					ArchiveContentLocalServiceUtil
							.deleteArchiveContent(archive);
					archive.setCONTENT_STAT_CD(SchedulerConstant.UPDATED);
				} else {
					DLFileEntry dlFileEntry = null;

					int id = Integer.parseInt(archive.getCONTENT_ID());
					dlFileEntry = getDlFileEntryById(id);
					AssetEntry assetEntry = AssetEntryLocalServiceUtil
							.getEntry(DLFileEntry.class.getName(),
									dlFileEntry.getFileEntryId());
					if (!assetEntry.equals(null)) {

						int noOfTags = assetEntry.getTagNames().length;
						String[] tagList = assetEntry.getTagNames();
						String[] tagNames = new String[++noOfTags];

						if (tagList.length > 0) {
							for (int i = 0; i < tagList.length; i++) {
								tagNames[i] = tagList[i];
							}
						}
						tagNames[noOfTags - 1] = SchedulerConstant.EXPIRED;

						AssetEntryLocalServiceUtil.updateEntry(
								dlFileEntry.getUserId(),
								dlFileEntry.getGroupId(),
								DLFileEntry.class.getName(),
								dlFileEntry.getFileEntryId(), null, tagNames);
						archive.setCONTENT_STAT_CD(SchedulerConstant.EXPIRED);
						archive.setLST_UPDT_TS(new Date());
						ArchiveContentLocalServiceUtil
								.updateArchiveContent(archive);

						if (!(ExpireSchedulerUtil
								.newHashSet(SchedulerConstant.DONT_SEND_MAIL_COMPONENTS)
								.contains(archive.getCONTENT_TYP()
										.toLowerCase()))) {
							ExpireSchedulerUtil.sendMail(
									SchedulerConstant.MAIL_TMPL_PATH, archive);
						}
					}
				}
			} catch (SystemException e) {
				log.info("Error while updating data in the Database");
				log.error(e);
			} catch (PortalException e) {
				log.info("Error while updating data in the Database");
				log.error(e);
			}
		}
	}

	public static void expireBookMarks(List<ArchiveContent> archives) {
		for (ArchiveContent archive : archives) {
			Date modifiedDate = archive.getCONTENT_STAT_MOD_DT();
			Date expiredDate = archive.getCONTENT_STAT_EXPIRED_DT();
			Date notifiedDate = archive.getCONTENT_STAT_NOTIFIED_DT();

			try {
				if ((modifiedDate.before(expiredDate) || (modifiedDate
						.equals(expiredDate)))
						&& (modifiedDate.after(notifiedDate) || (modifiedDate
								.equals(notifiedDate)))) {
					ArchiveContentLocalServiceUtil
							.deleteArchiveContent(archive);
					archive.setCONTENT_STAT_CD(SchedulerConstant.UPDATED);
				} else {
					BookmarksEntry bookMarksEntry = null;

					int id = Integer.parseInt(archive.getCONTENT_ID());
					bookMarksEntry = getBookMarksEntryById(id);
					AssetEntry assetEntry = AssetEntryLocalServiceUtil
							.getEntry(BookmarksEntry.class.getName(),
									bookMarksEntry.getPrimaryKey());
					if (!assetEntry.equals(null)) {

						int noOfTags = assetEntry.getTagNames().length;
						String[] tagList = assetEntry.getTagNames();
						String[] tagNames = new String[++noOfTags];

						if (tagList.length > 0) {
							for (int i = 0; i < tagList.length; i++) {
								tagNames[i] = tagList[i];
							}
						}
						tagNames[noOfTags - 1] = SchedulerConstant.EXPIRED;

						AssetEntryLocalServiceUtil.updateEntry(
								bookMarksEntry.getUserId(),
								bookMarksEntry.getGroupId(),
								BookmarksEntry.class.getName(),
								bookMarksEntry.getPrimaryKey(), null, tagNames);
						archive.setCONTENT_STAT_CD(SchedulerConstant.EXPIRED);
						archive.setLST_UPDT_TS(new Date());
						ArchiveContentLocalServiceUtil
								.updateArchiveContent(archive);

						if (!(ExpireSchedulerUtil
								.newHashSet(SchedulerConstant.DONT_SEND_MAIL_COMPONENTS)
								.contains(archive.getCONTENT_TYP()
										.toLowerCase()))) {
							ExpireSchedulerUtil.sendMail(
									SchedulerConstant.MAIL_TMPL_PATH, archive);
						}
					}
				}
			} catch (SystemException e) {
				log.info("Error while updating data in the Database");
				log.error(e);
			} catch (PortalException e) {
				log.info("Error while updating data in the Database");
				log.error(e);
			}

		}
	}

	// To update expired Community in Archive Content and also tag community
	// with the Expired keyword

	public static void expireCommunities(List<ArchiveContent> archives) {
		for (ArchiveContent archive : archives) {

			Group group = null;
			// get archive content id
			String contentId = archive.getCONTENT_ID();
			int groupId = Integer.parseInt(contentId);
			group = getGroupById(groupId);
			try {
				AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
						group.getClassName(), group.getGroupId());

				// If any of the content status of community is "Updated" then
				// community status get changed to "Updated".

				List<ArchiveContent> updatedWebContents = new ArrayList<ArchiveContent>();
				if (webContetList.size() > 0) {
					for (ArchiveContent webContent : webContetList) {
						if (webContent.getGRP_ID() == groupId
								&& webContent.getCONTENT_STAT_CD().equals(
										SchedulerConstant.UPDATED)) {
							updatedWebContents.add(webContent);
						}
					}
				}

				List<ArchiveContent> updatedDocuments = new ArrayList<ArchiveContent>();
				if (documentList.size() > 0) {
					for (ArchiveContent document : documentList) {
						if (document.getGRP_ID() == groupId
								&& document.getCONTENT_STAT_CD().equals(
										SchedulerConstant.UPDATED)) {
							updatedDocuments.add(document);
						}
					}
				}

				if (updatedWebContents.size() > 0
						|| updatedDocuments.size() > 0) {

					ArchiveContentLocalServiceUtil
							.deleteArchiveContent(archive);
					archive.setCONTENT_STAT_CD(SchedulerConstant.UPDATED);
				} else {
					int noOfTags = assetEntry.getTagNames().length;
					String[] tagList = assetEntry.getTagNames();
					String[] tagNames = new String[++noOfTags];
					if (tagList.length > 0) {
						for (int i = 0; i < tagList.length; i++) {
							tagNames[i] = tagList[i];
						}
					}
					tagNames[noOfTags - 1] = SchedulerConstant.EXPIRED;

					if (!assetEntry.equals(null)) {
						AssetEntryLocalServiceUtil.updateEntry(
								group.getCreatorUserId(),
								assetEntry.getGroupId(),
								assetEntry.getClassName(), group.getGroupId(),
								null, tagNames);
						archive.setCONTENT_STAT_CD(SchedulerConstant.EXPIRED);
						archive.setLST_UPDT_TS(new Date());
						ArchiveContentLocalServiceUtil
								.updateArchiveContent(archive);
						ExpireSchedulerUtil.sendMail(
								SchedulerConstant.MAIL_TMPL_PATH, archive);
					}
				}

			} catch (PortalException e) {
				log.info("Error while updating data in the Database");
				log.error(e);
			} catch (SystemException e) {
				log.info("Error while updating data in the Database");
				log.error(e);
			}
			 new ReIndexer().reIndex(group);
		}
	}

	public static void changeStatus(List<ArchiveContent> archives) {
		for (ArchiveContent archive : archives) {
			archive.setCONTENT_STAT_CD(SchedulerConstant.DELETEDFROMPORTAL);
			archive.setLST_UPDT_TS(new Date());
			archive.setLST_UPDT_USR_ID("1");
			try {
				ArchiveContentLocalServiceUtil.updateArchiveContent(archive);
			} catch (SystemException e) {
				log.info("Error while updating data in the Database");
				log.error(e);
			}

		}
	}

	public static Set<String> newHashSet(String... strings) {
		HashSet<String> set = new HashSet<String>();

		for (String s : strings) {
			set.add(s.toLowerCase().trim());
		}
		return set;
	}

	public static void sendMail(String tmplPath, ArchiveContent a) {
		Message m = new Message();
		m.setPayload(a);
		Map<String, Object> data = new java.util.HashMap<String, Object>();
		data.put("tmpl", tmplPath);
		m.setValues(data);
		MessageBusUtil.sendMessage("destinationBus", m);
	}
}
