package client;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class BottomPanel extends JPanel{
	
	BatchState bs;
	JSplitPane split;
	public TableFormPanels tf;
	public FieldImagePanel fi;
	
	public BottomPanel(BatchState batch)
	{
		bs = batch;
		
		tf = new TableFormPanels(batch);
		fi = new FieldImagePanel(batch);
		setLayout(new BorderLayout());
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,tf , fi);
		
		add(split);
	}
	
	public void After()
	{
		this.remove(split);
	}
	
	public void ReMake()
	{
		tf = new TableFormPanels(bs);
		fi = new FieldImagePanel(bs);
		setLayout(new BorderLayout());
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,tf , fi);
		
		add(split);
	}

}
