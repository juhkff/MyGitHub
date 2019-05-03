package tools;

import element.StaticData;
import uiDao.CardPanel;
import uiDao.GatherCardPanel;
import uiPaint.Index;

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

	/**
	 * ����cardPanel�Ƿ��ܷ���SevenStackPanel.sevenStackIndex��СֵΪ1.ע��CardPanel�������ֻ�ǽڵ��β��.ʹ�ô˺�����ǰ������֪�����鿨������
	 * 
	 * @param sevenStackIndex-��������ƶ�
	 * @param cardPanel-������Ŀ���
	 * @return true-�ɷ���;false-���ɷ���
	 */
	public static boolean canPushToSevenStack(int sevenStackIndex, CardPanel cardPanel) {
		if(!Index.getTop(sevenStackIndex).getStackNode().isPositive())
			return false;
		String sevenBottomCardValue = Index.getTop(sevenStackIndex).getStackNode().getCardValue();
		String topCardValue = cardPanel.getCardValue();
		String[] values = StaticData.getValueMapValue(topCardValue);
		if (values == null)
			return false;
		else if (sevenBottomCardValue.equals(values[0]) || sevenBottomCardValue.equals(values[1]))
			return true;
		else
			return false;
	}
}
