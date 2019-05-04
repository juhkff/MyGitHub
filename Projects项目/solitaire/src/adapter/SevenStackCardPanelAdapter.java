package adapter;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import element.CardStackNode;
import element.StaticData;
import tools.FindComponent;
import tools.SolitaireCheck;
import uiDao.CardPanel;
import uiPaint.Index;

public class SevenStackCardPanelAdapter extends MouseAdapter {
	// private static CardStackNode top, bottom;
	private String startComponentName; // 选取卡牌所在的卡堆,用于排除卡牌放回到自身原牌堆的情况
	private int mouseStartX, mouseStartY; // 鼠标按下时坐标
	private int mouseEndX, mouseEndY; // 鼠标移动时坐标
	private int[] componentX, componentY; // 控件坐标
	private JLayeredPane secondPanel;
	private CardPanel cardPanel;
	private CardStackNode bottom = null; // 用于恢复移动前情形
	private boolean isLock = false; // 鼠标是否在拖动卡牌
	private int index;
	private int cardNum;

	public SevenStackCardPanelAdapter(int index, JLayeredPane secondPanel) {
		super();
		// SevenStackCardPanelAdapter.top = top;
		// SevenStackCardPanelAdapter.bottom = bottom;
		this.index = index;
		this.secondPanel = secondPanel;
		this.componentX = new int[30];
		this.componentY = new int[30];
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseClicked(arg0);
		if (arg0.getButton() == MouseEvent.BUTTON3) {
			if (Index.isHasClicked()) {
				System.out.println("触发第二次点击");
				CardStackNode recBottom = Index.getTranBottom();
				CardPanel cardPanel = Index.cardPanel;
				String sendName = Index.getClickComponentName();
				if (sendName.startsWith("sevenStackPanel")) {
					int length = sendName.length();
					int startLength = "sevenStackPanel".length();
					String indexStr = sendName.substring(startLength, length);
					int Gindex = Integer.parseInt(indexStr); // 获得传入的是哪个下标的牌堆
					if ((Index.sevenStackPanels[index - 1].cardNum == 0 && cardPanel.getCardNumber().equals("K"))
							|| (Index.sevenStackPanels[index - 1].cardNum > 0
									&& SolitaireCheck.canPushToSevenStack(index, cardPanel))) {
						// 可以插入
						System.out.println("可以插入");
						CardStackNode top = Index.getTop(Gindex); // 获得对应的卡牌堆
						int clickCardNum = 0;
						while (recBottom != null) {
							clickCardNum++;
							CardPanel cur = recBottom.getStackNode();
							recBottom = recBottom.getNextNode();
							cur.setLocation(0,
									(Index.sevenStackPanels[index - 1].getCardNum()) * StaticData.getMinilocation(3));
							Index.sevenStackPanels[index - 1].add(cur,
									new Integer(Index.sevenStackPanels[index - 1].getCardNum() + 1));
							cur.removeMouseListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
							cur.removeMouseMotionListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
							cur.addMouseListener(Index.sevenStackCardPanelAdapters[index - 1]);
							cur.addMouseMotionListener(Index.sevenStackCardPanelAdapters[index - 1]);
							CardStackNode newTop = new CardStackNode(cur);
							System.out.println("插入的卡牌值为:" + cur.getCardValue());
							newTop.setNextNode(Index.getTop(index));
							Index.setTop(index, newTop);
							Index.sevenStackPanels[index - 1].cardNum++;

							Index.sevenStackPanels[Gindex - 1].remove(top.getStackNode());
							// Index.setTop(Gindex, top.getNextNode());
							top = top.getNextNode();
							Index.sevenStackPanels[Gindex - 1].cardNum--;
						}
						System.out.println("插入了" + clickCardNum + "张牌");
						System.out.println("验证---添加后的卡堆的顶部牌值为:" + Index.getTop(index).getStackNode().getCardValue());
						Index.sevenStackPanels[index - 1].resetHeightAfterAdd(clickCardNum);
						Index.sevenStackPanels[index - 1].repaint();

						Index.setTop(Gindex, top);
						Index.sevenStackPanels[Gindex - 1].resetHeightAfterDelete(clickCardNum);
						Index.sevenStackPanels[Gindex - 1].repaint();
						if (Index.sevenStackPanels[Gindex - 1].getCardNum() == 0) {
							// 设置底纹边框
							Index.sevenStackPanels[Gindex - 1]
									.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
						} else {
							// 去除边框(视觉上其实没啥用)
							Index.sevenStackPanels[Gindex - 1].setBorder(null);
						}
					}
				} else if (sendName.startsWith("gatherCardPanel")) {
					int length = sendName.length();
					int startLength = "gatherCardPanel".length();
					String indexStr = sendName.substring(startLength, length);
					int Gindex = Integer.parseInt(indexStr); // 获得传入的是哪个下标的牌堆
					if ((Index.sevenStackPanels[index - 1].cardNum == 0 && cardPanel.getCardNumber().equals("K"))
							|| (Index.sevenStackPanels[index - 1].cardNum > 0
									&& SolitaireCheck.canPushToSevenStack(index, cardPanel))) {
						// 可以传入
						Index.gatherCardPanels[Gindex - 1].getTop(true);
						CardPanel cur = Index.cardPanel;
						cur.setLocation(0,
								(Index.sevenStackPanels[index - 1].getCardNum()) * StaticData.getMinilocation(3));
						Index.sevenStackPanels[index - 1].add(cur,
								new Integer(Index.sevenStackPanels[index - 1].getCardNum() + 1));
						cur.addMouseListener(Index.sevenStackCardPanelAdapters[index - 1]);
						cur.addMouseMotionListener(Index.sevenStackCardPanelAdapters[index - 1]);
						CardStackNode newTop = new CardStackNode(cur);
						System.out.println("插入的卡牌值为:" + cur.getCardValue());
						newTop.setNextNode(Index.getTop(index));
						Index.setTop(index, newTop);
						Index.sevenStackPanels[index - 1].cardNum++;
						System.out.println("验证---添加后的卡堆的顶部牌值为:" + Index.getTop(index).getStackNode().getCardValue());
						Index.sevenStackPanels[index - 1].resetHeightAfterAdd(1);
						Index.gatherCardPanels[Gindex - 1].repaint();
						Index.sevenStackPanels[index - 1].repaint();
					}
				} else if (sendName.equals("dealedStackPanel")) {
					if ((Index.sevenStackPanels[index - 1].cardNum == 0 && cardPanel.getCardNumber().equals("K"))
							|| (Index.sevenStackPanels[index - 1].cardNum > 0
									&& SolitaireCheck.canPushToSevenStack(index, cardPanel))) {
						Index.sevenStackPanels[index - 1].add(cardPanel,
								new Integer(Index.sevenStackPanels[index - 1].getCardNum() + 1));
						cardPanel.setLocation(0,
								(Index.sevenStackPanels[index - 1].getCardNum()) * StaticData.getMinilocation(3));
						cardPanel.addMouseListener(Index.sevenStackCardPanelAdapters[index - 1]);
						cardPanel.addMouseMotionListener(Index.sevenStackCardPanelAdapters[index - 1]);
						CardStackNode newTop = new CardStackNode(cardPanel);
						System.out.println("放入的卡牌值为:" + cardPanel.getCardValue());
						newTop.setNextNode(Index.getTop(index));
						Index.setTop(index, newTop);
						Index.sevenStackPanels[index - 1].cardNum++;
						System.out.println("验证---添加后的卡堆的顶部牌值为:" + Index.getTop(index).getStackNode().getCardValue());
						Index.sevenStackPanels[index - 1].resetHeightAfterAdd(1);
						Index.dealedStackPanel.getTop();
						Index.dealedStackPanel.repaint();
						Index.sevenStackPanels[index - 1].repaint();
					}
				}
				Index.refresh();
			} else {
				// 第一次点击
				this.cardPanel = (CardPanel) arg0.getComponent();
				if (cardPanel.isPositive()) {
					// 卡牌在正面
					Index.setHasClicked(true);
					CardStackNode top = Index.getTop(index); // 获得对应的卡牌堆
					System.out.println("第" + index + "个牌堆的牌数为" + Index.sevenStackPanels[index - 1].cardNum);
					// System.out.println("第" + index + "个牌堆的top卡牌值为" +
					// top.getStackNode().getCardValue());
					int i = 0;
					while (top.getStackNode().getCardValue() != cardPanel.getCardValue()) {
						i++;
						if (bottom == null) {
							bottom = new CardStackNode(top.getStackNode());
							bottom.setNextNode(null);
						} else {
							CardStackNode newNode = new CardStackNode(top.getStackNode());
							newNode.setNextNode(bottom);
							bottom = newNode;
						}
						top = top.getNextNode();
					}
					// 等于后
					i++;
					System.out.println("i=" + i);
					cardNum = i;
					if (bottom == null) {
						bottom = new CardStackNode(top.getStackNode());
						bottom.setNextNode(null);
					} else {
						CardStackNode newNode = new CardStackNode(top.getStackNode());
						newNode.setNextNode(bottom);
						bottom = newNode;
					}
					if (cardNum == 1) {
						Index.setSingle(true);
						Index.setTranBottom(bottom);
						Index.cardPanel = bottom.getStackNode();
						Index.setClickComponentName(Index.sevenStackPanels[index - 1].getName());
					} else {
						Index.setSingle(false);
						Index.setTranBottom(bottom);
						Index.cardPanel = bottom.getStackNode();
						Index.setClickComponentName(Index.sevenStackPanels[index - 1].getName());
					}
					bottom = null; // 返回初始状态
				}
			}
		} else if (arg0.getButton() == MouseEvent.BUTTON1) {
			System.out.println("触发点击");
			this.bottom = null;
			this.cardPanel = (CardPanel) arg0.getComponent();
			System.out.println(cardPanel.getCardValue());
			System.out.println(Index.getTop(index).getStackNode().getCardValue());
			if (Index.getTop(index) != null
					&& Index.getTop(index).getStackNode().getCardValue() == cardPanel.getCardValue()
					&& !cardPanel.isPositive()) {
				// 底部牌在背面时
				System.out.println("底部牌在背面,将其翻到正面");
				cardPanel.changeToFront();
			} else if (Index.getTop(index).getStackNode().getCardValue() == cardPanel.getCardValue()
					&& cardPanel.isPositive()) {
				System.out.println("底部牌在正面");
			} else if (Index.getTop(index).getStackNode().getCardValue() != cardPanel.getCardValue()) {
				System.out.println("点击的不是底部牌");
			}
			/*
			 * CardStackNode cardStackNode = cardPanel.getTop(false).getNextNode(); if
			 * (cardPanel.getCardNum() > 0 && cardStackNode != null &&
			 * cardStackNode.getStackNode().isPositive()) { // 底部牌在背面时
			 * System.out.println("底部牌在背面,将其翻到正面");
			 * cardStackNode.getStackNode().changeToFront(); }
			 */
			Index.refresh();
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseDragged(arg0);
		this.cardPanel = (CardPanel) arg0.getComponent();
		if (isLock) {
			mouseEndX = arg0.getXOnScreen();
			mouseEndY = arg0.getYOnScreen();
			// System.out.println("top的卡牌值为:" + top.getStackNode().getCardValue());
			int i = 0;
			/*
			 * while (top.getStackNode().getCardValue() != cardPanel.getCardValue()) { i++;
			 * CardPanel cur = top.getStackNode(); int component_X = componentX[i - 1]; int
			 * component_Y = componentY[i - 1]; System.out.println("cur.setLocation(" +
			 * (component_X + mouseEndX - mouseStartX) + "," + (component_Y + mouseEndY -
			 * mouseStartY) + ")"); cur.setLocation(component_X + mouseEndX - mouseStartX,
			 * component_Y + mouseEndY - mouseStartY); top = top.getNextNode(); }
			 */
			CardStackNode curNode = bottom;
			while (curNode != null) {
				i++;
				CardPanel cur = curNode.getStackNode();
				int component_X = componentX[cardNum - i];
				int component_Y = componentY[cardNum - i];
				// System.out.println("cur.setLocation(" + (component_X + mouseEndX -
				// mouseStartX) + ","
				// + (component_Y + mouseEndY - mouseStartY) + ")");
				cur.setLocation(component_X + mouseEndX - mouseStartX, component_Y + mouseEndY - mouseStartY);
				curNode = curNode.getNextNode();
			}
			// System.out.println("循环完毕");
			// 等于后
			/*
			 * i++; System.out.println("cardNum-i==0?---" + ((cardNum - i == 0) ? true :
			 * false)); CardPanel cur = top.getStackNode(); int component_X =
			 * componentX[cardNum - i]; // cardNum-i==0 int component_Y = componentY[cardNum
			 * - i]; // cardNum-i==0 System.out.println("cur.setLocation(" + (component_X +
			 * mouseEndX - mouseStartX) + "," + (component_Y + mouseEndY - mouseStartY) +
			 * ")"); cur.setLocation(component_X + mouseEndX - mouseStartX, component_Y +
			 * mouseEndY - mouseStartY);
			 */
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mousePressed(arg0);
		if (arg0.getButton() != MouseEvent.BUTTON1) {
			return;
		}
		System.out.println("触发按下");
		this.cardPanel = (CardPanel) arg0.getComponent();
		mouseStartX = arg0.getXOnScreen();
		mouseStartY = arg0.getYOnScreen();
		System.out.println(mouseStartX + "\t" + mouseStartY);
		System.out.println("选中卡牌的值为:" + cardPanel.getCardValue());
		if (cardPanel.isPositive()) {
			// 卡牌在正面,isLock才变为true
			int location_X = Index.sevenStackPanels[index - 1].getX();
			int location_Y = Index.sevenStackPanels[index - 1].getY();
			int stackHeight = Index.sevenStackPanels[index - 1].getHeight();
			int stackWidth = Index.sevenStackPanels[index - 1].getWidth();
			System.out.println(location_X + "\t" + location_Y + "\t" + stackHeight + "\t" + stackWidth);
			CardStackNode top = Index.getTop(index); // 获得对应的卡牌堆
			System.out.println("第" + index + "个牌堆的牌数为" + Index.sevenStackPanels[index - 1].cardNum);
			// System.out.println("第" + index + "个牌堆的top卡牌值为" +
			// top.getStackNode().getCardValue());
			int i = 0;
			while (top.getStackNode().getCardValue() != cardPanel.getCardValue()) {
				i++;
				if (bottom == null) {
					bottom = new CardStackNode(top.getStackNode());
					bottom.setNextNode(null);
				} else {
					CardStackNode newNode = new CardStackNode(top.getStackNode());
					newNode.setNextNode(bottom);
					bottom = newNode;
				}
				CardPanel cur = top.getStackNode();
				Index.setTop(index, top.getNextNode());
				Index.sevenStackPanels[index - 1].remove(cur);
				Index.sevenStackPanels[index - 1].cardNum--;
				cur.setLocation(location_X,
						location_Y + stackHeight - StaticData.getCardsize(3) - (i - 1) * StaticData.getMinilocation(3));
				componentX[i - 1] = cur.getX();
				componentY[i - 1] = cur.getY();
				Index.sevenStackPanels[index - 1].repaint();
				secondPanel.add(cur, new Integer(30 - i));
				top = top.getNextNode();
			}
			// 等于后
			i++;
			System.out.println("i=" + i);
			System.out.println("现在的牌堆高度为:" + stackHeight);
			cardNum = i;
			if (bottom == null) {
				bottom = new CardStackNode(top.getStackNode());
				bottom.setNextNode(null);
			} else {
				CardStackNode newNode = new CardStackNode(top.getStackNode());
				newNode.setNextNode(bottom);
				bottom = newNode;
			}
			CardPanel cur = top.getStackNode();
			Index.setTop(index, top.getNextNode());
			Index.sevenStackPanels[index - 1].remove(cur);
			Index.sevenStackPanels[index - 1].cardNum--;
			cur.setLocation(location_X,
					location_Y + stackHeight - StaticData.getCardsize(3) - (i - 1) * StaticData.getMinilocation(3));
			componentX[i - 1] = cur.getX();
			componentY[i - 1] = cur.getY();
			// Index.sevenStackPanels[index - 1].repaint();
			secondPanel.add(cur, new Integer(30 - i));
			Index.sevenStackPanels[index - 1].resetHeightAfterDelete(cardNum);
			Index.sevenStackPanels[index - 1].repaint();
			if (Index.sevenStackPanels[index - 1].getCardNum() == 0) {
				// 设置底纹边框
				Index.sevenStackPanels[index - 1].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
			} else {
				// 去除边框(视觉上其实没啥用)
				Index.sevenStackPanels[index - 1].setBorder(null);
			}
			this.startComponentName = FindComponent.findComponentByCenterPoint(cardPanel);
			System.out.println("卡牌初始牌堆:" + startComponentName);
			isLock = true;
		} else {
			isLock = false;
			this.startComponentName = null;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseReleased(arg0);
		if (arg0.getButton() != MouseEvent.BUTTON1) {
			return;
		}
		System.out.println("触发释放");
		this.cardPanel = (CardPanel) arg0.getComponent();
		if (isLock) {
			isLock = false;
			String resultComponent = FindComponent.findComponentByCenterPoint(cardPanel);
			System.out.println(resultComponent);
			if (resultComponent != null) {
				if (resultComponent.startsWith("gatherCardPanel") && cardNum == 1) {
					int length = resultComponent.length();
					int startLength = "gatherCardPanel".length();
					String indexStr = resultComponent.substring(startLength, length);
					int Gindex = Integer.parseInt(indexStr);
					// 检验该牌是否能放入此堆中
					if (SolitaireCheck.canPushToGatherStack(Index.gatherCardPanels[Gindex - 1], cardPanel)) {
						// 能放入
						System.out.println("能放入");
						secondPanel.remove(cardPanel);
						secondPanel.repaint();
						cardPanel.setLocation(0, 0);
						Index.gatherCardPanels[Gindex - 1].setTop(new CardStackNode(cardPanel));
						cardPanel.removeMouseListener(Index.sevenStackCardPanelAdapters[index - 1]);
						cardPanel.removeMouseMotionListener(Index.sevenStackCardPanelAdapters[index - 1]);

						// Index.setTop(index, Index.getTop(index).getNextNode());
						// cardPanel.repaint();
					} else {
						// 不能放入
						System.out.println("不能放入");
						// 放回原处
						// int i = 0;
						while (bottom != null) {
							// i++;
							CardPanel cur = bottom.getStackNode();
							bottom = bottom.getNextNode();
							// int height = Index.sevenStackPanels[index - 1].getHeight();
							secondPanel.remove(cur);
							cur.setLocation(0,
									(Index.sevenStackPanels[index - 1].getCardNum()) * StaticData.getMinilocation(3));
							Index.sevenStackPanels[index - 1].add(cur,
									new Integer(Index.sevenStackPanels[index - 1].getCardNum() + 1));
							CardStackNode newTop = new CardStackNode(cur);
							newTop.setNextNode(Index.getTop(index));
							Index.setTop(index, newTop);
							Index.sevenStackPanels[index - 1].cardNum++;
						}
						secondPanel.repaint();
						Index.sevenStackPanels[index - 1].resetHeightAfterAdd(cardNum);
					}
				} else if (resultComponent.startsWith("sevenStackPanel")
						&& !resultComponent.equals(startComponentName)) {
					// 放在其他堆上时
					int length = resultComponent.length();
					int startLength = "sevenStackPanel".length();
					String indexStr = resultComponent.substring(startLength, length);
					int Gindex = Integer.parseInt(indexStr); // Gindex为所放置堆的下标索引(从1开始)
					System.out.println(
							"Index.sevenStackPanels[Gindex - 1].cardNum = " + Index.sevenStackPanels[Gindex - 1].cardNum
									+ "\tcardPanel.getCardNumber() = " + cardPanel.getCardNumber());
					if (Index.sevenStackPanels[Gindex - 1].cardNum == 0 && cardPanel.getCardNumber().equals("K")) {
						// 目标卡堆无牌且该牌值为K时
						// 将选取的牌移入目标卡堆
						System.out.println("目标卡堆无牌且该牌值为K");
						/*
						 * while (bottom != null) { // i++; CardPanel cur = bottom.getStackNode();
						 * bottom = bottom.getNextNode(); // int height = Index.sevenStackPanels[index -
						 * 1].getHeight(); secondPanel.remove(cur); cur.setLocation(0,
						 * (Index.sevenStackPanels[Gindex - 1].getCardNum()) *
						 * StaticData.getMinilocation(3)); Index.sevenStackPanels[Gindex - 1].add(cur,
						 * new Integer(Index.sevenStackPanels[Gindex - 1].getCardNum() + 1));
						 * cur.removeMouseListener(Index.sevenStackCardPanelAdapters[index - 1]);
						 * cur.removeMouseMotionListener(Index.sevenStackCardPanelAdapters[index - 1]);
						 * cur.addMouseListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
						 * cur.addMouseMotionListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
						 * CardStackNode newTop = new CardStackNode(cur);
						 * newTop.setNextNode(Index.getTop(Gindex)); Index.setTop(Gindex, newTop);
						 * Index.sevenStackPanels[Gindex - 1].cardNum++; // Index.setTop(index,
						 * Index.getTop(index).getNextNode()); } secondPanel.repaint();
						 * Index.sevenStackPanels[Gindex - 1].resetHeightAfterAdd(cardNum);
						 */
						int j = 0;
						while (bottom != null) {
							// i++;
							j++;
							CardPanel cur = bottom.getStackNode();
							bottom = bottom.getNextNode();
							// int height = Index.sevenStackPanels[index - 1].getHeight();
							secondPanel.remove(cur);
							cur.setLocation(0,
									(Index.sevenStackPanels[Gindex - 1].getCardNum()) * StaticData.getMinilocation(3));
							Index.sevenStackPanels[Gindex - 1].add(cur,
									new Integer(Index.sevenStackPanels[Gindex - 1].getCardNum() + 1));
							cur.removeMouseListener(Index.sevenStackCardPanelAdapters[index - 1]);
							cur.removeMouseMotionListener(Index.sevenStackCardPanelAdapters[index - 1]);
							cur.addMouseListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
							cur.addMouseMotionListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
							CardStackNode newTop = new CardStackNode(cur);
							System.out.println("插入的卡牌值为:" + cur.getCardValue());
							newTop.setNextNode(Index.getTop(Gindex));
							Index.setTop(Gindex, newTop);
							Index.sevenStackPanels[Gindex - 1].cardNum++;
							// Index.setTop(index, Index.getTop(index).getNextNode());
						}
						System.out.println("插入了" + j + "张牌");
						System.out.println("验证---添加后的卡堆的顶部牌值为:" + Index.getTop(Gindex).getStackNode().getCardValue());
						secondPanel.repaint();
						Index.sevenStackPanels[Gindex - 1].resetHeightAfterAdd(cardNum);

					} else if (Index.sevenStackPanels[Gindex - 1].cardNum > 0
							&& SolitaireCheck.canPushToSevenStack(Gindex, cardPanel)) {
						// 目标卡堆有牌且该牌符合插入条件时
						// 将选取的牌移入目标卡堆
						System.out.println("目标卡堆有牌且该牌符合插入条件");
						int j = 0;
						while (bottom != null) {
							// i++;
							j++;
							CardPanel cur = bottom.getStackNode();
							bottom = bottom.getNextNode();
							// int height = Index.sevenStackPanels[index - 1].getHeight();
							secondPanel.remove(cur);
							cur.setLocation(0,
									(Index.sevenStackPanels[Gindex - 1].getCardNum()) * StaticData.getMinilocation(3));
							Index.sevenStackPanels[Gindex - 1].add(cur,
									new Integer(Index.sevenStackPanels[Gindex - 1].getCardNum() + 1));
							cur.removeMouseListener(Index.sevenStackCardPanelAdapters[index - 1]);
							cur.removeMouseMotionListener(Index.sevenStackCardPanelAdapters[index - 1]);
							cur.addMouseListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
							cur.addMouseMotionListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
							CardStackNode newTop = new CardStackNode(cur);
							System.out.println("插入的卡牌值为:" + cur.getCardValue());
							newTop.setNextNode(Index.getTop(Gindex));
							Index.setTop(Gindex, newTop);
							Index.sevenStackPanels[Gindex - 1].cardNum++;
							// Index.setTop(index, Index.getTop(index).getNextNode());
						}
						System.out.println("插入了" + j + "张牌");
						System.out.println("验证---添加后的卡堆的顶部牌值为:" + Index.getTop(Gindex).getStackNode().getCardValue());
						secondPanel.repaint();
						Index.sevenStackPanels[Gindex - 1].resetHeightAfterAdd(cardNum);
					} else {
						// 放回原处
						while (bottom != null) {
							// i++;
							CardPanel cur = bottom.getStackNode();
							bottom = bottom.getNextNode();
							// int height = Index.sevenStackPanels[index - 1].getHeight();
							secondPanel.remove(cur);
							cur.setLocation(0,
									(Index.sevenStackPanels[index - 1].getCardNum()) * StaticData.getMinilocation(3));
							Index.sevenStackPanels[index - 1].add(cur,
									new Integer(Index.sevenStackPanels[index - 1].getCardNum() + 1));
							CardStackNode newTop = new CardStackNode(cur);
							newTop.setNextNode(Index.getTop(index));
							Index.setTop(index, newTop);
							Index.sevenStackPanels[index - 1].cardNum++;
						}
						secondPanel.repaint();
						Index.sevenStackPanels[index - 1].resetHeightAfterAdd(cardNum);

					}
					// 暂时用放回原处
					// int i = 0;
					/*
					 * while (bottom != null) { // i++; CardPanel cur = bottom.getStackNode();
					 * bottom = bottom.getNextNode(); // int height = Index.sevenStackPanels[index -
					 * 1].getHeight(); secondPanel.remove(cur); cur.setLocation(0,
					 * (Index.sevenStackPanels[index - 1].getCardNum()) *
					 * StaticData.getMinilocation(3)); Index.sevenStackPanels[index - 1].add(cur,
					 * new Integer(Index.sevenStackPanels[index - 1].getCardNum() + 1));
					 * CardStackNode newTop = new CardStackNode(cur);
					 * newTop.setNextNode(Index.getTop(index)); Index.setTop(index, newTop);
					 * Index.sevenStackPanels[index - 1].cardNum++; } secondPanel.repaint();
					 * Index.sevenStackPanels[index - 1].resetHeightAfterAdd(cardNum);
					 */
				} else {
					// 放回原处
					while (bottom != null) {
						// i++;
						CardPanel cur = bottom.getStackNode();
						bottom = bottom.getNextNode();
						// int height = Index.sevenStackPanels[index - 1].getHeight();
						secondPanel.remove(cur);
						cur.setLocation(0,
								(Index.sevenStackPanels[index - 1].getCardNum()) * StaticData.getMinilocation(3));
						Index.sevenStackPanels[index - 1].add(cur,
								new Integer(Index.sevenStackPanels[index - 1].getCardNum() + 1));
						CardStackNode newTop = new CardStackNode(cur);
						newTop.setNextNode(Index.getTop(index));
						Index.setTop(index, newTop);
						Index.sevenStackPanels[index - 1].cardNum++;
					}
					secondPanel.repaint();
					Index.sevenStackPanels[index - 1].resetHeightAfterAdd(cardNum);
				}
			} else {
				// 放回原处
				// int i = 0;
				while (bottom != null) {
					// i++;
					CardPanel cur = bottom.getStackNode();
					bottom = bottom.getNextNode();
					// int height = Index.sevenStackPanels[index - 1].getHeight();
					secondPanel.remove(cur);
					cur.setLocation(0,
							(Index.sevenStackPanels[index - 1].getCardNum()) * StaticData.getMinilocation(3));
					Index.sevenStackPanels[index - 1].add(cur,
							new Integer(Index.sevenStackPanels[index - 1].getCardNum() + 1));
					CardStackNode newTop = new CardStackNode(cur);
					newTop.setNextNode(Index.getTop(index));
					Index.setTop(index, newTop);
					Index.sevenStackPanels[index - 1].cardNum++;
				}
				secondPanel.repaint();
				Index.sevenStackPanels[index - 1].resetHeightAfterAdd(cardNum);
			}
			Index.refresh();
		} else {
			isLock = false;
			Index.refresh();
		}
		this.bottom = null;
	}

}
