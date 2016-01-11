#include <iostream>
#include <iomanip>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
#include <sstream>
#include <map>
#include "Student.h"
#include "Grade.h"

using namespace std;

vector<Student> LoadFile(ifstream& read, string id)
{
	vector<Student> studentbody;
	while (getline(read, id))
    {
        string sname;
        string saddress;
        string snumber;
        

        
        getline(read, sname);
        getline(read, saddress);
        getline(read, snumber);

        Student newbie(id,sname,saddress,snumber);


		studentbody.push_back(newbie);
		
    }
    
   return studentbody;
	
}

vector<Grade> LoadGrades(ifstream& readgrade, string course)
{
	vector<Grade> grades;
	while (getline(readgrade, course))
    {
        string gradeid;
        string gletter;
        
        getline(readgrade, gradeid);
        getline(readgrade, gletter);
     
        Grade newbster(course, gradeid, gletter);
        
		grades.push_back(newbster);
		
	
    }
    return grades;
}

/*vector<string> LoadQuery(ifstream& readquery)
{
	vector<string> ids;
	string idone;
	
	while(getline(readquery, idone))
	{
		ids.push_back(idone);
	}
	return ids;
}
*/
void WriteFile(ofstream& write, vector<Student> studentbody, vector<Grade> grades, vector<string> gpaparty)
{
	//int size = studentbody.size();
	if (studentbody.size()>0)
	{
		for (int i=0;i< studentbody.size(); i++)
		{
			string nam;
			string num;
			string add;
			string id;
      

			nam = studentbody[i].getName();
			id = studentbody[i].getID();
			num = studentbody[i].getNumber();
			add = studentbody[i].getAddress();

			write<< nam << endl;
			write<< id << endl;
			write << num << endl;
			write << add << endl;        
		}
    write << endl;
	}
	
    //int size2 = grades.size();
    
    if (grades.size()>0)
    {
		for (int i=0;i< grades.size(); i++)
		{
      
			string gcourse;
			string gletter;
			string gid;
           
			gid = grades[i].getID();
			gletter= grades[i].getLetter();
			gcourse= grades[i].getCourse();
        
			write << gid << "    " << gletter << "    " << gcourse << endl;
		}
	}
	write << endl;
	
	/*if (studentbody.size()>0)
	{
		//int sizetuanis = gpaparty.size();
		for (int i=0; i< gpaparty.size(); i++)
		{
			string gpaid;
			gpaid = gpaparty[i];
			write << gpaid << endl;
		}
	}*/
}

/*vector<string> GetGPA(vector<string>& ids, vector<Grade>& gra, vector<Student>& stu, map<string, double>& key)
{
	vector<string> gpaparty;
	if (stu.size()>0)
	{
		
		//int i = 0;
	
		int idsize = ids.size();
		string coolid;
		string firstID;
	
		for (int m = 0; m < ids.size(); m++)
		{
			coolid = ids[m];
			double total = 0;
			double GPA1 = 0;
			
		
			//int gradesize = gra.size();
			
			int counter = 0;
			
			if (gra.size()>0)
			{
		
				for (int i = 0; i< gra.size(); i++)
				{
					if (coolid == gra[i].getID())
					{
						total = total + key[gra[i].getLetter()];
						counter++;
					}
				}
	
				if (total > 0)
				{
					GPA1= total / counter;
				}
				
			
				
			}
			int c=0;
			while(coolid != stu[c].getID())
			{
				c++;
			
			}
			
			
			string nameparty = stu[c].getName();
			
			
			stringstream party;
			party << coolid << "    ";
			party << fixed << setprecision(2) << setfill('0') << GPA1;
			party << "    " << nameparty;
	
			gpaparty.push_back(party.str());
			
			
			
		}
		
	
	}	
	
	return gpaparty;
}
*/
double Gpaargh (string idone, vector<Grade> gra, map<string, double>& key)
{
	
	double total = 0;
	double GPA1 = 0;
	
	
	int counter = 0;
	if (gra.size()>0)
	{
		
		for (int i = 0; i< gra.size(); i++)
		{
			
			if (idone == gra[i].getID())
			{
				total = total + key[gra[i].getLetter()];
				counter++;
				
			}
		}
		
		if (total > 0)
		{
			GPA1= total / counter;
		}
	}
	return GPA1;			
}

int FindStu (string idone, vector<Student> stu) //finding student
{
	
	for (int i=0; i < stu.size(); i++)
	{
		if(idone == stu[i].getID())
		{
			return i;
		}
	}
	
	
	return -1;
	
}
	

int main (int argc, char* argv[])
{
    ifstream read;
    ofstream write;
    vector<Student> studentbody;
    vector<Grade> grades;
    vector<string> gpaparty;
    //vector<string> ids;
    ifstream readgrade;
    ofstream writegrade;
    ifstream readquery;
    ofstream writequery;
    
    map<string, double> grade_key;
		grade_key ["A"] = 4.0;
		grade_key ["A-"] = 3.7;
		grade_key ["B+"] = 3.4;
		grade_key ["B"] = 3.0;
		grade_key ["B-"] = 2.7;
		grade_key ["C+"] = 2.4;
		grade_key ["C"] = 2.0;
		grade_key ["C-"] = 1.7;
		grade_key ["D+"] = 1.4;
		grade_key ["D"] = 1.0;
		grade_key ["D-"] = 0.7;
		grade_key ["E"] = 0.0;

	read.open(argv[1]); //opening student
    string id;

    studentbody = LoadFile(read, id);
    
	read.close();
	
	
	sort(studentbody.begin(), studentbody.end());
	
	readgrade.open(argv[2]); //opening grade
	string course;
	grades = LoadGrades(readgrade, course);
	readgrade.close();
	
	
	sort(grades.begin(), grades.end());
	
	
	//ids = LoadQuery(readquery);
	
	//vector<string> ids;
	
	

	
	//gpaparty = GetGPA(ids, grades, studentbody,grade_key);

	
	write.open(argv[4]); //writing to outie
	WriteFile(write, studentbody, grades, gpaparty);
	
	
	string idone;
	readquery.open(argv[3]); //opening query
	while(getline(readquery, idone))
	{
		
		double gpadangit = 0;
		
		gpadangit = Gpaargh(idone, grades, grade_key);
		
		int pos = FindStu(idone, studentbody);
	
		
		
		if (pos>= 0)
		{
			
			write << idone << "    ";
			write << fixed << setprecision(2) << setfill('0') << gpadangit;
			write << "    " << studentbody[pos].getName() << endl;
		}
		
	}
	readquery.close();


    write.close();

	
	
    
    return 0;
}
