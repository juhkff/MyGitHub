package adapter;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import element.StaticData;
import uiPaint.Index;

public class IndexAdapter extends ComponentAdapter {

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		super.componentResized(e);
		System.out.println("����");
		Index frame = (Index) e.getComponent();
		int newHeight = frame.getHeight();
		int newWidth = frame.getWidth();
		StaticData.setFRAMEHEIGHT(newHeight);
		StaticData.setFRAMEWIDTH(newWidth);
		System.out.println("�µĿ��:" + StaticData.getFRAMEWIDTH() + "\t" + StaticData.getFRAMEHEIGHT());
		StaticData.reDefine();
		Index.reSize();
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
