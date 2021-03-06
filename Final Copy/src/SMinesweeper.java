
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDialog;

import java.awt.*;
import java.awt.event.*;

import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
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
import javax.swing.event.ChangeListener;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.File;
import java.io.IOException;

public class SMinesweeper extends JFrame {
	
	private JLabel statusBar;
	private int difficulty;
	private int numBombs;
	private int xDim;
	private int yDim;
	private final int CELL_SIZE = 19;
	private final int CELL_SIZE_X = 10;
	private final int CELL_SIZE_Y = 20;
	private StopWatch stopWatch = new StopWatch(this);
	private int gridShape;
	

    JMenuBar optionsMenuBar;
    JMenu menuOne, menuTwo, menuCustom;
    JMenuItem newGame, load, save, about, ten;
    JRadioButtonMenuItem beginner, advanced, expert, custom;
	JRadioButtonMenuItem rectangular, hexagonal, triangular;
    JTextField mineTextField, xTextField, yTextField;
    Record record;
    scoreTable st;
    public BeginnerGrid grid;
    public HexJavaBeginnerGrid hexGrid;
    

	
	public SMinesweeper(int numBombs)
	{
		//setSize((CELL_SIZE *(xDim+2)), (CELL_SIZE *(2* yDim+4))+50);
		//setSize((2 * CELL_SIZE * xDim) + 6, (2 * CELL_SIZE * yDim) + 68 + 10);
		//setSize(CELL_SIZE_X * xDim * 2, CELL_SIZE_Y * yDim * 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Square Minesweeper");
        setJMenuBar(createMenuBar());
        gridShape = 0; //0 for rect, 1 for hex, 2 for tri?
		
		statusBar = new JLabel("Software Engineering Project");
		add( statusBar, BorderLayout.NORTH);
		st = new scoreTable(this);
		record=new Record(this);
		
		if(gridShape == 0){
			xDim = 9;
			yDim = 9;
			numBombs = 10;
			grid = new BeginnerGrid(statusBar, xDim, yDim, numBombs, stopWatch, record);
			setSize((CELL_SIZE * xDim) + 6, (CELL_SIZE * yDim) + 100);
			add(grid);
		}
		if(gridShape == 1){
			xDim = 12;
			yDim = 6;
			numBombs = 10;
			hexGrid = new HexJavaBeginnerGrid(statusBar, xDim, yDim, numBombs, stopWatch, record);
			setSize((CELL_SIZE_X *(2*xDim+2)), (CELL_SIZE_Y *(2* yDim+4))+22);
			add(hexGrid);
		}
		
		setResizable(false);
		setVisible(true);
		
	}
	
	public static void main(String[] args)
	{
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
        
        newGame = new JMenuItem("New");
        newGame.addActionListener(new NewActionListener(this));
        menuOne.add(newGame);

        load = new JMenuItem("Load");
        menuOne.add(load);

        save = new JMenuItem("Save");
        menuOne.add(save);
        
        menuOne.addSeparator();
        ButtonGroup shapes = new ButtonGroup();
        
        rectangular = new JRadioButtonMenuItem("rect");
        rectangular.addActionListener(new ShapeActionListener(this, shapes));
        shapes.add(rectangular);
        rectangular.setSelected(true);
        menuOne.add(rectangular);
        
        hexagonal = new JRadioButtonMenuItem("hex");
        hexagonal.addActionListener(new ShapeActionListener(this, shapes));
        shapes.add(hexagonal);
        //hexagonal.setSelected(true);
        menuOne.add(hexagonal);

        //a group of radio button menu items
        menuOne.addSeparator();
        ButtonGroup levels = new ButtonGroup();

        beginner = new JRadioButtonMenuItem("beginner");
        beginner.addActionListener(new LevelActionListener(this, levels));
        levels.add(beginner);
        beginner.setSelected(true);
        menuOne.add(beginner);

        advanced = new JRadioButtonMenuItem("advanced");
        advanced.addActionListener(new LevelActionListener(this, levels));
        levels.add(advanced);
        menuOne.add(advanced);

        expert = new JRadioButtonMenuItem("expert");
        expert.addActionListener(new LevelActionListener(this, levels));
        levels.add(expert);
        menuOne.add(expert);

        custom = new JRadioButtonMenuItem("custom");
        custom.addActionListener(new LevelActionListener(this, levels));
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
		about.addActionListener(new AboutActionListener(this));
		menuTwo.add(about);
		
		ten = new JMenuItem("Highest Scores");
		ten.addActionListener(new ScoreActionListener(this));
		menuTwo.add(ten);

        return optionsMenuBar;
    }
     
