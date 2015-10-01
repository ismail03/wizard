package com.ihg.me2.archive.scheduler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ihg.me2.archive.service.model.ArchiveContent;
import com.ihg.me2.archive.service.service.ArchiveContentLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListenerException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ArchiveContentLocalServiceUtil.class)
public class ArchivalMonitorSchedulerTest {

	@Test
	public void testArchiveContentMonitorEntry() throws ParseException,
			SystemException, MessageListenerException {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		Date today = null;// format.parse("10-28-2014");
		Date lastmodifeddate = format.parse("10-19-2014");
		Date deletedate = null;// format.parse("10-28-2014");
		Date expireddate = format.parse("10-20-2014");
		ArchiveContent mockportaldeletetable = mock(ArchiveContent.class);
		when(mockportaldeletetable.getCONTENT_STAT_MOD_DT()).thenReturn(
				lastmodifeddate);
		when(mockportaldeletetable.getCONTENT_STAT_DEL_DT()).thenReturn(deletedate);
		when(mockportaldeletetable.getCONTENT_STAT_EXPIRED_DT()).thenReturn(expireddate);
		when(mockportaldeletetable.getCONTENT_TYP()).thenReturn(null);
		List<ArchiveContent> contents = new ArrayList<ArchiveContent>();
		contents.add(mockportaldeletetable);

		PowerMockito.mockStatic(ArchiveContentLocalServiceUtil.class);

		PowerMockito.when(ArchiveContentLocalServiceUtil.getArchiveContents(0, 10))
				.thenReturn(contents);

		System.out.println("mockportaldeletetable:-->"+mockportaldeletetable.getCONTENT_ID());
		/*ArchivalScheduler ac = new ArchivalScheduler();
		Message message = new Message();
		ac.receive(message);*/
		PowerMockito.verifyStatic();
	}
}
