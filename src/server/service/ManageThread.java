package server.service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import Tools.Message;
import Tools.MsgTypes;

/**
 * 
 * @ClassName: ManageOnline
 * @Description: TODO用于管理与客户端通信的线程，方便转发
 * @author TwoDaysZzz
 * @date 2021年5月18日
 *
 */
public class ManageThread {
	// userId,
	static HashMap<String, ServerToClientThread> map = new HashMap<>();

	static public void add(String userId, ServerToClientThread thread) {
		map.put(userId, thread);
	}

	static public ServerToClientThread retSoc(String userId) {
		return map.get(userId);
	}

	static public int getOnlinePerson() {
		return map.size();
	}

	static public void sendToall() {
		Message outMsg = new Message();
		for (String string : map.keySet()) {
			Socket socket = map.get(string).accept;
			ObjectOutputStream out;
			try {
				out = new ObjectOutputStream(socket.getOutputStream());
				outMsg.setMsgType(MsgTypes.MESSAGE_FRIENG_LIST);
				outMsg.setList(ServerService.getAllUsers());
				out.writeObject(outMsg);
				System.out.println("发送的人数：");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
