package server.handler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatServer {
	private int port;

	public ChatServer(int port) {
		this.port = port;
	}

	/*
	 * 开启方法
	 */
	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel channel) throws Exception {
							// TODO Auto-generated method stub
							ChannelPipeline pipeline = channel.pipeline();
							// pipeline.addLast("decoder", new
							// ObjectDecoder(ClassResolvers.cacheDisabled(null)));
							// pipeline.addLast(
							// new
							// ObjectDecoder(ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
							// pipeline.addLast("encoder", new ObjectEncoder());
							pipeline.addLast("decoder", new StringDecoder());
							pipeline.addLast("encoder", new StringEncoder());
							pipeline.addLast(new ChatServerHandler());
						}

					});
			System.out.println("port:" + port + "netty 服务器启动");
			ChannelFuture sync = bootstrap.bind(port).sync();

			// 监听关闭
			sync.channel().closeFuture().sync();
			System.out.println("server监听结束");

		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

	public static void main(String[] args) {
		ChatServer chatServer = new ChatServer(9999);
		try {
			chatServer.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
