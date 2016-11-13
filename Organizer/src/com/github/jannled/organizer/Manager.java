package com.github.jannled.organizer;

import java.io.File;

import com.github.jannled.lib.datastorage.Datastorage;
import com.github.jannled.lib.datastorage.Storage;
import com.github.jannled.lib.datastorage.StorageKey;
import com.github.jannled.organizer.window.Entry;
import com.github.jannled.organizer.window.ListEntry;

public class Manager
{
	ListEntry[] lists;
	Storage storage;
	
	public Manager()
	{
		
	}
	
	/**
	 * Reads the save file and stores its content into Manager, returns an Array of ListEntry for conveniece
	 * @param saveFile
	 * @return
	 */
	public ListEntry[] loadSaveFile(File saveFile)
	{
		this.storage = Datastorage.parseFile(saveFile);
		ListEntry[] entry = new ListEntry[storage.getKeys().size()];
		for(int i=0; i<entry.length; i++)
		{
			StorageKey key = storage.getKeys().get(i);
			Entry[] entrys = new Entry[key.getKeys().size()];
			for(int j=0; j<entrys.length; j++)
			{
				entrys[j] = new Entry(entry[i], key.getKeys().get(j));
			}
			entry[i] = new ListEntry(entrys, key.getName(), key);
		}
		lists = entry;
		return entry;
	}
	
	public ListEntry[] getListEntrys()
	{
		return lists;
	}
	
	public ListEntry addCategory(String name)
	{
		StorageKey category = new StorageKey(name, storage);
		ListEntry list = new ListEntry(name, category);
		lists = incrementArray(lists, 1);
		lists[lists.length-1] = list;
		storage.addStorageKey(category);
		return list;
	}
	
	public void addCategory(ListEntry category)
	{
		lists = incrementArray(lists, 1);
		lists[lists.length] = category;
		storage.addStorageKey(new StorageKey(category.getName(), storage));
	}
	
	public void removeCategory(ListEntry category)
	{
		ListEntry[] buffer = new ListEntry[lists.length-1];
		int pos = 0;
		for(int i=0; i<lists.length; i++)
		{
			if(lists[i]!=category)
			{
				buffer[pos] = lists[i];
				pos++;
			}
		}
		lists = buffer;
	}
	
	private ListEntry[] incrementArray(ListEntry[] array, int amount)
	{
		ListEntry[] buffer = new ListEntry[array.length+amount];
		for(int i=0; i<array.length; i++)
		{
			if(i<array.length)
			{
				buffer[i] = array[i];
			}
		}
		return buffer;
	}
	
	public Storage getStorage()
	{
		return storage;
	}
}
