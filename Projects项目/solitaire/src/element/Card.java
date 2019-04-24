package element;

import java.awt.Color;

public class Card {
	/*
	 * 纸牌的属性:正反、背景、值、下张牌. 方法:set正反(设置背景/值 为纸牌背景),get正反,下张牌值，是否可以翻面
	 */
	private boolean positive = false; // 正面?否
	private String backGroundUrl = null;
	private String value;
	private String[] nextCardValue;
	private String number;

	public Card(String value) {
		super();
		this.value = value;
		this.number = value.substring(1, 2);
		if (this.number.equals("0"))
			this.number = "10";
		this.nextCardValue = StaticData.getValueMapValue(value);
	}

	public boolean isPositive() {
		return positive;
	}

	public void setPositive(boolean positive) {
		this.positive = positive;
	}

	public String getBackGroundUrl() {
		return backGroundUrl;
	}

	public void setBackGroundUrl(String backGroundUrl) {
		this.backGroundUrl = backGroundUrl;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String[] getNextCardValue() {
		return nextCardValue;
	}

	public void setNextCardValue(String[] nextCardValue) {
		this.nextCardValue = nextCardValue;
	}

	public String getNumber() {
		return number;
	}

	public Color getColor() {
		String temp = this.value.substring(0, 1);
		if (temp.equals("A") || temp.equals("B"))
			return Color.RED;
		else
			return Color.BLACK;
	}
}
