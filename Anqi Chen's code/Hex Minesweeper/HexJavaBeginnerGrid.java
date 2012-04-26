import java.util.Iterator;
import java.util.Random;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import java.lang.Math;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.*;


//Beginner Hex Grid is board with numBombs bombs
public class HexJavaBeginnerGrid extends JPanel{
	
	private final int EMPTY_HexCell = 0;		//Empty HexCells
	private final int COVER_FOR_HexCell = 9;	//Cover for Hexcell picture
	private final int FLAG = 10;				//Flag picture number
	private final int WRONG_FLAG = 13;			//Wrong flag picture number
	private final int BOMB = 11;				//Bomb picture number
	private final int RED_BOMB = 12;			//Click wrong bomb
	private final int NUM_IMAGES = 14;			//Number of images
	private final int HexCell_SIZE_X = 10;		//Set Size, better not be changed
	private final int HexCell_SIZE_Y = 20;		//Set Size, better not be changed
	
	private Image[] image;						//Original Image Array
	private BufferedImage[] img;				//Resize Image Array
	private int numMines = 10;					//Num of Bombs
	private int numFlags = 10;					//Num of Flags
	private int gridDimX;						//Grid DimensionX, should be changed
	private int gridDimY;						//Grid DimensionY, should be changed
	private boolean inGame = true;
	
	private JLabel statusBar;
	HexCell[][] HexCells; 						//it's a map
	
	//private StopWatch sw;
	//private Record rec = new Record();
	
	
	/// Constructor for BeginnerGrid, gridDimsnsionX x gridDimensionY with numBombs mines
	public HexJavaBeginnerGrid(JLabel statusBar, int gridDimensionX, int gridDimensionY, int numBombs) {
		
		numMines = numBombs;
		gridDimX = gridDimensionX*2;
		gridDimY = gridDimensionY*2;
		HexCells = new HexCell[gridDimX][gridDimY];  //notice, it use new.  HexCell[gridDim][gridDim]
		this.statusBar = statusBar;					//Show the message
		//sw=s;
		
		image = new Image[NUM_IMAGES];				//Java use this method
		img = new BufferedImage[NUM_IMAGES];   		//Resize Image
		
		
		for (int i = 0; i < NUM_IMAGES; i++){		//Resize the picture
			image[i] = new ImageIcon(this.getClass().getResource((i) + ".png")).getImage();
			img[i] = createResizedCopy(image[i],21,26,true);
		}
		
		
		setDoubleBuffered(true);		
		addMouseListener(new MinesAdapter());
		
		// Build and populate grid with HexCells.
		for ( int i = 0; i < gridDimX; i++){
			for ( int j = 0; j < gridDimY; j++){
				if((i+j)%2==1)
					HexCells[i][j] = new HexCell(i,j);			//Two dimension initialize
			}
		}
		
		
		this.statusBar.setText("Flags Left: " + numBombs);
		initializeBoard(numMines);
		//testGrid();
	}
	
	// Places mines in random HexCells, initializes the numbers in non-mine HexCells
	private void initializeBoard(int _numMines){

		numFlags = _numMines;
		this.statusBar.setText("Flags Left: " + numFlags);
		int i,j;
		
		// Build and populate grid with HexCells.
		for ( i = 0; i < gridDimX; i++){
			for ( j = 0; j < gridDimY; j++){
				if((i+j)%2==1)
					HexCells[i][j] = new HexCell(i,j);			//Two dimension initialize
			}
		}
		
		
		for(i=0;i<gridDimX;i++)
			for(j=0;j<gridDimY;j++){
				if((i+j)%2==1){
					if(i+1<gridDimX&&j+1<gridDimY)
						HexCells[i][j].getAdjacentArray().add(HexCells[i+1][j+1]);
					
					if(i+1<gridDimX&&j-1>=0)
						HexCells[i][j].getAdjacentArray().add(HexCells[i+1][j-1]);
					
					if(i+2<gridDimX)
						HexCells[i][j].getAdjacentArray().add(HexCells[i+2][j]);//
					
					if(i-2>=0)
						HexCells[i][j].getAdjacentArray().add(HexCells[i-2][j]);//
					
					if(i-1>=0&&j+1<gridDimY)
						HexCells[i][j].getAdjacentArray().add(HexCells[i-1][j+1]);
					
					if(i-1>=0&&j-1>=0)
						HexCells[i][j].getAdjacentArray().add(HexCells[i-1][j-1]);
				}
			}
		
		
		// Generate random bombs
		Random random = new Random();
		int randIndexX = random.nextInt(gridDimX);
		int randIndexY = random.nextInt(gridDimY);
		int numBombs = 0;
		
		while (numBombs != _numMines){
			if ((randIndexX+randIndexY)%2==1){
				if((HexCells[randIndexX][randIndexY].getValue()!=BOMB)){    //BOMB=11
					HexCells[randIndexX][randIndexY].setValue(BOMB);
					numBombs++;
				}
			}
			randIndexX = random.nextInt(gridDimX);
			randIndexY = random.nextInt(gridDimY);
		}
		
	
		for(i=0;i<gridDimX;i++)
			for(j=0;j<gridDimY;j++){
				if((i+j)%2==1&&HexCells[i][j].getValue()!=BOMB){
					Iterator<HexCell> adIT= (HexCells[i][j].getAdjacentArray()).iterator();
					while(adIT.hasNext()){
						if(adIT.next().getValue()==BOMB)
							HexCells[i][j].setValue(HexCells[i][j].getValue()+1);
					}
				}
			}
		
		repaint();
	}
		
