package jar.common;

import io.netty.channel.Channel;

public interface MessageHandler<T extends Message> {
	/*
	 * handler处理接收到的消息 ，channel转发
	 */
	void excute(Channel channel, T message);

	/*
	 * 消息类型
	 */
	String getType();
}
