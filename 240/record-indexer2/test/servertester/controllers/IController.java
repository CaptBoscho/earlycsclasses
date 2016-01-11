package servertester.controllers;

import server.communicator.ClientException;

public interface IController {

	void initialize();
	
	void operationSelected();
	
	void executeOperation();
}

