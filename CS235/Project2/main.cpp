#include <iostream>
#include <set>
#include <iomanip>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
#include <sstream>
#include <map>
#include <list>
#include "LineCounter.h"

using namespace std;



string LowerandStuff(string word)
{
	for (unsigned int i = 0; i< word.size(); i++) 
		{
			char c;
			if(!isalpha(word.at(i)))
			{
				
				word.replace(i, 1, " ");
			}
			
			c = word[i];
			
			if(!islower(c))
			{
				word[i] = tolower(word[i]);
				
			}			
		}
	return word;
}

set<string> LoadDic(ifstream& read, string word)
{
	set<string> dictionary;
	while (getline(read, word))
    {
		word = LowerandStuff(word);
		dictionary.insert(word);
	}
	
	return dictionary;
}

vector<LineCounter> LoadDoc(ifstream& read, string fun, set<string> dic) //map function?
{
	vector<LineCounter> list;
	int count = 1;
	LineCounter temporary;
	while (getline(read, fun)) //So every line is 1 string now
	{
		fun = LowerandStuff(fun);

		
		stringstream party;
		
		party << fun;
		string word;
		while (party>>word)
		{
			const bool is_in = dic.find(word) != dic.end();
			if(is_in== false)//How do you say if it's not in a set?
			{
				temporary.setWord(word);
				temporary.setNumber(count);
			
				list.push_back(temporary);	
			}
					
		}		
		
	
		count++;
	}
	
	return list;
	
}

map<string, vector<int> > CreateMap(vector<LineCounter> doc)
{
	
	string wordtime;
	map<string, vector<int> > linecheck;
	
	for(unsigned int i=0; i< doc.size(); i++)
	{
		vector<int> lines;
		wordtime = doc[i].getWord();
		//lines.push_back(doc[i].getNumber());
	
		
		for(unsigned int k=0; k<doc.size(); k++)
		{
			if(doc[k].getWord() == wordtime)
			{
				lines.push_back(doc[k].getNumber());
			}
		}
		
		
		
		linecheck [doc[i].getWord()] = lines;
	}

	return linecheck;
}



void PrintMap (map<string, vector<int> > m, set<string> dic, ofstream& write)
{
	map<string, vector<int> >::iterator p;
	
	
	for(p = m.begin(); p != m.end(); p++)
	{
		
		//string wordcheck = p->first;
		//set<string>::iterator it;
		//const bool is_in = dic.find(wordcheck) != dic.end();
		//if(is_in== false)//How do you say if it's not in a set?
		//{

		
			write << p->first<< ": ";
		
		
			for(unsigned int j=0; j<(p->second).size(); j++)
			{
			
				write << (p->second)[j]<< " ";
				
			
			}
			write << endl;
		//}
		
	
	}
	

}

int main (int argc, char* argv[])
{
	ifstream readdic;
	ifstream readdoc;
    ofstream write;
    set<string> dictionary;
    vector<LineCounter> document;
    map<string, vector<int> > linechecker;
    
	
	
	readdic.open(argv[1]); 
    string word;
    
    
    dictionary = LoadDic(readdic, word); 
    
    readdic.close();
   	
	readdoc.open(argv[2]);
	string fun;
	document = LoadDoc(readdoc, fun, dictionary);
	readdoc.close();
	
	linechecker = CreateMap(document);
	
	write.open(argv[3]);
	PrintMap(linechecker, dictionary, write);
	
	write.close();
	
	return 0;
}
