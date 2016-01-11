#pragma once
#include "Fighters.h"
class Cleric :public Fighters
{

public:

	~Cleric(void);

	
	Cleric (string newName, int newHP, int newStrength, int newSpeed, int newMagic);
	void reset();

	/*
	Will have to reference the Fighters::regenerate();
	Cleric:
		Also increases a Cleric's current mana by an amount equal to one fifth of the 
		Cleric's magic.  This method must increase the Cleric's current mana by at 
		least one.  Do not allow the current mana to exceed the maximum mana.
	*/
	void regenerate();

		/*
		reset()

		Restores a fighter's current hit points to its maximum hit points.

		Robot:
		Also restores a Robot's current energy to its maximum value (which is 2 times its magic).
		Also resets a Robot's bonus damage to 0.

		Archer:
		Also resets an Archer's current speed to its original value.

		Cleric:
		Also restores a Cleric's current mana to its maximum value (which is 5 times its magic).
	*/

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
	int getDamage();

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
	bool useAbility();

	bool isSimplified();

private:
	int currMP, maxMP;
};