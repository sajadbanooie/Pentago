package Pentago;

public class move {
	private int row, column;
	private int subboard;
	private boolean clockwise;
	
	public move(int _row, int _column, int _subboard, boolean _clockwise)
	{
		row = _row;
		column = _column;
		subboard = _subboard;
		clockwise = _clockwise;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getColumn()
	{
		return column;
	}
	
	public int getSubboard()
	{
		return subboard;
	}
	
	public boolean getClockwise()
	{
		return clockwise;
	}
}
