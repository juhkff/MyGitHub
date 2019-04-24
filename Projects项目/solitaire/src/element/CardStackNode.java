package element;

import uiDao.CardPanel;

public class CardStackNode {
	private CardPanel stackNode;
	private CardStackNode nextNode;

	public CardStackNode(CardPanel stackNode) {
		super();
		this.stackNode = stackNode;
	}

	public CardStackNode(CardPanel stackNode, CardStackNode nextNode) {
		super();
		this.stackNode = stackNode;
		this.nextNode = nextNode;
	}

	public CardStackNode getNextNode() {
		return nextNode;
	}

	public void setNextNode(CardStackNode nextNode) {
		this.nextNode = nextNode;
	}

	public CardPanel getStackNode() {
		return stackNode;
	}

}
