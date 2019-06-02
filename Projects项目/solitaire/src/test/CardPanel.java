package test;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import element.StaticData;

public class CardPanel extends JPanel {
	// private JPanel contentPanel = new JPanel();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel imgLabel;
	private ImageIcon backgroundImg;

	public CardPanel(String value) {
		this(value, "");
	}

	public CardPanel(String value, String backGroundUrl) {
		// TODO Auto-generated constructor stub
		super();
		this.setLayout(null);
		// contentPanel.setLayout(null);
		// add(miniText);
		// add(mainText);

		backgroundImg = new ImageIcon("D:\\study\\各种文件\\头像.jpg");
		backgroundImg.setImage(backgroundImg.getImage().getScaledInstance(StaticData.getCardsize(2),
				StaticData.getCardsize(3), Image.SCALE_DEFAULT));
		imgLabel = new JLabel(backgroundImg);
		imgLabel.setBounds(StaticData.getCardsize(0), StaticData.getCardsize(1), StaticData.getCardsize(2),
				StaticData.getCardsize(3));
		// contentPanel.add(mainText);
		// contentPanel.add(miniText);
		// this.add(mainText);
		// this.add(miniText);
		this.add(imgLabel);
		this.setBounds(0, 0, StaticData.getCardsize(2), StaticData.getCardsize(3));

		// getContentPane().add(contentPanel);
	}
}
