package live_UI;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UI_LiveRoom {
	private inner_UI mainFrame;
	private ImageInputStream imgInputStream = null;
	private BufferedImage img = null;

	UI_LiveRoom(InputStream inputStream) {
		try {
			this.imgInputStream = ImageIO.createImageInputStream(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mainFrame = new inner_UI();
		mainFrame.setTitle("直播间");
		// ui_Register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置窗口大小不可变
		mainFrame.setResizable(false);
		// 设置窗口打开居中
		mainFrame.setLocationRelativeTo(null);
		// 窗口大小
		mainFrame.setSize(1920, 1080);
		// 展示窗口
		mainFrame.setVisible(true);
	}

	public void startLive() {
		mainFrame.beginLive();
	}

	/*
	 * private UI_LiveRoom() { this.mainFrame = new inner_UI();
	 * mainFrame.setTitle("直播间"); //
	 * ui_Register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口大小不可变
	 * mainFrame.setResizable(false); // 设置窗口打开居中
	 * mainFrame.setLocationRelativeTo(null); // 窗口大小 mainFrame.setSize(1920, 1080);
	 * // 展示窗口 mainFrame.setVisible(true); }
	 */

	private class inner_UI extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JPanel mainPanel = new JPanel();
		private JLabel image = new JLabel();

		public inner_UI() {
			// TODO Auto-generated constructor stub
			mainPanel.setLayout(null);
			mainPanel.add(image);
			// mainPanel.setBounds(0, 0, 1920, 1080);
			image.setBounds(0, 0, 1920, 1080);

			getContentPane().add(mainPanel);
		}
		
		public void beginLive() {
			try {
				int i=0;
				while (true) {
					img = ImageIO.read(imgInputStream);
					if (img != null) {
						System.out.println("进行第"+i+"次屏幕读取\t\t");
						File file=new File("D:\\新建文件夹 (3)\\新建文件夹\\test.jpg");
						//FileOutputStream fileOutputStream=new FileOutputStream(file);
						ImageIO.write(img, "JPG", file);
						//image.setIcon(new ImageIcon(img));
						//image.repaint();
						System.out.println("第"+i+"次屏幕读取成功");
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// UI_LiveRoom test = new UI_LiveRoom();
	}
}
