package Tools;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * 
 * @ClassName: MyTools
 * @Description: TODO工具类，，很多长代码
 * @author TwoDaysZzz
 * @date 2021年5月22日
 *
 */
public class MyTools {
	/**
	 * 根据文件路径获取图片
	 * 
	 * @param path
	 *            路径
	 * @return 返回获取的图片
	 */
	public static ImageIcon getIcon(String path) {
		try {
			ImageIcon icon = new ImageIcon(ImageIO.read(MyTools.class.getResource(path)));
			return icon;
		} catch (IOException e) {
			System.out.println("图片：" + path + "不存在！");
			return null;
		}
	}

	/**
	 * 将窗体居中显示
	 * 
	 * @param frame
	 *            需要居中显示的窗体
	 */
	public static void setWindowsMiddleShow(JFrame frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds((screenSize.width - frame.getWidth()) / 2, (screenSize.height - frame.getHeight()) / 2,
				frame.getWidth(), frame.getHeight());
	}
}
