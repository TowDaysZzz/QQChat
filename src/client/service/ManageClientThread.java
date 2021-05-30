package client.service;

import java.util.HashMap;

public class ManageClientThread {
	// userId,
	static HashMap<String, ClientToServerThread> map = new HashMap<>();

	static public void add(String userId, ClientToServerThread thread) {
		map.put(userId, thread);
	}

	static public ClientToServerThread get(String userId) {
		return map.get(userId);
	}

	static public int getOnlinePerson() {
		return map.size();
	}

}
