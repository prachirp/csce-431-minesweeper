
public class Cell {
	
	private int value;	// either a number or B (bomb)
	private boolean isUncovered;
	private boolean isFlagged;
	
	public Cell() { value = 0; isUncovered = isFlagged = false;}
	public Cell(int val) { value = val; isUncovered = isFlagged = false; }
	public int getValue() { return value; }
	public boolean getIsUncovered() { return isUncovered; }
	public boolean getIsFlagged() { return isFlagged; }
	public void setValue(int c) { value = c; }
}
