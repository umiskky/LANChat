package org.umiskky.service;

import org.umiskky.service.task.InitTask;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/19
 */
public class InitService implements Runnable{

    public InitService() {
    }

    private void initService(){
        InitTask.importConfig();
        InitTask.initDatabase();
        InitTask.cleanDatabase();
        InitTask.initNetworkCards();
        InitTask.initEthernetTypeCode();
        InitTask.launchNetworkCardTasks();
        InitTask.launchSocketServerTask();
    }

    @Override
    public void run() {
        initService();

    }
}
