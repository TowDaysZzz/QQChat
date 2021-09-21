package server.msgHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import Beans.User;
import io.netty.channel.Channel;
import jar.common.LoginRequestMsg;
import jar.common.LoginResponseMsg;
import jar.common.MessageHandler;
import jar.common.MsgObject;
import server.service.ServerService;

@Component
public class LoginRequestHandler implements MessageHandler<LoginRequestMsg> {
	@Autowired
	private ServerChannelManager manage;
	private Logger Logger = LoggerFactory.getLogger(getClass());

	@Override
	public void excute(Channel channel, LoginRequestMsg message) {
		// TODO Auto-generated method stub
		// 检查发送的消息是否正确
		String username = message.getUsername();
		String pwd = message.getPwd();
		User login = ServerService.getLogin(username, pwd);
		LoginResponseMsg msg = new LoginResponseMsg();
		msg.setMsgid(message.getMsgid());
		msg.setUser(login);
		Logger.info("[excute][({})]" + JSON.toJSONString(msg));
		// System.out.println("开始判断");
		if (login != null) {
			System.out.println("登陆成功！！！！！！！！！！");
		}
		manage.sendLogin(channel, new MsgObject(LoginResponseMsg.getType(), msg));

	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub

		return LoginRequestMsg.getType();
	}

}
