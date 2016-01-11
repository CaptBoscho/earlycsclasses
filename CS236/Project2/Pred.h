#pragma once

#include <iostream>
#include <string>
#include <fstream>
#include <map>
#include <vector>


#include "Param.h"

using namespace std;

class Pred {
	
private:
	/*name
	 * list of param (vector)
	 
	 */
	 string name;
	 vector<Param> list;



public:
	
	Pred(){};
	
	Pred(string s){
		name = s;
	}
	
	~Pred(){};
	
	void setName(string s)
	{
		name = s;
	}
	
	string getName()
	{
		return name;
	}
	
	void AddParam(Param pm)
	{
		list.push_back(pm);
	}
	
	vector<Param> getParam()
	{
		return list;
	}
	
	
	
	
	
};
