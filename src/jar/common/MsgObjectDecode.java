package jar.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

/*
 * 解码器
 */
public class MsgObjectDecode extends ByteToMessageDecoder {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		//
		in.markReaderIndex();
		if (in.readableBytes() <= 4) {
			return;
		}
		int len = in.readInt();
		if (len < 0) {
			throw new CorruptedFrameException("negative length:" + len);
		}
		if (in.readableBytes() < len) {
			in.resetReaderIndex();
			return;
		}

		byte[] content = new byte[len];
		in.readBytes(content);
		MsgObject msgObject = JSON.parseObject(content, MsgObject.class);
		out.add(msgObject);
		logger.info("[decode][连接({})解析到一条消息({})]", ctx.channel().id(), msgObject.toString());
	}
}
