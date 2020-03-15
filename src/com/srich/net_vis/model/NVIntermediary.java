package com.srich.net_vis.model;

import java.io.Serializable;

/**
 * Filename: NVIntermediary.java <p>
 * Date: 01 Nov 2019 <p>
 * 
 * Purpose: This class is used to create an intermediary that can exist between
 * different nodes depending on there NVConnType. <p>
 * 
 * @author srichs <p>
 */
public class NVIntermediary implements Serializable {
	
	//NVIntermediary Class Variables
	private static final long serialVersionUID = 1L;
	/**
	 * An integer value to store the id of the intermediary.
	 */
	private int id;
	
	/**
	 * Constructor for the NVIntermediary Class.
	 */
	public NVIntermediary() {
		this.id = 0;
	}
	
	/**
	 * Constructor for the NVIntermediary Class.
	 * @param id An integer value
	 */
	public NVIntermediary(int id) {
		this.id = id;
	}
	
	/**
	 * A getter method for the id variable.
	 * @return An integer value
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * A method that returns the id variable as a String.
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
	 * A method that returns the style of the intermediary node for use in the GraphViz Dot notation.
	 * @return A String value
	 */
	public String intGVStyle() {
		String str = "    node [shape=box, label=\"\", xlabel=\"\", style=filled, fillcolor=lightblue] " + this.getIdStr() + ";\n";
		return str;
	}

}
