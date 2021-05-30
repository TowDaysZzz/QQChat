package Tools;

import java.io.Serializable;
import java.util.List;

import Beans.User;

/**
 * 
 * @ClassName: Message
 * @Description: TODO 信息类，传送的是类对象
 * @author TwoDaysZzz
 * @date 2021年5月18日
 *
 */
// 序列化
public class Message implements Serializable {
	static final long serialVersionUID = 1L;
	private String sender;// 发送者
	private String getter;// 接收者
	private String content;// 内容
	private String sendTime; // 发送时间
	private String msgType;// 消息类型
	private User user;
	private List<User> list;

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Message() {
		// TODO Auto-generated constructor stub
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getGetter() {
		return getter;
	}

	public void setGetter(String getter) {
		this.getter = getter;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendTime() {
		return sendTime;
	}

	public Message(String sender, String getter, String content, String sendTime, String msgType) {
		super();
		this.sender = sender;
		this.getter = getter;
		this.content = content;
		this.sendTime = sendTime;
		this.msgType = msgType;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

}
