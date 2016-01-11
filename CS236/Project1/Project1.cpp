#include <iostream>
#include <array>
#include <string>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>

#include "Token.h"
#include "Scanner.h"

using namespace std;


/*string ScanString(ifstream& in, char& c, int& linenumb, ofstream& out)
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
}*/

/*string ScanId (char& c, int& linenumb, ifstream& in, ofstream& out)
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
}*/

/*string ColonDash(char&c, ifstream& in, ofstream& out)
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
}*/

/*void Execute(char& c, int& linenumb, ifstream& in, ofstream& out, int& totalt)
{
	
	string str;
	if (isspace(c))
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
	}
	
	totalt++;
	
	switch (c)
	{
	case ',':
		out << "(COMMA,\",\","<< linenumb << ")" << endl;
		break;
	case ':':
		
		str = ColonDash(c, in, out);
		if (str == ":-")
		{
			out << "(COLON_DASH,\":-\"," << linenumb << ")" << endl;
		}
		else
		{
			out << "(COLON,\":\"," << linenumb << ")" << endl;
		}
		break;
	case '(':
		out << "(LEFT_PAREN,\"(\"," << linenumb << ")" << endl;
		break;
	case ')':
		out << "(RIGHT_PAREN,\")\"," << linenumb << ")" << endl;
		break;
	case '?':
		out << "(Q_MARK,\"?\"," << linenumb << ")" << endl;
		break;
	case '.':
		out << "(PERIOD,\".\"," << linenumb << ")" << endl;
		break;
	case '\'':
		str = ScanString(in, c, linenumb, out);
		out << "(STRING,\"" << str << "\"," << linenumb << ")" << endl;
		break;
		
	
	default:
		if (isalpha(c))
		{
			string str = ScanId(c, linenumb, in, out);
			out << "(ID,\"" << str << "\"," << linenumb << ")" << endl;
		}
		else
		{
			out << "Input Error on line " << linenumb << endl;
			in.close();
			out.close();
		}
		
	}


}*/


int main (int argc, char* argv[])
{
	ifstream in;
	ofstream out;
	
	
	in.open(argv[1]); 
	out.open(argv[2]);
    char c;
    //int linenumb = 0;
    
    Scanner scan;
    
    map<string, string> matching;
		matching [","] = "COMMA";
		matching ["."] = "PERIOD";
		matching ["?"] = "Q_MARK";
		matching ["("] = "LEFT_PAREN";
		matching [")"] = "RIGHT_PAREN";
		matching [":"] = "COLON";
		matching [":-"] = "COLON_DASH";
		matching ["Schemes"] = "SCHEMES";
		matching ["Facts"] = "FACTS";
		matching ["Rules"] = "RULES";
		matching ["Queries"] = "QUERIES";
		matching ["x"] = "ID";
		matching ["\'"] = "STRING";
		
	vector<string> specials = {"Facts", "Queries", "Rules", "Schemes"};
	
    
    c = in.get();
    while (!in.eof())
    {
		
		scan.Execute(c, in , out, matching, specials);
		c = in.get();
	}
	int totalt = scan.getTotalT();
	out << "Total Tokens = " << totalt;
    in.close();
    out.close();
	
	
	return 0;
} 
