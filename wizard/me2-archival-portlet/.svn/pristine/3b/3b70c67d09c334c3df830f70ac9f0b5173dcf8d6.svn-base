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

package com.ihg.me2.archive.service.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * This class is used by
 * {@link com.ihg.me2.archive.service.service.http.ArchiveContentServiceSoap}.
 * </p>
 *
 * @author    Sandip Patel
 * @see       com.ihg.me2.archive.service.service.http.ArchiveContentServiceSoap
 * @generated
 */
public class ArchiveContentSoap implements Serializable {
	public static ArchiveContentSoap toSoapModel(ArchiveContent model) {
		ArchiveContentSoap soapModel = new ArchiveContentSoap();

		soapModel.setCONTENT_ID(model.getCONTENT_ID());
		soapModel.setCONTENT_TYP(model.getCONTENT_TYP());
		soapModel.setCONTENT_NM(model.getCONTENT_NM());
		soapModel.setGRP_ID(model.getGRP_ID());
		soapModel.setAUTHOR_EMAIL_ID(model.getAUTHOR_EMAIL_ID());
		soapModel.setAUTHOR_NM(model.getAUTHOR_NM());
		soapModel.setCONTENT_URL_TXT(model.getCONTENT_URL_TXT());
		soapModel.setCONTENT_STAT_CD(model.getCONTENT_STAT_CD());
		soapModel.setCONTENT_STAT_MOD_DT(model.getCONTENT_STAT_MOD_DT());
		soapModel.setCONTENT_STAT_NOTIFIED_DT(model.getCONTENT_STAT_NOTIFIED_DT());
		soapModel.setCONTENT_STAT_EXPIRED_DT(model.getCONTENT_STAT_EXPIRED_DT());
		soapModel.setCONTENT_STAT_DEL_DT(model.getCONTENT_STAT_DEL_DT());
		soapModel.setCREAT_USR_ID(model.getCREAT_USR_ID());
		soapModel.setCREAT_TS(model.getCREAT_TS());
		soapModel.setLST_UPDT_USR_ID(model.getLST_UPDT_USR_ID());
		soapModel.setLST_UPDT_TS(model.getLST_UPDT_TS());

		return soapModel;
	}

