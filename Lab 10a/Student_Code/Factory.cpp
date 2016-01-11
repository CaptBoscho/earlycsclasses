#include "Factory.h"
#include "Arena.h"
#include "ArenaInterface.h"
//Add #include statements here as needed

//Unlike every other document, you are allowed to modify this document slightly

ArenaInterface* Factory::createArena()
{
	return new Arena();//Modify this line
}