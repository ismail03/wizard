package com.ihg.me2.archive.util;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface ArchivalMonitorConstants {
        
		
		String CONTENT_TYPE_NEWSROOM_WEB_CONTENT = "News";
		String CONTENT_TYPE_DEPARTMENT_WEB_CONTENT = "Dept_hot_topics";
		String CONTENT_TYPE_DEPARTMENT_DOCUMENTS = "Dept_resource";
		String CONTENT_TYPE_DEPARTMENT_BOOKMARK = "Dept_links";
		String CONTENT_TYPE_INITIATIVES_COMMUNITY = "initiative";
		String CONTENT_TYPE_INITIATIVES_BOOKMARK = "Init_links";
		String CONTENT_TYPE_INITIATIVES_WEB_CONTENT = "Init_hot_topics";
		String CONTENT_TYPE_INITIATIVES_DOCUMENTS = "Init_resource";
		String CONTENT_TYPE_CONFERENCE_COMMUNITY = "conference";
		String CONTENT_TYPE_CONFERENCE_WEB_CONTENT = "Conf_news";
		String CONTENT_TYPE_CONFERENCE_DOCUMENTS = "Conf_resource";
		String CONTENT_TYPE_APPLICATION_WEB_CONTENT = "apps";
		String[] STRUCTURE_ID_NEWSROOM_WEB_CONTENT = {"AWARDS", "EVENTS", "FEATURES", "LATEST_STORIES", "MOVERS_&_SHAKERS", "PRESS_RELEASE", "RESULTS", "SUBMIT_YOUR_STORY"};
		String[] STRUCTURE_ID_DEPARTMENT_WEB_CONTENT = {"HOT_TOPICS_DETAILS"};
		String[] STRUCTURE_ID_APPLICATION_WEB_CONTENT = {"APPLICATION"};
		String[] STRUCTURE_ID_INITIATIVES_WEB_CONTENT = {"ALL_BULLETINS_DETAILS"};
		String[] STRUCTURE_ID_CONFERENCE_WEB_CONTENT = {"CONF_AGENDA_CONTENT","CONF_ALL_PHOTOS","CONF_AWARD","CONF_AWARDS_RICHTEXT","CONF_AWARD_SIDE_INFO_BOX",
		        "CONF_CONTACT_US","CONF_EVENT_DETAILS","CONF_EVENT_SESSION","CONF_HEADER","CONF_LIVE_FEED","CONF_NEWS","CONF_PAGE_HEADER",
		        "CONF_PARTNER_DETAIL","CONF_PHOTO_CAROUSEL","CONF_POSTEVENT_RICHTEXT","CONF_PRESS_RELEASES","CONF_REGISTRATION_IMAGE",
		        "CONF_REGISTRATION_RICHTEXT","CONF_SIDE_INFO_BOX","CONF_SPEAKERSBIO","ONF_USEFUL_LINKS","CONF_VIDEO","CONF_VIDEO_COMING_SOON",
		        "CONF_VIDEO_LIBRARY"};
		String Field_STRUCTURE_ID = "structureId";
		String MODIFIED_DATE = "modifiedDate";
		
		String ARCHIVE_CONTENT_STATUS_NOTIFIED = "Notified";
		String USER_ARCHIVAL_MONITOR = "ArchivalMonitor";
		String WEB_CONTENT_ARTICLE_URL = StringPool.SLASH + "article?id=";
		String ARCHIVAL_COMPONENT_ON = "ON";
		String ARCHIVAL_COMPONENT_OFF = "OFF";
		
		/**
		 * Portlet.properties key
		 */
		String PORTLET_COMPONENT_NEWSROOM = "Newsroom";
		String PORTLET_COMPONENT_APPLICATIONS = "App Store";
		String PORTLET_COMPONENT_DEPARTMENTS = "Departments";
		String PORTLET_COMPONENT_INITIATIVES = "Initiatives";
		String PORTLET_COMPONENT_CONFERENCES = "Conferences";
		String PORTLET_ARCHIVAL_YEAR = "archival-year";
		String PORTLET_EXPIRED_WEEK = "expired-week";
		String PORTLET_DELETED_MONTH = "deleted-month";
		String PORTLET_EXPIRED_WEEK_INVALIDEMAIL = "expired-week-invalidemail";
		String PORTLET_MERLIN_INBOX = "merlin-inbox";
		String PORTLET_MONITOR_SCHEDULER_ID = "monitor-scheduler-id";
		String SOLR_BATCH_SIZE = "solr-batch";
	
        /***
         * Meta-data filed
         */
        String META_DATA_CONTENT_SOURCE = "CONTENT_SOURCE";
        String META_DATA_CONTENT_TYPE = "CONTENT_TYPE";
        String META_DATA_CONTENT_LINK = "CONTENT_LINK";
        String META_DATA_CONTENT_PARENT_NAME = "CONTENT_PARENT_NAME";
        String META_DATA_CONTENT_PARENT_LINK = "CONTENT_PARENT_LINK";
        String META_DATA_RESOURCE_TYPE = "RESOURCE_TYPE";
        String META_DATA_PARENT_CONTENT_TYPE = "PARENT_CONTENT_TYPE";
        
        /**
         * Solr field
         */
        String FIRST_NAME = "firstName";
        String LAST_NAME="lastName";
        String JOB_TITLE = "expando/custom_fields/Business JobTitle";
        
        String DEPARTMENT = "expando/custom_fields/Department";
        String LOCATIONS = "expando/custom_fields/Locations";
        String REGION = "expando/custom_fields/Management Region";
        
        String CUSTOM_NAME = "customName";
        String CUSTOM_DEPT_NAME="customDeptName";
        String CUSTOM_TITLE = "customTitle";
        String WEB_CONTENT_INTRODUCTION="web_content/Introduction";
        String WEB_CONTENT_FIRST_SUMMARY="web_content/first_summary";
        String RESOURCE_TITLE = "expando/custom_fields/resourcetitle";
        String HOTEL_SOLN_ID_FIELD="expando/custom_fields/Hotel_Solution_id";
        String HOTEL_SOLN_DETAILS_URL="/web/hotel-solutions/solution-detail?solution-uuid=";
        /***
         * Pagination field
         */
        int  PER_PAGE =10;
        String CURRENT_PAGE ="currPage";
        String START ="Start";
        String END ="end";
        String TOTAL_COUNT ="totalCount";       
        String TOTAL_PAGE ="totalPage";
        
        /***
         * URL String literal
         */
        String THEME_IMAGE_PATH ="/me2-theme/images/search-icons/";
        String EXTENTION = ".png";
        /**
         * Constance for string literal
         */
        String WEB =StringPool.SLASH +"web";
        String HOME = StringPool.SLASH +"home";
        String GROUP= StringPool.SLASH +"group";
        String MEMBER =StringPool.SLASH +"member";
        
        String HOTEL_SOLN_URL="/web/hotel-solutions/home";
        String USER_PREFFERED_NAME="expando/custom_fields/Preferred Name";
        String USER_QUERY_PREFFERED_NAME="expando/custom_fields/Preferred\\ Name";
        
        String[] _KEYWORDS_FIELDS = { Field.COMMENTS, Field.CONTENT,
                    Field.DESCRIPTION, Field.PROPERTIES, 
                        Field.URL, Field.TITLE,Field.ASSET_TAG_NAMES, Field.ASSET_CATEGORY_NAMES,  Field.NAME, Field.TYPE,CUSTOM_TITLE,RESOURCE_TITLE, CUSTOM_DEPT_NAME };
        
        String[] _KEYWORDS_FIELDS_FOR_USER = {LAST_NAME , FIRST_NAME , USER_QUERY_PREFFERED_NAME };
        
        String[] _KEYWORDS_FIELDS_FOR_WEB_CONTENT = {Field.CONTENT , Field.TITLE , Field.DESCRIPTION, Field.TYPE,CUSTOM_TITLE,Field.ASSET_TAG_NAMES, Field.ASSET_CATEGORY_NAMES };
        
        String[] _KEYWORDS_FIELDS_FOR_WEB_CONTENT_EXACT = {Field.DESCRIPTION, Field.TYPE,Field.ASSET_TAG_NAMES, Field.ASSET_CATEGORY_NAMES };
        
        String[] _KEYWORDS_FIELDS_FOR_WEB_CONTENT_WO_CONTENT = {Field.TITLE , Field.DESCRIPTION, Field.TYPE,CUSTOM_TITLE,Field.ASSET_TAG_NAMES, Field.ASSET_CATEGORY_NAMES };
        
        String[] _KEYWORDS_FIELDS_FOR_DL = {Field.TITLE,Field.ASSET_TAG_NAMES, Field.ASSET_CATEGORY_NAMES, RESOURCE_TITLE , Field.DESCRIPTION };
        
        String[] _KEYWORDS_FIELDS_FOR_DL_EXACT = {Field.TITLE,Field.ASSET_TAG_NAMES, Field.ASSET_CATEGORY_NAMES,Field.DESCRIPTION };
        
        String[] _KEYWORDS_FIELDS_FOR_BOOKMARK = {Field.TITLE,Field.ASSET_TAG_NAMES, Field.ASSET_CATEGORY_NAMES, Field.DESCRIPTION  };
        
        String[] _KEYWORDS_FIELDS_FOR_BOOKMARK_EXACT = {Field.ASSET_TAG_NAMES, Field.ASSET_CATEGORY_NAMES, Field.DESCRIPTION  };
        
        String[] _KEYWORDS_FIELDS_FOR_GROUP = {Field.NAME, Field.ASSET_TAG_NAMES, Field.ASSET_CATEGORY_NAMES, Field.DESCRIPTION, CUSTOM_DEPT_NAME };
        
        String[] _KEYWORDS_FIELDS_FOR_GROUP_EXACT = {Field.ASSET_TAG_NAMES, Field.ASSET_CATEGORY_NAMES, Field.DESCRIPTION };
        
        public String USER_TYPE = "USRTYP";
        
        public String USER_TYPE_CATEGORY="Corporate And Temporary Contractors Only";
        
        public String USER_TYPE_CORP_ONLY="User Type Corporate Only";
        
        public String USER_TYPE_OWN_MANAGED="User Type Owned and Managed";
        
        public String USER_GROUP_HR_ACCESS="HR-Access";
        
        public String USER_ROLE_IHG_GLOBAL_ADMIN="IHG Global Admin";
        
        public String USER_TYPE_CORP="[C]";
        public String MGMT_TYPE_CMH="COO";
        
        public String APPLICATION_FIELD_BOOST="APPLICATION^";
        
        
        public static final String REGION_CATE_NAME = "Regions";
    	public static final String BRANDS_CATE_NAME = "Brands";
    	public static final Map<String, String> MAP_USER_TYPE_CATEGORY_NAMES = 
    	         Collections.unmodifiableMap(new HashMap<String, String>() {{ put("C" , "Corporate And Temporary Contractors Only");
    	             put("H" , "Franchise Only");
    	             put("M" , "Owned Or Managed Only");
    	             put("O" , "Owned Or Managed Only");
    	             put("V" , "Vendors Only");
    	             put("T" , "Corporate And Temporary Contractors Only");
    	             put("GLOBAL" , "Global");
    	             put("GC" , "Greater China");
    	             put("AMEA" , "Asia Middle East and Africa");
    	             put("AMER" , "Americas");
    	             put("EURO" , "Europe");
    	             put("CP" , "Crowne Plaza");
    	             put("CW" , "Candlewood Suites");
    	             put("EX" , "Holiday Inn Express");
    	             put("HI" , "Holiday Inn");
    	             put("IC" , "InterContinental");
    	             put("IN" , "Hotel Indigo");
    	             put("SB" , "Staybridge Suites");
    	             put("VN" , "EVEN");
    	             put("UL" , "HUALUXE");
    	             put("CV" , "Holiday Inn Club Vacations");
    	             put("RS" , "Holiday Inn Resorts");
    	             
    	             
    	         }});
    	
    	
    	
    	public static final String USER_TYPE_CAT_NAME = "UserType";
    	public static final String REGIONS_CAT_NAME = "Regions";
    	public static final String BRANDS_CAT_NAME = "Brands";
    	public static final String CORPORATE_AND_TEMP_CONTRACTORS_CAT_NAME = "Corporate And Temporary Contractors Only";
    	public static final String CORPORATE_AND_TEMP_CONTRACTORS_ROLE_NAME = "User Type Corp and Temp";
    	public static final String FRANCHISE_CAT_NAME = "Franchise Only";
    	public static final String FRANCHISE_ROLE_NAME = "User Type Franchise";
    	public static final String ORG_CONTENT_PUBLISHER_ROLE_NAME = "IHG Organization Content Publisher";
    	public static final String OWNED_AND_MANAGED_CAT_NAME = "Owned Or Managed Only";
    	public static final String OWNED_AND_MANAGED_ROLE_NAME = "User Type Owned and Managed";
    	public static final String VENDORS_CAT_NAME = "Vendors Only";
    	public static final String VENDORS_ROLE_NAME = "User Type Vendor";
    	public static final String CORPORATE_CAT_NAME = "User Type Corporate Only";
    	public static final String CORPORATE_ROLE_NAME = "User Type Corporate Only";
    	
    	public static final String VOCABULARY_NAME = "Teamspaces";
    	
    	public static final String CUSTOM_FIELD_ORGANIZATION_TYPE = "Type";
    	public static final String ABOUT_IHG_ORGANIZATION_TYPE = "About-IHG";
    	public static final String OUR_PEOPLE_ORGANIZATION_NAME = "Our People";
    	public static final String OUR_PEOPLE_TAG = "our-people";
    	public static final String DEPARTMENT_ORGANIZATION_TYPE = "Department";
    	
    	public static final String MERLIN_TAG_NAME = "merlin";
    	public static final String TEAMSPACE_TAG_NAME = "teamspace";
    	public static final String INITIATIVE_TAG_NAME = "initiative";
    	public static final String CONFERENCE_TAG_NAME = "conference";
    	public static final String DEFAULT_ROLE = CORPORATE_AND_TEMP_CONTRACTORS_ROLE_NAME;
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	

}
