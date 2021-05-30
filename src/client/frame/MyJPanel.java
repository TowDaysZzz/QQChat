package client.frame;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import Tools.MyTools;

public class MyJPanel extends JPanel {
	private Image image = null;

	public MyJPanel(String imagePath) {
		// 透明的
		this.setOpaque(false);
		image = MyTools.getIcon(imagePath).getImage();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
}
