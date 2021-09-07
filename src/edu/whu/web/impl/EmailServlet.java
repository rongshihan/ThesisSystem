package edu.whu.web.impl;

import edu.whu.web.support.StaffControllerSupport;

public class EmailServlet extends StaffControllerSupport {

	@Override
	public String execute() throws Exception
	{
		this.update("email", "É¾³ý");
		this.queryByID();
		return "queryExp";
	}
}
