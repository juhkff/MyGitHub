package uiDao;

import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import element.Card;
import element.StaticData;

public class CardPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel positiveLabel;
	private JLabel imgLabel;
	private ImageIcon positiveImg;
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

		this.card = new Card(value);
		if (backGroundUrl == null || backGroundUrl.equals(""))
			this.card.setBackGroundUrl(StaticData.getDefaultbackgroundurl());
		else if (new File(backGroundUrl).exists() && backGroundUrl.endsWith(".png"))
			this.card.setBackGroundUrl(backGroundUrl);
		else
			this.card.setBackGroundUrl(StaticData.getDefaultbackgroundurl());
		// System.out.println("卡牌的背景是:"+StaticData.getDefaultbackgroundurl());
		positiveImg = new ImageIcon("CARD\\" + card.getValue() + ".jpg");
		positiveImg.setImage(positiveImg.getImage().getScaledInstance(StaticData.getCardsize(2),
				StaticData.getCardsize(3), Image.SCALE_DEFAULT));
		backgroundImg = new ImageIcon(card.getBackGroundUrl());
		backgroundImg.setImage(backgroundImg.getImage().getScaledInstance(StaticData.getCardsize(2),
				StaticData.getCardsize(3), Image.SCALE_DEFAULT));
		positiveLabel = new JLabel(positiveImg);
		positiveLabel.setBounds(StaticData.getCardsize(0), StaticData.getCardsize(1), StaticData.getCardsize(2),
				StaticData.getCardsize(3));
		imgLabel = new JLabel(backgroundImg);
		imgLabel.setBounds(StaticData.getCardsize(0), StaticData.getCardsize(1), StaticData.getCardsize(2),
				StaticData.getCardsize(3));
		this.add(imgLabel);
		this.setBounds(0, 0, StaticData.getCardsize(2), StaticData.getCardsize(3));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		this.repaint();
	}

	/**
	 * 卡牌变为正面并重绘
	 */
	public void changeToFront() { // 卡牌变为正面
		this.remove(imgLabel);
		this.add(positiveLabel);
		this.card.setPositive(true);
		this.repaint();
	}

	/**
	 * 卡牌变为背面并重绘
	 */
	public void changeToBack() { // 卡牌变为背面
		this.remove(positiveLabel);
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

	public void setBGUrl(String newBGUrl) {
		if (newBGUrl == null || newBGUrl.equals(""))
			newBGUrl = StaticData.getDefaultbackgroundurl();
		this.card.setBackGroundUrl(newBGUrl);
		System.out.println("卡牌的背景图片重设为:" + newBGUrl);
		backgroundImg = new ImageIcon(card.getBackGroundUrl());
		backgroundImg.setImage(backgroundImg.getImage().getScaledInstance(StaticData.getCardsize(2),
				StaticData.getCardsize(3), Image.SCALE_DEFAULT));
		imgLabel.setIcon(backgroundImg);
		if (StaticData.isCARDSIZEChanged()) {
			System.out.println("正面修改!");
			positiveImg = new ImageIcon("CARD\\" + card.getValue() + ".jpg");
			positiveImg.setImage(positiveImg.getImage().getScaledInstance(StaticData.getCardsize(2),
					StaticData.getCardsize(3), Image.SCALE_DEFAULT));
			positiveLabel.setIcon(positiveImg);
		}
	}

	public void resetCardSize() {
		this.setSize(StaticData.getCardsize(2), StaticData.getCardsize(3));
		this.positiveLabel.setSize(StaticData.getCardsize(2), StaticData.getCardsize(3));
		this.imgLabel.setSize(StaticData.getCardsize(2), StaticData.getCardsize(3));
	}
}
