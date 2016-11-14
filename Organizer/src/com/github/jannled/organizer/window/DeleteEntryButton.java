package com.github.jannled.organizer.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.github.jannled.window.DropdownButton;

public class DeleteEntryButton extends JPanel implements MouseListener
{
	private static final long serialVersionUID = -6412205158850007589L;
	
	public static BufferedImage deleteIcon = loadImage();
	
	Color defaultC = UIManager.getColor("Panel.background");
	Color hoveredC = new Color(0.8F, 0.8F, 1.0F);
	Color clickedC = new Color(0.6F, 0.6F, 1.0F);
	
	int width;
	int height;
	Entry item;
	
	public DeleteEntryButton(Entry e)
	{
		this.item = e;
		width = 20;
		height = 20;
		
		setup();
	}
	
	public DeleteEntryButton(Entry e, int width, int height)
	{
		this.item = e;
		this.width = width;
		this.height = height;
		
		setup();
	}
	
	public void setup()
	{
		this.addMouseListener(this);
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		setBackground(hoveredC);
		item.selfDelete();
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
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(deleteIcon, 0, 0, width, height, 0, 0, 64, 64, null);
	}

	private static final BufferedImage loadImage()
	{
		BufferedImage img = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
		try
		{
			img = ImageIO.read(DropdownButton.class.getResourceAsStream("/assets/x.png"));
		} catch (IOException | NullPointerException e)
		{
			e.printStackTrace();
			return null;
		}
		return img;
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(width, height);
	}
}
