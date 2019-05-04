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
import uiDao.GatherCardPanel;
import uiPaint.Index;

public class GatherStackAdapter extends MouseAdapter {
	private int stackIndex = 0; // 存储该堆的索引(第几个堆)
	private int mouseStartX, mouseStartY; // 鼠标按下时坐标
	private int mouseEndX, mouseEndY; // 鼠标移动时坐标
	private int componentX, componentY; // 控件坐标
	private boolean isLockedOnCard = false; // 鼠标是否是拖动的卡牌
	private CardPanel cardPanel;

	private GatherCardPanel gatherCardPanel;
	private JLayeredPane secondPanel;

	public GatherStackAdapter(JLayeredPane secondPanel) {
		super();
		// TODO Auto-generated constructor stub
		this.secondPanel = secondPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		if (e.getButton() != MouseEvent.BUTTON3) {
			return;
		}
		if (Index.isHasClicked()) {
			String componentName = Index.getClickComponentName();
			if (componentName != null) {
				this.gatherCardPanel = (GatherCardPanel) e.getComponent();
				if (this.stackIndex == 0) {
					String str = gatherCardPanel.getName();
					int length = str.length();
					int startLength = "gatherCardPanel".length();
					String indexStr = str.substring(startLength, length);
					int Gindex = Integer.parseInt(indexStr);
					this.stackIndex = Gindex; // 获取索引
				}
				this.cardPanel = Index.cardPanel;
				switch (componentName) {
				case "dealedStackPanel":
					// 将牌从已发牌堆移入此置牌堆
					// 检验该牌是否能放入此堆中
					if (SolitaireCheck.canPushToGatherStack(Index.gatherCardPanels[stackIndex - 1], cardPanel)) {
						// 能放入
						System.out.println("能放入");
						Index.dealedStackPanel.getTop(); // 取出顶部的牌
						Index.gatherCardPanels[stackIndex - 1].setTop(new CardStackNode(cardPanel));
						Index.dealedStackPanel.repaint();
						Index.gatherCardPanels[stackIndex - 1].repaint();
						Index.setHasClicked(false);
						// cardPanel.repaint();
					} else {
						// 不能放入
						System.out.println("不能放入");
						Index.setHasClicked(false);
						// Index.dealedStackPanel.setTop(new CardStackNode(cardPanel));
					}
					Index.setClickComponentName(null);
					break;
				default:
					if (componentName.startsWith("gatherCardPanel")) {
						// 从别的置牌堆移入此置牌堆
						int length = componentName.length();
						int startLength = "gatherCardPanel".length();
						String indexStr = componentName.substring(startLength, length);
						int Gindex = Integer.parseInt(indexStr); // 获取索引
						// 检验该牌是否能放入此堆中
						if (SolitaireCheck.canPushToGatherStack(Index.gatherCardPanels[stackIndex - 1], cardPanel)) {
							// 能放入
							System.out.println("能放入");
							Index.gatherCardPanels[Gindex - 1].getTop(true);
							Index.gatherCardPanels[stackIndex - 1].setTop(new CardStackNode(cardPanel));
							Index.gatherCardPanels[Gindex - 1].repaint();
							Index.gatherCardPanels[stackIndex - 1].repaint();
							Index.setHasClicked(false);
							// cardPanel.repaint();
						} else {
							// (不可能)不能放入
							System.out.println("不能放入");
							Index.setHasClicked(false);
							// Index.gatherCardPanels[stackIndex - 1].setTop(new CardStackNode(cardPanel));
						}
					} else if (componentName.startsWith("sevenStackPanel")) {
						if (Index.isSingle()) {
							int length = componentName.length();
							int startLength = "sevenStackPanel".length();
							String indexStr = componentName.substring(startLength, length);
							int Gindex = Integer.parseInt(indexStr); // 获取索引
							// 检验该牌是否能放入此堆中
							if (SolitaireCheck.canPushToGatherStack(Index.gatherCardPanels[stackIndex - 1],
									cardPanel)) {
								// 能放入
								System.out.println("能放入");
								// 去除原堆
								CardStackNode top = Index.getTop(Gindex); // 获得对应的卡牌堆
								CardPanel cur = top.getStackNode();
								Index.setTop(Gindex, top.getNextNode());
								Index.sevenStackPanels[Gindex - 1].remove(cur);
								Index.sevenStackPanels[Gindex - 1].cardNum--;
								// 加入该堆顶部
								cardPanel.setLocation(0, 0);
								cardPanel.removeMouseListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
								cardPanel.removeMouseMotionListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
								Index.gatherCardPanels[stackIndex - 1].setTop(new CardStackNode(cardPanel));
								Index.sevenStackPanels[Gindex - 1].resetHeightAfterDelete(1);
								Index.sevenStackPanels[Gindex - 1].repaint();
								Index.gatherCardPanels[stackIndex - 1].repaint();
								if (Index.sevenStackPanels[Gindex - 1].getCardNum() == 0) {
									// 设置底纹边框
									Index.sevenStackPanels[Gindex - 1]
											.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
								} else {
									// 去除边框(视觉上其实没啥用)
									Index.sevenStackPanels[Gindex - 1].setBorder(null);
								}
							} else {
								// 不能放入
								System.out.println("不能放入");
							}
						}
						Index.setHasClicked(false);
						Index.setSingle(false);
					}
					Index.setClickComponentName(null);
					break;
				}
			}
			Index.refresh();
		} else {
			// 第一次的点击
			this.gatherCardPanel = (GatherCardPanel) e.getComponent();
			System.out.println(gatherCardPanel.getCardNum());
			if (gatherCardPanel.getCardNum() > 0) {
				Index.setHasClicked(true);
				cardPanel = gatherCardPanel.getTop(false).getStackNode();
				// cardPanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3, true));
				// System.out.println("上色成功");
				Index.cardPanel = cardPanel;
				Index.setSingle(true);
				Index.setTranBottom(null);
				Index.setClickComponentName(gatherCardPanel.getName());
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mousePressed(e);
		if (e.getButton() != MouseEvent.BUTTON1) {
			return;
		}
		this.gatherCardPanel = (GatherCardPanel) e.getComponent();
		if (this.stackIndex == 0) {
			String str = gatherCardPanel.getName();
			int length = str.length();
			int startLength = "gatherCardPanel".length();
			String indexStr = str.substring(startLength, length);
			int Gindex = Integer.parseInt(indexStr);
			this.stackIndex = Gindex; // 获取索引
		}
		System.out.println("获取鼠标坐标");
		mouseStartX = e.getXOnScreen();
		mouseStartY = e.getYOnScreen();
		System.out.println(mouseStartX + "\t" + mouseStartX);
		System.out.println(gatherCardPanel.getCardNum());
		if (gatherCardPanel.getCardNum() > 0) {
			cardPanel = gatherCardPanel.getTop(true).getStackNode();
			Index.cardPanel = cardPanel;
			cardPanel.setLocation(StaticData.getGathercardlocation(0)
					+ (StaticData.getGathercardlocation(2) + StaticData.getGathercardlocation(4)) * (stackIndex - 1),
					StaticData.getGathercardlocation(1));
			secondPanel.add(cardPanel);
			componentX = cardPanel.getX();
			componentY = cardPanel.getY();
			isLockedOnCard = true;
			System.out.println(componentX + "\t\t" + componentY);
		} else {
			isLockedOnCard = false;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseReleased(e);
		if (e.getButton() != MouseEvent.BUTTON1) {
			return;
		}
		if (isLockedOnCard) {
			isLockedOnCard = false;
			String resultComponent = FindComponent.findComponentByCenterPoint(cardPanel);
			System.out.println("\t" + resultComponent);

			secondPanel.remove(cardPanel);
			secondPanel.repaint();
			cardPanel.setLocation(0, 0);

			if (resultComponent != null) {
				if (resultComponent.startsWith("gatherCardPanel")) {
					int length = resultComponent.length();
					int startLength = "gatherCardPanel".length();
					String indexStr = resultComponent.substring(startLength, length);
					int index = Integer.parseInt(indexStr);
					// 检验该牌是否能放入此堆中
					if (SolitaireCheck.canPushToGatherStack(Index.gatherCardPanels[index - 1], cardPanel)) {
						// 能放入
						System.out.println("能放入");
						Index.gatherCardPanels[index - 1].setTop(new CardStackNode(cardPanel));
						// cardPanel.repaint();
					} else {
						// (不可能)不能放入
						System.out.println("不能放入");
						Index.gatherCardPanels[stackIndex - 1].setTop(new CardStackNode(cardPanel));
					}
				} else if (resultComponent.startsWith("sevenStackPanel")) {
					// 放在下面的牌堆上时
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
						System.out.println("目标卡堆无牌且发牌堆中该牌值为K");
						Index.sevenStackPanels[Gindex - 1].add(cardPanel,
								new Integer(Index.sevenStackPanels[Gindex - 1].getCardNum() + 1));
						cardPanel.setLocation(0,
								(Index.sevenStackPanels[Gindex - 1].getCardNum()) * StaticData.getMinilocation(3));
						cardPanel.addMouseListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
						cardPanel.addMouseMotionListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
						CardStackNode newTop = new CardStackNode(cardPanel);
						System.out.println("放入的卡牌值为:" + cardPanel.getCardValue());
						newTop.setNextNode(Index.getTop(Gindex));
						Index.setTop(Gindex, newTop);
						Index.sevenStackPanels[Gindex - 1].cardNum++;
						System.out.println("验证---添加后的卡堆的顶部牌值为:" + Index.getTop(Gindex).getStackNode().getCardValue());
						Index.sevenStackPanels[Gindex - 1].resetHeightAfterAdd(1);
					} else if (Index.sevenStackPanels[Gindex - 1].cardNum > 0
							&& SolitaireCheck.canPushToSevenStack(Gindex, cardPanel)) {
						// 目标卡堆有牌且该牌符合插入条件时
						// 将选取的牌移入目标卡堆
						System.out.println("目标卡堆无牌且发牌堆中该牌值为K");
						Index.sevenStackPanels[Gindex - 1].add(cardPanel,
								new Integer(Index.sevenStackPanels[Gindex - 1].getCardNum() + 1));
						cardPanel.setLocation(0,
								(Index.sevenStackPanels[Gindex - 1].getCardNum()) * StaticData.getMinilocation(3));
						cardPanel.addMouseListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
						cardPanel.addMouseMotionListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
						CardStackNode newTop = new CardStackNode(cardPanel);
						System.out.println("放入的卡牌值为:" + cardPanel.getCardValue());
						newTop.setNextNode(Index.getTop(Gindex));
						Index.setTop(Gindex, newTop);
						Index.sevenStackPanels[Gindex - 1].cardNum++;
						System.out.println("验证---添加后的卡堆的顶部牌值为:" + Index.getTop(Gindex).getStackNode().getCardValue());
						Index.sevenStackPanels[Gindex - 1].resetHeightAfterAdd(1);
					} else {
						// 不满足放入条件
						System.out.println("不满足放入条件");
						Index.gatherCardPanels[stackIndex - 1].setTop(new CardStackNode(cardPanel));
					}
				} else {
					System.out.println("caseOthers(Gather)");
					Index.gatherCardPanels[stackIndex - 1].setTop(new CardStackNode(cardPanel));
				}
			} else {
				System.out.println("caseNull(Gather)");
				Index.gatherCardPanels[stackIndex - 1].setTop(new CardStackNode(cardPanel));
			}
			Index.refresh();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseDragged(e);
		if (isLockedOnCard) {
			// 拖拽卡牌时
			System.out.println("拖拽");
			mouseEndX = e.getXOnScreen(); // 获取新坐标
			mouseEndY = e.getYOnScreen(); // 获取新坐标
			cardPanel.setLocation(componentX + mouseEndX - mouseStartX, componentY + mouseEndY - mouseStartY);
			// contentPanel.repaint();
		}
	}
}
