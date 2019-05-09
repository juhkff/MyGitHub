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
import uiDao.GatherCardPanel;

public class GatherStackAdapter extends MouseAdapter {
	private int stackIndex = 0; // �洢�öѵ�����(�ڼ�����)
	private int mouseStartX, mouseStartY; // ��갴��ʱ����
	private int mouseEndX, mouseEndY; // ����ƶ�ʱ����
	private int componentX, componentY; // �ؼ�����
	private boolean isLockedOnCard = false; // ����Ƿ����϶��Ŀ���
	private CardPanel cardPanel;

	private GatherCardPanel gatherCardPanel;
	private JLayeredPane secondPanel;

	private GamePage jf;

	public GatherStackAdapter(JLayeredPane secondPanel, GamePage jf) {
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
		if (jf.isHasClicked() && !isLockedOnCard) {
			String componentName = jf.getClickComponentName();
			if (componentName != null) {
				this.gatherCardPanel = (GatherCardPanel) e.getComponent();
				if (this.stackIndex == 0) {
					String str = gatherCardPanel.getName();
					int length = str.length();
					int startLength = "gatherCardPanel".length();
					String indexStr = str.substring(startLength, length);
					int Gindex = Integer.parseInt(indexStr);
					this.stackIndex = Gindex; // ��ȡ����
				}
				this.cardPanel = jf.getCardPanel();
				switch (componentName) {
				case "dealedStackPanel":
					// ���ƴ��ѷ��ƶ���������ƶ�
					// ��������Ƿ��ܷ���˶���
					System.out.println("Yes");
					if (SolitaireCheck.canPushToGatherStack(jf.getGatherCardPanels()[stackIndex - 1], cardPanel)) {
						// �ܷ���
						System.out.println("�ܷ���");
						jf.getDealedStackPanel().getTop(); // ȡ����������
						jf.getGatherCardPanels()[stackIndex - 1].setTop(new CardStackNode(cardPanel));
						jf.getDealedStackPanel().repaint();
						jf.getGatherCardPanels()[stackIndex - 1].repaint();
						jf.setHasClicked(false);
						// cardPanel.repaint();
					} else {
						// ���ܷ���
						System.out.println("���ܷ���");
						jf.setHasClicked(false);
					}
					jf.setClickComponentName(null);
					break;
				default:
					if (componentName.startsWith("gatherCardPanel")) {
						// �ӱ�����ƶ���������ƶ�
						int length = componentName.length();
						int startLength = "gatherCardPanel".length();
						String indexStr = componentName.substring(startLength, length);
						int Gindex = Integer.parseInt(indexStr); // ��ȡ����
						// ��������Ƿ��ܷ���˶���
						if (SolitaireCheck.canPushToGatherStack(jf.getGatherCardPanels()[stackIndex - 1], cardPanel)) {
							// �ܷ���
							System.out.println("�ܷ���");
							jf.getGatherCardPanels()[Gindex - 1].getTop(true);
							jf.getGatherCardPanels()[stackIndex - 1].setTop(new CardStackNode(cardPanel));
							jf.getGatherCardPanels()[Gindex - 1].repaint();
							jf.getGatherCardPanels()[stackIndex - 1].repaint();
							jf.setHasClicked(false);
							// cardPanel.repaint();
						} else {
							// (������)���ܷ���
							System.out.println("���ܷ���");
							jf.setHasClicked(false);
						}
					} else if (componentName.startsWith("sevenStackPanel")) {
						if (jf.isSingle()) {
							int length = componentName.length();
							int startLength = "sevenStackPanel".length();
							String indexStr = componentName.substring(startLength, length);
							int Gindex = Integer.parseInt(indexStr); // ��ȡ����
							// ��������Ƿ��ܷ���˶���
							if (SolitaireCheck.canPushToGatherStack(jf.getGatherCardPanels()[stackIndex - 1],
									cardPanel)) {
								// �ܷ���
								System.out.println("�ܷ���");
								// ȥ��ԭ��
								CardStackNode top = jf.getTop(Gindex); // ��ö�Ӧ�Ŀ��ƶ�
								CardPanel cur = top.getStackNode();
								jf.setTop(Gindex, top.getNextNode());
								jf.getSevenStackPanels()[Gindex - 1].remove(cur);
								jf.getSevenStackPanels()[Gindex - 1].cardNum--;
								// ����öѶ���
								cardPanel.setLocation(0, 0);
								cardPanel.removeMouseListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
								cardPanel.removeMouseMotionListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
								jf.getGatherCardPanels()[stackIndex - 1].setTop(new CardStackNode(cardPanel));
								jf.getSevenStackPanels()[Gindex - 1].resetHeightAfterDelete(1);
								jf.getSevenStackPanels()[Gindex - 1].repaint();
								jf.getGatherCardPanels()[stackIndex - 1].repaint();
								if (jf.getSevenStackPanels()[Gindex - 1].getCardNum() == 0) {
									// ���õ��Ʊ߿�
									jf.getSevenStackPanels()[Gindex - 1]
											.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
								} else {
									// ȥ���߿�(�Ӿ�����ʵûɶ��)
									jf.getSevenStackPanels()[Gindex - 1].setBorder(null);
								}
							} else {
								// ���ܷ���
								System.out.println("���ܷ���");
							}
						}
						jf.setHasClicked(false);
						jf.setSingle(false);
					}
					jf.setClickComponentName(null);
					break;
				}
			}
			jf.refresh();
		} else if (!isLockedOnCard) {
			// ��һ�εĵ��
			this.gatherCardPanel = (GatherCardPanel) e.getComponent();
			System.out.println(gatherCardPanel.getCardNum());
			if (gatherCardPanel.getCardNum() > 0) {
				jf.setHasClicked(true);
				cardPanel = gatherCardPanel.getTop(false).getStackNode();
				jf.setCardPanel(cardPanel);
				jf.setSingle(true);
				jf.setTranBottom(null);
				jf.setClickComponentName(gatherCardPanel.getName());
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
			this.stackIndex = Gindex; // ��ȡ����
		}
		System.out.println("��ȡ�������");
		mouseStartX = e.getXOnScreen();
		mouseStartY = e.getYOnScreen();
		System.out.println(mouseStartX + "\t" + mouseStartX);
		System.out.println(gatherCardPanel.getCardNum());
		if (gatherCardPanel.getCardNum() > 0) {
			cardPanel = gatherCardPanel.getTop(true).getStackNode();
			jf.setCardPanel(cardPanel);
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
					// ��������Ƿ��ܷ���˶���
					if (SolitaireCheck.canPushToGatherStack(jf.getGatherCardPanels()[index - 1], cardPanel)) {
						// �ܷ���
						System.out.println("�ܷ���");
						jf.getGatherCardPanels()[index - 1].setTop(new CardStackNode(cardPanel));
						// cardPanel.repaint();
					} else {
						// (������)���ܷ���
						System.out.println("���ܷ���");
						jf.getGatherCardPanels()[stackIndex - 1].setTop(new CardStackNode(cardPanel));
					}
				} else if (resultComponent.startsWith("sevenStackPanel")) {
					// ����������ƶ���ʱ
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
						System.out.println("Ŀ�꿨�������ҷ��ƶ��и���ֵΪK");
						jf.getSevenStackPanels()[Gindex - 1].add(cardPanel,
								new Integer(jf.getSevenStackPanels()[Gindex - 1].getCardNum() + 1));
						cardPanel.setLocation(0,
								(jf.getSevenStackPanels()[Gindex - 1].getCardNum()) * StaticData.getMinilocation(3));
						cardPanel.addMouseListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
						cardPanel.addMouseMotionListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
						CardStackNode newTop = new CardStackNode(cardPanel);
						System.out.println("����Ŀ���ֵΪ:" + cardPanel.getCardValue());
						newTop.setNextNode(jf.getTop(Gindex));
						jf.setTop(Gindex, newTop);
						jf.getSevenStackPanels()[Gindex - 1].cardNum++;
						System.out.println("��֤---��Ӻ�Ŀ��ѵĶ�����ֵΪ:" + jf.getTop(Gindex).getStackNode().getCardValue());
						jf.getSevenStackPanels()[Gindex - 1].resetHeightAfterAdd(1);
					} else if (jf.getSevenStackPanels()[Gindex - 1].cardNum > 0
							&& SolitaireCheck.canPushToSevenStack(Gindex, cardPanel, jf)) {
						// Ŀ�꿨�������Ҹ��Ʒ��ϲ�������ʱ
						// ��ѡȡ��������Ŀ�꿨��
						System.out.println("Ŀ�꿨�������ҷ��ƶ��и���ֵΪK");
						jf.getSevenStackPanels()[Gindex - 1].add(cardPanel,
								new Integer(jf.getSevenStackPanels()[Gindex - 1].getCardNum() + 1));
						cardPanel.setLocation(0,
								(jf.getSevenStackPanels()[Gindex - 1].getCardNum()) * StaticData.getMinilocation(3));
						cardPanel.addMouseListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
						cardPanel.addMouseMotionListener(jf.getSevenStackCardPanelAdapters()[Gindex - 1]);
						CardStackNode newTop = new CardStackNode(cardPanel);
						System.out.println("����Ŀ���ֵΪ:" + cardPanel.getCardValue());
						newTop.setNextNode(jf.getTop(Gindex));
						jf.setTop(Gindex, newTop);
						jf.getSevenStackPanels()[Gindex - 1].cardNum++;
						System.out.println("��֤---��Ӻ�Ŀ��ѵĶ�����ֵΪ:" + jf.getTop(Gindex).getStackNode().getCardValue());
						jf.getSevenStackPanels()[Gindex - 1].resetHeightAfterAdd(1);
					} else {
						// �������������
						System.out.println("�������������");
						jf.getGatherCardPanels()[stackIndex - 1].setTop(new CardStackNode(cardPanel));
					}
				} else {
					System.out.println("caseOthers(Gather)");
					jf.getGatherCardPanels()[stackIndex - 1].setTop(new CardStackNode(cardPanel));
				}
			} else {
				System.out.println("caseNull(Gather)");
				jf.getGatherCardPanels()[stackIndex - 1].setTop(new CardStackNode(cardPanel));
			}
			jf.refresh();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseDragged(e);
		if (isLockedOnCard) {
			// ��ק����ʱ
			System.out.println("��ק");
			mouseEndX = e.getXOnScreen(); // ��ȡ������
			mouseEndY = e.getYOnScreen(); // ��ȡ������
			cardPanel.setLocation(componentX + mouseEndX - mouseStartX, componentY + mouseEndY - mouseStartY);
			// contentPanel.repaint();
		}
	}
}
