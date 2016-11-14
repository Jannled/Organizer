package com.github.jannled.organizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.github.jannled.lib.ArrayUtils;
import com.github.jannled.lib.FileUtils;
import com.github.jannled.lib.Print;
import com.github.jannled.organizer.window.Searchbar;
import com.github.jannled.organizer.window.WindowManager;
import com.github.jannled.window.Utils;

public class Main
{
	public static File saveFile;
	WindowManager windowManager;
	Manager manager;
	Searchbar searchbar;
	
	public Main()
	{
		manager = new Manager();
		windowManager = new WindowManager(manager);

		saveFile = new File("Items.jsf");

		Print.m("Setting save file to " + saveFile.getAbsolutePath());
		readSaveFile();
	}

	public static void main(String[] args)
	{
		Print.setOutputLevel(Print.ALL);
		Print.m("Running Organizer with arguments " + ArrayUtils.arrayToString(args) + ".");
		Utils.setOSLook();
		new Main();
	}
	
	public void readSaveFile()
	{
		if(!saveFile.exists())
		{
			try
			{
				Print.m("File " + saveFile.getPath() + " doesn't exist, loading default one!");
				FileUtils.stream(Main.class.getResourceAsStream("/save/Items.jsf"), new FileOutputStream(saveFile));
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		windowManager.load();
	}
}
