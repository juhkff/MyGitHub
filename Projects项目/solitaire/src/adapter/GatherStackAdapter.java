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
import uiDao.DealedStackPanel;
import uiDao.GatherCardPanel;
import uiPaint.Index;

public class GatherStackAdapter extends MouseAdapter {
	private int stackIndex = 0; // �洢�öѵ�����(�ڼ�����)
	private int mouseStartX, mouseStartY; // ��갴��ʱ����
	private int mouseEndX, mouseEndY; // ����ƶ�ʱ����
	private int componentX, componentY; // �ؼ�����
	private boolean isLockedOnCard = false; // ����Ƿ����϶��Ŀ���
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
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mousePressed(e);
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
				if (SolitaireCheck.canPushToGatherStack(Index.gatherCardPanels[index - 1], cardPanel)) {
					// �ܷ���
					System.out.println("�ܷ���");
					Index.gatherCardPanels[index - 1].setTop(new CardStackNode(cardPanel));
					// cardPanel.repaint();
				} else {
					// (������)���ܷ���
					System.out.println("���ܷ���");
					Index.gatherCardPanels[stackIndex - 1].setTop(new CardStackNode(cardPanel));
				}
			} else if (resultComponent.startsWith("sevenStackPanel")) {
				// ����������ƶ���ʱ
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
					System.out.println("Ŀ�꿨�������ҷ��ƶ��и���ֵΪK");
					Index.sevenStackPanels[Gindex - 1].add(cardPanel,
							new Integer(Index.sevenStackPanels[Gindex - 1].getCardNum() + 1));
					cardPanel.setLocation(0,
							(Index.sevenStackPanels[Gindex - 1].getCardNum()) * StaticData.getMinilocation(3));
					cardPanel.addMouseListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
					cardPanel.addMouseMotionListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
					CardStackNode newTop = new CardStackNode(cardPanel);
					System.out.println("����Ŀ���ֵΪ:" + cardPanel.getCardValue());
					newTop.setNextNode(Index.getTop(Gindex));
					Index.setTop(Gindex, newTop);
					Index.sevenStackPanels[Gindex - 1].cardNum++;
					System.out.println("��֤---��Ӻ�Ŀ��ѵĶ�����ֵΪ:" + Index.getTop(Gindex).getStackNode().getCardValue());
					Index.sevenStackPanels[Gindex - 1].resetHeightAfterAdd(1);
				} else if (Index.sevenStackPanels[Gindex - 1].cardNum > 0
						&& SolitaireCheck.canPushToSevenStack(Gindex, cardPanel)) {
					// Ŀ�꿨�������Ҹ��Ʒ��ϲ�������ʱ
					// ��ѡȡ��������Ŀ�꿨��
					System.out.println("Ŀ�꿨�������ҷ��ƶ��и���ֵΪK");
					Index.sevenStackPanels[Gindex - 1].add(cardPanel,
							new Integer(Index.sevenStackPanels[Gindex - 1].getCardNum() + 1));
					cardPanel.setLocation(0,
							(Index.sevenStackPanels[Gindex - 1].getCardNum()) * StaticData.getMinilocation(3));
					cardPanel.addMouseListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
					cardPanel.addMouseMotionListener(Index.sevenStackCardPanelAdapters[Gindex - 1]);
					CardStackNode newTop = new CardStackNode(cardPanel);
					System.out.println("����Ŀ���ֵΪ:" + cardPanel.getCardValue());
					newTop.setNextNode(Index.getTop(Gindex));
					Index.setTop(Gindex, newTop);
					Index.sevenStackPanels[Gindex - 1].cardNum++;
					System.out.println("��֤---��Ӻ�Ŀ��ѵĶ�����ֵΪ:" + Index.getTop(Gindex).getStackNode().getCardValue());
					Index.sevenStackPanels[Gindex - 1].resetHeightAfterAdd(1);
				} else {
					// �������������
					System.out.println("�������������");
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
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseExited(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		super.mouseWheelMoved(e);
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

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseMoved(e);
	}
}
