package element;

import uiDao.CardPanel;

/*
 * (�����ĸ�)���ÿ��ƶ�
 */
public class CardStack {
	private CardStackNode top = null;
	private int cardNum = 0;

	public CardStackNode pullTopCard() { // ���ܷ���nullֵ(���һ����ʱ)
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
