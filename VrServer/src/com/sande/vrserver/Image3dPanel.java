package com.sande.vrserver;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Image3dPanel extends JPanel{
	

	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	
	public Image3dPanel(BufferedImage img)
	{
		this.setBackground(Color.BLACK);

		this.img=img;
	}

	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}

}
