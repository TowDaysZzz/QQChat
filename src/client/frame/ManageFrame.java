package client.frame;

import java.util.HashMap;

import client.Control.Main;

public class ManageFrame {
	static HashMap<String, Main> map = new HashMap<>();

	public static void add(String name, Main jFrame) {
		map.put(name, jFrame);
	}

	public static Main get(String name) {
		return map.get(name);
	}
}
