package edu.whu.web.impl;

import edu.whu.web.support.UserControllerSupport;

public class Submit_PasswordServlet extends UserControllerSupport 
{

	@Override
	public String execute() throws Exception 
	{
		this.update("modify", "�޸�");
		return "passwordChange";
	}

}
