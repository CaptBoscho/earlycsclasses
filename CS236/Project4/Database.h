#pragma once

#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <map>
#include "Relation.h"
#include "Tuple.h"
#include "Parser.h"

using namespace std;

class Database {
private:
	map<string,Relation> relations;
	



public:
	
	Database(){};
	~Database(){};
	
	void addRelation(Relation r)
	{
		string temp = r.getName();
		relations [temp] = r;
	}
	
	Relation getRelation(string s)
	{
		return relations[s];
	}
	
	int Sizer()
	{
		int number = 0;
		for(map<string,Relation>::iterator it=relations.begin(); it!=relations.end(); ++it)
		{
			Relation r = it->second;
			set<Tuple> s = r.getTuples();
			number = number + s.size();
		}
		return number;
	}
	
	void addTupler(string n, Tuple t)
	{
		relations[n].addTuples(t);
	}
	
	int getAmountTuples(string n)
	{
		int temp = relations[n].getAmount();
		return temp;
	}
	
};
