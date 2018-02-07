package com.sande.vrserver.object3d;

import java.awt.Font;

import java.io.FileNotFoundException;


import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.media.j3d.Text3D;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.geometry.Box;


import javax.vecmath.AxisAngle4d;


import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;

import javax.media.j3d.Appearance;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.objectfile.ObjectFile;

public class VrBranchGraph 
{
	private BranchGroup group;
	
	public VrBranchGraph()
	{
		// Maak een branchgroup
		group = new BranchGroup();
		 
        // Plaats de objecten
		//TransformGroup tgCone=buildCone(-2,0,0);
        //tgCone.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //group.addChild(tgCone);
        
        TransformGroup tgCube=buildSphere(-4,-4,-4);
        tgCube.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        group.addChild(tgCube);		
        
        TransformGroup tgCube2=buildSphere(4,4,4);
        tgCube.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        group.addChild(tgCube2);
        
        //TransformGroup objTrans=buildObejct("d:\\tmp\\test10mm.obj");
        //objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //group.addChild(objTrans);
        
        TransformGroup tgText=this.setText("TEST", 4, 0, 0);
        tgText.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        group.addChild(tgText);
        	
        this.grid(group);
        
        
        group.addChild(this.cylinder(-4,-4,-4,4,4,4));
        
        drawBlocks(-4,-4,-4,4,4,4,group);
        
        /*for(int telx=-10;telx<10;telx+=2)
        {
        	for(int telz=-10;telz<10;telz+=2)
        	{
        		TransformGroup intCube=buildCone(telx,-1,telz);
                intCube.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                group.addChild(intCube);
        	}
        }*/
        
        // De belichting van het geheel
        Color3f light1Color = new Color3f(.1f, 1.4f, .1f); // green light
        BoundingSphere bounds =  new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        group.addChild(light1);
        
        
	}
	
	
	
	
	private void drawBlocks(double sx,double sy,double sz,double ex,double ey,double ez, BranchGroup group) 
	{
		
		double ay=(ey-sy)/30;
		double az=(ez-sz)/30;
		
		double step=(ex-sx)/30;
		System.out.println("step "+step);
		
		for(int tel=0;tel<30;tel++)
		{
			double tx=sx+tel*step;
			double ty=sy+(ay*tel);
			double tz=sz+(az*tel);
			group.addChild(buildCube(tx,ty,tz,0.2f));
		}
		
	}




	public BranchGroup getGroup() {
		return group;
	}

	
	public TransformGroup setText(String text,double x,double y, double z)
	{
		TransformGroup tgTrans=new TransformGroup();
		Transform3D tx3d=new Transform3D();
		BranchGroup txBranch=new BranchGroup();
		Vector3d txVector=new Vector3d(x,y,z);
		tx3d.setTranslation(txVector);
		tgTrans.setTransform(tx3d);
		
		Font myFont = new Font("TimesRoman",Font.PLAIN,10);			// Neem een font
		Font3D myFont3D = new Font3D(myFont,new FontExtrusion());	// Maak het font 3D
		Text3D myText3D = new Text3D(myFont3D, text);				// Maak een 3d text met het 3d font
		Shape3D myShape3D = new Shape3D(myText3D, new Appearance());	// Maak er ee shape van
		
		tgTrans.addChild(myShape3D);
		
		return tgTrans;
	}
	
	
	

