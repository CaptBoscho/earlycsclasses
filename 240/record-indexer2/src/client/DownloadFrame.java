package client;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class DownloadFrame extends JDialog{
	
	JLabel test = new JLabel("test");
	
	public DownloadFrame()
	{
		//super(op, "Indexer", true);
		setSize(200,400);
		setResizable(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		add(test);
		
		
		setVisible(true);
	}

}
