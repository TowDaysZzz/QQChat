package server.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import Beans.User;
import Tools.MsgTypes;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import redis.clients.jedis.Jedis;
import server.service.ServerService;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
	static ConcurrentHashMap<Integer, Channel> map = new ConcurrentHashMap<>();
	static Jedis jedis = new Jedis("127.0.0.1", 6379);

	/// ConcurrentHashMap<>();
	// public static List<Channel> channels = new ArrayList<Channel>();

	// 使用一个hashmap 管理
	// public static Map<String, Channel> channels = new
	// HashMap<String,Channel>();

	// 定义一个channle 组，管理所有的channel
	// GlobalEventExecutor.INSTANCE) 是全局的事件执行器，是一个单例
	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// handlerAdded 表示连接建立，一旦连接，第一个被执行
	// 将当前channel 加入到 channelGroup
	// @Override

	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel(); // 将该客户加入聊天的信息推送给其它在线的客户端

		// 该方法会将 channelGroup 中所有的channel 遍历，并发送 消息， 我们不需要自己遍历

		// channelGroup
		// .writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天" +
		// sdf.format(new java.util.Date()) + " \n");
		channelGroup.add(channel);
		System.out.println(channel.remoteAddress() + "加入聊天"); //
		map.put(channel.hashCode(), channel);
		Set<String> keys = jedis.keys("*");
		System.out.println("##########遍历如下##########");
		Iterator<String> iterator = keys.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			// String value = jedis.get(key);
			System.out.println(key);
		}
		System.out.println("##########遍历结束##########");

	}

	// 断开连接, 将xx客户离开信息推送给当前在线的客户

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

		Channel channel = ctx.channel();
		channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "离开了\n");
		map.remove(channel.hashCode());
		System.out.println("channelGroup size" + channelGroup.size());

	}

	// 表示channel 处于活动状态, 提示 xx上线

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		System.out.println(ctx.channel().remoteAddress() + " 上线了~");
	}

	// 表示channel 处于不活动状态, 提示 xx离线了

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		System.out.println(ctx.channel().remoteAddress() + " 离线了~");
	}

	// 读取数据
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

		// 获取到当前channel
		/*
		 * Channel channel = ctx.channel(); // 这时我们遍历channelGroup,
		 * 根据不同的情况，回送不同的消息 System.out.println("msg:" + msg);
		 * channel.writeAndFlush("pippppppp"); String strings[] =
		 * msg.split("#"); String name = strings[0]; String pwd = strings[1];
		 * String value = name + "#" + pwd; User user = new User(name, pwd);
		 * User login = ServerService.getLogin((user)); if (login != null) {
		 * jedis.lpush(name, channel.remoteAddress().toString());
		 * channel.writeAndFlush("true"); }
		 * 
		 */
		Channel channel = ctx.channel();
		// 这时我们遍历channelGroup, 根据不同的情况，回送不同的消息
		System.out.println("服务器收到的消息:" + msg);
		String[] split = msg.split("#");
		String flag = split[0];
		switch (flag) {
		case MsgTypes.MESSAGE_LOGIN_:
			dealwithLogin(split, channel);
			break;
		case MsgTypes.MESSAGE_REGISTER_:
			dealwithRegister(split, channel);
			break;
		case MsgTypes.MESSAGE_PRIVATE_CHAT:
			dealwithPriChat(split);
			break;
		}

		/*
		 * channelGroup.forEach(ch -> { if (channel != ch) { //
		 * 不是当前的channel,转发消息 ch.writeAndFlush("[客户]" + channel.remoteAddress() +
		 * " 发送了消息" + msg + "\n"); } else {// 回显自己发送的消息给自己
		 * ch.writeAndFlush("[自己]发送了消息" + msg + "\n"); } });
		 */
		// String msgType = msg.getMsgType();
		/*
		 * switch (msgType) { case MsgTypes.MESSAGE_REGISTER_:
		 * 
		 * break; case MsgTypes.MESSAGE_LOGIN_: // dealwithlogin(msg, channel);
		 * 
		 * break;
		 * 
		 * case MsgTypes.MESSAGE_PRIVATE_CHAT:d
		 * 
		 * break;
		 * 
		 * default: break; }
		 */
	}

	private void dealwithPriChat(String[] split) {
		// TODO Auto-generated method stub
		String fromUser = split[1];
		String toUser = split[2];
		String string = jedis.get(toUser);
		System.out.println(sdf.format(new Date()) + fromUser + "需要发送给：" + toUser + "------->" + string);

	}

	private void dealwithRegister(String[] msg, Channel channel) {
		// TODO Auto-generated method stub
		String name = msg[1];
		String pwd = msg[2];
		boolean rigester = ServerService.getRigester(name, pwd);
		if (rigester) {
			System.out.println(sdf.format(new Date()) + "注册成功");
			String registerSuccess = MsgTypes.MESSAGE_REGISTER_SUCCESS + "#";
			channel.writeAndFlush(registerSuccess);
			System.out.println(sdf.format(new Date()) + "注册成功消息发送" + registerSuccess);

			return;
		} else {
			System.out.println(sdf.format(new Date()) + "注册失败");
			String registerFail = MsgTypes.MESSAGE_LOGIN_FAIL + "#";
			channel.writeAndFlush(registerFail);
			System.out.println(sdf.format(new Date()) + "注册成功消息发送" + registerFail);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 关闭通道
		ctx.close();
	}

	private void dealwithLogin(String[] msg, Channel channel) {
		String name = msg[1];
		String pwd = msg[2];
		User login = ServerService.getLogin(name, pwd);
		if (login != null) {
			System.out.println(sdf.format(new Date()) + "登陆成功");
			String loginSuccess = MsgTypes.MESSAGE_LOGIN_SUCCESS + "#";
			channel.writeAndFlush(loginSuccess);
			System.out.println(sdf.format(new Date()) + "登陆成功消息发送" + loginSuccess);
			jedis.lpush(name, channel.remoteAddress().toString());
			return;
		}
		// 登陆失败
		System.out.println(sdf.format(new Date()) + "登陆失败");
		String loginFail = MsgTypes.MESSAGE_LOGIN_FAIL + "#";
		channel.writeAndFlush(loginFail);
		System.out.println(sdf.format(new Date()) + "登陆失败消息发送" + loginFail);
	}

}