     class AboutActionListener implements ActionListener{
    	 public JFrame gameWindow;
    	 
    	 public AboutActionListener(JFrame gw){
    		 gameWindow = gw;
    	 }
    	 
    	 public void actionPerformed(ActionEvent evt){
    		 
    	 }
     }
    
     class NewActionListener implements ActionListener{
    	 public JFrame gameWindow;
    	 
    	 public NewActionListener(JFrame gw){
    		 gameWindow = gw;
    	 }
    	 
    	 public void actionPerformed(ActionEvent evt){
    		 new SMinesweeper(numBombs);
    	 }
     }
     
     class ScoreActionListener implements ActionListener{
    	public JFrame gameWindow;
    	
    	public ScoreActionListener(JFrame gw){
    		gameWindow = gw;
    	}
    	public void actionPerformed(ActionEvent evt){
    		st.run();
    	}
    	
      }
    
     class ShapeActionListener implements ActionListener{
	   public JFrame gameWindow;
	   public ButtonGroup shapes;
	   public JRadioButtonMenuItem lastChosen;
	   
	   public ShapeActionListener(JFrame gw, ButtonGroup shps){
		   gameWindow = gw;
		   shapes = shps;
	   }
	   
	   public void actionPerformed(ActionEvent evt){	   
		   
		   if(rectangular.isSelected() == true){
			   if(gridShape == 0){
				   //return;
			   }
			   if(gridShape == 1){
				   remove(hexGrid);
				   hexGrid = null;
			   }
			   if(gridShape == 2){
			   }
			   if(beginner.isSelected() == true){
				   xDim = 9;
				   yDim = 9;
				   numBombs = 10;
			   }
			   if(advanced.isSelected() == true){
				   xDim = 16;
				   yDim = 16;
				   numBombs = 40;
			   }
			   if(expert.isSelected() == true){
				   xDim = 30;
				   yDim = 16;
				   numBombs = 99;
			   }
			   if(custom.isSelected() == true){
				   
			   }
			   stopWatch.reset();
			   grid = new BeginnerGrid(statusBar, xDim, yDim, numBombs, stopWatch, record);
				setSize((CELL_SIZE * xDim) + 6, (CELL_SIZE * yDim) + 100);
			   add(grid);
			   System.out.println("game set to rectangular");
			   lastChosen = rectangular;
			   gridShape = 0;
		   }
		   else if(hexagonal.isSelected() == true){
			   if(gridShape == 1){
				   //return;
			   }
			   if(gridShape == 0){
				   remove(grid);
				   grid = null;
			   }
			   if(gridShape == 2){				   
			   }
			   if(beginner.isSelected() == true){
				   xDim = 12;
				   yDim = 6;
				   numBombs = 10;
			   }
			   if(advanced.isSelected() == true){
				   xDim = 18;
				   yDim = 9;
				   numBombs = 20;
			   }
			   if(expert.isSelected() == true){
				   xDim = 30;
				   yDim = 12;
				   numBombs = 60;
			   }
			   if(custom.isSelected() == true){
				   
			   }
			   stopWatch.reset();
			   hexGrid = new HexJavaBeginnerGrid(statusBar, xDim, yDim, numBombs, stopWatch, record);
   			setSize((CELL_SIZE_X *(2*xDim+2)), (CELL_SIZE_Y *(2* yDim+4))+22);
			   add(hexGrid);			   
			   System.out.println("game set to hexagonal");
			   lastChosen = hexagonal;
			   gridShape = 1;
		   }
		   else if(triangular.isSelected() == true){
			   System.out.println("game set to triangular");
			   lastChosen = triangular;
			   gridShape = 2;
		   }
	   }
   }

    class LevelActionListener implements ActionListener{
    	
    	public JFrame gameWindow;
    	public ButtonGroup levels;
    	public JRadioButtonMenuItem lastChosen;
    	
    	public LevelActionListener(JFrame gw, ButtonGroup lvls) {
			gameWindow = gw;
			levels = lvls;
			lastChosen = beginner;
		}
    	
