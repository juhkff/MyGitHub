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
				&& StaticData.getGathervaluemap(gatherCardPanel.getTop(false).getStackNode().getCardValue()) != null
				&& StaticData.getGathervaluemap(gatherCardPanel.getTop(false).getStackNode().getCardValue())
						.equals(cardPanel.getCardValue())) // 牌堆有牌且牌面即为牌堆下一个要插入的牌
			return true;
		else
			return false;
	}

	/**
	 * 检验cardPanel是否能放入SevenStackPanel.sevenStackIndex最小值为1.注意CardPanel本身可能只是节点的尾部.使用此函数的前提是已知待检验卡堆有牌
	 * 
	 * @param sevenStackIndex-待放入的牌堆
	 * @param cardPanel-待检验的卡牌
	 * @return true-可放入;false-不可放入
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
	 * 鼠标中键触发自动检查
	 * 
	 * @param jf
	 * @return autoCheckCardNum
	 */
	public static int autoCheck(GamePage jf) {
		int autoCheckCardNum = 0;
		int checkTime = StaticData.getCheckTime();
		for (int i = 0; i < checkTime; i++) {
			// 翻牌堆判断
			CardStackNode dTop = jf.getDealedStackPanel().getTop(false); // 取出顶部的牌
			if (dTop != null && dTop.getStackNode() != null) {
				CardPanel cardPanel = dTop.getStackNode();
				System.out.println("Yes");
				for (int stackIndex = 1; stackIndex < StaticData.getGathernum() + 1; stackIndex++) {
					if (SolitaireCheck.canPushToGatherStack(jf.getGatherCardPanels()[stackIndex - 1], cardPanel)) {
						// 能放入
						System.out.println("能放入");
						jf.getDealedStackPanel().getTop(); // 取出顶部的牌
						jf.getGatherCardPanels()[stackIndex - 1].setTop(new CardStackNode(cardPanel));
						jf.getDealedStackPanel().repaint();
						jf.getGatherCardPanels()[stackIndex - 1].repaint();
						jf.setHasClicked(false);
						// cardPanel.repaint();
						autoCheckCardNum++;
						break;
					} else {
						// 不能放入
						System.out.println("不能放入");
						jf.setHasClicked(false);
					}
				}
				jf.setClickComponentName(null);
				jf.getGameFoot().reset();
			}
			// 底部牌堆判断
			for (int j = 1; j < StaticData.getSevenstacknum() + 1; j++) {
				CardStackNode top = jf.getTop(j);
				if (top != null && top.getStackNode() != null) {
					CardPanel cardPanel = top.getStackNode();
					for (int k = 0; k < StaticData.getGathernum(); k++) {
						if (SolitaireCheck.canPushToGatherStack(jf.getGatherCardPanels()[k], cardPanel)) {
							// 能放入
							System.out.println("能放入");
							// 去除原堆
							CardPanel cur = top.getStackNode();
							jf.setTop(j, top.getNextNode());
							jf.getSevenStackPanels()[j - 1].remove(cur);
							jf.getSevenStackPanels()[j - 1].cardNum--;
							// 加入该堆顶部
							cardPanel.setLocation(0, 0);
							cardPanel.removeMouseListener(jf.getSevenStackCardPanelAdapters()[j - 1]);
							cardPanel.removeMouseMotionListener(jf.getSevenStackCardPanelAdapters()[j - 1]);
							jf.getGatherCardPanels()[k].setTop(new CardStackNode(cardPanel));
							jf.getSevenStackPanels()[j - 1].resetHeightAfterDelete(1);
							jf.getSevenStackPanels()[j - 1].repaint();
							jf.getGatherCardPanels()[k].repaint();
							if (jf.getSevenStackPanels()[j - 1].getCardNum() == 0) {
								// 设置底纹边框
								jf.getSevenStackPanels()[j - 1]
										.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
							} else {
								// 去除边框(视觉上其实没啥用)
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
