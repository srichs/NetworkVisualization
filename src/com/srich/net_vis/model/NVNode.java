package com.srich.net_vis.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Filename: NVNode.java <p>
 * Date: 01 Nov 2019 <p>
 * 
 * Purpose: This class is used to create a node object that can be used
 * to build a network and then can be visualized using GraphViz. <p>
 * 
 * @author srichs <p>
 */
public class NVNode implements Serializable {

	/**
	 * An enum declaration for the connection type. DIRECT - No intermediaries, 
	 * SINGLE - One intermediary for all subordinate connections, 
	 * ONE_PER - One intermediary per subordinate connection.
	 */
	public enum NVConnType {
		DIRECT, SINGLE, ONE_PER;
	}
	
	//NVNode Class Variables
	private static final long serialVersionUID = 1L;
	/**
	 * An integer value to store the id of the node.
	 */
	private int id;
	/**
	 * A String value to store the name of the node.
	 */
	private String name;
	/**
	 * An NVConnType enum value to store the connection type to subordinate connections.
	 */
	private NVConnType connType;
	/**
	 * A boolean value that stores whether the node is new.
	 */
	private boolean newNode;
	/**
	 * An integer value to store the node's parent's id.
	 */
	private int parentId;
	/**
	 * An ArrayList of NVNodes to store the subordinate connected nodes.
	 */
	private ArrayList<NVNode> connections;
	/**
	 * An ArrayList of NVIntermediarys to store the intermediaries of the node.
	 */
	private ArrayList<NVIntermediary> intermediaries;
	
	/**
	 * Constructor for the NVNode Class.
	 */
	public NVNode() {
		this.id = 0;
		this.name = "";
		this.connType = null;
		this.newNode = true;
		this.parentId = 0;
		this.connections = new ArrayList<>();
		this.intermediaries = new ArrayList<>();
	}
	
	/**
	 * A getter method for the id variable.
	 * @return An integer value
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * A getter method for the id variable as a String.
	 * @return A String value
	 */
	public String getIdStr() {
		return String.valueOf(this.id);
	}
	
	/**
	 * A setter method for the id variable.
	 * @param id An integer value
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * A getter method for the name variable.
	 * @return A String value
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * A setter method for the name variable.
	 * @param name A String value
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * A getter method for abbreviated for of the name variable.
	 * @return A String value
	 */
	public String getAbbr() {
		String[] s = this.name.split(" ");
		String str = "";
		for(int i = 0; i < (s.length); i++){
			str = str + s[i].charAt(0);
		}
		return str;
	}
	
	/**
	 * A getter method for the connType variable.
	 * @return An NVConnType enum value
	 */
	public NVConnType getConnType() {
		return this.connType;
	}
	
	/**
	 * A setter method for the connType variable.
	 * @param NVConnType An NVConnType enum value
	 */
	public void setConnType(NVConnType NVConnType) {
		this.connType = NVConnType;
	}
	
	/**
	 * A getter for the connType as a String value.
	 * @return A String value
	 */
	public String getConnStr() {
		if(this.connType == NVConnType.DIRECT)
			return "Direct";
		else if(this.connType == NVConnType.SINGLE)
			return "Single";
		else
			return "One Per";
	}
	
	/**
	 * A method that returns if the node is new.
	 * @return A boolean value
	 */
	public boolean isNewNode() {
		return this.newNode;
	}
	
	/**
	 * A setter method for the newNode variable.
	 * @param newNode A boolean value
	 */
	public void setNewNode(boolean newNode) {
		this.newNode = newNode;
	}
	
	/**
	 * A getter method for the parentId.
	 */
	public int getParentId() {
		return this.parentId;
	}
	
	/**
	 * A getter method for the parentId as a String value.
	 * @return A String value
	 */
	public String getParentIdStr() {
		return String.valueOf(this.parentId);
	}
	
	/**
	 * A setter method for the parentId variable.
	 * @param id An integer value
	 */
	public void setParentId(int id) {
		this.parentId = id;
	}
	
