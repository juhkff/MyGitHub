package uiDao;

import javax.swing.JPanel;

import element.CardStackNode;
import element.StaticData;

/*
 * 已翻开的发牌堆
 */
public class DealedStackPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardStackNode top = null;
	// private JPanel topPanel=null;
	private int dealedNum = 0;

	/**
	 * 初始化翻牌堆,应当只有一个翻牌堆
	 */
	public DealedStackPanel() {
		super();
		// topPanel=new JPanel();
		// topPanel.setBounds(StaticData.getCardsize(0), StaticData.getCardsize(0),
		// StaticData.getCardsize(0), StaticData.getCardsize(0));
		// topPanel.setOpaque(false);
		this.setBounds(StaticData.getDealedlocation(0), StaticData.getDealedlocation(1),
				StaticData.getDealedlocation(2), StaticData.getDealedlocation(3));
		this.setLayout(null);
		// this.add(topPanel);
		// this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}

	/**
	 * 将发牌堆中顶部牌放入已翻开的牌堆,实质即为setTop(CardStackNode)
	 * 
	 * @param cardStackNode
	 */
	public void pushToDealedStack(CardStackNode cardStackNode) {
		// 将发牌堆中顶部牌放入已翻开的牌堆
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
		dealedNum++;
	}

	/**
	 * 将已翻开的牌收集起来返回top(此堆最顶部的牌即是发牌堆最底部的牌),并重绘该翻牌堆
	 * 
	 * @return CardStackNode-顶部牌
	 */
	public CardStackNode selectAllDealedCard() {
		// 将已翻开的牌收集起来返回top(此堆最顶部的牌即是发牌堆最底部的牌)
		CardStackNode result = top;
		if (top != null)
			this.remove(top.getStackNode());
		while (top != null) {
			CardPanel curPanel = top.getStackNode();
			curPanel.changeToBack();
			// this.remove(curPanel);
			top = top.getNextNode();
		}
		this.repaint();
		dealedNum = 0;
		return result;
	}

	/**
	 * 获取顶部牌(取出顶部牌)
	 * 
	 * @return CardStackNode-顶部牌
	 */
	public CardStackNode getTop() {
		return getTop(true);
	}

	/**
	 * 获取顶部牌
	 * 
	 * @param isPull
	 * @return CardStackNode-顶部牌
	 */
	public CardStackNode getTop(boolean isPull) {
		if (!isPull)
			return top;
		CardStackNode cur = top;
		top = top.getNextNode();
		this.remove(cur.getStackNode());
		// this.repaint();
		if (top != null)
			this.add(top.getStackNode());
		dealedNum--;
		return cur;
	}

	/**
	 * 设置顶部牌
	 * 
	 * @param cardStackNode
	 */
	public void setTop(CardStackNode cardStackNode) {
		if (top == null) {
			top = cardStackNode;
			top.setNextNode(null);
		} else {
			this.remove(top.getStackNode());
			cardStackNode.setNextNode(top);
			top = cardStackNode;
		}
		dealedNum++;
		this.add(cardStackNode.getStackNode());
	}

	/**
	 * 获得翻牌堆的牌数
	 * 
	 * @return int-牌数
	 */
	public int getDealedNum() {
		return dealedNum;
	}

}
