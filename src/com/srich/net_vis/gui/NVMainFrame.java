package com.srich.net_vis.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.srich.net_vis.model.GraphViz;
import com.srich.net_vis.model.NVNetwork;
import com.srich.net_vis.model.NVNode;
import com.srich.net_vis.model.NVNode.NVConnType;
//import com.srich.net_vis.test.NVTest; //for testing purposes

/**
 * Filename: NVMainFrame.java <p>
 * Date: 01 Nov 2019 <p>
 * 
 * Purpose: This class is used to create the main JFrame where the GUI is
 * created and all user interaction is handled. <p>
 * 
 * @author srichs <p>
 */
public class NVMainFrame extends JFrame implements ActionListener {
	
	//NVMainFrame Class Variables
	private static final long serialVersionUID = 1L;
	private JPanel treePanel, displayPanel, tBtnPanel;
	private JButton addNodeBtn, deleteBtn, editBtn;
	private JSplitPane splitPane;
	private JScrollPane tPane, dPane;
	private NVNodeInfoFrame infoFrame;
	private NVImagePanel imagePanel;
	private JTree tree;
	private NVNetwork network;
	private GraphViz gv;
	private String graphLayout;
	private String imageExt;
	private String nodeLabels;
	private final String[] gvLayouts = {"dot", "neato", "twopi", "circo", "fdp", "sfdp", "patchwork", "osage"};
	private final String[] fileTypes = {"bmp", "gif", "jpg", "pdf", "png", "svg", "tga", "tif"};
	private final String[] labelTypes = {"No Labels", "ID Number", "Abbreviation"};

	/**
	 * Constructor for the NVMainFrame Class.
	 */
	public NVMainFrame() {
		super();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(720, 480);
        this.setResizable(true);
        this.setMinimumSize(new Dimension(900, 500));
        this.setLayout(new BorderLayout());
        this.setIcons();
        this.setupNew();
        this.initComponents();
	}
	
	private void setIcons() {
		List<Image> icons = new ArrayList<Image>();
		icons.add(new ImageIcon("res/usnvt_16x16.png").getImage());
		icons.add(new ImageIcon("res/usnvt_24x24.png").getImage());
		icons.add(new ImageIcon("res/usnvt_32x32.png").getImage());
		icons.add(new ImageIcon("res/usnvt_64x64.png").getImage());
		icons.add(new ImageIcon("res/usnvt_256x256.png").getImage());
		this.setIconImages(icons);
	}
	
	/**
	 * A method that is used to instantiate some variables of the class.
	 */
	private void setupNew() {
		this.network = new NVNetwork();
        this.gv = new GraphViz();
        this.graphLayout = "dot";
        this.imageExt = "pdf";
        this.nodeLabels = "ID Number";
	}
	
	/**
     * A method that is used to instantiate some variables of the class.
     */
    private void setupNewWithSettings(String gLayout, String iExt, String nLabel) {
        this.network = new NVNetwork();
        this.gv = new GraphViz();
        this.graphLayout = gLayout;
        this.imageExt = iExt;
        this.nodeLabels = nLabel;
    }
	
