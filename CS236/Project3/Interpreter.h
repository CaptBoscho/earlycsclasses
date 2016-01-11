#pragma once

#include <iostream>
#include <string>
#include <vector>
#include <fstream>
#include <map>
#include "Relation.h"
#include "Tuple.h"
#include "Parser.h"
#include "Database.h"

using namespace std;

class Interpreter {
private:
	Database data;
	Parser ping;
	vector<Pred> Factuals;
	vector<Pred> Schematics;
	vector<Pred> Querify;
	Relation rel;



public:
	
	Interpreter(){};
	~Interpreter(){};
	
	void DoIt(ifstream& in, ofstream& out)
	{
		ping.RunParse(in, out);
		Factuals = ping.getFacts();
		Schematics = ping.getSchemes();
		Querify = ping.getQueries();
		createRelations();
		
		Print1(out);
		EvaluateQueries(out);
		//Print2(out);
	}
	
	
	void createRelations()
	{
		for (unsigned int i=0; i<Schematics.size(); i++)
		{
			string name = Schematics[i].getName();
			rel= Relation(name, Schematics[i]);
			for (unsigned int k=0; k<Factuals.size(); k++)
			{
				if(Factuals[k].getName() == name)
				{
					putTuples(Factuals[k]);
				}
				
			}
			data.addRelation(rel);			
		}
	}
	
	void putTuples(Pred temp)
	{
		vector<Param> ps = temp.getParam();
		Tuple t;
		
		for(unsigned int i=0; i<ps.size(); i++)
		{
			string cool = ps[i].getName();
			t.push_back(cool);
		}
		rel.addTuples(t);
		
	}
	
	
	
	
	void EvaluateQueries(ofstream& out)
	{
		for (unsigned int i=0; i<Querify.size(); i++)
		{
			string head = Querify[i].getName();
			//search relationship map
			Relation r = data.getRelation(head);
			Relation alter = Select(Querify[i], r, out);
			Print2(out, alter, Querify[i]);
		}
	}
	
	
	bool Select2(vector<Param>& questions, bool add, Tuple t, int i)
	{
		if (questions[i].getBoolean())
		{
			if(t[i] != questions[i].getName()) //compare strings
			{
				add = false;
			}
			
		}
		return add;
	}
	
	Relation Select(Pred q, Relation r, ofstream& out)
	{
		vector<Param> questions = q.getParam();
		Relation alter = Relation(r.getName(), r.getScheme());
		set<Tuple> pair = r.getTuples();
		set<Tuple> pair2;		
		
		for( set<Tuple>::iterator it = pair.begin(); it != pair.end(); it++)
		{
			bool add = true;
			map<string,int> tempmap;
			Tuple t = *it;
			
			for (unsigned int i=0; i<questions.size(); i++)
			{
				add = Select2(questions, add, t, i);
				/*if (questions[i].getBoolean())
				{
					if(t[i] != questions[i].getName()) //compare strings
					{
						add = false;
					}
					
				}*/
				if (questions[i].getBoolean() == false)
				{
					if(tempmap.count(questions[i].getName()))
					{
						
						int index = tempmap[questions[i].getName()];
						
						if (t[index] != t[i])
						{
							add = false;
							
						}
						else
						{
							tempmap[questions[i].getName()] = i;
						}
					}
					else
					{
						tempmap [questions[i].getName()] = i;
					}
				}
			}
			
			if(add)
			{
				pair2.insert(t);
			}
		}
		alter.setTuples(pair2);
		return alter;
	}
	
	void Project2(Tuple t, vector<Param> temp, vector<Param> temp2, ofstream& out)
	{
		set<string> doubles;
				
		bool party = false;
		if(temp[0].getBoolean() == false)
		{
			out << "  ";
			party = true;
		}
		
		bool space = false;
		for(unsigned int i=0; i<temp.size(); i++)
		{
			if(temp[i].getBoolean() == false && doubles.count(temp[i].getName())==0)
			{
				if(party == false)
				{
					out << "  ";
					party = true;
				}
				out << temp2[i].getName() << "='" << t[i] << "' ";
				doubles.insert(temp[i].getName());
				space = true;
			}
		}
		if(space)
		{
			out << endl;
		}
	}
	
