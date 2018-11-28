package com.sparrow.log.file;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create by harry 2018/11/27 上午10:01
 */
public class InnerStateManageTest {
    public static void main(String[] args) {
        Logger logger= LoggerFactory.getLogger(InnerStateManageTest.class);
        // print internal state
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);

        logger.error("error");
    }
}
