package edu.whu.web.impl;

import edu.whu.web.support.AdminControllerSupport;

public final class SecAddServlet extends AdminControllerSupport
{
	@Override
	public String execute()throws Exception
	{
		this.update("secAdd","���");
		this.query();
		return "secManage";
	}
}
