package server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import jar.common.MessageContainer;
import jar.common.MessageDispacher;
import server.msgHandler.ServerNetty;
import server.msgHandler.ServerNettyHandlerInitializer;

@Configuration
@ComponentScan(basePackages = "server.msgHandler")
@ComponentScan(basePackages = "jar.common")
public class ServerNettyConfig {
	/*
	 * 需要springioc所有加了注解的
	 */

	@Bean
	public MessageDispacher messageDispatcher() {
		return new MessageDispacher();
	}

	@Bean
	public MessageContainer messageHandlerContainer() {
		return new MessageContainer();
	}

	@Bean
	public ServerNettyHandlerInitializer nettyHandlerInitializer() {
		return new ServerNettyHandlerInitializer();
	}

	public ServerNetty serverNetty() {
		return new ServerNetty();
	}

	/*
	 * @Bean public HeartbeatRequestHandler heartbeatRequestHandler() { return
	 * new HeartbeatRequestHandler(); }
	 */
	/*
	 * @Bean public HeartbeatResponseHandler heartbeatResponseHandler() { return
	 * new HeartbeatResponseHandler(); }
	 */
}
