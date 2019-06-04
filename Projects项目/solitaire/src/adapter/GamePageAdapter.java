package adapter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import element.StaticData;
import tools.SolitaireCheck;
import uiDao.GamePage;

public class GamePageAdapter extends MouseAdapter {
	private GamePage jf;

	public GamePageAdapter(GamePage jf) {
		// TODO Auto-generated constructor stub
		this.jf = jf;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseClicked(e);
		if (e.getButton() != MouseEvent.BUTTON2) {
			return;
		} else {
			if (StaticData.isCardAutoCheck()) {
				System.out.println("´¥·¢ÖÐ¼ü!");
				SolitaireCheck.autoCheck(jf);
			}
		}
	}

}
