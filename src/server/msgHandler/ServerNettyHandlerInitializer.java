package server.msgHandler;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;
import jar.common.MessageDispacher;
import jar.common.MsgObjectDecode;
import jar.common.MsgObjectEncode;

@Component
public class ServerNettyHandlerInitializer extends ChannelInitializer<Channel> {
	private static final int READ_TIMEOUT_SECONDS = 3 * 60;
	@Autowired
	private MessageDispacher messageDispacher;
	@Autowired
	private ServerNettyHandler serverNettyHandler;

	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)).addLast(new MsgObjectDecode())
				.addLast(new MsgObjectEncode()).addLast(messageDispacher).addLast(serverNettyHandler);

	}

}
