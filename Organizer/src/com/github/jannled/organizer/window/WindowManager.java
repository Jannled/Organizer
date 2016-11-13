package com.github.jannled.organizer.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.github.jannled.lib.Print;
import com.github.jannled.lib.datastorage.Datastorage;
import com.github.jannled.organizer.Main;
import com.github.jannled.organizer.Manager;
import com.github.jannled.window.TabbedPane;
import com.github.jannled.window.Textfield;

public class WindowManager
{	
	Manager manager;
	
	private JFrame frame;
	private Searchbar searchbar = new Searchbar();
	private final JPanel panel = new JPanel();
	private final JTabbedPane tabbedPane = new TabbedPane(JTabbedPane.TOP);
	
	Vector<ListEntry> entryList = new Vector<ListEntry>();
	private ListEntry activeEntryList;
	private final JPanel managerCategory = new JPanel();
	private final Textfield newCategoryName = new Textfield(ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("WindowManager.newCategoryName.defaultText"));
	private final JButton submitNewCategory = new JButton(ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("WindowManager.submitNewCategory.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JPanel newCategory = new JPanel();
	private final JToolBar toolBar = new JToolBar();
	private final JPanel panelToolbar = new JPanel();
	private final JButton btnSave = new JButton(ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("WindowManager.btnSave.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JButton btnOpen = new JButton(ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("WindowManager.btnOpen.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JPanel deleteCategory = new JPanel();

	public WindowManager(Manager manager)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try
				{
					frame = new JFrame();
					initialize();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		this.manager = manager;
	}
	
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 682, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel.add(panelToolbar, BorderLayout.NORTH);
		panelToolbar.setLayout(new BorderLayout(0, 0));
		panelToolbar.add(toolBar, BorderLayout.NORTH);
		
		//Toolbar Buttons
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				save();
			}
		});
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				load();
			}
		});
		try
		{
			Image imgSave = ImageIO.read(Main.class.getResourceAsStream("/assets/save.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			Image imgLoad = ImageIO.read(Main.class.getResourceAsStream("/assets/load.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
			btnSave.setIcon(new ImageIcon(imgSave));
			btnOpen.setIcon(new ImageIcon(imgLoad));
		} catch (IOException e)
		{
			Print.e("Error while reading file");
			e.printStackTrace();
		} catch(Exception ex)
		{
			ex.printStackTrace();
		}
		toolBar.add(btnSave);
		toolBar.add(btnOpen);
		searchbar.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("WindowManager.searchbar.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0))); //$NON-NLS-2$ //$NON-NLS-3$
		
		panelToolbar.add(searchbar, BorderLayout.SOUTH);
		tabbedPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		panel.add(tabbedPane, BorderLayout.CENTER);
		managerCategory.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		tabbedPane.addTab("+", null, managerCategory, null);
		managerCategory.setLayout(new BorderLayout(0, 0));
		managerCategory.add(newCategory, BorderLayout.NORTH);
		newCategory.setBorder(new TitledBorder(null, ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("WindowManager.newCategory.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null)); //$NON-NLS-1$ //$NON-NLS-2$
		newCategory.setLayout(new BorderLayout(0, 0));
		newCategoryName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) 
			{
				if(e.getKeyCode() == KeyEvent.VK_ENTER) addCategory();
			}
		});
		newCategory.add(newCategoryName);
		submitNewCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				addCategory();
			}
		});
		//Submit new Category Button
		submitNewCategory.setMnemonic(KeyEvent.VK_ENTER);
		newCategory.add(submitNewCategory, BorderLayout.EAST);
		
		managerCategory.add(deleteCategory, BorderLayout.CENTER);
		deleteCategory.setBorder(new TitledBorder(null, ResourceBundle.getBundle("com.github.jannled.organizer.window.messages").getString("WindowManager.deleteCategory.borderTitle"), TitledBorder.LEADING, TitledBorder.TOP, null, null)); //$NON-NLS-1$ //$NON-NLS-2$
		deleteCategory.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}

	public void addEntryList(ListEntry entryList)
	{
		this.entryList.add(entryList);
		tabbedPane.addTab(entryList.getName(), null, entryList);
		deleteCategory.add(new CategoryRemover(this, entryList));
	}
	
	public void setActiveEntryList()
	{
		if(tabbedPane.getSelectedComponent() instanceof ListEntry)
		{
			activeEntryList = (ListEntry) tabbedPane.getSelectedComponent();
		}
	}
	
	public ListEntry getActiveEntryList()
	{
		return activeEntryList;
	}
	
	public void addCategory()
	{
		ListEntry list = manager.addCategory(newCategoryName.getText());
		addEntryList(list);
		newCategoryName.setText("");
		tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
	}
	
	public void removeCategory(ListEntry category)
	{
		manager.removeCategory(category);
		tabbedPane.remove(category);
	}
	
	public void load()
	{
		Print.m("Loading File " + Main.saveFile.getAbsolutePath());
		ListEntry[] listEntrys = manager.loadSaveFile(Main.saveFile);
		//Reset all
		deleteCategory.removeAll();
		for(ListEntry le : this.entryList)
		{
			tabbedPane.remove(le);
		}
		//Add new Categorys
		for(ListEntry listEntry : listEntrys)
		{
			addEntryList(listEntry);
		}
	}
	
	public void save()
	{
		Print.m("Saving file " + Main.saveFile);
		Datastorage.writeFile(Main.saveFile, manager.getStorage());
	}
	
	public Manager getManager()
	{
		return manager;
	}
}
