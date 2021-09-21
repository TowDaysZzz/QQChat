package jar.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/*
 * 编码器
 */
public class MsgObjectEncode extends MessageToByteEncoder<MsgObject> {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void encode(ChannelHandlerContext ctx, MsgObject msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		byte[] content = JSON.toJSONBytes(msg);
		out.writeInt(content.length);
		out.writeBytes(content);
		logger.info("[encode][连接({})][编码了一条消息({})]", ctx.channel().id(), msg.toString());

	}

}