	void Project(vector<Param> temp, Relation r, ofstream& out, vector<Param> temp2)
	{
		set<Tuple> pairs = r.getTuples();
		
		for(set<Tuple>::iterator j=pairs.begin(); j!=pairs.end(); ++j)
			{
				Tuple t = *j;
				Project2(t, temp, temp2, out);
				/*set<string> doubles;
				Tuple t = *j;
				
				bool party = false;
				if(temp[0].getBoolean() == false)
				{
					out << "  ";
					party = true;
				}
				
				bool space = false;
				for(unsigned int i=0; i<temp.size(); i++)
				{
					if(temp[i].getBoolean() == false && doubles.count(temp[i].getName())==0)
					{
						if(party == false)
						{
							out << "  ";
							party = true;
						}
						out << temp2[i].getName() << "='" << t[i] << "' ";
						doubles.insert(temp[i].getName());
						space = true;
					}
				}
				if(space)
				{
					out << endl;
				}*/
				
			}
	}
	
	void Rename(vector<Param> temp, Relation r, ofstream& out)
	{
		set<Tuple> pairs = r.getTuples();
		
		for(set<Tuple>::iterator j=pairs.begin(); j!=pairs.end(); ++j)
			{
				set<string> doubles;
				Tuple t = *j;
				out << "  ";
				bool space = false;
				for(unsigned int i=0; i<temp.size(); i++)
				{
					if(temp[i].getBoolean() == false && doubles.count(temp[i].getName())==0)
					{
						out << temp[i].getName() << "='" << t[i] << "' ";
						doubles.insert(temp[i].getName());
						space = true;
					}
				}
				if(space)
				{
					out << endl;
				}
			}
	}
	
	
	
	void Print2(ofstream& out, Relation alter, Pred p)
	{
		
		vector<Param> temp = p.getParam();
		out << endl << p.getName() << "(";
		for(unsigned int i=0; i<temp.size(); i++)
		{
			if(temp[i].getBoolean())
			{
				out << "'"<<temp[i].getName()<<"'";
			}
			else
			{
				out << temp[i].getName();
			}
			
			if (i<(temp.size()-1))
			{
				out <<","; 
			}
		}
		out <<")? ";
		set<Tuple> mark = alter.getTuples();
		if (mark.size()>0)
		{
			out << "Yes(" << mark.size() << ")" << endl;
			
			out << "select" << endl;
			
			vector<Param> temp2 = alter.getScheme().getParam();		
			PrintParam(temp2, alter, out);
			
			out<<"project" << endl;			
			Project(temp, alter, out, temp2);
			
			out<<"rename" << endl;
			Rename(temp, alter, out);
			
			
			
		}
		else
		{
			out << "No" << endl;
		}
	}
	
	
	
	
	
	
	
	
	
	void Print1(ofstream& out)
	{
		out << "Scheme Evaluation" << endl << endl;
		out << "Fact Evaluation" << endl << endl;
		
		
		PrintFacts(out);
		out << endl;
		
		out << "Query Evaluation" << endl;
		
	}
	
	void PrintFacts(ofstream& out)
	{
		set<string> factnames;
		
		for (unsigned int i=0; i<Schematics.size(); i++)
		{
			factnames.insert(Schematics[i].getName());
		}
		
		for (set<string>::iterator it = factnames.begin(); it!=factnames.end(); it++)
		{
			string name = *it;
			
			out << name << endl;
			
			Relation r = data.getRelation(name);
			Pred p = r.getScheme();
			
			vector<Param> temp = p.getParam();
			
			PrintParam(temp, r, out);
			
		}
		
	}
	
	void PrintParam(vector<Param> temp, Relation r, ofstream& out)
	{
		
		set<Tuple> pairs = r.getTuples();
		
		for(set<Tuple>::iterator j=pairs.begin(); j!=pairs.end(); ++j)
			{
				
				Tuple t = *j;
				out<<"  ";
				for(unsigned int i=0; i<temp.size(); i++)
				{
					if(temp[i].getBoolean())
					{
						out << "'"<<temp[i].getName()<<"'"<< "='"<< t[i] << "' ";
					}
					else
					{
						out << temp[i].getName() << "='" << t[i] << "' ";
					}
				}
				out << endl;
				
			}
	}
	
};
