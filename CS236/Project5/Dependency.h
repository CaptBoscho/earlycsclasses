#pragma once

#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <map>

#include "Node.h"




using namespace std;

class Dependency {
	
private:
	
	map<string,Node> Graph;
	int count = 1;
	map<string,Node> Back;


public:

	Dependency(){};
	~Dependency(){};
	
	void insertNode(Node n, string s)
	{
		Graph[s] = n;
	}
	
	Node getNode(string s)
	{
		return Graph[s];
	}
	
	void addAdj(string s, string add)
	{
		Graph[s].addAdjacency(add);
	}
	
	void PrintGraph(ofstream& out)
	{
		out <<"Dependency Graph" << endl;
		map<string,Node>::iterator it;
		for (it=Graph.begin(); it != Graph.end(); it++)
		{
			out << "  " << it->first << ": ";
			set<string> temp = (it->second).getAdj();
			
			set<string>::iterator k;
			for (k=temp.begin(); k!=temp.end(); k++)
			{
				out << *k << " ";
			}
			out << endl;
		}
		out << endl;
	}
	
	vector<string> Numbers(ofstream& out, int index, int rulesize)
	{
		DFSmain(out, index);
		
		string temp = "Q";
		temp += to_string(index+1);
		
		
		//set<string> ss = Graph[temp].getAdj();
		map<string,Node>::iterator k;
		for (k=Graph.begin(); k!=Graph.end(); k++)
		{
			if((k->second).getNumber()>0)
			{
				out <<"  "<< k->first <<": "<< (k->second).getNumber()<<endl;
			}
		}
		
		out << endl;
		
		out << "Rule Evaluation Order" << endl; 
		
		vector<string> order = REO(rulesize);
		
		for(unsigned int p=0; p<order.size(); p++)
		{
			out <<"  " << order[p] << endl;
		}
		out << endl;
		
		out << "Backward Edges" << endl;
		//isCyclic(temp, out);
		
		PrintBack(out);
		out << endl;
		return order;
	}
	
	void DFSmain(ofstream& out, int index)
	{
		map<string,Node>::iterator it;
		for (it=Graph.begin(); it != Graph.end(); it++) //sets the ponumbers to 0
		{
			(it->second).setNumber(0);
			(it->second).setMark(false);
			count =1;
		}
		Back.clear();
		
		out << endl<< endl << "Postorder Numbers" << endl;
		string temp = "Q";
		temp += to_string(index+1);
		
		DFS(temp, out);
		CheckBack();
	}
	
	void DFS(string id, ofstream& out)
	{
		Graph[id].setMark(true);
		set<string> ss = Graph[id].getAdj();
		if(ss.size() > 0)
		{
			set<string>::iterator k;
			for (k=ss.begin(); k!=ss.end(); k++)
			{
				if(!Graph[*k].getMark())
				{
					//Graph[*k].setMark(true); //marking as visited
					DFS(*k, out);
				}
				
				else if(Graph[*k].getNumber() == 0)
				{
					Backwards(id, Graph[*k].getName());
					
				}
			}
		}
	
		Graph[id].setNumber(count);
		count++;
		
		
	}
	
	void Backwards(string dad, string son)
	{
		//set<string> temp = Graph[son].getAdj();
		//if(temp.count(dad))
		//{
		
			/*if(!Back.count(dad))
			{
				Node n = Node(Graph[dad].getName());
			}*/
		Back[dad].addAdjacency(son);
		//}
	}
	
	void CheckBack()
	{
		map<string,Node>::iterator it;
		for (it=Back.begin(); it!=Back.end(); it++)
		{
			set<string> temp = (it->second).getAdj();
			
			set<string>::iterator k;
			for (k=temp.begin(); k!=temp.end(); k++)
			{
				if(Graph[*k].getNumber() < Graph[(it->first)].getNumber())
				{
					temp.erase(k);
				}
			}
			
			if(temp.size() == 0)
			{
				Back.erase(it);
			}
			else
			{
				(it->second).setAdjSet(temp);
			}
			
		}
	}
	
	void PrintBack(ofstream& out)
	{
		map<string,Node>::iterator it;
		for (it=Back.begin(); it!=Back.end(); it++)
		{
			out << "  " << it->first << ": ";
			set<string> temp = (it->second).getAdj();
			
			set<string>::iterator k;
			for (k=temp.begin(); k!=temp.end(); k++)
			{
				out << *k << " ";
			}
			out << endl;
		}
		
	}
	
	
	vector<string> REO(int s)
	{
		vector<string> order;
		for(unsigned int m=0; m<(s+1); m++)
		{
			order.push_back("x");
		}
		for(unsigned int i=1; i<(s+1); i++)
		{
			string rul = "R";
			rul += to_string(i);
			if(Graph[rul].getNumber()>0)
			{
				order[Graph[rul].getNumber()] = rul;
			}
		}
		
		vector<string> order2;
		for(unsigned int p=1; p<order.size(); p++)
		{
			if(order[p] != "x")
			{
				order2.push_back(order[p]);
			}
		}
		return order2;
	}
	
	
	int Backside()
	{
		return Back.size();
	}
	
	
	/*void isCyclic(string temp, ofstream& out)
	{
		set<string> st = Graph[temp].getAdj();
		
		set<string>::iterator it;
		for(it=st.begin(); it!=st.end(); it++)
		{
			if(Graph[temp].getNumber() <= Graph[*it].getNumber())
			{
				out <<"  "<<temp<<": " << *it << endl;
			}
			if(temp != *it)
			{
				isCyclic(*it, out);
			}
			return;
		}
	}*/
	
	

	
	
	
	
};
