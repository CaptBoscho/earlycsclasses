#pragma once

#include <iostream>
#include <string>
#include <fstream>
#include <map>

using namespace std;

enum TokenType
{
	COMMA, PERIOD, Q_MARK, LEFT_PAREN, RIGHT_PAREN, COLON, COLON_DASH, STRING, ID,
	SCHEMES, FACTS, RULES, QUERIES};


class Token
{
private:
	string kind;
	string value;
	//int linenumb;
	
	//map<string, TokenType> matching;
		
		
public:
	
	Token()
	{
		
	};
	
	~Token(){}
	
	void setValue(string v)
	{
		value = v;
	}
	
	void setKind(string k)
	{
		kind = k;
	}
	
	string getValue()
	{
		return value;
	}
	
	string getKind()
	{
		return kind;
	}
	
	
	
	
		
	
		
};
