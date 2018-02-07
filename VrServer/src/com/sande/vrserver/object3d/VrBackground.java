package com.sande.vrserver.object3d;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

public class VrBackground {
	
	
	private BranchGroup group;
	private String imageFile;
	private int sphereSize;
	
	public VrBackground(String imageFile,int sphereSize,double rot)
	{
		group = new BranchGroup();
		
		group.addChild(setBackGround(imageFile,sphereSize,rot));
		
	}
	
	public BranchGroup getGroup() {
		return group;
	}

	public TransformGroup setBackGround(String imageFile,int sphereSize,double rot)
	{
		TransformGroup bgTrans=new TransformGroup();
		Transform3D bgt3d=new Transform3D();
		BranchGroup bgGroup=new BranchGroup();
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),sphereSize);		// Zet de bol voor de achtergrond
		Background bg=new Background();
		bg.setApplicationBounds(bounds);   // Plak de background op de bol
		Sphere sphereObj = new Sphere(1.0f, Sphere.GENERATE_NORMALS | Sphere.GENERATE_NORMALS_INWARD | Sphere.GENERATE_TEXTURE_COORDS, 45);
		
		 Appearance backgroundApp = sphereObj.getAppearance();
		 
		 BufferedImage bufImage=null;
		try {
			bufImage = ImageIO.read(new File(imageFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 TextureLoader txl=new TextureLoader(bufImage);
		 backgroundApp.setTexture(txl.getTexture());
		 bgGroup.addChild(sphereObj);
		 bg.setGeometry(bgGroup);
		 bgTrans.addChild(bg);
		 
		 bgt3d.rotX(rot);
		 bgTrans.setTransform(bgt3d);
		 
		 bgTrans.setName("background");
		 
		 return bgTrans;
	}
	
}
