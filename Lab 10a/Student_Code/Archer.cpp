#include "Archer.h"


Archer::~Archer(void)
{
}

Archer::Archer(string newName, int newHP, int newStrength, int newSpeed, int newMagic):Fighters(newName, newHP, newStrength, newSpeed, newMagic)
{
	original_speed = newSpeed;
}

void Archer::reset()
{
	HP = max_hp;
	Speed = original_speed;
}

/*
	getDamage()

	Returns the amount of damage a fighter will deal.

	Robot:
	This value is equal to the Robot's strength plus any additional damage added for having just used its special ability.

	Archer:
	This value is equal to the Archer's speed.

	Cleric:
	This value is equal to the Cleric's magic.
*/
int Archer::getDamage()
{
	int damage = Speed;
	return damage;
}

/*
	useAbility()
			
	Attempts to perform a special ability based on the type of fighter.  The 
	fighter will attempt to use this special ability just prior to attacking 
	every turn.

	

	Archer: Quickstep
	Increases the Archer's speed by one point each time the ability is used.
	This bonus lasts until the reset() method is used.
	This ability always works; there is no maximum bonus speed.


	Return true if the ability was used; false otherwise.
*/
bool Archer::useAbility()
{
	Speed++;
	return true;
}

bool Archer::isSimplified()
{
	return false;
}