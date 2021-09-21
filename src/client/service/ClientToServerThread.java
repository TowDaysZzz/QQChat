package client.service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Beans.User;
import Tools.Message;
import Tools.MsgTypes;
import client.Control.Chat;
import client.Control.Main;
import client.frame.ManageFrame;

public class ClientToServerThread extends Thread {
	Socket socket;
	String userID;
	Main main;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	public ClientToServerThread(String uString, Socket socket, Main main2) {

		this.socket = socket;
		this.userID = uString;
		this.main = main2;
	}

	public void run() {
		// 不断接收消息
		while (socket != null) {

			try {
				objectInputStream = new ObjectInputStream(socket.getInputStream());
				Message message = (Message) objectInputStream.readObject();
				dealWithMeg(message);
			} catch (Exception e) {
				System.out.println("accept读入数据出错");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void sendMsg(Message message) {
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectOutputStream.writeObject(message);
		} catch (Exception e) {
			System.out.println("accept读入数据出错");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void dealWithMeg(Message message) {
		// TODO Auto-generated method stub
		String type = message.getMsgType();
		String send = message.getSender();
		String getter = message.getGetter();
		switch (type) {
		// 来自私聊
		case MsgTypes.MESSAGE_FRIENG_LIST:
			doFriendList(message);
			break;
		case MsgTypes.MESSAGE_PRIVATE_CHAT_REQUEST:
			// 同时要创建一个chat：
			if (!ManageFrame.get(userID).map.containsKey(send)) {
				Chat chat = new Chat("1", 1111, userID, send);
				ManageFrame.get(userID).map.put(send, chat);
			}

			break;
		// 群聊
		case MsgTypes.MESSAGE_COMMON_:
			// 刷新消息
			ManageFrame.get(userID).map.get(send).setReceivePaneText(false, message.getContent());

			break;
		case MsgTypes.MESSAGE_QUN_CHAT:

			break;
		// 来自服务器
		case MsgTypes.MESSAGE_TO_ALL_MSG:

			break;

		default:
			break;
		}

	}

	public void doFriendList(Message message) {
		System.out.println("客户端已接收到好友列表！");
		System.out.println(message);
		List<User> lists = message.getList();
		String[] groupNames = { "所有在线用户", "所有不在线用户", "我的好友" };
		ArrayList<String[]> friendNames = new ArrayList<>();
		String[] index = new String[lists.size() + 1];
		int i = 0;
		for (User user : lists) {
			index[i++] = user.getName();
			// System.out.println(user.getName());
		}
		friendNames.add(index);
		// System.out.println("...." + groupNames[2]);
		// System.out.println(",,," + friendNames);// + friendNames);
		ManageFrame.get(userID).initjTree(groupNames, friendNames);
	}

}
