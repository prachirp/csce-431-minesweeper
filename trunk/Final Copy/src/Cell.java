
public class Cell {
	
	private int value;
	private int imageIndex;
	private boolean isUncovered;
	private boolean isFlagged;
	
	public Cell() { value = 0; imageIndex = 9; isUncovered = isFlagged = false;}
	public Cell(int val) { value = val; imageIndex = 9; isUncovered = isFlagged = false; }
	public int getValue() { return value; }
	public int getImageIndex() { return imageIndex; }
	public boolean getIsUncovered() { return isUncovered; }
	public boolean getIsFlagged() { return isFlagged; }
	public void setValue(int c) { value = c; }
	public void setImageIndex(int c) { imageIndex = c; }
	public void setIsUncovered(boolean b) { isUncovered = b; }
	public void setIsFlagged(boolean b) { isFlagged = b; }
}
