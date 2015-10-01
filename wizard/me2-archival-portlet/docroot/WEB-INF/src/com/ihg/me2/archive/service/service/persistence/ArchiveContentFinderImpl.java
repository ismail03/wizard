package com.ihg.me2.archive.service.service.persistence;

import com.ihg.me2.archive.service.NoSuchArchiveContentException;
import com.ihg.me2.archive.service.model.ArchiveContent;
import com.ihg.me2.archive.service.model.impl.ArchiveContentImpl;
import com.ihg.me2.archive.service.model.impl.ArchiveContentModelImpl;
import com.ihg.me2.archive.service.service.ArchiveContentLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.service.persistence.BatchSession;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.List;

import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

public class ArchiveContentFinderImpl extends BasePersistenceImpl<ArchiveContent> implements ArchiveContentFinder {

	public static final String FINDER_CLASS_NAME_ENTITY = ArchiveContentImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
		".List";
	/**
	* Adds the archive content to the database. Also notifies the appropriate model listeners.
	*
	* @param archiveContent the archive content to add
	* @return the archive content that was added
	* @throws SystemException if a system exception occurred
	*/
	public List<com.ihg.me2.archive.service.model.ArchiveContent> addBatchArchiveContent(
		List<com.ihg.me2.archive.service.model.ArchiveContent> archiveContents)
		throws com.liferay.portal.kernel.exception.SystemException {
		Session session = null;
		try {
			session = openSession();
//			TransactionManager tm = (TransactionManager) InfrastructureUtil.getTransactionManager();
			BatchSession batchSession = BatchSessionUtil.getBatchSession();
			batchSession.setEnabled(true);
			for (ArchiveContent archiveContent2 : archiveContents) {
				ArchiveContent archiveContent = ArchiveContentLocalServiceUtil.createArchiveContent(archiveContent2.getCONTENT_ID());
				batchSession.update(session, archiveContent, false);	
				archiveContent.setNew(false);
				FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
				EntityCacheUtil.removeResult(ArchiveContentModelImpl.ENTITY_CACHE_ENABLED,
					ArchiveContentImpl.class, archiveContent.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
//		Session session = opens
		/*autoCommit = getSession().connection().getAutoCommit();
		getSession().connection().setAutoCommit(false);
		Transaction tx = getSession().beginTransaction();
		for (MyObject object : objectList){
		getSession().saveOrUpdate(object);
		}
		tx.commit();*/
		
		return archiveContents;
	}
}
