import java.util.*;

public class HexCell {
	
	private int X;
	private int Y;
	private int value;
	private int imageIndex;
	private boolean isUncovered;
	private boolean isFlagged;
	private ArrayList<HexCell> Adjacent;
	
	
	public HexCell() { X=Y=-1;value = 0; imageIndex = 9; isUncovered = isFlagged = false;Adjacent=new ArrayList<HexCell>();}
	public HexCell(int x, int y) { 
		value = 0; 
		imageIndex = 9; 
		isUncovered = isFlagged = false; 
		X=x;
		Y=y;
		Adjacent=new ArrayList<HexCell>();
	}
	
	public int getValue() { return value; }
	public int getImageIndex() { return imageIndex; }
	public int getX() { return X;}
	public int getY() { return Y;}
	public ArrayList<HexCell> getAdjacentArray() { return Adjacent;}
	public boolean getIsUncovered() { return isUncovered; }
	public boolean getIsFlagged() { return isFlagged; }
	public void setValue(int c) { value = c; }
	public void setImageIndex(int c) { imageIndex = c; }
	public void setIsUncovered(boolean b) { isUncovered = b; }
	public void setIsFlagged(boolean b) { isFlagged = b; }
	public void setX(int x){ X=x;}
	public void setY(int y){ Y=y;}
	
}