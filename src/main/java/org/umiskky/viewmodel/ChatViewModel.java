package org.umiskky.viewmodel;

import org.umiskky.model.DataModel;

/**
 * @author umiskky
 * @version 0.0.1
 * @date 2021/04/13
 */
public class ChatViewModel {

    private DataModel dataModel;

    public ChatViewModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public static void quit(){
        System.exit(0);
    }

    public static void switchSC(){

    }

    public static void switchSF(){

    }

}
