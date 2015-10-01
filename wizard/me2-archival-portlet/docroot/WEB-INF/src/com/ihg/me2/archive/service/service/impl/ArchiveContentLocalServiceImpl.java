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

package com.ihg.me2.archive.service.service.impl;

import com.ihg.me2.archive.service.service.base.ArchiveContentLocalServiceBaseImpl;
import com.ihg.me2.archive.service.service.persistence.ArchiveContentFinderUtil;

import java.util.List;

/**
 * The implementation of the archive content local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.ihg.me2.archive.service.service.ArchiveContentLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Sandip Patel
 * @see com.ihg.me2.archive.service.service.base.ArchiveContentLocalServiceBaseImpl
 * @see com.ihg.me2.archive.service.service.ArchiveContentLocalServiceUtil
 */
public class ArchiveContentLocalServiceImpl
	extends ArchiveContentLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.ihg.me2.archive.service.service.ArchiveContentLocalServiceUtil} to access the archive content local service.
	 */
	
	public List<com.ihg.me2.archive.service.model.ArchiveContent> addBatchArchiveContent(
			List<com.ihg.me2.archive.service.model.ArchiveContent> archiveContents)
			throws com.liferay.portal.kernel.exception.SystemException{
		return ArchiveContentFinderUtil.addBatchArchiveContent(archiveContents);
	}
}