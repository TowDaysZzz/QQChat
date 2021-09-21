package server.msgHandler;

import java.util.List;

import Beans.User;
import Tools.MsgTypes;
import jar.common.Message;

public class ServerListMsg implements Message {
	private static final String TYPE = MsgTypes.MESSAGE_FRIENG_LIST;
	private List<User> lists;
	private String toUser;

	public List<User> getLists() {
		return lists;
	}

	public void setLists(List<User> lists) {
		this.lists = lists;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public static String getType() {
		return TYPE;
	}

}
