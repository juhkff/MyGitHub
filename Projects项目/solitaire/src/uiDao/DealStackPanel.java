package uiDao;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import element.CardStackNode;
import element.StaticData;

/*
 * 发牌堆
 */
public class DealStackPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardStackNode top = null;
	private int dealNum = 0;

	public DealStackPanel(Set<Integer> thisSet) {
		super();
		this.setBounds(StaticData.getPanelsize(0), StaticData.getPanelsize(1), StaticData.getPanelsize(2),
				StaticData.getPanelsize(3));
		Random random = new Random();
		this.setLayout(null);
		for (int i = 0; i < StaticData.getDealnum(); i++) {
			int curIndex = random.nextInt(StaticData.getCardnum()); // 获取0-13*4之间的随机数(不包括13*4)
			if (thisSet.contains(curIndex)) {
				// 未生成此牌时,生成此牌
				CardPanel cardPanel = new CardPanel(StaticData.getDeals(curIndex));
				pushCardStackNode(new CardStackNode(cardPanel), true);
				this.add(cardPanel);
				thisSet.remove(curIndex);
			} else {
				// 生成重复牌时,重新执行
				i--;
			}
		}
		// 生成完毕所有发牌后
		top.getStackNode().setCanTurnOver(true);
	}

	public CardStackNode pullFromDealStack() {
		// 从发牌堆中翻开最上面的牌
		CardStackNode cur = top;
		top = top.getNextNode();

		if (cur.getStackNode().isCanTurnOver()) {
			cur.getStackNode().changeToFront();
		}
		this.remove(cur.getStackNode());
		if (top != null) {
			top.getStackNode().setCanTurnOver(true);
		}
		if (top == null) {

		}
		cur.setNextNode(null);
		dealNum--;
		this.repaint();
		return cur;
	}

	public void pushCardStackNode(CardStackNode curStackNode, boolean isSingle) {
		// 放入发牌堆
		if (isSingle) {
			if (top == null) {
				top = curStackNode;
				top.getStackNode().setCanTurnOver(true);
			} else {
				curStackNode.setNextNode(top);
				top.getStackNode().setCanTurnOver(false);
				top = curStackNode;
				top.getStackNode().setCanTurnOver(true);
			}
			dealNum++;
		} else {
			// 将翻过的牌重新洗入
			CardStackNode tempNode;
			while (curStackNode != null) {
				tempNode = curStackNode.getNextNode();
				if (top == null) {
					top = curStackNode;
					top.setNextNode(null);
					top.getStackNode().setCanTurnOver(false);
				} else {
					curStackNode.setNextNode(top);
					top = curStackNode;
					top.getStackNode().setCanTurnOver(false);
				}
				this.add(curStackNode.getStackNode());
				dealNum++;
				curStackNode = tempNode;
			}
			top.getStackNode().setCanTurnOver(true);
		}
	}

	public int getDealCardNum() {
		return dealNum;
	}

	public boolean isPositive() {
		return top.getStackNode().isPositive();
	}
}
