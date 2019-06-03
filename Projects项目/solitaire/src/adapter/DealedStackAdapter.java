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
	private int mouseStartX, mouseStartY; // ��갴��ʱ����
	private int mouseEndX, mouseEndY; // ����ƶ�ʱ����
	private int componentX, componentY; // �ؼ�����
	private boolean isLockedOnCard = false; // ����Ƿ����϶��Ŀ���
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
			// ��һ�εĵ��
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
			// ȡ����λ
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
		System.out.println("��ȡ�������");
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
					// ��������Ƿ��ܷ���˶���
					if (SolitaireCheck.canPushToGatherStack(jf.getGatherCardPanels()[index - 1], cardPanel)) {
						// �ܷ���
						System.out.println("�ܷ���");
						jf.getGatherCardPanels()[index - 1].setTop(new CardStackNode(cardPanel));
						// cardPanel.repaint();
					} else {
						// ���ܷ���
						System.out.println("���ܷ���");
						dealedStackPanel.setTop(new CardStackNode(cardPanel));
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
			// ��ק����ʱ
			System.out.println("��ק");
			mouseEndX = e.getXOnScreen(); // ��ȡ������
			mouseEndY = e.getYOnScreen(); // ��ȡ������
			cardPanel.setLocation(componentX + mouseEndX - mouseStartX, componentY + mouseEndY - mouseStartY);
			// contentPanel.repaint();
		}
	}
}
