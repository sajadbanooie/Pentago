package Pentago.Graphic;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import Pentago.board;

/**
 * Created with IntelliJ IDEA.
 * User: LGM
 * Date: 3/7/15
 * Time: 12:30 PM
 */
public class Panel extends JPanel
{
	public board board=new board();
	public int rotate,bn;
	public int[]  boardRotate=new int[4];
	public Panel() { }
	public Panel(int x, int y, int w, int h){
		setBounds(x, y, w, h);
		setLayout(null);
		setOpaque(true);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D graphics2D=(Graphics2D) g;

		AffineTransform transform=AffineTransform.getTranslateInstance(0,0);
		graphics2D.drawImage(Pentago.Graphic.Frame.backImg,transform,null);
		int cnt=0;
		for (int x=0;x<2;x++)
			for (int y=0;y<2;y++)
			{
				draw(graphics2D, x * 3, y * 3, cnt, 30 + 180 * y, 30 + 180 * x);
				cnt++;
			}
	}

	private void draw(Graphics2D g, int r, int c, int board, int cx, int cy)
	{
		AffineTransform transform=AffineTransform.getTranslateInstance(cx,cy);
		BufferedImage img= Pentago.Graphic.Frame.LoadImage(Pentago.Graphic.Frame.board);
		transform.rotate(Math.toRadians(boardRotate[board]), img.getWidth() / 2, img.getHeight() / 2);
		g.drawImage(img, transform, null);
		for (int i=0;i<3;i++) for (int j=0;j<3;j++) if (this.board.get(r+i,c+j)!=0)
			draw2(g,board,cx,cy,i,j,this.board.get(r+i,c+j),img);
		img.flush();
	}

	private void draw2(Graphics2D g, int board, int cx, int cy, int y, int x,int player,BufferedImage img2)
	{
		AffineTransform transform=AffineTransform.getTranslateInstance(cx + 5 + x * 55, cy + 5 + y * 55);
		BufferedImage img= Pentago.Graphic.Frame.LoadImage((player == 1 ? Pentago.Graphic.Frame.white : Pentago.Graphic.Frame.black));
		if (board==bn && rotate!=0)
			transform.rotate(Math.toRadians(rotate),img2.getWidth()/2-5-x*55,img2.getHeight()/2-5-y*55);
		g.drawImage(img,transform,null);
		img.flush();
	}
}
