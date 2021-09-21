package client.msgHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Beans.User;
import client.main.LoginFrame;
import client.main.MainFrame;
import io.netty.channel.Channel;
import jar.common.LoginResponseMsg;
import jar.common.MessageHandler;

@Component
public class LoginResponseHandler implements MessageHandler<LoginResponseMsg> {
	// @Autowired
	// private ServerChannelManager manage;
	private Logger Logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private LoginFrame loginFrame;
	@Autowired
	private MainFrame mainFrame;

	@Override
	public void excute(Channel channel, LoginResponseMsg message) {
		User user = message.getUser();

		if (user != null) {
			System.out.println(user.getName() + "登录成功");
			loginFrame.dispose();
			mainFrame.startMainFrame();

		}

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub

		return LoginResponseMsg.getType();
	}

}
