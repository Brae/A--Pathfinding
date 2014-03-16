package com.casegeek.test.astar;

import java.awt.*;
import java.applet.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.border.*;
import SECommon.*;

/**
Interface for the Room & Robot sim
*/

public class DrawFrame extends JFrame {

	public DrawFrame (Picture picture) {
		super("Robby 2D");
		this.picture = picture;
		this.activeColor = Room.BLUE;

		//code to exit the application gracefully
		this.addWindowListener(new ShutDownGUI());

		this.initComponents();
		this.setResizable(false);
		this.setIconImage(new ImageIcon(DrawFrame.class.getResource("media/blueI.gif")).getImage());
		this.getContentPane().add(picture);
		this.pack();
		SeCommTool.centerOnScreen(this);
	}

	public void showStatus (final String status) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				statusLabel.setText(" " + status);
			}
		});
	}

	public void postPos(final int x, final int y) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				posLabel.setText("[" + x + "," + y + "]");
			}
		});
	}

	//displays the app window... starts busy wait :-(
    protected void display() {
		this.setVisible(true);
  		while (!start) {
	  		picture.delay(500);
	  	}
	}

	private void initComponents() {

		Action whiteAction	= new ColorAction("White",	new ImageIcon(DrawFrame.class.getResource("media/whiteI.gif")),	Room.WHITE);
		Action blackAction	= new ColorAction("Black",	new ImageIcon(DrawFrame.class.getResource("media/blackI.gif")),	Room.BLACK);
		Action blueAction	= new ColorAction("Blue",	new ImageIcon(DrawFrame.class.getResource("media/blueI.gif")),	Room.BLUE);
		Action yellowAction	= new ColorAction("Yellow",	new ImageIcon(DrawFrame.class.getResource("media/yellowI.gif")),Room.YELLOW);
		Action greenAction	= new ColorAction("Green",	new ImageIcon(DrawFrame.class.getResource("media/greenI.gif")),	Room.GREEN);

		Action quitAction = new SE11xAction("Quit",
											new ImageIcon(DrawFrame.class.getResource("media/quitI.gif")),
											new ImageIcon(DrawFrame.class.getResource("media/quit.gif")),
											"Exit the application",
											KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK)
											) {
			public void actionPerformed (ActionEvent e) {
				System.exit(0);
			}
		};

		startAction = new SE11xAction(	"Start",
										new ImageIcon(DrawFrame.class.getResource("media/startI.gif")),
										new ImageIcon(DrawFrame.class.getResource("media/start.gif")),
										"Start rendering",
										KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK)
										) {
			public void actionPerformed (ActionEvent e) {
				start = true;
				DrawFrame.this.showStatus("Running...");

				//blank out various GUI components that shouldn't be accessible after main thread resumes
				picture.removeMouseListener(pListen);
				startButton.setEnabled(false);
				startAction.setEnabled(false);
				setColorsMenu.setEnabled(false);
			}
		};

		Action tutorialAction = new SE11xAction("The Java Tutorial",
										new ImageIcon(DrawFrame.class.getResource("media/helpI.gif")),
										new ImageIcon(DrawFrame.class.getResource("media/help.gif")),
										"Sun's Java Tutorial website",
										KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK)
											) {
			public void actionPerformed (ActionEvent e) {
				BrowserControl.displayURL("http://java.sun.com/docs/books/tutorial/");
			}
		};

		//create the popupMenu
		popup = new JPopupMenu();
		pListen = new Picker(popup);
	    picture.addMouseListener(pListen);
			popup.add(whiteAction);
			popup.add(blackAction);
			popup.add(blueAction);
			popup.add(yellowAction);
			popup.add(greenAction);

		//create the toolbar
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		this.getContentPane().add(toolbar, BorderLayout.NORTH);
			startButton = new ToolBarButton(startAction);
			toolbar.add(startButton);
			toolbar.addSeparator();
			createStatusBar(toolbar);

		//create the menubar
		JMenuBar mBar = new JMenuBar();
		this.setJMenuBar(mBar);
			JMenu fileMenu = new JMenu("File");
			fileMenu.setMnemonic(KeyEvent.VK_F);
			mBar.add(fileMenu);
				fileMenu.add(SeCommTool.createJMenuItem(startAction));
				fileMenu.addSeparator();
				fileMenu.add(SeCommTool.createJMenuItem(quitAction));

			JMenu setMenu = new JMenu("Settings");
			setMenu.setMnemonic(KeyEvent.VK_S);
			mBar.add(setMenu);
				setColorsMenu = new JMenu("Set Color");
				setMenu.add(setColorsMenu);
					setColorsMenu.add(whiteAction);
					setColorsMenu.add(blackAction);
					setColorsMenu.add(blueAction);
					setColorsMenu.add(yellowAction);
					setColorsMenu.add(greenAction);
				setMenu.addSeparator();
				JMenu LAFMenu = new JMenu("Look & Feel");
				setMenu.add(LAFMenu);
					ButtonGroup LAFgroup = new ButtonGroup();
					JRadioButtonMenuItem metal = new JRadioButtonMenuItem(new LAFAction("Metal", "javax.swing.plaf.metal.MetalLookAndFeel", this));
					metal.setSelected(true);
					JRadioButtonMenuItem motif = new JRadioButtonMenuItem(new LAFAction("Motif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel", this));
					JRadioButtonMenuItem win9x = new JRadioButtonMenuItem(new LAFAction("Win9x", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel", this));
					LAFgroup.add(metal);
					LAFgroup.add(motif);
					LAFgroup.add(win9x);
					LAFMenu.add(metal);
					LAFMenu.add(motif);
					LAFMenu.add(win9x);

			JMenu helpMenu = new JMenu("Help");
			helpMenu.setMnemonic(KeyEvent.VK_H);
			mBar.add(helpMenu);
				helpMenu.add(SeCommTool.createJMenuItem(tutorialAction));
	}

    private void createStatusBar(JToolBar toolbar) {

		Dimension labelDim = new Dimension(50, 20);
		Dimension separatorDim = new Dimension(5, 0);
		Border lB = BorderFactory.createLoweredBevelBorder();

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
        toolbar.add(statusPanel, BorderLayout.SOUTH);

        statusLabel = new JLabel(" Click the PLAY button to start rendering");
        statusLabel.setPreferredSize(new Dimension(335, 20));
		statusLabel.setBorder(lB);
        statusPanel.add(statusLabel);

        statusPanel.add(Box.createHorizontalGlue());

        posLabel = new JLabel("[1,1]", SwingConstants.CENTER);
        posLabel.setPreferredSize(labelDim);
        posLabel.setToolTipText("Robby's  x/y coordinates");
        posLabel.setBorder(lB);
        statusPanel.add(posLabel);

		statusPanel.add(Box.createRigidArea(separatorDim));

        //create the label that shows the currently selected color
        activeColorLabel = new JLabel(" ");
        activeColorLabel.setOpaque(true);
        activeColorLabel.setToolTipText("active color");
        activeColorLabel.setBackground(daColors[Room.BLUE]);
        activeColorLabel.setPreferredSize(labelDim);
        activeColorLabel.setBorder(lB);
        statusPanel.add(activeColorLabel);

        statusPanel.add(Box.createRigidArea(separatorDim));
    }

	class ColorAction extends SE11xAction {
		public ColorAction (final String name, final Icon smallIcon, final int color) {
			super(name, smallIcon, null, null, null);
			this.color = color;
		}

		public void actionPerformed (final ActionEvent e) {
			activeColor = color;
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					activeColorLabel.setBackground(daColors[activeColor]);
				}
			});
		}

		private int color = 0;
	}

	class Picker extends PopupListener {

		public Picker (JPopupMenu popup) {
			super(popup);
		}

		//'huh' code... code that makes you go 'huh?'
		public void mouseClicked (MouseEvent e) {
			if ((e.getModifiers() & InputEvent.BUTTON1_MASK) != 0) {
				int x = e.getX();
				int y = e.getY();
				Room room = picture.getRoom();

				x /= Picture.CELLSIZE;
				y = (room.room_size() - 1) - (y / Picture.CELLSIZE);
				//huh?
				try {
					room.setSquare(x, y, activeColor);
					picture.repaint();
				}
				catch (Exception ignored) {}
			}
			else {
				mebbeShowPopup(e);
			}
		}
	}

	private Picture picture = null;
	private JPopupMenu popup = null;
	private JLabel statusLabel = null;
	private JLabel activeColorLabel = null;
	private JLabel posLabel = null;
	private JButton startButton = null;
	private Picker pListen = null;
	private SE11xAction startAction = null;
	private JMenu setColorsMenu = null;
	private int activeColor = 0;
	private boolean start = false;
	private final Color[] daColors = {
		Color.white,
		Color.black,
		new Color(45, 90, 181),
		new Color(255, 211, 38),
		new Color(97, 168, 40)
	};
}