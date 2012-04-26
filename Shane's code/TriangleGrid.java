import java.util.Random;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TriangleGrid extends JPanel
{
	//0-11 are really 1-6 for cell value images
	private final int EMPTY_UP=12;
	private final int EMPTY_DOWN=13;
	private final int COVER_UP=14;
	private final int COVER_DOWN=15;
	private final int BOMB_UP=16;
	private final int BOMB_DOWN=17;
	private final int RED_BOMB_UP=18;
	private final int RED_BOMB_DOWN=19;
	private final int FLAG_UP=20;
	private final int FLAG_DOWN=21;
	private final int WRONG_FLAG_UP=22;
	private final int WRONG_FLAG_DOWN=23;
	private final int NUM_IMAGES=24;
	private final int CELL_SIZE=25;
	
	private Image[] img;
	private int numMines=10;
	private int gridDimX, gridDimY;
	private boolean inGame = true;
	private boolean mouseInputEnabled=true;
	private int flaggedMines=0;
	private int numFlags=0;
	private StopWatch s;
	private Record record=new Record();

	private JLabel statusBar;
	public String message, title;
	TriangleCell[][] cells;
	
	public TriangleGrid() {}
	
	public TriangleGrid(JLabel statusBar, int gridDimensionX, int gridDimensionY, int numBombs, StopWatch sw)
	{
		numMines=numBombs;
		gridDimX=gridDimensionX;
		gridDimY=gridDimensionY;
		cells=new TriangleCell[gridDimX][gridDimY];
		s=sw;
		
		this.statusBar=statusBar;
		
		img=new Image[NUM_IMAGES];
		for(int i=0;i<NUM_IMAGES;i++) //initialize image vector
		{
			img[i]=new ImageIcon(this.getClass().getResource((i)+".png")).getImage();
		}
		
		setDoubleBuffered(true);
		addMouseListener(new MinesAdapter());
		
		//Build grid
		for(int i=0;i<gridDimX;i++)
			for(int j=0;j<gridDimY;j++)
			{
				if(i%2==0)
					cells[i][j]=new TriangleCell(14); //UP triangle
				else
					cells[i][j]=new TriangleCell(15); //DOWN triangle
			}
			
		this.statusBar.setText("Mines Left: "+numMines);
		setBombs(numMines);
	}
	
	private void setBombs(int _numMines) //randomly assigns bombs and sets adjacent values
	{
		Random random = new Random();
		int randIndexX = random.nextInt(gridDimX);
		int randIndexY = random.nextInt(gridDimY);
		int numBombs = 0;
		
		while (numBombs != _numMines)
		{
			if((cells[randIndexX][randIndexY].getValue()!=BOMB_UP) || (cells[randIndexX][randIndexY].getValue()!=BOMB_DOWN))
			{
				if(randIndexX%2==0)
					cells[randIndexX][randIndexY] = new TriangleCell(BOMB_UP);
				else
					cells[randIndexX][randIndexY] = new TriangleCell(BOMB_DOWN);
				numBombs++;
				
				if(randIndexX == 0) //left edge
				{
					//(x+1,y) always checked/set for left edge
					if((cells[randIndexX+1][randIndexY].getValue()!=BOMB_UP) || (cells[randIndexX+1][randIndexY].getValue()!=BOMB_DOWN))
						cells[randIndexX+1][randIndexY].setValue(cells[randIndexX+1][randIndexY].getValue() + 1);
					
					if(randIndexY == 0) //top left corner
					{
						//(x,y+1) (x+1,y+1)
						//type is always UP
						if((cells[randIndexX][randIndexY+1].getValue()!=BOMB_UP) || (cells[randIndexX][randIndexY+1].getValue()!=BOMB_DOWN))
							cells[randIndexX][randIndexY+1].setValue(cells[randIndexX][randIndexY+1].getValue() + 1);
						if((cells[randIndexX+1][randIndexY+1].getValue()!=BOMB_UP) || (cells[randIndexX+1][randIndexY+1].getValue()!=BOMB_DOWN))
							cells[randIndexX+1][randIndexY+1].setValue(cells[randIndexX+1][randIndexY+1].getValue() + 1);
					}
					if(randIndexY == gridDimY-1) //bottom left corner
					{
						//(x,y-1)
						//type is always UP
						if((cells[randIndexX][randIndexY-1].getValue()!=BOMB_UP) || (cells[randIndexX][randIndexY-1].getValue()!=BOMB_DOWN))
							cells[randIndexX][randIndexY-1].setValue(cells[randIndexX][randIndexY-1].getValue() + 1);
					}
					else //somewhere on left edge
					{
						//(x,y-1) (x,y+1) (x+1,y+1)
						//type is always UP
						if((cells[randIndexX][randIndexY-1].getValue()!=BOMB_UP) || (cells[randIndexX][randIndexY-1].getValue()!=BOMB_DOWN))
							cells[randIndexX][randIndexY-1].setValue(cells[randIndexX][randIndexY-1].getValue() + 1);
						if((cells[randIndexX][randIndexY+1].getValue()!=BOMB_UP) || (cells[randIndexX][randIndexY+1].getValue()!=BOMB_DOWN))
							cells[randIndexX][randIndexY+1].setValue(cells[randIndexX][randIndexY+1].getValue() + 1);
						if((cells[randIndexX+1][randIndexY+1].getValue()!=BOMB_UP) || (cells[randIndexX+1][randIndexY+1].getValue()!=BOMB_DOWN))
							cells[randIndexX+1][randIndexY+1].setValue(cells[randIndexX+1][randIndexY+1].getValue() + 1);
					}
				}
				
				else if(randIndexX == gridDimx-1) //right edge
				{
					//(x-1,y) always checked/set for right edge
					if((cells[randIndexX-1][randIndexY].getValue()!=BOMB_UP) || (cells[randIndexX-1][randIndexY].getValue()!=BOMB_DOWN))
						cells[randIndexX-1][randIndexY].setValue(cells[randIndexX-1][randIndexY].getValue() + 1);
					
					if(randIndexY == 0) //top right corner
					{
						//(x,y+1)
						//type is not known
						//UP: (x-1,y+1)
						if(randIndexX%2==0) //UP cell
							if((cells[randIndexX-1][randIndexY+1].getValue()!=BOMB_DOWN))
								cells[randIndexX-1][randIndexY+1].setValue(cells[randIndexX-1][randIndexY+1].getValue() + 1);
						if((cells[randIndexX][randIndexY+1].getValue()!=BOMB_UP) || (cells[randIndexX][randIndexY+1].getValue()!=BOMB_DOWN))
							cells[randIndexX][randIndexY+1].setValue(cells[randIndexX][randIndexY+1].getValue() + 1);
					}
					if(randIndexY == gridDimY-1) //bottom right corner
					{
						//(x,y-1) (x-1,y-1)
						//type is alwasy DOWN on 10x10 grid
						if((cells[randIndexX][randIndexY-1].getValue()!=BOMB_UP) || (cells[randIndexX][randIndexY-1].getValue()!=BOMB_DOWN))
							cells[randIndexX][randIndexY-1].setValue(cells[randIndexX][randIndexY-1].getValue() + 1);
						if((cells[randIndexX-1][randIndexY-1].getValue()!=BOMB_UP) || (cells[randIndexX-1][randIndexY-1].getValue()!=BOMB_DOWN))
							cells[randIndexX-1][randIndexY-1].setValue(cells[randIndexX-1][randIndexY-1].getValue() + 1);
					}
					else //somewhere on right edge
					{
						//(x-1,y-1) (x,y-1) (x,y+1)
						//type is always DOWN on 10x10 grid
						if((cells[randIndexX][randIndexY-1].getValue()!=BOMB_UP) || (cells[randIndexX][randIndexY-1].getValue()!=BOMB_DOWN))
							cells[randIndexX][randIndexY-1].setValue(cells[randIndexX][randIndexY-1].getValue() + 1);
						if((cells[randIndexX][randIndexY+1].getValue()!=BOMB_UP) || (cells[randIndexX][randIndexY+1].getValue()!=BOMB_DOWN))
							cells[randIndexX][randIndexY+1].setValue(cells[randIndexX][randIndexY+1].getValue() + 1);
						if((cells[randIndexX-1][randIndexY-1].getValue()!=BOMB_UP) || (cells[randIndexX-1][randIndexY-1].getValue()!=BOMB_DOWN))
							cells[randIndexX-1][randIndexY-1].setValue(cells[randIndexX-1][randIndexY-1].getValue() + 1);
					}
				}
				
				else if(randIndexY==0 && (randIndexX!=0 || randIndexX!=gridDimX-1)) //top edge **not at corners**
				{
					//type is not known
					//for UP: (x-1,y+1) (x+1,y+1)
					//for DOWN: 
					//for both: (x-1,y) (x+1,y) (x,y+1)
					if((cells[randIndexX-1][randIndexY].getValue()!=BOMB_UP) || (cells[randIndexX-1][randIndexY].getValue()!=BOMB_DOWN))
						cells[randIndexX-1][randIndexY].setValue(cells[randIndexX-1][randIndexY].getValue() + 1);
					if((cells[randIndexX+1][randIndexY].getValue()!=BOMB_UP) || (cells[randIndexX+1][randIndexY].getValue()!=BOMB_DOWN))
						cells[randIndexX+1][randIndexY].setValue(cells[randIndexX+1][randIndexY].getValue() + 1);
					if((cells[randIndexX][randIndexY+1].getValue()!=BOMB_UP) || (cells[randIndexX][randIndexY+1].getValue()!=BOMB_DOWN))
						cells[randIndexX][randIndexY+1].setValue(cells[randIndexX][randIndexY+1].getValue() + 1);
					if(randIndexX%2==0) //type is UP
					{
						if((cells[randIndexX+1][randIndexY+1].getValue()!=BOMB_UP) || (cells[randIndexX+1][randIndexY+1].getValue()!=BOMB_DOWN))
							cells[randIndexX+1][randIndexY+1].setValue(cells[randIndexX+1][randIndexY+1].getValue() + 1);
						if((cells[randIndexX-1][randIndexY+1].getValue()!=BOMB_UP) || (cells[randIndexX-1][randIndexY+1].getValue()!=BOMB_DOWN))
							cells[randIndexX-1][randIndexY+1].setValue(cells[randIndexX-1][randIndexY+1].getValue() + 1);
					}
				}
				
				else if(randIndexY==gridDimY-1 && (randIndexX!=0 || randIndexX!=gridDimX-1)) //bottom edge **not at corners**
				{
					//type is not known
					//for UP: 
					//for DOWN: (x-1,y-1) (x+1,y-1)
					//for both: (x-1,y) (x+1,y) (x,y-1)
					if((cells[randIndexX-1][randIndexY].getValue()!=BOMB_UP) || (cells[randIndexX-1][randIndexY].getValue()!=BOMB_DOWN))
						cells[randIndexX-1][randIndexY].setValue(cells[randIndexX-1][randIndexY].getValue() + 1);
					if((cells[randIndexX+1][randIndexY].getValue()!=BOMB_UP) || (cells[randIndexX+1][randIndexY].getValue()!=BOMB_DOWN))
						cells[randIndexX+1][randIndexY].setValue(cells[randIndexX+1][randIndexY].getValue() + 1);
					if((cells[randIndexX][randIndexY-1].getValue()!=BOMB_UP) || (cells[randIndexX][randIndexY-1].getValue()!=BOMB_DOWN))
						cells[randIndexX][randIndexY-1].setValue(cells[randIndexX][randIndexY-1].getValue() + 1);
					if(randIndexX%2==1) //type is DOWN
					{
						if((cells[randIndexX-1][randIndexY-1].getValue()!=BOMB_UP) || (cells[randIndexX-1][randIndexY-1].getValue()!=BOMB_DOWN))
							cells[randIndexX-1][randIndexY-1].setValue(cells[randIndexX-1][randIndexY-1].getValue() + 1);
						if((cells[randIndexX+1][randIndexY-1].getValue()!=BOMB_UP) || (cells[randIndexX+1][randIndexY-1].getValue()!=BOMB_DOWN))
							cells[randIndexX+1][randIndexY-1].setValue(cells[randIndexX+1][randIndexY-1].getValue() + 1);
					}
				}
				
				else //somewhere in the middle
				{
					//type is not known
					//for UP: (x-1,y+1) (x+1,y+1)
					//for DOWN: (x-1,y-1) (x+1,y-1)
					//for both: (x,y-1) (x-1,y) (x+1,y) (x,y+1)
					if((cells[randIndexX+1][randIndexY].getValue()!=BOMB_UP) || (cells[randIndexX+1][randIndexY].getValue()!=BOMB_DOWN))
						cells[randIndexX+1][randIndexY].setValue(cells[randIndexX+1][randIndexY].getValue() + 1);
					if((cells[randIndexX-1][randIndexY].getValue()!=BOMB_UP) || (cells[randIndexX-1][randIndexY].getValue()!=BOMB_DOWN))
						cells[randIndexX-1][randIndexY].setValue(cells[randIndexX-1][randIndexY].getValue() + 1);
					if((cells[randIndexX][randIndexY-1].getValue()!=BOMB_UP) || (cells[randIndexX][randIndexY-1].getValue()!=BOMB_DOWN))
						cells[randIndexX][randIndexY-1].setValue(cells[randIndexX][randIndexY-1].getValue() + 1);
					if((cells[randIndexX][randIndexY+1].getValue()!=BOMB_UP) || (cells[randIndexX][randIndexY+1].getValue()!=BOMB_DOWN))
						cells[randIndexX][randIndexY+1].setValue(cells[randIndexX][randIndexY+1].getValue() + 1);
					if(randIndexX%2==0) //type is UP
					{
						if((cells[randIndexX-1][randIndexY+1].getValue()!=BOMB_UP) || (cells[randIndexX-1][randIndexY+1].getValue()!=BOMB_DOWN))
							cells[randIndexX-1][randIndexY+1].setValue(cells[randIndexX-1][randIndexY+1].getValue() + 1);
						if((cells[randIndexX+1][randIndexY+1].getValue()!=BOMB_UP) || (cells[randIndexX+1][randIndexY+1].getValue()!=BOMB_DOWN))
							cells[randIndexX+1][randIndexY+1].setValue(cells[randIndexX+1][randIndexY+1].getValue() + 1);
					}
					else //type is DOWN
					{
						if((cells[randIndexX-1][randIndexY-1].getValue()!=BOMB_UP) || (cells[randIndexX-1][randIndexY-1].getValue()!=BOMB_DOWN))
							cells[randIndexX-1][randIndexY-1].setValue(cells[randIndexX-1][randIndexY-1].getValue() + 1);
						if((cells[randIndexX+1][randIndexY-1].getValue()!=BOMB_UP) || (cells[randIndexX+1][randIndexY-1].getValue()!=BOMB_DOWN))
							cells[randIndexX+1][randIndexY-1].setValue(cells[randIndexX+1][randIndexY-1].getValue() + 1);
					}
				}
			}
			randIndexX=random.nextInt(gridDimX);
			randIndexY=random.nextInt(gridDimY);
		}
		
		//Set image index to value of cell
		for(int i=0;i<gridDimX;i++)
			for(int j=0;j<gridDimY;j++)
			{
				if(cells[i][j].getValue()!=BOMB_UP || cells[i][j].getValue()!=BOMB_DOWN)
				{
					if(i%2==0) //type UP
					{
						switch cells[i][j].getValue()
						{
							case 0:
								cells[i][j].setImageIndex(12);
								break;
							case 1:
								cells[i][j].setImageIndex(0);
								break;
							case 2:
								cells[i][j].setImageIndex(2);
								break;
							case 3:
								cells[i][j].setImageIndex(4);
								break;
							case 4:
								cells[i][j].setImageIndex(6);
								break;
							case 5:
								cells[i][j].setImageIndex(8);
								break;
							case 6:
								cells[i][j].setImageIndex(10);
								break;
						}
					}
					else //type DOWN
					{
						switch cells[i][j].getValue()
						{
							case 0:
								cells[i][j].setImageIndex(13);
								break;
							case 1:
								cells[i][j].setImageIndex(1);
								break;
							case 2:
								cells[i][j].setImageIndex(3);
								break;
							case 3:
								cells[i][j].setImageIndex(5);
								break;
							case 4:
								cells[i][j].setImageIndex(7);
								break;
							case 5:
								cells[i][j].setImageIndex(9);
								break;
							case 6:
								cells[i][j].setImageIndex(11);
								break;
						}
					}
				}	
			}
	}				
		
	public void findEmptyCells(int i, int j) //called when non-mine cell is clicked, finds and uncovers all empty cells
	{
		if((cells[i][j].getValue()==EMPTY_UP || cells[i][j].getValue()==EMPTY_DOWN) && !cells[i][j].getIsUncovered() && !cells[i][j].getIsFlagged())
		{
			cells[i][j].setIsUncovered(true);
			//adjacent cells can vary for type of cell
			//for UP: (x-1,y+1) (x+1,y+1)
			//for DOWN: (x-1,y-1) (x+1,y-1)
			//for both: (x-1,y) (x+1,y) (x,y-1) (x,y+1)
			if(i<gridDimX-1)			//right neighbor
				findEmptyCells(i+1,j);
			if(j<gridDimY-1)			//bottom neighbor
				findEmptyCells(i,j+1);
			if(i>0)						//left neighbor
				findEmptyCells(i-1,j);
			if(j>0)						//top neighbor
				findEmptyCells(i,j-1);
			if(i<gridDimX-1 && j<gridDimY-1 && i%2==0) 	//must be type UP to check this way
				findEmptyCells(i+1,j+1);
			if(i>0 && j<gridDimY-1 && i%2==0)  			//must be type UP to check this way
				findEmptyCells(i-1,j+1);
			if(i>0 && j>0 && i%2==1) 					//must be type DOWN to check this way
				findEmptyCells(i-1,j-1);
			if(i<gridDimX-1 && j>0 && i%2==1) 			//must be type DOWN to check this way
				findEmptyCells(i+1,j-1);

		}
		if(!cells[i][j].getIsFlagged())
			cells[i][j].setIsUncovered(true);
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		TriangleCell cell;
		
		for(int i=0;i<gridDimX;i++)
			for(int j=0;j<gridDimY;j++)
			{
				cell=cells[i][j];
				if(!cell.getIsUncovered())
				{
					if(i%2==0)
						cell.setImageIndex(COVER_UP);
					else
						cell.setImageIndex(COVER_DOWN);
				}
				if(cell.getIsUncovered())
				{
					if(i%2==0) //UP cell
						switch cell.getValue()
						{
							case 1:
								cell.setImageIndex(0);
								break;
							case 2:
								cell.setImageIndex(2);
								break;
							case 3:
								cell.setImageIndex(4);
								break;
							case 4:
								cell.setImageIndex(6);
								break;
							case 5:
								cell.setImageIndex(8);
								break;
							case 6:
								cell.setImageIndex(10);
								break;
							case 16:
								cell.setImageIndex(18);
								break;
						}
					else //DOWN cell
						switch cell.getValue()
						{
							case 1:
								cell.setImageIndex(1);
								break;
							case 2:
								cell.setImageIndex(3);
								break;
							case 3:
								cell.setImageIndex(5);
								break;
							case 4:
								cell.setImageIndex(7);
								break;
							case 5:
								cell.setImageIndex(9);
								break;
							case 6:
								cell.setImageIndex(11);
								break;
							case 17:
								cell.setImageIndex(19);
								break;
						}
				}
				else if(cell.getIsFlagged())
				{
					if(i%2==0) //UP cell
						cell.setImageIndex(FLAG_UP);
					else //DOWN cell
						cell.setImageIndex(FLAG_DOWN);
				}
				
				g.drawImage(img[cell.getImageIndex()], (i*13), (j*CELL_SIZE), this);
			}
		
	}
	
	public void gameOver(boolean winner)
	{
		inGame = false;
		
		for(int i=0;i<gridDimY;i++)
			for(int j=0;j<gridDimX;j++)
			{
				if(winner)
				{
					if(cells[i][j].getImageIndex()==COVER_UP || cells[i][j].getImageIndex()==COVER_DOWN)
						cells[i][j].setIsUncovered(true);
					if(cells[i][j].getValue()==BOMB_UP || cells[i][j].getValue()==BOMB_DOWN)
					{
						if(i%2==0)
							cells[i][j].setImageIndex(BOMB_UP);
						else
							cells[i][j].setImageIndex(BOMB_DOWN);
						//cells[i][j].setIsUncovered(true);
					}
					repaint();
				}
				else
				{
					if(cells[i][j].getImageIndex()==FLAG_UP || cells[i][j].getImageIndex()==FLAG_DOWN) //flagged cell
					{
						if(cells[i][j].getValue()==BOMB_UP || cells[i][j].getValue()==BOMB_DOWN)
						{}
						if(cells[i][j].getValue()!=BOMB_UP || cells[i][j].getValue()!=BOMB_DOWN)
						{
							if(i%2==0)
								cells[i][j].setValue(WRONG_FLAG_UP);
							else
								cells[i][j].setValue(WRONG_FLAG_DOWN);
							cells[i][j].setIsUncovered(true);
						}
					}
					else													//non flagged cell
						cells[i][j].setIsUncovered(true);
				}
			}
			
		if(winner)
		{
			s.pause();
			record.run(s.getTime());
			message="Hooray! You Won!";
			title=":D";
		}
		else
		{
			s.reset();
			message="Oh no! You lost!";
			title="D:";
		}
		
		JOptionPane.showMessageDialog(this,message,title,0);
		repaint();
		mouseInputEnabled=false;
	}
	
	class MinesAdapter extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
            int x = e.getX();
            int y = e.getY();
            int cCol;
            int cRow;
            boolean rep = false;

			//figure out ccol and crow right here
			int xx=x/(CELL_SIZE/2);
			int yy=y/CELL_SIZE);
			cRow=yy;
			int starty;
			//if x<12 set flag
			//if x>
			
			if(xx%2==0)  //even
			{
				starty=(yy*25)+25;
				for(int i=(xx*13);i<=(xx*13)+13;i++)
					if(i==x)
					{
						if(starty>=y)
							cCol==xx;
						else if(starty<y)
							cCol==xx-1;
						starty-=2;
					}
			}
			else if(xx%2==1)  //odd
			{
				starty=(yy*25);
				for(int i=(xx*13);i<=(xx*13)+13;i++)
					if(i==x)
					{
						if(starty>=y)
							cCol=xx;
						else if(starty<y)
							cCol=xx-1;
						starty+=2;
					}
			}

            if((x<gridDimX*CELL_SIZE && y < gridDimY * CELL_SIZE) && (cCol>=0 && cCol<gridDimX))
			{
				s.start();
				if(e.getButton() == MouseEvent.BUTTON1) 
				{
					if(cells[cCol][cRow].getValue() == EMPTY_UP || cells[cCol][cRow].getValue()==EMPTY_DOWN)
						findEmptyCells(cCol, cRow);
					else 
						cells[cCol][cRow].setIsUncovered(true);
				
					if(cells[cCol][cRow].getValue()==BOMB_UP)
					{
						cells[cCol][cRow].setValue(RED_BOMB_UP);
						gameOver(false);
					}
					if(cells[cCol][cRow].getValue()==BOMB_DOWN)
					{
						cells[cCol][cRow].setValue(RED_BOMB_UP);
						gameOver(false);
					}
					rep = true;
				}
			
				else if(e.getButton() == MouseEvent.BUTTON3)
				{
					////////// if it's not covered or isn't already flagged, BUTTON3 does nothing ////////////////
					if(cells[i][j].getImageIndex()!=COVER_UP || cells[i][j].getImageIndex()!=COVER_DOWN || cells[i][j].getImageIndex()!=FLAG_UP || cells[i][j].getImageIndex()!=FLAG_DOWN)
						return;
					////////////////////////////////////////////////////////////////////////////////////////////
					
					//flip cell's flag T/F
					boolean prevFlagStatus = cells[cCol][cRow].getIsFlagged();
					cells[cCol][cRow].setIsFlagged(!prevFlagStatus);
					
					if (cells[cCol][cRow].getIsFlagged())
						numFlags++;
					else
						numFlags--;
					
					if ((cells[cCol][cRow].getValue()==BOMB_UP || cells[cCol][cRow].getValue()==BOMB_DOWN) && cells[cCol][cRow].getIsFlagged())
					{
						flaggedMines++;
						statusBar.setText("Number of flagged Mines: " + flaggedMines);
					}
					if ((cells[cCol][cRow].getValue()==BOMB_UP || cells[cCol][cRow].getValue()==BOMB_DOWN) && prevFlagStatus==true)
					{
						flaggedMines--;
						statusBar.setText("Number of flagged Mines: " + flaggedMines);
					}
					if (flaggedMines == numMines && numFlags == flaggedMines)
					{
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
					for ( int i = 0; i < gridDimY; i++)
						for ( int j = 0; j < gridDimX; j++)
						{
							if(cells[i][j].getImageIndex()==BOMB_UP)
								cells[i][j].setValue(BOMB_UP);
							if(cells[i][j].getImageIndex()==BOMB_DOWN)
								cells[i][j].setValue(BOMB_DOWN);
						}
            }
        }
    }
	
}

