package edu.whu.web.impl;

import edu.whu.web.support.StaffControllerSupport;

public class EmailServlet extends StaffControllerSupport {

	@Override
	public String execute() throws Exception
	{
		this.update("email", "ɾ��");
		this.queryByID();
		return "queryExp";
	}
}
