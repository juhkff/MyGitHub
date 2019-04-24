package element;

import uiDao.CardPanel;

/*
 * (共需四个)放置卡牌堆
 */
public class CardStack {
	private CardStackNode top = null;
	private int cardNum = 0;

	public CardStackNode pullTopCard() { // 可能返回null值(最后一张牌时)
		if (this.top == null)
			return null;
		CardStackNode result = this.top;
		this.top = this.top.getNextNode();
		cardNum--;
		return result;
	}

	public void pushTopCard(CardPanel cardPanel) {
		CardStackNode cardStackNode = new CardStackNode(cardPanel);
		cardStackNode.setNextNode(top);
		this.top = cardStackNode;
		cardNum++;
	}

	public int getCardNum() {
		return cardNum;
	}
	
}
