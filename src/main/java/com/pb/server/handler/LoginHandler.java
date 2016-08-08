package com.pb.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pb.server.session.PBSession;
import com.pb.server.session.SessionManage;
import com.pb.server.util.ContexHolder;
import com.server.constant.PBCONSTANT;
import com.server.model.Message;
import com.server.model.loginMsg;

public class LoginHandler implements PBRequestHandler {
	private static Logger logger = LoggerFactory.getLogger(LoginHandler.class);

	@Override
	public Message process(PBSession session, Message msg) {
		Message reply = new Message();
		reply.setReceiver_uid(msg.getSender_uid());
		reply.setTitle(PBCONSTANT.LOGIN_REPLY);
		reply.setSender_uid(PBCONSTANT.SYSTEM);
		reply.setType(PBCONSTANT.LOGIN_REPLY);
		reply.setTime(System.currentTimeMillis());
		if (!(msg instanceof loginMsg)) {
			reply.setContent(PBCONSTANT.FAIL);
			return reply;
		}
		SessionManage sessionManager = (SessionManage) ContexHolder
				.getBean("pbSessionManage");
		loginMsg loginmsg = (loginMsg) msg;
		logger.info("Received loginMsg:"+msg.toString());
		session.setChannel(loginmsg.getChannel());
		session.setClientVersion(loginmsg.getClientVersion());
		session.setDeviceId(loginmsg.getDeviceId());
		session.setDeviceModel(loginmsg.getDeviceModel());
		session.setUid(loginmsg.getSender_uid());
		session.setSystemVersion(loginmsg.getSystemVersion());
		if (msg.getSender_uid().equals("test1")
				&& (msg.getContent().equals("123"))) {
			reply.setContent(PBCONSTANT.SUCCESS);
			PBSession oldsession = sessionManager.get(msg.getSender_uid());
			if (oldsession == null)
				sessionManager.add(msg.getSender_uid(), session);
			else if (oldsession == session) {
			} else {
				sendForceOffLine(oldsession);
				sessionManager.add(msg.getSender_uid(), session);
			}
		} else if (msg.getSender_uid().equals("test2")
				&& (msg.getContent().equals("123"))) {
			reply.setContent(PBCONSTANT.SUCCESS);
			sessionManager.add(msg.getSender_uid(), session);
		} else {
			reply.setContent(PBCONSTANT.FAIL);
		}
		return reply;
	}

	public void sendForceOffLine(PBSession session) {
		logger.info("Force offline:" + session.getUid() + " at "
				+ session.getSession().remoteAddress() + " on "
				+ session.getDeviceId());
		Message force = new Message();
		force.setType(PBCONSTANT.FORCEOFFLINE);
		force.setTitle(PBCONSTANT.FORCEOFFLINE);
		force.setContent(PBCONSTANT.FORCEOFFLINE);
		force.setReceiver_uid(session.getUid());
		force.setSender_uid(PBCONSTANT.SYSTEM);
		force.setTime(System.currentTimeMillis());
		session.getSession().writeAndFlush(force);

	}
}
