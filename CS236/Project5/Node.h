#pragma once

#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <set>




using namespace std;

class Node {
	
private:
	
	bool mark;
	int ponumb;
	string nodeid;
	set<string> adnodes;
	int index;


public:

	Node(){};
	~Node(){};
	
	Node(string s)
	{
		mark = false;
		nodeid = s;
	}
	
	void setName(string s)
	{
		nodeid= s;
	}
	
	void addAdjacency(string name)
	{
		adnodes.insert(name);
	}
	
	string getName()
	{
		return nodeid;
	}
	
	set<string> getAdj()
	{
		return adnodes;
	}
	
	void setAdjSet(set<string> s)
	{
		adnodes = s;
	}
	
	void setNumber(int n)
	{
		ponumb = n;
	}
	
	int getNumber()
	{
		return ponumb;
	}
	
	void setMark(bool b)
	{
		mark = b;
	}
	
	bool getMark()
	{
		return mark;
	}
	
	void setIndex(int i)
	{
		index = i;
	}
	
	int getIndex()
	{
		return index;
	}
	
	
};
