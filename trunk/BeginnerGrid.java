import java.util.Random;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//BeginnerGrid class is a 9X9 board with 10 bombs
public class BeginnerGrid extends JPanel {
	
	private final int EMPTY_CELL = 0;
	private final int COVER_FOR_CELL = 9;
	private final int FLAG = 10;
	private final int WRONG_FLAG = 13;
	private final int BOMB = 11;
	private final int RED_BOMB = 12;
	private final int NUM_IMAGES = 14;
	private final int CELL_SIZE = 19;
	private final int COVERED_MINE_CELL = COVER_FOR_CELL + BOMB;
	private final int FLAGGED_MINE_CELL = FLAG + BOMB;
	
	private Image[] img;
	private int numMines = 10;
	private int gridDimX, gridDimY;
	private boolean inGame = true;
	private boolean mouseInputEnabled = true;
	private int flaggedMines = 0;
	private int numFlags = 0;
	private StopWatch s;
	private Record record=new Record();
	
	private JLabel statusBar;
	Cell[][] cells;// = new Cell[gridDim][gridDim];
	
	/// Constructor for BeginnerGrid, 9 x 9 with 10 mines
	public BeginnerGrid(JLabel statusBar, int gridDimensionX, int gridDimensionY, int numBombs, StopWatch sw) {
		
		numMines = numBombs;
		gridDimX = gridDimensionX;
		gridDimY = gridDimensionY;
		cells = new Cell[gridDimX][gridDimY];
		s = sw;
		
		this.statusBar = statusBar;
		
		img = new Image[NUM_IMAGES];
		
		for (int i = 0; i < NUM_IMAGES; i++)
		{
			img[i] = new ImageIcon(this.getClass().getResource((i) + ".png")).getImage();
		}
		
		
		setDoubleBuffered(true);
		
		addMouseListener(new MinesAdapter());
		
		// Build and populate grid with cells.
		for ( int i = 0; i < gridDimX; i++)
		{
			for ( int j = 0; j < gridDimY; j++)
			{
				cells[i][j] = new Cell();
			}
		}
		
		
		this.statusBar.setText("Mines Left: " + numMines);
		initializeBoard(numMines);
		testGrid();
	}
	
