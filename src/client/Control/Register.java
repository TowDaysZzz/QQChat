package client.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Beans.User;
import client.frame.RegisterFrame;
import client.service.ClientService;

public class Register extends RegisterFrame {
	public Register() {
		addEvent();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		new Register();

	}

	private void addEvent() {
		// TODO Auto-generated method stub
		// 注册按钮监听
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtName.getText().equals(""))
					JOptionPane.showMessageDialog(null, "用户名不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
				else if (new String(pwd.getPassword()).equals(""))
					JOptionPane.showMessageDialog(null, "密码不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
				else if (!new String(pwd.getPassword()).equals(new String(pwdRe.getPassword())))
					JOptionPane.showMessageDialog(null, "两次输入的密码不一致，请重新输入！", "错误", JOptionPane.ERROR_MESSAGE);
				else {
					String sex = "男";
					if (comGender.getSelectedIndex() == 1)
						sex = "女";
					System.out.println(new String(pwd.getPassword()));
					User user = new User(txtName.getText(), new String(pwd.getPassword()));
					if (ClientService.Sendregister(user)) {
						setVisible(false);
					}
				}

			}
		});
	}
}
