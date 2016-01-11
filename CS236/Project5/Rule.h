#pragma once

#include <iostream>
#include <string>
#include <fstream>
#include <map>
#include <vector>


#include "Pred.h"

using namespace std;

class Rule {
	
private:

	Pred head;
	vector<Pred> body;

public:
	
	Rule(){
	}
	
	~Rule(){};
	
	void setHead(Pred p)
	{
		head = p;
	}
	
	void addPred(Pred p)
	{
		body.push_back(p);
	}
	
	Pred getHead()
	{
		return head;
	}
	
	vector<Pred> getBody()
	{
		return body;
	}
	
	
};
