#include "Cleric.h"
#include <iostream>
#include <string>
using namespace std;


Cleric::~Cleric(void)
{
}

Cleric::Cleric(string newName, int newHP, int newStrength, int newSpeed, int newMagic):Fighters(newName, newHP, newStrength, newSpeed, newMagic)
{
	maxMP = newMagic*5;
}

void Cleric::reset()
{
	HP = max_hp;
	currMP = maxMP;
}

bool Cleric::isSimplified()
{
	return false;
}


/*
reset()

Restores a fighter's current hit points to its maximum hit points.

Cleric:
Also restores a Cleric's current mana to its maximum value (which is 5 times its magic).
*/

/*
getDamage()

Returns the amount of damage a fighter will deal.

Cleric:
This value is equal to the Cleric's magic.
*/
int Cleric::getDamage()
{
	return Magic;
}

/*
useAbility()
			
Attempts to perform a special ability based on the type of fighter.  The 
fighter will attempt to use this special ability just prior to attacking 
every turn.

Cleric: Healing Light
Increases the Cleric's current hit points by an amount equal to one third of its magic.
Can only be used if the Cleric has at least [CLERIC_ABILITY_COST] mana.
Decreases the Cleric's current mana by [CLERIC_ABILITY_COST] when used.
Cleric Note:
This ability, when successful, must increase the Cleric's current hit points 
by at least one.  Do not allow the current hit points to exceed the maximum 
hit points.

Return true if the ability was used; false otherwise.
*/
bool Cleric::useAbility()
{
	int magic3= (Magic/3);
	if (currMP > CLERIC_ABILITY_COST)
	{
		if (magic3 < 1)
		{
			magic3 = 1;
		}
		
		HP = HP + magic3;
		currMP = currMP - CLERIC_ABILITY_COST;

		return true;
	}
	return false;
}


	/*
	Will have to reference the Fighters::regenerate();
	Cleric:
		Also increases a Cleric's current mana by an amount equal to one fifth of the 
		Cleric's magic.  This method must increase the Cleric's current mana by at 
		least one.  Do not allow the current mana to exceed the maximum mana.
	*/
void Cleric::regenerate()
{
	Fighters::regenerate();
	int add = (Magic/5);
	if (add < 1)
	{
		add = 1;
	}
	currMP = currMP + add;
}
