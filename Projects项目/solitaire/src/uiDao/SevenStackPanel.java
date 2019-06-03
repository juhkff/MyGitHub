package uiDao;

import java.util.HashSet;
import java.util.Random;

import javax.swing.JLayeredPane;

import element.CardStackNode;
import element.StaticData;

public class SevenStackPanel extends JLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int cardNum = 0;
	private int location_X, location_Y, panelHeight, panelWidth;
	private int index;
	private GamePage jf;

	/**
	 * 七牌堆上面的待翻牌堆.传入牌堆的序数(第几个牌堆),index最小为1
	 * 
	 * @param index
	 */
	public SevenStackPanel(int index, JLayeredPane secondPanel, GamePage jf) {
		super();
		// TODO Auto-generated constructor stub
		// 待翻牌堆的大小只会减小
		this.jf = jf;
		this.index = index;
		this.panelHeight = (index - 1) * StaticData.getMinilocation(3) + StaticData.getCardsize(3); // 初始高度
		this.panelWidth = StaticData.getCardsize(2); // 初始宽度
		this.location_X = (index - 1) * panelWidth + index * StaticData.getSevenstacklocation(0); // 距离最左边的距离
		this.location_Y = StaticData.getSevenstacklocation(1); // 距离顶部的距离
		// 绘制待翻牌堆
		this.setBounds(location_X, location_Y, panelWidth, panelHeight);
		HashSet<Integer> set = StaticData.getCardIndexSet();
		Random random = new Random();
		for (int i = 0; i < index; i++) {
			int curIndex = random.nextInt(StaticData.getCardnum()); // 获取0-13*4之间的随机数(不包括13*4)
			if (set.contains(curIndex)) {
				// 未生成此牌时,生成此牌
				CardPanel cardPanel = new CardPanel(StaticData.getDeals(curIndex));
				// 用于更新牌的大小
				// GamePage.cardPanelSet.add(cardPanel);
				jf.getCardPanelSet().add(cardPanel);
				System.out.println("CardPanelSet的元素个数为:" + jf.getCardPanelSet().length());
				// 设置卡牌在待翻牌堆中的位置
				cardPanel.setLocation(0, i * StaticData.getMinilocation(3));
				// 为每张卡牌设置监听器
				cardPanel.addMouseListener(jf.getSevenStackCardPanelAdapters()[index - 1]); // 不完全确定可行
				cardPanel.addMouseMotionListener(jf.getSevenStackCardPanelAdapters()[index - 1]); // 不完全确定可行
				// 将牌按层级添加到面板上
				Integer layer = i;
				this.add(cardPanel, layer); // 先加者在下

				// 将牌加入链表中
				if (jf.getTop(index) != null) {
					CardStackNode newNode = new CardStackNode(cardPanel);
					newNode.setNextNode(jf.getTop(index));
					jf.setTop(index, newNode);
				} else {
					CardStackNode top = new CardStackNode(cardPanel);
					top.setNextNode(null);
					jf.setTop(index, new CardStackNode(cardPanel));
					jf.setBottom(index, top);
				}
				this.cardNum++;
				// 从集合中移除
				set.remove(curIndex);
			} else {
				// 生成重复牌时,重新执行
				i--;
			}
		}
		jf.getTop(index).getStackNode().changeToFront();
	}

	/**
	 * 将牌加入链表中
	 * 
	 * @param newTopNode
	 */
	public void push(CardStackNode newTopNode) {
		// 将牌加入链表中
		if (jf.getTop(index) != null) {
			newTopNode.setNextNode(jf.getTop(index));
			jf.setTop(index, newTopNode);
		} else {
			newTopNode.setNextNode(null);
			jf.setTop(index, newTopNode);
			jf.setBottom(index, newTopNode);
		}
		this.cardNum++;
	}

	/**
	 * 获取顶部/最下部的牌
	 * 
	 * @param isPull
	 * @return CardStackNode/null
	 */
	public CardStackNode getTop(boolean isPull) {
		if (!isPull)
			return jf.getTop(index);
		else {
			CardStackNode cardStackNode = jf.getTop(index);
			if (jf.getTop(index) != null) {
				jf.setTop(index, jf.getTop(index).getNextNode());
				this.cardNum--;
			}
			return cardStackNode;
		}
	}

	/**
	 * 获取牌堆的牌数
	 * 
	 * @return int
	 */
	public int getCardNum() {
		return cardNum;
	}

	/**
	 * 根据减少的牌数更改此牌堆的高度(从1->0的特殊情况暂时未考虑)
	 * 
	 * @param deleteCardNum
	 */
	public void resetHeightAfterDelete(int deleteCardNum) {
		this.panelHeight -= deleteCardNum * StaticData.getMinilocation(3);
		if (getCardNum() == 0) // 无牌时
			this.panelHeight += StaticData.getMinilocation(3);
		this.setSize(this.panelWidth, this.panelHeight);
	}

	/**
	 * 根据增加的牌数更改此牌堆的高度(从0->1的特殊情况暂时未考虑)
	 * 
	 * @param addCardNum
	 */
	public void resetHeightAfterAdd(int addCardNum) {
		this.panelHeight += addCardNum * StaticData.getMinilocation(3);
		if (getCardNum() - addCardNum == 0) // 未加前无牌时
			this.panelHeight -= StaticData.getMinilocation(3);
		this.setSize(this.panelWidth, this.panelHeight);
	}

	/**
	 * 重新应用卡牌上下间距
	 */
	public void reDistanceCards() {
		// TODO Auto-generated method stub
		CardStackNode tempTop = this.getTop(false);
		for (int j = 0; j < cardNum - 1; j++) {
			tempTop.getStackNode().setLocation(0, (cardNum - j - 1) * StaticData.getMinilocation(3));
			tempTop = tempTop.getNextNode();
		}
		this.panelWidth = StaticData.getCardsize(2); // 初始宽度
		this.panelHeight = (cardNum - 1) * StaticData.getMinilocation(3) + StaticData.getCardsize(3); // 初始高度
		if (cardNum == 0) {
			this.panelHeight += StaticData.getMinilocation(3);
		}
		this.setSize(this.panelWidth, this.panelHeight);
	}

}
