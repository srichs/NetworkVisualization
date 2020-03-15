package com.srich.net_vis.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.srich.net_vis.model.NVNode;
import com.srich.net_vis.model.NVNode.NVConnType;

/**
 * Filename: NVNodeInfoFrame.java <p>
 * Date: 01 Nov 2019 <p>
 * 
 * Purpose: This class is used to create or edit an NVNode that will be part of 
 * an NVNetwork Object. <p>
 * 
 * @author srichs <p>
 */
public class NVNodeInfoFrame extends JFrame {
	
	//NVNodeInfoFrame Class Variables
	private static final long serialVersionUID = 1L;
	private NVNode node;
	private HashMap<String, NVNode> nMap;
	private JLabel idLabel;
	private JTextField nameField;
	private JComboBox<String> cTypeBox, pIdBox;
	private JButton saveBtn, cancelBtn;
	
	/**
	 * A Constructor for the NVNodeInfoFrame Class
	 * @param id The id of an NVNode
	 * @param map A HashMap of all NVNodes
	 */
	public NVNodeInfoFrame(int id, HashMap<String, NVNode> map) {
		super();
		this.nMap = map;
		this.node = new NVNode();
		this.node.setId(id);
		this.node.setNewNode(true);
		this.setTitle("Add New Node");
        this.initComponents();
	}
	
	/**
	 * A Constructor for the NVNodeInfoFrame Class
	 * @param node An NVNode Object
	 * @param map A HashMap of all NVNodes
	 */
	public NVNodeInfoFrame(NVNode node, HashMap<String, NVNode> map) {
		super();
		this.nMap = map;
		this.node = node;
		if(node.getId() == 10000) {
			this.setTitle("Edit Root Node");
		}
		else {
			this.setTitle("Edit Node");
		}
		this.initComponents();
	}
	
	/**
	 * A method that returns a boolean value for if the Object is displayed.
	 * @return A boolean value
	 */
	public boolean isOpen() {
		if(this.isShowing())
			return true;
		else
			return false;
	}
	
	/**
	 * A method that is used to instantiate the GUI Components of the class.
	 */
	private void initComponents() {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		JPanel panel = new JPanel();
		this.isAlwaysOnTop();
        this.setSize(720, 480);
        this.setResizable(false);
        this.setMinimumSize(new Dimension(300, 100));
        this.setLayout(new BorderLayout());
        
        this.idLabel = new JLabel();
        this.nameField = new JTextField();
        
		String[] cTStr = {"Direct", "Single", "One Per"};
		this.cTypeBox = new JComboBox<>(cTStr);
		this.cTypeBox.setActionCommand("connection");
		this.cTypeBox.addActionListener(this.cTypeBox);
		
		if(!this.node.isNewNode()) {
			this.cTypeBox.setSelectedItem(this.node.getConnStr());
		}
		
		this.cancelBtn = new JButton("Cancel");
		this.cancelBtn.setActionCommand("cancel");
		this.saveBtn = new JButton("Save");
		this.saveBtn.setActionCommand("save");
		
		this.initPIdBox();
		
		this.setComponents();
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.add(Box.createRigidArea(new Dimension(10,10)));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new FlowLayout());
		idPanel.add(this.idLabel);
		panel.add(idPanel);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		namePanel.add(new JLabel("Name: "));
		namePanel.add(this.nameField);
		panel.add(namePanel);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		JPanel connPanel = new JPanel();
		connPanel.setLayout(new BoxLayout(connPanel, BoxLayout.X_AXIS));
		connPanel.add(new JLabel("Connections: "));
		connPanel.add(this.cTypeBox);
		panel.add(connPanel);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		JPanel pPanel = new JPanel();
		
