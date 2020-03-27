package com.srich.net_vis.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
    private BufferedImage image;
    
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
    		this.image = img;
    	} catch (IOException ex) {
            ex.printStackTrace();
    	}
    }
    
    public NVImagePanel() {
        this.setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
    }
    
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        int x = (this.getWidth() - image.getWidth(null)) / 2;
        int y = (this.getHeight() - image.getHeight(null)) / 2;
        g2d.drawImage(this.image, x, y, null);
    }
}
