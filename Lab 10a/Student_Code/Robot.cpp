#include "Robot.h"
#include <cmath>



Robot::~Robot(void)
{
}

Robot::Robot(string newName, int newHP, int newStrength, int newSpeed, int newMagic):Fighters(newName, newHP, newStrength, newSpeed, newMagic)
{
	curr_energy = 2*newMagic;
	bonus_damage=0;
}


void Robot::reset()
{
	HP = max_hp;
	curr_energy = 2 * Magic;
	bonus_damage = 0;
	max_energy = 20;	
}


	/*
	reset()

	Restores a fighter's current hit points to its maximum hit points.

	Robot:
	Also restores a Robot's current energy to its maximum value (which is 2 times its magic).
	Also resets a Robot's bonus damage to 0.

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
int Robot::getDamage()
{
	int damage = Strength + bonus_damage;//Add additional damage for having just used its special ability
	bonus_damage=0;
	return damage;
}

/*
	useAbility()
			
	Attempts to perform a special ability based on the type of fighter.  The 
	fighter will attempt to use this special ability just prior to attacking 
	every turn.

	Robot: Shockwave Punch
	Adds bonus damage to the Robot's next attack (and only its next attack) equal to (strength * ((current_energy/maximum_energy)^4)).
	Can only be used if the Robot has at least [ROBOT_ABILITY_COST] energy.
	Decreases the Robot's current energy by [ROBOT_ABILITY_COST] (after calculating the additional damage) when used.
		Examples:
		strength=20, current_energy=20, maximum_energy=20		=> bonus_damage=20
		strength=20, current_energy=15, maximum_energy=20		=> bonus_damage=6
		strength=20, current_energy=10, maximum_energy=20		=> bonus_damage=1
		strength=20, current_energy=5,  maximum_energy=20		=> bonus_damage=0
	Robot Note:
	The exponent part of the Robot's added damage must be calculated using a 
	recursive power function of your own creation.  In addition, the bonus damage 
	formula should be computed using double arithmetic, and only the final result 
	should be cast into an integer.

	Return true if the ability was used; false otherwise.
*/
bool Robot::useAbility()
{
	double temp_current = curr_energy;
	double temp_max = max_energy;
	double temp_bonus;
	double temp_strength=Strength;
	if (curr_energy>ROBOT_ABILITY_COST)
	{
		temp_bonus = (temp_strength * (pow((temp_current/temp_max),4)));
		bonus_damage = temp_bonus;
		curr_energy = curr_energy - ROBOT_ABILITY_COST;
		return true;
	}
	return false;
}

bool Robot::isSimplified()
{
	return false;
}