#include <iostream>
#include <set>
#include <iomanip>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
#include <sstream>
#include <cmath>

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
		if (word.size() > 3)
		{
			word = LowerandStuff(word);
			dictionary.insert(word);
		}
	}
	
	return dictionary;
}

vector<vector<string> > LoadBoard(ifstream& read, string tileline)
{
	vector<vector<string> > board;
	vector<string> tiles;

	while (read>>tileline)
	{	
		tileline = LowerandStuff(tileline);
		tiles.push_back(tileline);	
	}
	
	int n = tiles.size();
	int r = sqrt(n);
	
	for (int i=0; i<n; i++)
	{
		vector<string> rows;		
		int count = 0;
		
		while (count < r)
		{
			rows.push_back(tiles[i]);
			
			count++;
			i++;
		}
		board.push_back(rows);
		i--;
	}
	
	/*for (int q=0; q<board.size(); q++)//checking it out
		{
			for (int p=0; p<board[q].size(); p++)
			{
				cout << board[q][p]<< " ";
			}
			cout << endl;
		}*/
		
	return board;
}

void Printing(ofstream& write, vector<vector<string> >& board, set<string>& ws)
{
	for (unsigned int q=0; q<board.size(); q++)//checking it out
	{
		for (unsigned int p=0; p<board[q].size(); p++)
		{
			write << board[q][p]<< " ";
		}
		write << endl;
	}
	
	//write dic for fun
	
	write <<  endl;
	
	
	set<string>::iterator it;
	for (it = ws.begin(); it!= ws.end(); ++it)
	{
		
		write << *it<< endl;
	}
}

bool BaseStatements(vector<vector<string> >& board, set<string>& dic, set<string>& ws, int r, int c, string word, set<pair<int, int> >& sc)
{
	//base cases
	if (r<0 || c<0 || r>= board.size() || c>= board.size())
	{
		return false;
	}
	
	set<string>::iterator w;
	w = dic.lower_bound(word);
	string checker;
	if(w!=dic.end())
	{	
		checker=*w;
		checker=checker.substr(0,word.size());
	}
	else 
	{
		return false;
	}
	
	
	
	if (word != checker)
	{
		return false;
	}
	
	pair <int, int> space;
	space = make_pair(r, c);	
	const bool in = sc.find(space) != sc.end();
	if(in== true)
	{
		return false;
	}
	

	return true;
}

void FindWord(vector<vector<string> >& board, set<string>& dic, set<string>& ws, int r, int c, string word, set<pair<int, int> > &sc)
{

	
	bool check = BaseStatements(board, dic, ws, r, c, word, sc);
	if (check == true)
	{
		pair <int, int> space;
		space = make_pair(r, c);
		sc.insert(space);
		word = word + board[r][c];
		const bool is_in = dic.find(word) != dic.end();
		if(is_in== true)//add to set
		{
			ws.insert(word);		
		}
	
		FindWord(board, dic, ws, r+1, c+1, word, sc);
		FindWord(board, dic, ws, r-1, c+1, word, sc);
		FindWord(board, dic, ws, r, c+1, word, sc);
		FindWord(board, dic, ws, r+1, c-1, word, sc);
		FindWord(board, dic, ws, r+1, c, word, sc);
		FindWord(board, dic, ws, r-1, c, word, sc);
		FindWord(board, dic, ws, r-1, c-1, word, sc);
		FindWord(board, dic, ws, r, c-1, word, sc);
	
		sc.erase(space);
		
		
	}
	//return ws;
}

int main (int argc, char* argv[])
{
	ifstream readdic;
	set<string> dictionary;
	ifstream readboard;
	vector<vector<string> > boggleboard;
	ofstream write;
	set<string> ws;
	
	readdic.open(argv[1]); 
    string word;
    dictionary = LoadDic(readdic, word); 
    readdic.close();
	
	readboard.open(argv[2]);
	string tline;
	boggleboard = LoadBoard(readboard, tline);
	readboard.close();
	
	for (unsigned int r=0; r<boggleboard.size(); r++)
	{
		for(unsigned int c=0; c<boggleboard[r].size(); c++)
		{
			//string w = boggleboard[r][c];
			set<pair<int, int> > sc;
			string word;
			FindWord(boggleboard, dictionary, ws, r, c, word, sc);
		}
	}
		
	
	write.open(argv[3]);
	Printing(write, boggleboard, ws);
	write.close();
	
	return 0;
}
