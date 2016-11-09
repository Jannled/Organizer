package com.github.jannled.organizer.window;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.github.jannled.lib.Print;
import com.github.jannled.organizer.Main;

public class CategoryRemover extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 4339829971814622602L;
	WindowManager window;
	ListEntry category;
	JButton delete;
	
	public CategoryRemover(WindowManager window, ListEntry category)
	{
		this.category = category;
		this.window = window;
		setup();
	}
	
	private void setup()
	{
		delete = new JButton(category.getName());
		delete.addActionListener(this);
		add(delete);
		try
		{
			Image deleteIcon= ImageIO.read(Main.class.getResourceAsStream("/assets/x.png"));
			delete.setIcon(new ImageIcon(deleteIcon.getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public ListEntry getCategory()
	{
		return category;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == delete)
		{
			Print.m("Removing Category " + category.getName() + "!");
			window.removeCategory(category);
			getParent().remove(this);
		}
	}
}
