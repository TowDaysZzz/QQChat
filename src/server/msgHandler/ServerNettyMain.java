package server.msgHandler;

import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import Tools.MsgTypes;
import jar.common.MessageContainer;
import jar.common.MessageHandler;
import server.config.ServerNettyConfig;

@Component
public class ServerNettyMain {
	// @Test
	public void test() {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ServerNettyConfig.class);
		// SayHello hello = ctx.getBean(SayHello.class);

		// System.out.println(hello.sayHello("javaboy"));
		// MessageDispacher bean = ctx.getBean(MessageDispacher.class);
		// System.out.println(bean);
		String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
		for (String beanName : beanDefinitionNames) {
			System.out.println("beanName: " + beanName);
		}
		MessageContainer bean = ctx.getBean(MessageContainer.class);
		MessageHandler messageHandler = bean.getMessageHandler(MsgTypes.MESSAGE_LOGIN_REQUEST);
		System.out.println(messageHandler);
		Map<String, MessageHandler> map = bean.getMap();
		for (MessageHandler msHandler : map.values()) {
			// System.out.println(msHandler);
		}
		Set<String> keySet = map.keySet();
		for (String string : keySet) {
			// System.out.println(string);
		}
	}

	@Autowired
	private ServerNetty serverNetty;

	@Test
	public void test1() {
		AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(ServerNettyConfig.class);
		System.out.println(serverNetty);
	}

	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(ServerNettyConfig.class);
		ServerNetty serverNetty = atx.getBean(ServerNetty.class);
		serverNetty.start();
	}

}
