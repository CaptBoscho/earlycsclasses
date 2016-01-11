#include <iostream>
#include <array>
#include <string>
#include <fstream>
#include <sstream>

//#include <cstdlib>
#include "DNode.h"


using namespace std;



void Execute(string& word, LinkedList<string>& students, ofstream& actions)
{
	
	stringstream party;
	party << word;
	string first;
	party>>first;

	if (first == "clear")
	{
		students.clear();
		actions<<first<<endl;
	}
	
	if (first == "insert")
	{
		int ind;
		string name;
		party>>ind>>name;
		students.insert(ind, name);
		actions <<first << "  " <<ind<<"  "<<name<<endl;
		
	}
	
	if (first == "remove")
	{
		int ind;
		party>>ind;
		string nombre= students.remove(ind);
		actions << "remove "<<ind<<" "<<nombre<<endl;
	}
	
	if (first == "find")
	{
		string rescue;
		party>>rescue;
		int pos = students.find(rescue);
		
		actions<<"find "<<rescue<<" "<<pos<<endl;
	}
	
	if (first == "print")
	{
		students.Printer(actions);
		
		
	}
	


}


int main (int argc, char* argv[])
{
	ifstream commands;
	ofstream actions;
	LinkedList<string> students;
	
	
	commands.open(argv[1]); 
    string word;
    actions.open(argv[2]);
    while (getline(commands, word))
    {
		Execute(word, students, actions);
	}
	cout << "test" << endl;
    commands.close();
    actions.close();
    cout << "test2" << endl;
	
	
	return 0;
}
