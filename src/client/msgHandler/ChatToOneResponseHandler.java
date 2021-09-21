package client.msgHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import jar.common.ChatToOneResponseMsg;
import jar.common.MessageHandler;

@Component
public class ChatToOneResponseHandler implements MessageHandler<ChatToOneResponseMsg> {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void excute(Channel channel, ChatToOneResponseMsg message) {
		// TODO Auto-generated method stub
		String content = message.getContent();
		logger.info("[ChatToOneResponseHandler][客户端接收到的消息是：({})]", content);

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return ChatToOneResponseMsg.getType();
	}

}
