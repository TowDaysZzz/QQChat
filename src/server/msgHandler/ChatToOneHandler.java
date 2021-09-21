package server.msgHandler;

import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import jar.common.ChatToOneRequestMsg;
import jar.common.MessageHandler;

@Component
public class ChatToOneHandler implements MessageHandler<ChatToOneRequestMsg> {

	@Override
	public void excute(Channel channel, ChatToOneRequestMsg message) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return ChatToOneRequestMsg.getType();
	}

}
