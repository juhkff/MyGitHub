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
			System.out.println("�����м�!");
			SolitaireCheck.autoCheck(jf);
		}
		if (arg0.getButton() == MouseEvent.BUTTON3) {
			if (jf.isHasClicked() && !isLock) {
				System.out.println("�����ڶ��ε��(Single)");
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
						if (top != null && top.getStackNode() != null && StaticData.isCardAutoChange()) {
							top.getStackNode().changeToFront();
							// System.out.println("asdasd");
						}
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
			} else if (!isLock) {
				// ��һ�ε��
				this.cardPanel = (CardPanel) arg0.getComponent();
				if (cardPanel.isPositive()) {
					// ����������
					jf.setHasClicked(true);
					CardStackNode top = jf.getTop(index); // ��ö�Ӧ�Ŀ��ƶ�
					System.out.println("��" + index + "���ƶѵ�����Ϊ" + jf.getSevenStackPanels()[index - 1].cardNum);
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
					bottom = null; // ���س�ʼ״̬
					jf.getGameFoot().setTextToClicked(this.cardPanel);
				}
			}
		} else if (arg0.getButton() == MouseEvent.BUTTON1) {
			System.out.println("�������");
			this.bottom = null;
			this.cardPanel = (CardPanel) arg0.getComponent();
			System.out.println(cardPanel.getCardValue());
			System.out.println(jf.getTop(index).getStackNode().getCardValue());
			if (jf.getTop(index) != null && jf.getTop(index).getStackNode().getCardValue() == cardPanel.getCardValue()
					&& !cardPanel.isPositive()) {
				// �ײ����ڱ���ʱ
				System.out.println("�ײ����ڱ���,���䷭������");
				cardPanel.changeToFront();
			} else if (jf.getTop(index).getStackNode().getCardValue() == cardPanel.getCardValue()
					&& cardPanel.isPositive()) {
				System.out.println("�ײ���������");
			} else if (jf.getTop(index).getStackNode().getCardValue() != cardPanel.getCardValue()) {
				System.out.println("����Ĳ��ǵײ���");
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
		System.out.println("��������");
		this.cardPanel = (CardPanel) arg0.getComponent();
		mouseStartX = arg0.getXOnScreen();
		mouseStartY = arg0.getYOnScreen();
		System.out.println(mouseStartX + "\t" + mouseStartY);
		System.out.println("ѡ�п��Ƶ�ֵΪ:" + cardPanel.getCardValue());
		if (cardPanel.isPositive()) {
			// ����������,isLock�ű�Ϊtrue
			int location_X = jf.getSevenStackPanels()[index - 1].getX();
			int location_Y = jf.getSevenStackPanels()[index - 1].getY();
			int stackHeight = jf.getSevenStackPanels()[index - 1].getHeight();
			int stackWidth = jf.getSevenStackPanels()[index - 1].getWidth();
			System.out.println(location_X + "\t" + location_Y + "\t" + stackHeight + "\t" + stackWidth);
			CardStackNode top = jf.getTop(index); // ��ö�Ӧ�Ŀ��ƶ�
			System.out.println("��" + index + "���ƶѵ�����Ϊ" + jf.getSevenStackPanels()[index - 1].cardNum);
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
			// ���ں�
			i++;
			System.out.println("i=" + i);
			System.out.println("���ڵ��ƶѸ߶�Ϊ:" + stackHeight);
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
				// ���õ��Ʊ߿�
				jf.getSevenStackPanels()[index - 1].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
			} else {
				// ȥ���߿�(�Ӿ�����ʵûɶ��)
				jf.getSevenStackPanels()[index - 1].setBorder(null);
			}
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
		if (arg0.getButton() != MouseEvent.BUTTON1) {
			return;
		}
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
					if (SolitaireCheck.canPushToGatherStack(jf.getGatherCardPanels()[Gindex - 1], cardPanel)) {
						// �ܷ���
						System.out.println("�ܷ���");
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
						// ���ܷ���
						System.out.println("���ܷ���");
						// �Ż�ԭ��
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
					// ������������ʱ
					int length = resultComponent.length();
					int startLength = "sevenStackPanel".length();
					String indexStr = resultComponent.substring(startLength, length);
					int Gindex = Integer.parseInt(indexStr); // GindexΪ�����öѵ��±�����(��1��ʼ)
					System.out.println("Index.getSevenStackPanels()[Gindex - 1].cardNum = "
							+ jf.getSevenStackPanels()[Gindex - 1].cardNum + "\tcardPanel.getCardNumber() = "
							+ cardPanel.getCardNumber());
					if (jf.getSevenStackPanels()[Gindex - 1].cardNum == 0 && cardPanel.getCardNumber().equals("K")) {
						// Ŀ�꿨�������Ҹ���ֵΪKʱ
						// ��ѡȡ��������Ŀ�꿨��
						System.out.println("Ŀ�꿨�������Ҹ���ֵΪK");
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
							System.out.println("����Ŀ���ֵΪ:" + cur.getCardValue());
							newTop.setNextNode(jf.getTop(Gindex));
							jf.setTop(Gindex, newTop);
							jf.getSevenStackPanels()[Gindex - 1].cardNum++;
						}
						System.out.println("������" + j + "����");
						System.out.println("��֤---��Ӻ�Ŀ��ѵĶ�����ֵΪ:" + jf.getTop(Gindex).getStackNode().getCardValue());
						secondPanel.repaint();
						jf.getSevenStackPanels()[Gindex - 1].resetHeightAfterAdd(cardNum);

						if (jf.getTop(index) != null && jf.getTop(index).getStackNode() != null
								&& StaticData.isCardAutoChange()) {
							jf.getTop(index).getStackNode().changeToFront();
							// System.out.println("asdasd");
						}

					} else if (jf.getSevenStackPanels()[Gindex - 1].cardNum > 0
							&& SolitaireCheck.canPushToSevenStack(Gindex, cardPanel, jf)) {
						// Ŀ�꿨�������Ҹ��Ʒ��ϲ�������ʱ
						// ��ѡȡ��������Ŀ�꿨��
						System.out.println("Ŀ�꿨�������Ҹ��Ʒ��ϲ�������");
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
							System.out.println("����Ŀ���ֵΪ:" + cur.getCardValue());
							newTop.setNextNode(jf.getTop(Gindex));
							jf.setTop(Gindex, newTop);
							jf.getSevenStackPanels()[Gindex - 1].cardNum++;
						}
						System.out.println("������" + j + "����");
						System.out.println("��֤---��Ӻ�Ŀ��ѵĶ�����ֵΪ:" + jf.getTop(Gindex).getStackNode().getCardValue());
						secondPanel.repaint();
						jf.getSevenStackPanels()[Gindex - 1].resetHeightAfterAdd(cardNum);

						if (jf.getTop(index) != null && jf.getTop(index).getStackNode() != null
								&& StaticData.isCardAutoChange()) {
							jf.getTop(index).getStackNode().changeToFront();
							// System.out.println("asdasd");
						}
					} else {
						// �Ż�ԭ��
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
					// �Ż�ԭ��
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
				// �Ż�ԭ��
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
