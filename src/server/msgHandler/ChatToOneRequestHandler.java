package server.msgHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import jar.common.ChatToOneRequestMsg;
import jar.common.ChatToOneResponseMsg;
import jar.common.MessageHandler;
import jar.common.MsgObject;

@Component
public class ChatToOneRequestHandler implements MessageHandler<ChatToOneRequestMsg> {
	@Autowired
	private ServerChannelManager serverChannelManager;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void excute(Channel channel, ChatToOneRequestMsg message) {
		// TODO Auto-generated method stub
		String toUser = message.getToUser();
		ChatToOneResponseMsg chatToOneResponseMsg = new ChatToOneResponseMsg();
		chatToOneResponseMsg.setFromUser(message.getFromUser());
		chatToOneResponseMsg.setToUser(message.getToUser());
		chatToOneResponseMsg.setContent(message.getContent());
		MsgObject msgObject = new MsgObject(ChatToOneResponseMsg.getType(), chatToOneResponseMsg);
		logger.info("[ChatToOneRequestHandler][服务器接收到了消息({})]", message);
		System.out.println("发送给：" + toUser);
		serverChannelManager.send(toUser, msgObject);

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return ChatToOneRequestMsg.TYPE;
	}

}
