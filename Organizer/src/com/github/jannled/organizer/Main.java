package com.github.jannled.organizer;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import com.github.jannled.lib.ArrayUtils;
import com.github.jannled.lib.Print;
import com.github.jannled.organizer.window.WindowManager;
import com.github.jannled.window.Utils;

public class Main
{
	WindowManager windowManager;
	public static File saveFile;
	Manager manager;
	
	public Main()
	{
		manager = new Manager();
		windowManager = new WindowManager(manager);
		try
		{
			URL savePath = this.getClass().getResource("items.jsf");
			Print.m("Setting save file to " + savePath.toString());
			saveFile = new File(savePath.toURI());
		} catch (URISyntaxException e)
		{
			Print.e("Could not load save file, wrong Syntax");
			e.printStackTrace();
		}
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
		windowManager.load(saveFile);
	}
}
