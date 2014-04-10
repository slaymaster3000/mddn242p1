package gui;

import gui.toolbars.CanvasSettings;
import gui.toolbars.Properties;
import gui.toolbars.TimeLine;
import gui.toolbars.ToolBar;
import gui.toolbars.ToolBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -4580460675035694517L;

	private static final Color BG_COLOR = new Color(40, 40, 40);

	private MenuBar menu;
	private Canvas canvas;

	// tool bars
	private CanvasSettings canvasSettings;
	private ToolBox toolbox;
	private Properties properties;
	private TimeLine timeLine;

	/**
	 * Create this (the window)
	 */
	public MainWindow(){
		setTitle("Particle Creator");
		setSize(1280, 900);
		setMinimumSize(new Dimension(1024, 768));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		JPanel content = new JPanel(new BorderLayout(-1, -1), true);
		content.setBackground(BG_COLOR);
		setContentPane(content);

		createMenuBar();
		createCanvas();
		createCanvasSettings();
		createToolBox();
		craeteProperties();
		createTimeLine();

		canvas.init();					// start the animation thread (and other such stuff)
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);	// maximize the window
		setLocationRelativeTo(null);	// center the window on the screen
		setVisible(true);				// display the window
	}
	
	public boolean dock(ToolBar toolBar, int mouseX, int mouseY) {
		String dockPosition = getDockingLocation(mouseX, mouseY);
		
		if(dockPosition == null) return false;
		dock(toolBar, dockPosition);
		return true;
	}
	
	public void dock(ToolBar toolBar, String position) {
		getContentPane().add(toolBar, position);
		revalidate();
		repaint();
	}
	
	private String getDockingLocation(int mouseX, int mouseY){
		final int dist = 24;
		
		Insets insets = getInsets();
		Point windowLocation = getLocationOnScreen();
		Dimension windowSize = getSize();
		if(mouseX > windowLocation.x + insets.left
		&& mouseX < windowLocation.x + insets.left + dist){
			return BorderLayout.WEST;
		} else
		if(mouseX < windowLocation.x + windowSize.width - insets.right
		&& mouseX > windowLocation.x + windowSize.width - insets.right - dist){
			return BorderLayout.EAST;
		} else
		if(mouseY > windowLocation.y + insets.top + menu.getHeight()
		&& mouseY < windowLocation.y + insets.top + menu.getHeight() + dist){
			return BorderLayout.NORTH;
		} else
		if(mouseY < windowLocation.y + windowSize.height - insets.bottom
		&& mouseY > windowLocation.y + windowSize.height - insets.bottom - dist){
			return BorderLayout.SOUTH;
		}
		return null;
	}

	private void createMenuBar() {
		setJMenuBar(menu = new MenuBar());
	}

	/**
	 * Create the canvas
	 */
	private void createCanvas(){
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		canvas = new Canvas(640, 480);
		panel.add(canvas);
		getContentPane().add(panel, BorderLayout.CENTER);
	}

	private void createCanvasSettings() {
		canvasSettings = new CanvasSettings(this);
		getContentPane().add(canvasSettings, BorderLayout.NORTH);
	}

	/**
	 * Create the tool box
	 */
	private void createToolBox(){
		toolbox = new ToolBox(this);
		getContentPane().add(toolbox, BorderLayout.WEST);
	}

	private void craeteProperties() {
		properties = new Properties(this);
		getContentPane().add(properties, BorderLayout.EAST);
	}

	private void createTimeLine() {
		timeLine = new TimeLine(this);
		getContentPane().add(timeLine, BorderLayout.SOUTH);
	}
}
