package edu.whu.web.impl;

import edu.whu.web.support.AdminControllerSupport;

public final class SecModifyServlet extends AdminControllerSupport
{
	@Override
	public String execute()throws Exception
	{
		this.update("modifySecretary","�޸�");
		this.query();
		return "secManage";
	}
}