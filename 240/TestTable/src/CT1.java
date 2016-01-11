
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;


@SuppressWarnings("serial")
public class ColorTable extends JFrame {

	private ArrayList<ColorScheme> colorSchemes;
	private ColorTableModel tableModel;
	private JTable table;
	
	public ColorTable(String title) throws HeadlessException {
		super(title);

		colorSchemes = generateSchemes();
		
		tableModel = new ColorTableModel(colorSchemes);
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		
		TableColumnModel columnModel = table.getColumnModel();
		for (int i = 0; i < tableModel.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(150);
		}	
		
		JPanel rootPanel = new JPanel(new BorderLayout());
		rootPanel.add(table.getTableHeader(), BorderLayout.NORTH);
		rootPanel.add(table, BorderLayout.CENTER);
		
		this.add(rootPanel);
	}
	
	private ArrayList<ColorScheme> generateSchemes() {
		
		final int NUM_SCHEMES = 20;
		
		ArrayList<ColorScheme> result = new ArrayList<ColorScheme>();
		Random rand = new Random();
		
		for (int i = 1; i <= NUM_SCHEMES; ++i) {
			
			ColorScheme scheme = new ColorScheme("Scheme " + i,
													generateColor(rand),
													generateColor(rand),
													generateColor(rand),
													generateColor(rand));
			result.add(scheme);
		}
		
		return result;
	}
	
	private Color generateColor(Random rand) {
		
		int r = rand.nextInt(256);
		int g = rand.nextInt(256);
		int b = rand.nextInt(256);
		
		return new Color(r, g, b);
	}
	
	public static void main(String[] args) {
    	
        EventQueue.invokeLater(new Runnable()
        {
           public void run()
           {
        	   ColorTable frame = new ColorTable("Color Table");
               frame.pack();
               frame.setVisible(true);
           }
        });
	}

}
