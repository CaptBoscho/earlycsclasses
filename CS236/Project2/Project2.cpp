#include <iostream>
#include <array>
#include <string>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>

#include "Token.h"
#include "Scanner.h"
#include "Parser.h"

using namespace std;

int main (int argc, char* argv[])
{
	ifstream in;
	ofstream out;
	
	
	in.open(argv[1]); 
	out.open(argv[2]);
    
    Parser run;
    
	run.RunParse(in , out);
	
    in.close();
    out.close();
	
	
	return 0;
} 
