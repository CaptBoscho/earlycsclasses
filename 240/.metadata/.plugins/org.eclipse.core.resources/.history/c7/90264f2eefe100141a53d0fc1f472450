package client.Bottom;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;

public class FieldImagePanel extends JPanel {
	
	FieldTab field;
	ImageTab image;
	BatchState bs;
	
	public FieldImagePanel(BatchState batch)
	{
		bs = batch;
		setLayout(new BorderLayout());
		JTabbedPane tabs = new JTabbedPane();
		
		field = new FieldTab(bs);
		image = new ImageTab(bs);
		
		JScrollPane scroll1 = new JScrollPane(field);
		scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		tabs.add("field", scroll1);
		tabs.add("image", image);
		
		add(tabs);
		
	}

}
