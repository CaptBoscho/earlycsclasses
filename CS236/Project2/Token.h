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
	int linen;
	TokenType typee;
	map<string, TokenType> matching;
	
		
		
public:
	
	Token()
	{
		matching [","] = COMMA;
		matching ["."] = PERIOD;
		matching ["?"] = Q_MARK;
		matching ["("] = LEFT_PAREN;
		matching [")"] = RIGHT_PAREN;
		matching [":"] = COLON;
		matching [":-"] = COLON_DASH;
		matching ["Schemes"] = SCHEMES;
		matching ["Facts"] = FACTS;
		matching ["Rules"] = RULES;
		matching ["Queries"] = QUERIES;
		matching ["x"] = ID;
		matching ["\'"] = STRING;
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
	
	void setLine(int l)
	{
		linen = l;
	}
	
	void setType(string s)
	{
		
		
		
		typee = matching[s];
	}
	
	string getValue()
	{
		return value;
	}
	
	string getKind()
	{
		return kind;
	}
	
	int getLine()
	{
		return linen;
	}
	
	int getType()
	{
		return typee;
	}
	
	
	
	
		
	
		
};
