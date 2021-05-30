package Tools;

import java.util.List;

import org.junit.Test;

import Beans.User;
import server.dao.UserDao;

public class DBtest {
	// @Test
	public void getSize() {
		// Connection conn;
		// try {
		// conn = DruidUtils.getConn();
		// System.out.println(conn);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		String sql = "select * from user";
		UserDao userDao = new UserDao();
		List<User> query = userDao.query(sql, User.class);
		System.out.println("数量为：" + query.size());
		for (User user : query) {
			System.out.print(user.getLastLogin());
			System.out.print(user.getLastExit());
			System.out.print(user.getHeadImg());
			System.out.println();
		}

	}

	@Test
	public void getUser() {
		String sql = "select * from user where name='jie'";
		UserDao userDao = new UserDao();
		User user = userDao.get(sql, User.class);

		System.out.println(user.getEmail());

	}
}
