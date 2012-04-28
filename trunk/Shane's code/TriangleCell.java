public class TriangleCell
{
	
	private int X;
	private int Y;
	private int value;
	private int imageIndex;
	private boolean isUncovered;
	private boolean isFlagged;
	private boolean isUp;
	private boolean isBomb;
	private ArrayList<TriangleCell> Adjacent;
	
	
	public TriangleCell() { X=Y=-1;value = 0; imageIndex = 0; isUncovered = isFlagged = isBomb = false; isUp = null;Adjacent=new ArrayList<TriangleCell>();}
	public TriangleCell(int x, int y) {
		imageIndex = 9; 
		isUncovered = isFlagged = isBomb = false;
		if((x+y)%2==0){
			isUp = true;
			value = 0;
		}
		else{
			isUp = false;
			value = 1;
		}		
		X=x;
		Y=y;
		Adjacent=new ArrayList<TriangleCell>();
	}
	
	public int getValue() { return value; }
	public int getImageIndex() { return imageIndex; }
	public int getX() { return X;}
	public int getY() { return Y;}
	public ArrayList<TriangleCell> getAdjacentArray() { return Adjacent;}
	public boolean isUncovered() { return isUncovered; }
	public boolean isFlagged() { return isFlagged; }
	public boolean isBomb(){return isBomb;}
	public void setValue(int c) { value = c; if(c == 28 || c == 29){isBomb = true;}}
	public void setImageIndex(int c) { imageIndex = c; }
	public void setUncovered(boolean b) { isUncovered = b; }
	public void setFlagged(boolean b) { isFlagged = b; }
	public void setX(int x){ X=x;}
	public void setY(int y){ Y=y;}
	
}

//for imageIndex, difficult to understand because there's two images for everything
//because there's an entire different set of images for the upside down triangles
//img=val
//0 = empty UP
//1 = empty DOWN
//2 = 1 UP triangle
//3 = 1 DOWN triangle
//4 = 2 UP triangle
//5 = 2 DOWN triangle
//6 = 3 UP triangle
//7 = 3 DOWN triangle
//8 = 4 UP triangle
//9 = 4 DOWN triangle
//10 = 5 UP triangle
//11 = 5 DOWN triangle
//12 = 6 UP triangle
//13 = 6 DOWN triangle
//14 = 7 UP triangle
//15 = 7 DOWN triangle
//16 = 8 UP triangle
//17 = 8 DOWN triangle
//18 = 9 UP triangle
//19 = 9 DOWN triangle
//20 = 10 UP triangle
//21 = 10 DOWN triangle
//22 = 11 UP triangle
//23 = 11 DOWN triangle
//24 = 12 UP triangle
//25 = 12 DOWN triangle
//26 = covered UP
//27 = covered DOWN
//28 = bomb UP
//29 = bomb DOWN
//30 = red bomb UP
//31 = red bomb DOWN
//32 = flag UP
//33 = flag DOWN
//34 = wrong flag UP
//35 = wrong flag DOWN


