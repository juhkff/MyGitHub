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
				cardPanel.setSize(StaticData.getCardsize(2), StaticData.getCardsize(3));
			}
		}
	}
}
