package com.sande.vrserver;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sande.vrserver.common.Globals;
import com.sande.vrserver.network.VrNetworkServer;
import com.sande.vrserver.worker.RenderWorker;


public class VrServer extends JFrame implements KeyListener{

	private static final long serialVersionUID = 1L;
	JPanel jp=new JPanel();
	VrUniverse vru;
	Image3dPanel i3dp;
	BorderLayout br;
	VrNetworkServer vrNetworkServer;
	Thread renderThread;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VrServer vrServer=new VrServer();
		vrServer.setFocusable(true);
		vrServer.setVisible(true);
	}

	
	
	public VrServer()
	{
		vrNetworkServer=new VrNetworkServer(this);
		
		addKeyListener(this);
		
		this.setSize(Globals.srcDm);
		this.setTitle("Virtual Reality server 1.0");
		br=new BorderLayout();
		
		JButton bt=new JButton("test");
		jp.add(bt);
		br.addLayoutComponent(jp, BorderLayout.EAST);
		this.setLayout(br);
		
		this.add(bt, BorderLayout.CENTER);
		
		vru=new VrUniverse();
		BufferedImage bfi=vru.getBufImage();
		
		i3dp=new Image3dPanel(bfi);
		this.add(i3dp, BorderLayout.CENTER);

		// Start de thread voor het renderen
		new Thread(new RenderWorker(vru)).start();
			
	}
	
	// De key events
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("Key :"+e.getKeyChar());
			
		}

		public void keyPressed(KeyEvent e) {
			
			//System.out.println("Key :"+e.getKeyChar()+" "+e.getKeyCode());
			if(e.getKeyChar()=='a')
			{
				vru.toLeft();
			}
			if(e.getKeyChar()=='d')
			{
				vru.toRight();
			}
			if(e.getKeyChar()=='w')
			{
				vru.toUp();
			}
			if(e.getKeyChar()=='x')
			{
				vru.toDown();
			}
			if(e.getKeyChar()=='s')
			{
				vru.foreward();
			}
			if(e.getKeyChar()=='f')
			{
				vru.backward();
			}
			
			if(e.getKeyChar()=='j')
			{
				vru.bgmin();
			}
			if(e.getKeyChar()=='k')
			{
				vru.bgplus();
			}
			
			
			if(e.getKeyCode()==38)
			{
				vru.toYangleUp();
			}
			if(e.getKeyCode()==40)
			{
				vru.toYangleDown();
			}
			if(e.getKeyCode()==39)
			{
				vru.toXangleLeft();
			}
			if(e.getKeyCode()==37)
			{
				vru.toXangleRight();
			}
			
		
			
			BufferedImage bfi=vru.getBufImage();
			this.remove(i3dp);
			
			i3dp=new Image3dPanel(bfi);
			this.add(i3dp, BorderLayout.CENTER);
			
			this.revalidate();
			//this.repaint();
			
			Globals.delay(20);
			
		}

		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("Key :"+e.getKeyChar());
			
		}
		
		public BufferedImage getImage()
		{
			return vru.getBufImage();
		}
	
}
