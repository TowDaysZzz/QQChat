package client.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;

public class ChatClient {
	private final int port;
	private final String host;
	private static Channel channel;

	public ChatClient(String host, int port) {
		// TODO Auto-generated constructor stub
		this.host = host;
		this.port = port;

	}

	public void run() throws Exception {
		NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap().group(nioEventLoopGroup).channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// TODO Auto-generated method stub
							ChannelPipeline pipeline = ch.pipeline();
							// pipeline.addLast("decoder", new StringDecoder());
							// pipeline.addLast("encoder", new StringEncoder());
							pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
							// pipeline.addLast("encoder", new
							pipeline.addLast(new ChatClientHandler());
						}

					});
			ChannelFuture sync = bootstrap.connect(host, port).sync();
			this.channel = sync.channel();
			/*
			 * Scanner scanner = new Scanner(System.in); while
			 * (scanner.hasNextLine()) { String nextLine = scanner.nextLine();
			 * channel.writeAndFlush(nextLine + "\r\n");
			 * System.out.println("发送了" + nextLine);
			 * 
			 * }
			 */
			/*
			 * Thread thread = new Thread(new LoginMain(channel)); thread.run();
			 */
			// new LoginMain(channel);
		} finally {
			nioEventLoopGroup.shutdownGracefully();
		}
	}

}