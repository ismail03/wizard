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

package com.ihg.me2.archive.service.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Sandip Patel
 */
public class ArchiveContentFinderUtil {
	public static java.util.List<com.ihg.me2.archive.service.model.ArchiveContent> addBatchArchiveContent(
		java.util.List<com.ihg.me2.archive.service.model.ArchiveContent> archiveContents)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().addBatchArchiveContent(archiveContents);
	}

	public static ArchiveContentFinder getFinder() {
		if (_finder == null) {
			_finder = (ArchiveContentFinder)PortletBeanLocatorUtil.locate(com.ihg.me2.archive.service.service.ClpSerializer.SERVLET_CONTEXT_NAME,
					ArchiveContentFinder.class.getName());

			ReferenceRegistry.registerReference(ArchiveContentFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(ArchiveContentFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(ArchiveContentFinderUtil.class,
			"_finder");
	}

	private static ArchiveContentFinder _finder;
}