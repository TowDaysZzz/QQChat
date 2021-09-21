package client.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("client.main")
@ComponentScan("client.msgHandler")
@ComponentScan("jar.common")
public class ClientNettyConfig {

}