	/// For testing: prints the underlying values of the grid
	public void testGrid() 
	{
		for ( int i = 0; i < gridDimX; i++){	
			for ( int j = 0; j < gridDimY; j++){
				if((i+j)%2==1)
					System.out.print(HexCells[i][j].getValue() + " ");
				else
					System.out.print("*"+" ");
			}
			System.out.println();
		}
	}

	/// Called when a non-mine is clicked, finds and uncovers all the appropriate HexCells
	public void findEmptyHexCells(int i, int j)
	{
		Iterator<HexCell> HIT;
		HexCell tmp;
		
		// TODO Write findEmptyHexCells() Function
		if((i+j)%2==1&&HexCells[i][j].getValue()==0&&!HexCells[i][j].getIsUncovered()&&!HexCells[i][j].getIsFlagged()){
			HexCells[i][j].setIsUncovered(true);
			HIT=HexCells[i][j].getAdjacentArray().iterator();
			while(HIT.hasNext()){
				tmp=HIT.next();
				findEmptyHexCells(tmp.getX(),tmp.getY());
			}
		}
		
		if ((i+j)%2==1&&!HexCells[i][j].getIsFlagged())
			HexCells[i][j].setIsUncovered(true);
	}
	
		
	/// Overriding JFrame's paintComponent function. DO NOT call directly, rather, call repaint().
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

        HexCell _HexCell;
        int uncover = 0;

        for (int i = 0; i < gridDimX; i++) {
            for (int j = 0; j < gridDimY; j++) {
                if((i+j)%2==1){
            		_HexCell = HexCells[i][j];
                
                	if ( !_HexCell.getIsUncovered() )
                	{
                		_HexCell.setImageIndex(COVER_FOR_HexCell);
                		 uncover++;
                	}
                
                	if ( _HexCell.getIsUncovered() )
                	{
                		_HexCell.setImageIndex(_HexCell.getValue());
                	}
                
                	else if ( _HexCell.getIsFlagged() )
                	{
                		_HexCell.setImageIndex(FLAG);
                	}
                
                	else if ( _HexCell.getIsUncovered() && _HexCell.getValue() == BOMB)
                	{
                		_HexCell.setImageIndex(RED_BOMB);
                		inGame = false;
                	}

                	g.drawImage(img[_HexCell.getImageIndex()], (i * HexCell_SIZE_X), (j * HexCell_SIZE_Y), this); //////
                
                }
               
            } 
        }

