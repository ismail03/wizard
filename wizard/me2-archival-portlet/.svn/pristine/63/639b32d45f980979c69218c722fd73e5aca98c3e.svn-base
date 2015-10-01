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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * The interface for the archive content local service.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Sandip Patel
 * @see ArchiveContentLocalServiceUtil
 * @see com.ihg.me2.archive.service.service.base.ArchiveContentLocalServiceBaseImpl
 * @see com.ihg.me2.archive.service.service.impl.ArchiveContentLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ArchiveContentLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ArchiveContentLocalServiceUtil} to access the archive content local service. Add custom service methods to {@link com.ihg.me2.archive.service.service.impl.ArchiveContentLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the archive content to the database. Also notifies the appropriate model listeners.
	*
	* @param archiveContent the archive content to add
	* @return the archive content that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.ihg.me2.archive.service.model.ArchiveContent addArchiveContent(
		com.ihg.me2.archive.service.model.ArchiveContent archiveContent)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates a new archive content with the primary key. Does not add the archive content to the database.
	*
	* @param CONTENT_ID the primary key for the new archive content
	* @return the new archive content
	*/
	public com.ihg.me2.archive.service.model.ArchiveContent createArchiveContent(
		java.lang.String CONTENT_ID);

	/**
	* Deletes the archive content with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CONTENT_ID the primary key of the archive content to delete
	* @throws PortalException if a archive content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteArchiveContent(java.lang.String CONTENT_ID)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the archive content from the database. Also notifies the appropriate model listeners.
	*
	* @param archiveContent the archive content to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteArchiveContent(
		com.ihg.me2.archive.service.model.ArchiveContent archiveContent)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the archive content with the primary key.
	*
	* @param CONTENT_ID the primary key of the archive content to get
	* @return the archive content
	* @throws PortalException if a archive content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.ihg.me2.archive.service.model.ArchiveContent getArchiveContent(
		java.lang.String CONTENT_ID)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets a range of all the archive contents.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of archive contents to return
	* @param end the upper bound of the range of archive contents to return (not inclusive)
	* @return the range of archive contents
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.ihg.me2.archive.service.model.ArchiveContent> getArchiveContents(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the number of archive contents.
	*
	* @return the number of archive contents
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getArchiveContentsCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the archive content in the database. Also notifies the appropriate model listeners.
	*
	* @param archiveContent the archive content to update
	* @return the archive content that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.ihg.me2.archive.service.model.ArchiveContent updateArchiveContent(
		com.ihg.me2.archive.service.model.ArchiveContent archiveContent)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the archive content in the database. Also notifies the appropriate model listeners.
	*
	* @param archiveContent the archive content to update
	* @param merge whether to merge the archive content with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the archive content that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.ihg.me2.archive.service.model.ArchiveContent updateArchiveContent(
		com.ihg.me2.archive.service.model.ArchiveContent archiveContent,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.ihg.me2.archive.service.model.ArchiveContent> addBatchArchiveContent(
		java.util.List<com.ihg.me2.archive.service.model.ArchiveContent> archiveContents)
		throws com.liferay.portal.kernel.exception.SystemException;
}