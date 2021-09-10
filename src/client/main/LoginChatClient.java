package client.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import Beans.User;
import client.Control.Register;
import client.frame.LoginFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class LoginChatClient extends LoginFrame {
	private final int port;
	private final String host;
	private static Channel channel;
	// private Channel channel;

	public LoginChatClient(String host, int port) throws Exception {
		// TODO Auto-generated constructor stub
		this.host = host;
		this.port = port;
		// run();
		// login();
		// run();
		addEvent();
	}

	public void run(String msg) throws Exception {
		NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap().group(nioEventLoopGroup).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// TODO Auto-generated method stub
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast("decoder", new StringDecoder());
							pipeline.addLast("encoder", new StringEncoder());
							// pipeline.addLast("decoder", new
							// ObjectDecoder(ClassResolvers.cacheDisabled(null)));
							// pipeline.addLast(
							// new
							// ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
							// pipeline.addLast("encoder", new ObjectEncoder());
							// pipeline.addLast("encoder", new
							// ObjectEncoder(ClassResolvers.cacheDisabled(null)));
							pipeline.addLast(new LoginChatClientHandler());
						}

					});
			ChannelFuture sync = bootstrap.connect(host, port).sync();
			channel = sync.channel();
			System.out.println("客户端启动" + channel.remoteAddress());
			channel.writeAndFlush(msg);
			// Scanner scanner = new Scanner(System.in);
			// addEvent();
			/*
			 * while (scanner.hasNextLine()) { String msg = scanner.nextLine();
			 * String strings[] = msg.split("#"); String name = strings[0];
			 * String pwd = strings[1]; User sUser = new User(name, pwd);
			 * Message mwMessage = new Message(); mwMessage.setUser(sUser); //
			 * 通过channel 发送到服务器端 channel.writeAndFlush(mwMessage + "\r\n");
			 * System.out.println("发送了" + mwMessage.getUser()); }
			 */

			// login();
			try {
				// System.out.println("正在运行的线程名称：" +
				// this.currentThread().getName() + " 开始");
				Thread.sleep(100000); // 延时2秒
				// System.out.println("正在运行的线程名称：" +
				// this.currentThread().getName() + " 结束");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} finally {
			nioEventLoopGroup.shutdownGracefully();
		}
	}

	public void login() throws Exception {

		// 获取用户名与密码
		String name = textFieldUserName.getText();
		String password = new String(UserPwd.getPassword());

		if (name.equals("") || password.equals(""))
			JOptionPane.showMessageDialog(this, "用户名和密码不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
		else {
			User user = new User(name, password);
			/*
			 * if (ClientService.sendLogin(user)) { this.dispose();// 销毁登录窗体
			 * System.out.println("登陆成功"); } else {
			 * System.out.println("用户登陆失败，请重新登陆！！！！！！！"); }
			 */
			/*
			 * Message sendMsg = new Message(); sendMsg.setUser(user);
			 */
			// run(sendMsg);
			String sendMsg = name + "#" + password;
			System.out.println("应该发送消息" + sendMsg);
			// channel.writeAndFlush(sendMsg);
			// System.out.println("实际发送消息" + sendMsg);
			run(sendMsg);

		}

	}

	private void addEvent() {
		// TODO Auto-generated method stub
		loginButton.addMouseListener(new MouseAdapter() {
			// 登录按钮的单击事件
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					login();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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

	public static void main(String[] args) {
		try {
			LoginChatClient loginChatClient = new LoginChatClient("127.0.0.1", 9999);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
