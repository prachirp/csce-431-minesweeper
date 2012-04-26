public class TriangleCell
{
	private int value;
	private int imageIndex;
	private boolean isUncovered;
	private boolean isFlagged;
	
	public TriangleCell(int imageIndex)
	{ value=0; this.imageIndex=imageIndex; isUncovered=isFlagged=false; }
	
	public int getValue()
	{ return value; }
	
	public void setValue(int value)
	{ this.value=value; }
	
	public int getImageIndex()
	{ return imageIndex; }
	
	public void setImageIndex(int imageIndex)
	{ this.imageIndex=imageIndex; }
	
	public boolean getIsUncovered()
	{ return isUncovered; }
	
	public void setIsUncovered(boolean b)
	{ isUncovered=b; }
	
	public boolean getIsFlagged()
	{ return isFlagged; }
	
	public void setIsFlagged(boolean b)
	{ isFlagged=b; }
}

//for imageIndex, difficult to understand because there's two images for everything
//because there's an entire different set of images for the upside down triangles
//img=val
//0 = 1 UP triangle
//1 = 1 DOWN triangle
//2 = 2 UP triangle
//3 = 2 DOWN triangle
//4 = 3 UP triangle
//5 = 3 DOWN triangle
//6 = 4 UP triangle
//7 = 4 DOWN triangle
//8 = 5 UP triangle
//9 = 5 DOWN triangle
//10 =6 UP triangle
//11 =6 DOWN triangle
//12 = empty UP
//13 = empty DOWN
//14 = covered UP
//15 = covered DOWN
//16 = bomb UP
//17 = bomb DOWN
//18 = red bomb UP
//19 = red bomb DOWN
//20 = flag UP
//21 = flag DOWN
//22 = wrong flag UP
//23 = wrong flag DOWN
