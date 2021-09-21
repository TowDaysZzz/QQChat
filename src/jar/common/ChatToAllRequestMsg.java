package jar.common;

import Tools.MsgTypes;

public class ChatToAllRequestMsg implements Message {
	public static final String TYPE = MsgTypes.MESSAGE_TO_ALL_MSG;
	private String fromUser;
	private String msgid;
	private String content;

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static String getType() {
		return TYPE;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ChatToAllMsg{ fromUser='" + fromUser + "\'" + ",content='" + content + "\'" + "}";
	}

}
