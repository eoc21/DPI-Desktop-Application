package dpi;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClearClickHandler implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent arg0) {
		DPIDesktopApplication.setConditionBoxValue("");
		DPIDesktopApplication.setExactTextBoxValue("");
		DPIDesktopApplication.setMaxTextBoxValue("");
		DPIDesktopApplication.setMinTextBoxValue("");
		DPIDesktopApplication.setTechniqueBoxValue("");
		DPIDesktopApplication.setUnitBoxValue("");
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
