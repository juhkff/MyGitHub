package adapter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLayeredPane;

import element.CardStackNode;
import element.StaticData;
import tools.FindComponent;
import tools.SolitaireCheck;
import uiDao.CardPanel;
import uiDao.DealedStackPanel;
import uiDao.GamePage;

public class DealedStackAdapter extends MouseAdapter {
	private int mouseStartX, mouseStartY; // 鼠标按下时坐标
	private int mouseEndX, mouseEndY; // 鼠标移动时坐标
	private int componentX, componentY; // 控件坐标
	private boolean isLockedOnCard = false; // 鼠标是否是拖动的卡牌
	private CardPanel cardPanel;

	private DealedStackPanel dealedStackPanel;
	private JLayeredPane secondPanel;

	private GamePage jf;

	public DealedStackAdapter(JLayeredPane secondPanel, GamePage jf) {
		super();
		// TODO Auto-generated constructor stub
		this.secondPanel = secondPanel;
		this.jf = jf;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		if (e.getButton() != MouseEvent.BUTTON3) {
			return;
		}
		if (!jf.isHasClicked() && !isLockedOnCard) {
			// 第一次的点击
			this.dealedStackPanel = (DealedStackPanel) e.getComponent();
			System.out.println(dealedStackPanel.getDealedNum());
			if (dealedStackPanel.getDealedNum() > 0) {
				jf.setHasClicked(true);
				cardPanel = dealedStackPanel.getTop(false).getStackNode();
				jf.setCardPanel(cardPanel);
				jf.setClickComponentName(dealedStackPanel.getName());
				jf.setSingle(true);
				jf.setTranBottom(null);
				jf.getGameFoot().setTextToClicked(cardPanel);
			}
		} else if (!isLockedOnCard) {
			// 取消定位
			jf.refresh();
			jf.getGameFoot().reset();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mousePressed(e);
		if (e.getButton() != MouseEvent.BUTTON1) {
			return;
		}
		this.dealedStackPanel = (DealedStackPanel) e.getComponent();
		System.out.println("获取鼠标坐标");
		mouseStartX = e.getXOnScreen();
		mouseStartY = e.getYOnScreen();
		System.out.println(mouseStartX + "\t" + mouseStartX);
		System.out.println(dealedStackPanel.getDealedNum());
		if (dealedStackPanel.getDealedNum() > 0) {
			cardPanel = dealedStackPanel.getTop().getStackNode();
			cardPanel.setLocation(StaticData.getDealedlocation(0), StaticData.getDealedlocation(1));
			secondPanel.add(cardPanel);
			componentX = cardPanel.getX();
			componentY = cardPanel.getY();
			isLockedOnCard = true;
			System.out.println(componentX + "\t\t" + componentY);

		} else {
			cardPanel = null;
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
					if (SolitaireCheck.canPushToGatherStack(jf.getGatherCardPanels()[index - 1], cardPanel)) {
						// 能放入
						System.out.println("能放入");
						jf.getGatherCardPanels()[index - 1].setTop(new CardStackNode(cardPanel));
						// cardPanel.repaint();
					} else {
						// 不能放入
						System.out.println("不能放入");
						dealedStackPanel.setTop(new CardStackNode(cardPanel));
					}
				} else if (resultComponent.startsWith("sevenStackPanel")) {
					// 放在下面的牌堆上时
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
						System.out.println("目标卡堆无牌且发牌堆中该牌值为K");
						jf.getSevenStackPanels()[Gindex - 1].add(cardPanel,
								new Integer(jf.getSevenStackPanels()[Gindex - 1].getCardNum() + 1));
						cardPanel.setLocation(0,
								(jf.getSevenStackPanels()[Gindex - 1].getCardNum()) * StaticData.getMinilocation(3));
						cardPanel.addMouseListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
						cardPanel.addMouseMotionListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
						CardStackNode newTop = new CardStackNode(cardPanel);
						System.out.println("放入的卡牌值为:" + cardPanel.getCardValue());
						newTop.setNextNode(jf.getTop(Gindex));
						jf.setTop(Gindex, newTop);
						jf.getSevenStackPanels()[Gindex - 1].cardNum++;
						System.out.println("验证---添加后的卡堆的顶部牌值为:" + jf.getTop(Gindex).getStackNode().getCardValue());
						jf.getSevenStackPanels()[Gindex - 1].resetHeightAfterAdd(1);
					} else if (jf.getSevenStackPanels()[Gindex - 1].cardNum > 0
							&& SolitaireCheck.canPushToSevenStack(Gindex, cardPanel, jf)) {
						// 目标卡堆有牌且该牌符合插入条件时
						// 将选取的牌移入目标卡堆
						System.out.println("目标卡堆无牌且发牌堆中该牌值为K");
						jf.getSevenStackPanels()[Gindex - 1].add(cardPanel,
								new Integer(jf.getSevenStackPanels()[Gindex - 1].getCardNum() + 1));
						cardPanel.setLocation(0,
								(jf.getSevenStackPanels()[Gindex - 1].getCardNum()) * StaticData.getMinilocation(3));
						cardPanel.addMouseListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
						cardPanel.addMouseMotionListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
						CardStackNode newTop = new CardStackNode(cardPanel);
						System.out.println("放入的卡牌值为:" + cardPanel.getCardValue());
						newTop.setNextNode(jf.getTop(Gindex));
						jf.setTop(Gindex, newTop);
						jf.getSevenStackPanels()[Gindex - 1].cardNum++;
						System.out.println("验证---添加后的卡堆的顶部牌值为:" + jf.getTop(Gindex).getStackNode().getCardValue());
						jf.getSevenStackPanels()[Gindex - 1].resetHeightAfterAdd(1);
					} else {
						// 不满足放入条件
						System.out.println("不满足放入条件");
						dealedStackPanel.setTop(new CardStackNode(cardPanel));
					}
				} else {
					System.out.println("caseOthers");
					dealedStackPanel.setTop(new CardStackNode(cardPanel));
				}
			} else {
				System.out.println("caseNull");
				dealedStackPanel.setTop(new CardStackNode(cardPanel));
			}
			// dealedStackPanel.repaint();
			jf.refresh();
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
