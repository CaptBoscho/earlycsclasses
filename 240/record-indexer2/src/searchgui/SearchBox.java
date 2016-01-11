package searchgui;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import server.communicator.ClientCommunicator;
import shared.communication.ValidateUserInput;
import shared.communication.ValidateUserOutput;
import shared.communication.getProjectsOutput;

public class SearchBox extends JFrame{

	public ResultsPanel results = new ResultsPanel();
	public JSplitPane split;
	
	public SearchBox(getProjectsOutput plist, ClientCommunicator cc, ValidateUserInput person)
	{
		super("Search");
		setSize(500,500);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		SelectionPanel select = new SelectionPanel(plist, cc, person, this);
		//ResultsPanel results = select.getResults();
		split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, select, results);
		//split.setDividerLocation(0);
		add(split);
		
		//results.addResults("test");
		
		
		setVisible(true);
	}
	
	public void setFrame(ResultsPanel rp)
	{
		split.setRightComponent(rp);
	}
	
	public void updateResults(ArrayList<String> images)
	{
		results.addResults(images);
	}
}