	public static ArchiveContentSoap[] toSoapModels(ArchiveContent[] models) {
		ArchiveContentSoap[] soapModels = new ArchiveContentSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ArchiveContentSoap[][] toSoapModels(ArchiveContent[][] models) {
		ArchiveContentSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ArchiveContentSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ArchiveContentSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ArchiveContentSoap[] toSoapModels(List<ArchiveContent> models) {
		List<ArchiveContentSoap> soapModels = new ArrayList<ArchiveContentSoap>(models.size());

		for (ArchiveContent model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ArchiveContentSoap[soapModels.size()]);
	}

	public ArchiveContentSoap() {
	}

	public String getPrimaryKey() {
		return _CONTENT_ID;
	}

	public void setPrimaryKey(String pk) {
		setCONTENT_ID(pk);
	}

	public String getCONTENT_ID() {
		return _CONTENT_ID;
	}

	public void setCONTENT_ID(String CONTENT_ID) {
		_CONTENT_ID = CONTENT_ID;
	}

	public String getCONTENT_TYP() {
		return _CONTENT_TYP;
	}

	public void setCONTENT_TYP(String CONTENT_TYP) {
		_CONTENT_TYP = CONTENT_TYP;
	}

	public String getCONTENT_NM() {
		return _CONTENT_NM;
	}

	public void setCONTENT_NM(String CONTENT_NM) {
		_CONTENT_NM = CONTENT_NM;
	}

	public long getGRP_ID() {
		return _GRP_ID;
	}

	public void setGRP_ID(long GRP_ID) {
		_GRP_ID = GRP_ID;
	}

	public String getAUTHOR_EMAIL_ID() {
		return _AUTHOR_EMAIL_ID;
	}

	public void setAUTHOR_EMAIL_ID(String AUTHOR_EMAIL_ID) {
		_AUTHOR_EMAIL_ID = AUTHOR_EMAIL_ID;
	}

	public String getAUTHOR_NM() {
		return _AUTHOR_NM;
	}

	public void setAUTHOR_NM(String AUTHOR_NM) {
		_AUTHOR_NM = AUTHOR_NM;
	}

	public String getCONTENT_URL_TXT() {
		return _CONTENT_URL_TXT;
	}

	public void setCONTENT_URL_TXT(String CONTENT_URL_TXT) {
		_CONTENT_URL_TXT = CONTENT_URL_TXT;
	}

	public String getCONTENT_STAT_CD() {
		return _CONTENT_STAT_CD;
	}

	public void setCONTENT_STAT_CD(String CONTENT_STAT_CD) {
		_CONTENT_STAT_CD = CONTENT_STAT_CD;
	}

	public Date getCONTENT_STAT_MOD_DT() {
		return _CONTENT_STAT_MOD_DT;
	}

	public void setCONTENT_STAT_MOD_DT(Date CONTENT_STAT_MOD_DT) {
		_CONTENT_STAT_MOD_DT = CONTENT_STAT_MOD_DT;
	}

	public Date getCONTENT_STAT_NOTIFIED_DT() {
		return _CONTENT_STAT_NOTIFIED_DT;
	}

	public void setCONTENT_STAT_NOTIFIED_DT(Date CONTENT_STAT_NOTIFIED_DT) {
		_CONTENT_STAT_NOTIFIED_DT = CONTENT_STAT_NOTIFIED_DT;
	}

	public Date getCONTENT_STAT_EXPIRED_DT() {
		return _CONTENT_STAT_EXPIRED_DT;
	}

	public void setCONTENT_STAT_EXPIRED_DT(Date CONTENT_STAT_EXPIRED_DT) {
		_CONTENT_STAT_EXPIRED_DT = CONTENT_STAT_EXPIRED_DT;
	}

	public Date getCONTENT_STAT_DEL_DT() {
		return _CONTENT_STAT_DEL_DT;
	}

	public void setCONTENT_STAT_DEL_DT(Date CONTENT_STAT_DEL_DT) {
		_CONTENT_STAT_DEL_DT = CONTENT_STAT_DEL_DT;
	}

	public String getCREAT_USR_ID() {
		return _CREAT_USR_ID;
	}

	public void setCREAT_USR_ID(String CREAT_USR_ID) {
		_CREAT_USR_ID = CREAT_USR_ID;
	}

	public Date getCREAT_TS() {
		return _CREAT_TS;
	}

	public void setCREAT_TS(Date CREAT_TS) {
		_CREAT_TS = CREAT_TS;
	}

	public String getLST_UPDT_USR_ID() {
		return _LST_UPDT_USR_ID;
	}

	public void setLST_UPDT_USR_ID(String LST_UPDT_USR_ID) {
		_LST_UPDT_USR_ID = LST_UPDT_USR_ID;
	}

	public Date getLST_UPDT_TS() {
		return _LST_UPDT_TS;
	}

	public void setLST_UPDT_TS(Date LST_UPDT_TS) {
		_LST_UPDT_TS = LST_UPDT_TS;
	}

	private String _CONTENT_ID;
	private String _CONTENT_TYP;
	private String _CONTENT_NM;
	private long _GRP_ID;
	private String _AUTHOR_EMAIL_ID;
	private String _AUTHOR_NM;
	private String _CONTENT_URL_TXT;
	private String _CONTENT_STAT_CD;
	private Date _CONTENT_STAT_MOD_DT;
	private Date _CONTENT_STAT_NOTIFIED_DT;
	private Date _CONTENT_STAT_EXPIRED_DT;
	private Date _CONTENT_STAT_DEL_DT;
	private String _CREAT_USR_ID;
	private Date _CREAT_TS;
	private String _LST_UPDT_USR_ID;
	private Date _LST_UPDT_TS;
}