package uiDao;

import java.awt.Color;
import java.util.Random;
import java.util.Set;

import javax.swing.BorderFactory;
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

	/**
	 * 初始化发牌堆,应当只有一个发牌堆,传入0-牌堆数量上限的Set<Integer>
	 * 
	 * @param thisSet
	 */
	public DealStackPanel(Set<Integer> thisSet, GamePage jf) {
		super();
		this.setBounds(StaticData.getPanelsize(0), StaticData.getPanelsize(1), StaticData.getPanelsize(2),
				StaticData.getPanelsize(3));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		Random random = new Random();
		this.setLayout(null);
		for (int i = 0; i < StaticData.getDealnum(); i++) {
			int curIndex = random.nextInt(StaticData.getCardnum()); // 获取0-13*4之间的随机数(不包括13*4)
			if (thisSet.contains(curIndex)) {
				// 未生成此牌时,生成此牌
				CardPanel cardPanel;
				if (StaticData.getBACKGROUNDURL() == null || StaticData.getBACKGROUNDURL().equals(""))
					cardPanel = new CardPanel(StaticData.getDeals(curIndex));
				else
					cardPanel = new CardPanel(StaticData.getDeals(curIndex), StaticData.getBACKGROUNDURL());
				pushCardStackNode(new CardStackNode(cardPanel), true);
				// 用于更新牌的大小
				jf.getCardPanelSet().add(cardPanel);
				System.out.println("CardPanelSet的元素个数为:" + jf.getCardPanelSet().length());
				thisSet.remove(curIndex);
			} else {
				// 生成重复牌时,重新执行
				i--;
			}
		}
		this.add(top.getStackNode());
		// 生成完毕所有发牌后
		top.getStackNode().setCanTurnOver(true);
	}

	/**
	 * 从发牌堆中翻开最上面的牌(若顶部已经无牌则重绘)
	 * 
	 * @return CardStackNode-顶部牌
	 */
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
			this.add(top.getStackNode());
		} else {
			this.repaint();
		}
		cur.setNextNode(null);
		dealNum--;
		// this.repaint();
		return cur;
	}

	/**
	 * 放入发牌堆,传入卡牌与是否为单张牌.若isSingle==true,则重绘
	 * 
	 * @param curStackNode
	 * @param isSingle
	 */
	public void pushCardStackNode(CardStackNode curStackNode, boolean isSingle) {
		// 放入发牌堆
		if (isSingle) {
			if (top == null) {
				top = curStackNode;
				top.setNextNode(null);
				top.getStackNode().setCanTurnOver(true);
			} else {
				this.remove(top.getStackNode());
				curStackNode.setNextNode(top);
				top.getStackNode().setCanTurnOver(false);
				top = curStackNode;
				top.getStackNode().setCanTurnOver(true);
				this.add(top.getStackNode());
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
				dealNum++;
				curStackNode = tempNode;
			}
			if (top != null) {
				top.getStackNode().setCanTurnOver(true);
				this.add(top.getStackNode());
			}
			this.repaint(); // 为什么要repaint?
		}
	}

	/**
	 * 获得发牌堆牌数
	 * 
	 * @return int-牌数
	 */
	public int getDealCardNum() {
		return dealNum;
	}

	/**
	 * 发牌堆顶部牌是否在正面
	 * 
	 * @return boolean-顶部牌在正面
	 */
	public boolean isPositive() {
		return top.getStackNode().isPositive();
	}
}
