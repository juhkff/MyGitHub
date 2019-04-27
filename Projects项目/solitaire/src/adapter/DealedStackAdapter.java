package adapter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import element.CardStackNode;
import element.StaticData;
import tools.FindComponent;
import tools.SolitaireCheck;
import uiDao.CardPanel;
import uiDao.DealedStackPanel;
import uiPaint.Index;

public class DealedStackAdapter extends MouseAdapter {
	private int mouseStartX, mouseStartY; // ��갴��ʱ����
	private int mouseEndX, mouseEndY; // ����ƶ�ʱ����
	private int componentX, componentY; // �ؼ�����
	private boolean isLockedOnCard = false; // ����Ƿ����϶��Ŀ���
	private CardPanel cardPanel;

	private DealedStackPanel dealedStackPanel;
	private JPanel secondPanel;

	public DealedStackAdapter(/* DealedStackPanel dealedStackPanel, */JPanel secondPanel) {
		super();
		// TODO Auto-generated constructor stub
		// this.dealedStackPanel = dealedStackPanel;
		this.secondPanel = secondPanel;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mousePressed(e);
		// System.out.println(e.getComponent().getName());
		this.dealedStackPanel = (DealedStackPanel) e.getComponent();
		System.out.println("��ȡ�������");
		mouseStartX = e.getXOnScreen();
		mouseStartY = e.getYOnScreen();
		System.out.println(mouseStartX + "\t" + mouseStartX);
		System.out.println(dealedStackPanel.getDealedNum());
		if (dealedStackPanel.getDealedNum() > 0) {
			cardPanel = dealedStackPanel.getTop().getStackNode();
			Index.cardPanel = cardPanel;
			// dealedStackPanel.remove(cardPanel);
			// dealedStackPanel.repaint();
			cardPanel.setLocation(StaticData.getDealedlocation(0), StaticData.getDealedlocation(1));
			// contentPanel.add(cardPanel);
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
		// super.mouseReleased(e);
		isLockedOnCard = false;
		String resultComponent = FindComponent.findComponentByCenterPoint(cardPanel);
		System.out.println("\t" + resultComponent);

		secondPanel.remove(cardPanel);
		secondPanel.repaint();
		cardPanel.setLocation(0, 0);

		if (resultComponent != null) {
			switch (resultComponent) {
			case "gatherCardPanel1":
				// ��������Ƿ��ܷ���˶���
				if (SolitaireCheck.canPushToGatherStack(Index.gatherCardPanels[0], cardPanel)) {
					// �ܷ���
					System.out.println("case1");
					Index.gatherCardPanels[0].setTop(new CardStackNode(cardPanel));
					// cardPanel.repaint();
				} else {
					// ���ܷ���
					System.out.println("case1_2");
					dealedStackPanel.setTop(new CardStackNode(cardPanel));
				}
				break;

			default:
				System.out.println("caseDefault");
				dealedStackPanel.setTop(new CardStackNode(cardPanel));
				break;
			}
		} else {
			System.out.println("caseNull");
			dealedStackPanel.setTop(new CardStackNode(cardPanel));
		}
		// dealedStackPanel.repaint();
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
