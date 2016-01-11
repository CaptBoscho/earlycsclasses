package client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import shared.model.field;




public class ImageComponent extends JComponent{
	
	private static Image NULL_IMAGE = new BufferedImage(10,10, BufferedImage.TYPE_INT_ARGB);
	
	private int w_translateX = 0;
	private int w_translateY;
	private double scale;
	
	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartTranslateX;
	private int w_dragStartTranslateY;
	private AffineTransform dragTransform;
	
	private ArrayList<DrawingShape> shapes;
	private Font font;
	Image file;
	DrawingRect rect;
	
	BatchState bs;
	
	public ImageComponent(String url, int rh, int firsty, int firstx, int xwidth, BatchState batch, RescaleOp op)
	{
		w_translateX = 0;
		w_translateY = 0;
		scale = 1.0;
		
		bs = batch;
		
		shapes = new ArrayList<DrawingShape>();
		
		initDrag();
		
		this.setBackground(new Color(178, 223, 210));
		//this.setPreferredSize(new Dimension(700, 700));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(1000, 1000));
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		this.addComponentListener(componentAdapter);
		
		file = loadImage(url);
		BufferedImage img = (BufferedImage) file;
		BufferedImage negative = op.filter(img, null);
		//System.out.println(negative);
		file = negative;
		
		DrawingImage temp = new DrawingImage(file, new Rectangle2D.Double(0, 0, file.getWidth(null), file.getHeight(null)));
		
		shapes.add(temp);
		
		rect = new DrawingRect(new Rectangle2D.Double(firstx, firsty, xwidth, rh), new Color(210, 180, 140, 192));
		
		shapes.add(rect);
		//shapes.add(new DrawingLine(new Line2D.Double(400, 400, 600, 600), new Color(255, 0, 0, 64)));
		
		//createTextShapes();
	}
	
	public ImageComponent(String url, int rh, int firsty, int firstx, int xwidth, BatchState batch)
	{
		w_translateX = 0;
		w_translateY = 0;
		scale = 1.0;
		
		bs = batch;
		
		shapes = new ArrayList<DrawingShape>();
		
		initDrag();
		
		this.setBackground(new Color(178, 223, 210));
		//this.setPreferredSize(new Dimension(700, 700));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(1000, 1000));
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		this.addComponentListener(componentAdapter);
		
		file = loadImage(url);
		
		DrawingImage temp = new DrawingImage(file, new Rectangle2D.Double(0, 0, file.getWidth(null), file.getHeight(null)));
		
		shapes.add(temp);
		
		rect = new DrawingRect(new Rectangle2D.Double(firstx, firsty, xwidth, rh), new Color(210, 180, 140, 192));
		
		shapes.add(rect);
		//shapes.add(new DrawingLine(new Line2D.Double(400, 400, 600, 600), new Color(255, 0, 0, 64)));
		
		//createTextShapes();
	}

	private void initDrag() {
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartTranslateX = 0;
		w_dragStartTranslateY = 0;
		dragTransform = null;
	}
	
	public void updateHighlight(int x, int y, int w, int h)
	{
		rect.setRect(x, y, w, h);
		repaint();
	}
	
	/*private void createTextShapes() {
		String text1 = "Width: " + this.getWidth();
		shapes.add(new DrawingText(text1, Color.BLACK, new Point2D.Float(200, 200)));
		
		String text2 = "Height: " + this.getHeight();
		shapes.add(new DrawingText(text2, Color.BLACK, new Point2D.Float(200, 400)));
	}*/
	
	private void updateTextShapes() {
		for (DrawingShape shape : shapes) {
			if (shape instanceof DrawingText) {
				DrawingText textShape = (DrawingText)shape;
				if (textShape.getText().startsWith("Width:")) {
					textShape.setText("Width: " + this.getWidth());
				}
				else if (textShape.getText().startsWith("Height:")) {
					textShape.setText("Height: " + this.getHeight());
				}
			}
		}
	}
	
	public double getScale()
	{
		return scale;
	}
	
	private Image loadImage(String imageFile) {
		try {
			return ImageIO.read(new URL(imageFile));
		}
		catch (IOException e) {
			return NULL_IMAGE;
		}
	}
	
	public void setScale(double newScale) {
		scale = newScale;
		bs.setScale(scale);
		this.repaint();
	}
	
	public void setTranslation(int w_newTranslateX, int w_newTranslateY) {
		w_translateX = w_newTranslateX;
		w_translateY = w_newTranslateY;
		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		//drawBackground(g2);
		g2.translate(getWidth()/2, getHeight()/2);
		g2.scale(scale, scale);
		//g2.translate(- file.getWidth(null) / 2.0 + w_translateX, - file.getHeight(null) / 2.0 + w_translateY);
		g2.translate(w_translateX, w_translateY);

		drawShapes(g2);
	}
	
	/*private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0,  0, getWidth(), getHeight());
	}*/

