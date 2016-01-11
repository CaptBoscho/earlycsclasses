#pragma once

#include <iostream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

class LineCounter
{

private:

	string word;
	int number;
	
	
public:
	
	LineCounter();

	LineCounter(string w, int n);

	string getWord() const {return word;}
	int getNumber() const {return number;}
	

	void setWord(string w);
	void setNumber(int n);

};
	
