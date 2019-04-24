package tools;

import element.StaticData;
import uiDao.CardPanel;
import uiDao.GatherCardPanel;

public class SolitaireCheck {
	/**
	 * 检验cardPanel是否能放入GatherCardPanel
	 * 
	 * @param gatherCardPanel-待放入的牌堆
	 * @param cardPanel-待检验的卡牌
	 * @return true-可放入;false-不可放入
	 */
	public static boolean canPushToGatherStack(GatherCardPanel gatherCardPanel, CardPanel cardPanel) {
		if (gatherCardPanel.getCardNum() > 0)
			System.out.println(
					gatherCardPanel.getTop(false).getStackNode().getCardValue() + "\t" + cardPanel.getCardValue());
		if (gatherCardPanel.getCardNum() == 0 && cardPanel.getCardValue().endsWith("1")) // 牌堆无牌且牌面为1
			return true;
		else if (gatherCardPanel.getCardNum() > 0
				&& StaticData.getGathervaluemap(gatherCardPanel.getTop(false).getStackNode().getCardValue())
						.equals(cardPanel.getCardValue())) // 牌堆有牌且牌面即为牌堆下一个要插入的牌
			return true;
		else
			return false;
	}
}
