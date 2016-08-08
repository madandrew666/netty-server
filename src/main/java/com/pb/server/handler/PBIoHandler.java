package com.pb.server.handler;

import java.util.Map;

import com.server.constant.PBCONSTANT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.server.model.Message;
import com.pb.server.session.PBSession;
import com.pb.server.session.SessionManage;
import com.pb.server.util.ContexHolder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

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
            Message reply = null;
            switch (msg.getType()) {
                case 1:
                    reply = handlers.get(PBCONSTANT.LOGIN).process(Pbsession, msg);
                    break;
                case 2:
                    reply = handlers.get(PBCONSTANT.MESSAGE).process(Pbsession, msg);
                    break;
                case 5:
                    reply = handlers.get(PBCONSTANT.ACK).process(Pbsession, msg);
                default:
            }
            Pbsession.write(reply);
        }
    }

    public void setHandlers(Map<String, PBRequestHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Connected from:" + ctx.channel().remoteAddress());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("unregistered");
    }


}
