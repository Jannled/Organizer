package com.github.jannled.organizer.window;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.jannled.organizer.Main;
import com.github.jannled.window.Textfield;

public class ItemPropertie extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 2312896473509851231L;
	
	ItemAdder parent;
	Textfield key;
	Textfield value;
	JButton remove = new JButton();
	ImageIcon icon;
	JPanel panelProperties = new JPanel(new GridLayout(1, 2));

	public ItemPropertie(String key, String value, ItemAdder parent)
	{
		this.key = new Textfield();
		this.value = new Textfield(value);
		this.parent = parent;
		setup();
	}
	
	public ItemPropertie(ItemAdder parent)
	{
		this.key = new Textfield(ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("ItemPropertie.key.defaultText"));
		this.value = new Textfield(ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("ItemPropertie.value.defaultText"));
		this.parent = parent;
		setup();
	}
	
	private void setup()
	{
		setLayout(new BorderLayout());
		remove.addActionListener(this);
		try
		{
			icon = new ImageIcon(ImageIO.read(Main.class.getResourceAsStream("/assets/x.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH));
			remove.setIcon(icon);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		panelProperties.add(key);
		panelProperties.add(value);
		add(panelProperties, BorderLayout.CENTER);
		add(remove, BorderLayout.EAST);
	}

	public JTextField[] getPropertie()
	{
		return new JTextField[] {key, value};
	}
	
	public boolean checkRemovable()
	{
		if(getParent() instanceof ItemAdder)
		{
			ItemAdder itemAdder = (ItemAdder) getParent();
			if(itemAdder.getItemProperties().length > 1)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == remove)
		{
			parent.removePropertie(this);
			parent.panelProperties.remove(this);
			parent.revalidate();
		}
	}
}
