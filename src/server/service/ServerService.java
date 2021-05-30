package server.service;

import java.util.List;

import Beans.User;
import server.dao.UserDao;

/**
 * 
 * @ClassName: ServerService
 * @Description: TODO 直接与数据库进行交互
 * @author TwoDaysZzz
 * @date 2021年5月18日
 *
 */
public class ServerService {
	static UserDao userDao = new UserDao();

	public static User getLogin(User user) {
		String id = user.getName();
		String pwd = user.getPwd();
		System.out.println("正在数据库检索...");
		String sql = "select * from user where name=? and pwd=?";
		User getUser = userDao.get(sql, User.class, id, pwd);
		if (getUser != null) {
			// 更改用户状态
			String sql1 = "update user set state=? WHERE name=?";
			userDao.update(sql1, 1, id);
		}
		System.out.println("个性签名：" + getUser.getSignature());
		return getUser;
	}

	// @Test
	public static List<User> getAllUsers() {
		System.out.println("正在数据库检索...");
		String sql = "select * from user ";
		List<User> list = userDao.query(sql, User.class);
		System.out.println("所有用户大小" + list.size());
		return list;
	}

	// @Test
	public void getLogin() {
		String id = "jw";
		String pwd = "991205";
		String sql = "select * from user where name=? and pwd=?";
		User user = userDao.get(sql, User.class, id, pwd);
		System.out.println(user.getEmail());
	}

	// @Test
	// public void getRigester() {
	// //
	// // String name = user.getName();
	// // String pwd = user.getPwd();
	// String sql = "INSERT INTO `user` VALUES (7, ?, ?, '127.0.0.1', '-1', '男',
	// '1952175699@qq.com', '2019-08-11 17:23:34', '2019-08-11 17:32:50', null,
	// '往后余生，风也是你，雨也是你，全都是你', '5', null, '1992-07-28')";
	// System.out.println(userDao.update(sql, "xx", "999999"));
	// }
	/// @Test
	public static boolean getRigester(User user) {
		//
		String name = user.getName();
		String pwd = user.getPwd();
		// System.out.println("用户注册的姓名" + user.getPwd());
		String sql = "select * from user ";
		int size = userDao.query(sql, User.class).size();
		String sql1 = "INSERT INTO `user` VALUES (?, ?, ?, '127.0.0.1', '-1', '男', '1952175699@qq.com', '2019-08-11 17:23:34', '2019-08-11 17:32:50', null, '往后余生，风也是你，雨也是你，全都是你', '5', null, '1992-07-28')";
		return userDao.update(sql1, size + 1, name, pwd);
	}

	public static boolean updateUser(User user) {
		return false;
	}
}
