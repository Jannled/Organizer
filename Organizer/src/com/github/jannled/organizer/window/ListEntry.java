package com.github.jannled.organizer.window;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class ListEntry extends JPanel
{
	private static final long serialVersionUID = 6234778588489197751L;
	
	private String name;
	private JPanel panel = new JPanel(new GridLayout(0, 1));
	private JScrollPane scrollBar;
	ItemAdder itemAdder;
	
	private Vector<Entry> entrys = new Vector<Entry>();
	
	public ListEntry(Entry[] entrys, String name, ItemAdder itemAdder)
	{	
		this.name = name;
		this.itemAdder = itemAdder;
		
		setup();
		for(Entry e : entrys)
		{
			this.entrys.add(e);
			panel.add(e);
		}
	}
	
	public ListEntry(String name, ItemAdder itemAdder)
	{
		this.name = name;
		this.itemAdder = itemAdder;
		
		setup();
	}
	
	private void setup()
	{
		setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(itemAdder);
		add(panel, BorderLayout.CENTER);
		scrollBar = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollBar.getHorizontalScrollBar().setUnitIncrement(20);
		scrollBar.getVerticalScrollBar().setUnitIncrement(20);
		add(scrollBar);
	}
	
	public void addEntry(Entry entry)
	{
		entrys.add(entry);
		panel.add(entry);
	}
	
	public void removeEntry(Entry entry)
	{
		entrys.remove(entry);
		remove(entry);
	}
	
	public Entry[] getEntrys()
	{
		return (Entry[]) entrys.toArray();
	}
	
	@Override
	public String getName()
	{
		return name;
	}
}
