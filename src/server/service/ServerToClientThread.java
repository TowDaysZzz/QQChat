package server.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Tools.Message;
import Tools.MsgTypes;

/**
 * 
 * @ClassName: ServerToClientThread
 * @Description: TODO一个线程，服务器不断接收来自这个插孔的消息
 * @author TwoDaysZzz
 * @date 2021年5月18日
 *
 */
public class ServerToClientThread extends Thread {
	Socket accept;
	String userID;
	ObjectInputStream in;
	ObjectOutputStream out;

	public ServerToClientThread(Socket accpet, String userid) throws IOException {
		this.accept = accpet;
		this.userID = userid;

	}

	public void run() {
		// 不断接收消息
		while (accept != null) {
			// System.out.println("服务器与客户端" + userID + "保持通信");
			try {
				in = new ObjectInputStream(accept.getInputStream());
				Message message = (Message) in.readObject();
				dealWithMsg(message);
			} catch (Exception e) {
				System.out.println("accept读入数据出错");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	// 回复消息
	public void sendMsg(Message message) {
		try {
			out = new ObjectOutputStream(accept.getOutputStream());
			out.writeObject(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 转发
	public void sendRedict(String userId, Message message) {
		Socket socket = ManageThread.retSoc(userId).accept;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void dealWithMsg(Message message) {
		// TODO Auto-generated method stub
		String type = message.getMsgType();
		String sendID = message.getSender();
		String getID = message.getGetter();
		Message message2 = new Message();
		switch (type) {
		case MsgTypes.MESSAGE_CLIENT_EXIT:
			if (accept != null)
				try {
					accept.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			System.out.println("accept关闭" + userID);
			break;
		case MsgTypes.MESSAGE_COMMON_:
			sendID = message.getSender();
			getID = message.getGetter();
			message2 = new Message();
			message2.setGetter(getID);
			message2.setSender(sendID);
			message2.setContent(message.getContent());
			message2.setMsgType(MsgTypes.MESSAGE_COMMON_);
			ManageThread.retSoc(getID).sendMsg(message2);
			System.out.println(sendID + "转发成功:" + getID);
			break;
		case MsgTypes.MESSAGE_PRIVATE_CHAT:
			// 消息转发
			sendID = message.getSender();
			getID = message.getGetter();
			message2 = new Message();
			message2.setGetter(getID);
			message2.setSender(sendID);
			message2.setContent(message.getContent());
			message2.setMsgType(MsgTypes.MESSAGE_PRIVATE_CHAT);
			ManageThread.retSoc(getID).sendMsg(message2);
			System.out.println(sendID + "转发成功:" + getID);
			break;

		default:
			break;
		}

	}

}
