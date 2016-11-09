package com.github.jannled.organizer.window;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.github.jannled.lib.Print;
import com.github.jannled.lib.datastorage.StorageKey;
import com.github.jannled.organizer.Main;
import com.github.jannled.window.Textfield;

public class ItemAdder extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 8861026527796473837L;
	
	StorageKey category;
	JPanel panelName = new JPanel();
	JPanel panelProperties = new JPanel();
	ItemPropertie[] properties = new ItemPropertie[0];
	Textfield txtName = new Textfield(ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("ItemAdder.txtName.defaultText"));
	JButton btnAddPropertie = new JButton(ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("ItemAdder.btnAddPropertie.text"));
	JButton btnConfirm = new JButton(ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("ItemAdder.btnConfirm.text"));
	
	public ItemAdder(StorageKey category)
	{
		this.category = category;
		setup();
	}
	
	private void setup()
	{
		try
		{
			Image yes = ImageIO.read(Main.class.getResourceAsStream("/assets/yes.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			btnConfirm.setIcon(new ImageIcon(yes.getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		setLayout(new BorderLayout(0, 0));
		panelName.setLayout(new BorderLayout(0, 0));
		panelProperties.setLayout(new GridLayout(0, 1));
		panelName.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("ItemAdder.panelName.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelProperties.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("ItemAdder.panelProperties.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelName.add(txtName, BorderLayout.CENTER);
		btnAddPropertie.addActionListener(this);
		btnConfirm.addActionListener(this);
		add(panelName, BorderLayout.NORTH);
		add(panelProperties, BorderLayout.CENTER);
		addPropertie();
	}
	
	public void addPropertie()
	{
		panelProperties.remove(btnAddPropertie);
		panelProperties.remove(btnConfirm);
		properties = incrementArray(properties, 1);
		properties[properties.length-1] = new ItemPropertie();
		panelProperties.add(properties[properties.length-1]);
		panelProperties.add(btnAddPropertie);
		panelProperties.add(btnConfirm);
		panelProperties.revalidate();
	}
	
	public static ItemPropertie[] incrementArray(ItemPropertie[] array, int amount)
	{
		ItemPropertie[] buffer = new ItemPropertie[array.length+amount];
		for(int i=0; i<array.length; i++)
		{
			if(i<array.length)
			{
				buffer[i] = array[i];
			}
		}
		return buffer;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		//Add Propertie
		if(e.getSource() == btnAddPropertie)
		{
			addPropertie();
		}
		//Confirm
		else if(e.getSource() == btnConfirm)
		{
			Print.m("Adding new Item");
		}
	}
	
	public ItemPropertie[] getItemProperties()
	{
		return properties;
	}
}