	/**
	 * A method that returns whether the node is the root node of a network.
	 * @return A boolean value
	 */
	public boolean isRoot() {
		if(this.parentId == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * A getter method for the connections ArrayList Object.
	 * @return An ArrayList Object
	 */
	public ArrayList<NVNode> getConnections(){
		return this.connections;
	}
	
	/**
	 * A setter method for the connections ArrayList Object.
	 * @param connections An ArrayList Object
	 */
	public void setConnections(ArrayList<NVNode> connections) {
		this.connections = connections;
	}
	
	/**
	 * A method to add a connection to the connections ArrayList.
	 * @param node An NVNode Object
	 */
	public void addConnections(NVNode node) {
		this.connections.add(node);
	}
	
	/**
	 * A method to remove a connection from the connections ArrayList.
	 * @param node An NVNode Object
	 */
	public void removeConnection(NVNode node) {
		this.connections.remove(node);
	}
	
	/**
	 * A method to remove all connections from the connections ArrayList.
	 */
	public void clearConnections() {
		this.connections.clear();
	}
	
	/**
	 * A method to get the number of subordinate connections.
	 * @return An integer value
	 */
	public int getNumberOfConnections() {
		return this.connections.size();
	}
	
	/**
	 * A method to get the ArrayList of intermediaries.
	 * @return An ArryList Object
	 */
	public ArrayList<NVIntermediary> getIntermediaries(){
		return this.intermediaries;
	}
	
	/**
	 * A method to set the intermediaries ArrayList.
	 * @param intermediaries An ArrayList Object
	 */
	public void setIntermediaries(ArrayList<NVIntermediary> intermediaries) {
		this.intermediaries = intermediaries;
	}
	
	/**
	 * A method to add an intermediary to the intermediaries ArrayList.
	 * @param intermed An NVIntermediary Object
	 */
	public void addIntermediaries(NVIntermediary intermed) {
		this.intermediaries.add(intermed);
	}
	
	/**
	 * A method to remove an intermediary from the intermediaries ArrayList.
	 * @param intermed An NVIntermediary Object
	 */
	public void removeIntermediary(NVIntermediary intermed) {
		this.intermediaries.remove(intermed);
	}
	
	/**
	 * A method to remove all intermediaries from the ArrayList.
	 */
	public void clearIntermediaries() {
		this.intermediaries.clear();
	}
	
	/**
	 * A method to get the number of intermediaries from the ArrayList.
	 * @return An integer value
	 */
	public int getNumberOfIntermediaries() {
		return this.intermediaries.size();
	}
	
	/**
	 * A method that returns whether the node is a terminal (no connections).
	 * @return A boolean value
	 */
	public boolean isTerminal() {
		if(this.getNumberOfConnections() == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * A recursive method to add a node to a network.
	 * @param node An NVNode Object
	 */
	public void addNode(NVNode node) {
		if(this.id == node.getParentId()) {
			this.connections.add(node);
		}
		else {
			if(!this.isTerminal()) {
				for(NVNode nvNode: this.connections)
					nvNode.addNode(node);
			}
		}
	}
	
	/**
	 * A recursive method to add a node to a network.
	 * @param node An NVNode Object
	 * @param intCount An integer value
	 */
	public void addNode(NVNode node, int intCount) {
		if(this.id == node.getParentId()) {
			this.intermediaries.add(new NVIntermediary(intCount));
			intCount++;
			this.connections.add(node);
		}
		else {
			if(!this.isTerminal()) {
				for(NVNode nvNode: this.connections)
					nvNode.addNode(node, intCount);
			}
		}
	}
	
	/**
	 * A recursive method to remove a node from a network.
	 * @param node An NVNode Object
	 */
	public void removeNode(NVNode node) {
		if(this.connections.contains(node)) {
			this.connections.remove(node);
			this.intermediaries.remove(this.intermediaries.size() - 1);
		}
		else {
			if(!this.isTerminal()) {
				for(NVNode nvNode: this.connections)
					nvNode.removeNode(node);
			}
		}
	}
	
	/**
	 * A recursive method to find the node and get the list of its children nodes.
	 * @param node An NVNode Object to find the children of 
	 * @param list An ArrayList of NVNodes to hold the children nodes
	 * @return An ArrayList of NVNodes of the children of the node
	 */
	public ArrayList<NVNode> getChildNodes(NVNode node, ArrayList<NVNode> list) {
		if(this.id != node.getId()) {
			for(NVNode nvNode: this.connections)
				list = nvNode.getChildNodes(node, list);
		}
		else if(this.id == node.getId()) {
			list = this.getAllChildren(list);
		}
		return list;
	}
	
	/**
	 * A recursive method to find the node and get the list of its children nodes.
	 * @param list An ArrayList of NVNodes to hold the children nodes
	 * @return An ArrayList of NVNodes of the children of the node
	 */
	public ArrayList<NVNode> getAllChildren(ArrayList<NVNode> list) {
		list.add(this);
		if(!this.isTerminal()) {
			for(NVNode nvNode: this.connections)
				list = nvNode.getAllChildren(list);
		}
		return list;
	}
	
	/**
	 * A recursive method to remove a node from a network by id.
	 * @param id An integer value
	 */
	public void removeWithId(int id) {
		if(!this.connections.isEmpty()) {
			for(NVNode nvNode: this.connections) {
				if(nvNode.getId() == id) {
					this.connections.remove(nvNode);
					this.intermediaries.remove(this.intermediaries.size() - 1);
					break;
				}
			}
		}
	}
	
	/**
	 * A recursive method that is used to construct a JTree of a network.
	 * @return A DefaultMutableTreeNode Object
	 */
	public DefaultMutableTreeNode toTree() {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(this);
		if(!this.isTerminal()) {
			for(NVNode nvNode: this.connections)
				node.add(nvNode.toTree());
		}
		return node;
	}
	
	/**
	 * A method to remove all intermediaries from the ArrayList.
	 */
	public void clearInts() {
		this.intermediaries.clear();
		for(NVNode node: this.connections)
			node.clearInts();
	}
	
	/**
	 * A recursive method to create a String with the GraphViz Dot notation representation of a network.
	 * @return A String value
	 */
	public String nodeToGVStr() {
		String str = "";
		if(!this.isTerminal()) {
			if(this.connType == NVConnType.DIRECT) {
				for(NVNode node: this.connections) 
					str = str + "    " + this.getIdStr() + "->" + node.getIdStr() + ";\n";
			}
			else if(this.connType == NVConnType.SINGLE) {
				str = str + "    " + this.getIdStr() + "->" + this.intermediaries.get(0).getIdStr() + ";\n";
				for(NVNode node: this.connections) 
					str = str + "    " + this.intermediaries.get(0).getIdStr() + "->" + node.getIdStr() + ";\n";
			}
			else if(this.connType == NVConnType.ONE_PER) {
				for(int i = 0; i < this.intermediaries.size(); i++) {
					str = str + "    " + this.getIdStr() + "->" + this.intermediaries.get(i).getIdStr() + ";\n";
					str = str + "    " + this.intermediaries.get(i).getIdStr() + "->" + this.connections.get(i).getIdStr() + ";\n";
				}
			}
			for(NVNode node: this.connections)
				str = str + node.nodeToGVStr();
		}
		return str;
	}
	
	/**
	 * A recursive method to get a String with the GraphViz Dot notation node styles for a network.
	 * This method creates styling for each intermediary.
	 * @return A String value
	 */
	public String nodeGVStyle() {
		String str = "    node [shape=circle, style=filled, fillcolor=orange, label=\"\", xlabel=\"" + this.getAbbr() + "\"] " + this.getIdStr() + ";\n";
		if(!this.isTerminal()) {
			if(this.connType == NVConnType.SINGLE)
				str = str + this.intermediaries.get(0).intGVStyle();
			else if(this.connType == NVConnType.ONE_PER) {
				for(NVIntermediary intermed: this.intermediaries) 
					str = str + intermed.intGVStyle();
			}
			for(NVNode node: this.connections)
				str = str + node.nodeGVStyle();
		}
		return str;
	}
	
	/**
	 * A recursive method to get a String with the GraphViz Dot notation node styles for a network.
     * @return A String value
	 */
	public String nodeGVStyleTwo() {
		String str = "    node [shape=circle, style=filled, fillcolor=orange, label=\"\", xlabel=\"" + this.getAbbr() + "\"] " + this.getIdStr() + ";\n";
		if(!this.isTerminal()) {
			for(NVNode node: this.connections)
				str = str + node.nodeGVStyleTwo();
		}
		return str;
	}
	
	/**
     * A recursive method to get a String with the GraphViz Dot notation node styles for a network.
     * @return A String value
     */
    public String nodeGVStyleThree() {
        String str = "    node [shape=circle, style=filled, fillcolor=orange, label=\"\", xlabel=\"" + this.getIdStr() + "\"] " + this.getIdStr() + ";\n";
        if(!this.isTerminal()) {
            for(NVNode node: this.connections)
                str = str + node.nodeGVStyleThree();
        }
        return str;
    }
    
    /**
     * A recursive method to get a String with a csv list of the nodes for a network.
     * @return A String value in csv format
     */
    public String nodeToList() {
        String str = this.getIdStr() + ", " + this.getAbbr() + ", " + this.getName() + "\n";
        if(!this.isTerminal()) {
            for(NVNode node: this.connections)
                str = str + node.nodeToList();
        }
        return str;
    }
	
	/**
	 * A toString method for the NVNode Class. Format: 'id - name'.
	 */
	public String toString() {
		return this.getIdStr() + " - " + this.name;
	}
	
	/**
	 * A static method that is used to convert a node's toString value to its id
	 * @param str A String value
	 * @return An integer value
	 */
	public static int idFromString(String str) {
		String[] s = str.split(" ");
		return Integer.valueOf(s[0]);
	}

	/**
	 * A compareTo method that is used to compare two nodes when sorting the HashMap in the NVNetwork Class.
	 * @param node An NVNode Object
	 * @return An integer value
	 */
	public int compareTo(NVNode node) {
		if(this.id < node.id)
			return -1;
		else if(this.id == node.id)
			return 0;
		else
			return 1;
	}
	
}
