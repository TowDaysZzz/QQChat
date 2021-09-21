package Tools;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class RedisTest {
	public static void main(String[] args) {
		/*
		 * // 连接本地的 Redis 服务 Jedis jedis = new Jedis("127.0.0.1", 6379);
		 * System.out.println("连接成功"); // 查看服务是否运行 System.out.println("服务正在运行: "
		 * + jedis.ping()); // 存储数据到列表中 jedis.lpush("site-list1", "Runoob");
		 * jedis.lpush("site-list2", "Google"); jedis.lpush("site-lis3",
		 * "Taobao"); // 获取存储的数据并输出 // Set<String> keys2 = jedis.keys("*");
		 * 
		 * List<String> list = jedis.lrange("site-list", 0, 0);
		 * 
		 * for (int i = 0; i < list.size(); i++) { System.out.println("列表项为: " +
		 * list.get(i)); }
		 * 
		 * // 获取数据并输出 Set<String> keys = jedis.keys("*"); Iterator<String> it =
		 * keys.iterator(); while (it.hasNext()) { String key = it.next();
		 * System.out.println(key); } jedis.close();
		 */
		ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
		// System.out.println("最大值为：" + GlobalEventExecutor.INSTANCE);
	}
}
