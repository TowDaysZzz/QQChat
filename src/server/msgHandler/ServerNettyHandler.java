package server.msgHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Component
@ChannelHandler.Sharable
public class ServerNettyHandler extends ChannelInboundHandlerAdapter {
	@Autowired
	private ServerChannelManager servermanage;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		servermanage.add(ctx.channel());
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) {
		servermanage.remove(ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error("[exceptionCaught][连接({})发生异常]", ctx.channel().id(), cause);
		ctx.channel().close();
	}

}
