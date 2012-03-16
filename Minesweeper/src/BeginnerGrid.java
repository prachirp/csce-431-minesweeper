import java.util.Random;

import java.awt.Color;
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
	private final int BOMB = 11;
	private final int RED_BOMB = 12;
	private final int NUM_IMAGES = 13;
	private final int CELL_SIZE = 19;
	private final int COVERED_MINE_CELL = COVER_FOR_CELL + BOMB;
	private final int FLAGGED_MINE_CELL = FLAG + BOMB;
	
	private Image[] img;
	private int numMines = 10;
	private boolean inGame = true;
	
	private JLabel statusBar;
	Cell[][] cells = new Cell[9][9];
	
	/// Constructor for BeginnerGrid, 9 x 9 with 10 mines
	public BeginnerGrid(JLabel statusBar) {
		
		this.statusBar = statusBar;
		
		img = new Image[NUM_IMAGES];
		
		for (int i = 0; i < NUM_IMAGES; i++)
		{
			img[i] = new ImageIcon(this.getClass().getResource((i) + ".png")).getImage();
		}
		
		
		setDoubleBuffered(true);
		
		// TODO Add Mouse Listener
		
		// Build and populate grid with cells.
		for ( int i = 0; i < 9; i++)
		{
			for ( int j = 0; j < 9; j++)
			{
				cells[i][j] = new Cell(COVER_FOR_CELL);
			}
		}
		
		
		this.statusBar.setText("Mines Left: " + numMines);
		initializeBoard(numMines);
		testGrid();
	}
	
	/// Places mines in random cells, initializes the numbers in non-mine cells
	private void initializeBoard(int _numMines)
	{
		// Generate 10 bombs
		Random random = new Random();
		int randIndexX = random.nextInt(9);
		int randIndexY = random.nextInt(9);
		int numBombs = 0;
		
		while (numBombs != _numMines)
		{
			if ( cells[randIndexX][randIndexY].getValue() != BOMB + COVER_FOR_CELL )
			{
				cells[randIndexX][randIndexY] = new Cell(BOMB + COVER_FOR_CELL);
				numBombs++;
				
				if ( randIndexX == 0 )	// we are at the left edge
				{
					if ( randIndexY == 0 ) // we are at the top left corner
					{
						int curVal;
						if ( cells[randIndexX + 1][randIndexY].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX + 1][randIndexY].getValue();
							cells[randIndexX + 1][randIndexY].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX][randIndexY + 1].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX][randIndexY + 1].getValue();	
							cells[randIndexX][randIndexY + 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX + 1][randIndexY + 1].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX + 1][randIndexY + 1].getValue();	
							cells[randIndexX + 1][randIndexY + 1].setValue(curVal + 1);
						}
					}
					
					else if ( randIndexY == 8 ) // we are at the bottom left corner
					{
						int curVal;
						if ( cells[randIndexX + 1][randIndexY].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX + 1][randIndexY].getValue();
							cells[randIndexX + 1][randIndexY].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX][randIndexY - 1].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX][randIndexY - 1].getValue();
							cells[randIndexX][randIndexY - 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX + 1][randIndexY - 1].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX + 1][randIndexY - 1].getValue();
							cells[randIndexX + 1][randIndexY - 1].setValue(curVal + 1);
						}
					}
					
