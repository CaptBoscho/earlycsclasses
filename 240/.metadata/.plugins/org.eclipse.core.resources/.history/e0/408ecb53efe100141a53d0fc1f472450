package client.BottomLeft;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

public class FieldTab extends JPanel{
	
	BatchState bs;
	JEditorPane jep;
	JScrollPane scrollPane;
	
	public FieldTab(BatchState batch)
	{
		setLayout(new BorderLayout());
		bs = batch;
		jep = new JEditorPane();
	    jep.setEditable(false);  
	    HTMLEditorKit kit = new HTMLEditorKit();
	    jep.setEditorKit(kit);
	    scrollPane = new JScrollPane(jep);
	    add(scrollPane, BorderLayout.CENTER);
	}
	
	public void setPage(String url)
	{
		remove(scrollPane);
		try {
			jep.setPage(url);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scrollPane = new JScrollPane(jep);
		add(scrollPane, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

}
