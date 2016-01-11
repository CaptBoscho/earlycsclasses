package client.MainFrame;

import javax.swing.JFrame;

import server.communicator.ClientCommunicator;
import shared.communication.ValidateUserInput;
import shared.communication.getProjectsOutput;

public class MainFrameParent extends JFrame{

	public MainFrameParent(ClientCommunicator cc, ValidateUserInput p, getProjectsOutput plist, LoginFrame parent)
	{
		new MainFrame(this, cc, p, plist, parent);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}