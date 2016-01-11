package client;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TableFormPanels extends JPanel {
	
	public TableTab2 table;
	public FormTab form;
	BatchState bs;
	
	public TableFormPanels(BatchState batch)
	{
		bs = batch;
		setLayout(new BorderLayout());
		JTabbedPane tabs = new JTabbedPane();
		table = new TableTab2(bs);
		form = new FormTab(bs);
		
		JScrollPane scroll1 = new JScrollPane(table);
		scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		tabs.add("table", scroll1);
		tabs.add("form", form);
		
		tabs.addChangeListener(new ChangeListener()
		{

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				Cell c= bs.getSelectedCell();
				int r = c.record;
				form.fr.updateFR(r);
			}
			
		});
		
		add(tabs);
		
	}

}
