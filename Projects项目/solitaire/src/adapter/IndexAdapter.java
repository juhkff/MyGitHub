package adapter;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import element.StaticData;
import uiDao.GamePage;

public class IndexAdapter extends ComponentAdapter {
	private GamePage jf;

	public IndexAdapter(GamePage jf) {
		super();
		this.jf = jf;
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		super.componentResized(e);
		System.out.println("触发");
		GamePage frame = (GamePage) e.getComponent();
		int newHeight = frame.getHeight();
		int newWidth = frame.getWidth();
		StaticData.setFRAMEHEIGHT(newHeight);
		StaticData.setFRAMEWIDTH(newWidth);
		System.out.println("新的宽高:" + StaticData.getFRAMEWIDTH() + "\t" + StaticData.getFRAMEHEIGHT());
		StaticData.reDefine();
		jf.reSize();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		super.componentMoved(e);
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		super.componentShown(e);
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		super.componentHidden(e);
	}

}
