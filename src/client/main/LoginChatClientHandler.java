package client.main;

import java.net.InetAddress;
import java.net.Socket;

import Tools.MsgTypes;
import client.Control.Main;
import client.frame.ManageFrame;
import client.service.ClientToServerThread;
import client.service.ManageClientThread;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginChatClientHandler extends SimpleChannelInboundHandler<String> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(msg);
		String[] split = msg.split("#");
		switch (split[0]) {
		case MsgTypes.MESSAGE_LOGIN_SUCCESS:
			Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
			String name = split[1];
			String signature = split[2];
			System.out.println("登陆成功");
			Main main = new Main(name, signature);
			ClientToServerThread thread = new ClientToServerThread(name, socket, main);
			ManageClientThread.add(name, thread);
			thread.start();

			// 或许添加一个界面管理
			ManageFrame.add(name, main);
			break;

		case MsgTypes.MESSAGE_LOGIN_FAIL:

			System.out.println("登陆失败");

			break;
		}

	}

}
