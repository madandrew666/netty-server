package com.pb.server.filter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pb.server.util.ByteObjConverter;
import com.server.model.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MessageDecoder extends ByteToMessageDecoder {
	private static Logger logger = LoggerFactory
			.getLogger(MessageDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf inbuf,
			List<Object> out) throws Exception {
		byte[] bytes = new byte[inbuf.readableBytes()];
		inbuf.readBytes(bytes);
		out.add((Message)ByteObjConverter.ByteToObject(bytes));
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		logger.info("Connected from:" + ctx.channel().remoteAddress());
	}

}
