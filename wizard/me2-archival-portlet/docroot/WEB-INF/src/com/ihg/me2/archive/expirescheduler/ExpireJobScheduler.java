package com.ihg.me2.archive.expirescheduler;

import com.ihg.me2.archive.expireutil.ExpireSchedulerUtil;
import com.ihg.me2.archive.expireutil.IndexerLogger;
import com.ihg.me2.archive.expireutil.SchedulerConstant;
import com.ihg.me2.archive.service.model.ArchiveContent;
import com.ihg.me2.archive.service.service.ArchiveContentLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ExpireJobScheduler implements MessageListener {
	private final Logger log = IndexerLogger
			.getLogger(ExpireJobScheduler.class);

	public void receive(Message arg0) throws MessageListenerException {
		try {
			Date date = new Date();
			SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");
			Date currentDate;
			currentDate = formatDate.parse(formatDate.format(date));

			// Dynamic Query to return List of Archive_Content whose
			// expired_date = Todays date.

			Properties props = new Properties();
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream is = classLoader
					.getResourceAsStream(SchedulerConstant.PROPERTIES_PATH);
			props.load(is);
			String week = props.getProperty("expired-week");
			int noOfWeeks = Integer.parseInt(week);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			calendar.add(Calendar.WEEK_OF_YEAR, -noOfWeeks);

			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil
					.forClass(ArchiveContent.class);
			Criterion criterion = null;
			criterion = RestrictionsFactoryUtil.eq("status",
					SchedulerConstant.NOTIFIED);
			criterion = RestrictionsFactoryUtil.and(criterion,
					RestrictionsFactoryUtil.between("expired_date", formatDate
							.parse(formatDate.format(calendar.getTime())),
							currentDate));

			dynamicQuery.add(criterion);
			List<ArchiveContent> checkExpriedArchives = ArchiveContentLocalServiceUtil
					.dynamicQuery(dynamicQuery);
			// Checking whether modified date is between expired date and
			// notified date.
			if (checkExpriedArchives.size() != 0) {
				ExpireSchedulerUtil.separateContents(checkExpriedArchives);
			}

		} catch (ParseException e) {
			log.info("Not able to parse the given date");
			log.error(e);
		} catch (SystemException e) {
			log.error(e);
			log.info("Error while retrieving data from Database");
		} catch (IOException e) {
			log.error(e);
			log.info("Not able to find the property file");
		} catch (PortalException e) {
			log.error(e);
		}
	}

}
