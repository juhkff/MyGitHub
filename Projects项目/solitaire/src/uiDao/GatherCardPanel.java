package uiDao;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import element.CardStackNode;
import element.StaticData;

public class GatherCardPanel extends JPanel {
	private CardStackNode top;
	private int cardNum = 0;

	public GatherCardPanel(int gateherStack_No) { // 输入编号(1-4)
		super();
		// TODO Auto-generated constructor stub
		this.setName(String.valueOf(gateherStack_No));
		top = null;
		this.setBounds(
				StaticData.getGathercardlocation(0) + (gateherStack_No - 1)
						* (StaticData.getGathercardlocation(4) + StaticData.getGathercardlocation(2)),
				StaticData.getGathercardlocation(1), StaticData.getGathercardlocation(2),
				StaticData.getGathercardlocation(3));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.setBackground(Color.BLACK);
		this.setOpaque(false);
		this.setLayout(null);
	}

	public CardStackNode getTop(boolean isPull) {
		if (isPull) {
			if (top != null) {
				CardStackNode cardStackNode = top;
				top = top.getNextNode();
				cardNum--;
				this.remove(cardStackNode.getStackNode());
				this.repaint();
				return cardStackNode;
			} else {
				return null;
			}
		} else {
			if (top != null) {
				CardStackNode cardStackNode = top;
				return cardStackNode;
			} else {
				return null;
			}
		}
	}

	public void setTop(CardStackNode cardStackNode) { // 应该还需要添加一些更改卡牌属性的操作
		if (top == null) {
			top = cardStackNode;
			top.setNextNode(null);
		} else {
			cardStackNode.setNextNode(top);
			top = cardStackNode;
		}
		this.add(cardStackNode.getStackNode());
		this.repaint();
		cardNum++;
	}

	public int getCardNum() {
		return cardNum;
	}
}
