package Pentago.Graphic;

import java.awt.*;

import Pentago.move;

/**
 * Created with IntelliJ IDEA.
 * User: LGM
 * Date: 3/7/15
 * Time: 12:12 PM
 */
public class Client
{
	Frame frame;
	Panel panel;
	public Client()
	{
		frame=new Frame("Pentago");
		frame.setPreferredSize(new Dimension(400,400));
		frame.setVisible(true);
		frame.add(panel=new Panel(0,0,400,400));
	}
	public void add(move move,int player)
	{
		panel.board.move(move,player);
		panel.bn=move.getSubboard();
		panel.rotate=-(move.getClockwise()?90:-90);
		panel.repaint();
		rotate(panel.bn,-panel.rotate);
	}
	private void rotate(final int board, final int degree)
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					Thread.sleep(300);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				while (true)
				{
					if (panel.rotate == 0) return;
					panel.rotate += (degree > 0 ? 3 : -3);
					panel.boardRotate[board]+=(degree > 0 ? 3 : -3);
					panel.repaint();
					try
					{
						Thread.sleep(20);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
}
