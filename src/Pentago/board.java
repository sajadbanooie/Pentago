package Pentago;

import java.util.Arrays;


public class board {
	private int [] dx, dy;
	private int [][] Board;
	private int [][][] RX, RY;
	public final int BoardSize = 6;
	
	public board()
	{
		Board = new int[BoardSize][BoardSize];
		for(int i = 0; i < BoardSize; i++)
			Arrays.fill(Board[i], 0);
		dx = new int[] {0, 1, 1, 1};
		dy = new int[] {-1, -1, 0, 1};
		RX = new int [2][3][3];
		RY = new int [2][3][3];
		RX[0] = new int [][] {{0, 1, 2}, {0, 1, 2}, {0, 1, 2}};
		RX[1] = new int [][] {{2, 1, 0}, {2, 1, 0}, {2, 1, 0}};
		RY[0] = new int [][] {{2, 2, 2}, {1, 1, 1}, {0, 0, 0}};
		RY[1] = new int [][] {{0, 0, 0}, {1, 1, 1}, {2, 2, 2}};
	}
	
	public int get(int r, int c)
	{
		return Board[r][c];
	}
	
	private void fill(int [][] board)
	{
		for(int i = 0; i < BoardSize; i++)
			for(int j = 0; j < BoardSize; j++)
				Board[i][j] = board[i][j];
	}
	
	private boolean checkWin(int player)
	{
		boolean check;
		for(int i = 0; i < BoardSize; i++)
			for(int j = 0; j < BoardSize; j++)
				for(int d = 0; d < 4; d++)
					if(0 <= i + 4 * dx[d] && i + 4 * dx[d] < BoardSize && 0 <= j + 4 * dy[d] && j + 4 * dy[d] < BoardSize )
					{
						check = true;
						for(int t = 0; t < 5; t++)
							if(Board[i + t * dx[d]][j + t * dy[d]] != player)
							{
								check = false;
								break;
							}
						if(check)
							return true;
					}
		return false;	
	}
	
	private boolean boardIsFull()
	{
		for(int i = 0; i < BoardSize; i++)
			for(int j = 0; j < BoardSize; j++)
				if(Board[i][j] == 0)
					return false;
		return true;
	}
	
	public int winner()
	{
		boolean win1 = checkWin(1), win2 = checkWin(2);
		
		int win = 0;
		if((win1 && win2) || (!win1 && !win2 && boardIsFull()))
			win = 3;
		else if(win1)
			win = 1;
		else if(win2)
			win = 2;
		return win;
	}
	
	private boolean validMove(move m)
	{
		if(m.getRow() < 0 || m.getRow() >= BoardSize)
			return false;
		if(m.getColumn() < 0 || m.getColumn() >= BoardSize)
			return false;
		if(m.getSubboard() < 0 || m.getSubboard() > 3)
			return false;
		if(Board[m.getRow()][m.getColumn()] != 0)
			return false;
		return true;
	}
	
	private void assignBoard(int [][] to, int [][] from)
	{
		for(int i = 0; i < BoardSize; i++)
			for(int j = 0; j < BoardSize; j++)
				to[i][j] = from[i][j];
	}
	
	private void rotate(int subboard, boolean clockwise)
	{
		int [][] tmp = new int[BoardSize][BoardSize];
		assignBoard(tmp, Board);
		int r = (subboard / 2) * 3, c = (subboard % 2) * 3;
		int dir = clockwise ? 1 : 0;
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				tmp[r + i][c + j] = Board[r + RX[dir][i][j]][c + RY[dir][i][j]];
		assignBoard(Board, tmp);
	}
	
	public board nextMove(move m, int player)
	{
		board tmp = new board();
		tmp.fill(Board);
		tmp.move(m, player);
		return tmp;
	}
	
	public void move(move m, int player)
	{
		if(validMove(m))
		{
			Board[m.getRow()][m.getColumn()] = player;
			rotate(m.getSubboard(), m.getClockwise());
		}
	}
}
