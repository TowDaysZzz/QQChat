package client.msgHandler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import client.main.ClientNetty;
import client.main.LoginFrame;
import jar.common.LoginRequestMsg;
import jar.common.MsgObject;

@Component

public class ClientLoginListener extends MouseAdapter {
	// @Autowired
	// private LoginFrame loginframe;
	// public ClientActionListener() {
	// System.out.println("ClientActionListener--init");
	// }
	// static {
	// System.out.println("ClientActionListener---static");
	// }
	@Autowired
	private LoginFrame loginFrame;
	@Autowired
	private ClientNetty clietNetty;

	@Override
	public void mouseClicked(MouseEvent e) {

		// TODO Auto-generated method stub
		String name = loginFrame.getName();
		String password = loginFrame.getPassword();
		// if (name.equals("") || password.equals("")) {
		// JOptionPane.showMessageDialog(loginframe, "用户名和密码不能为空！", "错误",
		// JOptionPane.ERROR_MESSAGE);
		// return;
		// }
		System.out.println("name:" + name + "pwd:" + password);
		LoginRequestMsg loginRequestMsg = new LoginRequestMsg();
		loginRequestMsg.setUsername(name);
		loginRequestMsg.setPwd(password);
		loginRequestMsg.setFromUser(clietNetty.getChannel().id());
		MsgObject msgObject = new MsgObject(LoginRequestMsg.getType(), loginRequestMsg);
		clietNetty.getChannel().writeAndFlush(msgObject);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
