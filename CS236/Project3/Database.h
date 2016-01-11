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
	
};
