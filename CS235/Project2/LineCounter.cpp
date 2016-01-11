#include "LineCounter.h"

using namespace std;

LineCounter::LineCounter()
{
	word = "";
	number= 0;
}

LineCounter::LineCounter(string w, int n)
{
	word = w;
	number = n;
}

void LineCounter::setWord(string word) 
{
	this->word = word;
}

void LineCounter::setNumber(int number)
{
	this->number = number;
}
