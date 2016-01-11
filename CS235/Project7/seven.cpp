#include <iostream>
#include <array>
#include <string>
#include <fstream>
#include <sstream>

//#include <cstdlib>
#include "DNode.h"
#include "Hash.h"


using namespace std;



void Execute(string& word, HashSet<string>& dudes, ofstream& actions)
{
	
	stringstream party;
	party << word;
	string first;
	party>>first;

	if (first == "clear")
	{
		dudes.clear();
		actions<<first<<endl;
	}
	
	if (first == "add")
	{				
		string name;
		party>>name;
		dudes.add(name);
		actions <<first << "  " <<name<<endl;
		
		
	}
	
	if (first == "remove")
	{
		string name;
		party>>name;
		dudes.remove(name);
		actions << "remove "<<name<<endl;
	}
	
	if (first == "find")
	{
		string rescue;
		string truth;
		party>>rescue;
		int pos = dudes.find(rescue);
		if (pos >= 0)
		{
			truth = "true";
		}
		else
		{
			truth = "false";
		}
		
		actions<<"find "<<rescue<<" "<<truth<<endl;
	}
	
	if (first == "print")
	{
		actions << first << endl;;
		dudes.Print(actions);	
		
		
	}
	


}


int main (int argc, char* argv[])
{
	ifstream commands;
	ofstream actions;
	HashSet<string> dudes;
	
	
	commands.open(argv[1]); 
    string word;
    actions.open(argv[2]);
    while (getline(commands, word))
    {
		Execute(word, dudes, actions);
	}
	
    commands.close();
    actions.close();
   
	
	
	return 0;
}
