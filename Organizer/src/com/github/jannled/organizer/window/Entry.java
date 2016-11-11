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
import com.github.jannled.window.DropdownButton;

public class Entry extends JPanel
{
	private static final long serialVersionUID = 2943419749871064512L;

	Dimension minimized = new Dimension(600, 20);
	Dimension maximized = new Dimension(600, 100);
	
	ListEntry list;
	DropdownButton dropdown;
	StorageKey storageKey;
	String[][] information;
	JTextField name;
	JTextArea description;
	
	public Entry(ListEntry list, StorageKey storageKey)
	{
		this.storageKey = storageKey;
		StorageKey[] storageKeys = storageKey.getKeysArray();
		name = new JTextField(storageKey.getName());
		description = new JTextArea(storageKey.getValue());
		
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
		
		dropdown = new DropdownButton(20, 20);
		this.add(dropdown);
		
		name.setFont(new Font(defaultF, Font.PLAIN, 15));
		name.setEditable(false);
		name.setBounds(20, 0, 600, 20);
		
		description.setLineWrap(true);
		description.setBackground(UIManager.getColor("Panel.background"));
		description.setFont(new Font(defaultF, Font.PLAIN, 10));
		description.setBounds(20, 20, 600, 300);
		description.setEditable(false);
		setDescription();
		
		this.add(description);
		this.add(name);
		
		mouseEvents(this);
		mouseEvents(name);
		mouseEvents(description);
	}
	
	public void setDescription()
	{
		for(int i=0; i<6; i++)
		{
			if(i<information.length)
			{
				String[] s = information[i];
				description.setText(description.getText() + s[0] + ": " + s[1] + System.lineSeparator());
			}
		}
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
	
	@Override
	public void repaint()
	{
		super.repaint();
		if(dropdown!=null)
		{
			if(dropdown.getStatus())
			{
				add(description);
				setMinimumSize(maximized);
				setPreferredSize(maximized);
				setSize(maximized);
				revalidate();
			}
			else
			{
				remove(description);
				setMinimumSize(minimized);
				setPreferredSize(minimized);
				setSize(minimized);
				revalidate();
			}
		}
	}
	
	@Override
	public Dimension getPreferredSize() 
	{
		if(dropdown.getStatus())
		{
			return maximized;
		}
		else
		{
			return minimized;
		}
	}
}
