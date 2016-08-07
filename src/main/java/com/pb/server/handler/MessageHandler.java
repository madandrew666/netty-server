package com.pb.server.handler;

import com.server.model.Message;
import com.pb.server.session.PBSession;
import com.pb.server.session.SessionManage;
import com.pb.server.util.ContexHolder;
import com.server.constant.PBCONSTANT;

public class MessageHandler implements PBRequestHandler {
	@Override
	public Message process(PBSession session, Message msg) {
		SessionManage sessionManager = (SessionManage) ContexHolder
				.getBean("pbSessionManage");
		PBSession receiver_session = sessionManager.get(msg.get("r_uid"));
		Message reply = new Message();
		reply.setType(PBCONSTANT.MESSAGE_REPLY_FLAG);
		if (receiver_session != null && receiver_session.getSession().isActive()) {
			receiver_session.getSession().writeAndFlush(msg);
			reply.setParam("r_uid",msg.get("s_uid"));
			reply.setParam("st","sc");
		} else {
			msg.setParam("st","fl");
		}
		reply.setParam("s_uid",PBCONSTANT.SYSTEM);
		return reply;
	}
}