	/**
	 * A method that is used to instantiate the GUI components of the class.
	 */
	private void initComponents() {
		this.buildMenuBar();
		this.treePanel = new JPanel();
		this.tPane = new JScrollPane();
		this.dPane = new JScrollPane();
		this.displayPanel = new JPanel();
		this.tBtnPanel = new JPanel();
		this.tBtnPanel.setMaximumSize(new Dimension(300, 0));
		this.tBtnPanel.setMinimumSize(new Dimension(300, 0));
		this.tBtnPanel.setLayout(new FlowLayout());
		this.addNodeBtn = new JButton("Add Node");
		this.addNodeBtn.setActionCommand("add");
		this.addNodeBtn.addActionListener(this);
		this.editBtn = new JButton("Edit Node");
		this.editBtn.setActionCommand("edit");
		this.editBtn.addActionListener(this);
		this.deleteBtn = new JButton("Delete Node");
		this.deleteBtn.setActionCommand("delete");
		this.deleteBtn.addActionListener(this);
		this.tBtnPanel.add(this.addNodeBtn);
		this.tBtnPanel.add(this.editBtn);
		this.tBtnPanel.add(this.deleteBtn);
		
		this.treePanel.setLayout(new BoxLayout(this.treePanel, BoxLayout.Y_AXIS));
		this.treePanel.add(this.tPane);
		this.treePanel.add(this.tBtnPanel);
		
		this.displayPanel.setLayout(new BoxLayout(this.displayPanel, BoxLayout.Y_AXIS));
		this.displayPanel.setBackground(Color.WHITE);
		this.displayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		this.dPane.setBackground(Color.WHITE);
		this.displayPanel.add(this.dPane);
		
		this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.treePanel, this.displayPanel);
		this.splitPane.setDividerLocation(300);
		this.splitPane.setDividerSize(5);
		this.tree = new JTree();
		this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		this.add(this.splitPane);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		this.initNew(); //this.initNet(); for testing
	}
	
	/**
	 * A method to build the JMenuBar for the JFrame.
	 */
	private void buildMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newItem = new JMenuItem("New");
		newItem.setActionCommand("itemNew");
		newItem.addActionListener(this);
		JMenuItem openItem = new JMenuItem("Open");
		openItem.setActionCommand("itemOpen");
		openItem.addActionListener(this);
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.setActionCommand("itemSave");
		saveItem.addActionListener(this);
		JMenuItem settings = new JMenuItem("GV Settings");
		settings.setActionCommand("itemSettings");
		settings.addActionListener(this);
		JMenuItem lSettings = new JMenuItem("Label Settings");
		lSettings.setActionCommand("itemLSettings");
		lSettings.addActionListener(this);
		JMenu export = new JMenu("Export To");
		JMenuItem image = new JMenuItem("Image File");
		image.setActionCommand("itemImage");
		image.addActionListener(this);
		JMenuItem graphviz = new JMenuItem("Graphviz File");
		graphviz.setActionCommand("itemGV");
		graphviz.addActionListener(this);
		JMenuItem listExport = new JMenuItem("Node .csv File");
		listExport.setActionCommand("itemList");
		listExport.addActionListener(this);
		
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(settings);
		fileMenu.add(lSettings);
		export.add(image);
		export.add(graphviz);
		export.add(listExport);
		fileMenu.add(export);
		
		JMenu helpMenu = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		about.setActionCommand("itemAbout");
		about.addActionListener(this);
		JMenuItem help = new JMenuItem("Help");
		help.setActionCommand("itemHelp");
		help.addActionListener(this);
		
		helpMenu.add(about);
		helpMenu.add(help);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * A method that is used to create a new root node, update the JTree and 
	 * update the display panel where the graph is displayed.
	 */
	private void initNew() {
		this.createRoot();
		this.updateTree();
		this.networkToGV();
	}
	
	/**
	 * A method used to create a new root node in the network Object.
	 */
	private void createRoot() {
		NVNode node = new NVNode();
		node.setId(this.network.getIdCount());
		node.setName("Root");
		node.setConnType(NVConnType.ONE_PER);
		node.setNewNode(false);
		node.setParentId(0);
		this.network.setRootNode(node);
	}
	
	/**
	 * A method that is used to update the JTree display of the network.
	 */
	private void updateTree() {
		this.tree = this.network.toTree();
		this.expandAllNodes(this.tree, 0, this.tree.getRowCount());
		this.tPane.setViewportView(this.tree);
		this.tree.revalidate();
		this.tree.repaint();
	}
	
	/**
	 * A recursive method that is used to expand all of the nodes with handles 
	 * in the JTree.
	 * @param tree A JTree Object
	 * @param startingIndex The index to start at
	 * @param rowCount The number of rows in the JTree
	 */
	private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
	    for(int i=startingIndex;i<rowCount;++i){
	        tree.expandRow(i);
	    }
	    if(tree.getRowCount()!=rowCount){
	        expandAllNodes(tree, rowCount, tree.getRowCount());
	    }
	}
	
	/**
	 * A method that is used to get a complete GraphViz Dot notation in a single String variable.
	 * @return A String of the network in GraphViz Dot notation
	 */
	private String getGVDotStr() {
	    if(this.nodeLabels.equals("Abbreviation")) {
	        return this.network.networkToGV(); 
	    } else if(this.nodeLabels.equals("ID Number")) {
	        return this.network.networkToGVID();
	    } else if(this.nodeLabels.equals("No Labels")) {
	        return this.network.networkToGVNL();
	    } else {
	        return ""; // Error
	    }
	}
	
	/**
	 * A method that is used to update the image of the graph displayed in the application.
	 */
	private void networkToGV() {
		this.dPane.setPreferredSize(new Dimension(100,100));
		byte[] image = this.gv.getGraph(this.getGVDotStr(), "png", this.graphLayout);
		this.imagePanel = new NVImagePanel(image);
		this.dPane.setViewportView(this.imagePanel);
		this.dPane.revalidate();
		this.dPane.repaint();
	}
	
	/**
	 * A method that is used to export a graph to an image file
	 */
	private void saveGVImageToFile() {
		JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new File("/home/me/Documents"));
	    int retrieval = chooser.showSaveDialog(null);
	    File file = null;
	    if (retrieval == JFileChooser.APPROVE_OPTION) {
	        try {
	        	file = chooser.getSelectedFile();
	        	file = new File(file.toString() + "." + this.imageExt);
	        	byte[] image = this.gv.getGraph(this.getGVDotStr(), this.imageExt, this.graphLayout);
	        	gv.writeGraphToFile(image, file);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	/**
	 * This method takes a String that contains the GraphViz Dot notation and saves 
	 * it to the chosen location.
	 */
	private void saveToGVFile() {
		try {
			JFileChooser jfc = new JFileChooser();
			jfc.setCurrentDirectory(new File("/home/me/Documents"));
		    int retrieval = jfc.showSaveDialog(null);
		    File file = null;
		    if (retrieval == JFileChooser.APPROVE_OPTION) {
		        file = jfc.getSelectedFile();
		    }
		    if(file != null) {
		    	file = new File(file.toString() + ".gv");
				PrintWriter p = new PrintWriter(file);
				p.println(this.getGVDotStr());
				p.close();
		    }
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}
	
	/**
	 * A method that is used to save a network to a file.
	 */
	private void saveNetworkToFile() {
		try {
			JFileChooser jfc = new JFileChooser();
			jfc.setCurrentDirectory(new File("/home/me/Documents"));
		    int retrieval = jfc.showSaveDialog(null);
		    File file = null;
		    if (retrieval == JFileChooser.APPROVE_OPTION) {
		        file = jfc.getSelectedFile();
		    }
		    if(file != null) {
		        file = new File(file.toString() + ".nv");
				FileOutputStream f = new FileOutputStream(file);
				ObjectOutputStream o = new ObjectOutputStream(f);
				o.writeObject(this.network);
				o.close();
				f.close();
		    }
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		}
	}
	
	/**
	 * A method that is used to load a network from a file.
	 */
	private void loadNetworkFromFile() {
		try {
			JFileChooser jfc = new JFileChooser();
			jfc.setCurrentDirectory(new File("/home/me/Documents"));
		    int retrieval = jfc.showOpenDialog(null);
		    File file = null;
		    if (retrieval == JFileChooser.APPROVE_OPTION) {
		        file = jfc.getSelectedFile();
		    }
		    if(file != null) {
		        String fName = file.getName();
		        if(fName.substring(fName.lastIndexOf(".") + 1).equals("nv")) {
    				FileInputStream f = new FileInputStream(file);
    				ObjectInputStream o = new ObjectInputStream(f);
    				this.network = (NVNetwork) o.readObject();
    				o.close();
    				f.close();
		        } else {
		            JOptionPane.showMessageDialog(new JFrame(), "Only .nv filetypes can be opened.", "Warning", 
		                    JOptionPane.WARNING_MESSAGE);
		        }
		    }
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			System.out.println("Error initializing stream");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * This method takes a String that contains the GraphViz Dot notation and saves 
     * it to the chosen location.
     */
    private void saveListToFile() {
        try {
            JFileChooser jfc = new JFileChooser();
            jfc.setCurrentDirectory(new File("/home/me/Documents"));
            int retrieval = jfc.showSaveDialog(null);
            File file = null;
            if (retrieval == JFileChooser.APPROVE_OPTION) {
                file = jfc.getSelectedFile();
            }
            if(file != null) {
                file = new File(file.toString() + ".csv");
                PrintWriter p = new PrintWriter(file);
                p.println(this.network.networkToList());
                p.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
	
	/**
	 * A method to setup a new NVInfoFrame.
	 */
	private void infoFrameInit() {
		this.infoFrame.getSaveBtn().addActionListener(this);
		this.infoFrame.getCancelBtn().addActionListener(this);
		this.infoFrame.setAlwaysOnTop(true);
		this.setEnabled(false);
	}
	
	/**
	 * A method to be called when the 'Add Node' button is pressed
	 */
	private void addNodePressed() {
		this.infoFrame = new NVNodeInfoFrame(this.network.getIdCount(), this.network.getNetworkMap());
		this.infoFrameInit();
	}
	
	/**
	 * A method to be called when the 'Edit Node' button is pressed
	 */
	private void editNodePressed() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.tree.getLastSelectedPathComponent();
		if(node != null) {
			NVNode nvNode = this.network.getNodeById(NVNode.idFromString(node.toString()));
			this.infoFrame = new NVNodeInfoFrame(nvNode, this.network.getNetworkMap());
			this.infoFrameInit();
		}
		else
			JOptionPane.showMessageDialog(new JFrame(), "No node selected.", "Warning", 
			        JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * A method to be called when the 'Delete Node' button is pressed
	 */
	private void deleteNodePressed() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.tree.getLastSelectedPathComponent();
		if(node != null) {
			NVNode nvNode = this.network.getNodeById(NVNode.idFromString(node.toString()));
			if(nvNode.getId() == 10000)
				JOptionPane.showMessageDialog(new JFrame(), "Cannot delete the Root Node.", 
				        "Warning", JOptionPane.WARNING_MESSAGE);
			else {
				this.network.removeNode(nvNode);
				this.dPane.setViewportView(new NVImagePanel());
				this.updateTree();
				this.networkToGV();
			}
		}
		else
			JOptionPane.showMessageDialog(new JFrame(), "No node selected.", "Warning", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * A method to be called when the 'Save' button is pressed in an NVInfoFrame.
	 */
	private void infoSavePressed() {
		if(!this.infoFrame.getName().equals("")) {
			if(this.infoFrame.wasChanged()) {
				this.infoFrame.setValuesToNode();
				NVNode node = this.infoFrame.getNode();
				if(this.infoFrame.isNewNode()) {
					node.setNewNode(false);
					this.network.addNode(node);
				}
				else {
					this.network.editNode(node);
				}
				this.updateTree();
				this.networkToGV();
			}
			this.infoFrame.dispose();
			this.setVisible(true);
			this.setEnabled(true);
		}
		else {
			JOptionPane.showMessageDialog(new JFrame(), "Please enter a name for "
			        + "the node.", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * A method to be called when the 'Cancel' button is pressed in an NVInfoFrame.
	 */
	private void infoCancelPressed() {
		this.infoFrame.dispose();
		this.setVisible(true);
		this.setEnabled(true);
	}
	
	/**
	 * A method that creates a JOptionPane for the About information for the application.
	 */
	private void aboutMessage() {
		String str = "Network Visualiztion Tool\n";
		str = str + "Version: 0.1.0\n\n";
		str = str + "Description: Used to design networks and visualize them using GraphViz.\n\n";
		str = str + "Created By: srichs\n";
		str = str + "Email: sr@srich.us\n";
		str = str + "Date Created: November 2019\n\n";
		JOptionPane.showMessageDialog(new JFrame(), str, "About", JOptionPane.PLAIN_MESSAGE);
	}
	
	/**
     * A method that creates a JOptionPane for the Label Settings of the application.
     * @return A boolean value
     */
    private boolean labelsPane() {
        String s = (String)JOptionPane.showInputDialog(
                new JFrame(),
                "Select the Node Label Setting",
                "Label Settings",
                JOptionPane.PLAIN_MESSAGE,
                null,
                this.labelTypes,
                this.nodeLabels);
        if ((s != null) && (s.length() > 0)) {
            this.nodeLabels = s;
            return true;
        }
        else
            return false;
    }
	
	/**
	 * A method that creates a JOptionPane for the Settings of the application.
	 * @return A boolean value
	 */
	private boolean settingsPane() {
		String s = (String)JOptionPane.showInputDialog(
                new JFrame(),
                "Select the Graphviz Layout to Use",
                "Settings",
                JOptionPane.PLAIN_MESSAGE,
                null,
                this.gvLayouts,
                this.graphLayout);
		if ((s != null) && (s.length() > 0)) {
			this.graphLayout = s;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * A method that creates a JOptionPane for the filetype when saving an image.
	 * @return A boolean value
	 */
	private boolean fileTypePane() {
		String s = (String)JOptionPane.showInputDialog(
                new JFrame(),
                "Select the Filetype for the Image",
                "Select Filetype",
                JOptionPane.PLAIN_MESSAGE,
                null,
                this.fileTypes,
                this.imageExt);
		if ((s != null) && (s.length() > 0)) {
			this.imageExt = s;
			return true;
		}
		else
			return false;
	}

	/**
	 * A method that is implemented for the ActionListener Interface for Actions Performed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if("add".equals(e.getActionCommand())) {
			this.addNodePressed();
		}
		else if("edit".equals(e.getActionCommand())) {
			this.editNodePressed();
		}
		else if("delete".equals(e.getActionCommand())) {
			this.deleteNodePressed();
		}
		else if("save".equals(e.getActionCommand())) {
			this.infoSavePressed();
		}
		else if("cancel".equals(e.getActionCommand())) {
			this.infoCancelPressed();
		}
		else if("itemNew".equals(e.getActionCommand())) {
		    int dialogButton = JOptionPane.YES_NO_OPTION;
		    int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you " + 
		            "want to create a new visualization? If you continue all data will be lost.", 
		            "Warning", dialogButton);
		    if(dialogResult == JOptionPane.YES_OPTION){
		        this.setupNewWithSettings(this.graphLayout, this.imageExt, this.nodeLabels);
	            this.tPane.setViewportView(new JPanel());
	            this.dPane.setViewportView(new JPanel());
	            this.initNew();
		    }
			
		}
		else if("itemOpen".equals(e.getActionCommand())) {
			this.loadNetworkFromFile();
			this.updateTree();
			this.networkToGV();
		}
		else if("itemSave".equals(e.getActionCommand())) {
			this.saveNetworkToFile();
		}
		else if("itemSettings".equals(e.getActionCommand())) {
			if(this.settingsPane())
				this.networkToGV();
		}
		else if("itemLSettings".equals(e.getActionCommand())) {
            if(this.labelsPane())
                this.networkToGV();
        }
		else if("itemImage".equals(e.getActionCommand())) {
			if(this.fileTypePane())
				this.saveGVImageToFile();
		}
		else if("itemGV".equals(e.getActionCommand())) {
			this.saveToGVFile();
		}
		else if("itemList".equals(e.getActionCommand())) {
            this.saveListToFile();
        }
		else if("itemAbout".equals(e.getActionCommand())) {
			this.aboutMessage();
		}
		else if("itemHelp".equals(e.getActionCommand())) {
			JOptionPane.showMessageDialog(new JFrame(), "Help not yet configured.");
		}
	}
	
	//for testing purposes
	/*private void initNet() {
		boolean isTest = true;
		if(isTest) {
			NVTest test = new NVTest();
			this.network = test.getNet();
			this.network.sortByValue();
			this.updateTree();
			this.networkToGV();
		}
		else {
			this.initNew();
		}
	}*/
	
}
