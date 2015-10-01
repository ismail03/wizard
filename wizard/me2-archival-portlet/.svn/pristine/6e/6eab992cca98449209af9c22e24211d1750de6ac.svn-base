package com.ihg.me2.archive.util;

import java.util.List;

import org.apache.log4j.Logger;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.journal.model.JournalArticle;
/***
 * 
 * @author ketan.savaliya
 *
 */
public class MerlinGroupUtil {
	
	private static Logger _log = ArchivalMonitorLoggerUtil.getLogger(MerlinGroupUtil.class);
	
		/**
		 * Method is use to check orgnization is tagged with "merlin" and custome field have value "Department"
		 * @param org
		 * @param tagName
		 * @return
		 */
		public static boolean isOrganizationMerlin(Organization org,String tagName){
			return isMerlinTag(Organization.class.getName(), org.getOrganizationId(), tagName) && isOrganziationDepartment(org);
		}
		
		/**
		 * Method is user to check Organization have custome field "Type" value equal to "Deparment"
		 * @param org
		 * @return boolean
		 */
		private static boolean isOrganziationDepartment(Organization org) {
			ExpandoBridge customField = org.getExpandoBridge();
			if(Validator.isNotNull(customField) && Validator.isNotNull(customField.getAttribute("Type"))){	
				String[] types = (String[])customField.getAttribute("Type");
				if(types.length>0){
					return types[0].equals("Department");
				}
			}
			return false;
		}

		/**
		 * This method isuser to check organization or group have tagged with given taga name 
		 * @param className : e.g. Organization.class.getName()
		 * @param classPK : organizationId/communityId
		 * @param tagName : "Merlin"
		 * @return
		 */
		private static boolean isMerlinTag(String className, long classPK,String tagName){
			try {
				AssetEntry entry = AssetEntryLocalServiceUtil.getEntry(className, classPK);
				if(Validator.isNotNull(entry))
				{
					List<AssetTag> tagList = AssetTagLocalServiceUtil.getEntryTags(entry.getEntryId());
					for (AssetTag tag : tagList)
					{
						if(tag.getName().equalsIgnoreCase(tagName))
						{
							return true;
						}
					}
				}
			} catch (NoSuchEntryException e) {
				_log.error("Tag not found for classPK  : "  + classPK + " === "+ e.getMessage());
			}catch (PortalException e) {
				_log.error("Error while check organizationID " + classPK + " is tagged with   : " + tagName + ":: " + e.getMessage(), e);
			} catch (SystemException e) {
				_log.error("While check organizationID " + classPK + " is tag with   : " + tagName + "::" + e.getMessage(), e);
			}
			return false;
		}
		
		/**
		 * This method is check Group is tagged with give tagName?
		 * @param group
		 * @param tagName
		 * @return
		 */
		public static boolean isContentFromMerlin(Group group,String tagName){
			if(group!=null){
					return isMerlinTag(Group.class.getName(), group.getGroupId(),tagName);
			}
			return false;
		}
		
		
		/**
		 * Method is use to get Group Object based on group id
		 * @param groupId
		 * @return
		 */
		public static Group getGroupById(long groupId){
			try {
				return GroupLocalServiceUtil.getGroup( groupId);
			} catch (PortalException e) {
				_log.error("Error while get Group : " + e.getMessage(),e);
			} catch (SystemException e) {
				_log.error("Error from getGroupById : " + e.getMessage(),e);
			}
			return null;
		}
		
		/**
		 * this method is user for journalArticle have given structureId ?
		 * @param article
		 * @param structureId
		 * @return
		 */
		public static boolean isArticleMerlin(JournalArticle article,String structureId){
			String[] structureIds = structureId.split(StringPool.COMMA);
			for(String sid : structureIds){
				if(article.getStructureId().equalsIgnoreCase(sid)){
					return true;
				}
			}
			return false;
		}
		
		/**
		 * This method is user for which type of document based on extension of dlfileEntry
		 * @param dlFileEntry
		 * @return
		 */
		public static String getFileResourType(DLFileEntry dlFileEntry){
			if(ArrayUtil.contains(DOC, dlFileEntry.getExtension())){
				return "DOC";
			} else if (ArrayUtil.contains(PDF, dlFileEntry.getExtension())){
				return "PDF";
			} else if (ArrayUtil.contains(PPT, dlFileEntry.getExtension())){
				return "PPT";
			} else if (ArrayUtil.contains(XLS, dlFileEntry.getExtension())){
				return "XLS";
			} else if (ArrayUtil.contains(IMG, dlFileEntry.getExtension())){
				return "IMG";
			} else if (ArrayUtil.contains(VID, dlFileEntry.getExtension())){
				return "VID";
			} else if (ArrayUtil.contains(AUD, dlFileEntry.getExtension())){
				return "AUD";
			} else{
				return "OTH";
			}
		}
		
		/**
		 * This method is use to get Organization object based on organiationid
		 * @param organizationId
		 * @return
		 */
		public static Organization getOrganizationById(long organizationId){
			try {
				return OrganizationLocalServiceUtil.getOrganization( organizationId);
			} catch (PortalException e) {
				_log.error("Error while get organizationById : " + e.getMessage(),e);
			} catch (SystemException e) {
				_log.error("Error from getOrganizationById : " + e.getMessage(),e);
			}
			return null;
		}

		
		private static String[] DOC = {"doc", "docx", "odt"};
		private static String[] PPT = {"ppt", "pptx"};
		private static String[] XLS = {"xls", "xlsx"};
		private static String[] PDF = {"pdf"};
		private static String[] IMG = {"jpg", "png", "jpeg", "gif", "bmp"};
		private static String[] VID = {"mp4", "flv", "mv", "swf"};
		private static String[] AUD  = {"wma", "mp3"};
}
