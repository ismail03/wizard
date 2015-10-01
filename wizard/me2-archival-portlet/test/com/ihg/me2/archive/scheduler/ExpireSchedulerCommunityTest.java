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
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

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
@PrepareForTest({ GroupLocalServiceUtil.class,
		ArchiveContentLocalServiceUtil.class, AssetEntryLocalServiceUtil.class,
		String.class, DLFileEntry.class })
public class ExpireSchedulerCommunityTest {
	public ArchiveContent getExpiredCommunity(int i) {
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
		when(mockArchive.getCONTENT_TYP()).thenReturn("initiative");
		when(mockArchive.getAUTHOR_EMAIL_ID()).thenReturn("test@gmail.com");
		when(mockArchive.getAUTHOR_NM()).thenReturn("testing");
		when(mockArchive.getCONTENT_STAT_DEL_DT()).thenReturn(currentDate);
		when(mockArchive.getCONTENT_STAT_MOD_DT()).thenReturn(modifiedDate);
		when(mockArchive.getCONTENT_STAT_NOTIFIED_DT()).thenReturn(notifidDate);
		when(mockArchive.getCONTENT_STAT_CD()).thenReturn("Notified");
		return mockArchive;
	}

	@Test(expected = NullPointerException.class)
	public void expireCommunitySuccessTest() throws Exception {
		List<ArchiveContent> expiredCommunityArchives = new ArrayList<ArchiveContent>();

		ArchiveContent expiredArchive = getExpiredCommunity(1001);
		expiredCommunityArchives.add(expiredArchive);

		// mock Group
		Date modifiedDate = new DateTime().minusDays(50).toDate();

		Group mockGroup = mock(Group.class);
		when(mockGroup.getPrimaryKey()).thenReturn(Long.valueOf(1001));
		when(mockGroup.getClassName()).thenReturn("className");
		when(mockGroup.getGroupId()).thenReturn(1094L);
		when(mockGroup.getCreatorUserId()).thenReturn(1091L);

		PowerMockito.mockStatic(GroupLocalServiceUtil.class);
		when(GroupLocalServiceUtil.getGroup(1001)).thenReturn(mockGroup);

		// mock AssetEntry
		AssetEntry mockAssetEntry = mock(AssetEntry.class);
		when(mockAssetEntry.getPrimaryKey()).thenReturn(Long.valueOf(5555));
		when(mockAssetEntry.getGroupId()).thenReturn(Long.valueOf(1080));
		when(mockAssetEntry.getClassPK()).thenReturn(Long.valueOf(1001));
		when(mockAssetEntry.getUserId()).thenReturn(5555L);
		when(mockAssetEntry.getClassName()).thenReturn("ClassName");
		when(mockAssetEntry.getModifiedDate()).thenReturn(modifiedDate);
		when(mockAssetEntry.getTagNames()).thenReturn(
				new String[] { "E1", "E2" });

		String tagNames[] = { SchedulerConstant.EXPIRED };

		PowerMockito.mockStatic(AssetEntryLocalServiceUtil.class);
		when(
				AssetEntryLocalServiceUtil.getEntry(mockGroup.getClassName(),
						mockGroup.getGroupId())).thenReturn(mockAssetEntry);

		when(
				AssetEntryLocalServiceUtil.updateEntry(
						mockGroup.getCreatorUserId(),
						mockAssetEntry.getGroupId(),
						mockAssetEntry.getClassName(), mockGroup.getGroupId(),
						null, tagNames)).thenReturn(mockAssetEntry);

		ArchiveContent mockArchiveContent = mock(ArchiveContent.class);
		mockArchiveContent = getExpiredCommunity(1001);
		when(mockArchiveContent.getCONTENT_STAT_CD()).thenReturn("Expired");

		PowerMockito.mockStatic(ArchiveContentLocalServiceUtil.class);
		when(
				ArchiveContentLocalServiceUtil
						.updateArchiveContent(mockArchiveContent)).thenReturn(
				mockArchiveContent);

		PowerMockito.doThrow(new NullPointerException()).when(
				MessageBusUtil.class, "sendMessage", Mockito.any(String.class),
				Mockito.any(Message.class));
		ExpireSchedulerUtil.separateContents(expiredCommunityArchives);
	}

	// Community Failure Test
	// Remove Community from the Group
	@Test
	public void testForNullExpiredDocuments() throws SystemException,
			ParseException, PortalException {
		List<ArchiveContent> expiredCommunityArchives = new ArrayList<ArchiveContent>();

		ArchiveContent expiredArchive = getExpiredCommunity(1001);
		expiredCommunityArchives.add(expiredArchive);

		// mock Group
		PowerMockito.mockStatic(GroupLocalServiceUtil.class);
		when(GroupLocalServiceUtil.getGroup(1001)).thenReturn(null);

		// mock Archive Content
		ArchiveContent mockExpiredArchive = getExpiredCommunity(1001);
		when(mockExpiredArchive.getCONTENT_STAT_CD()).thenReturn(
				SchedulerConstant.DELETEDFROMPORTAL);

		PowerMockito.mockStatic(ArchiveContentLocalServiceUtil.class);
		when(
				ArchiveContentLocalServiceUtil
						.updateArchiveContent(mockExpiredArchive)).thenReturn(
				mockExpiredArchive);

		ExpireSchedulerUtil.separateContents(expiredCommunityArchives);
	}

}