	/// Places mines in random cells, initializes the numbers in non-mine cells
	protected void initializeBoard(int _numMines)
	{
		// Generate 10 bombs
		Random random = new Random();
		int randIndexX = random.nextInt(gridDimX);
		int randIndexY = random.nextInt(gridDimY);
		int numBombs = 0;
		
		while (numBombs != _numMines)
		{
			if ( cells[randIndexX][randIndexY].getValue() != BOMB )
			{
				cells[randIndexX][randIndexY] = new Cell(BOMB);
				numBombs++;
				
				if ( randIndexX == 0 )	// we are at the left edge
				{
					if ( randIndexY == 0 ) // we are at the top left corner
					{
						int curVal;
						if ( cells[randIndexX + 1][randIndexY].getValue() != BOMB )
						{
							curVal = cells[randIndexX + 1][randIndexY].getValue();
							cells[randIndexX + 1][randIndexY].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX][randIndexY + 1].getValue() != BOMB )
						{
							curVal = cells[randIndexX][randIndexY + 1].getValue();	
							cells[randIndexX][randIndexY + 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX + 1][randIndexY + 1].getValue() != BOMB )
						{
							curVal = cells[randIndexX + 1][randIndexY + 1].getValue();	
							cells[randIndexX + 1][randIndexY + 1].setValue(curVal + 1);
						}
					}
					
					else if ( randIndexY == gridDimY - 1 ) // we are at the bottom left corner
					{
						int curVal;
						if ( cells[randIndexX + 1][randIndexY].getValue() != BOMB )
						{
							curVal = cells[randIndexX + 1][randIndexY].getValue();
							cells[randIndexX + 1][randIndexY].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX][randIndexY - 1].getValue() != BOMB )
						{
							curVal = cells[randIndexX][randIndexY - 1].getValue();
							cells[randIndexX][randIndexY - 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX + 1][randIndexY - 1].getValue() != BOMB )
						{
							curVal = cells[randIndexX + 1][randIndexY - 1].getValue();
							cells[randIndexX + 1][randIndexY - 1].setValue(curVal + 1);
						}
					}
					
/*stopped here*/	else	// we are somewhere along the left edge
					{
						int curVal;	
						if ( cells[randIndexX + 1][randIndexY].getValue() != BOMB )
						{
							curVal = cells[randIndexX + 1][randIndexY].getValue();
							cells[randIndexX + 1][randIndexY].setValue(curVal + 1);
						}
						
						
						if ( cells[randIndexX][randIndexY - 1].getValue() != BOMB )
						{
							curVal = cells[randIndexX][randIndexY - 1].getValue();
							cells[randIndexX][randIndexY - 1].setValue(curVal + 1);
						}
						
						
						if ( cells[randIndexX][randIndexY + 1].getValue() != BOMB )
						{
							curVal = cells[randIndexX][randIndexY + 1].getValue();
							cells[randIndexX][randIndexY + 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX + 1][randIndexY + 1].getValue() != BOMB )
						{
							curVal = cells[randIndexX + 1][randIndexY + 1].getValue();
							cells[randIndexX + 1][randIndexY + 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX + 1][randIndexY - 1].getValue() != BOMB )
						{
							curVal = cells[randIndexX + 1][randIndexY - 1].getValue();
							cells[randIndexX + 1][randIndexY - 1].setValue(curVal + 1);
						}
					}
				}
				
				else if ( randIndexX == gridDimX - 1 )		// we are at right edge
				{
					if ( randIndexY == 0 ) // we are at the top right corner
					{
						int curVal;
						if ( cells[randIndexX - 1][randIndexY].getValue() != BOMB )
						{
							curVal = cells[randIndexX - 1][randIndexY].getValue();
							cells[randIndexX - 1][randIndexY].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX][randIndexY + 1].getValue() != BOMB )
						{
							curVal = cells[randIndexX][randIndexY + 1].getValue();
							cells[randIndexX][randIndexY + 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX - 1][randIndexY + 1].getValue() != BOMB )
						{
							curVal = cells[randIndexX - 1][randIndexY + 1].getValue();
							cells[randIndexX - 1][randIndexY + 1].setValue(curVal + 1);
						}
					}
					
					else if ( randIndexY == gridDimY - 1 ) // we are at the bottom right corner
					{
						int curVal;
						if ( cells[randIndexX - 1][randIndexY].getValue() != BOMB )
						{
							 curVal = cells[randIndexX - 1][randIndexY].getValue();
							 cells[randIndexX - 1][randIndexY].setValue(curVal + 1);
						}
							
						if ( cells[randIndexX][randIndexY - 1].getValue() != BOMB )
						{
							curVal = cells[randIndexX][randIndexY - 1].getValue();
							cells[randIndexX][randIndexY - 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX - 1][randIndexY - 1].getValue() != BOMB )
						{
							curVal = cells[randIndexX - 1][randIndexY - 1].getValue();
							cells[randIndexX - 1][randIndexY - 1].setValue(curVal + 1);
						}
					}
					
					else	// we are somewhere along the right edge
					{
						int curVal;
						if ( cells[randIndexX - 1][randIndexY].getValue() != BOMB )
						{
							curVal = cells[randIndexX - 1][randIndexY].getValue();
							cells[randIndexX - 1][randIndexY].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX][randIndexY - 1].getValue() != BOMB )
						{
							curVal = cells[randIndexX][randIndexY - 1].getValue();
							cells[randIndexX][randIndexY - 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX][randIndexY + 1].getValue() != BOMB ) {
							curVal = cells[randIndexX][randIndexY + 1].getValue();
							cells[randIndexX][randIndexY + 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX - 1][randIndexY + 1].getValue() != BOMB ) {
							curVal = cells[randIndexX - 1][randIndexY + 1].getValue();
							cells[randIndexX - 1][randIndexY + 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX - 1][randIndexY - 1].getValue() != BOMB ) {
							curVal = cells[randIndexX - 1][randIndexY - 1].getValue();
							cells[randIndexX - 1][randIndexY - 1].setValue(curVal + 1);
						}
					}
				}
				
				else if (randIndexY == 0)		// we are along the top of the board
				{
					// Corners are handled by earlier cases, so we only need the case where
					// we are somewhere along the top
					
					int curVal;
					if ( cells[randIndexX - 1][randIndexY].getValue() != BOMB )
					{
						curVal = cells[randIndexX - 1][randIndexY].getValue();
						cells[randIndexX - 1][randIndexY].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX + 1][randIndexY].getValue() != BOMB )
					{
						curVal = cells[randIndexX + 1][randIndexY].getValue();
						cells[randIndexX + 1][randIndexY].setValue(curVal + 1);
					}
					
					
					if ( cells[randIndexX][randIndexY + 1].getValue() != BOMB )
					{
						curVal = cells[randIndexX][randIndexY + 1].getValue();
						cells[randIndexX][randIndexY + 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX - 1][randIndexY + 1].getValue() != BOMB )
					{
						curVal = cells[randIndexX - 1][randIndexY + 1].getValue();
						cells[randIndexX - 1][randIndexY + 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX + 1][randIndexY + 1].getValue() != BOMB )
					{
						curVal = cells[randIndexX + 1][randIndexY + 1].getValue();
						cells[randIndexX + 1][randIndexY + 1].setValue(curVal + 1);
					}
					
				}
				
				else if (randIndexY == gridDimY - 1)		// we are along the bottom of the board
				{
					// Corners are handled by earlier cases, so we only need the case where
					// we are somewhere along the bottom
					int curVal;
					if ( cells[randIndexX - 1][randIndexY].getValue() != BOMB )
					{
						curVal = cells[randIndexX - 1][randIndexY].getValue();
						cells[randIndexX - 1][randIndexY].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX + 1][randIndexY].getValue() != BOMB )
					{
						curVal = cells[randIndexX + 1][randIndexY].getValue();
						cells[randIndexX + 1][randIndexY].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX][randIndexY - 1].getValue() != BOMB )
					{
						curVal = cells[randIndexX][randIndexY - 1].getValue();
						cells[randIndexX][randIndexY - 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX - 1][randIndexY - 1].getValue() != BOMB )
					{
						curVal = cells[randIndexX - 1][randIndexY - 1].getValue();
						cells[randIndexX - 1][randIndexY - 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX + 1][randIndexY - 1].getValue() != BOMB )
					{
						curVal = cells[randIndexX + 1][randIndexY - 1].getValue();
						cells[randIndexX + 1][randIndexY - 1].setValue(curVal + 1);
					}
				}
				
				else							// we are somewhere in the middle
				{
					int curVal;
					if ( cells[randIndexX - 1][randIndexY].getValue() != BOMB )
					{
						curVal = cells[randIndexX - 1][randIndexY].getValue();
						cells[randIndexX - 1][randIndexY].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX + 1][randIndexY].getValue() != BOMB )
					{
						curVal = cells[randIndexX + 1][randIndexY].getValue();
						cells[randIndexX + 1][randIndexY].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX][randIndexY - 1].getValue() != BOMB )
					{
						curVal = cells[randIndexX][randIndexY - 1].getValue();
						cells[randIndexX][randIndexY - 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX][randIndexY + 1].getValue() != BOMB )
					{
						curVal = cells[randIndexX][randIndexY + 1].getValue();
						cells[randIndexX][randIndexY + 1].setValue(curVal + 1);
					}
					
					// DIAGONALS BELOW
					
					if ( cells[randIndexX + 1][randIndexY + 1].getValue() != BOMB )
					{
						curVal = cells[randIndexX + 1][randIndexY + 1].getValue();
						cells[randIndexX + 1][randIndexY + 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX + 1][randIndexY - 1].getValue() != BOMB )
					{
						curVal = cells[randIndexX + 1][randIndexY - 1].getValue();
						cells[randIndexX + 1][randIndexY - 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX - 1][randIndexY + 1].getValue() != BOMB )
					{
						curVal = cells[randIndexX - 1][randIndexY + 1].getValue();
						cells[randIndexX - 1][randIndexY + 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX - 1][randIndexY - 1].getValue() != BOMB )
					{
						curVal = cells[randIndexX - 1][randIndexY - 1].getValue();
						cells[randIndexX - 1][randIndexY - 1].setValue(curVal + 1);
					}
				}
				
				
			}
			
			randIndexX = random.nextInt(gridDimX);
			randIndexY = random.nextInt(gridDimY);
		}
		
		// Set image index to value of cell
		for ( int i = 0; i < gridDimY; i++ )
		{
			for ( int j = 0; j < gridDimX; j++)
			{
				if ( cells[j][i].getValue() != BOMB)
				{
					cells[j][i].setImageIndex(cells[j][i].getValue());
				}
			}
		}
		//repaint();
		
	}
	
	/// For testing: prints the underlying values of the grid
	public void testGrid() 
	{
		for ( int i = 0; i < gridDimY; i++)
		{
			for ( int j = 0; j < gridDimX; j++)
			{
				System.out.print(cells[j][i].getValue() + " ");
			}
			System.out.println();
		}
	}

	/// Called when a non-mine is clicked, finds and uncovers all the appropriate cells
	public void findEmptyCells(int i, int j)
	{
		//cells[i][j].setImageIndex(cells[i][j].getValue());
		
		if ( cells[i][j].getValue() == 0 && !cells[i][j].getIsUncovered() &&  !cells[i][j].getIsFlagged())
		{
			cells[i][j].setIsUncovered(true);
			if ( j < gridDimY - 1) findEmptyCells(i, j + 1);
			if ( i < gridDimX - 1) findEmptyCells(i + 1, j);
			
			if ( i > 0) findEmptyCells(i - 1, j);
			if ( j > 0) findEmptyCells(i, j - 1);
			
			if ( i < gridDimX - 1 && j < gridDimY - 1) findEmptyCells(i + 1, j + 1);
			if ( i > 0 && j > 0) findEmptyCells(i - 1, j - 1);
			
			if ( i < gridDimX - 1 && j > 0) findEmptyCells(i + 1, j - 1);
			if ( i > 0 && j < gridDimY - 1) findEmptyCells(i - 1, j + 1);	
		}
	    
		if (!cells[i][j].getIsFlagged())
			cells[i][j].setIsUncovered(true);
		repaint();
	}
	
	/// Overriding JFrame's paintComponent function. DO NOT call directly, rather, call repaint().
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

        Cell cell;
        int uncover = 0;


        for (int i = 0; i < gridDimY; i++) {
            for (int j = 0; j < gridDimX; j++) {

                cell = cells[j][i];
                
                if ( !cell.getIsUncovered() )
                {
                	cell.setImageIndex(COVER_FOR_CELL);
                }
                
                if ( cell.getIsUncovered() )
                {
                	cell.setImageIndex(cell.getValue());
                }
                
                else if ( cell.getIsFlagged() )
                {
                	cell.setImageIndex(FLAG);
                }
                
                else if ( cell.getIsUncovered() && cell.getValue() == BOMB)
                {
                	cell.setImageIndex(RED_BOMB);
                	//inGame = false;
                }

                g.drawImage(img[cell.getImageIndex()], (j * CELL_SIZE), (i * CELL_SIZE), this);
            }
        }


        if (uncover == 0 && inGame) {
            //inGame = false;
            //statusBar.setText("Game won");
        } 
        else if (!inGame) {
            //statusBar.setText("Game lost");
		}
	}

	public void gameOver(boolean winner)
	{
		inGame = false;
		
		for ( int i = 0; i < gridDimY; i++)
		{
			for ( int j = 0; j < gridDimX; j++)
			{
				
				if (winner)
				{	
					if (cells[j][i].getImageIndex() == COVER_FOR_CELL) // image is not a flag
					{
						cells[j][i].setIsUncovered(true);
					}
					
					repaint();
				}
				
				else
				{
					if (cells[j][i].getImageIndex() == FLAG)
					{
						// if flag is correct, draw flag
						if (cells[j][i].getValue() == BOMB)
						{
							// do nothing
						}
						
						// if flag is incorrect, draw wrong_flag
						if (cells[j][i].getValue() != BOMB)
						{
							cells[j][i].setValue(WRONG_FLAG);
							cells[j][i].setIsUncovered(true);
						}
					}
					
					else // cell is not flagged
					{
						// uncover
						cells[j][i].setIsUncovered(true);
					}
				}
			}
		}
		
		
		if ( !winner )
		{
			statusBar.setText("Game Lost");
			// TODO stop timer
			s.reset();
		}
		
		else
		{
			statusBar.setText("Game Won!");
			// TODO stop timer
			// TODO Save high score
			s.pause();
			record.run(s.getTime());
			//s.reset();
			
			for ( int i = 0; i < gridDimY; i++)
			{
				for ( int j = 0; j < gridDimX; j++)
				{
					if (cells[j][i].getValue() == BOMB)
					{
						cells[j][i].setImageIndex(FLAG);
						cells[j][i].setValue(FLAG);
						repaint();
					}
				}
			}
			
		}
		
		
		repaint();
		mouseInputEnabled = false;
		
		
	}
	
	class MinesAdapter extends MouseAdapter {
		
        public void mousePressed(MouseEvent e) {
        	if (mouseInputEnabled){
        	
	            int x = e.getX();
	            int y = e.getY();

	            int cCol = x / CELL_SIZE;
	            int cRow = y / CELL_SIZE;
	
	            boolean rep = false;


	            /*if (!inGame) {
	                initializeBoard(numMines);
	                repaint();
	            }*/
	
	            
	
	            if ((x < gridDimX * CELL_SIZE) && (y < gridDimY * CELL_SIZE)) {
	            	
	            	s.start();
            	
	                if (e.getButton() == MouseEvent.BUTTON1) 
	                {
	                	//cells[cCol][cRow].setIsUncovered(true);
	                	//rep = true;
	                	
	                	if ( cells[cCol][cRow].getValue() == 0)
	                	{
	                		findEmptyCells(cCol, cRow);
	                	}
	                	else 
	                	{
	                		cells[cCol][cRow].setIsUncovered(true);
	                	}
	                	
	                	if ( cells[cCol][cRow].getValue() == BOMB)
	                	{
	                		cells[cCol][cRow].setValue(RED_BOMB);
	                		gameOver(false);
	                	}
	                	
	                	rep = true;
	                	
	                }
                
	                else if (e.getButton() == MouseEvent.BUTTON3)
	                {
	                	boolean prevFlagStatus = cells[cCol][cRow].getIsFlagged();
	                	cells[cCol][cRow].setIsFlagged(!cells[cCol][cRow].getIsFlagged());
	                	
	                	if (cells[cCol][cRow].getIsFlagged())
	                	{
	                		numFlags++;
	                	}
	                	else numFlags--;
	                	
	                	if (cells[cCol][cRow].getValue() == BOMB && cells[cCol][cRow].getIsFlagged())
	                	{
	                		flaggedMines++;
	                		statusBar.setText("Number of flagged Mines: " + flaggedMines);
	                	}
	                	
	                	if (cells[cCol][cRow].getValue() == BOMB && prevFlagStatus == true)
	                	{
	                		flaggedMines--;
	                		statusBar.setText("Number of flagged Mines: " + flaggedMines);
	                	}
	                	
	                	/*if (cells[cCol][cRow].getValue() == BOMB && prevFlagStatus == false)
	                	{
	                		flaggedMines++;
	                		statusBar.setText("Number of flagged Mines: " + flaggedMines);
	                	}*/
	                	
	                	if (flaggedMines == numMines && numFlags == flaggedMines)
	                	{
	                		//if(cells[cCol][cRow].getIsFlagged()) cells[cCol][cRow].setImageIndex(FLAG);
	                		gameOver(true);
	                	}
                	
	                	rep = true;
	                }

	                if (rep) 
	                {
	                    repaint();
	                    rep = false;
	                }
	                
	                if (!inGame)
	                {
	                	
	                	for ( int i = 0; i < gridDimY; i++)
	            		{
	            			for ( int j = 0; j < gridDimX; j++)
			            	{
			    				if (cells[j][i].getImageIndex() == BOMB)
			    				{
			    					cells[j][i].setValue(BOMB);
			    				}
			    				
			    				/*if (cells[j][i].getImageIndex() == FLAG && cells[j][i].getValue() == BOMB)
			    				{
			    					cells[j][i].setValue(FLAG);
			    				}*/
			            	}
	            		}
	                }
	            }
        	}
        }
	}
}
