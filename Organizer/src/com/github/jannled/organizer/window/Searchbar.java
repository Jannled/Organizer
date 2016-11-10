package com.github.jannled.organizer.window;

import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import com.github.jannled.window.Textfield;

public class Searchbar extends JPanel
{
	private static final long serialVersionUID = -3407714876456251671L;
	
	private Textfield searchField;
	
	public Searchbar()
	{
		setLayout(new GridLayout(0, 1));
		searchField = new Textfield(ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("Searchbar.searchField.defaultText"));
		add(searchField);
	}
	
	public void update()
	{
		
	}
}
