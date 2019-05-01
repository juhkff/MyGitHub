package uiDao;

import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import element.Card;
import element.StaticData;

public class CardPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private JPanel contentPanel = new JPanel();
	private JLabel miniText = new JLabel();
	private JLabel mainText = new JLabel();
	private JLabel typeText = new JLabel();
	private JLabel imgLabel;
	private ImageIcon backgroundImg;

	private Card card;
	private boolean canTurnOver = false;

	/**
	 * 构造方法,仅设定卡牌值,背景使用默认路径
	 * 
	 * @param value
	 */
	public CardPanel(String value) {
		this(value, "");
	}

	/**
	 * 构造方法,设定卡牌值和卡牌背景
	 * 
	 * @param value
	 * @param backGroundUrl
	 */
	public CardPanel(String value, String backGroundUrl) {
		// TODO Auto-generated constructor stub
		super();
		this.setName("cardPanel");
		this.setLayout(null);
		// contentPanel.setLayout(null);
		// add(miniText);
		// add(mainText);

		this.card = new Card(value);
		if (backGroundUrl == null || backGroundUrl.equals(""))
			this.card.setBackGroundUrl(StaticData.getDefaultbackgroundurl());
		else if (new File(backGroundUrl).exists() && backGroundUrl.endsWith(".png"))
			this.card.setBackGroundUrl(backGroundUrl);
		else
			this.card.setBackGroundUrl(StaticData.getDefaultbackgroundurl());

		String prefix = value.substring(0, 1);
		switch (prefix) {
		case "A":
			prefix = "方块";
			break;
		case "B":
			prefix = "红桃";
			break;
		case "C":
			prefix = "黑桃";
			break;
		case "D":
			prefix = "梅花";
			break;
		default:
			break;
		}
		typeText.setText(prefix);
		typeText.setForeground(card.getColor());
		typeText.setFont(StaticData.getTypefont());
		typeText.setBounds(StaticData.getTypelocation(0), StaticData.getTypelocation(1), StaticData.getTypelocation(2),
				StaticData.getTypelocation(3));
		typeText.setOpaque(false);
		// typeText.setHorizontalAlignment(SwingConstants.CENTER); // 设置控件左右居中

		miniText.setText(/* prefix + " " + */card.getNumber());
		miniText.setForeground(card.getColor());
		miniText.setFont(StaticData.getMiniTextFont());
		miniText.setBounds(StaticData.getMinilocation(0), StaticData.getMinilocation(1), StaticData.getMinilocation(2),
				StaticData.getMinilocation(3));
		// miniText.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		miniText.setOpaque(false);
		miniText.setHorizontalAlignment(SwingConstants.CENTER); // 设置控件左右居中

		mainText.setText(card.getNumber());
		mainText.setForeground(card.getColor());
		mainText.setFont(StaticData.getMainTextFont());
		mainText.setBounds(StaticData.getMainlocation(0), StaticData.getMainlocation(1), StaticData.getMainlocation(2),
				StaticData.getMainlocation(3));
		// mainText.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
		mainText.setOpaque(false);
		mainText.setHorizontalAlignment(SwingConstants.CENTER); // 设置控件左右居中

		backgroundImg = new ImageIcon(card.getBackGroundUrl());
		backgroundImg.setImage(backgroundImg.getImage().getScaledInstance(StaticData.getCardsize(2),
				StaticData.getCardsize(3), Image.SCALE_DEFAULT));
		imgLabel = new JLabel(backgroundImg);
		imgLabel.setBounds(StaticData.getCardsize(0), StaticData.getCardsize(1), StaticData.getCardsize(2),
				StaticData.getCardsize(3));
		// contentPanel.add(mainText);
		// contentPanel.add(miniText);
		if (card.getNumber().equals("10")) {
			mainText.setFont(StaticData.getMainTextFont_10());
			miniText.setFont(StaticData.getMiniTextFont_10());
		}
		// this.add(mainText);
		// this.add(miniText);
		this.add(imgLabel);
		this.setBounds(0, 0, StaticData.getCardsize(2), StaticData.getCardsize(3));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		// getContentPane().add(contentPanel);
	}

	/**
	 * 卡牌变为正面并重绘
	 */
	public void changeToFront() { // 卡牌变为正面
		this.remove(imgLabel);
		this.add(mainText);
		this.add(miniText);
		this.add(typeText);
		this.card.setPositive(true);
		this.repaint();
	}

	/**
	 * 卡牌变为背面并重绘
	 */
	public void changeToBack() { // 卡牌变为背面
		this.remove(mainText);
		this.remove(miniText);
		this.remove(typeText);
		this.add(imgLabel);
		this.card.setPositive(false);
		this.repaint();
	}

	/**
	 * 查看卡牌能否翻面
	 * 
	 * @return boolean 能-true,不能-false
	 */
	public boolean isCanTurnOver() { // 能否翻面
		return canTurnOver;
	}

	/**
	 * 设置卡牌能否翻面
	 * 
	 * @param canTurnOver
	 */
	public void setCanTurnOver(boolean canTurnOver) { // 设置能否翻面
		this.canTurnOver = canTurnOver;
	}

	/**
	 * 查看卡牌是否在正面
	 * 
	 * @return boolean 正面-true,背面-false
	 */
	public boolean isPositive() { // 是否在正面
		return card.isPositive();
	}

	/**
	 * 获得卡牌值(完整)
	 * 
	 * @return String-卡牌值
	 */
	public String getCardValue() { // 获得卡牌值
		return card.getValue();
	}

	/**
	 * 获得卡牌纯数值
	 * 
	 * @return String-卡牌纯数值
	 */
	public String getCardNumber() { // 获得卡牌纯数值
		return card.getNumber();
	}

	/**
	 * 获得下一张卡牌值
	 * 
	 * @return String[]下一张牌对应的值
	 */
	public String[] getNextCardValue() { // 获得下一张卡牌值
		return card.getNextCardValue();
	}
}
