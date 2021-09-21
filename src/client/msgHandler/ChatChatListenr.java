package client.msgHandler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import client.main.ChatFrame;
import client.main.ClientNetty;
import jar.common.ChatToOneRequestMsg;
import jar.common.MsgObject;

@Component
public class ChatChatListenr extends MouseAdapter {
	@Autowired
	private ChatFrame chatFrame;
	@Autowired
	private ClientNetty clietNetty;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void mouseClicked(MouseEvent e) {
		ChatToOneRequestMsg chatToOneRequestMsg = new ChatToOneRequestMsg();
		chatToOneRequestMsg.setFromUser("jw");
		chatToOneRequestMsg.setToUser("xx");
		chatToOneRequestMsg.setContent(chatFrame.jTextPaneSendMessage.getText());
		MsgObject msgObject = new MsgObject(ChatToOneRequestMsg.getType(), chatToOneRequestMsg);
		clietNetty.getChannel().writeAndFlush(msgObject);
		logger.info("[mouseClicked][发送了({})]", chatToOneRequestMsg);
	}

}
