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
import uiDao.GamePage;

public class SevenStackCardPanelAdapter extends MouseAdapter {
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

	private GamePage jf;

	public SevenStackCardPanelAdapter(int index, JLayeredPane secondPanel, GamePage jf) {
		super();
		this.index = index;
		this.secondPanel = secondPanel;
		this.componentX = new int[30];
		this.componentY = new int[30];
		this.jf = jf;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseClicked(arg0);
		if (arg0.getButton() == MouseEvent.BUTTON2 && StaticData.isCardAutoCheck()) {
			System.out.println("触发中键!");
			SolitaireCheck.autoCheck(jf);
		}
		if (arg0.getButton() == MouseEvent.BUTTON3) {
			if (jf.isHasClicked() && !isLock) {
				System.out.println("触发第二次点击(Single)");
				CardStackNode recBottom = jf.getTranBottom();
				CardPanel cardPanel = jf.getCardPanel();
				String sendName = jf.getClickComponentName();
				if (sendName.startsWith("sevenStackPanel")) {
					int length = sendName.length();
					int startLength = "sevenStackPanel".length();
					String indexStr = sendName.substring(startLength, length);
					int Gindex = Integer.parseInt(indexStr); // 获得传入的是哪个下标的牌堆
					if ((jf.getSevenStackPanels()[index - 1].cardNum == 0 && cardPanel.getCardNumber().equals("K"))
							|| (jf.getSevenStackPanels()[index - 1].cardNum > 0
									&& SolitaireCheck.canPushToSevenStack(index, cardPanel, jf))) {
						// 可以插入
						System.out.println("可以插入");
						CardStackNode top = jf.getTop(Gindex); // 获得对应的卡牌堆
						int clickCardNum = 0;
						while (recBottom != null) {
							clickCardNum++;
							CardPanel cur = recBottom.getStackNode();
							recBottom = recBottom.getNextNode();
							cur.setLocation(0,
									(jf.getSevenStackPanels()[index - 1].getCardNum()) * StaticData.getMinilocation(3));
							jf.getSevenStackPanels()[index - 1].add(cur,
									new Integer(jf.getSevenStackPanels()[index - 1].getCardNum() + 1));
							cur.removeMouseListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
							cur.removeMouseMotionListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
							cur.addMouseListener(jf.getSevenStackCardPanelAdapters()[index - 1]);
							cur.addMouseMotionListener(jf.getSevenStackCardPanelAdapters()[index - 1]);
							CardStackNode newTop = new CardStackNode(cur);
							System.out.println("插入的卡牌值为:" + cur.getCardValue());
							newTop.setNextNode(jf.getTop(index));
							jf.setTop(index, newTop);
							jf.getSevenStackPanels()[index - 1].cardNum++;

							jf.getSevenStackPanels()[Gindex - 1].remove(top.getStackNode());
							top = top.getNextNode();
							jf.getSevenStackPanels()[Gindex - 1].cardNum--;
						}
						System.out.println("插入了" + clickCardNum + "张牌");
						System.out.println("验证---添加后的卡堆的顶部牌值为:" + jf.getTop(index).getStackNode().getCardValue());
						jf.getSevenStackPanels()[index - 1].resetHeightAfterAdd(clickCardNum);
						jf.getSevenStackPanels()[index - 1].repaint();

						jf.setTop(Gindex, top);
						if (top != null && top.getStackNode() != null && StaticData.isCardAutoChange()) {
							top.getStackNode().changeToFront();
							// System.out.println("asdasd");
						}
						jf.getSevenStackPanels()[Gindex - 1].resetHeightAfterDelete(clickCardNum);
						jf.getSevenStackPanels()[Gindex - 1].repaint();
						if (jf.getSevenStackPanels()[Gindex - 1].getCardNum() == 0) {
							// 设置底纹边框
							jf.getSevenStackPanels()[Gindex - 1]
									.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
						} else {
							// 去除边框(视觉上其实没啥用)
							jf.getSevenStackPanels()[Gindex - 1].setBorder(null);
						}
					}
				} else if (sendName.startsWith("gatherCardPanel")) {
					int length = sendName.length();
					int startLength = "gatherCardPanel".length();
					String indexStr = sendName.substring(startLength, length);
					int Gindex = Integer.parseInt(indexStr); // 获得传入的是哪个下标的牌堆
					if ((jf.getSevenStackPanels()[index - 1].cardNum == 0 && cardPanel.getCardNumber().equals("K"))
							|| (jf.getSevenStackPanels()[index - 1].cardNum > 0
									&& SolitaireCheck.canPushToSevenStack(index, cardPanel, jf))) {
						// 可以传入
						jf.getGatherCardPanels()[Gindex - 1].getTop(true);
						CardPanel cur = jf.getCardPanel();
						cur.setLocation(0,
								(jf.getSevenStackPanels()[index - 1].getCardNum()) * StaticData.getMinilocation(3));
						jf.getSevenStackPanels()[index - 1].add(cur,
								new Integer(jf.getSevenStackPanels()[index - 1].getCardNum() + 1));
						cur.addMouseListener(jf.getSevenStackCardPanelAdapters()[index - 1]);
						cur.addMouseMotionListener(jf.getSevenStackCardPanelAdapters()[index - 1]);
						CardStackNode newTop = new CardStackNode(cur);
						System.out.println("插入的卡牌值为:" + cur.getCardValue());
						newTop.setNextNode(jf.getTop(index));
						jf.setTop(index, newTop);
						jf.getSevenStackPanels()[index - 1].cardNum++;
						System.out.println("验证---添加后的卡堆的顶部牌值为:" + jf.getTop(index).getStackNode().getCardValue());
						jf.getSevenStackPanels()[index - 1].resetHeightAfterAdd(1);
						jf.getGatherCardPanels()[Gindex - 1].repaint();
						jf.getSevenStackPanels()[index - 1].repaint();
					}
				} else if (sendName.equals("dealedStackPanel")) {
					if ((jf.getSevenStackPanels()[index - 1].cardNum == 0 && cardPanel.getCardNumber().equals("K"))
							|| (jf.getSevenStackPanels()[index - 1].cardNum > 0
									&& SolitaireCheck.canPushToSevenStack(index, cardPanel, jf))) {
						jf.getSevenStackPanels()[index - 1].add(cardPanel,
								new Integer(jf.getSevenStackPanels()[index - 1].getCardNum() + 1));
						cardPanel.setLocation(0,
								(jf.getSevenStackPanels()[index - 1].getCardNum()) * StaticData.getMinilocation(3));
						cardPanel.addMouseListener(jf.getSevenStackCardPanelAdapters()[index - 1]);
						cardPanel.addMouseMotionListener(jf.getSevenStackCardPanelAdapters()[index - 1]);
						CardStackNode newTop = new CardStackNode(cardPanel);
						System.out.println("放入的卡牌值为:" + cardPanel.getCardValue());
						newTop.setNextNode(jf.getTop(index));
						jf.setTop(index, newTop);
						jf.getSevenStackPanels()[index - 1].cardNum++;
						System.out.println("验证---添加后的卡堆的顶部牌值为:" + jf.getTop(index).getStackNode().getCardValue());
						jf.getSevenStackPanels()[index - 1].resetHeightAfterAdd(1);
						jf.getDealedStackPanel().getTop();
						jf.getDealedStackPanel().repaint();
						jf.getSevenStackPanels()[index - 1].repaint();
					}
				}
				jf.refresh();
				jf.getGameFoot().reset();
			} else if (!isLock) {
				// 第一次点击
				this.cardPanel = (CardPanel) arg0.getComponent();
				if (cardPanel.isPositive()) {
					// 卡牌在正面
					jf.setHasClicked(true);
					CardStackNode top = jf.getTop(index); // 获得对应的卡牌堆
					System.out.println("第" + index + "个牌堆的牌数为" + jf.getSevenStackPanels()[index - 1].cardNum);
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
						jf.setSingle(true);
						jf.setTranBottom(bottom);
						jf.setCardPanel(bottom.getStackNode());
						jf.setClickComponentName(jf.getSevenStackPanels()[index - 1].getName());
					} else {
						jf.setSingle(false);
						jf.setTranBottom(bottom);
						jf.setCardPanel(bottom.getStackNode());
						jf.setClickComponentName(jf.getSevenStackPanels()[index - 1].getName());
					}
					bottom = null; // 返回初始状态
					jf.getGameFoot().setTextToClicked(this.cardPanel);
				}
			}
		} else if (arg0.getButton() == MouseEvent.BUTTON1) {
			System.out.println("触发点击");
			this.bottom = null;
			this.cardPanel = (CardPanel) arg0.getComponent();
			System.out.println(cardPanel.getCardValue());
			System.out.println(jf.getTop(index).getStackNode().getCardValue());
			if (jf.getTop(index) != null && jf.getTop(index).getStackNode().getCardValue() == cardPanel.getCardValue()
					&& !cardPanel.isPositive()) {
				// 底部牌在背面时
				System.out.println("底部牌在背面,将其翻到正面");
				cardPanel.changeToFront();
			} else if (jf.getTop(index).getStackNode().getCardValue() == cardPanel.getCardValue()
					&& cardPanel.isPositive()) {
				System.out.println("底部牌在正面");
			} else if (jf.getTop(index).getStackNode().getCardValue() != cardPanel.getCardValue()) {
				System.out.println("点击的不是底部牌");
			}
			jf.refresh();
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
			int i = 0;
			CardStackNode curNode = bottom;
			while (curNode != null) {
				i++;
				CardPanel cur = curNode.getStackNode();
				int component_X = componentX[cardNum - i];
				int component_Y = componentY[cardNum - i];
				cur.setLocation(component_X + mouseEndX - mouseStartX, component_Y + mouseEndY - mouseStartY);
				curNode = curNode.getNextNode();
			}
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
			int location_X = jf.getSevenStackPanels()[index - 1].getX();
			int location_Y = jf.getSevenStackPanels()[index - 1].getY();
			int stackHeight = jf.getSevenStackPanels()[index - 1].getHeight();
			int stackWidth = jf.getSevenStackPanels()[index - 1].getWidth();
			System.out.println(location_X + "\t" + location_Y + "\t" + stackHeight + "\t" + stackWidth);
			CardStackNode top = jf.getTop(index); // 获得对应的卡牌堆
			System.out.println("第" + index + "个牌堆的牌数为" + jf.getSevenStackPanels()[index - 1].cardNum);
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
				jf.setTop(index, top.getNextNode());
				jf.getSevenStackPanels()[index - 1].remove(cur);
				jf.getSevenStackPanels()[index - 1].cardNum--;
				cur.setLocation(location_X,
						location_Y + stackHeight - StaticData.getCardsize(3) - (i - 1) * StaticData.getMinilocation(3));
				componentX[i - 1] = cur.getX();
				componentY[i - 1] = cur.getY();
				jf.getSevenStackPanels()[index - 1].repaint();
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
			jf.setTop(index, top.getNextNode());
			jf.getSevenStackPanels()[index - 1].remove(cur);
			jf.getSevenStackPanels()[index - 1].cardNum--;
			cur.setLocation(location_X,
					location_Y + stackHeight - StaticData.getCardsize(3) - (i - 1) * StaticData.getMinilocation(3));
			componentX[i - 1] = cur.getX();
			componentY[i - 1] = cur.getY();
			secondPanel.add(cur, new Integer(30 - i));
			jf.getSevenStackPanels()[index - 1].resetHeightAfterDelete(cardNum);
			jf.getSevenStackPanels()[index - 1].repaint();
			if (jf.getSevenStackPanels()[index - 1].getCardNum() == 0) {
				// 设置底纹边框
				jf.getSevenStackPanels()[index - 1].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
			} else {
				// 去除边框(视觉上其实没啥用)
				jf.getSevenStackPanels()[index - 1].setBorder(null);
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
					if (SolitaireCheck.canPushToGatherStack(jf.getGatherCardPanels()[Gindex - 1], cardPanel)) {
						// 能放入
						System.out.println("能放入");
						secondPanel.remove(cardPanel);
						secondPanel.repaint();
						cardPanel.setLocation(0, 0);
						jf.getGatherCardPanels()[Gindex - 1].setTop(new CardStackNode(cardPanel));
						cardPanel.removeMouseListener(jf.getSevenStackCardPanelAdapters()[index - 1]);
						cardPanel.removeMouseMotionListener(jf.getSevenStackCardPanelAdapters()[index - 1]);

						if (jf.getTop(index) != null && jf.getTop(index).getStackNode() != null
								&& StaticData.isCardAutoChange()) {
							jf.getTop(index).getStackNode().changeToFront();
							// System.out.println("asdasd");
						}
					} else {
						// 不能放入
						System.out.println("不能放入");
						// 放回原处
						while (bottom != null) {
							// i++;
							CardPanel cur = bottom.getStackNode();
							bottom = bottom.getNextNode();
							secondPanel.remove(cur);
							cur.setLocation(0,
									(jf.getSevenStackPanels()[index - 1].getCardNum()) * StaticData.getMinilocation(3));
							jf.getSevenStackPanels()[index - 1].add(cur,
									new Integer(jf.getSevenStackPanels()[index - 1].getCardNum() + 1));
							CardStackNode newTop = new CardStackNode(cur);
							newTop.setNextNode(jf.getTop(index));
							jf.setTop(index, newTop);
							jf.getSevenStackPanels()[index - 1].cardNum++;
						}
						secondPanel.repaint();
						jf.getSevenStackPanels()[index - 1].resetHeightAfterAdd(cardNum);
					}
				} else if (resultComponent.startsWith("sevenStackPanel")
						&& !resultComponent.equals(startComponentName)) {
					// 放在其他堆上时
					int length = resultComponent.length();
					int startLength = "sevenStackPanel".length();
					String indexStr = resultComponent.substring(startLength, length);
					int Gindex = Integer.parseInt(indexStr); // Gindex为所放置堆的下标索引(从1开始)
					System.out.println("Index.getSevenStackPanels()[Gindex - 1].cardNum = "
							+ jf.getSevenStackPanels()[Gindex - 1].cardNum + "\tcardPanel.getCardNumber() = "
							+ cardPanel.getCardNumber());
					if (jf.getSevenStackPanels()[Gindex - 1].cardNum == 0 && cardPanel.getCardNumber().equals("K")) {
						// 目标卡堆无牌且该牌值为K时
						// 将选取的牌移入目标卡堆
						System.out.println("目标卡堆无牌且该牌值为K");
						int j = 0;
						while (bottom != null) {
							// i++;
							j++;
							CardPanel cur = bottom.getStackNode();
							bottom = bottom.getNextNode();
							// int height = Index.getSevenStackPanels()[index - 1].getHeight();
							secondPanel.remove(cur);
							cur.setLocation(0, (jf.getSevenStackPanels()[Gindex - 1].getCardNum())
									* StaticData.getMinilocation(3));
							jf.getSevenStackPanels()[Gindex - 1].add(cur,
									new Integer(jf.getSevenStackPanels()[Gindex - 1].getCardNum() + 1));
							cur.removeMouseListener(jf.getSevenStackCardPanelAdapters()[index - 1]);
							cur.removeMouseMotionListener(jf.getSevenStackCardPanelAdapters()[index - 1]);
							cur.addMouseListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
							cur.addMouseMotionListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
							CardStackNode newTop = new CardStackNode(cur);
							System.out.println("插入的卡牌值为:" + cur.getCardValue());
							newTop.setNextNode(jf.getTop(Gindex));
							jf.setTop(Gindex, newTop);
							jf.getSevenStackPanels()[Gindex - 1].cardNum++;
						}
						System.out.println("插入了" + j + "张牌");
						System.out.println("验证---添加后的卡堆的顶部牌值为:" + jf.getTop(Gindex).getStackNode().getCardValue());
						secondPanel.repaint();
						jf.getSevenStackPanels()[Gindex - 1].resetHeightAfterAdd(cardNum);

						if (jf.getTop(index) != null && jf.getTop(index).getStackNode() != null
								&& StaticData.isCardAutoChange()) {
							jf.getTop(index).getStackNode().changeToFront();
							// System.out.println("asdasd");
						}

					} else if (jf.getSevenStackPanels()[Gindex - 1].cardNum > 0
							&& SolitaireCheck.canPushToSevenStack(Gindex, cardPanel, jf)) {
						// 目标卡堆有牌且该牌符合插入条件时
						// 将选取的牌移入目标卡堆
						System.out.println("目标卡堆有牌且该牌符合插入条件");
						int j = 0;
						while (bottom != null) {
							j++;
							CardPanel cur = bottom.getStackNode();
							bottom = bottom.getNextNode();
							secondPanel.remove(cur);
							cur.setLocation(0, (jf.getSevenStackPanels()[Gindex - 1].getCardNum())
									* StaticData.getMinilocation(3));
							jf.getSevenStackPanels()[Gindex - 1].add(cur,
									new Integer(jf.getSevenStackPanels()[Gindex - 1].getCardNum() + 1));
							cur.removeMouseListener(jf.getSevenStackCardPanelAdapters()[index - 1]);
							cur.removeMouseMotionListener(jf.getSevenStackCardPanelAdapters()[index - 1]);
							cur.addMouseListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
							cur.addMouseMotionListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
							CardStackNode newTop = new CardStackNode(cur);
							System.out.println("插入的卡牌值为:" + cur.getCardValue());
							newTop.setNextNode(jf.getTop(Gindex));
							jf.setTop(Gindex, newTop);
							jf.getSevenStackPanels()[Gindex - 1].cardNum++;
						}
						System.out.println("插入了" + j + "张牌");
						System.out.println("验证---添加后的卡堆的顶部牌值为:" + jf.getTop(Gindex).getStackNode().getCardValue());
						secondPanel.repaint();
						jf.getSevenStackPanels()[Gindex - 1].resetHeightAfterAdd(cardNum);

						if (jf.getTop(index) != null && jf.getTop(index).getStackNode() != null
								&& StaticData.isCardAutoChange()) {
							jf.getTop(index).getStackNode().changeToFront();
							// System.out.println("asdasd");
						}
					} else {
						// 放回原处
						while (bottom != null) {
							// i++;
							CardPanel cur = bottom.getStackNode();
							bottom = bottom.getNextNode();
							secondPanel.remove(cur);
							cur.setLocation(0,
									(jf.getSevenStackPanels()[index - 1].getCardNum()) * StaticData.getMinilocation(3));
							jf.getSevenStackPanels()[index - 1].add(cur,
									new Integer(jf.getSevenStackPanels()[index - 1].getCardNum() + 1));
							CardStackNode newTop = new CardStackNode(cur);
							newTop.setNextNode(jf.getTop(index));
							jf.setTop(index, newTop);
							jf.getSevenStackPanels()[index - 1].cardNum++;
						}
						secondPanel.repaint();
						jf.getSevenStackPanels()[index - 1].resetHeightAfterAdd(cardNum);

					}
				} else {
					// 放回原处
					while (bottom != null) {
						CardPanel cur = bottom.getStackNode();
						bottom = bottom.getNextNode();
						secondPanel.remove(cur);
						cur.setLocation(0,
								(jf.getSevenStackPanels()[index - 1].getCardNum()) * StaticData.getMinilocation(3));
						jf.getSevenStackPanels()[index - 1].add(cur,
								new Integer(jf.getSevenStackPanels()[index - 1].getCardNum() + 1));
						CardStackNode newTop = new CardStackNode(cur);
						newTop.setNextNode(jf.getTop(index));
						jf.setTop(index, newTop);
						jf.getSevenStackPanels()[index - 1].cardNum++;
					}
					secondPanel.repaint();
					jf.getSevenStackPanels()[index - 1].resetHeightAfterAdd(cardNum);
				}
			} else {
				// 放回原处
				while (bottom != null) {
					CardPanel cur = bottom.getStackNode();
					bottom = bottom.getNextNode();
					secondPanel.remove(cur);
					cur.setLocation(0,
							(jf.getSevenStackPanels()[index - 1].getCardNum()) * StaticData.getMinilocation(3));
					jf.getSevenStackPanels()[index - 1].add(cur,
							new Integer(jf.getSevenStackPanels()[index - 1].getCardNum() + 1));
					CardStackNode newTop = new CardStackNode(cur);
					newTop.setNextNode(jf.getTop(index));
					jf.setTop(index, newTop);
					jf.getSevenStackPanels()[index - 1].cardNum++;
				}
				secondPanel.repaint();
				jf.getSevenStackPanels()[index - 1].resetHeightAfterAdd(cardNum);
			}
			jf.refresh();
		} else {
			isLock = false;
			jf.refresh();
		}
		this.bottom = null;
	}

}
