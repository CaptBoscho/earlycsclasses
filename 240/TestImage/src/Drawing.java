

import java.awt.*;

public class Drawing {

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			
			private DrawingFrame frame1;
			private DrawingFrame frame2;
			
			public void run() {
				frame1 = new DrawingFrame();
				frame2 = new DrawingFrame();
				
				frame1.setLocation(100, 100);
				frame2.setLocation(700, 100);
				
				frame1.setVisible(true);
				frame2.setVisible(true);
			}
	
		});

	}

}