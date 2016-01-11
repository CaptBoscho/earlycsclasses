#pragma once

#include <iostream>
#include <string>
#include <vector>
#include <set>
#include "DatalogProg.h"
#include "Tuple.h"
#include "Scheme.h"

using namespace std;

class Relation {
private:
	set<Tuple> pairs;
	string name;
	Pred object;


public:
	
	Relation(){};
	~Relation(){};
	
	Relation(string s, Pred p){
		name = s;
		object = p;
	}
	
	void setName(string s)
	{
		name = s;
	}
	
	void setScheme(Pred s)
	{
		object = s;
	}
	
	void addTuples(Tuple vs)
	{
		pairs.insert(vs);
	}
	
	void setTuples(set<Tuple> m)
	{
		pairs = m;
	}
	
	string getName()
	{
		return name;
	}
	
	Pred getScheme()
	{
		return object;
	}
	
	set<Tuple> getTuples()
	{
		return pairs;
	}
	
	
};
