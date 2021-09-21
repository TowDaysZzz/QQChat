package jar.common;

import com.alibaba.fastjson.JSON;

public class Test {
	@org.junit.Test
	public void test01() {
		ChatToOneRequestMsg chatToOneMsg = new ChatToOneRequestMsg();
		chatToOneMsg.setFromUser("xx");
		chatToOneMsg.setToUser("ljw");
		chatToOneMsg.setMsgid("999");
		chatToOneMsg.setContent("你好，ljw!!!");
		String jsonString = JSON.toJSONString(chatToOneMsg);
		System.out.println(jsonString);

	}
}
