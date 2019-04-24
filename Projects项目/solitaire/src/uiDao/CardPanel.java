package uiDao;

import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import element.Card;
import element.StaticData;

public class CardPanel extends JPanel {
	// private JPanel contentPanel = new JPanel();
	private JLabel miniText = new JLabel();
	private JLabel mainText = new JLabel();
	private JLabel imgLabel;
	private ImageIcon backgroundImg;

	private Card card;
	private boolean canTurnOver = false;

	public CardPanel(String value) {
		this(value, "");
	}

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

		miniText.setText(card.getNumber());
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

	public void changeToFront() { // 卡牌变为正面
		this.remove(imgLabel);
		this.add(mainText);
		this.add(miniText);
		this.card.setPositive(true);
		this.repaint();
	}

	public void changeToBack() { // 卡牌变为背面
		this.remove(mainText);
		this.remove(miniText);
		this.add(imgLabel);
		this.card.setPositive(false);
		this.repaint();
	}

	public boolean isCanTurnOver() { // 能否翻面
		return canTurnOver;
	}

	public void setCanTurnOver(boolean canTurnOver) { // 设置能否翻面
		this.canTurnOver = canTurnOver;
	}

	public boolean isPositive() { // 是否在正面
		return card.isPositive();
	}

	public String getCardValue() { // 获得卡牌值
		return card.getValue();
	}

	public String getCardNumber() { // 获得卡牌纯数值
		return card.getNumber();
	}

	public String[] getNextCardValue() { // 获得下一张卡牌值
		return card.getNextCardValue();
	}
}
