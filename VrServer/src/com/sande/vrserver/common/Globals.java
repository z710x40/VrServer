package com.sande.vrserver.common;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class Globals 
{

	static public BufferedImage imageL=null;
	static public BufferedImage imageR=null;
	static public String imageStringL="";
	static public String imageStringR="";
	
	public static boolean flagRenderWorker=true;
	
	public static int framesPerSeconds=40;				// Aantal renders per seconde
	
	
	public static void delay(int ms)
	{
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static Dimension srcDm=new Dimension(1200,600);
}
