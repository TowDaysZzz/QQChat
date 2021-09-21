package jar.common;

import com.alibaba.fastjson.JSON;

/*
 * 防止沾包，丢包,发送的是 type+message
 */
public class MsgObject {
	/*
	 * 消息头
	 * 
	 */
	private String type;

	private String message;

	public MsgObject() {

	}

	public MsgObject(String type, String message) {
		this.type = type;
		this.message = message;
	}

	public MsgObject(String type, Message message) {
		this.type = type;
		this.message = JSON.toJSONString(message);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "MsgObject{ type='" + type + '\'' + ",message='" + message + '\'' + '}';
	}

}
