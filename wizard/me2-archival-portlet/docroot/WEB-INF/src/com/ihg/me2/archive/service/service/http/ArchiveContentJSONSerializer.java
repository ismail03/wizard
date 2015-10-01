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

package com.ihg.me2.archive.service.service.http;

import com.ihg.me2.archive.service.model.ArchiveContent;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Date;
import java.util.List;

/**
 * @author    Sandip Patel
 * @generated
 */
public class ArchiveContentJSONSerializer {
	public static JSONObject toJSONObject(ArchiveContent model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("CONTENT_ID", model.getCONTENT_ID());
		jsonObject.put("CONTENT_TYP", model.getCONTENT_TYP());
		jsonObject.put("CONTENT_NM", model.getCONTENT_NM());
		jsonObject.put("GRP_ID", model.getGRP_ID());
		jsonObject.put("AUTHOR_EMAIL_ID", model.getAUTHOR_EMAIL_ID());
		jsonObject.put("AUTHOR_NM", model.getAUTHOR_NM());
		jsonObject.put("CONTENT_URL_TXT", model.getCONTENT_URL_TXT());
		jsonObject.put("CONTENT_STAT_CD", model.getCONTENT_STAT_CD());

		Date CONTENT_STAT_MOD_DT = model.getCONTENT_STAT_MOD_DT();

		String CONTENT_STAT_MOD_DTJSON = StringPool.BLANK;

		if (CONTENT_STAT_MOD_DT != null) {
			CONTENT_STAT_MOD_DTJSON = String.valueOf(CONTENT_STAT_MOD_DT.getTime());
		}

		jsonObject.put("CONTENT_STAT_MOD_DT", CONTENT_STAT_MOD_DTJSON);

		Date CONTENT_STAT_NOTIFIED_DT = model.getCONTENT_STAT_NOTIFIED_DT();

		String CONTENT_STAT_NOTIFIED_DTJSON = StringPool.BLANK;

		if (CONTENT_STAT_NOTIFIED_DT != null) {
			CONTENT_STAT_NOTIFIED_DTJSON = String.valueOf(CONTENT_STAT_NOTIFIED_DT.getTime());
		}

		jsonObject.put("CONTENT_STAT_NOTIFIED_DT", CONTENT_STAT_NOTIFIED_DTJSON);

		Date CONTENT_STAT_EXPIRED_DT = model.getCONTENT_STAT_EXPIRED_DT();

		String CONTENT_STAT_EXPIRED_DTJSON = StringPool.BLANK;

		if (CONTENT_STAT_EXPIRED_DT != null) {
			CONTENT_STAT_EXPIRED_DTJSON = String.valueOf(CONTENT_STAT_EXPIRED_DT.getTime());
		}

		jsonObject.put("CONTENT_STAT_EXPIRED_DT", CONTENT_STAT_EXPIRED_DTJSON);

		Date CONTENT_STAT_DEL_DT = model.getCONTENT_STAT_DEL_DT();

		String CONTENT_STAT_DEL_DTJSON = StringPool.BLANK;

		if (CONTENT_STAT_DEL_DT != null) {
			CONTENT_STAT_DEL_DTJSON = String.valueOf(CONTENT_STAT_DEL_DT.getTime());
		}

		jsonObject.put("CONTENT_STAT_DEL_DT", CONTENT_STAT_DEL_DTJSON);
		jsonObject.put("CREAT_USR_ID", model.getCREAT_USR_ID());

		Date CREAT_TS = model.getCREAT_TS();

		String CREAT_TSJSON = StringPool.BLANK;

		if (CREAT_TS != null) {
			CREAT_TSJSON = String.valueOf(CREAT_TS.getTime());
		}

		jsonObject.put("CREAT_TS", CREAT_TSJSON);
		jsonObject.put("LST_UPDT_USR_ID", model.getLST_UPDT_USR_ID());

		Date LST_UPDT_TS = model.getLST_UPDT_TS();

		String LST_UPDT_TSJSON = StringPool.BLANK;

		if (LST_UPDT_TS != null) {
			LST_UPDT_TSJSON = String.valueOf(LST_UPDT_TS.getTime());
		}

		jsonObject.put("LST_UPDT_TS", LST_UPDT_TSJSON);

		return jsonObject;
	}

	public static JSONArray toJSONArray(
		com.ihg.me2.archive.service.model.ArchiveContent[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ArchiveContent model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.ihg.me2.archive.service.model.ArchiveContent[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ArchiveContent[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.ihg.me2.archive.service.model.ArchiveContent> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ArchiveContent model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}