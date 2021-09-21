package server.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import Beans.User;
import Tools.Message;
import Tools.MsgTypes;

/**
 * 
 * @ClassName: Server_1
 * @Description: TODO 主线程(打开服务器端口)，包括其余accept子线程（要进行判断） 这是一个总开关
 * @author TwoDaysZzz
 * @date 2021年5月18日
 *
 */
public class ManageAcceptThread {
	static int port;
	static ServerSocket serverSocket;

	// static HashMap<Integer, Socket> accpets = new HashMap<>();

	public ManageAcceptThread(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("服务器在" + port + "端口监听.....");
			//
			getConnectionNewThread();
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 添加子线程，先循环读取
	private void getConnectionNewThread() throws IOException, ClassNotFoundException {
		// ????????????????????????????????????????????????????????????????
		while (true) {
			System.out.println("服务器正在接收中......");
			Socket accept = serverSocket.accept();
			System.out.println("ac:" + accept);
			ObjectInputStream in = new ObjectInputStream(accept.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(accept.getOutputStream());
			Message inMsg = (Message) in.readObject();
			User user = inMsg.getUser();
			Message outMsg = new Message();
			// 如果是登陆
			if (inMsg.getMsgType().equals(MsgTypes.MESSAGE_LOGIN_REQUEST)) {
				User loginUser = ServerService.getLogin((user));
				if (loginUser != null) {

					outMsg.setMsgType(MsgTypes.MESSAGE_LOGIN_SUCCESS);
					outMsg.setUser(loginUser);
					out.writeObject(outMsg);
					System.out.println("发送的个性签名：" + loginUser.getSignature());
					// 进入新的线程
					ServerToClientThread thread = new ServerToClientThread(accept, user.getName());
					thread.start();//
					ManageThread.add(user.getName(), thread);
					// 同时要发送，所有用户list，，，应该是发送给所有的
					ManageThread.sendToall();
					System.out.println("用户" + loginUser.getName() + "登陆成功！！！！");
					System.out.println("此时总在线人数为：" + ManageThread.getOnlinePerson() + "!!!!");
				} else {
					System.out.println("用户登陆失败，请重新登陆！！！");
					outMsg.setMsgType(MsgTypes.MESSAGE_LOGIN_FAIL);

					out.writeObject(outMsg);
					accept.close();
				}
			} // 剩下只能是注册
			else {
				if (ServerService.getRigester(user)) {
					outMsg.setMsgType(MsgTypes.MESSAGE_REGISTER_SUCCESS);
					// 进入新的线程
					out.writeObject(outMsg);
					System.out.println(user.getName() + "注册成功！！！！");
					System.out.println("此时总在线人数为：" + ManageThread.getOnlinePerson() + "!!!!");
				} else {
					System.out.println("用户注册失败，请重新注册！！！");
					outMsg.setMsgType(MsgTypes.MESSAGE_REGISTER_FAIL);

					out.writeObject(outMsg);
					accept.close();
				}
			}

		}

	}
}
