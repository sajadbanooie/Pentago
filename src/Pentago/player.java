package Pentago;

import Pentago.board;
import Pentago.move;

public interface player {
	public String getName();
	public void setColor(int _color);
	public move getMove(board b);
}