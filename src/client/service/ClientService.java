package client.service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import Beans.User;
import Tools.Message;
import Tools.MsgTypes;
import client.Control.Main;
import client.frame.ManageFrame;

/**
 * 
 * @ClassName: ClientService
 * @Description: TODO直接和socket进行交互,判断是否进入线程
 * @author TwoDaysZzz
 * @date 2021年5月18日
 *
 */
public class ClientService {
	static ObjectOutputStream out;
	static ObjectInputStream in;

	public static boolean sendLogin(User user) {
		System.out.println("正在进行判断" + user.getName());
		try {
			Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
			Message message = new Message();
			message.setMsgType(MsgTypes.MESSAGE_LOGIN_REQUEST);
			message.setUser(user);
			// 注意in和out的顺序
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.writeObject(message);

			Message getmsg = (Message) in.readObject();
			if (getmsg.getMsgType().equals(MsgTypes.MESSAGE_LOGIN_SUCCESS)) {
				User loginUser = getmsg.getUser();
				Main main = new Main(loginUser.getName(), loginUser.getSignature());
				ClientToServerThread thread = new ClientToServerThread(user.getName(), socket, main);
				ManageClientThread.add(user.getName(), thread);
				thread.start();

				// 或许添加一个界面管理
				ManageFrame.add(loginUser.getName(), main);
				System.out.println("登陆成功！！！");

				return true;
			} else {
				socket.close();
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean Sendregister(User user) {
		try {
			Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
			Message message = new Message();
			System.out.println("socket:" + socket);
			message.setMsgType(MsgTypes.MESSAGE_REGISTER_);
			message.setUser(user);
			System.out.println("注册：" + user.getName());
			// 注意in和out的顺序
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			out.writeObject(message);

			Message getmsg = (Message) in.readObject();
			if (getmsg.getMsgType().equals(MsgTypes.MESSAGE_REGISTER_SUCCESS)) {
				System.out.println("注册成功！！！");
				// 回到主界面

				return true;
			} else {
				socket.close();
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	public static void sendExit(String id) {
		// TODO Auto-generated method stub
	}

}
