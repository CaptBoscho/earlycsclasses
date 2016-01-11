#pragma once

#include <iostream>
#include <string>
#include <fstream>
#include <map>
#include <vector>




using namespace std;

class Param {
	
private:
	
	string value;
	bool isString;
/* name ID
 * 
 */


public:
	
	Param() {
		
	}
	
	~Param(){};
	
	void setName(string s)
	{
		value = s;
	}
	
	void setBooleanGood()
	{
		isString = true;
	}
	
	void setBooleanBad()
	{
		isString = false;
	}
	
	string getName()
	{
		return value;
	}
	
	bool getBoolean()
	{
		return isString;
	}
	
	
};
