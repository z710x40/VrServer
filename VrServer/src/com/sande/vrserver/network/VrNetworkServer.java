package com.sande.vrserver.network;


import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


import com.sande.vrserver.VrServer;
import com.sande.vrserver.common.Globals;

public class VrNetworkServer implements Runnable{

	
	Socket clientSocket = null;
	Thread listener = null;
	//OutputStream out=null;
	InputStream inp=null;
	ServerSocket serverSocket;
	
	VrServer vrServer=null;
	String jpgImage=null;
	
	boolean threadFlag=true;
	
	double xAngle,yAngle;
	
	public VrNetworkServer(VrServer vrServer)
	{
		try {
			serverSocket = new ServerSocket(3000);
			serverSocket.setReuseAddress(true);
		} catch (IOException e) {
			this.threadFlag=false;
			e.printStackTrace();
		}
		
		listener=new Thread(this);
		listener.start();
		this.vrServer=vrServer;
	}
	
	@Override
	public void run() {
		System.out.println("Start server loop");
		
		//De loop
		threadFlag=true;
		while(threadFlag)
		{
			clientSocket=networkStart();
			ServerLoop(clientSocket);
			try {
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
		}
		
	}

	
	
	private void ServerLoop(Socket clientSocket) {
		PrintWriter out=null;
		BufferedReader inp=null;
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			inp=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		
		boolean flag=true;
		while(flag)
		{
			String inString;
			try {
				inString = inp.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=false;
				continue;
			}
			if(inString==null)
			{
				System.out.println("End of stream, possible disconnect");
				flag=false;
				continue;
			}
			if(inString.startsWith("pos"))		// Geef positie door
			{
				// Geef nieuwe coordinaten
				out.println("pos");
				System.out.println("pos received");
			}
			
			if(inString.startsWith("size"))		// Vraag om de size
			{
				System.out.println("size received");
				if(Globals.imageStringL!=null)
				{	
					out.println(Globals.imageStringL.length());
				}
				else
				{
					out.println("0");
					System.out.println("error inrendering");
				}
			}
			
			if(inString.startsWith("oke"))		// Stuur de string met de image
			{
				if(Globals.imageStringL!=null)
				{
					out.println(Globals.imageStringL);
					System.out.println("oke received");
				}
				else
				{
					out.println("== ERROR ==");
				}
			}
		}
		
		try {
			out.close();
			inp.close();
		} catch (IOException e) {
			System.out.println("Cannot close sockets");
			this.threadFlag=false;
			e.printStackTrace();
			System.exit(1);
		}
		
	}

	public Socket networkStart()
	{
		
		Socket clientSocket=null;
		try {
			
			clientSocket = serverSocket.accept();
			System.out.println("Server socket geopend ");
		} catch (IOException e) {
			this.threadFlag=false;
			e.printStackTrace();
			System.exit(1);
		}
		
		
		
		return clientSocket;
	}
	
}
