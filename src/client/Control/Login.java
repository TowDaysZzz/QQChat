package client.Control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import Beans.User;
import client.frame.LoginFrame;
import client.service.ClientService;

public class Login extends LoginFrame {
	public Login() {
		// TODO Auto-generated constructor stub
		addEvent();
	}

	public static void main(String[] args) {
		new Login();
	}

	public void login() {

		// 获取用户名与密码
		String name = textFieldUserName.getText();
		String password = new String(UserPwd.getPassword());

		if (name.equals("") || password.equals(""))
			JOptionPane.showMessageDialog(this, "用户名和密码不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
		else {
			User user = new User(name, password);
			if (ClientService.sendLogin(user)) {
				this.dispose();// 销毁登录窗体
				System.out.println("登陆成功");
			} else {
				System.out.println("用户登陆失败，请重新登陆！！！！！！！");
			}
		}

	}

	private void addEvent() {
		// TODO Auto-generated method stub
		loginButton.addMouseListener(new MouseAdapter() {
			// 登录按钮的单击事件
			@Override
			public void mouseClicked(MouseEvent e) {
				login();
			}
		});
		this.addMouseListener(new MouseAdapter() {
			// 窗体的鼠标按下事件
			@Override
			public void mousePressed(MouseEvent e) {
				// mousePress(e);
			}
		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// mouseDrag(e);
			};
		});

		registerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Register();
			}
		});
	}
}
