package com.pb.server.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.server.model.Message;
import com.pb.server.session.PBSession;
import com.pb.server.session.SessionManage;
import com.pb.server.util.ContexHolder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
@Sharable
public class PBIoHandler extends SimpleChannelInboundHandler<Message> {
	private static Logger logger = LoggerFactory.getLogger(PBIoHandler.class);
	private Map<String, PBRequestHandler> handlers;

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Message msg)
			throws Exception {
		logger.info("Received from " + ctx.channel().remoteAddress() + " "
				+ msg.toString());
		if (msg != null) {
			PBSession Pbsession = new PBSession(ctx.channel());
			Message reply = handlers.get(msg.getType()).process(Pbsession, msg);
			Pbsession.write(reply);
		}
	}

	public void setHandlers(Map<String, PBRequestHandler> handlers) {
		this.handlers = handlers;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("Connected from:"+ctx.channel().remoteAddress());
	}

}
