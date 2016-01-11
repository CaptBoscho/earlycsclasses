#pragma once

#include <iostream>
#include <string>
#include <fstream>
#include <map>
#include <vector>
#include <set>

#include "Token.h"
#include "Pred.h"
#include "Rule.h"
#include "Param.h"


using namespace std;

class DatalogProg {
	
private:
	/* vector of schemes
	 * facts
	 * rules
	 * queries
	 * domain values
	 */
	 vector<Pred> Schematics;
	 vector<Pred> Factuals;
	 vector<Rule> Rules;
	 vector<Pred> Querify;
	 
	 set<string> domain;
	 



public:
	
	DatalogProg() {
	}
	
	~DatalogProg(){};
	
	void addScheme(Pred P)
	{
		Schematics.push_back(P);
	}
	
	void addFacts(Pred P)
	{
		Factuals.push_back(P);
	}
	
	void addRule(Rule P)
	{
		Rules.push_back(P);
	}
	
	void addQuery(Pred P)
	{
		Querify.push_back(P);
	}
	
	vector<Pred> getFacts()
	{
		return Factuals;
	}
	
	vector<Pred> getSchemes()
	{
		return Schematics;
	}
	
	vector<Pred> getQueries()
	{
		return Querify;
	}
	
	vector<Rule> getRules()
	{
		return Rules;
	}
	
	
	void PrintSchemes(ofstream& out)
	{
		out << "Schemes(" << Schematics.size() << "):" << endl;
		vector<Param> temp;
		
		for (unsigned int i=0; i<Schematics.size(); i++)
		{
			temp = Schematics[i].getParam();
			
			out << "  "<<Schematics[i].getName()<< "(";
			
			PrintParam(temp, out);
			
			out << ")" << endl;
		}
	}
	
	void PrintFacts(ofstream& out)
	{
		out << "Facts(" << Factuals.size() << "):" << endl;
		
		for (unsigned int i=0; i<Factuals.size(); i++)
		{
			vector<Param> temp = Factuals[i].getParam();
			
			out << "  "<<Factuals[i].getName()<< "(";
			
			PrintParam(temp, out);
			
			out << ")" << endl;
		}
	}
	
	void PrintRules(ofstream& out)
	{
		out << "Rules(" << Rules.size() << "):" << endl;
		
		for (unsigned int i=0; i<Rules.size(); i++)
		{
			Pred temporary = Rules[i].getHead();
			out << "  " << temporary.getName() << "(";
			
			vector<Param> header = temporary.getParam();
			
			toString(header, out);
			
			out << ") :- ";
			
			
			
			
			
			
			
			vector<Pred> listpred = Rules[i].getBody();
			
			
			for(unsigned int h=0; h<listpred.size(); h++)
			{
				vector<Param> temp = listpred[h].getParam();
				out << listpred[h].getName() << "(";
			
				PrintParam(temp, out);
				
				out << ")";
				if (h < listpred.size()-1)
				{
					out <<",";
				}
				
				
			}
			out << endl;
		}
		
	}
	
	void toString(vector<Param> header, ofstream& out)
	{
		for(unsigned int j=0; j<header.size(); j++)
			{
				if (header[j].getBoolean() == true)
				{
					out << "'" << header[j].getName() << "'";
					domain.insert(header[j].getName());
				}
				else
				{
					out << header[j].getName();
				}
				
				if (j < header.size() - 1)
				{
					out << ",";
				}
				
			}
	}
	
	void PrintParam(vector<Param> temp, ofstream& out)
	{
		for(unsigned int j=0; j<temp.size(); j++)
			{
				if (temp[j].getBoolean() == true)
				{
					out << "'" << temp[j].getName() << "'";
					domain.insert(temp[j].getName());
				}
				else
				{
					out << temp[j].getName();
				}
				
				if (j < temp.size() - 1)
				{
					out << ",";
				}
				
			}
	}
	
	void PrintQueries(ofstream& out)
	{
		
		out << "Queries(" << Querify.size() << "):" << endl;
		
		for (unsigned int i=0; i<Querify.size(); i++)
		{
			vector<Param> temp = Querify[i].getParam();
			
			out << "  "<<Querify[i].getName()<< "(";
			
			PrintParam(temp, out);
			out << ")" << endl;
		}
	}
	
	void PrintDomain(ofstream& out)
	{
		
		out << "Domain("<< domain.size()<<"):"<<endl;
		set<string>::iterator it;
		for(it=domain.begin(); it!=domain.end(); ++it)
		{
			out << "  '"<< *it << "'" << endl;
		}
	}
	
	void PrintParse(ofstream& out)
	{
		out << "Success!" << endl;
		PrintSchemes(out);
		PrintFacts(out);
		PrintRules(out);
		PrintQueries(out);
		PrintDomain(out);
		
	}
	
	
	
};
