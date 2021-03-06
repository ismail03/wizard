package com.ihg.me2.archive.scheduler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ihg.me2.archive.expireutil.ExpireSchedulerUtil;
import com.ihg.me2.archive.expireutil.SchedulerConstant;
import com.ihg.me2.archive.service.model.ArchiveContent;
import com.ihg.me2.archive.service.service.ArchiveContentLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ JournalArticleLocalServiceUtil.class,
		ArchiveContentLocalServiceUtil.class, ExpireSchedulerUtil.class,
		MessageBusUtil.class, String.class, Message.class })
public class ExpireSchedulerWebContentTest {

	public ArchiveContent getExpiredWebContentArchive(int i) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date currentDate = null;
		try {
			currentDate = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date modifiedDate = new DateTime().minusDays(50).toDate();
		Date notifidDate = new DateTime().minusDays(20).toDate();

		ArchiveContent mockArchive = mock(ArchiveContent.class);
		when(mockArchive.getCONTENT_ID()).thenReturn(String.valueOf(i));
		when(mockArchive.getCONTENT_TYP()).thenReturn("News");
		when(mockArchive.getAUTHOR_EMAIL_ID()).thenReturn("test@gmail.com");
		when(mockArchive.getAUTHOR_NM()).thenReturn("testing");
		when(mockArchive.getCONTENT_STAT_EXPIRED_DT()).thenReturn(currentDate);
		when(mockArchive.getCONTENT_STAT_MOD_DT()).thenReturn(modifiedDate);
		when(mockArchive.getCONTENT_STAT_NOTIFIED_DT()).thenReturn(notifidDate);
		when(mockArchive.getCONTENT_STAT_CD()).thenReturn("Notified");
		return mockArchive;
	}

	// Expire WebContent SuccessTest
	// Changes the status to Expired in Archive Content
	@Test(expected = NullPointerException.class)
	public void expireWebContentSuccessTest() throws Exception {
		List<ArchiveContent> expiredWebContentArchives = new ArrayList<ArchiveContent>();

		ArchiveContent expiredArchive = getExpiredWebContentArchive(1001);
		expiredWebContentArchives.add(expiredArchive);

		// mock journal article
		Date modifiedDate = new DateTime().minusDays(50).toDate();

		JournalArticle mockJournalArticle = mock(JournalArticle.class);
		when(mockJournalArticle.getPrimaryKey()).thenReturn(Long.valueOf(1001));
		when(mockJournalArticle.getGroupId()).thenReturn(Long.valueOf(1080));
		when(mockJournalArticle.getModifiedDate()).thenReturn(modifiedDate);

		PowerMockito.mockStatic(JournalArticleLocalServiceUtil.class);
		when(JournalArticleLocalServiceUtil.getJournalArticle(1001))
				.thenReturn(mockJournalArticle);

		// mock Archive Content
		ArchiveContent mockExpiredArchive = getExpiredWebContentArchive(1001);
		when(mockExpiredArchive.getCONTENT_STAT_CD()).thenReturn(
				SchedulerConstant.EXPIRED);

		PowerMockito.mockStatic(ArchiveContentLocalServiceUtil.class);
		when(
				ArchiveContentLocalServiceUtil
						.updateArchiveContent(mockExpiredArchive)).thenReturn(
				mockExpiredArchive);

		PowerMockito.doThrow(new NullPointerException()).when(
				MessageBusUtil.class, "sendMessage", Mockito.any(String.class),
				Mockito.any(Message.class));

	}

	// Delete updated web content SuccessTest
	// Changes the status to Expired in Archive Content
	@Test(expected = NullPointerException.class)
	public void deleteUpdatedWebContentSuccessTest() throws Exception {
		List<ArchiveContent> expiredWebContentArchives = new ArrayList<ArchiveContent>();

		ArchiveContent mockArchive = mock(ArchiveContent.class);
		mockArchive = getExpiredWebContentArchive(1001);
		when(mockArchive.getCONTENT_STAT_MOD_DT()).thenReturn(
				new DateTime().minusDays(10).toDate());
		expiredWebContentArchives.add(mockArchive);

		// mock journal article
		Date modifiedDate = new DateTime().minusDays(10).toDate();

		JournalArticle mockJournalArticle = mock(JournalArticle.class);
		when(mockJournalArticle.getPrimaryKey()).thenReturn(Long.valueOf(1001));
		when(mockJournalArticle.getGroupId()).thenReturn(Long.valueOf(1080));
		when(mockJournalArticle.getModifiedDate()).thenReturn(modifiedDate);

		PowerMockito.mockStatic(JournalArticleLocalServiceUtil.class);
		when(JournalArticleLocalServiceUtil.getJournalArticle(1001))
				.thenReturn(mockJournalArticle);

		getExpiredWebContentArchive(1001);

		PowerMockito.mockStatic(ArchiveContentLocalServiceUtil.class);
		PowerMockito.doThrow(new NullPointerException()).when(
				ArchiveContentLocalServiceUtil.class, "deleteArchiveContent",
				Mockito.any(ArchiveContent.class));

		ExpireSchedulerUtil.separateContents(expiredWebContentArchives);
	}

	// WebContent Failure Test
	// Remove webcontent from the Journal Article
	@Test
	public void testForNullExpiredWebContents() throws SystemException,
			ParseException, PortalException {
		List<ArchiveContent> expiredWebContentArchives = new ArrayList<ArchiveContent>();

		ArchiveContent expiredArchive = getExpiredWebContentArchive(1001);
		expiredWebContentArchives.add(expiredArchive);

		// mock journal article
		PowerMockito.mockStatic(JournalArticleLocalServiceUtil.class);
		when(JournalArticleLocalServiceUtil.getJournalArticle(1001))
				.thenReturn(null);

		// mock Archive Content
		ArchiveContent mockExpiredArchive = getExpiredWebContentArchive(1001);
		when(mockExpiredArchive.getCONTENT_STAT_CD()).thenReturn(
				"DeletedFromPortal");

		PowerMockito.mockStatic(ArchiveContentLocalServiceUtil.class);
		when(
				ArchiveContentLocalServiceUtil
						.updateArchiveContent(mockExpiredArchive)).thenReturn(
				mockExpiredArchive);

		ExpireSchedulerUtil.separateContents(expiredWebContentArchives);
	}
}
