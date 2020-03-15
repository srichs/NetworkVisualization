package com.srich.net_vis.main;

import com.srich.net_vis.gui.NVMainFrame;

/**
 * Filename: NVMain.java <p>
 * Date: 01 Nov 2019 <p>
 * 
 * Purpose: The Network Visualization tool is used to design networks using cellular 
 * structures and intermediaries. <p>
 * 
 * This class contains the program's <code>main<code> method and instantiates the GUI for the program.
 * 
 * @author srichs <p> 
 * @version %I%
 */
public class NVMain {

	/**
	 * The main method for the NV application. Creates an instance of the NVMainFrame Class.
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	NVMainFrame mainFrame = new NVMainFrame();
            	mainFrame.setTitle("Network Visualization Tool");
            }
        });
	}
	
}
