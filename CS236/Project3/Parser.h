#pragma once

#include <iostream>
#include <string>
#include <fstream>
#include <map>
#include <vector>

#include "Token.h"
#include "DatalogProg.h"
#include "Param.h"
#include "Rule.h"
#include "Pred.h"
#include "Scanner.h"


using namespace std;

class Parser {
	
private:
	int currindex;
	TokenType token;
	vector<Token> tokenslist;
	//do i need vectors of Queries, Facts, etc?
	
	DatalogProg d;
	Rule r;
	Pred p;
	Param pm;
	



public:
	
	Parser(){
		currindex = 0;
	}
	
	~Parser(){};
	
	
	void RunParse(ifstream& in, ofstream& out)
	{
		
		Scanner scan;
		tokenslist = scan.Execute(in, out);
		
		try {
			DatalogProgram(in, out);
		
		
			//d.PrintParse(out);
		}
		
		catch (int i)
		{
			
			out << "Failure!" << endl;
			out << "  (" << tokenslist[currindex].getKind()<< ",\""<<tokenslist[currindex].getValue()<<"\","<<tokenslist[currindex].getLine()<<")"<<endl;
			in.close();
			out.close();
		}
		
	}
	
	void Error(ifstream& in, ofstream& out)
	{
		throw 1;
	}
	
	void match(string t, ifstream& in, ofstream& out)
	{
		if(tokenslist[currindex].getKind() == t)
		{
			if (currindex<tokenslist.size())
			{
				//cout << currindex << endl;
				currindex++;
			}
		}
		else
		{
			Error(in, out);
		}
	}
	
	
	
	void DatalogProgram(ifstream& in, ofstream& out)
	{
		match("SCHEMES" , in, out);
		match("COLON", in, out);
		SchemeList(in, out);
		
		match("FACTS", in, out);
		match("COLON", in, out);
		FactList(in, out);
		
		match("RULES", in, out);
		match("COLON", in, out);
		RuleList(in, out);
		
		match("QUERIES", in, out);
		match("COLON", in, out);
		QueryList(in, out);
		
		
		if(tokenslist.size()-1 > currindex)
		{
			Error(in, out);
		}
	}
	
	void SchemeList(ifstream& in, ofstream& out)
	{
		Scheme(in, out);
		//cout << "test" << tokenslist[currindex].getType() << endl;
		if (tokenslist[currindex].getKind()=="ID")
		{
			SchemeList(in, out);
		}
	}
	
	void Scheme(ifstream& in, ofstream& out)
	{
		
		Predicate(in, out);
		d.addScheme(p);
	}
	
	void FactList(ifstream& in, ofstream& out)
	{
		if (tokenslist[currindex].getKind()=="RULES")
		{
		}
		else
		{
			Fact(in, out);
			if (tokenslist[currindex].getKind()=="ID")
			{
				FactList(in, out);
			}
		}
		//how do i do it for epsilon?
	}
	
	void Fact(ifstream& in, ofstream& out)
	{
		Predicate(in, out);
		d.addFacts(p);
		match("PERIOD", in, out);
	}
	
	void RuleList(ifstream& in, ofstream& out)
	{
		if (tokenslist[currindex].getKind()=="QUERIES")
		{
		}
		else
		{
			Rules(in, out);
			if (tokenslist[currindex].getKind()=="ID")
			{
				RuleList(in, out);
				//how do i do it for epsilon?
			}
		}
	}
	
	void Rules(ifstream& in, ofstream& out)
	{
		if (tokenslist[currindex].getKind()=="ID")
		{
			Predicate(in, out);
			r.setHead(p);
			match("COLON_DASH", in, out);
			PredList(in, out); //add the vectors here
			match("PERIOD", in, out);
			d.addRule(r);
		}
	}
	
	void QueryList(ifstream& in, ofstream& out)
	{
		Query(in, out);
		
		if (currindex < tokenslist.size())
		{
			if (tokenslist[currindex].getKind()=="ID")
			{
				QueryList(in, out);
			}
		}
	}
	
	void Query(ifstream& in, ofstream& out)
	{
		Predicate(in, out);
		d.addQuery(p);
		match("Q_MARK", in, out);
	}
	
	void PredList(ifstream& in, ofstream& out) {
		Predicate(in, out);
		r.addPred(p);
		if(tokenslist[currindex].getKind()== "COMMA") {
			match("COMMA", in, out);
			PredList(in, out);
		}
		
		
	}
	
	void Predicate(ifstream& in, ofstream& out) {
		p = Pred(tokenslist[currindex].getValue());
		//p.setName(tokenslist[currindex].getValue());
		//cout << p.getName()<< endl;
		match("ID", in, out);
		match("LEFT_PAREN", in, out);
		Paramlist(in, out); 
		match("RIGHT_PAREN", in, out);
		
	}
	
	void Paramlist(ifstream& in, ofstream& out) {
		Parameter(in, out);
		if(tokenslist[currindex].getKind()== "COMMA") {
			match("COMMA", in, out);
			Paramlist(in, out);
		}
	
	}
	
	void Parameter(ifstream& in, ofstream& out) {
		if (tokenslist[currindex].getKind() == "STRING") {
			pm.setName(tokenslist[currindex].getValue());
			pm.setBooleanGood();
			match("STRING", in, out);
		}
		else if (tokenslist[currindex].getKind() == "ID") {
			pm.setName(tokenslist[currindex].getValue());
			pm.setBooleanBad();
			match("ID", in, out);
		}
		else {
			Error(in, out);
		}
		
		p.AddParam(pm);
	}
	

	vector<Pred> getFacts()
	{
		return d.getFacts();
	}
	
	vector<Pred> getSchemes()
	{
		return d.getSchemes();
	}
	
	vector<Pred> getQueries()
	{
		return d.getQueries();
	}
	
	
	
	
};
