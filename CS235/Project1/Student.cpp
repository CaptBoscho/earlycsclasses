#include "Student.h"

using namespace std;


void Student::setID(string id) 
{
	this->id = id;
}

void Student::setAddress(string saddress)
{
	this->saddress = saddress;
}

void Student::setName(string sname)
{
	this->sname = sname;
}

void Student::setNumber(string snumber)
{
	this->snumber = snumber;
}

string Student::toString() const
{
	stringstream out;
    out << sname << endl;
    out << id << endl;
    out << snumber << endl;
    out << saddress << endl;

    return out.str();
    //combines strings on one line!
}
