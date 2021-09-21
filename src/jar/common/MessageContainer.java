package jar.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MessageContainer implements InitializingBean {
	/*
	 * 消息类型和MessageHandler之间的映射
	 */
	/*
	 * (non-Javadoc) 会调用这个方法
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final Map<String, MessageHandler> handlers = new HashMap<>();
	@Autowired
	private ApplicationContext applicationContext;

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		applicationContext.getBeansOfType(MessageHandler.class).values()
				.forEach(messageHandler -> handlers.put(messageHandler.getType(), messageHandler));

		logger.info("[afterPropertiesSet][消息处理器的数量：({})]", handlers.size());

	}

	public Map<String, MessageHandler> getMap() {
		return handlers;

	}

	public MessageHandler getMessageHandler(String type) {
		MessageHandler messageHandler = handlers.get(type);
		if (handlers == null) {
			throw new IllegalArgumentException(String.format("找不到匹配的handler"));

		}
		return messageHandler;
	}

	public static Class<? extends Message> getMessageClass(MessageHandler handler) {
		// 获得 Bean 对应的 Class 类名。因为有可能被 AOP 代理过。
		Class<?> targetClass = AopProxyUtils.ultimateTargetClass(handler);

		// 获得接口的 Type 数组
		Type[] interfaces = targetClass.getGenericInterfaces();
		Class<?> superclass = targetClass.getSuperclass();

		// 此处，是以父类的接口为准
		while ((Objects.isNull(interfaces) || 0 == interfaces.length) && Objects.nonNull(superclass)) {
			interfaces = superclass.getGenericInterfaces();
			superclass = targetClass.getSuperclass();
		}

		if (Objects.nonNull(interfaces)) {
			// 遍历 interfaces 数组
			for (Type type : interfaces) {
				// 要求 type 是泛型参数
				if (type instanceof ParameterizedType) {
					ParameterizedType parameterizedType = (ParameterizedType) type;
					// 要求是 MessageHandler 接口
					if (Objects.equals(parameterizedType.getRawType(), MessageHandler.class)) {
						Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
						// 取首个元素
						if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0) {
							return (Class<Message>) actualTypeArguments[0];
						} else {
							throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", handler));
						}
					}
				}
			}
		}
		throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", handler));

	}
}
