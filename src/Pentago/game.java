package Pentago;

import Pentago.Graphic.Client;
public class game {
	private board Board;
	private player white, black;
	public Client client;
	private move current = null;
	
	public game(player one, player two)
	{
		Board = new board();
		white = one;
		black = two;
		white.setColor(1);
		black.setColor(2);
		client=new Client();
	}
	
	private void log(move m, player p)
	{
		System.err.println("Player " + p.getName() + " Pentago.move: row = " + m.getRow() + " column = " + m.getColumn() + " subboard = " + m.getSubboard() + " clockwise = " + m.getClockwise());
		for(int i = 0; i < Board.BoardSize; i++)
		{
			for(int j = 0; j < Board.BoardSize; j++)
				System.err.print(Board.get(i, j) + " ");
			System.err.println();
		}
	}
	
	int timeout=0;
	boolean exception=false;
	public void _wait(){
		synchronized (this) {
			try {
				this.wait(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void start()
	{
		move m;
		player [] players=new player[] {white,black};
		int now=0;
		while(Board.winner() == 0)
		{
			try {
				_wait();
				getMove(players[now],Board);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			m = current;
			if (m==null)
			{
				timeout = 2 - now;
				if (!exception) System.out.println("Player " + players[now].getName() + " exceeded time limit");
				break;
			}
			client.add(m, now + 1);
			Board.move(m, now + 1);
			log(m, players[now]);
			try
			{
				Thread.sleep(1100);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			now^=1;
		}
		System.out.println("*** End ***");
		int winner = (timeout==0?Board.winner():timeout);
		if(winner == 1)
			System.out.println(white.getName() + " won the Pentago.game");
		else if(winner == 2)
			System.out.println(black.getName() + " won the Pentago.game");
		else
			System.out.println("Nobody won the Pentago.game");
	}
	
	private move m;
	private void getMove(final player p, final board board) throws InterruptedException {
		Thread t=new Thread()
		{
			public void run()
			{
				try
				{
					current = p.getMove(board);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					exception=true;
					current=null;
				}
			}
		};
		t.start();
	}
}
