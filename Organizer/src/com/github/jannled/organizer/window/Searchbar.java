package com.github.jannled.organizer.window;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import com.github.jannled.window.Textfield;

public class Searchbar extends JPanel implements KeyListener
{
	private static final long serialVersionUID = -3407714876456251671L;
	
	private WindowManager windowManager;
	
	private Textfield searchField;
	
	public Searchbar(WindowManager windowManager)
	{
		this.windowManager = windowManager;
		setLayout(new GridLayout(0, 1));
		searchField = new Textfield(ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("Searchbar.searchField.defaultText"));
		searchField.addKeyListener(this);
		add(searchField);
	}
	
	public void update()
	{
		windowManager.setActiveEntryList();
		ListEntry activeEntry = windowManager.getActiveEntryList();
		if(activeEntry!=null)
		{
			activeEntry.search(searchField.getText());
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		update();
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		
	}
}
