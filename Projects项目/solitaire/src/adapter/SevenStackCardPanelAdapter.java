package adapter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JLayeredPane;

import element.CardStackNode;
import element.StaticData;
import tools.FindComponent;
import tools.SolitaireCheck;
import uiDao.CardPanel;
import uiPaint.Index;

public class SevenStackCardPanelAdapter extends MouseAdapter {
	// private static CardStackNode top, bottom;
	private String startComponentName; // ѡȡ�������ڵĿ���,�����ų����ƷŻص�����ԭ�ƶѵ����
	private int mouseStartX, mouseStartY; // ��갴��ʱ����
	private int mouseEndX, mouseEndY; // ����ƶ�ʱ����
	private int[] componentX, componentY; // �ؼ�����
	private JLayeredPane secondPanel;
	private CardPanel cardPanel;
	private CardStackNode bottom = null; // ���ڻָ��ƶ�ǰ����
	private boolean isLock = false; // ����Ƿ����϶�����
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
		System.out.println("�������");
		this.bottom = null;
		this.cardPanel = (CardPanel) arg0.getComponent();
		System.out.println(cardPanel.getCardValue());
		System.out.println(Index.getTop(index).getStackNode().getCardValue());
		if (Index.getTop(index) != null && Index.getTop(index).getStackNode().getCardValue() == cardPanel.getCardValue()
				&& !cardPanel.isPositive()) {
			// �ײ����ڱ���ʱ
			System.out.println("�ײ����ڱ���,���䷭������");
			cardPanel.changeToFront();
		} else if (Index.getTop(index).getStackNode().getCardValue() == cardPanel.getCardValue()
				&& cardPanel.isPositive()) {
			System.out.println("�ײ���������");
		} else if (Index.getTop(index).getStackNode().getCardValue() != cardPanel.getCardValue()) {
			System.out.println("����Ĳ��ǵײ���");
		}
		/*
		 * CardStackNode cardStackNode = cardPanel.getTop(false).getNextNode(); if
		 * (cardPanel.getCardNum() > 0 && cardStackNode != null &&
		 * cardStackNode.getStackNode().isPositive()) { // �ײ����ڱ���ʱ
		 * System.out.println("�ײ����ڱ���,���䷭������");
		 * cardStackNode.getStackNode().changeToFront(); }
		 */
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseDragged(arg0);
		this.cardPanel = (CardPanel) arg0.getComponent();
		if (isLock) {
			mouseEndX = arg0.getXOnScreen();
			mouseEndY = arg0.getYOnScreen();
			CardStackNode top = Index.getTop(index); // ��ö�Ӧ�Ŀ��ƶ�
			// System.out.println("top�Ŀ���ֵΪ:" + top.getStackNode().getCardValue());
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
				System.out.println("cur.setLocation(" + (component_X + mouseEndX - mouseStartX) + ","
						+ (component_Y + mouseEndY - mouseStartY) + ")");
				cur.setLocation(component_X + mouseEndX - mouseStartX, component_Y + mouseEndY - mouseStartY);
				curNode = curNode.getNextNode();
			}
			System.out.println("ѭ�����");
			// ���ں�
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
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseEntered(arg0);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseExited(arg0);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseMoved(arg0);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		super.mousePressed(arg0);
		System.out.println("��������");
		this.cardPanel = (CardPanel) arg0.getComponent();
		mouseStartX = arg0.getXOnScreen();
		mouseStartY = arg0.getYOnScreen();
		System.out.println(mouseStartX + "\t" + mouseStartY);
		System.out.println("ѡ�п��Ƶ�ֵΪ:" + cardPanel.getCardValue());
		if (cardPanel.isPositive()) {
			// ����������,isLock�ű�Ϊtrue
			int location_X = Index.sevenStackPanels[index - 1].getX();
			int location_Y = Index.sevenStackPanels[index - 1].getY();
			int stackHeight = Index.sevenStackPanels[index - 1].getHeight();
			int stackWidth = Index.sevenStackPanels[index - 1].getWidth();
			System.out.println(location_X + "\t" + location_Y + "\t" + stackHeight + "\t" + stackWidth);
			CardStackNode top = Index.getTop(index); // ��ö�Ӧ�Ŀ��ƶ�
			System.out.println("��" + index + "���ƶѵ�����Ϊ" + Index.sevenStackPanels[index - 1].cardNum);
			// System.out.println("��" + index + "���ƶѵ�top����ֵΪ" +
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
			// ���ں�
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
			Index.sevenStackPanels[index - 1].resetHeightAfterDelete(cardNum);
			this.startComponentName = FindComponent.findComponentByCenterPoint(cardPanel);
			System.out.println("���Ƴ�ʼ�ƶ�:" + startComponentName);
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
		System.out.println("�����ͷ�");
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
					// ��������Ƿ��ܷ���˶���
					if (SolitaireCheck.canPushToGatherStack(Index.gatherCardPanels[Gindex - 1], cardPanel)) {
						// �ܷ���
						System.out.println("�ܷ���");
						secondPanel.remove(cardPanel);
						secondPanel.repaint();
						cardPanel.setLocation(0, 0);
						Index.gatherCardPanels[Gindex - 1].setTop(new CardStackNode(cardPanel));
						cardPanel.removeMouseListener(Index.sevenStackCardPanelAdapters[index - 1]);
						cardPanel.removeMouseMotionListener(Index.sevenStackCardPanelAdapters[index - 1]);

						// Index.setTop(index, Index.getTop(index).getNextNode());
						// cardPanel.repaint();
					} else {
						// ���ܷ���
						System.out.println("���ܷ���");
						// �Ż�ԭ��
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
					// ������������ʱ
					int length = resultComponent.length();
					int startLength = "sevenStackPanel".length();
					String indexStr = resultComponent.substring(startLength, length);
					int Gindex = Integer.parseInt(indexStr); // GindexΪ�����öѵ��±�����(��1��ʼ)
					System.out.println(
							"Index.sevenStackPanels[Gindex - 1].cardNum = " + Index.sevenStackPanels[Gindex - 1].cardNum
									+ "\tcardPanel.getCardNumber() = " + cardPanel.getCardNumber());
					if (Index.sevenStackPanels[Gindex - 1].cardNum == 0 && cardPanel.getCardNumber().equals("K")) {
						// Ŀ�꿨�������Ҹ���ֵΪKʱ
						// ��ѡȡ��������Ŀ�꿨��
						System.out.println("Ŀ�꿨�������Ҹ���ֵΪK");
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
							System.out.println("����Ŀ���ֵΪ:" + cur.getCardValue());
							newTop.setNextNode(Index.getTop(Gindex));
							Index.setTop(Gindex, newTop);
							Index.sevenStackPanels[Gindex - 1].cardNum++;
							// Index.setTop(index, Index.getTop(index).getNextNode());
						}
						System.out.println("������" + j + "����");
						System.out.println("��֤---��Ӻ�Ŀ��ѵĶ�����ֵΪ:" + Index.getTop(Gindex).getStackNode().getCardValue());
						secondPanel.repaint();
						Index.sevenStackPanels[Gindex - 1].resetHeightAfterAdd(cardNum);

					} else if (Index.sevenStackPanels[Gindex - 1].cardNum > 0
							&& SolitaireCheck.canPushToSevenStack(Gindex, cardPanel)) {
						// Ŀ�꿨�������Ҹ��Ʒ��ϲ�������ʱ
						// ��ѡȡ��������Ŀ�꿨��
						System.out.println("Ŀ�꿨�������Ҹ��Ʒ��ϲ�������");
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
							System.out.println("����Ŀ���ֵΪ:" + cur.getCardValue());
							newTop.setNextNode(Index.getTop(Gindex));
							Index.setTop(Gindex, newTop);
							Index.sevenStackPanels[Gindex - 1].cardNum++;
							// Index.setTop(index, Index.getTop(index).getNextNode());
						}
						System.out.println("������" + j + "����");
						System.out.println("��֤---��Ӻ�Ŀ��ѵĶ�����ֵΪ:" + Index.getTop(Gindex).getStackNode().getCardValue());
						secondPanel.repaint();
						Index.sevenStackPanels[Gindex - 1].resetHeightAfterAdd(cardNum);
					} else {
						// �Ż�ԭ��
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
					// ��ʱ�÷Ż�ԭ��
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
					// �Ż�ԭ��
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
				// �Ż�ԭ��
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
		} else {
			isLock = false;
		}
		this.bottom = null;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		super.mouseWheelMoved(arg0);
	}

}
