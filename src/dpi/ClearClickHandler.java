package dpi;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClearClickHandler implements MouseListener {

	public void mouseClicked(MouseEvent arg0) {
		DPIDesktopApplication.setConditionBoxValue("");
		DPIDesktopApplication.setExactTextBoxValue("");
		DPIDesktopApplication.setMaxTextBoxValue("");
		DPIDesktopApplication.setMinTextBoxValue("");
		DPIDesktopApplication.setTechniqueBoxValue("");
		DPIDesktopApplication.setUnitBoxValue("");
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

}
