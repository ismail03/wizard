package com.ihg.me2.archive.util;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArchivalMonitorUtil {
	
	private ArchivalMonitorUtil(){
		
	}
	
	/**
	 * it will get minimum Asset modified date from AssetEntry table.
	 * @return
	 */
	public static Date getMinimumAssetModifiedDate(){
		Date fromDate = null;
		try {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntry.class, PortalClassLoaderUtil.getClassLoader());
		Criterion criterion = null;
		dynamicQuery.addOrder(OrderFactoryUtil.asc("modifiedDate"));
		System.out.println("========= using dynamic Query	===============");
		List<AssetEntry> assetEntries;
			assetEntries = AssetEntryLocalServiceUtil.dynamicQuery(dynamicQuery, 0, 1);
		for (AssetEntry assetEntry : assetEntries) {
			System.out.println("1) assetEntry details:->"+assetEntry.getTitle());
			System.out.println("1) assetEntry getModifiedDate():->"+assetEntry.getModifiedDate());
			fromDate = assetEntry.getModifiedDate();
		}
		System.out.println("========= End using dynamic Query	===============");
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fromDate;
	}
	
	/**
	 * This function will returning one year before toDate. and if toDate is 
	 * null then it will returning one year before current Date. 
	 * @param date
	 */
	public static Date getNYearBeforeDate(Date toDate, int nMonth) {
	    Calendar cal = Calendar.getInstance();
	    Date targetDate;
	    if(null!=toDate)
	    	cal.setTime(toDate);
	    System.out.println("Today : " + cal.getTime());

	    // Substract 1 year from the calendar
	    cal.add(Calendar.YEAR, 1);
	    targetDate = cal.getTime();
	    System.out.println("1 year ago: " + targetDate);
	    
	    return targetDate;
	}
	
	/**
	 * This function will returning three months later than toDate. and if toDate is null then it will returning three month later than current Date.
	 * @param toDate
	 * @return
	 */
	public static Date getNMonthLaterDate(Date toDate, int nMonth){
		Calendar now = Calendar.getInstance();
		if(Validator.isNotNull(toDate))
			now.setTime(toDate);
	    now.add(Calendar.MONTH, nMonth);
	    System.out.println("date after one week : " + (now.get(Calendar.MONTH) + 1) + "-"
	        + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR));
	    return now.getTime();
	}
	
	/**
	 * This function will returning three weeks later than toDate. and if toDate is null then it will returning three week later than current Date.
	 * @param toDate
	 * @return
	 */
	public static Date getNWeeksLaterDate(Date toDate, int nWeek){
		Calendar now = Calendar.getInstance();
		if(Validator.isNotNull(toDate))
			now.setTime(toDate);
	    now.add(Calendar.WEEK_OF_YEAR, nWeek);
	    System.out.println("date after one week : " + (now.get(Calendar.MONTH) + 1) + "-"
	        + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR));
	    return now.getTime();
	}
	
}
