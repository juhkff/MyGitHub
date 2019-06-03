package adapter;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;

import element.CardStackNode;
import element.StaticData;
import tools.SolitaireCheck;
import uiDao.CardPanel;
import uiDao.GamePage;

public class SevenStackAdapter extends MouseAdapter {

	private int index;
	private GamePage jf;

	public SevenStackAdapter(int index, GamePage jf) {
		super();
		this.index = index;
		this.jf = jf;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseClicked(arg0);
		if (arg0.getButton() == MouseEvent.BUTTON3) {
			if (jf.isHasClicked()) {
				System.out.println("�����ڶ��ε��(All)");
				CardStackNode recBottom = jf.getTranBottom();
				CardPanel cardPanel = jf.getCardPanel();
				String sendName = jf.getClickComponentName();
				if (sendName.startsWith("sevenStackPanel")) {
					int length = sendName.length();
					int startLength = "sevenStackPanel".length();
					String indexStr = sendName.substring(startLength, length);
					int Gindex = Integer.parseInt(indexStr); // ��ô�������ĸ��±���ƶ�
					if ((jf.getSevenStackPanels()[index - 1].cardNum == 0 && cardPanel.getCardNumber().equals("K"))
							|| (jf.getSevenStackPanels()[index - 1].cardNum > 0
									&& SolitaireCheck.canPushToSevenStack(index, cardPanel, jf))) {
						// ���Բ���
						System.out.println("���Բ���");
						CardStackNode top = jf.getTop(Gindex); // ��ö�Ӧ�Ŀ��ƶ�
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
							System.out.println("����Ŀ���ֵΪ:" + cur.getCardValue());
							newTop.setNextNode(jf.getTop(index));
							jf.setTop(index, newTop);
							jf.getSevenStackPanels()[index - 1].cardNum++;

							jf.getSevenStackPanels()[Gindex - 1].remove(top.getStackNode());
							top = top.getNextNode();
							jf.getSevenStackPanels()[Gindex - 1].cardNum--;
						}
						System.out.println("������" + clickCardNum + "����");
						System.out.println("��֤---��Ӻ�Ŀ��ѵĶ�����ֵΪ:" + jf.getTop(index).getStackNode().getCardValue());
						jf.getSevenStackPanels()[index - 1].resetHeightAfterAdd(clickCardNum);
						jf.getSevenStackPanels()[index - 1].repaint();

						jf.setTop(Gindex, top);
						jf.getSevenStackPanels()[Gindex - 1].resetHeightAfterDelete(clickCardNum);
						jf.getSevenStackPanels()[Gindex - 1].repaint();
						if (jf.getSevenStackPanels()[Gindex - 1].getCardNum() == 0) {
							// ���õ��Ʊ߿�
							jf.getSevenStackPanels()[Gindex - 1]
									.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
						} else {
							// ȥ���߿�(�Ӿ�����ʵûɶ��)
							jf.getSevenStackPanels()[Gindex - 1].setBorder(null);
						}
					}
				} else if (sendName.startsWith("gatherCardPanel")) {
					int length = sendName.length();
					int startLength = "gatherCardPanel".length();
					String indexStr = sendName.substring(startLength, length);
					int Gindex = Integer.parseInt(indexStr); // ��ô�������ĸ��±���ƶ�
					if ((jf.getSevenStackPanels()[index - 1].cardNum == 0 && cardPanel.getCardNumber().equals("K"))
							|| (jf.getSevenStackPanels()[index - 1].cardNum > 0
									&& SolitaireCheck.canPushToSevenStack(index, cardPanel, jf))) {
						// ���Դ���
						jf.getGatherCardPanels()[Gindex - 1].getTop(true);
						CardPanel cur = jf.getCardPanel();
						cur.setLocation(0,
								(jf.getSevenStackPanels()[index - 1].getCardNum()) * StaticData.getMinilocation(3));
						jf.getSevenStackPanels()[index - 1].add(cur,
								new Integer(jf.getSevenStackPanels()[index - 1].getCardNum() + 1));
						cur.addMouseListener(jf.getSevenStackCardPanelAdapters()[index - 1]);
						cur.addMouseMotionListener(jf.getSevenStackCardPanelAdapters()[index - 1]);
						CardStackNode newTop = new CardStackNode(cur);
						System.out.println("����Ŀ���ֵΪ:" + cur.getCardValue());
						newTop.setNextNode(jf.getTop(index));
						jf.setTop(index, newTop);
						jf.getSevenStackPanels()[index - 1].cardNum++;
						System.out.println("��֤---��Ӻ�Ŀ��ѵĶ�����ֵΪ:" + jf.getTop(index).getStackNode().getCardValue());
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
						System.out.println("����Ŀ���ֵΪ:" + cardPanel.getCardValue());
						newTop.setNextNode(jf.getTop(index));
						jf.setTop(index, newTop);
						jf.getSevenStackPanels()[index - 1].cardNum++;
						System.out.println("��֤---��Ӻ�Ŀ��ѵĶ�����ֵΪ:" + jf.getTop(index).getStackNode().getCardValue());
						jf.getSevenStackPanels()[index - 1].resetHeightAfterAdd(1);
						jf.getDealedStackPanel().getTop();
						jf.getDealedStackPanel().repaint();
						jf.getSevenStackPanels()[index - 1].repaint();
					}
				}
				jf.refresh();
				jf.getGameFoot().reset();
			}
		}
	}

}
