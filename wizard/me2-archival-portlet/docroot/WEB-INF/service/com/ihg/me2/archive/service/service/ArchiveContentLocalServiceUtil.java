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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the archive content local service. This utility wraps {@link com.ihg.me2.archive.service.service.impl.ArchiveContentLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Sandip Patel
 * @see ArchiveContentLocalService
 * @see com.ihg.me2.archive.service.service.base.ArchiveContentLocalServiceBaseImpl
 * @see com.ihg.me2.archive.service.service.impl.ArchiveContentLocalServiceImpl
 * @generated
 */
public class ArchiveContentLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.ihg.me2.archive.service.service.impl.ArchiveContentLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the archive content to the database. Also notifies the appropriate model listeners.
	*
	* @param archiveContent the archive content to add
	* @return the archive content that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.ihg.me2.archive.service.model.ArchiveContent addArchiveContent(
		com.ihg.me2.archive.service.model.ArchiveContent archiveContent)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addArchiveContent(archiveContent);
	}

	/**
	* Creates a new archive content with the primary key. Does not add the archive content to the database.
	*
	* @param CONTENT_ID the primary key for the new archive content
	* @return the new archive content
	*/
	public static com.ihg.me2.archive.service.model.ArchiveContent createArchiveContent(
		java.lang.String CONTENT_ID) {
		return getService().createArchiveContent(CONTENT_ID);
	}

	/**
	* Deletes the archive content with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CONTENT_ID the primary key of the archive content to delete
	* @throws PortalException if a archive content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteArchiveContent(java.lang.String CONTENT_ID)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteArchiveContent(CONTENT_ID);
	}

	/**
	* Deletes the archive content from the database. Also notifies the appropriate model listeners.
	*
	* @param archiveContent the archive content to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteArchiveContent(
		com.ihg.me2.archive.service.model.ArchiveContent archiveContent)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteArchiveContent(archiveContent);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the archive content with the primary key.
	*
	* @param CONTENT_ID the primary key of the archive content to get
	* @return the archive content
	* @throws PortalException if a archive content with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.ihg.me2.archive.service.model.ArchiveContent getArchiveContent(
		java.lang.String CONTENT_ID)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getArchiveContent(CONTENT_ID);
	}

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
	public static java.util.List<com.ihg.me2.archive.service.model.ArchiveContent> getArchiveContents(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArchiveContents(start, end);
	}

	/**
	* Gets the number of archive contents.
	*
	* @return the number of archive contents
	* @throws SystemException if a system exception occurred
	*/
	public static int getArchiveContentsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getArchiveContentsCount();
	}

	/**
	* Updates the archive content in the database. Also notifies the appropriate model listeners.
	*
	* @param archiveContent the archive content to update
	* @return the archive content that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.ihg.me2.archive.service.model.ArchiveContent updateArchiveContent(
		com.ihg.me2.archive.service.model.ArchiveContent archiveContent)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateArchiveContent(archiveContent);
	}

	/**
	* Updates the archive content in the database. Also notifies the appropriate model listeners.
	*
	* @param archiveContent the archive content to update
	* @param merge whether to merge the archive content with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the archive content that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.ihg.me2.archive.service.model.ArchiveContent updateArchiveContent(
		com.ihg.me2.archive.service.model.ArchiveContent archiveContent,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateArchiveContent(archiveContent, merge);
	}

	public static java.util.List<com.ihg.me2.archive.service.model.ArchiveContent> addBatchArchiveContent(
		java.util.List<com.ihg.me2.archive.service.model.ArchiveContent> archiveContents)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addBatchArchiveContent(archiveContents);
	}

	public static void clearService() {
		_service = null;
	}

	public static ArchiveContentLocalService getService() {
		if (_service == null) {
			Object object = PortletBeanLocatorUtil.locate(ClpSerializer.SERVLET_CONTEXT_NAME,
					ArchiveContentLocalService.class.getName());
			ClassLoader portletClassLoader = (ClassLoader)PortletBeanLocatorUtil.locate(ClpSerializer.SERVLET_CONTEXT_NAME,
					"portletClassLoader");

			ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(object,
					ArchiveContentLocalService.class.getName(),
					portletClassLoader);

			_service = new ArchiveContentLocalServiceClp(classLoaderProxy);

			ClpSerializer.setClassLoader(portletClassLoader);

			ReferenceRegistry.registerReference(ArchiveContentLocalServiceUtil.class,
				"_service");
			MethodCache.remove(ArchiveContentLocalService.class);
		}

		return _service;
	}

	public void setService(ArchiveContentLocalService service) {
		MethodCache.remove(ArchiveContentLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(ArchiveContentLocalServiceUtil.class,
			"_service");
		MethodCache.remove(ArchiveContentLocalService.class);
	}

	private static ArchiveContentLocalService _service;
}