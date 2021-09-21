package client.msgHandler;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import client.config.ClientNettyConfig;
import client.main.ClientNetty;

public class ClientNettyMain {
	public static void main(String[] args) throws InterruptedException, BeansException {
		AnnotationConfigApplicationContext atx = new AnnotationConfigApplicationContext(ClientNettyConfig.class);

		ClientNetty clientNetty = atx.getBean(ClientNetty.class);
		clientNetty.start();

		// LoginFrame bean = atx.getBean(LoginFrame.class);
		// bean.addEvent();

		// String[] beanDefinitionNames = atx.getBeanDefinitionNames();
		// for (String string : beanDefinitionNames) {
		// System.out.println(string);
		// }
		// ClientActionListener bean = atx.getBean(ClientActionListener.class);
		// System.out.println(bean);
	}
}
