package client.Control;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import Tools.Message;
import Tools.MsgTypes;
import client.frame.MainFrame;
import client.service.ManageClientThread;

public class Main extends MainFrame {
	String user;
	public static HashMap<String, Chat> map = new HashMap<>();

	public Main(String userName, String signate) {
		// TODO Auto-generated constructor stub
		this.userName.setText(userName);
		this.user = userName;
		this.signate.setText(signate);

	}

	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		new Main();
	}

	public void startChat(ActionEvent e) {
		if (tree.getSelectionPath().getPathCount() == 3) {
			String str = tree.getSelectionPath().getLastPathComponent().toString();
			String friendName = str.substring(0, str.indexOf("("));
			String friendIP = str.substring(str.indexOf("(") + 1, str.indexOf(":"));
			int friendPort = Integer.parseInt(str.substring(str.indexOf(":") + 1, str.indexOf(")")));
			System.out.println(friendName);
			Chat chat = new Chat("1", 9999, user, friendName);
			// 发送消息告诉服务器
			Message message = new Message();
			message.setMsgType(MsgTypes.MESSAGE_PRIVATE_CHAT);
			message.setSender(user);
			message.setGetter(friendName);

			// 打开一个窗口

			ManageClientThread.get(user).sendMsg(message);

			map.put(friendName, chat);
		} else {
			JOptionPane.showMessageDialog(null, "对不起，您未选中任何好友！");
		}
	}

	// 初始化好友列表
	public void initjTree(String[] groupNames, ArrayList<String[]> friendNames) {
		new MyTree(tree, groupNames, friendNames);
	}
}
