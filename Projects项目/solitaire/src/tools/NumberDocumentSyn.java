package tools;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import uiDao.LockPanel;

public class NumberDocumentSyn extends PlainDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField originTextField;
	private JTextField destTextField;
	private LockPanel lockPanel;
	private int originNum;
	private boolean first;
	private boolean isThis;
	private NumberDocumentSyn destDocumentSyn;
	private float receiveRate;

	public void setReceiveRate(float receiveRate) {
		this.receiveRate = receiveRate;
		System.out.println("receiveRate=" + receiveRate);
	}

	public NumberDocumentSyn(JTextField originTextField, JTextField destTextField, LockPanel lockPanel) {
		super();
		// TODO Auto-generated constructor stub
		this.originTextField = originTextField;
		this.destTextField = destTextField;
		this.lockPanel = lockPanel;
		this.first = true;
		this.isThis = true;
	}

	public void setDestDocumentSyn(NumberDocumentSyn destDocumentSyn) {
		this.destDocumentSyn = destDocumentSyn;
	}

	public void setThis(boolean isThis) {
		this.isThis = isThis;
	}

	public void setOriginNum(int originNum) {
		this.originNum = originNum;
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		// TODO Auto-generated method stub
		if (!isThis) {
			isThis = true;
			super.insertString(offs, str, a);
			return;
		}
		if (this.first) {
			this.originNum = Integer.valueOf(str);
			this.first = false;
		}
		System.out.println(offs + "\t" + str + "\t" + a);
		if (str == null)
			return;
		boolean isNumber = true;
		char[] s = str.toCharArray();
		// 过滤非数字
		for (int i = 0; i < s.length; i++) {
			if ((s[i] >= '0') && (s[i] <= '9'))
				continue;
			else {
				isNumber = false;
				break;
			}
		}
		if (isNumber) {
			// 输入的是数字
			super.insertString(offs, str, a);
			originNum = Integer.parseInt(originTextField.getText());
			System.out.println(originNum);
			if (lockPanel.isValueLocked()) {
				// 修改另一个栏中的数
				System.out.println("isValueLocked()");
				int resultNum = originNum;
				int otherNum;
				float rate = receiveRate;
				System.out.println("rate=" + rate);
				originNum = resultNum;
				otherNum = (int) (rate * resultNum);
				System.out.println("otherNum为:" + otherNum);
				destDocumentSyn.setThis(false);
				destTextField.setText(String.valueOf(otherNum));
			}
		} else {
			super.insertString(offs, "", a);
		}
	}

}
