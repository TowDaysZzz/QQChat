package client.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import client.Control.Register;
import client.frame.MyJPanel;
import client.msgHandler.ClientLoginListener;

@Component
public class LoginFrame extends JFrame {

	private JPanel contentPane;
	// 用户名、密码、头像
	public JTextField textFieldUserName;
	public JPasswordField UserPwd;
	public JLabel headImg;
	// 记住密码、自动登录按钮
	public JCheckBox checkBoxMemoryPwd;
	public JCheckBox checkBoxSelfLogin;
	// 登录、注册按钮
	public JLabel loginButton;
	public JLabel registerButton;

	// 获取用户名与密码
	private String name;
	private String password;
	// static {
	// System.out.println("loginframe ---static");
	// }
	@Autowired
	private ClientLoginListener actionListener;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { //
	 * MyTools.class.getResource(path); //
	 * System.out.println(MyTools.class.getResource(path)); LoginFrame frame =
	 * new LoginFrame(); frame.setVisible(true); }
	 */

	public LoginFrame() {
		// System.out.println("loginFrame---init");
		// System.out.println("loginframe()..........");
		// System.out.println("loginframe...........");
		setTitle("chat");
		System.out.println("path:" + LoginFrame.class.getResource("/"));
		// System.out.println(PathClass.class.getResource("/"));
		setIconImage(Toolkit.getDefaultToolkit().getImage("D:/aa/project/QQChat/src/client/img/QQ_64.png"));
		// setIconImage(Toolkit.getDefaultToolkit().getImage("/client/img/QQ_64.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 448, 350);

		contentPane = new MyJPanel("D:/aa/project/QQChat/src/client/img/register.jpg");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		UserPwd = new JPasswordField();
		UserPwd.setEchoChar('●');
		UserPwd.setBounds(130, 163, 201, 26);
		contentPane.add(UserPwd);

		headImg = new JLabel("");
		headImg.setIcon(new ImageIcon("/client/img/head_boy_01_64.jpg"));
		headImg.setBounds(18, 127, 64, 64);
		contentPane.add(headImg);

		checkBoxMemoryPwd = new JCheckBox("记住密码");
		checkBoxMemoryPwd.setBounds(104, 198, 76, 18);
		contentPane.add(checkBoxMemoryPwd);

		checkBoxSelfLogin = new JCheckBox("自动登录");
		checkBoxSelfLogin.setBounds(229, 198, 76, 18);
		contentPane.add(checkBoxSelfLogin);

		loginButton = new JLabel("");
		loginButton.setIcon(new ImageIcon("D:/aa/project/QQChat/src/client/img/button_login_1.png"));
		loginButton.setBounds(161, 240, 69, 22);
		contentPane.add(loginButton);

		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(130, 128, 201, 26);
		contentPane.add(textFieldUserName);
		textFieldUserName.setColumns(10);

		registerButton = new JLabel("注册账号");
		registerButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
		registerButton.setForeground(new Color(0, 51, 255));
		registerButton.setBounds(0, 244, 55, 18);
		contentPane.add(registerButton);

		JLabel lblNewLabel = new JLabel("Welcom TO Chat…");
		lblNewLabel.setBackground(SystemColor.inactiveCaptionBorder);
		lblNewLabel.setFont(new Font("Franklin Gothic Heavy", Font.BOLD | Font.ITALIC, 21));
		lblNewLabel.setBounds(48, 27, 257, 71);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("账号");
		// lblNewLabel_1.set
		lblNewLabel_1.setBounds(100, 133, 58, 15);
		contentPane.add(lblNewLabel_1);

		JLabel label = new JLabel("密码");
		label.setBounds(100, 168, 58, 15);
		contentPane.add(label);
		this.setVisible(true);
		// addEvent();

	}

	public String getName() {
		return textFieldUserName.getText();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return new String(UserPwd.getPassword());
	}

	public void setPassword(String password) {
		this.password = password;
	}
	//
	// @Test
	// public void test() {
	// AnnotationConfigApplicationContext atx = new
	// AnnotationConfigApplicationContext(ClientNettyConfig.class);
	// addEvent();
	// }

	public void addEvent() {
		// TODO Auto-generated method stub
		// System.out.println("监听器添加成功");
		System.out.println(actionListener + "/////////");
		loginButton.addMouseListener(actionListener);

		registerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new Register();
			}
		});
	}
}
