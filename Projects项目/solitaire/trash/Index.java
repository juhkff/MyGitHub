package trash;

import uiDao.DealStackPanel;
import uiDao.DealedStackPanel;
import uiDao.GatherCardPanel;

public class Index {

	private int[] contentPanelLocation = new int[4];
	private int[] dealStackPanelLocation = new int[4];
	private int[] dealedStackPanelLocation = new int[4];
	private int[][] gatherCardPanelsLocation = new int[4][4];

	public int[] getContentPanel() {
		contentPanelLocation[0] = contentPanel.getX() + this.getX();
		contentPanelLocation[1] = contentPanel.getY() + this.getY();
		contentPanelLocation[2] = contentPanelLocation[0] + contentPanel.getWidth();
		contentPanelLocation[3] = contentPanelLocation[1] + contentPanel.getHeight();
		return contentPanelLocation;
	}

	/**
	 * 获得dealStackPanel的左上与右下边界顶点的坐标,[0]-X,[1]-Y,[2]-X,[3]-Y
	 */
	public int[] getDealStackPanel(boolean isRelative) {
		if (isRelative) {
			dealStackPanelLocation[0] = dealStackPanel.getX();
			dealStackPanelLocation[1] = dealStackPanel.getY();
		} else {
			dealStackPanelLocation[0] = dealStackPanel.getX() + this.getX();
			dealStackPanelLocation[1] = dealStackPanel.getY() + this.getY();
		}
		dealStackPanelLocation[2] = dealStackPanelLocation[0] + dealStackPanel.getWidth();
		dealStackPanelLocation[3] = dealStackPanelLocation[1] + dealStackPanel.getHeight();
		return dealStackPanelLocation;
	}

	public int[] getDealedStackPanel(boolean isRelative) {
		if (isRelative) {
			dealedStackPanelLocation[0] = dealedStackPanel.getX();
			dealedStackPanelLocation[1] = dealedStackPanel.getY();
		} else {
			dealedStackPanelLocation[0] = dealStackPanel.getX() + this.getX();
			dealedStackPanelLocation[1] = dealStackPanel.getY() + this.getY();
		}
		dealedStackPanelLocation[2] = dealedStackPanelLocation[0] + dealedStackPanel.getWidth();
		dealedStackPanelLocation[3] = dealedStackPanelLocation[1] + dealedStackPanel.getHeight();
		return dealedStackPanelLocation;
	}

	public int[][] getGatherCardPanels(boolean isRelative) {
		if (isRelative) {
			for (int i = 0; i < 4; i++) {
				gatherCardPanelsLocation[i][0] = gatherCardPanels[i].getX();
				gatherCardPanelsLocation[i][1] = gatherCardPanels[i].getY();
				gatherCardPanelsLocation[i][2] = gatherCardPanelsLocation[i][0] + gatherCardPanels[i].getWidth();
				gatherCardPanelsLocation[i][3] = gatherCardPanelsLocation[i][1] + gatherCardPanels[i].getHeight();
			}
		} else {
			for (int i = 0; i < 4; i++) {
				gatherCardPanelsLocation[i][0] = gatherCardPanels[i].getX() + this.getX();
				gatherCardPanelsLocation[i][1] = gatherCardPanels[i].getY() + this.getY();
				gatherCardPanelsLocation[i][2] = gatherCardPanelsLocation[i][0] + gatherCardPanels[i].getWidth();
				gatherCardPanelsLocation[i][3] = gatherCardPanelsLocation[i][1] + gatherCardPanels[i].getHeight();
			}
		}
		return gatherCardPanelsLocation;
	}

	public DealStackPanel getDealStackPanel() {
		return dealStackPanel;
	}

	public DealedStackPanel getDealedStackPanel() {
		return dealedStackPanel;
	}

	public GatherCardPanel getGatherCardPanels(int index) {
		return gatherCardPanels[index];
	}
}
