#include "Grade.h"

using namespace std;


void Grade::setGID(string gid) 
{
	this->gid = gid;
}

void Grade::setLetter(string letter)
{
	this->letter = letter;
}

void Grade::setCourse(string course)
{
	this->course = course;
}


string Grade::toString() const
{
	stringstream out;
    out << gid << endl;
    out << letter << endl;
    out << course << endl;

    return out.str();
    //combines strings on one line!
}
