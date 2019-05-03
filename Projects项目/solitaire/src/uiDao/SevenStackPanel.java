package uiDao;

import java.util.HashSet;
import java.util.Random;

import javax.swing.JLayeredPane;

import element.CardStackNode;
import element.StaticData;
import uiPaint.Index;

public class SevenStackPanel extends JLayeredPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private static CardStackNode top = null; // 底部靠上,顶部靠下
	// private static CardStackNode bottom = null; // 底部牌
	public int cardNum = 0;
	// private SevenStackCardPanelAdapter sevenStackCardPanelAdapter;
	private int location_X, location_Y, panelHeight, panelWidth;
	private int index;

	/**
	 * 七牌堆上面的待翻牌堆.传入牌堆的序数(第几个牌堆),index最小为1
	 * 
	 * @param index
	 */
	public SevenStackPanel(int index, JLayeredPane secondPanel/* ,CardStackNode top,CardStackNode bottom */) {
		super();
		// TODO Auto-generated constructor stub
		// 待翻牌堆的大小只会减小
		// SevenStackPanel.top=top;
		// SevenStackPanel.bottom=bottom;
		this.index = index;
		this.panelHeight = (index - 1) * StaticData.getMinilocation(3) + StaticData.getCardsize(3); // 初始高度
		this.panelWidth = StaticData.getCardsize(2); // 初始宽度
		this.location_X = (index - 1) * panelWidth + index * StaticData.getSevenstacklocation(0); // 距离最左边的距离
		this.location_Y = StaticData.getSevenstacklocation(1); // 距离顶部的距离
		// sevenStackCardPanelAdapter = new SevenStackCardPanelAdapter(index,
		// secondPanel);
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
				Index.cardPanelSet.add(cardPanel);
				// 设置卡牌在待翻牌堆中的位置
				cardPanel.setLocation(0, i * StaticData.getMinilocation(3));
				// 为每张卡牌设置监听器
				cardPanel
						.addMouseListener(/* sevenStackCardPanelAdapter */Index.sevenStackCardPanelAdapters[index - 1]); // 不完全确定可行
				cardPanel.addMouseMotionListener(
						/* sevenStackCardPanelAdapter */Index.sevenStackCardPanelAdapters[index - 1]); // 不完全确定可行
				// 将牌按层级添加到面板上
				Integer layer = i;
				this.add(cardPanel, layer); // 先加者在下

				// 将牌加入链表中
				if (Index.getTop(index) != null) {
					CardStackNode newNode = new CardStackNode(cardPanel);
					newNode.setNextNode(Index.getTop(index));
					Index.setTop(index, newNode);
				} else {
					CardStackNode top = new CardStackNode(cardPanel);
					top.setNextNode(null);
					Index.setTop(index, new CardStackNode(cardPanel));
					Index.setBottom(index, top);
				}
				this.cardNum++;
				// 从集合中移除
				set.remove(curIndex);
			} else {
				// 生成重复牌时,重新执行
				i--;
			}
		}
		// top.getStackNode().setCanTurnOver(true);
		Index.getTop(index).getStackNode().changeToFront();
	}

	/**
	 * 将牌加入链表中
	 * 
	 * @param newTopNode
	 */
	public void push(CardStackNode newTopNode) {
		// 将牌加入链表中
		if (Index.getTop(index) != null) {
			newTopNode.setNextNode(Index.getTop(index));
			Index.setTop(index, newTopNode);
		} else {
			newTopNode.setNextNode(null);
			Index.setTop(index, newTopNode);
			Index.setBottom(index, newTopNode);
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
			return Index.getTop(index);
		else {
			CardStackNode cardStackNode = Index.getTop(index);
			if (Index.getTop(index) != null) {
				Index.setTop(index, Index.getTop(index).getNextNode());
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
		// this.setBounds(this.location_X, this.location_Y, this.panelWidth,
		// this.panelHeight);
		this.setSize(this.panelWidth, this.panelHeight);
		// this.repaint();
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
		// this.setBounds(this.location_X, this.location_Y, this.panelWidth,
		// this.panelHeight);
		this.setSize(this.panelWidth, this.panelHeight);
		// this.repaint();
	}

}
