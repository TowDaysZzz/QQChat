package server.msgHandler;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;
import jar.common.MsgObject;

@Component
public class ServerChannelManager {
	private static final AttributeKey<String> CHANNEL_ATTR_KEY_USER = AttributeKey.newInstance("user");
	public static ConcurrentHashMap<ChannelId, Channel> map = new ConcurrentHashMap<ChannelId, Channel>();
	public static ConcurrentHashMap<String, Channel> userChannels = new ConcurrentHashMap<String, Channel>();
	private Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * channel
	 */
	public void add(Channel channel) {
		map.put(channel.id(), channel);
		logger.info("[add][一个连接({})加入]", channel.id());

	}

	/*
	 * 添加指定用户到userchannels中
	 */
	public void addUser(Channel channel, String user) {
		Channel channel2 = userChannels.get(channel.id());
		if (channel2 == null) {
			logger.info("[addUser][连接({})不存在]", channel.id());
		}
		channel.attr(CHANNEL_ATTR_KEY_USER).set(user);
		userChannels.put(user, channel2);
	}

	public void remove(Channel channel) {
		map.remove(channel.id());
		/*
		 * 一处userchannel
		 */
		if (channel.hasAttr(CHANNEL_ATTR_KEY_USER)) {
			userChannels.remove(channel.attr(CHANNEL_ATTR_KEY_USER).get());

		}
		logger.info("[remove][一个连接({})离开]", channel.id());
	}

	/*
	 * 转发消息
	 */
	public void send(String user, MsgObject msgObject) {
		Channel channel = userChannels.get(user);
		if (channel == null) {
			logger.error("[send][连接不存在]");
			return;
		}
		if (!channel.isActive()) {
			logger.info("[send][连接({})未激活]", channel.id());
			return;
		}
		channel.writeAndFlush(msgObject);
	}

	/*
	 * login消息转发回去
	 */
	public void sendLogin(Channel channel, MsgObject meMsgObject) {
		channel.writeAndFlush(meMsgObject);
	}

	/*
	 * sendtoall
	 */
	public void sendAll(MsgObject msgObject) {
		for (Channel channel : map.values()) {
			if (!channel.isActive()) {
				logger.error("[sendAll][连接({})未激活]", channel.id());
				break;

			}
			channel.writeAndFlush(msgObject);
		}
	}
}
