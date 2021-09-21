package jar.common;

import Tools.MsgTypes;
import io.netty.channel.ChannelId;

public class LoginRequestMsg implements Message {
	public static final String TYPE = MsgTypes.MESSAGE_LOGIN_REQUEST;
	private String msgid;
	private String username;
	private String pwd;
	private ChannelId fromUser;

	public String getMsgid() {
		return msgid;
	}

	public ChannelId getFromUser() {
		return fromUser;
	}

	public void setFromUser(ChannelId fromUser) {
		this.fromUser = fromUser;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public static String getType() {
		return TYPE;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "LoginMsg{ username ='" + username + "\',pwd='" + pwd + "\'}";
	}

}
