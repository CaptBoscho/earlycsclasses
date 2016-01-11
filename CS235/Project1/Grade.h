#pragma once


#include <iostream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

class Grade
{

private:

	string gid;
	string letter;
	string course;
	
public:

	Grade(string c, string i, string l) : course(c), gid(i), letter(l) {}

	string getID() const {return gid;}
	string getLetter() const {return letter;}
	string getCourse() const {return course;}

	void setGID(string gid);
	void setLetter(string letter);
	void setCourse(string course);
	
	
	string toString() const;
	
	bool operator < (Grade g) const {
    return gid < g.gid ||
      (gid == g.gid && course < g.course) ||
      (gid == g.gid && course == g.course && letter < g.letter);
  }
	



};

	
