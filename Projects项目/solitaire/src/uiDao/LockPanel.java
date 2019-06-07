package uiDao;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class LockPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7496561886405987711L;
	private boolean valueLocked;

	public LockPanel(boolean valueLocked) {
		super();
		// TODO Auto-generated constructor stub
		this.valueLocked = valueLocked;
	}

	public boolean isValueLocked() {
		return valueLocked;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(4.0f));
		g2.setColor(Color.decode("#cccccc"));
		g2.drawLine(0, 15, 10, 15);
		g2.drawLine(10, 15, 10, 30 + 2 + 15);
		g2.drawLine(10, 30 + 2 + 15, 0, 30 + 2 + 15);
		if (!valueLocked) {
			g2.setStroke(new BasicStroke(3.0f));
			g2.drawLine(4, 23, 16, 39);
			g2.drawLine(16, 23, 4, 39);
		}
	}

	protected void changeColor(boolean isLocked, boolean valueLocked) {
		this.valueLocked = valueLocked;
		Graphics2D g2;
		if (isLocked) {
			g2 = (Graphics2D) this.getGraphics();
			g2.setStroke(new BasicStroke(4.0f));
			if (!valueLocked)
				g2.setColor(Color.BLACK);
			else
				g2.setColor(Color.decode("#cccccc"));
		} else {
			g2 = (Graphics2D) this.getGraphics();
			g2.setStroke(new BasicStroke(4.0f));
			if (valueLocked)
				g2.setColor(Color.BLACK);
			else
				g2.setColor(Color.decode("#cccccc"));
		}
		g2.drawLine(0, 15, 10, 15);
		g2.drawLine(10, 15, 10, 30 + 2 + 15);
		g2.drawLine(10, 30 + 2 + 15, 0, 30 + 2 + 15);
		if (!valueLocked) {
			g2.setStroke(new BasicStroke(3.0f));
			g2.drawLine(4, 23, 16, 39);
			g2.drawLine(16, 23, 4, 39);
		}
	}

	protected void changeStyle(boolean valueLocked) {
		Graphics2D g2 = (Graphics2D) this.getGraphics();
		g2.clearRect(0, 0, getWidth(), getHeight());
		changeColor(true, valueLocked);
	}
}
