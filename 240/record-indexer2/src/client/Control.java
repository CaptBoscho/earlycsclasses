package client;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class Control implements IControl{

private IView _view;
	
	public Control() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	public void initialize() {
//		try {
//			getView().setHost(InetAddress.getLocalHost().getHostName());
//		} catch (UnknownHostException e) {
			getView().setHost("localhost");
		//}
		getView().setPort("40000");
		//operationSelected();
		System.out.println("controller:" + getView().getPort());
	}

	@Override
	public void operationSelected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeOperation() {
		// TODO Auto-generated method stub
		
	}
}
