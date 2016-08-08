package com.pb.server.pusher;

import com.pb.server.session.PBSessionManage;
import com.pb.server.util.ContexHolder;
import com.pb.server.util.MessageHolder;
import com.server.model.Message;

/**
 * Created by DW on 2016/8/8.
 */
public class PBMessagePusher implements MessagePusher {

    @Override
    public void push(Message msg){
        PBSessionManage sessionManager =(PBSessionManage) ContexHolder.getBean("sessionManager");
        String msg_key = msg.get("s_uid")+msg.getMsg_id();
        MessageHolder.send_messages.put(msg_key,msg);
        sessionManager.get(msg.get("r_uid")).write(msg);
    }
}
