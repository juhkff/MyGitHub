package element;

import uiDao.CardPanel;

public class CardPanelSet {
	private int cardNum;
	private CardPanel[] cardPanels;

	public CardPanelSet() {
		super();
		this.cardNum = 0;
		this.cardPanels = new CardPanel[StaticData.getCardnum()];
	}

	public void add(CardPanel cardPanel) {
		cardPanels[cardNum] = cardPanel;
		cardNum++;
	}

	public void reSizeCards() {
		if (this.cardNum == StaticData.getCardnum()) {
			for (CardPanel cardPanel : cardPanels) {
				cardPanel.resetCardSize();
				cardPanel.setBGUrl(StaticData.getBACKGROUNDURL());
				System.out.println("cardPanel的宽高已经改为:" + StaticData.getCardsize(2) + "," + StaticData.getCardsize(3));
			}
		}
	}

	public void rePaintCards() {
		if (this.cardNum == StaticData.getCardnum()) {
			for (CardPanel cardPanel : cardPanels) {
				cardPanel.setBGUrl(StaticData.getBACKGROUNDURL());
			}
		}
	}

	public int length() {
		return cardNum;
	}

	public CardPanel getCardPanel(int index) {
		return cardPanels[index];
	}
}
