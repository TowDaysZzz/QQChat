package client.msgHandler;

import java.util.List;

import org.springframework.stereotype.Component;

import Beans.User;
import io.netty.channel.Channel;
import jar.common.MessageHandler;
import server.msgHandler.ServerListMsg;

@Component
public class ListClientHandler implements MessageHandler<ServerListMsg> {

	@Override
	public void excute(Channel channel, ServerListMsg message) {
		// TODO Auto-generated method stub
		List<User> lists = message.getLists();
		for (User user : lists) {
			System.out.println(user.getName());
		}

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return ServerListMsg.getType();
	}

}
