package client.msgHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import jar.common.MessageDispacher;
import jar.common.MsgObjectDecode;
import jar.common.MsgObjectEncode;

@Component
public class ClientNettyInitializer extends ChannelInitializer<Channel> {

	/**
	 * 心跳超时时间
	 */
	private static final Integer READ_TIMEOUT_SECONDS = 60;

	@Autowired
	private MessageDispacher messageDispatcher;

	@Autowired
	private ClientNettyHandler clientNettyHander;

	@Override
	protected void initChannel(Channel ch) {
		ch.pipeline()
				// 空闲检测
				// 编码器
				// 空闲检测
				.addLast(new IdleStateHandler(READ_TIMEOUT_SECONDS, 0, 0))
				.addLast(new ReadTimeoutHandler(3 * READ_TIMEOUT_SECONDS)).addLast(new MsgObjectEncode())
				// 解码器
				.addLast(new MsgObjectDecode())
				// 消息分发器
				.addLast(messageDispatcher)
				// 客户端处理器
				.addLast(clientNettyHander);
	}
}
