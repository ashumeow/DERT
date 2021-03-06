package gov.nasa.arc.dert.action;

import gov.nasa.arc.dert.icon.Icons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * Helper class for building UI buttons.
 *
 */
public abstract class ButtonAction extends JButton {
	
	protected boolean selected;

	public ButtonAction(String toolTipText, String label, String iconFileName) {
		this(toolTipText, label, iconFileName, false);
	}

	public ButtonAction(String toolTipText, String label, String iconFileName, boolean border) {
		super(label);
		if (iconFileName == null) {
			border = true;
		}
		if (!border) {
			setBorder(BorderFactory.createEmptyBorder());
		}
		setBorderPainted(border);
		setToolTipText(toolTipText);
		setIcon(Icons.getImageIcon(iconFileName));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				run();
			}
		});
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	protected abstract void run();
}
