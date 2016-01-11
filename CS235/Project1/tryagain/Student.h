#pragma once


#include <iostream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

class Student
{

private:

	string id;
	string sname;
	string saddress;
	string snumber;

public:


	bool operator < (Student b) const
	{
		return this->id < b.id;
	}
	//sorts by id



	Student(string i, string n, string a, string nu) : id(i), sname(n), saddress(a), snumber(nu) {}

	string getID() const {return id;}
	string getName() const {return sname;}
	string getAddress() const {return saddress;}
	string getNumber() const {return snumber;}

	void setID(string id);
	void setName(string sname);
	void setAddress(string saddress);
	void setNumber(string snumber);
	
	string toString() const;
	
	//vector<Student> LoadFile(ifstream read, string id);



};
