#pragma once

#include <iostream>
#include <string>
#include <fstream>
#include <map>

#include "Token.h"

using namespace std;

class Scanner {

private:
	//char c;
    int linenumb;
    int totalt;

public:
	
	Scanner(){
		//c;
		linenumb = 1;
		totalt = 0;
	}
	
	~Scanner(){};
	
	int getLineN()
	{
		return linenumb;
	}
	
	int getTotalT()
	{
		return totalt;
	}
	
	
	
	string ColonDash(char&c, ifstream& in, ofstream& out)
	{
		string str;
		char p = in.peek();
		str.push_back(c);
			
		if (p == '-')
		{
			c=in.get();
			str.push_back(c);
		}
		return str;
	}
	
	
	string ScanId (char& c, ifstream& in, ofstream& out)
	{
		char p = in.peek();
		string str;
		str.push_back(c);
		while (isalnum(p))
		{
			c=in.get();
			str.push_back(c);
			p=in.peek();
			
		}
		return str;
	}
	
	
	
	string ScanString(ifstream& in, char& c, int& linenumb, ofstream& out)
	{
		string str;
		c=in.get();
		while (c != '\'' && !in.eof())
		{
			str.push_back(c);
			c=in.get();
			if (c == '\n')
			{
				out << "Input Error on line " << linenumb << endl;
				in.close();
				out.close();
			}
		}
		return str;
	}
	
	int Check(char&c, ifstream& in)
	{
		if (isspace(c))
		{
			if (c == '\n')
			{
				linenumb++;
			}
			return 0;
		}
		if (c == '#')
		{
			while (c!='\n')
			{
				c=in.get();
			}
			
			linenumb++;
			return 0;
		}
		return 1;
	}
	
	void Execute(char& c, ifstream& in, ofstream& out, map<string, string>& matching, vector<string>& specials)
	{
		Token t;
		string str;
		/*if (isspace(c))
		{
			if (c == '\n')
			{
				linenumb++;
			}
			return;
		}
		if (c == '#')
		{
			while (c!='\n')
			{
				c=in.get();
			}
			
			linenumb++;
			return;
		}*/
		
		int base = Check(c, in);
		if (base == 0)
		{
			return;
		}
		
		totalt++;
		
		
		
		switch (c)
		{
		case ',':
			str.push_back(c);
			t.setValue(str);
			t.setKind(matching[str]);
			
			out << "("<<t.getKind()<<",\""<<t.getValue()<<"\","<<linenumb<<")"<<endl;
			//out << "(COMMA,\",\","<< linenumb << ")" << endl;
			break;
			
		case ':':
			
			str = ColonDash(c, in, out);
			
			t.setValue(str);
			t.setKind(matching[str]);
			out << "("<<t.getKind()<<",\""<<t.getValue()<<"\","<<linenumb<<")"<<endl;
			
			break;
		case '(':
			str.push_back(c);
			t.setValue(str);
			t.setKind(matching[str]);
			
			out << "("<<t.getKind()<<",\""<<t.getValue()<<"\","<<linenumb<<")"<<endl;
			break;
		case ')':
			str.push_back(c);
			t.setValue(str);
			t.setKind(matching[str]);
			
			out << "("<<t.getKind()<<",\""<<t.getValue()<<"\","<<linenumb<<")"<<endl;
			break;
		case '?':
			str.push_back(c);
			t.setValue(str);
			t.setKind(matching[str]);
			
			out << "("<<t.getKind()<<",\""<<t.getValue()<<"\","<<linenumb<<")"<<endl;
			break;
		case '.':
			str.push_back(c);
			t.setValue(str);
			t.setKind(matching[str]);
			
			out << "("<<t.getKind()<<",\""<<t.getValue()<<"\","<<linenumb<<")"<<endl;
			break;
		case '\'':
			str = ScanString(in, c, linenumb, out);
			
			t.setValue(str);
			t.setKind(matching["\'"]);
			
			out << "("<<t.getKind()<<",\""<<t.getValue()<<"\","<<linenumb<<")"<<endl;
			break;
			
		
		default:
			if (isalpha(c))
			{
				string str = ScanId(c, in, out);
				for (unsigned int i = 0; i<specials.size(); i++)
				{
					if (str == specials[i])
					{
						t.setKind(matching[str]);
						i=specials.size();
					}
					else
					{
						t.setKind(matching["x"]);
					}
				}
				
				t.setValue(str);
				
				
				out << "("<<t.getKind()<<",\""<<t.getValue()<<"\","<<linenumb<<")"<<endl;
			}
			else
			{
				out << "Input Error on line " << linenumb << endl;
				in.close();
				out.close();
			}
			
		}


	}

		
	
	
	
	
};
