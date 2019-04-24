package tools;

import element.StaticData;
import uiDao.CardPanel;
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
				&& StaticData.getGathervaluemap(gatherCardPanel.getTop(false).getStackNode().getCardValue())
						.equals(cardPanel.getCardValue())) // �ƶ����������漴Ϊ�ƶ���һ��Ҫ�������
			return true;
		else
			return false;
	}
}
