package com.ihg.me2.archive.expireutil;

import org.apache.log4j.Logger;

import com.ihg.community.indexer.CommunityIndexer;
import com.liferay.portal.model.Group;

public class ReIndexer extends CommunityIndexer{
	private final static Logger log = IndexerLogger
			.getLogger(ExpireSchedulerUtil.class);
	public void reIndex(Group grp){
		try {
			doReindex(grp);
		} catch (Exception e) {
			log.error(e);
		}
	}
}
