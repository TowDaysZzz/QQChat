package client.main;

import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import client.msgHandler.ClientActionListener;
import client.msgHandler.ClientNettyInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import jar.common.MsgObject;

@Component
// @DependsOn("loginFrame")
public class ClientNetty {
	/**
	 * 重连频率，单位：秒
	 */
	private static final Integer RECONNECT_SECONDS = 20;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private String serverHost = "127.0.0.1";
	private Integer serverPort = 9999;
	@Autowired
	private ClientNettyInitializer clientNettyInitializer;
	@Autowired
	private LoginFrame loginFrame;
	/**
	 * 线程组，用于客户端对服务端的链接、数据读写
	 */
	private EventLoopGroup eventGroup = new NioEventLoopGroup();
	/**
	 * Netty Client Channel
	 */
	private volatile Channel channel;
	@Autowired
	private ClientActionListener actionListener;

	/**
	 * 启动 Netty Client
	 */
	// @PostConstruct
	public void start() throws InterruptedException {
		System.out.println("监听器" + actionListener);
		// 创建 Bootstrap 对象，用于 Netty Client 启动
		Bootstrap bootstrap = new Bootstrap();
		// 设置 Bootstrap 的各种属性。
		// 设置一个 EventLoopGroup 对象
		bootstrap.group(eventGroup)
				// 指定 Channel 为客户端 NioSocketChannel
				.channel(NioSocketChannel.class)
				// 指定链接服务器的地址
				.remoteAddress(serverHost, serverPort)
				// TCP Keepalive 机制，实现 TCP 层级的心跳保活功能
				.option(ChannelOption.SO_KEEPALIVE, true)
				// 允许较小的数据包的发送，降低延迟
				.option(ChannelOption.TCP_NODELAY, true).handler(clientNettyInitializer);
		// 链接服务器，并异步等待成功，即启动客户端
		System.out.println("客户端开启端口");
		System.out.println("actionlistener" + actionListener);
		/*
		 * 给窗体加监听器
		 */
		loginFrame.addEvent();
		bootstrap.connect().addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				// 连接失败
				if (!future.isSuccess()) {
					logger.error("[start][Netty Client 连接服务器({}:{}) 失败]", serverHost, serverPort);
					reconnect();
					return;
				}
				// 连接成功
				channel = future.channel();
				logger.info("[start][Netty Client 连接服务器({}:{}) 成功]", serverHost, serverPort);
			}

		});

	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void reconnect() {
		eventGroup.schedule(new Runnable() {
			@Override
			public void run() {
				logger.info("[reconnect][开始重连]");
				try {
					start();
				} catch (InterruptedException e) {
					logger.error("[reconnect][重连失败]", e);
				}
			}
		}, RECONNECT_SECONDS, TimeUnit.SECONDS);
		logger.info("[reconnect][{} 秒后将发起重连]", RECONNECT_SECONDS);
	}

	/**
	 * 关闭 Netty Server
	 */
	@PreDestroy
	public void shutdown() {
		// 关闭 Netty Client
		if (channel != null) {
			channel.close();
		}
		// 优雅关闭一个 EventLoopGroup 对象
		eventGroup.shutdownGracefully();
	}

	/**
	 * 发送消息
	 *
	 * @param invocation
	 *            消息体
	 */
	public void send(MsgObject msgObject) {
		if (channel == null) {
			logger.error("[send][连接不存在]");
			return;
		}
		if (!channel.isActive()) {
			logger.error("[send][连接({})未激活]", channel.id());
			return;
		}
		// 发送消息
		channel.writeAndFlush(msgObject);
	}

	// @Test
	// public void test() {
	// AnnotationConfigApplicationContext atx = new
	// AnnotationConfigApplicationContext(ClientNettyConfig.class);
	// ClientNetty bean = atx.getBean(ClientNetty.class);
	//
	// System.out.println(bean);
	// addEvent();
	// }

	// private void addEvent() {
	// // TODO Auto-generated method stub
	// // System.out.println("监听器添加成功");
	// System.out.println(actionListener + "/////////");
	//
	// }
}
