package com.sande.vrserver;


import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Screen3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import com.sande.vrserver.common.Globals;
import com.sande.vrserver.object3d.VrBackground;
import com.sande.vrserver.object3d.VrBranchGraph;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import javax.media.j3d.RestrictedAccessException;

public class VrUniverse {
	
	static final double pi=22/3;
	static double x=0;
	static double y=0;
	static double z=20;
	
	static double rot=0;
	
	static double xAngle=Math.PI/2;
	static double yAngle=0;
	
	Canvas3D canvasOffScreen;
	SimpleUniverse universe;
	ViewingPlatform vp;
	VrBranchGraph brGraph;
	VrBackground vrBackground;
	
	static double stickSize=100;
	
	GraphicsConfiguration config;
	public View camera;
	
	public VrUniverse()
	{
		//Get the preferred graphics configuration for the default screen
        config = SimpleUniverse.getPreferredConfiguration();
 
        // Set de offscreen buffer
        BufferedImage bImage = new BufferedImage(Globals.srcDm.width,Globals.srcDm.height,BufferedImage.TYPE_INT_BGR);
        ImageComponent2D ic2d=new ImageComponent2D(ImageComponent2D.FORMAT_RGB,bImage);
        ic2d.setCapability(ImageComponent2D.ALLOW_IMAGE_READ);
        
        //Create a Canvas3D using the preferred configuration
        canvasOffScreen = new Canvas3D(config,true);
        canvasOffScreen.setOffScreenBuffer(ic2d);
        
        Screen3D sOff = canvasOffScreen.getScreen3D();
        sOff.setSize(Globals.srcDm);
        sOff.setPhysicalScreenWidth(1);
        sOff.setPhysicalScreenHeight(0.5);


        //Create a Simple Universe with view branch
        SimpleUniverse universe = new SimpleUniverse(canvasOffScreen);

        //maak de objecten 
        brGraph=new VrBranchGraph();
        
        // Voeg de objecten aan de universe toe
        universe.addBranchGraph(brGraph.getGroup());
        
        vrBackground=new VrBackground("D:\\tmp\\sky1.jpeg",100,rot);
        universe.addBranchGraph(vrBackground.getGroup());
        
        vp=universe.getViewingPlatform();
        vp.setNominalViewingTransform();
        
        //Set the render distance of objects
        camera=universe.getViewer().getView();
        camera.setBackClipDistance(200.0);
	}

	
	public BufferedImage getBufImage()
	{
		try{
			canvasOffScreen.renderOffScreenBuffer();
		} catch(RestrictedAccessException e ){
			return null;
		}
		canvasOffScreen.waitForOffScreenRendering();
		ImageComponent2D ic2d= canvasOffScreen.getOffScreenBuffer();
		BufferedImage bi=ic2d.getImage();
		//System.out.println("image object "+bi.hashCode());
		return bi;
	}
	
	// Verplaats de camera
    public void setCamera(double x,double y,double z,double xAngle,double yAngle)
    {
    	//System.out.println("Replace camera");
    	//System.out.println(new String().format("x %f y %f z %f   rad x-rad %f y-rad %f",x,y,z,xAngle,yAngle));
    	double xPoint=Math.sin(xAngle)*stickSize;
    	double yPoint=Math.sin(yAngle)*stickSize;
    	double zPoint=Math.cos(xAngle)*stickSize;
    	//System.out.println(new String().format("x %f y %f z %f   rad x-rad %f y-rad %f",x,y,z,xAngle,yAngle));
    	//System.out.println(new String().format("Point 3d  x %f y %f z %f",x+xPoint,y+yPoint,z+zPoint));
    	TransformGroup vptg=vp.getViewPlatformTransform();
    	Transform3D ntf3d=new Transform3D();
    	ntf3d.lookAt(new Point3d(x,y,z),new Point3d(x+xPoint,y+yPoint,z+zPoint), new Vector3d(0,100,0));		// lookAt is de key tot de positie van de camera
    	ntf3d.invert();
    	vptg.setTransform(ntf3d);
    	
    }
    
    
    // Methods om de camera te bewegen
    public void toLeft()
    {
    	x=x-0.05;
    	setCamera(x,y,z,xAngle,yAngle);
    }
    
    public void toRight()
    {
    	x=x+0.05;
    	setCamera(x,y,z,xAngle,yAngle);
    }
    
    
    public void toUp()
    {
    	y=y+0.05;
    	setCamera(x,y,z,xAngle,yAngle);
    }
    
    public void toDown()
    {
    	y=y-0.05;
    	setCamera(x,y,z,xAngle,yAngle);
    }
    
    public void toXangleLeft()
    {
    	xAngle=xAngle-0.05;
    	setCamera(x,y,z,xAngle,yAngle);
    }
    
    public void toXangleRight()
    {
    	xAngle=xAngle+0.05;
    	setCamera(x,y,z,xAngle,yAngle);
    }
    
    public void toYangleUp()
    {
    	yAngle=yAngle+0.05;
    	setCamera(x,y,z,xAngle,yAngle);
    }
    
    public void toYangleDown()
    {
    	yAngle=yAngle-0.05;
    	setCamera(x,y,z,xAngle,yAngle);
    } 
    
    public void foreward()
    {
    	x=x+0.2*Math.sin(xAngle);
    	z=z+0.2*Math.cos(xAngle);
    	y=y+0.2*Math.sin(yAngle);
    	setCamera(x,y,z,xAngle,yAngle);
    }
    
    public void backward()
    {
    	x=x-0.2*Math.sin(xAngle);
    	z=z-0.2*Math.cos(xAngle);
    	y=y-0.2*Math.sin(yAngle);
    	setCamera(x,y,z,xAngle,yAngle);
    }


	public void bgmin() {
		// TODO Auto-generated method stub
		rot=rot+0.3;
		
		
	}


	public void bgplus() {
		rot=rot-0.3;
				
				
	}
}
