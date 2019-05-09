package uiDao;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import element.CardStackNode;
import element.StaticData;

public class GatherCardPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardStackNode top;
	private int cardNum = 0;

	/**
	 * 初始化置牌堆,应当有4个置牌堆,传入编号1~4
	 * 
	 * @param gateherStack_No
	 */
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
		this.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setBackground(Color.BLACK);
		this.setOpaque(false);
		this.setLayout(null);
	}

	public CardStackNode getTop() {
		return getTop(true);
	}

	/**
	 * 获得置牌堆顶部牌,传入是否将该牌从堆中取出
	 * 
	 * @param isPull
	 * @return CardStackNode-顶部牌
	 */
	public CardStackNode getTop(boolean isPull) {
		if (isPull) {
			if (top != null) {
				CardStackNode cardStackNode = top;
				top = top.getNextNode();
				cardNum--;
				this.remove(cardStackNode.getStackNode());
				if (top != null)
					this.add(top.getStackNode());
				// this.repaint();
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

	/**
	 * 将牌放入置牌堆顶部,传入一张卡牌
	 * 
	 * @param cardStackNode
	 */
	public void setTop(CardStackNode cardStackNode) { // 应该还需要添加一些更改卡牌属性的操作
		if (top == null) {
			top = cardStackNode;
			top.setNextNode(null);
		} else {
			this.remove(top.getStackNode());
			cardStackNode.setNextNode(top);
			top = cardStackNode;
		}
		this.add(cardStackNode.getStackNode());
		// this.repaint();
		cardNum++;
	}

	/**
	 * 获得置牌堆卡牌数
	 * 
	 * @return int-卡牌数
	 */
	public int getCardNum() {
		return cardNum;
	}
}
