
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFrame;

public class SMinesweeper extends JFrame {
	
	private JLabel statusBar;
	private int difficulty;
	private int numBombs;
	private int xDim;
	private int yDim;
	private final int CELL_SIZE = 19;
	private StopWatch stopWatch = new StopWatch(this);
	

    JMenuBar optionsMenuBar;
    JMenu menuOne, menuTwo, menuCustom;
    JMenuItem load, save, about;
    JRadioButtonMenuItem beginner, advanced, expert, custom;
    JTextField mineTextField, xTextField, yTextField;
    
    public BeginnerGrid grid;
	
	
	public SMinesweeper(int numBombs)
	{
		xDim = 19;
		yDim = 19;
		numBombs = 5;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize((CELL_SIZE * xDim) + 6, (CELL_SIZE * yDim) + 68 + 10);
		setLocationRelativeTo(null);
		setTitle("Square Minesweeper");
        setJMenuBar(createMenuBar());
		
		statusBar = new JLabel("");
		//add( statusBar, BorderLayout.SOUTH );
		
		grid = new BeginnerGrid(statusBar, xDim, yDim, numBombs, stopWatch);
		add(grid);
		
		setResizable(false);
		setVisible(true);
		
	}
	
	public static void main(String[] args)
	{
		//System.out.println("Creating Grid...");
		//BeginnerGrid gameGrid = new BeginnerGrid();
		//gameGrid.testGrid();
		//StopWatch sWatch = new StopWatch();
		int numBombs = 10;
		new SMinesweeper(numBombs);
	}
	
    public JMenuBar createMenuBar() {

        //Create the menu bar.
        optionsMenuBar = new JMenuBar();

        //Build the first menu.
        menuOne = new JMenu("Options");
        menuOne.getAccessibleContext().setAccessibleDescription(
                "this is menu");
        optionsMenuBar.add(menuOne);

        //a group of JMenuItems
        load = new JMenuItem("Load");
        menuOne.add(load);

        //a group of JMenuItems
        save = new JMenuItem("Save");
        menuOne.add(save);

        //a group of radio button menu items
        menuOne.addSeparator();
        ButtonGroup levels = new ButtonGroup();

        beginner = new JRadioButtonMenuItem("beginner");
        beginner.addActionListener(new LevelActionListener());
        levels.add(beginner);
        menuOne.add(beginner);

        advanced = new JRadioButtonMenuItem("advanced");
        advanced.addActionListener(new LevelActionListener());
        advanced.setSelected(true);
        levels.add(advanced);
        menuOne.add(advanced);

        expert = new JRadioButtonMenuItem("expert");
        expert.addActionListener(new LevelActionListener());
        levels.add(expert);
        menuOne.add(expert);

        custom = new JRadioButtonMenuItem("custom");
        custom.addActionListener(new LevelActionListener());
        levels.add(custom);
        menuOne.add(custom);

        //a submenu for custom settings
        menuOne.addSeparator();
        menuCustom = new JMenu("Custom settings");

        mineTextField = new JTextField("Number of mines");
        menuCustom.add(mineTextField);

        xTextField = new JTextField("X size:");
        menuCustom.add(xTextField);

        yTextField = new JTextField("Y size:");
        menuCustom.add(yTextField);
        menuOne.add(menuCustom);
		
        //Build second menu in the menu bar.
        menuTwo = new JMenu("Help");
        optionsMenuBar.add(menuTwo);
		
		about = new JMenuItem("About");
		menuTwo.add(about);

        return optionsMenuBar;
    }
    

    class LevelActionListener implements ActionListener{
	
    	public void actionPerformed(ActionEvent evt){
    		if(beginner.isSelected() == true){
    			xDim = 9;
    			yDim = 9;
    			numBombs = 10;
    			System.out.println("game set to beginner");
    		}
    		else if(advanced.isSelected() == true){
    			xDim = 16;
    			yDim = 16;
    			numBombs = 40;
    			System.out.println("game set to advanced");
    		}
    		else if(expert.isSelected() == true){
    			xDim = 30;
    			yDim = 16;
    			numBombs = 99;
    			System.out.println("game set to expert");
    		}
    		else{
    	        xDim = (int)((Double.parseDouble(xTextField.getText())));
    	        yDim = (int)((Double.parseDouble(yTextField.getText())));
    	        numBombs = (int)((Double.parseDouble(mineTextField.getText())));
    			System.out.println("game set to custom. xdim ydim numBombs: " + xDim + yDim + numBombs);
    		}
    		
    		remove(grid);
    		stopWatch.reset();
			grid = new BeginnerGrid(statusBar, xDim, yDim, numBombs, stopWatch);
			setSize((CELL_SIZE * xDim) + 6, (CELL_SIZE * yDim) + 68 + 10);
			add(grid);
		
    	}
	
	}
    

}
