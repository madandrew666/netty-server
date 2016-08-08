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
		PBSession receiver_session = sessionManager.get(msg.getReceiver_uid());
		Message mes = new Message();
		mes.setTitle(PBCONSTANT.MESSAGE_REPLY);
		mes.setSender_uid(PBCONSTANT.SYSTEM);
		mes.setType(PBCONSTANT.MESSAGE_REPLY);
		if (receiver_session != null && receiver_session.getSession().isActive()) {
			receiver_session.getSession().writeAndFlush(msg);
			mes.setReceiver_uid(msg.getSender_uid());
			mes.setContent(PBCONSTANT.SUCCESS);
		} else {
			mes.setContent(PBCONSTANT.USER_OFFLINE);
		}
		mes.setTime(System.currentTimeMillis());
		return mes;
	}
}
