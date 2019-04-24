package tools;

import element.StaticData;
import uiDao.CardPanel;

public class FindComponent {
	/**
	 * ���ݿ��Ƶ����ĵ�Ѱ�Ҹô����ڵ����
	 * 
	 * @param cardPanel-����
	 * @return �����������Name
	 */
	public static String findComponentByCenterPoint(CardPanel cardPanel) {
		int x = cardPanel.getX() + cardPanel.getWidth() / 2;
		int y = cardPanel.getY() + cardPanel.getHeight() / 2;
		if (StaticData.getGathercardlocation(1) < y
				&& y < StaticData.getGathercardlocation(1) + StaticData.getGathercardlocation(3)) {
			// ֻ�����ڶ������ƶ���
			if (StaticData.getGathercardlocation(0) < x
					&& x < StaticData.getGathercardlocation(0) + StaticData.getGathercardlocation(2))
				return "gatherCardPanel1";
			else if (StaticData.getGathercardlocation(0) + StaticData.getGathercardlocation(4) * 1 < x
					&& x < StaticData.getGathercardlocation(0) + StaticData.getGathercardlocation(2)
							+ StaticData.getGathercardlocation(4) * 1)
				return "gatherCardPanel2";
			else if (StaticData.getGathercardlocation(0) + StaticData.getGathercardlocation(4) * 2 < x
					&& x < StaticData.getGathercardlocation(0) + StaticData.getGathercardlocation(2)
							+ StaticData.getGathercardlocation(4) * 2)
				return "gatherCardPanel3";
			else if (StaticData.getGathercardlocation(0) + StaticData.getGathercardlocation(4) * 3 < x
					&& x < StaticData.getGathercardlocation(0) + StaticData.getGathercardlocation(2)
							+ StaticData.getGathercardlocation(4) * 3) {
				return "gatherCardPanel4";
			}
		}
		return null;
	}
}
