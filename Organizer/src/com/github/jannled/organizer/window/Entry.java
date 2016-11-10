package com.github.jannled.organizer.window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.github.jannled.lib.datastorage.StorageKey;

public class Entry extends JPanel
{
	private static final long serialVersionUID = 2943419749871064512L;

	StorageKey storageKey;
	String[][] information;
	
	public Entry(StorageKey storageKey)
	{
		this.storageKey = storageKey;
		StorageKey[] storageKeys = storageKey.getKeysArray();
		information = new String[storageKeys.length][2];
		for(int i=0; i<storageKeys.length; i++)
		{
			information[i][0] = storageKeys[i].getName();
			information[i][1] = storageKeys[i].getValue();
		}
		setupComponents();
	}
	
	public void setupComponents()
	{
		String defaultF = UIManager.getDefaults().getFont("TextField.font").getName();
		
		this.setLayout(null);
		
		JTextField name = new JTextField(storageKey.getName());
		name.setFont(new Font(defaultF, Font.PLAIN, 15));
		name.setEditable(false);
		name.setBounds(0, 0, 600, 20);
		
		JTextArea description = new JTextArea(storageKey.getValue());
		description.setLineWrap(true);
		description.setBackground(UIManager.getColor("Panel.background"));
		description.setFont(new Font(defaultF, Font.PLAIN, 10));
		description.setBounds(0, 20, 600, 300);
		description.setEditable(false);
		
		this.add(name);
		this.add(description);
		
		mouseEvents(this);
		mouseEvents(name);
		mouseEvents(description);
	}
	
	public void mouseEvents(Component target)
	{
		Color defaultC = UIManager.getColor("Panel.background");
		Color hoveredC = new Color(0.8F, 0.8F, 1.0F);
		Color clickedC = new Color(0.6F, 0.6F, 1.0F);
		
		target.addMouseListener(new MouseListener() 
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				setBackground(hoveredC);
				clicked();
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				setBackground(clickedC);
			}
			
			@Override
			public void mouseExited(MouseEvent e)
			{
				setBackground(defaultC);
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
				setBackground(hoveredC);
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				
			}
		});
	}
	
	/**
	 * Method gets called whenever this entry is clicked
	 */
	public void clicked()
	{
		
	}
	
	public Dimension getPreferredSize() 
	{
		return new Dimension(260, 60);
	}
}
