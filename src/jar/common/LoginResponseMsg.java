package jar.common;

import Beans.User;
import Tools.MsgTypes;

public class LoginResponseMsg implements Message {
	private static final String TYPE = MsgTypes.MESSAGE_LOGIN_RESPONSE;
	private String msgid;
	private User user;

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static String getType() {
		return TYPE;
	}

}
