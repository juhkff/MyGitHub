package tools;

import element.StaticData;
import uiDao.CardPanel;

public class FindComponent {
	/**
	 * 根据卡牌的中心点寻找该处存在的组件
	 * 
	 * @param cardPanel-卡牌
	 * @return 所存在组件的Name
	 */
	public static String findComponentByCenterPoint(CardPanel cardPanel) {
		int x = cardPanel.getX() + cardPanel.getWidth() / 2;
		int y = cardPanel.getY() + cardPanel.getHeight() / 2;
		System.out.println("坐标:" + x + "\t" + y);
		if (StaticData.getGathercardlocation(1) < y
				&& y < StaticData.getGathercardlocation(1) + StaticData.getGathercardlocation(3)) {
			// 只可能在顶部四牌堆中
			if (StaticData.getGathercardlocation(0) < x
					&& x < StaticData.getGathercardlocation(0) + StaticData.getGathercardlocation(2))
				return "gatherCardPanel1";
			else if (StaticData.getGathercardlocation(0)
					+ (StaticData.getGathercardlocation(2) + StaticData.getGathercardlocation(4)) * 1 < x
					&& x < StaticData.getGathercardlocation(0) + StaticData.getGathercardlocation(2) * 2
							+ StaticData.getGathercardlocation(4) * 1)
				return "gatherCardPanel2";
			else if (StaticData.getGathercardlocation(0)
					+ (StaticData.getGathercardlocation(2) + StaticData.getGathercardlocation(4)) * 2 < x
					&& x < StaticData.getGathercardlocation(0) + StaticData.getGathercardlocation(2) * 3
							+ StaticData.getGathercardlocation(4) * 2)
				return "gatherCardPanel3";
			else if (StaticData.getGathercardlocation(0)
					+ (StaticData.getGathercardlocation(2) + StaticData.getGathercardlocation(4)) * 3 < x
					&& x < StaticData.getGathercardlocation(0) + StaticData.getGathercardlocation(2) * 4
							+ StaticData.getGathercardlocation(4) * 3) {
				return "gatherCardPanel4";
			}
		} else if (StaticData.getSevenstacklocation(1) < y && StaticData.getSevenstacklocation(0) < x
				&& x < (StaticData.getCardsize(2) + StaticData.getSevenstacklocation(0))
						* StaticData.getSevenstacknum()) {
			// 只可能在下部(七个)牌堆中
			int oneWidth = StaticData.getSevenstacklocation(0) + StaticData.getCardsize(2);
			int length = StaticData.getSevenstacklocation(0);
			for (int i = 0; i < StaticData.getSevenstacknum(); i++) {
				if (length + oneWidth * i < x && x < oneWidth * (i + 1))
					return ("sevenStackPanel" + (i + 1));
			}
		}
		return null;
	}
}
