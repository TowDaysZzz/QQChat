package jar.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/*
 *根据message的type分发到不同的handler中去
 */
@ChannelHandler.Sharable
@Component
public class MessageDispacher extends SimpleChannelInboundHandler<MsgObject> {
	@Autowired
	private MessageContainer messageContainer;

	// 需要线程池
	private ExecutorService excutorService = new ThreadPoolExecutor(10, 20, 200L, TimeUnit.MILLISECONDS,
			new LinkedBlockingDeque<Runnable>(1024), new ThreadPoolExecutor.DiscardOldestPolicy());

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MsgObject msg) throws Exception {
		// TODO Auto-generated method stub
		MessageHandler messageHandler = messageContainer.getMessageHandler(msg.getType());
		Class<? extends Message> messageClass = messageContainer.getMessageClass(messageHandler);
		Message parseObject = JSON.parseObject(msg.getMessage(), messageClass);

		excutorService.submit(() -> {
			// System.out.println("??????????????????????????????????????????????");
			messageHandler.excute(ctx.channel(), parseObject);
		});

	}

}
