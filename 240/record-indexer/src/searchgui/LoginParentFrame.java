package searchgui;

import javax.swing.JFrame;

public class LoginParentFrame extends JFrame{

	public LoginParentFrame()
	{
		new LoginFrame(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
