package com.ihg.me2.archive.messagebus;


import com.ihg.me2.archive.service.model.ArchiveContent;
import com.ihg.me2.archive.deleteutil.SchedulerConstant;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.util.ContentUtil;
import com.liferay.util.portlet.PortletProps;

import javax.mail.internet.InternetAddress;

public class MailSender extends BaseMessageListener {

	@Override
	protected void doReceive(Message m) throws Exception {
		ArchiveContent a = (ArchiveContent) m.getPayload();
		String tmplpath = (String) m.getValues().get("tmpl");
		InternetAddress fromAddress = new InternetAddress();
		fromAddress.setAddress(PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		InternetAddress toAddress = new InternetAddress();

		if (a.getAUTHOR_EMAIL_ID() == null || a.getAUTHOR_EMAIL_ID().equals("")) {
			toAddress.setAddress(PortletProps
					.get(SchedulerConstant.DEFAULT_SENDER_INBOX));
		} else {
			toAddress.setAddress(a.getAUTHOR_EMAIL_ID());
		}
		String subjectTemplate = PortletProps
				.get(SchedulerConstant.DEFAULT_REMAINDER_MAIL_SUBJECT);
		String subjectLine = StringUtil.replace(subjectTemplate,
				"[$CONTENT_NAME$]", a.getCONTENT_NM());
		String body = ContentUtil.get(tmplpath, true);

		body = StringUtil.replace(
				body,
				new String[] { "[$EMAIL_TO$]", "[$AUTHOR_NAME$]",
						"[$CONTENT_NAME$]",
 
				},
				new String[] { a.getAUTHOR_EMAIL_ID(), a.getCONTENT_NM(),
						a.getCONTENT_NM() });
		MailMessage message = new MailMessage(fromAddress, toAddress,
				subjectLine, body, true);

		MailServiceUtil.sendEmail(message);

	}

}
