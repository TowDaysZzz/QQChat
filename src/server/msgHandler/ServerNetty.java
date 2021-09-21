package server.msgHandler;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Component
public class ServerNetty {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private int port = 9999;
	@Autowired
	private ServerNettyHandlerInitializer nettyHandlerInitializer;
	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	private EventLoopGroup workGroup = new NioEventLoopGroup();
	private Channel channel;

	public void start() throws Exception {
		System.out.println(nettyHandlerInitializer + "////");
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
				.localAddress(new InetSocketAddress(port)).option(ChannelOption.SO_BACKLOG, 1024)
				.childOption(ChannelOption.SO_KEEPALIVE, true).childOption(ChannelOption.TCP_NODELAY, true)
				.childHandler(nettyHandlerInitializer);
		ChannelFuture sync = serverBootstrap.bind(port).sync();
		System.out.println("开启端口。。。。");
		if (sync.isSuccess()) {
			channel = sync.channel();
			logger.info("[start][netty server 启动在{}端口]", port);
		}
	}

	public void shutdown() {
		if (channel != null) {
			channel.close();
		}
		bossGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}

}
