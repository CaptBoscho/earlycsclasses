#pragma once

#include "DNode.h"

template <typename ItemType>

class HashSet {

public:

	LinkedList<ItemType>* table;
	int tableSize;
	int size;

	HashSet(){
		size=0;
		tableSize = 0;
		table = NULL;
	}
		
	~HashSet()
	{
		if (size > 0)
		{
			clear();
		}
		delete[] table;
	}
	
	
	void clear()
	{
		for (int i = 0; i< tableSize; i++)
		{
			table[i].clear();
		}
		tableSize = 0;
		size = 0;
		
	}
	
	
	int getsize()
	{ return size;}
	
	int getTS()
	{ return tableSize; }
	
	
	unsigned hashCode( string item, unsigned tableSize ) //if string
	{

	    unsigned hashCode = 0;

	    for (unsigned i = 0; i < item.length(); i++)
	       { hashCode = hashCode * 31 + item.at(i); }

	    return hashCode % tableSize;

	}
	
	void rhfunction(LinkedList<ItemType>*& table2, LinkedList<ItemType>& inlist, int ts)
	{
		
		while (inlist.getsize() > 0)
		{
			string item = inlist.remove(0);
			int index = hashCode(item, ts);
		
			table2[index].insert(table2[index].getsize(), item);
		}		
		
		
	}
	
	void rehash()
	{
		int new_ts = 0;
		if (size >= tableSize)
		{
			new_ts = tableSize * 2 + 1;
		}
		else
		{
			new_ts = tableSize/2;
		}
		
		LinkedList<ItemType>* table2 = new LinkedList<ItemType>[new_ts];
		
		for(int i=0; i < tableSize; i++)
		{
			rhfunction(table2, table[i], new_ts);
		}
		
		LinkedList<ItemType>* temp = table;
		table = table2;
		tableSize = new_ts;
		delete[] temp;
	}
	
	void add(const ItemType& item) 
	{
		if (size > 0)
		{
			int there = find(item);
			if(there >= 0)
			{ return; }
		}
		
		if (size >= tableSize)
		{
			rehash();
		}
		int index = hashCode(item, tableSize);
		
		table[index].insert(table[index].getsize(), item);
		size++;

	}

	void remove(const ItemType& item) {
		
		int index = hashCode(item, tableSize);
		int listindex = find(item);
		if (listindex == -1)
		{
			return;
		}
		
		table[index].remove(listindex);
		size--;
		
		if (size <= tableSize/2)
		{
			rehash();
		}

	}
	
	int find(const ItemType& item) {
		if (size == 0)
		{
			return -1;
		}
		int index = hashCode(item, tableSize);
		int found = table[index].find(item);
		
		return found;

	}
	
	void Print(ofstream& out)
	{
		if (tableSize > 0)
		{
			for(int i=0; i < tableSize; i++)
			{
				out << "hash "<<i<<":";
				table[i].Printer(out, i);
				out << endl;
			}
		}
	}
	
	

};
