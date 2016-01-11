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
	vector<Rule> Rules;
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
		Rules = ping.getRules();
		createRelations();
		
		Print1(out);
		EvaluateRules(out);
		PrintFacts(out);
		out << "Query Evaluation" << endl;
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
	
	void putTuples(Pred& temp)
	{
		vector<Param> ps = temp.getParam();
		Tuple t;
		string cool;
		for(unsigned int i=0; i<ps.size(); i++)
		{
			cool = ps[i].getName();
			t.push_back(cool);
		}
		rel.addTuples(t);
		
	}
	
	void EvaluateRules(ofstream& out)
	{
		out << "Rule Evaluation" << endl << endl;
		int temp = 0;
		int count = 0;
		Rule ruler;
		Relation cousin;
		do
		{
			temp = data.Sizer();
			
			for (unsigned int i=0; i<Rules.size(); i++)
			{
				ruler = Rules[i];				
				Join(ruler, out);
				
			}
			count++;
		} while (temp != data.Sizer());
		
		out << endl << "Converged after "<<count<<" passes through the Rules." << endl << endl;
	}
	
	
	void Join(Rule& ruler, ofstream& out)
	{	
		vector<Pred> body = ruler.getBody();
		if (body.size()>1)
		{	
			Pred s1 = body[0];
			Pred header = ruler.getHead();
			Relation cousin = Relation(header.getName(), s1); //schemer
			Pred s2;
			Pred schemer;
			vector<Param> temp;
			Relation r1 = data.getRelation(s1.getName());
			Relation r2;
			Relation r1a = Select(s1, r1, out);;
			Relation r2a;
			set<Tuple> m;
			set<Tuple> n;
			for(unsigned int q=1; q<body.size(); q++) //loop
			{
				s2 = body[q];
				schemer = makesch(s1, s2); //schemer
				s1 = schemer;
				
				temp = s1.getParam();
				
				cousin.setScheme(s1);
				r2 = data.getRelation(s2.getName());
				r2a = Select(s2, r2, out);
				
				if (q==1)
				{
					m = r1a.getTuples(); //can fix
				}
				else
				{
					m = cousin.getTuples(); //can fix
				}
				n = r2a.getTuples(); // can fix
				cousin = Join2(m, n, s1, s2, out, cousin); //can fix
				
			}
			set<Tuple> david = cousin.getTuples();
			set<Tuple> nate;
			Tuple t;
			set<Tuple>::iterator end = david.end();
			for(set<Tuple>::iterator the = david.begin(); the != end; the++)
			{
				t = *the;
				if (t.size()== s1.getParam().size())
				{
					nate.insert(t);
				}
			}
			cousin.setTuples(nate);
			ProjectRules(cousin, header, out, ruler);	
		}
		else
		{
			Pred s1 = body[0];
			Pred header = ruler.getHead();
			Relation cousin = Relation(header.getName(), s1);
			Relation r1 = data.getRelation(s1.getName());
			Relation r1a = Select(s1, r1, out);
			set<Tuple> m = r1a.getTuples();
			Tuple t1;
			for(set<Tuple>::iterator it = m.begin(); it != m.end(); it++)
			{
				t1 = *it;
				
				cousin.addTuples(t1);
			}
			ProjectRules(cousin, header, out, ruler);
		}
	}
	
	Relation Join2 (set<Tuple>& m, set<Tuple>& n, Pred& s1, Pred& s2, ofstream& out, Relation& cousin)
	{
		Tuple t1;
		Tuple t2;
		Tuple t;
		for(set<Tuple>::iterator it = m.begin(); it != m.end(); it++)
		{
			for(set<Tuple>::iterator the = n.begin(); the != n.end(); the++)
			{
				t1 = *it;
				t2 = *the;
				if(Joinable(t1, t2, s1, s2))
				{
					t = makeTup(t1, t2, s1, s2, out);
					cousin.addTuples(t);
				}
			}
		}
		return cousin;
	}
	
	void ProjectRules(Relation& cousin, Pred& header, ofstream& out, Rule& ruler)
	{
		//Relation r = data.getRelation(header.getName());//add the stuff instead of taking it out, adding, replacing
		string name = header.getName();
		Relation r1 = Relation(name, header);
		vector<int> indexes;
		set<Tuple> pairs = cousin.getTuples();
		set<Tuple> results;
		vector<Param> rulename = header.getParam();
		vector<Param> rulelist = cousin.getScheme().getParam();
		for (unsigned int i=0; i<rulename.size(); i++)
		{
			for(unsigned int j=0; j<rulelist.size(); j++)
			{
				if (rulelist[j].getName() == rulename[i].getName())
				{
					indexes.push_back(j);
				}
			}
			
		}
		
		Tuple t;
		Tuple nt;
		//set<Tuple> ss;
		int temp;
		//set<Tuple> st;
		for(set<Tuple>::iterator j=pairs.begin(); j!=pairs.end(); ++j)
		{
			
			t = *j;
			
			nt = ProjectRules2(t, header, indexes, out);	//here
			
			//ss = r.getTuples();
			temp = data.getAmountTuples(name);
			data.addTupler(name, nt);
			//r.addTuples(nt);
			//st = r.getTuples();
			if (temp != data.getAmountTuples(name))
			{
				r1.addTuples(nt);
			}		
		}
		PrintRuleStep(out, r1, ruler); //trying print here
		//data.addRelation(r);
	}
	
	Tuple ProjectRules2(Tuple& t, Pred& header, vector<int>& indexes, ofstream& out)
	{
		Tuple nt;
		for (unsigned int i=0; i<indexes.size(); i++)
		{
		
			nt.push_back(t[indexes[i]]);
			
		}
		return nt;
	}
	
	Tuple makeTup(Tuple& t1, Tuple& t2, Pred& s1, Pred& s2, ofstream& out)
	{
		Tuple t;
		for (unsigned int p=0; p<t1.size(); p++)
		{
			t.push_back(t1[p]);
		}
		vector<Param> one = s1.getParam();
		vector<Param> two = s2.getParam();
		bool notthere;
		for (unsigned int j=0; j<t2.size(); j++)
		{
			notthere = true;
			for (unsigned int i=0; i<t1.size(); i++)
			{
				if (two[j].getName() == one[i].getName())
				{
					notthere = false;
				}
			}
			if(notthere)
			{
				t.push_back(t2[j]);
			}
		}
		return t;
	}
	
	bool Joinable(Tuple& t1, Tuple& t2, Pred& s1, Pred& s2)
	{
		vector<Param> one = s1.getParam();
		vector<Param> two = s2.getParam();
		for (unsigned int i=0; i<t1.size(); i++)
		{
			for (unsigned int j=0; j<t2.size(); j++)
			{
				if(one[i].getName()==two[j].getName() && t1[i] != t2[j])
				{
					return false; 
				}
			}
		}
		
		return true;
	}
	
	
	Pred makesch(Pred& s1, Pred& s2)
	{
		vector<Param> one = s1.getParam();
		vector<Param> two = s2.getParam();
		bool notthere;
		for (unsigned int i=0; i<two.size(); i++)
		{
			notthere = true;
			for (unsigned int j=0; j<one.size(); j++)
			{
				if (one[j].getName() == two[i].getName())
				{
					notthere = false;
				}
			}
			
			if (notthere)
			{
				s1.AddParam(two[i]);
			}
		}
		return s1;
	}
	
	
	
	
	void EvaluateQueries(ofstream& out)
	{
		string head;
		Relation r;
		Relation alter;
		
		for (unsigned int i=0; i<Querify.size(); i++)
		{
			head = Querify[i].getName();
			//search relationship map
			r = data.getRelation(head);
			alter = Select(Querify[i], r, out);
			Print2(out, alter, Querify[i]);
		}
	}
	
	
	bool Select2(vector<Param>& questions, bool& add, Tuple& t, int i)
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
	
	Relation Select(Pred& q, Relation& r, ofstream& out)
	{
		vector<Param> questions = q.getParam();
		Relation alter = Relation(r.getName(), r.getScheme());
		set<Tuple> pair = r.getTuples();
		set<Tuple> pair2;		
		map<string,int> tempmap;
		Tuple t;
		
		for( set<Tuple>::iterator it = pair.begin(); it != pair.end(); it++)
		{
			bool add = true;
			
			t = *it;
			
			for (unsigned int i=0; i<questions.size(); i++)
			{
				add = Select2(questions, add, t, i);
				
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
			tempmap.clear();//clear tempmap
		}
		alter.setTuples(pair2);
		return alter;
	}
	
	void Project2(Tuple& t, vector<Param>& temp, vector<Param>& temp2, ofstream& out)
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
	
	void Project(vector<Param>& temp, Relation& r, ofstream& out, vector<Param>& temp2)
	{
		set<Tuple> pairs = r.getTuples();
		Tuple t;
		
		for(set<Tuple>::iterator j=pairs.begin(); j!=pairs.end(); ++j)
			{
				t = *j;
				Project2(t, temp, temp2, out);				
			}
	}
	
	void Rename(vector<Param>& temp, Relation& r, ofstream& out)
	{
		set<Tuple> pairs = r.getTuples();
		set<string> doubles;
		Tuple t;
		
		for(set<Tuple>::iterator j=pairs.begin(); j!=pairs.end(); ++j)
		{
			
			t = *j;
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
			doubles.clear(); //clear set doubles
		}
	}
	
	
	
	void Print2(ofstream& out, Relation& alter, Pred& p)
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
		
		//out << "Query Evaluation" << endl;
		
	}
	
	void PrintFacts(ofstream& out)
	{
		set<string> factnames;
		
		for (unsigned int i=0; i<Schematics.size(); i++)
		{
			factnames.insert(Schematics[i].getName());
		}
		
		string name;
		Relation r;
		Pred p;
		vector<Param> temp;
		for (set<string>::iterator it = factnames.begin(); it!=factnames.end(); it++)
		{
			name = *it;
			
			out << name << endl;
			
			r = data.getRelation(name);
			p = r.getScheme();
			
			temp = p.getParam();
			
			PrintParam(temp, r, out);
			out << endl; //trying here
		}
		
	}
	
	void PrintParam(vector<Param>& temp, Relation& r, ofstream& out)
	{
		
		set<Tuple> pairs = r.getTuples();
		Tuple t;
		
		for(set<Tuple>::iterator j=pairs.begin(); j!=pairs.end(); ++j)
			{
				
				t = *j;
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
	
	void PrintRuleStep(ofstream& out, Relation& r, Rule& ruler)
	{
		Pred temporary = ruler.getHead();
		out << temporary.getName() << "(";
		
		vector<Param> header = temporary.getParam();
		
		toString(header, out);
		
		out << ") :- ";
		
		
		vector<Pred> listpred = ruler.getBody();
		
		PrintRidk(out, listpred);
		
		out << endl;
		vector<Param> cmon;
		for (unsigned int k=0; k<Schematics.size(); k++)
		{
			if (Schematics[k].getName() == temporary.getName())
			{
				cmon = Schematics[k].getParam();
			}
		}
		PrintParam(cmon, r, out);
	}
	
	void PrintRidk(ofstream& out, vector<Pred>& listpred)
	{
		vector<Param> temp;
		for(unsigned int h=0; h<listpred.size(); h++) 
		{
			temp = listpred[h].getParam();
			out << listpred[h].getName() << "(";
		
			
			for (unsigned int i=0; i<temp.size(); i++)
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
			
			out << ")";
			if (h < listpred.size()-1)
			{
				out <<",";
			}
			
			
		}
	}

	void PrintRules(ofstream& out, Rule& ruler)
	{		
			
			Pred temporary = ruler.getHead();
			Relation r = data.getRelation(temporary.getName());
			out << temporary.getName() << "(";
			
			vector<Param> header = temporary.getParam();
			
			toString(header, out);
			
			out << ") :- ";
			
			
			vector<Pred> listpred = ruler.getBody();
			vector<Param> temp;
			
			for(unsigned int h=0; h<listpred.size(); h++) 
			{
				temp = listpred[h].getParam();
				out << listpred[h].getName() << "(";
			
				
				for (unsigned int i=0; i<temp.size(); i++)
				{
					out << temp[i].getName();
					if (i < temp.size()-1)
					{
						out << ",";
					}
				}
				
				out << ")";
				if (h < listpred.size()-1)
				{
					out <<",";
				}
				
				
			}
			out << endl;
			PrintParam(header, r, out);
			
		
		
	}


	
	void toString(vector<Param>& header, ofstream& out)
	{
		for(unsigned int j=0; j<header.size(); j++)
		{
			if (header[j].getBoolean() == true)
			{
				out << "'" << header[j].getName() << "'";
				
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
	
	
};
