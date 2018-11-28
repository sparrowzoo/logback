package com.sparrow.log.file;

import ch.qos.logback.core.BasicStatusManager;
import ch.qos.logback.core.LifeCycleManager;
import ch.qos.logback.core.status.InfoStatus;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import ch.qos.logback.core.status.Status;

/**
 * create by harry 2018/11/27 上午11:19
 */
public class BasicStatusManagerTest {
    public static void main(String[] args) {
        BasicStatusManager bsm = new BasicStatusManager();

        LifeCycleManager lifeCycleManager=new LifeCycleManager();

        OnConsoleStatusListener statusListener= new OnConsoleStatusListener();
        lifeCycleManager.register(statusListener);
        statusListener.start();

        bsm.add(statusListener);

        int i = 0;
        while (true) {
            bsm.add(new InfoStatus(i++ + "", BasicStatusManager.class));

            if (i > 20) {
                break;
            }
        }

        for (Status status : bsm.getCopyOfStatusList()) {
            System.out.println(status.toString());
        }
    }
}
