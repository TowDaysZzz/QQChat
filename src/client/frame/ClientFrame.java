package client.frame;

import java.util.Scanner;

import Beans.User;
import client.service.ClientService;

/**
 * 
 * @ClassName: ClientFrame
 * @Description: TODO仅仅是个界面
 * @author TwoDaysZzz
 * @date 2021年5月18日
 *
 */
public class ClientFrame {
	static boolean flag = true;
	static Scanner scanner = new Scanner(System.in);
	static String Id;

	static void mainMenu() {
		while (flag) {
			System.out.println("=============欢迎登陆网络通信系统=============");
			System.out.println("1:用户登陆");
			System.out.println("2:用户注册");
			System.out.println("4:用户注销");
			System.out.println("请输入你的选择为:");
			int type = scanner.nextInt();
			scanner.nextLine();
			System.out.println("type:" + type);
			switch (type) {
			case 1:
				System.out.println("输入用户名：");
				String userID = scanner.nextLine();
				System.out.println("输入用户密码");
				String userPWD = scanner.nextLine();
				User user = new User(userID, userPWD);
				if (ClientService.sendLogin(user)) {
					System.out.println("进入第二级");
					Id = userID;

					while (true) {

						System.out.println("3:私聊");
						int type1 = scanner.nextInt();
						scanner.nextLine();
						System.out.println("type:" + type1);
						switch (type1) {
						case 3:

							break;

						default:
							break;
						}
					}

				} else {
					System.out.println("用户登陆失败，请重新登陆！！！！！！！");
				}
				break;

			// ClientService.sendExit(Id);
			default:
				break;
			}
		}
	}

	public static void main(String[] args) {
		mainMenu();
	}
}
