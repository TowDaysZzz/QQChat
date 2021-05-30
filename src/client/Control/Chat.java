package client.Control;

import java.text.DateFormat;
import java.util.Date;

import Tools.Message;
import Tools.MsgTypes;
import client.frame.ChatFrame;
import client.service.ManageClientThread;

public class Chat extends ChatFrame {
	String friendName;
	String userName;

	public Chat(String friendIP, int friendTCPPort, String myName, String friendName) {
		this.friendName = friendName;
		this.userName = myName;
		setTitle("与" + friendName + "(" + friendIP + ":" + friendTCPPort + ")聊天中");
		// init();
		// initGetFileServer();// 初始化接收文件的服务
	}

	public void sendMessage() {
		Message message = new Message();
		message.setMsgType(MsgTypes.MESSAGE_COMMON_);
		message.setSender(userName);
		message.setGetter(friendName);
		message.setContent(jTextPaneSendMessage.getText());
		ManageClientThread.get(userName).sendMsg(message);
		setReceivePaneText(true, jTextPaneSendMessage.getText());
		jTextPaneSendMessage.setText("");
	}

	/**
	 * 设置接收框的文本
	 * 
	 * @param text
	 *            需要设置的文本
	 */
	public void setReceivePaneText(boolean isFromMyself, String text) {
		String get = isFromMyself == true ? "我" : "你";
		System.out.println(get + "发送了" + text);
		if (isFromMyself) {// 是自己的消息
			String time = DateFormat.getTimeInstance().format(new Date());
			new MyTextPane(jTextPaneGetMessage).addText(userName + " " + time + "\n", MyTextPane.getTimeAttribute());
			new MyTextPane(jTextPaneGetMessage).addText(text + "\n", MyTextPane.getMyAttribute());
		} else {
			String time = DateFormat.getTimeInstance().format(new Date());
			new MyTextPane(jTextPaneGetMessage).addText(friendName + " " + time + "\n", MyTextPane.getTimeAttribute());
			new MyTextPane(jTextPaneGetMessage).addText(text + "\n", MyTextPane.getFriendAttribute());
		}
	}
}
