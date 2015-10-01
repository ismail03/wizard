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

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.util.Date;

/**
 * @author Sandip Patel
 */
public class ArchiveContentClp extends BaseModelImpl<ArchiveContent>
	implements ArchiveContent {
	public ArchiveContentClp() {
	}

	public String getPrimaryKey() {
		return _CONTENT_ID;
	}

	public void setPrimaryKey(String pk) {
		setCONTENT_ID(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return _CONTENT_ID;
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

	public ArchiveContent toEscapedModel() {
		if (isEscapedModel()) {
			return this;
		}
		else {
			return (ArchiveContent)Proxy.newProxyInstance(ArchiveContent.class.getClassLoader(),
				new Class[] { ArchiveContent.class },
				new AutoEscapeBeanHandler(this));
		}
	}

	public Object clone() {
		ArchiveContentClp clone = new ArchiveContentClp();

		clone.setCONTENT_ID(getCONTENT_ID());
		clone.setCONTENT_TYP(getCONTENT_TYP());
		clone.setCONTENT_NM(getCONTENT_NM());
		clone.setGRP_ID(getGRP_ID());
		clone.setAUTHOR_EMAIL_ID(getAUTHOR_EMAIL_ID());
		clone.setAUTHOR_NM(getAUTHOR_NM());
		clone.setCONTENT_URL_TXT(getCONTENT_URL_TXT());
		clone.setCONTENT_STAT_CD(getCONTENT_STAT_CD());
		clone.setCONTENT_STAT_MOD_DT(getCONTENT_STAT_MOD_DT());
		clone.setCONTENT_STAT_NOTIFIED_DT(getCONTENT_STAT_NOTIFIED_DT());
		clone.setCONTENT_STAT_EXPIRED_DT(getCONTENT_STAT_EXPIRED_DT());
		clone.setCONTENT_STAT_DEL_DT(getCONTENT_STAT_DEL_DT());
		clone.setCREAT_USR_ID(getCREAT_USR_ID());
		clone.setCREAT_TS(getCREAT_TS());
		clone.setLST_UPDT_USR_ID(getLST_UPDT_USR_ID());
		clone.setLST_UPDT_TS(getLST_UPDT_TS());

		return clone;
	}

	public int compareTo(ArchiveContent archiveContent) {
		int value = 0;

		value = getCONTENT_NM().compareTo(archiveContent.getCONTENT_NM());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ArchiveContentClp archiveContent = null;

		try {
			archiveContent = (ArchiveContentClp)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = archiveContent.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{CONTENT_ID=");
		sb.append(getCONTENT_ID());
		sb.append(", CONTENT_TYP=");
		sb.append(getCONTENT_TYP());
		sb.append(", CONTENT_NM=");
		sb.append(getCONTENT_NM());
		sb.append(", GRP_ID=");
		sb.append(getGRP_ID());
		sb.append(", AUTHOR_EMAIL_ID=");
		sb.append(getAUTHOR_EMAIL_ID());
		sb.append(", AUTHOR_NM=");
		sb.append(getAUTHOR_NM());
		sb.append(", CONTENT_URL_TXT=");
		sb.append(getCONTENT_URL_TXT());
		sb.append(", CONTENT_STAT_CD=");
		sb.append(getCONTENT_STAT_CD());
		sb.append(", CONTENT_STAT_MOD_DT=");
		sb.append(getCONTENT_STAT_MOD_DT());
		sb.append(", CONTENT_STAT_NOTIFIED_DT=");
		sb.append(getCONTENT_STAT_NOTIFIED_DT());
		sb.append(", CONTENT_STAT_EXPIRED_DT=");
		sb.append(getCONTENT_STAT_EXPIRED_DT());
		sb.append(", CONTENT_STAT_DEL_DT=");
		sb.append(getCONTENT_STAT_DEL_DT());
		sb.append(", CREAT_USR_ID=");
		sb.append(getCREAT_USR_ID());
		sb.append(", CREAT_TS=");
		sb.append(getCREAT_TS());
		sb.append(", LST_UPDT_USR_ID=");
		sb.append(getLST_UPDT_USR_ID());
		sb.append(", LST_UPDT_TS=");
		sb.append(getLST_UPDT_TS());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(52);

		sb.append("<model><model-name>");
		sb.append("com.ihg.me2.archive.service.model.ArchiveContent");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>CONTENT_ID</column-name><column-value><![CDATA[");
		sb.append(getCONTENT_ID());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CONTENT_TYP</column-name><column-value><![CDATA[");
		sb.append(getCONTENT_TYP());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CONTENT_NM</column-name><column-value><![CDATA[");
		sb.append(getCONTENT_NM());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>GRP_ID</column-name><column-value><![CDATA[");
		sb.append(getGRP_ID());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>AUTHOR_EMAIL_ID</column-name><column-value><![CDATA[");
		sb.append(getAUTHOR_EMAIL_ID());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>AUTHOR_NM</column-name><column-value><![CDATA[");
		sb.append(getAUTHOR_NM());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CONTENT_URL_TXT</column-name><column-value><![CDATA[");
		sb.append(getCONTENT_URL_TXT());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CONTENT_STAT_CD</column-name><column-value><![CDATA[");
		sb.append(getCONTENT_STAT_CD());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CONTENT_STAT_MOD_DT</column-name><column-value><![CDATA[");
		sb.append(getCONTENT_STAT_MOD_DT());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CONTENT_STAT_NOTIFIED_DT</column-name><column-value><![CDATA[");
		sb.append(getCONTENT_STAT_NOTIFIED_DT());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CONTENT_STAT_EXPIRED_DT</column-name><column-value><![CDATA[");
		sb.append(getCONTENT_STAT_EXPIRED_DT());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CONTENT_STAT_DEL_DT</column-name><column-value><![CDATA[");
		sb.append(getCONTENT_STAT_DEL_DT());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CREAT_USR_ID</column-name><column-value><![CDATA[");
		sb.append(getCREAT_USR_ID());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>CREAT_TS</column-name><column-value><![CDATA[");
		sb.append(getCREAT_TS());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>LST_UPDT_USR_ID</column-name><column-value><![CDATA[");
		sb.append(getLST_UPDT_USR_ID());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>LST_UPDT_TS</column-name><column-value><![CDATA[");
		sb.append(getLST_UPDT_TS());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
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