    	public void actionPerformed(ActionEvent evt){
    		if(beginner.isSelected() == true){
    			if(lastChosen == beginner){
    				//return;
    			}
    			lastChosen = beginner;
    			System.out.println("game set to beginner");
    			if(gridShape == 0){
        			xDim = 9;
        			yDim = 9;
        			numBombs = 10;
        			remove(grid);
        			stopWatch.reset();
        			grid = new BeginnerGrid(statusBar, xDim, yDim, numBombs, stopWatch, record);
        			setSize((CELL_SIZE * xDim) + 6, (CELL_SIZE * yDim) + 100);
        			add(grid);
        		}
        		
        		if(gridShape == 1){
        			xDim = 12;
        			yDim = 6;
        			numBombs = 10;
        			remove(hexGrid);
        			stopWatch.reset();
        			hexGrid = new HexJavaBeginnerGrid(statusBar, xDim, yDim, numBombs, stopWatch, record);
        			setSize((CELL_SIZE_X *(2*xDim+2)), (CELL_SIZE_Y *(2* yDim+4))+22);
        			add(hexGrid);
        		}
    		}
    		else if(advanced.isSelected() == true){
    			if(lastChosen == advanced){
    				//return;
    			}
    			lastChosen = advanced;
    			System.out.println("game set to advanced");
    			if(gridShape == 0){
        			xDim = 16;
        			yDim = 16;
        			numBombs = 40;
        			remove(grid);
        			stopWatch.reset();
        			grid = new BeginnerGrid(statusBar, xDim, yDim, numBombs, stopWatch, record);
        			setSize((CELL_SIZE * xDim) + 6, (CELL_SIZE * yDim) + 100);
        			add(grid);
        		}
        		
        		if(gridShape == 1){
        			xDim = 18;
        			yDim = 9;
        			numBombs = 20;
        			remove(hexGrid);
        			stopWatch.reset();
        			hexGrid = new HexJavaBeginnerGrid(statusBar, xDim, yDim, numBombs, stopWatch, record);
        			setSize((CELL_SIZE_X *(2*xDim+2)), (CELL_SIZE_Y *(2* yDim+4))+22);
        			add(hexGrid);
        		}
    		}
    		else if(expert.isSelected() == true){
    			if(lastChosen == expert){
    				//return;
    			}
    			lastChosen = expert;
    			System.out.println("game set to expert");
    			if(gridShape == 0){
        			xDim = 30;
        			yDim = 16;
        			numBombs = 99;
        			remove(grid);
        			stopWatch.reset();
        			grid = new BeginnerGrid(statusBar, xDim, yDim, numBombs, stopWatch, record);
        			setSize((CELL_SIZE * xDim) + 6, (CELL_SIZE * yDim) + 100);
        			add(grid);
        		}
        		
        		if(gridShape == 1){
        			xDim = 30;
        			yDim = 12;
        			numBombs = 60;
        			remove(hexGrid);
        			stopWatch.reset();
        			hexGrid = new HexJavaBeginnerGrid(statusBar, xDim, yDim, numBombs, stopWatch, record);
        			setSize((CELL_SIZE_X *(2*xDim+2)), (CELL_SIZE_Y *(2* yDim+4))+22);
        			add(hexGrid);
        		}
    		}
    		else{
    			try 
    			{ 
    				Integer.parseInt( xTextField.getText() ); 
    				Integer.parseInt(yTextField.getText());
    				Integer.parseInt(mineTextField.getText());
    			}
    			catch (NumberFormatException e) {
    				
    				levels.clearSelection();

    				JOptionPane.showMessageDialog(gameWindow, "Set custom values first!", "Error", 0);
					return;
				}
    	        xDim = (int)((Double.parseDouble(xTextField.getText())));
    	        yDim = (int)((Double.parseDouble(yTextField.getText())));
    	        numBombs = (int)((Double.parseDouble(mineTextField.getText())));
    			lastChosen =  custom;
    	        System.out.println("game set to custom. xdim ydim numBombs: " + xDim + yDim + numBombs);
    			if(gridShape == 0){
        			remove(grid);
        			stopWatch.reset();
        			grid = new BeginnerGrid(statusBar, xDim, yDim, numBombs, stopWatch, record);
        			setSize((CELL_SIZE * xDim) + 6, (CELL_SIZE * yDim) + 100);
        			add(grid);
        		}
        		
        		if(gridShape == 1){
        			remove(hexGrid);
        			stopWatch.reset();
        			hexGrid = new HexJavaBeginnerGrid(statusBar, xDim, yDim, numBombs, stopWatch, record);
        			setSize((CELL_SIZE_X *(2*xDim+2)), (CELL_SIZE_Y *(2* yDim+4))+22);
        			add(hexGrid);
        		}
    		}
    	}
	
	}
    

}
