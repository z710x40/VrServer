package com.sande.vrserver.worker;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sande.vrserver.VrUniverse;
import com.sande.vrserver.common.Globals;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class RenderWorker implements Runnable {

	
	private VrUniverse universe=null;
	private int delay=40;
	
	public RenderWorker(VrUniverse universe)
	{
		this.universe=universe;
		
		this.delay=1000/Globals.framesPerSeconds;
	}
	
	
	
	@Override
	public void run() {

		while (Globals.flagRenderWorker) {
			
			BufferedImage newImage = universe.getBufImage();	// Render een image
			
			if (newImage == null) {								// test of het is gelukt
				///System.out.println("Error in rendering");
				continue;										// Nee, behoudt het huidige image
			}
			Globals.imageL = newImage;							// Zo ja, zet de objecten om
			Globals.imageR = Globals.imageL;

			Globals.imageStringL = encodeToString(Globals.imageL);
			Globals.imageStringR = encodeToString(Globals.imageR);

			Globals.delay(this.delay);
		}

	}
	
	
	
	private String encodeToString(BufferedImage image) {
		if(image==null)return null;
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        
        try {
			ImageIO.write(image, "PNG", baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String stringImage =Base64.encode(baos.toByteArray());
        //System.out.println("size "+stringImage.length());
        return stringImage;
    }

}
