#include <iostream>
#include <array>
#include <string>
#include <fstream>
#include <sstream>

//#include <cstdlib>
#include "DNode.h"
#include "Tree.h"


using namespace std;



void Execute(string& word, AVLTreeSet<string>& dudes, ofstream& actions)
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
		actions << "remove "<<" "<<name<<endl;
	}
	
	if (first == "find")
	{
		string rescue;
		string truth;
		party>>rescue;
		bool pos = dudes.find(rescue);
		if (pos ==1)
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
		actions << first;
		dudes.Print(actions);	
		actions << endl;
		
	}
	


}


int main (int argc, char* argv[])
{
	ifstream commands;
	ofstream actions;
	AVLTreeSet<string> dudes;
	
	
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