		if(this.pIdBox.isVisible()) {
			pPanel.setLayout(new BoxLayout(pPanel, BoxLayout.X_AXIS));
			pPanel.add(new JLabel("Parent Node: "));
			pPanel.add(this.pIdBox);
			panel.add(pPanel);
			panel.add(Box.createRigidArea(new Dimension(10,10)));
			if(!this.node.isNewNode()) {
				this.pIdBox.setSelectedItem(this.nMap.get(this.node.getParentIdStr()).toString());
			}
		}
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout());
		btnPanel.add(this.saveBtn);
		btnPanel.add(this.cancelBtn);
		panel.add(btnPanel);
		panel.add(Box.createRigidArea(new Dimension(10,10)));
		
		mainPanel.add(panel);
		mainPanel.add(Box.createRigidArea(new Dimension(10,10)));
		
		this.add(mainPanel, BorderLayout.CENTER);
		
		this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
	}
	
	/**
	 * A method that is used to set the GUI components values for Editing of an NVNode.
	 */
	private void setComponents() {
		this.idLabel.setText("Node ID: " + this.node.getIdStr());
		this.nameField.setText(this.node.getName());
		this.cTypeBox.setSelectedItem(this.connTypeToString(this.node.getConnType()));
		if(this.node.getId() > 10000) {
			this.pIdBox.setSelectedItem(this.node.getParentIdStr());
			this.cancelBtn.setVisible(true);
		}
		else {
			this.pIdBox.setVisible(false);
		}
	}
	
	/**
	 * A method that sets the values for the Parent id JComboBox.
	 */
	private void initPIdBox() {
		if(this.node.getId() > 10000) {
			String[] s = this.getNMapArray();
			this.pIdBox = new JComboBox<>(s);
		}
		else
			this.pIdBox = new JComboBox<>();
	}
	
	/**
	 * A method that is used to get the String value of an NVConnType.
	 * @param ct An NVConnType Enum Value
	 * @return A String value
	 */
	public String connTypeToString(NVConnType ct) {
		if(ct == NVConnType.DIRECT)
			return "Direct";
		else if(ct == NVConnType.SINGLE)
			return "Single";
		else if(ct == NVConnType.ONE_PER)
			return "One Per";
		else
			return "";
	}
	
	/**
	 * A method that is used to get the NVConnType from a String value.
	 * @param s A String value
	 * @return An NVConnType Enum Value
	 */
	public NVConnType stringToConnType(String s) {
		if(s.equals("Direct"))
			return NVConnType.DIRECT;
		else if(s.equals("Single"))
			return NVConnType.SINGLE;
		else if(s.equals("One Per"))
			return NVConnType.ONE_PER;
		else
			return null;
	}
	
	/**
	 * A method that returns an Array of Strings to be used in the Parent id JComboBox.
	 * @return An Array of Strings
	 */
	private String[] getNMapArray() {
		ArrayList<String> strList = new ArrayList<>();
		for(Map.Entry<String, NVNode> mapElement : this.nMap.entrySet()) {
			NVNode node = (NVNode)mapElement.getValue();
			if(node.getId() != this.node.getId())
				strList.add(node.toString());
		}
		String[] s = new String[strList.size()];
		for(int i = 0; i < strList.size(); i++)
			s[i] = strList.get(i);
		return s;
	}
	
	/**
	 * A getter method for the node variable
	 * @return An NVNode Object
	 */
	public NVNode getNode() {
		return this.node;
	}
	
	/**
	 * A setter method for the node variable
	 * @param node An NVNode Object
	 */
	public void setNode(NVNode node) {
		this.node = node;
	}
	
	/**
	 * A method that checks if an edited infoFrame was changed.
	 * @return A boolean value
	 */
	public boolean wasChanged() {
		if(!node.isRoot()) {
			if(!this.node.getName().equals(this.nameField.getText()))
				return true;
			else if(this.node.getConnType() != this.getConnType())
				return true;
			else if(this.node.getParentId() != NVNode.idFromString((String)this.pIdBox.getSelectedItem()))
				return true;
			else
				return false;
		}
		else {
			if(!this.node.getName().equals(this.nameField.getText()))
				return true;
			else if(this.node.getConnType() != this.getConnType())
				return true;
			else 
				return false;
		}
	}
	
	/**
	 * A method to set the values of the GUI components to the node variable.
	 */
	public void setValuesToNode() {
		this.node.setName(this.nameField.getText());
		this.node.setConnType(this.getConnType());
		if(this.node.getId() > 10000)
			this.node.setParentId(this.getParentId());
	}
	
	/**
	 * A getter method for the nmap variable.
	 * @return A HashMap Object
	 */
	public HashMap<String, NVNode> getNMap(){
		return this.nMap;
	}
	
	/**
	 * A setter method for the nmap variable.
	 * @param nMap A HashMap Object
	 */
	public void setNMap(HashMap<String, NVNode> nMap) {
		this.nMap = nMap;
		this.initPIdBox();
	}
	
	/**
	 * A getter method for the name variable.
	 */
	public String getName() {
		return this.nameField.getText();
	}
	
	/**
	 * A getter method for the selected NVConnType in the JComboBox.
	 * @return An NVConnType Enum Value
	 */
	public NVConnType getConnType() {
		return this.stringToConnType((String) this.cTypeBox.getSelectedItem());
	}
	
	/**
	 * A getter method for the saveBtn variable.
	 * @return A JButton Object
	 */
	public JButton getSaveBtn() {
		return this.saveBtn;
	}
	
	/**
	 * A getter method for the cancelBtn variable.
	 * @return A JButton Object
	 */
	public JButton getCancelBtn() {
		return this.cancelBtn;
	}
	
	/**
	 * A getter method for the selected parent id in the JComboBox.
	 * @return An integer value
	 */
	public int getParentId() {
		String s = (String) this.pIdBox.getSelectedItem();
		return NVNode.idFromString(s);
	}
	
	/**
	 * A method that returns whether the node is a new node or a node that is being edited.
	 * @return A boolean value
	 */
	public boolean isNewNode() {
		return this.node.isNewNode();
	}
	
}
