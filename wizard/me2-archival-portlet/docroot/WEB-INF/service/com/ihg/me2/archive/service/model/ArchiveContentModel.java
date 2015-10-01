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

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the ArchiveContent service. Represents a row in the &quot;ARCHIVAL_CONTENT&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.ihg.me2.archive.service.model.impl.ArchiveContentModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.ihg.me2.archive.service.model.impl.ArchiveContentImpl}.
 * </p>
 *
 * @author Sandip Patel
 * @see ArchiveContent
 * @see com.ihg.me2.archive.service.model.impl.ArchiveContentImpl
 * @see com.ihg.me2.archive.service.model.impl.ArchiveContentModelImpl
 * @generated
 */
public interface ArchiveContentModel extends BaseModel<ArchiveContent> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a archive content model instance should use the {@link ArchiveContent} interface instead.
	 */

	/**
	 * Gets the primary key of this archive content.
	 *
	 * @return the primary key of this archive content
	 */
	public String getPrimaryKey();

	/**
	 * Sets the primary key of this archive content
	 *
	 * @param pk the primary key of this archive content
	 */
	public void setPrimaryKey(String pk);

	/**
	 * Gets the c o n t e n t_ i d of this archive content.
	 *
	 * @return the c o n t e n t_ i d of this archive content
	 */
	@AutoEscape
	public String getCONTENT_ID();

	/**
	 * Sets the c o n t e n t_ i d of this archive content.
	 *
	 * @param CONTENT_ID the c o n t e n t_ i d of this archive content
	 */
	public void setCONTENT_ID(String CONTENT_ID);

	/**
	 * Gets the c o n t e n t_ t y p of this archive content.
	 *
	 * @return the c o n t e n t_ t y p of this archive content
	 */
	@AutoEscape
	public String getCONTENT_TYP();

	/**
	 * Sets the c o n t e n t_ t y p of this archive content.
	 *
	 * @param CONTENT_TYP the c o n t e n t_ t y p of this archive content
	 */
	public void setCONTENT_TYP(String CONTENT_TYP);

	/**
	 * Gets the c o n t e n t_ n m of this archive content.
	 *
	 * @return the c o n t e n t_ n m of this archive content
	 */
	@AutoEscape
	public String getCONTENT_NM();

	/**
	 * Sets the c o n t e n t_ n m of this archive content.
	 *
	 * @param CONTENT_NM the c o n t e n t_ n m of this archive content
	 */
	public void setCONTENT_NM(String CONTENT_NM);

	/**
	 * Gets the g r p_ i d of this archive content.
	 *
	 * @return the g r p_ i d of this archive content
	 */
	public long getGRP_ID();

	/**
	 * Sets the g r p_ i d of this archive content.
	 *
	 * @param GRP_ID the g r p_ i d of this archive content
	 */
	public void setGRP_ID(long GRP_ID);

	/**
	 * Gets the a u t h o r_ e m a i l_ i d of this archive content.
	 *
	 * @return the a u t h o r_ e m a i l_ i d of this archive content
	 */
	@AutoEscape
	public String getAUTHOR_EMAIL_ID();

	/**
	 * Sets the a u t h o r_ e m a i l_ i d of this archive content.
	 *
	 * @param AUTHOR_EMAIL_ID the a u t h o r_ e m a i l_ i d of this archive content
	 */
	public void setAUTHOR_EMAIL_ID(String AUTHOR_EMAIL_ID);

	/**
	 * Gets the a u t h o r_ n m of this archive content.
	 *
	 * @return the a u t h o r_ n m of this archive content
	 */
	@AutoEscape
	public String getAUTHOR_NM();

	/**
	 * Sets the a u t h o r_ n m of this archive content.
	 *
	 * @param AUTHOR_NM the a u t h o r_ n m of this archive content
	 */
	public void setAUTHOR_NM(String AUTHOR_NM);

	/**
	 * Gets the c o n t e n t_ u r l_ t x t of this archive content.
	 *
	 * @return the c o n t e n t_ u r l_ t x t of this archive content
	 */
	@AutoEscape
	public String getCONTENT_URL_TXT();

	/**
	 * Sets the c o n t e n t_ u r l_ t x t of this archive content.
	 *
	 * @param CONTENT_URL_TXT the c o n t e n t_ u r l_ t x t of this archive content
	 */
	public void setCONTENT_URL_TXT(String CONTENT_URL_TXT);

	/**
	 * Gets the c o n t e n t_ s t a t_ c d of this archive content.
	 *
	 * @return the c o n t e n t_ s t a t_ c d of this archive content
	 */
	@AutoEscape
	public String getCONTENT_STAT_CD();

	/**
	 * Sets the c o n t e n t_ s t a t_ c d of this archive content.
	 *
	 * @param CONTENT_STAT_CD the c o n t e n t_ s t a t_ c d of this archive content
	 */
	public void setCONTENT_STAT_CD(String CONTENT_STAT_CD);

	/**
	 * Gets the c o n t e n t_ s t a t_ m o d_ d t of this archive content.
	 *
	 * @return the c o n t e n t_ s t a t_ m o d_ d t of this archive content
	 */
	public Date getCONTENT_STAT_MOD_DT();

	/**
	 * Sets the c o n t e n t_ s t a t_ m o d_ d t of this archive content.
	 *
	 * @param CONTENT_STAT_MOD_DT the c o n t e n t_ s t a t_ m o d_ d t of this archive content
	 */
	public void setCONTENT_STAT_MOD_DT(Date CONTENT_STAT_MOD_DT);

	/**
	 * Gets the c o n t e n t_ s t a t_ n o t i f i e d_ d t of this archive content.
	 *
	 * @return the c o n t e n t_ s t a t_ n o t i f i e d_ d t of this archive content
	 */
	public Date getCONTENT_STAT_NOTIFIED_DT();

	/**
	 * Sets the c o n t e n t_ s t a t_ n o t i f i e d_ d t of this archive content.
	 *
	 * @param CONTENT_STAT_NOTIFIED_DT the c o n t e n t_ s t a t_ n o t i f i e d_ d t of this archive content
	 */
	public void setCONTENT_STAT_NOTIFIED_DT(Date CONTENT_STAT_NOTIFIED_DT);

	/**
	 * Gets the c o n t e n t_ s t a t_ e x p i r e d_ d t of this archive content.
	 *
	 * @return the c o n t e n t_ s t a t_ e x p i r e d_ d t of this archive content
	 */
	public Date getCONTENT_STAT_EXPIRED_DT();

	/**
	 * Sets the c o n t e n t_ s t a t_ e x p i r e d_ d t of this archive content.
	 *
	 * @param CONTENT_STAT_EXPIRED_DT the c o n t e n t_ s t a t_ e x p i r e d_ d t of this archive content
	 */
	public void setCONTENT_STAT_EXPIRED_DT(Date CONTENT_STAT_EXPIRED_DT);

	/**
	 * Gets the c o n t e n t_ s t a t_ d e l_ d t of this archive content.
	 *
	 * @return the c o n t e n t_ s t a t_ d e l_ d t of this archive content
	 */
	public Date getCONTENT_STAT_DEL_DT();

	/**
	 * Sets the c o n t e n t_ s t a t_ d e l_ d t of this archive content.
	 *
	 * @param CONTENT_STAT_DEL_DT the c o n t e n t_ s t a t_ d e l_ d t of this archive content
	 */
	public void setCONTENT_STAT_DEL_DT(Date CONTENT_STAT_DEL_DT);

	/**
	 * Gets the c r e a t_ u s r_ i d of this archive content.
	 *
	 * @return the c r e a t_ u s r_ i d of this archive content
	 */
	@AutoEscape
	public String getCREAT_USR_ID();

	/**
	 * Sets the c r e a t_ u s r_ i d of this archive content.
	 *
	 * @param CREAT_USR_ID the c r e a t_ u s r_ i d of this archive content
	 */
	public void setCREAT_USR_ID(String CREAT_USR_ID);

	/**
	 * Gets the c r e a t_ t s of this archive content.
	 *
	 * @return the c r e a t_ t s of this archive content
	 */
	public Date getCREAT_TS();

	/**
	 * Sets the c r e a t_ t s of this archive content.
	 *
	 * @param CREAT_TS the c r e a t_ t s of this archive content
	 */
	public void setCREAT_TS(Date CREAT_TS);

	/**
	 * Gets the l s t_ u p d t_ u s r_ i d of this archive content.
	 *
	 * @return the l s t_ u p d t_ u s r_ i d of this archive content
	 */
	@AutoEscape
	public String getLST_UPDT_USR_ID();

	/**
	 * Sets the l s t_ u p d t_ u s r_ i d of this archive content.
	 *
	 * @param LST_UPDT_USR_ID the l s t_ u p d t_ u s r_ i d of this archive content
	 */
	public void setLST_UPDT_USR_ID(String LST_UPDT_USR_ID);

	/**
	 * Gets the l s t_ u p d t_ t s of this archive content.
	 *
	 * @return the l s t_ u p d t_ t s of this archive content
	 */
	public Date getLST_UPDT_TS();

	/**
	 * Sets the l s t_ u p d t_ t s of this archive content.
	 *
	 * @param LST_UPDT_TS the l s t_ u p d t_ t s of this archive content
	 */
	public void setLST_UPDT_TS(Date LST_UPDT_TS);

	public boolean isNew();

	public void setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(ArchiveContent archiveContent);

	public int hashCode();

	public ArchiveContent toEscapedModel();

	public String toString();

	public String toXmlString();
}