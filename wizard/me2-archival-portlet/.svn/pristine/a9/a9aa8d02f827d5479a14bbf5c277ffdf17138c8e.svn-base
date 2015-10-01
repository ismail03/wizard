package com.ihg.me2.archive.scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ihg.me2.archive.deleteutil.DeleteSchedulerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import java.text.ParseException;
import java.util.Date;



import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ JournalArticleLocalServiceUtil.class,
		DLFileEntryLocalServiceUtil.class, DateTime.class })
public class DeleteSchedulerUtilTest {
	@Test
	public void testisWebContent() {
		// positive Test
		assertTrue(DeleteSchedulerUtil.isWebContent("dept_hot_topics"));
		assertTrue(DeleteSchedulerUtil.isWebContent("News"));
		assertTrue(DeleteSchedulerUtil.isWebContent("news"));
		assertTrue(DeleteSchedulerUtil.isWebContent("  news    "));
		assertTrue(DeleteSchedulerUtil.isWebContent("news    "));
		// Negative test
		assertFalse(DeleteSchedulerUtil.isWebContent(null));
		assertFalse(DeleteSchedulerUtil.isWebContent("      "));
		assertFalse(DeleteSchedulerUtil.isWebContent(""));
		assertFalse(DeleteSchedulerUtil.isWebContent("xxx"));
		assertFalse(DeleteSchedulerUtil.isWebContent("ss"));
	}

	@Test
	public void testisMedia() {
		// positive Test data
		assertTrue(DeleteSchedulerUtil.isMedia("Conf_resource"));
		assertTrue(DeleteSchedulerUtil.isMedia("Conf_Resource       "));
		assertTrue(DeleteSchedulerUtil.isMedia("      conf_resource       "));
		// Negatie Test Data
		assertFalse(DeleteSchedulerUtil.isMedia(null));
		assertFalse(DeleteSchedulerUtil.isMedia("         "));
		assertFalse(DeleteSchedulerUtil.isMedia(""));
		assertFalse(DeleteSchedulerUtil.isMedia("not a media"));
	}

	@Test
	public void testisCommunity() {
		// positive
		assertTrue(DeleteSchedulerUtil.isCommunity("teamspace "));
		assertTrue(DeleteSchedulerUtil.isCommunity("  conference "));
		assertTrue(DeleteSchedulerUtil.isCommunity("ConfErencE "));
		// Negative
		assertFalse(DeleteSchedulerUtil.isCommunity(null));
		assertFalse(DeleteSchedulerUtil.isCommunity("     "));
		assertFalse(DeleteSchedulerUtil.isCommunity(""));
	}

	@Test
	public void testgetModifiedDate() throws PortalException, SystemException,
			ParseException {
		Date d = new Date();
		// mocking journalArcticle
		JournalArticle mockJournalArticle = mock(JournalArticle.class);
		when(mockJournalArticle.getModifiedDate()).thenReturn(d);
		// mock journalLocalServiceUtil
		PowerMockito.mockStatic(JournalArticleLocalServiceUtil.class);
		when(JournalArticleLocalServiceUtil.getJournalArticle(1001))
				.thenReturn(mockJournalArticle);
		// mock DLfileEntry
		DLFileEntry mockDlFileEntry = mock(DLFileEntry.class);
		when(mockDlFileEntry.getModifiedDate()).thenReturn(d);
		// mock DLfileEntryLocalServiceUtil
		PowerMockito.mockStatic(DLFileEntryLocalServiceUtil.class);
		when(DLFileEntryLocalServiceUtil.getDLFileEntry(1002)).thenReturn(
				mockDlFileEntry);

		// Test cases for type Webcontent

		// positive test
		assertEquals(d,
				DeleteSchedulerUtil.getModifiedDate("webcontent", 1001L));
		assertEquals(d,
				DeleteSchedulerUtil.getModifiedDate("   Webcontent", 1001L));
		// negative test
		assertNull(DeleteSchedulerUtil.getModifiedDate("xxxx", 1001L));

	}

}
