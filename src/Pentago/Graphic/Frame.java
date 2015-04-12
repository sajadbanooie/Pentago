package Pentago.Graphic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: LGM
 * Date: 3/7/15
 * Time: 12:08 PM
 */
public class Frame extends JFrame
{
	public static String prefix = "data/";
	public static String board=prefix+"board.png";
	public static String white=prefix+"white.png";
	public static String black=prefix+"black.png";
	public static String back=prefix+"back.jpg";
	public static BufferedImage backImg=LoadImage(back);
	public Frame(String title) {
		super();
		setLayout(null);
		setTitle(title);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		pack();
	}
	public static BufferedImage LoadImage(String FileName)
	{
		BufferedImage img = null;

		try{
			img = ImageIO.read(new File(FileName));
		}catch(IOException e){

		}

		return img;
	}
}
