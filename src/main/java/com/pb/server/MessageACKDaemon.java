package com.pb.server;

import com.pb.server.util.MessageHolder;
import com.server.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DW on 2016/8/8.
 */
public class MessageACKDaemon implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(MessageACKDaemon.class);
    @Override
    public void run() {
        while(true){
            System.out.println("MessageACKDaemon Running!");
            String msg_key = MessageHolder.rec_messages.poll();
            if(msg_key == null) {
                try {
                    MessageHolder.rec_messages.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                Message msg = MessageHolder.send_messages.remove(msg_key);
                if(msg != null){
                    logger.info("Insert messge :" + msg.toString());
                    //TODO:从redis插入MYsql
                }
            }
        }
    }
}
