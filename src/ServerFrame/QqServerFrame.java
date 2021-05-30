package ServerFrame;

import server.service.ManageAcceptThread;

public class QqServerFrame {
	public static void main(String[] args) {
		new ManageAcceptThread(9999);
	}
}
