package adapter;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;

import element.CardStackNode;
import element.StaticData;
import tools.SolitaireCheck;
import uiDao.CardPanel;
import uiPaint.Index;

public class SevenStackAdapter extends MouseAdapter {

	private int index;

	public SevenStackAdapter(int index) {
		super();
		// SevenStackCardPanelAdapter.top = top;
		// SevenStackCardPanelAdapter.bottom = bottom;
		this.index = index;
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
			}
		}
	}

}
