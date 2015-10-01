package com.ihg.me2.archive.deleteutil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class IndexerLogger {
	private static final int MAX_FILES = 25;
	private static final String MAX_FILE_SIZE = "512000KB"; // 50 MB
	private static final Level LOG_LEVEL = Level.DEBUG;
	private static final String APPENDER_NAME = "Indexer";
	private static RollingFileAppender appender;
	private static Map<String, Logger> loggerMap = new HashMap<String, Logger>();

	// private static String
	// LOGGER_PATH="/apps/merlin/log4j/Indexer/merlin-ndexer.log";

	private static String LOGGER_PATH = "C:/IHG/Project/liferay6.2/bundles/tomcat-7.0.42/logs/merlin/merlin-ndexer.log";

	/**
	 * Default constructor.
	 */
	private IndexerLogger() {
	}

	/**
	 * @return Logger object.
	 */
	public static Logger getLogger() {
		return getLogger("Indexer", LOG_LEVEL);
	}

	/**
	 * Get logger using Class.
	 * 
	 * @param clazz
	 *            - class
	 * @return logger
	 */
	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName(), LOG_LEVEL);
	}

	/**
	 * Get logger by name with specified logger level.
	 * 
	 * @param name
	 *            - logger name
	 * @param level
	 *            - logger level
	 * @return logger
	 */
	public static synchronized Logger getLogger(String name,
			org.apache.log4j.Level level) {
		Logger logger = loggerMap.get(name);
		if (logger == null) {
			logger = Logger.getLogger(name);
			logger.setLevel(level);
			if (appender == null) {
				try {
					appender = new RollingFileAppender(new PatternLayout(
							"%-5p %c.%M[%L] %d - %m%n"), LOGGER_PATH, true);
					appender.setName(APPENDER_NAME);
					appender.setThreshold(level);
					appender.setMaxBackupIndex(MAX_FILES);
					appender.setMaxFileSize(MAX_FILE_SIZE);
					logger.addAppender(appender);
					loggerMap.put(name, logger);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				logger.addAppender(appender);
				loggerMap.put(name, logger);
			}
		}
		return logger;
	}
}