	private void drawShapes(Graphics2D g2) {
		for (DrawingShape shape : shapes) {
			shape.draw(g2);
		}
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {

		public void mouseClicked(MouseEvent e)
		{
			int d_X = e.getX();
			int d_Y = e.getY();
			
			//System.out.println("dx: " + d_X + " dy: " + d_Y);
			
			if(dragTransform == null)
			{
				dragTransform = new AffineTransform();
			}
			
			dragTransform.translate(getWidth()/2, getHeight()/2);
			dragTransform.scale(scale, scale);
			dragTransform.translate(w_translateX, w_translateY);
			//transform.translate(w_translateX, w_translateY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				dragTransform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			//System.out.println("wx: " + w_X + " wy: " + w_Y);
			
			boolean hitShape = false;
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			for (DrawingShape shape : shapes) {
				if (shape.contains(g2, w_X, w_Y)) {
					hitShape = true;
					break;
				}
			}
			
			if (hitShape) {
				dragging = true;		
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartTranslateX = w_translateX;
				w_dragStartTranslateY = w_translateY;
				//dragTransform = transform;
			}
			
			bs.determineCell(w_X, w_Y);
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			int d_X = e.getX();
			int d_Y = e.getY();
			
			AffineTransform transform = new AffineTransform();
			transform.translate(getWidth()/2, getHeight()/2);
			transform.scale(scale, scale);
			transform.translate(w_translateX, w_translateY);
			//transform.translate(w_translateX, w_translateY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			boolean hitShape = false;
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			for (DrawingShape shape : shapes) {
				if (shape.contains(g2, w_X, w_Y)) {
					hitShape = true;
					break;
				}
			}
			
			if (hitShape) {
				dragging = true;		
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartTranslateX = w_translateX;
				w_dragStartTranslateY = w_translateY;
				dragTransform = transform;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {		
			if (dragging) {
				int d_X = e.getX();
				int d_Y = e.getY();
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try
				{
					dragTransform.inverseTransform(d_Pt, w_Pt);
				}
				catch (NoninvertibleTransformException ex) {
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				int w_deltaX = w_X - w_dragStartX;
				int w_deltaY = w_Y - w_dragStartY;
				
				w_translateX = w_dragStartTranslateX + w_deltaX;
				w_translateY = w_dragStartTranslateY + w_deltaY;
				
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
						
			if(e.getWheelRotation() < 0)
			{
				setScale(scale * 1.25);
			}
			
			if(e.getWheelRotation() > 0)
			{
				setScale(scale * 0.75);
			}
			
		}	
	};
	
	private ComponentAdapter componentAdapter = new ComponentAdapter() {

		@Override
		public void componentHidden(ComponentEvent e) {
			return;
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			return;
		}

		@Override
		public void componentResized(ComponentEvent e) {
			updateTextShapes();
		}

		@Override
		public void componentShown(ComponentEvent e) {
			return;
		}	
	};

	
	/////////////////
	// Drawing Shape
	/////////////////
	
	
	interface DrawingShape {
		boolean contains(Graphics2D g2, double x, double y);
		void draw(Graphics2D g2);
		Rectangle2D getBounds(Graphics2D g2);
	}


	class DrawingRect implements DrawingShape {

		private Rectangle2D rect;
		private Color color;
		
		public DrawingRect(Rectangle2D rect, Color color) {
			this.rect = rect;
			this.color = color;
		}
		
		public void setRect(int x, int y, int w, int h)
		{
			rect.setRect(x, y, w, h);
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.setColor(color);
			g2.fill(rect);
		}
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
	}


	class DrawingLine implements DrawingShape {

		private Line2D line;
		private Color color;
		
		public DrawingLine(Line2D rect, Color color) {
			this.line = rect;
			this.color = color;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {

			final double TOLERANCE = 5.0;
			
			Point2D p1 = line.getP1();
			Point2D p2 = line.getP2();
			Point2D p3 = new Point2D.Double(x, y);
			
			double numerator = (p3.getX() - p1.getX()) * (p2.getX() - p1.getX()) + (p3.getY() - p1.getY()) * (p2.getY() - p1.getY());
			double denominator =  p2.distance(p1) * p2.distance(p1);
			double u = numerator / denominator;
			
			if (u >= 0.0 && u <= 1.0) {
				Point2D pIntersection = new Point2D.Double(	p1.getX() + u * (p2.getX() - p1.getX()),
															p1.getY() + u * (p2.getY() - p1.getY()));
				
				double distance = pIntersection.distance(p3);
				
				return (distance <= TOLERANCE);
			}
			
			return false;
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.setColor(color);
			//g2.setStroke(stroke);
			g2.draw(line);
		}	
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return line.getBounds2D();
		}
	}


	class DrawingImage implements DrawingShape {

		private Image image;
		private Rectangle2D rect;
		
		public DrawingImage(Image image, Rectangle2D rect) {
			this.image = image;
			this.rect = rect;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.drawImage(image, (int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(),
							0, 0, image.getWidth(null), image.getHeight(null), null);
		}	
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
	}


	class DrawingText implements DrawingShape {

		private String text;
		private Color color;
		private Point2D location;
		
		public DrawingText(String text, Color color, Point2D location) {
			this.text = text;
			this.color = color;
			this.location = location;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			Rectangle2D bounds = getBounds(g2);
			return bounds.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.setColor(color);
			g2.setFont(font);
			g2.drawString(text, (int)location.getX(), (int)location.getY());
		}
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			FontRenderContext context = g2.getFontRenderContext();
			Rectangle2D bounds = font.getStringBounds(text, context);
			bounds.setRect(location.getX(), location.getY() + bounds.getY(), 
							bounds.getWidth(), bounds.getHeight());
			return bounds;
		}
		
		public String getText() {
			return text;
		}
		
		public void setText(String value) {
			text = value;
		}
	}


}
