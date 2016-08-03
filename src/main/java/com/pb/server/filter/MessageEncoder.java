package com.pb.server.filter;

import com.pb.server.util.ByteObjConverter;
import com.server.model.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf outbuf)
			throws Exception {
			byte[] bytes = ByteObjConverter.ObjectToByte(msg);
			outbuf.writeBytes(bytes);
			ctx.flush();
	}

}
