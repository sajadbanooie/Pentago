import Pentago.board;
import Pentago.move;
import Pentago.player;

import java.util.ArrayList;
import java.util.Random;

public class samplePlayer implements player
{
	private String name;
	private int color;
	
	samplePlayer(String _name)
	{
		name = _name;
	}
	
	private class position {
		int r, c;
		position(int _r, int _c)
		{
			r = _r;
			c = _c;
		}
		int getR()
		{
			return r;
		}
		int getC()
		{
			return c;
		}
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setColor(int _color) {
		color = _color;
	}

	@Override
	public move getMove(board b) {
		ArrayList<position> p = new ArrayList<position>();
		Random rand = new Random();
		for(int i = 0; i < b.BoardSize; i++)
			for(int j = 0; j < b.BoardSize; j++)
				if(b.get(i, j) == 0)
					p.add(new position(i, j));
		int ind = rand.nextInt(p.size());
		return new Pentago.move(p.get(ind).getR(), p.get(ind).getC(), rand.nextInt(4), rand.nextBoolean());
	}
}
