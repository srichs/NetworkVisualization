package com.srich.net_vis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Filename: NVNetwork.java <p>
 * Date: 01 Nov 2019 <p>
 * 
 * Purpose: This class is used to build a graph of the network of nodes. <p>
 * 
 * @author srichs <p>
 */
public class NVNetwork implements Serializable {
	
	//NVNetwork Class Variables
	private static final long serialVersionUID = 1L;
	/**
	 * An NVNode Object to store the root node of the network.
	 */
	private NVNode rootNode;
	/**
	 * A HashMap that stores all of the nodes in the network with the id's as keys.
	 */
	private HashMap<String, NVNode> networkMap;
	/**
	 * An integer value that stores the count for id's so that each node can be given a unique identifier.
	 */
	private int idCount;
	/**
	 * An integer value that stores the count for intermediary id's so that each intermediary can 
	 * be given a unique identifier.
	 */
	private int intCount;
	
	/**
	 * Constructor for the NVNetwork Class.
	 */
	public NVNetwork() {
		this.rootNode = null;
		this.networkMap = new HashMap<>();
		this.idCount = 10000;
		this.intCount = 50000;
	}
	
	/**
	 * A getter method for the rootNode variable.
	 * @return An NVNode Object
	 */
	public NVNode getRootNode() {
		return this.rootNode;
	}
	
	/**
	 * A setter method for the rootNode variable.
	 * @param node An NVNode Object
	 */
	public void setRootNode(NVNode node) {
		this.rootNode = node;
		this.networkMap.put(node.getIdStr(), node);
	}
	
	/**
	 * A getter method for the networkMap variable.
	 * @return A HashMap Object
	 */
	public HashMap<String, NVNode> getNetworkMap(){
		return this.networkMap;
	}
	
	/**
	 * A setter method for the networkMap variable.
	 * @param netMap A HashMap Object
	 */
	public void setNetworkMap(HashMap<String, NVNode> netMap) {
		this.networkMap = netMap;
	}
	
	/**
	 * A getter method to return the value of the idCount variable and increment it.
	 * @return An integer value
	 */
	public int getIdCount() {
		int i = this.idCount;
		this.increment();
		return i;
	}
	
	/**
	 * A setter method for the idCount variable.
	 * @param idCount An integer value
	 */
	public void setIdCount(int idCount) {
		this.idCount = idCount;
	}
	
	/**
	 * A method to increment the idCount integer by one.
	 */
	public void increment() {
		this.idCount++;
	}
	
	/**
	 * A getter method for the intCount variable.
	 * @return An integer value
	 */
	public int getIntCount() {
		return this.intCount;
	}
	
	/**
	 * A method that is used to call a recursive addNode method in the NVNode Class.
	 * @param node An NVNode Object
	 */
	public void addNode(NVNode node) {
		this.rootNode.addNode(node, this.intCount);
		this.intCount++;
		this.networkMap.put(node.getIdStr(), node);
		this.sortByValue();
	}
	
	/**
	 * A method that is used to call a recursive editNode method in the NVNode Class.
	 * @param node An NVNode Object
	 */
	public void editNode(NVNode node) {
		this.rootNode.removeWithId(node.getId());
		this.addNode(node);
		this.networkMap.replace(node.getIdStr(), node);
		this.sortByValue();
	}
	
	/**
	 * A method that is used to call a recursive removeNode method in the NVNode Class.
	 * @param node An NVNode Object
	 */
	public void removeNode(NVNode node) {
		ArrayList<NVNode> list = new ArrayList<>();
		list = this.getChildNodes(node);
		for(NVNode nvNode: list) {
			this.networkMap.remove(nvNode.getIdStr());
		}
		this.rootNode.removeNode(node);
	}
	
	/**
	 * A method that is used to get a list of all the child nodes of a node.
	 * @param node An NVNode Object to find the children of
	 * @return An ArrayList of NVNodes that are children of the node
	 */
	public ArrayList<NVNode> getChildNodes(NVNode node){
		ArrayList<NVNode> list = new ArrayList<>();
		list = this.rootNode.getChildNodes(node, list);
		return list;
	}
	
