package edu.whu.web.impl;

import edu.whu.web.support.StuControllerSupport;

public class AbleStuByIDServlet extends StuControllerSupport
{
	@Override
	public String execute() throws Exception 
	{
		this.update("ableStuByID","����");
		this.query();
		return "queryStu";
	}
}
