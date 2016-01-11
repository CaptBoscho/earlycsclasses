#pragma once
#include "Fighters.h"
#include <string>
#include <iostream>
#include <vector>
using namespace std;


Fighters::~Fighters(void)
{
}

Fighters::Fighters(string newName, int newHP, int newStrength, int newSpeed, int newMagic)
{
	Name = newName;
	HP = newHP;
	Strength = newStrength;
	Speed = newSpeed;
	Magic = newMagic;
	max_hp = newHP;
}

	//etc.
string Fighters::getName()
{
	return Name;
}

int Fighters::getMaximumHP()
{
	return max_hp;
}

/*
		takeDamage(int)

		Reduces the fighter's current hit points by an amount equal to the given 
		damage minus one fourth of the fighter's speed.  This method must reduce 
		the fighter's current hit points by at least one.  It is acceptable for 
		this method to give the fighter negative current hit points.

		Examples:
			damage=10, speed=7		=> damage_taken=9
			damage=10, speed=9		=> damage_taken=8
			damage=10, speed=50		=> damage_taken=1
	*/
void Fighters::takeDamage(int damage)
{
	int temp = damage - (Speed/4);
	if (temp < 1)
	{
		HP--;
	}
	else
	{
		HP -= temp;// HP !> maxHP
	}
}


/*
	getCurrentHP()

	Returns the current hit points of this fighter.
*/
int Fighters::getCurrentHP()
{
	return HP;
}

/*
	getStrength()

	Returns the strength stat of this fighter.
*/
int Fighters::getStrength()
{
	return Strength;
}

/*
	getSpeed()

	Returns the speed stat of this fighter.
*/
int Fighters::getSpeed()
{
	return Speed;
}

/*
	getMagic()

	Returns the magic stat of this fighter.
*/
int Fighters::getMagic()
{
	return Magic;
}

/*
	regenerate()

	Increases the fighter's current hit points by an amount equal to one sixth of
	the fighter's strength.  This method must increase the fighter's current hit 
	points by at least one.  Do not allow the current hit points to exceed the 
	maximum hit points.

	Cleric:
	Also increases a Cleric's current mana by an amount equal to one fifth of the 
	Cleric's magic.  This method must increase the Cleric's current mana by at 
	least one.  Do not allow the current mana to exceed the maximum mana.
*/
void Fighters::regenerate()
{
	int temp = HP + (Strength/6);
	if (temp < 1)
	{
		HP++;
	}
	else
	{
		HP = temp;
	}

	if (HP > max_hp)
	{
		HP = max_hp;
	}
}