	public static TransformGroup buildObejct(String filePathObject)
	{
		Scene sobj=null;
        ObjectFile object3d=new ObjectFile();
        try {
        	sobj=object3d.load(filePathObject);
		} catch (FileNotFoundException | IncorrectFormatException | ParsingErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        TransformGroup objtrans=new TransformGroup();
        Transform3D obj3d=new Transform3D();
        Vector3d vobj3d=new Vector3d(2,0,0);
        obj3d.setTranslation(vobj3d);
        objtrans.setTransform(obj3d);
        objtrans.addChild(sobj.getSceneGroup());
        
        return objtrans;
	}
	
	


	public static TransformGroup buildCube(double x,double y, double z,double size)
    {
    	TransformGroup tg=new TransformGroup();
    	Shape3D kubus=new ColorCube(size);
    	Transform3D tf3d=new Transform3D();
    	Vector3d v3d=new Vector3d(x,y,z);
    	tf3d.setTranslation(v3d);
    	tg.setTransform(tf3d);
    	tg.addChild(kubus);
    	return tg;
    }
    
    public static TransformGroup buildCone(double x,double y, double z)
    {
    	TransformGroup tg=new TransformGroup();
    	Cone cone=new Cone(1f, 1f);
    	Transform3D tf3d=new Transform3D();
    	tf3d.rotX(Math.PI/2);
    	Vector3d v3d=new Vector3d(x,y,z);
    	tf3d.setTranslation(v3d);
    	tg.setTransform(tf3d);
    	tg.addChild(cone);
    	return tg;
    }
    
    public static TransformGroup buildSphere(double x,double y, double z)
    {
    	TransformGroup tg=new TransformGroup();
    	Sphere sphere=new Sphere(1f);
    	Transform3D tf3d=new Transform3D();
    	tf3d.rotX(Math.PI/2);
    	Vector3d v3d=new Vector3d(x,y,z);
    	tf3d.setTranslation(v3d);
    	tg.setTransform(tf3d);
    	tg.addChild(sphere);
    	return tg;
    }
    
    
    public static void grid(BranchGroup group)
    {
    	for(int tel=-30;tel<30;tel++)
    	{
    		group.addChild(buildCube(tel,0,0,0.2f));
    		group.addChild(buildCube(0,tel,0,0.2f));
    		group.addChild(buildCube(0,0,tel,0.2f));
    	}
    }
    
    
    
    public TransformGroup cylinder(double sx,double sy,double sz,double ex,double ey,double ez)
    {
    	double size=Math.sqrt((sx-ex)*(sx-ex)+(sx-ex)*(sy-ey)+(sz-ez)*(sz-ez));
    	
    	double cx=(sx+ex)/2;		// Bepaal het centrum 
    	double cy=(sy+ey)/2;
    	double cz=(sz+ez)/2;
    	Vector3d v3d=new Vector3d(cx,cy,cz);
    	
    	System.out.println(cx+";"+cy+";"+cz);
    	
    	double rot_z=-Math.atan((ex-sx)/(ey-sy));
    	//double rot_y=-Math.atan((ez-sz)/(ex-sx));
    	double rot_y=-Math.atan((ey-sy)/(ex-sx));

    	System.out.println("rot_z "+rot_z+" rot_y "+rot_y);
    	
    	Transform3D   trans3D    = new Transform3D();
    	System.out.println("trans3D \n"+trans3D);
    	Transform3D   trans3DY   = new Transform3D();
    	System.out.println("trans3DY \n"+trans3DY);
    	Transform3D   trans3DZ   = new Transform3D();
    	System.out.println("trans3DZ \n"+trans3DZ);
    	
    	trans3DY.rotY(rot_y);	// Roteer om de Y-as
    	System.out.println("trans3DY \n"+trans3DY);
    	trans3DZ.rotZ(rot_z);
    	System.out.println("trans3DZ \n"+trans3DZ);
    	trans3DY.mul(trans3DZ);	
    	
    	System.out.println("trans3DY \n"+trans3DY);
    	
    	TransformGroup tg=new TransformGroup();
    	//Cylinder cone=new Cylinder(0.2f,(float) size);
    	//ColorCube cone=new ColorCube(size/2);
    	Appearance ap=new Appearance ();
    	Box cone=new Box(0.2f,(float)size/2,0.2f,ap);
    	
    	tg.setTransform(trans3DY);
    	tg.addChild(cone);
    	return tg;
    }
	
}
