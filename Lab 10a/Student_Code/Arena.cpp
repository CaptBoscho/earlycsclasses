#include "Arena.h"
#include "Archer.h"
#include "Robot.h"
#include "Cleric.h"
#include "Fighters.h"
#include <sstream>

using namespace std;
bool Arena::addFighter(string info)
{
	string newName;
	char Type;
	int HP;
	int Strength;
	int Speed;
	int Magic;

	stringstream buddy;
	buddy << info;
	buddy >> newName;
	if(buddy.fail())
	{
		return false;
	}

	for (int i=0; i<myfighters.size(); i++)
	{
		if (myfighters[i]->getName() == newName)
		{
			cout << "You already have that Name. I'm sorry." << endl;
			return false;
		}
	}

	buddy >> Type;
	if(buddy.fail())
	{
		return false;
	}

	buddy >> HP;
	if(buddy.fail())
	{
		return false;
	}

	buddy >> Strength;
	if(buddy.fail())
	{
		return false;
	}

	buddy >> Speed;
	if(buddy.fail())
	{
		return false;
	}

	buddy >> Magic;
	if(buddy.fail())
	{
		return false;
	}

	if (!buddy.eof())
	{
		return false;
	}
	
	//making it what type
	if (Type == 'R')
	{
		myfighters.push_back(new Robot(newName, HP, Strength, Speed, Magic));
		return true;
	}

	if (Type == 'A')
	{
		myfighters.push_back(new Archer(newName, HP, Strength, Speed, Magic));
		return true;
	}

	if (Type == 'C')
	{
		myfighters.push_back(new Cleric(newName, HP, Strength, Speed, Magic));
		return true;
	}

	return true;
}

bool Arena::removeFighter(string oldname)
{
	for (int i=0; i< myfighters.size(); i++)
	{
		if (myfighters[i]->getName() == oldname)
		{
			myfighters.erase(myfighters.begin()+i);
			return true;
		}
	}
	return false;
}

FighterInterface* Arena::getFighter(string fightername)
{
	for (int i=0; i< myfighters.size(); i++)
	{
		if (myfighters[i]->getName() == fightername)
		{
			return myfighters[i];
		}
	}
	return NULL;
}

int Arena::getSize()
{
	return myfighters.size();
}