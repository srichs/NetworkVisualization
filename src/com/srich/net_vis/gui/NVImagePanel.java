package com.srich.net_vis.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Filename: NVImagePanel.java <p>
 * Date: 01 Nov 2019 <p>
 * 
 * Purpose: This class is used to create a panel where an image of a network is displayed 
 * after being processed by GraphViz. <p>
 * 
 * @author srichs <p>
 */
public class NVImagePanel extends JPanel {

	//NVImagePanel Class Variables
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the NVImagePanel
	 * @param filepath The file path where the image is stored
	 */
    public NVImagePanel(String filepath) {
    	this.setBackground(Color.WHITE);
    	this.setLayout(new BorderLayout());
    	try {                
    		BufferedImage image = ImageIO.read(new File(filepath));
    		this.add(new JLabel(new ImageIcon(image)));
    	} catch (IOException ex) {
            ex.printStackTrace();
    	}
    }
    
    /**
     * Constructor for the NVImagePanel
     * @param image A byte array of an image
     */
    public NVImagePanel(byte[] image) {
    	this.setBackground(Color.WHITE);
    	this.setLayout(new BorderLayout());
    	try {
    		InputStream in = new ByteArrayInputStream(image);
    		BufferedImage img = ImageIO.read(in);
    		this.add(new JLabel(new ImageIcon(img)));
    	} catch (IOException ex) {
            ex.printStackTrace();
    	}
    }
    
    /**
     * Constructor for the NVImagePanel
     * @param image A byte array of an image
     * @param scaleFactor An integer value
     */
    public NVImagePanel(byte[] image, int scaleFactor) {
    	this.setBackground(Color.WHITE);
    	this.setLayout(new BorderLayout());
    	try {
    		InputStream in = new ByteArrayInputStream(image);
    		BufferedImage img = ImageIO.read(in);
    		int scaleX = (int) (img.getWidth() * scaleFactor / 100);
    		int scaleY = (int) (img.getHeight() * scaleFactor / 100);
    		Image newImg = img.getScaledInstance(scaleX, scaleY, Image.SCALE_SMOOTH);
    		BufferedImage buffered = new BufferedImage(scaleX, scaleY, IndexColorModel.OPAQUE);
    		buffered.getGraphics().drawImage(newImg, 0, 0 , null);
    		buffered.getGraphics().dispose();
    		this.add(new JLabel(new ImageIcon(buffered)));
    	} catch (IOException ex) {
            ex.printStackTrace();
    	}
    }
    
    /**
     * Constructor for the NVImagePanel
     */
    public NVImagePanel() {
    	this.setBackground(Color.WHITE);
    	this.setLayout(new BorderLayout());
    }
    
    /**
     * A method that gets the panel's size as a Dimension Object.
     * @return A Dimension of the panel's size
     */
    public Dimension getImagePanelSize() {
    	return this.getSize();
    }
	
}
