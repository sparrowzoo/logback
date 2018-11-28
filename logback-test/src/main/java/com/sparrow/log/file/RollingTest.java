package com.sparrow.log.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create by harry 2018/11/26 下午3:38
 */
public class RollingTest {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(RollingTest.class);
        while (true) {
            logger.info("info {}",System.currentTimeMillis());
        }
    }
}
