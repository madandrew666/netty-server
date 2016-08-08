package com.pb.server.handler;

import com.pb.server.session.PBSession;
import com.pb.server.session.SessionManage;
import com.pb.server.util.ContexHolder;
import com.server.constant.PBCONSTANT;
import com.server.model.Message;

public class LogoutHandler implements PBRequestHandler {

	@Override
	public Message process(PBSession session, Message msg) {

		/*if (msg.getType().equals(PBCONSTANT.LOGOUT)) {
			SessionManage sessionManager = (SessionManage) ContexHolder
					.getBean("pbSessionManage");
			session.close();
			sessionManager.remove(msg.getSender_uid());
		}*/

		return null;
	}

}