/*stopped here*/	else	// we are somewhere along the left edge
					{
						int curVal;	
						if ( cells[randIndexX + 1][randIndexY].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX + 1][randIndexY].getValue();
							cells[randIndexX + 1][randIndexY].setValue(curVal + 1);
						}
						
						
						if ( cells[randIndexX][randIndexY - 1].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX][randIndexY - 1].getValue();
							cells[randIndexX][randIndexY - 1].setValue(curVal + 1);
						}
						
						
						if ( cells[randIndexX][randIndexY + 1].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX][randIndexY + 1].getValue();
							cells[randIndexX][randIndexY + 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX + 1][randIndexY + 1].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX + 1][randIndexY + 1].getValue();
							cells[randIndexX + 1][randIndexY + 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX + 1][randIndexY - 1].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX + 1][randIndexY - 1].getValue();
							cells[randIndexX + 1][randIndexY - 1].setValue(curVal + 1);
						}
					}
				}
				
				else if ( randIndexX == 8 )		// we are at right edge
				{
					if ( randIndexY == 0 ) // we are at the top right corner
					{
						int curVal;
						if ( cells[randIndexX - 1][randIndexY].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX - 1][randIndexY].getValue();
							cells[randIndexX - 1][randIndexY].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX][randIndexY + 1].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX][randIndexY + 1].getValue();
							cells[randIndexX][randIndexY + 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX - 1][randIndexY + 1].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX - 1][randIndexY + 1].getValue();
							cells[randIndexX - 1][randIndexY + 1].setValue(curVal + 1);
						}
					}
					
					else if ( randIndexY == 8 ) // we are at the bottom right corner
					{
						int curVal;
						if ( cells[randIndexX - 1][randIndexY].getValue() != BOMB + COVER_FOR_CELL )
						{
							 curVal = cells[randIndexX - 1][randIndexY].getValue();
							 cells[randIndexX - 1][randIndexY].setValue(curVal + 1);
						}
							
						if ( cells[randIndexX][randIndexY - 1].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX][randIndexY - 1].getValue();
							cells[randIndexX][randIndexY - 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX - 1][randIndexY - 1].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX - 1][randIndexY - 1].getValue();
							cells[randIndexX - 1][randIndexY - 1].setValue(curVal + 1);
						}
					}
					
					else	// we are somewhere along the right edge
					{
						int curVal;
						if ( cells[randIndexX - 1][randIndexY].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX - 1][randIndexY].getValue();
							cells[randIndexX - 1][randIndexY].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX][randIndexY - 1].getValue() != BOMB + COVER_FOR_CELL )
						{
							curVal = cells[randIndexX][randIndexY - 1].getValue();
							cells[randIndexX][randIndexY - 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX][randIndexY + 1].getValue() != BOMB + COVER_FOR_CELL ) {
							curVal = cells[randIndexX][randIndexY + 1].getValue();
							cells[randIndexX][randIndexY + 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX - 1][randIndexY + 1].getValue() != BOMB + COVER_FOR_CELL ) {
							curVal = cells[randIndexX - 1][randIndexY + 1].getValue();
							cells[randIndexX - 1][randIndexY + 1].setValue(curVal + 1);
						}
						
						if ( cells[randIndexX - 1][randIndexY - 1].getValue() != BOMB + COVER_FOR_CELL ) {
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
					if ( cells[randIndexX - 1][randIndexY].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX - 1][randIndexY].getValue();
						cells[randIndexX - 1][randIndexY].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX + 1][randIndexY].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX + 1][randIndexY].getValue();
						cells[randIndexX + 1][randIndexY].setValue(curVal + 1);
					}
					
					
					if ( cells[randIndexX][randIndexY + 1].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX][randIndexY + 1].getValue();
						cells[randIndexX][randIndexY + 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX - 1][randIndexY + 1].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX - 1][randIndexY + 1].getValue();
						cells[randIndexX - 1][randIndexY + 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX + 1][randIndexY + 1].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX + 1][randIndexY + 1].getValue();
						cells[randIndexX + 1][randIndexY + 1].setValue(curVal + 1);
					}
					
				}
				
				else if (randIndexY == 8)		// we are along the bottom of the board
				{
					// Corners are handled by earlier cases, so we only need the case where
					// we are somewhere along the bottom
					int curVal;
					if ( cells[randIndexX - 1][randIndexY].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX - 1][randIndexY].getValue();
						cells[randIndexX - 1][randIndexY].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX + 1][randIndexY].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX + 1][randIndexY].getValue();
						cells[randIndexX + 1][randIndexY].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX][randIndexY - 1].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX][randIndexY - 1].getValue();
						cells[randIndexX][randIndexY - 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX - 1][randIndexY - 1].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX - 1][randIndexY - 1].getValue();
						cells[randIndexX - 1][randIndexY - 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX + 1][randIndexY - 1].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX + 1][randIndexY - 1].getValue();
						cells[randIndexX + 1][randIndexY - 1].setValue(curVal + 1);
					}
				}
				
				else							// we are somewhere in the middle
				{
					int curVal;
					if ( cells[randIndexX - 1][randIndexY].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX - 1][randIndexY].getValue();
						cells[randIndexX - 1][randIndexY].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX][randIndexY - 1].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX][randIndexY - 1].getValue();
						cells[randIndexX][randIndexY - 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX][randIndexY + 1].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX][randIndexY + 1].getValue();
						cells[randIndexX][randIndexY + 1].setValue(curVal + 1);
					}
					
					// DIAGONALS BELOW
					
					if ( cells[randIndexX + 1][randIndexY + 1].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX + 1][randIndexY + 1].getValue();
						cells[randIndexX + 1][randIndexY + 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX + 1][randIndexY - 1].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX + 1][randIndexY - 1].getValue();
						cells[randIndexX + 1][randIndexY - 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX - 1][randIndexY + 1].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX - 1][randIndexY + 1].getValue();
						cells[randIndexX - 1][randIndexY + 1].setValue(curVal + 1);
					}
					
					if ( cells[randIndexX - 1][randIndexY - 1].getValue() != BOMB + COVER_FOR_CELL )
					{
						curVal = cells[randIndexX - 1][randIndexY - 1].getValue();
						cells[randIndexX - 1][randIndexY - 1].setValue(curVal + 1);
					}
				}
				
				
			}
			
			randIndexX = random.nextInt(9);
			randIndexY = random.nextInt(9);
		}
		
		//drawCells(this.);
		//this.setVisible(true);
		//this.getGraphics().setColor(Color.red);
		repaint();
		
	}
	
	/// For testing: prints the underlying values of the grid
	public void testGrid() 
	{
		for ( int i = 0; i < 9; i++)
		{
			for ( int j = 0; j < 9; j++)
			{
				System.out.print(cells[j][i].getValue() + " ");
			}
			System.out.println();
		}
	}

	/// Called when a non-mine is clicked, finds and uncovers all the appropriate cells
	public void findEmptyCells(int i, int j)
	{
		// TODO Write findEmptyCells() Function
	    
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

        Cell cell;
        int uncover = 0;


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                cell = cells[i][j];

                if (inGame && cell.getValue() == BOMB)
                    inGame = false;

                if (!inGame)
                {
                    if (cell.getValue() == COVERED_MINE_CELL)
                    {
                        cell.setValue(BOMB);
                    } 
                    else if (cell.getValue() == FLAGGED_MINE_CELL)
                    {
                        cell.setValue(FLAG);
                    }
                    else if (cell.getValue() > COVERED_MINE_CELL)
                    {
                        cell.setValue(RED_BOMB);
                    } 
                    else if (cell.getValue() > BOMB)
                    {
                        cell.setValue(COVER_FOR_CELL);						//// MAYBE?
                    }


                }
                
                else
                {
                    if (cell.getValue() > COVERED_MINE_CELL)
                        cell.setValue(FLAG);
                    else if (cell.getValue() > BOMB)
                    {
                        cell.setValue(COVER_FOR_CELL);
                        uncover++;
                    }
                }

                g.drawImage(img[cell.getValue()], (j * CELL_SIZE), (i * CELL_SIZE), this);
            }
        }


        if (uncover == 0 && inGame) {
            inGame = false;
            statusBar.setText("Game won");
        } 
        else if (!inGame)
            statusBar.setText("Game lost");
		}
}
