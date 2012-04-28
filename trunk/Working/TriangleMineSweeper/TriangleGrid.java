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
import java.util.*;

public class TriangleGrid extends JPanel{
	//0-11 are really 1-6 for cell value images
	private final int EMPTY_UP=0;
	private final int EMPTY_DOWN=1;
	private final int COVER_UP=26;
	private final int COVER_DOWN=27;
	private final int BOMB_UP=28;
	private final int BOMB_DOWN=29;
	private final int RED_BOMB_UP=30;
	private final int RED_BOMB_DOWN=31;
	private final int FLAG_UP=32;
	private final int FLAG_DOWN=33;
	private final int WRONG_FLAG_UP=34;
	private final int WRONG_FLAG_DOWN=35;
	private final int NUM_IMAGES=36;
	private final int CELL_SIZE=25;

	private Image[] img;
	private int numMines=10;
	private int gridDimX, gridDimY;
	private boolean inGame = true;
	private boolean mouseInputEnabled=true;
	private int flaggedMines=0;
	private int numFlags=0;
	private StopWatch s;
	private Record record;//=new Record();

	private JLabel statusBar;
	public String message, title;
	TriangleCell[][] cells;

	public TriangleGrid() {}

	public TriangleGrid(JLabel statBar, int gridDimensionX, int gridDimensionY, int numBombs){ //Stopwatch sw
		numMines=numBombs;
		gridDimX=gridDimensionX*2;
		gridDimY=gridDimensionY;
		cells=new TriangleCell[gridDimX][gridDimY];
		//s=sw;

		this.statusBar=statBar;

		img=new Image[NUM_IMAGES];
		for(int i=0;i<NUM_IMAGES;i++){ //initialize image vector
			img[i]=new ImageIcon(this.getClass().getResource((i)+".png")).getImage();
		}

		setDoubleBuffered(true);
		addMouseListener(new MinesAdapter());

		//Build grid
		for(int i=0;i<gridDimX;i++){
			for(int j=0;j<gridDimY;j++){
				if((i+j)%2==0){
					cells[i][j]=new TriangleCell(i, j, COVER_UP); //UP triangle
				}
				else{
					cells[i][j]=new TriangleCell(i, j, COVER_DOWN); //DOWN triangle
				}

				if(i == 0){//start left trim
					if(j == 0){//start top corner
						cells[i][j].getAdjacentArray().add(cells[i+1][j]);
						cells[i][j].getAdjacentArray().add(cells[i+2][j]);
						cells[i][j].getAdjacentArray().add(cells[i+2][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i][j+1]);
					}
					else if(j == gridDimY-1){//start bottom corner
						if((i+j)%2 == 0){ //bottom left up
							cells[i][j].getAdjacentArray().add(cells[i+1][j]);
							cells[i][j].getAdjacentArray().add(cells[i+2][j]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
						}
						else{//bottom left down
							cells[i][j].getAdjacentArray().add(cells[i+1][j]);
							cells[i][j].getAdjacentArray().add(cells[i+2][j]);
							cells[i][j].getAdjacentArray().add(cells[i+2][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
						}
					}//end bottom corner
					else{//7 neighbors
						if((i+j)%2==0){
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j]);
							cells[i][j].getAdjacentArray().add(cells[i+2][j]);
							cells[i][j].getAdjacentArray().add(cells[i+2][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i][j+1]);
						}
						else{
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i+2][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i+2][j]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i][j+1]);
						}
					}
				}//end left trim
				else if(i == 1){//start left border
					if(j == 0){//start top corner
						cells[i][j].getAdjacentArray().add(cells[i-1][j]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j]);
						cells[i][j].getAdjacentArray().add(cells[i+2][j]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
					}//end top corner
					else if(j == gridDimY-1){//start bottom corner
						if((i+j)%2==0){
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j]);
							cells[i][j].getAdjacentArray().add(cells[i+2][j]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
						}
						else{
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j]);
							cells[i][j].getAdjacentArray().add(cells[i+2][j]);
							cells[i][j].getAdjacentArray().add(cells[i+2][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
						}
					}//end bottom corner
					else{
						if((i+j)%2==0){
							cells[i][j].getAdjacentArray().add(cells[i+1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j]);
							cells[i][j].getAdjacentArray().add(cells[i+2][j]);
							cells[i][j].getAdjacentArray().add(cells[i+2][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
						}
						else{
							cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i+2][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i+2][j]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j+1]);
						}
					}
				}//end left border
				else if(i == gridDimX-1){//start right trim
					if(j == 0){//start top corner
						if((i+j)%2==0){
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i][j+1]);
						}
						else{
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i][j+1]);
						}
					}//end top corner
					else if(j == gridDimY-1){//start bottom corner
						if((i+j)%2==0){
							cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j]);
						}
						else{
							cells[i][j].getAdjacentArray().add(cells[i-2][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j]);
						}
					}//end bottom corner
					else{
						if((i+j)%2==0){
							cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i][j+1]);
						}
						else{
							cells[i][j].getAdjacentArray().add(cells[i-2][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i][j+1]);
						}
					}
				}//end right trim
				else if(i == gridDimX-2){//start right border
					if(j == 0){//start top corner
						if((i+j)%2==0){
							cells[i][j].getAdjacentArray().add(cells[i+1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j+1]);
						}
						else{
							cells[i][j].getAdjacentArray().add(cells[i+1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j+1]);	
						}
					}
					else if(j == gridDimY-1){//start bottom corner
						if((i+j)%2==0){
							cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j]);
						}
						else{
							cells[i][j].getAdjacentArray().add(cells[i-2][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i+1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j]);
						}
					}
					else{
						if((i+j)%2==0){
							cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i][j+1]);
						}
						else{
							cells[i][j].getAdjacentArray().add(cells[i-2][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i][j-1]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j]);
							cells[i][j].getAdjacentArray().add(cells[i-2][j]);
							cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
							cells[i][j].getAdjacentArray().add(cells[i][j+1]);
						}
					}
				}//end right border
				else if(j == 0){//top edge
					if((i+j)%2==0){
						cells[i][j].getAdjacentArray().add(cells[i+2][j]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j]);
						cells[i][j].getAdjacentArray().add(cells[i-1][j]);
						cells[i][j].getAdjacentArray().add(cells[i-2][j]);
						cells[i][j].getAdjacentArray().add(cells[i-2][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i+2][j+1]);
					}
					else{
						cells[i][j].getAdjacentArray().add(cells[i+1][j]);
						cells[i][j].getAdjacentArray().add(cells[i-1][j]);
						cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j+1]);	
					}
				}//end top edge
				else if(j == gridDimY-1){//bottom edge
					if((i+j)%2==0){
						cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j]);
						cells[i][j].getAdjacentArray().add(cells[i-1][j]);	
					}
					else{
						cells[i][j].getAdjacentArray().add(cells[i-2][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i+2][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i+2][j]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j]);
						cells[i][j].getAdjacentArray().add(cells[i-1][j]);
						cells[i][j].getAdjacentArray().add(cells[i-2][j]);
					}
				}//end bottom edge
				else{//middle
					if((i+j)%2==0){
						cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i+2][j]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j]);
						cells[i][j].getAdjacentArray().add(cells[i-1][j]);
						cells[i][j].getAdjacentArray().add(cells[i-2][j]);
						cells[i][j].getAdjacentArray().add(cells[i-2][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i+2][j+1]);
					}
					else{
						cells[i][j].getAdjacentArray().add(cells[i-2][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i-1][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i+2][j-1]);
						cells[i][j].getAdjacentArray().add(cells[i+2][j]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j]);
						cells[i][j].getAdjacentArray().add(cells[i-1][j]);
						cells[i][j].getAdjacentArray().add(cells[i-2][j]);
						cells[i][j].getAdjacentArray().add(cells[i-1][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i][j+1]);
						cells[i][j].getAdjacentArray().add(cells[i+1][j+1]);						
					}
				}//end middle
			}
		}
		statusBar.setText("Mines Left: "+ numMines);
		setBombs(numMines);
		testGrid();
	}

	private void setBombs(int _numMines){ //randomly assigns bombs and sets adjacent values
		Random random = new Random();
		int randIndexX = random.nextInt(gridDimX);
		int randIndexY = random.nextInt(gridDimY);
		int numBombs = 0;
		while (numBombs != _numMines){
			if((cells[randIndexX][randIndexY].getValue()!=BOMB_UP) || (cells[randIndexX][randIndexY].getValue()!=BOMB_DOWN)){
				if((randIndexX+randIndexY)%2==0){
					cells[randIndexX][randIndexY] = new TriangleCell(randIndexX, randIndexY, BOMB_UP);
				}
				else{
					cells[randIndexX][randIndexY] = new TriangleCell(randIndexX, randIndexY, BOMB_DOWN);
				}
				for(int m = 0; m < cells[randIndexX][randIndexY].getAdjacentArray().size(); m++){
					TriangleCell x = cells[randIndexX][randIndexY].getAdjacentArray().get(m);
					int a = x.getValue();
					x.setValue(a+1);
				}
				numBombs++;
				/*
				if(randIndexX == 0){//start left trim
					if(randIndexY == 0){//start top corner
						if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){ //top left up
							if(cells[randIndexX+1][randIndexY].getValue()!=BOMB_DOWN){
								cells[randIndexX+1][randIndexY].setValue(cells[randIndexX+1][randIndexY].getValue()++);
							}
							if(cells[randIndexX+2][randIndexY].getValue()!=BOMB_UP){
								cells[randIndexX+2][randIndexY].setValue(cells[randIndexX+2][randIndexY].getValue()++);
							}
							if(cells[randIndexX+2][randIndexY+1].getValue()!=BOMB_DOWN){
								cells[randIndexX+2][randIndexY+1].setValue(cells[randIndexX+2][randIndexY+1].getValue()++);
							}
							if(cells[randIndexX+1][randIndexY+1].getValue()!=BOMB_UP){
								cells[randIndexX+1][randIndexY+1].setValue(cells[randIndexX+1][randIndexY+1].getValue()++);
							}
							if(cells[randIndexX][randIndexY+1].getValue()!=BOMB_DOWN){
								cells[randIndexX][randIndexY+1].setValue(cells[randIndexX][randIndexY+1].getValue()++);
							}
						}
						else{
							if(cells[randIndexX+1][randIndexY].getValue()!=BOMB_UP){ //top left down
								cells[randIndexX+1][randIndexY].setValue(cells[randIndexX+1][randIndexY].getValue()++);
							}
							if(cells[randIndexX+2][randIndexY].getValue()!=BOMB_DOWN){
								cells[randIndexX+2][randIndexY].setValue(cells[randIndexX+2][randIndexY].getValue()++);
							}
							if(cells[randIndexX+1][randIndexY+1].getValue()!=BOMB_DOWN){
								cells[randIndexX+1][randIndexY+1].setValue(cells[randIndexX+1][randIndexY+1].getValue()++);
							}
							if(cells[randIndexX][randIndexY+1].getValue()!=BOMB_UP){
								cells[randIndexX][randIndexY+1].setValue(cells[randIndexX][randIndexY+1].getValue()++);
							}
						}
					}//end top corner
					else if(randIndexY == gridDimY-1){//start bottom corner
						if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){ //bottom left up
							if(cells[randIndexX+1][randIndexY].getValue()!=BOMB_DOWN){
								cells[randIndexX+1][randIndexY].setValue(cells[randIndexX+1][randIndexY].getValue()++);
							}
							if(cells[randIndexX+2][randIndexY].getValue()!=BOMB_UP){
								cells[randIndexX+2][randIndexY].setValue(cells[randIndexX+2][randIndexY].getValue()++);
							}
							if(cells[randIndexX+1][randIndexY-1].getValue()!=BOMB_UP){
								cells[randIndexX+1][randIndexY-1].setValue(cells[randIndexX+1][randIndexY-1].getValue()++);
							}
							if(cells[randIndexX][randIndexY-1].getValue()!=BOMB_DOWN){
								cells[randIndexX][randIndexY-1].setValue(cells[randIndexX][randIndexY-1].getValue()++);
							}
						}
						else{//bottom left down
							if(cells[randIndexX+1][randIndexY].getValue()!=BOMB_UP){
								cells[randIndexX+1][randIndexY].setValue(cells[randIndexX+1][randIndexY].getValue()++);
							}
							if(cells[randIndexX+2][randIndexY].getValue()!=BOMB_DOWN){
								cells[randIndexX+2][randIndexY].setValue(cells[randIndexX+2][randIndexY].getValue()++);
							}
							if(cells[randIndexX+2][randIndexY-1].getValue()!=BOMB_UP){
								cells[randIndexX+2][randIndexY-1].setValue(cells[randIndexX+2][randIndexY-1].getValue()++);
							}
							if(cells[randIndexX+1][randIndexY-1].getValue()!=BOMB_DOWN){
								cells[randIndexX+1][randIndexY-1].setValue(cells[randIndexX+1][randIndexY-1].getValue()++);
							}
							if(cells[randIndexX][randIndexY-1].getValue()!=BOMB_UP){
								cells[randIndexX][randIndexY-1].setValue(cells[randIndexX][randIndexY-1].getValue()++);
							}
						}
					}//end bottom corner
					else{//7 neighbors
						if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){
							if(cells[randIndexX][randIndexY-1].getValue()!=BOMB_DOWN){
								cells[randIndexX][randIndexY-1].setValue(cells[randIndexX][randIndexY-1].getValue()++);
							}
							if(cells[randIndexX+1][randIndexY-1].getValue()!=BOMB_UP){
								cells[randIndexX+1][randIndexY-1].setValue(cells[randIndexX+1][randIndexY-1].getValue()++);
							}
							if(cells[randIndexX+1][randIndexY].getValue()!=BOMB_DOWN){
								cells[randIndexX+1][randIndexY].setValue(cells[randIndexX+1][randIndexY].getValue()++);
							}
							if(cells[randIndexX+2][randIndexY].getValue()!=BOMB_UP){
								cells[randIndexX+2][randIndexY].setValue(cells[randIndexX+2][randIndexY].getValue()++);
							}
							if(cells[randIndexX+2][randIndexY+1].getValue()!=BOMB_DOWN){
								cells[randIndexX+2][randIndexY+1].setValue(cells[randIndexX+2][randIndexY+1].getValue()++);
							}
							if(cells[randIndexX+1][randIndexY+1].getValue()!=BOMB_UP){
								cells[randIndexX+1][randIndexY+1].setValue(cells[randIndexX+1][randIndexY+1].getValue()++);
							}
							if(cells[randIndexX][randIndexY+1].getValue()!=BOMB_DOWN){
								cells[randIndexX][randIndexY+1].setValue(cells[randIndexX][randIndexY+1].getValue()++);
							}
						}
						else{
							if(cells[randIndexX][randIndexY-1].getValue()!=BOMB_UP){
								cells[randIndexX][randIndexY-1].setValue(cells[randIndexX][randIndexY-1].getValue()++);
							}
							if(cells[randIndexX+1][randIndexY-1].getValue()!=BOMB_DOWN){
								cells[randIndexX+1][randIndexY-1].setValue(cells[randIndexX+1][randIndexY-1].getValue()++);
							}
							if(cells[randIndexX+2][randIndexY-1].getValue()!=BOMB_UP){
								cells[randIndexX+2][randIndexY-1].setValue(cells[randIndexX+2][randIndexY-1].getValue()++);
							}
							if(cells[randIndexX+2][randIndexY].getValue()!=BOMB_DOWN){
								cells[randIndexX+2][randIndexY].setValue(cells[randIndexX+2][randIndexY].getValue()++);
							}
							if(cells[randIndexX+1][randIndexY].getValue()!=BOMB_UP){
								cells[randIndexX+1][randIndexY].setValue(cells[randIndexX+1][randIndexY].getValue()++);
							}
							if(cells[randIndexX+1][randIndexY+1].getValue()!=BOMB_DOWN){
								cells[randIndexX+1][randIndexY+1].setValue(cells[randIndexX+1][randIndexY+1].getValue()++);
							}
							if(cells[randIndexX][randIndexY+1].getValue()!=BOMB_UP){
								cells[randIndexX][randIndexY+1].setValue(cells[randIndexX][randIndexY+1].getValue()++);
							}
						}
					}
				}//end left trim
				else if(randIndexX == 1){//start left border
					if(randIndexY == 0){//start top corner
						if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){//TODO
							(x-1, y) not down
							(x+1, y) not down
							(x+2, y) not up
							(x+2, y+1) not down
							(x+1, y+1) not up
							(x, y+1) not down
							(x-1, y+1) not up
						}
						else{//TODO
							x-1, y not up
							x+1, y not up
							x+2, y not down
							x+1, y+1 not down
							x, y+1 not up
							x-1, y+1 not down
						}
					}//end top corner
					else if(randIndexY == gridDimY-1){//start bottom corner
						if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){//TODO
							x-1, y not down
							x+1, y not down
							x+2, y not up
							x+1, y-1 not up
							x, y-1 not down
							x-1, y-1 not up
						}
						else{//TODO
							x-1, y not up
							x+1, y not up
							x+2, y not down
							x+2, y-1 not up
							x+1, y-1 not down
							x, y-1 not up
							x-1, y-1 not down
						}
					}//end bottom corner
					else{
						if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){//TODO
							x+1, y-1 not up
							x, y-1 not down
							x-1, y-1 not up
							x-1, y not down
							x+1, y not down
							x+2, y not up
							x+2, y+1 not down
							x+1, y+1 not up
							x, y+1 not down
							x-1, y+1 not up
						}
						else{//TODO
							x+1, y+1 not down
							x, y+1 not up
							x-1, y+1 not down
							x-1, y not up
							x+1, y not up
							x+2, y not down
							x+2, y-1 not up
							x+1, y-1 not down
							x, y-1 not up
							x-1, y-1 not down
						}
					}
				}//end left border
				else if(randIndexX == gridDimX-1){//start right trim
					if(randIndexY == 0){//start top corner
						if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){//TODO
							x-1, y not down
							x-2, y not up
							x-2, y+1 not down
							x-1, y+1 not up
							x, y+1 not down
						}
						else{//TODO
							x-1, y not up
							x-2, y not down
							x-1, y+1 not down
							x, y+1 not up
						}
					}//end top corner
					else if(randIndexY == gridDimY-1){//start bottom corner
						if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){//TODO
							x-1, y not down
							x-2, y not up
							x-1, y-1 not up
							x, y-1 not down
						}
						else{//TODO
							x-2, y not down
							x-1, y not up
							x, y-1 not up
							x-1, y-1 not down
							x-2, y-1 not up
						}
					}//end bottom corner
					else{
						if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){//TODO
							x, y+1 not down
							x-1, y+1 not up
							x-2, y+1 not down
							x-2, y not up
							x-1, y not down
							x, y-1 not down
							x-1, y-1 not up
						}
						else{//TODO
							x, y-1 not up
							x-1, y-1 not down
							x-2, y-1 not up
							x-2, y not down
							x-1, y not up
							x-1, y+1 not down
							x, y+1 not up
						}
					}
				}//end right trim
				else if(randIndexX == gridDimX-2){//start right border
					if(randIndexY == 0){//start top corner
						if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){//TODO
							x+1, y not down
							x-1, y not down
							x-2, y not up
							x-2, y+1 not down
							x-1, y+1 not up
							x, y+1 not down
							x+1, y+1 not up
						}
						else{//TODO
							x+1, y not up
							x-1, y not up
							x-2, y not down
							x-1, y+1 not down
							x, y+1 not up
							x+1, y+1 not down
						}
					}
					else if(randIndexY == gridDimY-1){//start bottom corner
						if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){//TODO
							x+1, y not down
							x-1, y not down
							x-2, y not up
							x-1, y-1 not up
							x, y-1 not down
							x+1, y-1 not up
						}
						else{//TODO
							x+1, y not up
							x-1, y not up
							x-2, y not down
							x-2, y-1 not up
							x-1, y-1 not down
							x, y-1 not up
							x+1, y-1 not down
						}
					}
					else{
						if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){//TODO
							x, y-1 not down
							x-1, y-1 not up
							x-2, y not up
							x-1, y not down
							x, y+1 not down
							x-1, y+1 not up
							x-2, y+1 not down
						}
						else{//TODO
							x, y+1 not up
							x-1, y+1 not down
							x-2, y not down
							x-1, y not up
							x, y-1 not up
							x-1, y-1 not down
							x-2, y-1 not up
						}
					}
				}//end right border
				else if(randIndexY == 0){//top edge
					if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){//TODO
						x-2, y not up
						x-1, y not down
						x+1, y not down
						x+2, y not up
						x+2, y+1 not down
						x+1, y+1 not up
						x, y+1 not down
						x-1, y+1, not up
						x-2, y+1 not down
					}
					else{//TODO
						x-1, y not up
						x+1, y not up
						x+1, y+1 not down
						x, y+1 not up
						x-1, y+1 not down
					}
				}//end top edge
				else if(randIndexY == gridDimY-1){//bottom edge
					if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){//TODO
						(x-1, y) not down
						(x+1, y) not down
						(x+1, y-1) not up
						(x, y-1) not down
						(x-1, y-1) not up
					}
					else{//TODO
						x-2, y not down
						x-1, y not up
						x+1, y not up
						x+2, y not down
						x+2, y-1 not up
						x+1, y-1 not down
						x, y-1 not up
						x-1, y-1 not down
						x-2, y-1 not up
					}
				}//end bottom edge
				else{//middle
					if(cells[randIndexX][randIndexY].getValue()==BOMB_UP){//TODO
						x-1, y-1 not up
						x, y-1 not down
						x+1, y-1 not up
						x+2, y not up
						x+1, y not down
						x-1, y not down
						x-2, y not up
						x-2, y+1 not down
						x-1, y+1 not up
						x, y+1 not down
						x+1, y+1 not up
						x+2, y+1 not down
					}
					else{//TODO
						(x-2, y-1) not up
						(x-1, y-1) not down
						(x, y-1) not up
						(x+1, y-1) not down
						(x+2, y-1) not up
						(x+2, y) not down
						(x+1, y) not up
						(x-1, y) not up
						(x-2, y) not down
						(x-1, y+1) not down
						(x, y+1), not up
						(x+1, y+1), not down
					}
				}//end middle
				 */				
			}//end if not already bomb condition
			randIndexX=random.nextInt(gridDimX);
			randIndexY=random.nextInt(gridDimY);
		}//end while loop

		//for(

		//Set image index to value of cell
		for(int i=0;i<gridDimX;i++){
			for(int j=0;j<gridDimY;j++)
			{
				if(cells[i][j].getValue()!=BOMB_UP || cells[i][j].getValue()!=BOMB_DOWN)
				{
					if((i+j)%2==0) //type UP
					{
						switch (cells[i][j].getValue()){
						case 0:
							cells[i][j].setImageIndex(0);
							break;
						case 1:
							cells[i][j].setImageIndex(2);
							break;
						case 2:
							cells[i][j].setImageIndex(4);
							break;
						case 3:
							cells[i][j].setImageIndex(6);
							break;
						case 4:
							cells[i][j].setImageIndex(8);
							break;
						case 5:
							cells[i][j].setImageIndex(10);
							break;
						case 6:
							cells[i][j].setImageIndex(12);
							break;
						case 7:
							cells[i][j].setImageIndex(14);
							break;
						case 8:
							cells[i][j].setImageIndex(16);
							break;
						case 9:
							cells[i][j].setImageIndex(18);
							break;
						case 10:
							cells[i][j].setImageIndex(20);
							break;
						case 11:
							cells[i][j].setImageIndex(22);
							break;
						case 12:
							cells[i][j].setImageIndex(24);
							break;
						}
					}
					else{ //type DOWN
						switch (cells[i][j].getValue()){
						case 0:
							cells[i][j].setImageIndex(1);
							break;
						case 1:
							cells[i][j].setImageIndex(3);
							break;
						case 2:
							cells[i][j].setImageIndex(5);
							break;
						case 3:
							cells[i][j].setImageIndex(7);
							break;
						case 4:
							cells[i][j].setImageIndex(9);
							break;
						case 5:
							cells[i][j].setImageIndex(11);
							break;
						case 6:
							cells[i][j].setImageIndex(13);
							break;
						case 7:
							cells[i][j].setImageIndex(15);
							break;
						case 8:
							cells[i][j].setImageIndex(17);
							break;
						case 9:
							cells[i][j].setImageIndex(19);
							break;
						case 10:
							cells[i][j].setImageIndex(21);
							break;
						case 11:
							cells[i][j].setImageIndex(23);
							break;
						case 12:
							cells[i][j].setImageIndex(25);
							break;
						}//end switch
					}//end down
				}//end not bomb	
			}//end Y loop
		}//end X loop
	}//end setBombs function

	/// For testing: prints the underlying values of the grid
	public void testGrid(){
		for ( int i = 0; i < gridDimY; i++){
			for ( int j = 0; j < gridDimX; j++){
				System.out.print(cells[j][i].getValue() + " ");
			}
			System.out.println();
		}
	}

	public void findEmptyCells(int i, int j){ //called when non-mine cell is clicked, finds and uncovers all empty cells
		if((cells[i][j].getValue()==EMPTY_UP || cells[i][j].getValue()==EMPTY_DOWN) && !cells[i][j].isUncovered() && !cells[i][j].isFlagged()){
			cells[i][j].setUncovered(true);

			//adjacent cells can vary for type of cell
			//for UP: (x-1,y+1) (x+1,y+1)
			//for DOWN: (x-1,y-1) (x+1,y-1)
			//for both: (x-1,y) (x+1,y) (x,y-1) (x,y+1)
/*
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
*/
			if((i+j)%2==0){
				if(i>0){
					findEmptyCells(i-1, j);
				}
				if(i<(gridDimX-1)){
					findEmptyCells(i+1, j);
				}
				if(j<(gridDimY-1)){
					findEmptyCells(i, j+1);
				}
			}
			else{
				if(i>0){
					findEmptyCells(i-1, j);
				}
				if(i<(gridDimX-1)){
					findEmptyCells(i+1, j);
				}
				if(j>0){
					findEmptyCells(i, j-1);
				}
			}
		}
		if(!cells[i][j].isFlagged()){
			cells[i][j].setUncovered(true);
		}
		repaint();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		TriangleCell cell;

		for(int i=0;i<gridDimX;i++){
			for(int j=0;j<gridDimY;j++){
				cell=cells[i][j];
				if(!cell.isUncovered()){
					if((i+j)%2==0){
						cell.setImageIndex(COVER_UP);
					}
					else{
						cell.setImageIndex(COVER_DOWN);
					}
				}
				if(cell.isUncovered()){
					if((i+j)%2==0){ //UP cell
						switch (cell.getValue()){
						case 0:
							cell.setImageIndex(0);
							break;
						case 1:
							cell.setImageIndex(2);
							break;
						case 2:
							cell.setImageIndex(4);
							break;
						case 3:
							cell.setImageIndex(6);
							break;
						case 4:
							cell.setImageIndex(8);
							break;
						case 5:
							cell.setImageIndex(10);
							break;
						case 6:
							cell.setImageIndex(12);
							break;
						case 7:
							cell.setImageIndex(14);
							break;
						case 8:
							cell.setImageIndex(16);
							break;
						case 9:
							cell.setImageIndex(18);
							break;
						case 10:
							cell.setImageIndex(20);
							break;
						case 11:
							cell.setImageIndex(22);
							break;
						case 12:
							cell.setImageIndex(24);
							break;
						default:
							cell.setImageIndex(34);
							break;
						}
					}
					else{ //DOWN cell
						switch (cell.getValue()){
						case 0:
							cell.setImageIndex(1);
							break;
						case 1:
							cell.setImageIndex(3);
							break;
						case 2:
							cell.setImageIndex(5);
							break;
						case 3:
							cell.setImageIndex(7);
							break;
						case 4:
							cell.setImageIndex(9);
							break;
						case 5:
							cell.setImageIndex(11);
							break;
						case 6:
							cell.setImageIndex(13);
							break;
						case 7:
							cell.setImageIndex(15);
							break;
						case 8:
							cell.setImageIndex(17);
							break;
						case 9:
							cell.setImageIndex(19);
							break;
						case 10:
							cell.setImageIndex(21);
							break;
						case 11:
							cell.setImageIndex(23);
							break;
						case 12:
							cell.setImageIndex(25);
							break;
						default:
							cell.setImageIndex(35);
							break;
						}
					}
				}
				else if(cell.isFlagged()){
					if((i+j)%2==0){ //UP cell
						cell.setImageIndex(FLAG_UP);
					}
					else{ //DOWN cell
						cell.setImageIndex(FLAG_DOWN);
					}
				}

				g.drawImage(img[cell.getImageIndex()], (i*13), (j*CELL_SIZE), this); //MAGIC GRAPHICS CONSTANT 13!!!!
			}
		}
	}

	public void gameOver(boolean winner){
		inGame = false;

		for(int i=0;i<gridDimY;i++){
			for(int j=0;j<gridDimX;j++){
				if(winner){
					if(cells[i][j].getImageIndex()==COVER_UP || cells[i][j].getImageIndex()==COVER_DOWN){
						cells[i][j].setUncovered(true);
					}
					if(cells[i][j].getValue()==BOMB_UP || cells[i][j].getValue()==BOMB_DOWN){
						if((i+j)%2==0){
							cells[i][j].setImageIndex(BOMB_UP);
						}
						else{
							cells[i][j].setImageIndex(BOMB_DOWN);
						}
						//cells[i][j].setUncovered(true);
					}
					repaint();
				}
				else{
					if(cells[i][j].getImageIndex()==FLAG_UP || cells[i][j].getImageIndex()==FLAG_DOWN){ //flagged cell
						if(cells[i][j].getValue()==BOMB_UP || cells[i][j].getValue()==BOMB_DOWN){
						}
						if(cells[i][j].getValue()!=BOMB_UP || cells[i][j].getValue()!=BOMB_DOWN){
							if((i+j)%2==0){
								cells[i][j].setValue(WRONG_FLAG_UP);
							}
							else{
								cells[i][j].setValue(WRONG_FLAG_DOWN);
							}
							cells[i][j].setUncovered(true);
						}
					}
					else{//non flagged cell

						cells[i][j].setUncovered(true);
					}
				}
			}
		}
		if(winner){
			//s.pause();
			//record.run(s.getTime());
			message="Hooray! You Won!";
			title=":D";
		}
		else{
			//s.reset();
			message="Oh no! You lost!";
			title="D:";
		}

		JOptionPane.showMessageDialog(this,message,title,0);
		repaint();
		mouseInputEnabled=false;
	}

	class MinesAdapter extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			int x = e.getX();
			int y = e.getY();
			int cCol = -999;
			int cRow = -999;
			boolean rep = false;

			//figure out ccol and crow right here
			int xx=x/(CELL_SIZE/2);
			int yy=y/(CELL_SIZE);
			cRow=yy;
			int starty;
			//if x<12 set flag
			//if x>

			if(xx%2==0){  //even
				starty=(yy*25)+25;
				for(int i=(xx*13);i<=(xx*13)+13;i++){
					if(i==x){
						if(starty>=y){
							cCol = xx; //??
							//cCol==xx; //LOOK AT THIS!!!
						}
						else{ //if(starty<y)
							cCol = xx-1; //??
							//cCol==xx-1; //LOOK AT THIS!!!
						}
						starty-=2;
					}
				}
			}
			else{  //odd   if(xx%2==1)
				starty=(yy*25);
				for(int i=(xx*13);i<=(xx*13)+13;i++){
					if(i==x){
						if(starty>=y){
							cCol=xx;
						}
						else{ //  if(starty<y)
							cCol=xx-1;
						}
						starty+=2;
					}
				}
			}

			if((x<gridDimX*CELL_SIZE && y < gridDimY * CELL_SIZE) && (cCol>=0 && cCol<gridDimX)){
				s.start();
				if(e.getButton() == MouseEvent.BUTTON1) {
					if(cells[cCol][cRow].getValue() == EMPTY_UP || cells[cCol][cRow].getValue()==EMPTY_DOWN){
						findEmptyCells(cCol, cRow);
					}
					else{
						cells[cCol][cRow].setUncovered(true);
					}

					if(cells[cCol][cRow].getValue()==BOMB_UP){
						cells[cCol][cRow].setValue(RED_BOMB_UP);
						gameOver(false);
					}
					if(cells[cCol][cRow].getValue()==BOMB_DOWN){
						cells[cCol][cRow].setValue(RED_BOMB_UP);
						gameOver(false);
					}
					rep = true;
				}

				else if(e.getButton() == MouseEvent.BUTTON3){
					/*
					////////// if it's not covered or isn't already flagged, BUTTON3 does nothing ////////////////
					if(cells[i][j].getImageIndex()!=COVER_UP || cells[i][j].getImageIndex()!=COVER_DOWN || cells[i][j].getImageIndex()!=FLAG_UP || cells[i][j].getImageIndex()!=FLAG_DOWN){
						return;
					}
					////////////////////////////////////////////////////////////////////////////////////////////
					*/
					if(cells[cCol][cRow].getImageIndex()!=COVER_UP || cells[cCol][cRow].getImageIndex()!=COVER_DOWN || cells[cCol][cRow].getImageIndex()!=FLAG_UP || cells[cCol][cRow].getImageIndex()!=FLAG_DOWN){
						return;
					}

					//flip cell's flag T/F
					boolean prevFlagStatus = cells[cCol][cRow].isFlagged();
					cells[cCol][cRow].setFlagged(!prevFlagStatus);

					if (cells[cCol][cRow].isFlagged()){
						numFlags++;
					}
					else{
						numFlags--;
					}

					if ((cells[cCol][cRow].getValue()==BOMB_UP || cells[cCol][cRow].getValue()==BOMB_DOWN) && cells[cCol][cRow].isFlagged()){
						flaggedMines++;
						statusBar.setText("Number of flagged Mines: " + flaggedMines);
					}
					if ((cells[cCol][cRow].getValue()==BOMB_UP || cells[cCol][cRow].getValue()==BOMB_DOWN) && prevFlagStatus==true){
						flaggedMines--;
						statusBar.setText("Number of flagged Mines: " + flaggedMines);
					}
					if (flaggedMines == numMines && numFlags == flaggedMines){
						gameOver(true);
					}
					rep = true;
				}
				if (rep) {
					repaint();
					rep = false;
				}
				if (!inGame){
					for ( int i = 0; i < gridDimY; i++){
						for ( int j = 0; j < gridDimX; j++){
							if(cells[i][j].getImageIndex()==BOMB_UP){
								cells[i][j].setValue(BOMB_UP);
							}
							if(cells[i][j].getImageIndex()==BOMB_DOWN){
								cells[i][j].setValue(BOMB_DOWN);
							}
						}
					}
				}
			}
		}//end mousePressed function

	}//end MinesAdapter class

}//end TriangleGrid class