        if (uncover == numMines && numFlags==0 && inGame) {
            statusBar.setText("Game won");
            inGame=false;
        } 
	}

	public void gameOver(int col, int row)
	{
		for ( int i = 0; i < gridDimX; i++){
			for ( int j = 0; j < gridDimY; j++)
			{
				if((i+j)%2==1){
				
					if ( !HexCells[i][j].getIsFlagged())
					{
						HexCells[i][j].setIsUncovered(true);
					}
					else 
					{
						if ( HexCells[i][j].getValue() != BOMB)
						{
							HexCells[i][j].setValue(WRONG_FLAG);
							HexCells[i][j].setIsUncovered(true);
						}
					}
				}
			}
		}
		
		//************************
		//s.pause();
		//rec.run(s.getTime());
		//s.reset();
		repaint();
		statusBar.setText("Game lost");
		inGame=false;
	}
	
	//Resize Image
	BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) 
	{ 
		int imageType = BufferedImage.TYPE_INT_ARGB_PRE;
		//int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB; 
		//We don't use RGB and ARGB, we use ARGB_PRE
		BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType); 
		Graphics2D g = scaledBI.createGraphics(); 
		if (preserveAlpha) { 
			g.setComposite(AlphaComposite.Src); 
		} 
		g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);  
		g.dispose(); 
		return scaledBI; 
	} 
	
	class MinesAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {

            double x = e.getX();
            double y = e.getY();

            double fixX=x;
            double fixY=y;
            
            int cRow = (int)(fixX / HexCell_SIZE_X);
            int cCol = (int)(fixY / HexCell_SIZE_Y);
            
            
            boolean rep = false;            

            if (!inGame) {
                initializeBoard(numMines);
                repaint();
                inGame=true;
            }
            else{
            	int cRow1 = cRow-1;
            	int cRow2 = cRow;
            	int cCol1 = cCol-1;
            	int cCol2 = cCol;
            	int tmpRow1,tmpCol1,tmpRow2,tmpCol2;
            	if((cRow1+cCol1+10)%2==1){    //add 10, otherwise (-1)%2==0
            		tmpRow1=cRow1;
            		tmpCol1=cCol1;
            		tmpRow2=cRow2;
            		tmpCol2=cCol2;
            	}
            	else{
            		tmpRow1=cRow2;
            		tmpCol1=cCol1;
            		tmpRow2=cRow1;
            		tmpCol2=cCol2;
            	}
            
            	if(tmpRow1>gridDimX+1||tmpCol1>gridDimY+1){		//for border use
            		cRow=tmpRow2;
            		cCol=tmpCol2;
            	}
            	else if(tmpRow2>gridDimX+1||tmpCol2>gridDimY+1){   //for border use
            		cRow=tmpRow1;
            		cCol=tmpCol1;
            	}
            	else{
            		cRow= Math.pow((fixX-(double)(tmpRow1+1)*HexCell_SIZE_X),2)+Math.pow((fixY-(double)(tmpCol1+0.5)*HexCell_SIZE_Y),2)
            			< Math.pow((fixX-(double)(tmpRow2+1)*HexCell_SIZE_X),2)+Math.pow((fixY-(double)(tmpCol2+0.5)*HexCell_SIZE_Y),2)
            			?tmpRow1:tmpRow2;
            		cCol= Math.pow((fixX-(double)(tmpRow1+1)*HexCell_SIZE_X),2)+Math.pow((fixY-(double)(tmpCol1+0.5)*HexCell_SIZE_Y),2)
            			< Math.pow((fixX-(double)(tmpRow2+1)*HexCell_SIZE_X),2)+Math.pow((fixY-(double)(tmpCol2+0.5)*HexCell_SIZE_Y),2)
            			?tmpCol1:tmpCol2;
            	}
            
            
            	if (e.getButton() == MouseEvent.BUTTON1) 
            	{
            		if(cRow>=0&&cRow<gridDimX&&cCol>=0&&cCol<gridDimY){
            			if(HexCells[cRow][cCol].getIsFlagged()){           		
            				HexCells[cRow][cCol].setIsFlagged(false);
            				numFlags++;
            				statusBar.setText("Flags Left: " + numFlags);
            			}
            			else {
            				if(HexCells[cRow][cCol].getValue() == 0){
            					findEmptyHexCells(cRow, cCol);
            				}
            				else {
            					HexCells[cRow][cCol].setIsUncovered(true);
            				}
            	
            				if ( HexCells[cRow][cCol].getValue() == BOMB)
            				{
            					HexCells[cRow][cCol].setValue(RED_BOMB);
            					gameOver(cRow, cCol);
            				}
            			}
            		
            		rep = true;
            		System.out.println();
            	}
            }
            else if (e.getButton() == MouseEvent.BUTTON3)
            {
            	if(cRow>=0&&cRow<gridDimX&&cCol>=0&&cCol<gridDimY){
            		if(!HexCells[cRow][cCol].getIsFlagged()){
            			if(numFlags==0){
            				statusBar.setText("No More Flags. Flags Left: 0");
            			}
            			else{
            				if(!HexCells[cRow][cCol].getIsUncovered()){
            					HexCells[cRow][cCol].setIsFlagged(true);
            					numFlags--;
            					statusBar.setText("Flags Left: " + numFlags);
            					}
            				}
            		}
            		else{
            			HexCells[cRow][cCol].setIsFlagged(false);
            			numFlags++;
            			statusBar.setText("Flags Left: " + numFlags);
            		}
            			rep = true;
            	}
            }
            
            if (rep) 
            {
                repaint();
                rep = false;
            }   
        }
	}
   }
}

	
	
