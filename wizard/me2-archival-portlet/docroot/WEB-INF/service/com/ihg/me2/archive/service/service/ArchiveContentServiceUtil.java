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
 * The utility for the archive content remote service. This utility wraps {@link com.ihg.me2.archive.service.service.impl.ArchiveContentServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Sandip Patel
 * @see ArchiveContentService
 * @see com.ihg.me2.archive.service.service.base.ArchiveContentServiceBaseImpl
 * @see com.ihg.me2.archive.service.service.impl.ArchiveContentServiceImpl
 * @generated
 */
public class ArchiveContentServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.ihg.me2.archive.service.service.impl.ArchiveContentServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void clearService() {
		_service = null;
	}

	public static ArchiveContentService getService() {
		if (_service == null) {
			Object object = PortletBeanLocatorUtil.locate(ClpSerializer.SERVLET_CONTEXT_NAME,
					ArchiveContentService.class.getName());
			ClassLoader portletClassLoader = (ClassLoader)PortletBeanLocatorUtil.locate(ClpSerializer.SERVLET_CONTEXT_NAME,
					"portletClassLoader");

			ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(object,
					ArchiveContentService.class.getName(), portletClassLoader);

			_service = new ArchiveContentServiceClp(classLoaderProxy);

			ClpSerializer.setClassLoader(portletClassLoader);

			ReferenceRegistry.registerReference(ArchiveContentServiceUtil.class,
				"_service");
			MethodCache.remove(ArchiveContentService.class);
		}

		return _service;
	}

	public void setService(ArchiveContentService service) {
		MethodCache.remove(ArchiveContentService.class);

		_service = service;

		ReferenceRegistry.registerReference(ArchiveContentServiceUtil.class,
			"_service");
		MethodCache.remove(ArchiveContentService.class);
	}

	private static ArchiveContentService _service;
}