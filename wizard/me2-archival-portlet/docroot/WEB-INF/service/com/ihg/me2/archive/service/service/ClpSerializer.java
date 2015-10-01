/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.ihg.me2.archive.service.service;

import com.ihg.me2.archive.service.model.ArchiveContentClp;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.BaseModel;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ClpSerializer {
	public static final String SERVLET_CONTEXT_NAME = "me2-archival-portlet";

	public static void setClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	public static Object translateInput(BaseModel<?> oldModel) {
		Class<?> oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		if (oldModelClassName.equals(ArchiveContentClp.class.getName())) {
			ArchiveContentClp oldCplModel = (ArchiveContentClp)oldModel;

			ClassLoader contextClassLoader = Thread.currentThread()
												   .getContextClassLoader();

			try {
				Thread.currentThread().setContextClassLoader(_classLoader);

				try {
					Class<?> newModelClass = Class.forName("com.ihg.me2.archive.service.model.impl.ArchiveContentImpl",
							true, _classLoader);

					Object newModel = newModelClass.newInstance();

					Method method0 = newModelClass.getMethod("setCONTENT_ID",
							new Class[] { String.class });

					String value0 = oldCplModel.getCONTENT_ID();

					method0.invoke(newModel, value0);

					Method method1 = newModelClass.getMethod("setCONTENT_TYP",
							new Class[] { String.class });

					String value1 = oldCplModel.getCONTENT_TYP();

					method1.invoke(newModel, value1);

					Method method2 = newModelClass.getMethod("setCONTENT_NM",
							new Class[] { String.class });

					String value2 = oldCplModel.getCONTENT_NM();

					method2.invoke(newModel, value2);

					Method method3 = newModelClass.getMethod("setGRP_ID",
							new Class[] { Long.TYPE });

					Long value3 = new Long(oldCplModel.getGRP_ID());

					method3.invoke(newModel, value3);

					Method method4 = newModelClass.getMethod("setAUTHOR_EMAIL_ID",
							new Class[] { String.class });

					String value4 = oldCplModel.getAUTHOR_EMAIL_ID();

					method4.invoke(newModel, value4);

					Method method5 = newModelClass.getMethod("setAUTHOR_NM",
							new Class[] { String.class });

					String value5 = oldCplModel.getAUTHOR_NM();

					method5.invoke(newModel, value5);

					Method method6 = newModelClass.getMethod("setCONTENT_URL_TXT",
							new Class[] { String.class });

					String value6 = oldCplModel.getCONTENT_URL_TXT();

					method6.invoke(newModel, value6);

					Method method7 = newModelClass.getMethod("setCONTENT_STAT_CD",
							new Class[] { String.class });

					String value7 = oldCplModel.getCONTENT_STAT_CD();

					method7.invoke(newModel, value7);

					Method method8 = newModelClass.getMethod("setCONTENT_STAT_MOD_DT",
							new Class[] { Date.class });

					Date value8 = oldCplModel.getCONTENT_STAT_MOD_DT();

					method8.invoke(newModel, value8);

					Method method9 = newModelClass.getMethod("setCONTENT_STAT_NOTIFIED_DT",
							new Class[] { Date.class });

					Date value9 = oldCplModel.getCONTENT_STAT_NOTIFIED_DT();

					method9.invoke(newModel, value9);

					Method method10 = newModelClass.getMethod("setCONTENT_STAT_EXPIRED_DT",
							new Class[] { Date.class });

					Date value10 = oldCplModel.getCONTENT_STAT_EXPIRED_DT();

					method10.invoke(newModel, value10);

					Method method11 = newModelClass.getMethod("setCONTENT_STAT_DEL_DT",
							new Class[] { Date.class });

					Date value11 = oldCplModel.getCONTENT_STAT_DEL_DT();

					method11.invoke(newModel, value11);

					Method method12 = newModelClass.getMethod("setCREAT_USR_ID",
							new Class[] { String.class });

					String value12 = oldCplModel.getCREAT_USR_ID();

					method12.invoke(newModel, value12);

					Method method13 = newModelClass.getMethod("setCREAT_TS",
							new Class[] { Date.class });

					Date value13 = oldCplModel.getCREAT_TS();

					method13.invoke(newModel, value13);

					Method method14 = newModelClass.getMethod("setLST_UPDT_USR_ID",
							new Class[] { String.class });

					String value14 = oldCplModel.getLST_UPDT_USR_ID();

					method14.invoke(newModel, value14);

					Method method15 = newModelClass.getMethod("setLST_UPDT_TS",
							new Class[] { Date.class });

					Date value15 = oldCplModel.getLST_UPDT_TS();

					method15.invoke(newModel, value15);

					return newModel;
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
			finally {
				Thread.currentThread().setContextClassLoader(contextClassLoader);
			}
		}

		return oldModel;
	}

	public static Object translateInput(List<Object> oldList) {
		List<Object> newList = new ArrayList<Object>(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateInput(curObj));
		}

		return newList;
	}

	public static Object translateInput(Object obj) {
		if (obj instanceof BaseModel<?>) {
			return translateInput((BaseModel<?>)obj);
		}
		else if (obj instanceof List<?>) {
			return translateInput((List<Object>)obj);
		}
		else {
			return obj;
		}
	}

	public static Object translateOutput(BaseModel<?> oldModel) {
		Class<?> oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		if (oldModelClassName.equals(
					"com.ihg.me2.archive.service.model.impl.ArchiveContentImpl")) {
			ClassLoader contextClassLoader = Thread.currentThread()
												   .getContextClassLoader();

			try {
				Thread.currentThread().setContextClassLoader(_classLoader);

				try {
					ArchiveContentClp newModel = new ArchiveContentClp();

					Method method0 = oldModelClass.getMethod("getCONTENT_ID");

					String value0 = (String)method0.invoke(oldModel,
							(Object[])null);

					newModel.setCONTENT_ID(value0);

					Method method1 = oldModelClass.getMethod("getCONTENT_TYP");

					String value1 = (String)method1.invoke(oldModel,
							(Object[])null);

					newModel.setCONTENT_TYP(value1);

					Method method2 = oldModelClass.getMethod("getCONTENT_NM");

					String value2 = (String)method2.invoke(oldModel,
							(Object[])null);

					newModel.setCONTENT_NM(value2);

					Method method3 = oldModelClass.getMethod("getGRP_ID");

					Long value3 = (Long)method3.invoke(oldModel, (Object[])null);

					newModel.setGRP_ID(value3);

					Method method4 = oldModelClass.getMethod(
							"getAUTHOR_EMAIL_ID");

					String value4 = (String)method4.invoke(oldModel,
							(Object[])null);

					newModel.setAUTHOR_EMAIL_ID(value4);

					Method method5 = oldModelClass.getMethod("getAUTHOR_NM");

					String value5 = (String)method5.invoke(oldModel,
							(Object[])null);

					newModel.setAUTHOR_NM(value5);

					Method method6 = oldModelClass.getMethod(
							"getCONTENT_URL_TXT");

					String value6 = (String)method6.invoke(oldModel,
							(Object[])null);

					newModel.setCONTENT_URL_TXT(value6);

					Method method7 = oldModelClass.getMethod(
							"getCONTENT_STAT_CD");

					String value7 = (String)method7.invoke(oldModel,
							(Object[])null);

					newModel.setCONTENT_STAT_CD(value7);

					Method method8 = oldModelClass.getMethod(
							"getCONTENT_STAT_MOD_DT");

					Date value8 = (Date)method8.invoke(oldModel, (Object[])null);

					newModel.setCONTENT_STAT_MOD_DT(value8);

					Method method9 = oldModelClass.getMethod(
							"getCONTENT_STAT_NOTIFIED_DT");

					Date value9 = (Date)method9.invoke(oldModel, (Object[])null);

					newModel.setCONTENT_STAT_NOTIFIED_DT(value9);

					Method method10 = oldModelClass.getMethod(
							"getCONTENT_STAT_EXPIRED_DT");

					Date value10 = (Date)method10.invoke(oldModel,
							(Object[])null);

					newModel.setCONTENT_STAT_EXPIRED_DT(value10);

					Method method11 = oldModelClass.getMethod(
							"getCONTENT_STAT_DEL_DT");

					Date value11 = (Date)method11.invoke(oldModel,
							(Object[])null);

					newModel.setCONTENT_STAT_DEL_DT(value11);

					Method method12 = oldModelClass.getMethod("getCREAT_USR_ID");

					String value12 = (String)method12.invoke(oldModel,
							(Object[])null);

					newModel.setCREAT_USR_ID(value12);

					Method method13 = oldModelClass.getMethod("getCREAT_TS");

					Date value13 = (Date)method13.invoke(oldModel,
							(Object[])null);

					newModel.setCREAT_TS(value13);

					Method method14 = oldModelClass.getMethod(
							"getLST_UPDT_USR_ID");

					String value14 = (String)method14.invoke(oldModel,
							(Object[])null);

					newModel.setLST_UPDT_USR_ID(value14);

					Method method15 = oldModelClass.getMethod("getLST_UPDT_TS");

					Date value15 = (Date)method15.invoke(oldModel,
							(Object[])null);

					newModel.setLST_UPDT_TS(value15);

					return newModel;
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
			finally {
				Thread.currentThread().setContextClassLoader(contextClassLoader);
			}
		}

		return oldModel;
	}

	public static Object translateOutput(List<Object> oldList) {
		List<Object> newList = new ArrayList<Object>(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateOutput(curObj));
		}

		return newList;
	}

	public static Object translateOutput(Object obj) {
		if (obj instanceof BaseModel<?>) {
			return translateOutput((BaseModel<?>)obj);
		}
		else if (obj instanceof List<?>) {
			return translateOutput((List<Object>)obj);
		}
		else {
			return obj;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ClpSerializer.class);
	private static ClassLoader _classLoader;
}