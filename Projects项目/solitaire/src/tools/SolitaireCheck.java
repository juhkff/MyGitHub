package tools;

import java.awt.Color;

import javax.swing.BorderFactory;

import element.CardStackNode;
import element.StaticData;
import uiDao.CardPanel;
import uiDao.GamePage;
import uiDao.GatherCardPanel;

public class SolitaireCheck {
	/**
	 * ����cardPanel�Ƿ��ܷ���GatherCardPanel
	 * 
	 * @param gatherCardPanel-��������ƶ�
	 * @param cardPanel-������Ŀ���
	 * @return true-�ɷ���;false-���ɷ���
	 */
	public static boolean canPushToGatherStack(GatherCardPanel gatherCardPanel, CardPanel cardPanel) {
		if (gatherCardPanel.getCardNum() > 0)
			System.out.println(
					gatherCardPanel.getTop(false).getStackNode().getCardValue() + "\t" + cardPanel.getCardValue());
		if (gatherCardPanel.getCardNum() == 0 && cardPanel.getCardValue().endsWith("1")) // �ƶ�����������Ϊ1
			return true;
		else if (gatherCardPanel.getCardNum() > 0
				&& StaticData.getGathervaluemap(gatherCardPanel.getTop(false).getStackNode().getCardValue()) != null
				&& StaticData.getGathervaluemap(gatherCardPanel.getTop(false).getStackNode().getCardValue())
						.equals(cardPanel.getCardValue())) // �ƶ����������漴Ϊ�ƶ���һ��Ҫ�������
			return true;
		else
			return false;
	}

	/**
	 * ����cardPanel�Ƿ��ܷ���SevenStackPanel.sevenStackIndex��СֵΪ1.ע��CardPanel�������ֻ�ǽڵ��β��.ʹ�ô˺�����ǰ������֪�����鿨������
	 * 
	 * @param sevenStackIndex-��������ƶ�
	 * @param cardPanel-������Ŀ���
	 * @return true-�ɷ���;false-���ɷ���
	 */
	public static boolean canPushToSevenStack(int sevenStackIndex, CardPanel cardPanel, GamePage jf) {
		if (!jf.getTop(sevenStackIndex).getStackNode().isPositive())
			return false;
		String sevenBottomCardValue = jf.getTop(sevenStackIndex).getStackNode().getCardValue();
		String topCardValue = cardPanel.getCardValue();
		String[] values = StaticData.getValueMapValue(topCardValue);
		if (values == null)
			return false;
		else if (sevenBottomCardValue.equals(values[0]) || sevenBottomCardValue.equals(values[1]))
			return true;
		else
			return false;
	}

	/**
	 * ����м������Զ����
	 * 
	 * @param jf
	 * @return autoCheckCardNum
	 */
	public static int autoCheck(GamePage jf) {
		int autoCheckCardNum = 0;
		int checkTime = StaticData.getCheckTime();
		for (int i = 0; i < checkTime; i++) {
			// ���ƶ��ж�
			CardStackNode dTop = jf.getDealedStackPanel().getTop(false); // ȡ����������
			if (dTop != null && dTop.getStackNode() != null) {
				CardPanel cardPanel = dTop.getStackNode();
				System.out.println("Yes");
				for (int stackIndex = 1; stackIndex < StaticData.getGathernum() + 1; stackIndex++) {
					if (SolitaireCheck.canPushToGatherStack(jf.getGatherCardPanels()[stackIndex - 1], cardPanel)) {
						// �ܷ���
						System.out.println("�ܷ���");
						jf.getDealedStackPanel().getTop(); // ȡ����������
						jf.getGatherCardPanels()[stackIndex - 1].setTop(new CardStackNode(cardPanel));
						jf.getDealedStackPanel().repaint();
						jf.getGatherCardPanels()[stackIndex - 1].repaint();
						jf.setHasClicked(false);
						// cardPanel.repaint();
						autoCheckCardNum++;
						break;
					} else {
						// ���ܷ���
						System.out.println("���ܷ���");
						jf.setHasClicked(false);
					}
				}
				jf.setClickComponentName(null);
				jf.getGameFoot().reset();
			}
			// �ײ��ƶ��ж�
			for (int j = 1; j < StaticData.getSevenstacknum() + 1; j++) {
				CardStackNode top = jf.getTop(j);
				if (top != null && top.getStackNode() != null) {
					CardPanel cardPanel = top.getStackNode();
					for (int k = 0; k < StaticData.getGathernum(); k++) {
						if (SolitaireCheck.canPushToGatherStack(jf.getGatherCardPanels()[k], cardPanel)) {
							// �ܷ���
							System.out.println("�ܷ���");
							// ȥ��ԭ��
							CardPanel cur = top.getStackNode();
							jf.setTop(j, top.getNextNode());
							jf.getSevenStackPanels()[j - 1].remove(cur);
							jf.getSevenStackPanels()[j - 1].cardNum--;
							// ����öѶ���
							cardPanel.setLocation(0, 0);
							cardPanel.removeMouseListener(jf.getSevenStackCardPanelAdapters()[j - 1]);
							cardPanel.removeMouseMotionListener(jf.getSevenStackCardPanelAdapters()[j - 1]);
							jf.getGatherCardPanels()[k].setTop(new CardStackNode(cardPanel));
							jf.getSevenStackPanels()[j - 1].resetHeightAfterDelete(1);
							jf.getSevenStackPanels()[j - 1].repaint();
							jf.getGatherCardPanels()[k].repaint();
							if (jf.getSevenStackPanels()[j - 1].getCardNum() == 0) {
								// ���õ��Ʊ߿�
								jf.getSevenStackPanels()[j - 1]
										.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
							} else {
								// ȥ���߿�(�Ӿ�����ʵûɶ��)
								jf.getSevenStackPanels()[j - 1].setBorder(null);
							}
							if (jf.getTop(j) != null && jf.getTop(j).getStackNode() != null
									&& StaticData.isCardAutoChange()) {
								jf.getTop(j).getStackNode().changeToFront();
								// System.out.println("asdasd");
							}
							autoCheckCardNum++;
							break;
						}
					}
				}
			}
		}
		jf.getGameFoot().setTextToCheck(autoCheckCardNum);
		if (autoCheckCardNum > 0) {
			StaticData.setCheckTime(checkTime * 2);
		} else if (autoCheckCardNum == 0) {
			StaticData.setCheckTime(1);
		}
		return autoCheckCardNum;
	}
}