	/**
	 * A method that is used to get a node by its id
	 * @param id An integer value 
	 * @return An NVNode Object
	 */
	public NVNode getNodeById(int id) {
		return this.networkMap.get(String.valueOf(id));
	}
	
	/**
	 * A method that is used to clear the intermediaries from the network.
	 */
	public void clearInts() {
		this.rootNode.clearInts();
	}
	
	/**
	 * Creates a list of each node in the graph.
	 * @return a String containing a list of each node
	 */
	public String networkToList() {
        return this.getRootNode().nodeToList();
    }
	
	/**
	 * A method that is used to setup the styles of the nodes and edges of the graph
	 * in GraphViz Dot Notation. Sets the main nodes as orange circles and the intermediaries
	 * as light blue rectangles.
	 * @return A String of the styles of the network's nodes and edges in GraphViz Dot notation
	 */
	public String networkToGV() {
		String str = "digraph g {\n\n";
		str = str + this.getRootNode().nodeGVStyleTwo(); // Change Style
		str = str + "    node [shape=box, label=\"\", xlabel=\"\", style=filled, fillcolor=lightblue]\n";
		str = str + "    edge [arrowhead=none];\n\n";
		str = str + this.getRootNode().nodeToGVStr() + "\n}";
		return str;
	}
	
	/**
     * A method that is used to setup the styles of the nodes and edges of the graph
     * in GraphViz Dot Notation. Sets the main nodes as orange circles and the intermediaries
     * as light blue rectangles. This method displays the external label as the ID.
     * @return A String of the styles of the network's nodes and edges in GraphViz Dot notation
     */
	public String networkToGVID() {
        String str = "digraph g {\n\n";
        str = str + this.getRootNode().nodeGVStyleThree(); // Change Style
        str = str + "    node [shape=box, label=\"\", xlabel=\"\", style=filled, fillcolor=lightblue]\n";
        str = str + "    edge [arrowhead=none];\n\n";
        str = str + this.getRootNode().nodeToGVStr() + "\n}";
        return str;
    }
	
	/**
	 * This method is used to setup the styles of the nodes when it is desired for the main nodes 
	 * to not have external labels. Sets the main nodes as orange circles and the intermediaries
     * as light blue rectangles.
	 * @return A String of the styles of the network's nodes and edges in GraphViz Dot notation
	 */
	public String networkToGVNL() {
		String str = "digraph g {\n\n";
		str = str + "    node [shape=circle, style=filled, fillcolor=orange, label=\"\"];\n    ";
		int count = 0;
		for(Map.Entry<String, NVNode> mapElement : this.networkMap.entrySet()) {
			if(count == 12) {
				str = str + "\n    ";
				count = 0;
			}
			NVNode node = (NVNode)mapElement.getValue();
			str = str + node.getIdStr() + ";";
			count++;
		}
		str = str + "\n    node [shape=box, label=\"\", xlabel=\"\", style=filled, fillcolor=lightblue]\n";
		str = str + "    edge [arrowhead=none];\n\n";
		str = str + this.getRootNode().nodeToGVStr() + "\n}";
		return str;
	}
	
	/**
	 * A method that is used to build a JTree from the network.
	 * @return A JTree Object
	 */
	public JTree toTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(this.rootNode);
    	for (NVNode node: this.rootNode.getConnections()) {
    		root.add(node.toTree());
    	}
    	JTree tree = new JTree(root);
    	tree.setRootVisible(true);
    	tree.setShowsRootHandles(true);
    	return tree;
	}
	
	/**
	 * A method that is used to sort the values in the networkMap HashMap Object.
	 */
	public void sortByValue() { 
        List<Map.Entry<String, NVNode> > list = new LinkedList<Map.Entry<String, NVNode> >(this.networkMap.entrySet()); 
        Collections.sort(list, new Comparator<Map.Entry<String, NVNode> >() { 
			@Override
			public int compare(Entry<String, NVNode> o1, Entry<String, NVNode> o2) {
				return (o1.getValue()).compareTo(o2.getValue()); 
			} 
        });  
        HashMap<String, NVNode> temp = new LinkedHashMap<String, NVNode>(); 
        for (Map.Entry<String, NVNode> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        } 
        this.networkMap = temp;
    } 
	
}
