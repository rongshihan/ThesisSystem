package edu.whu.web.impl;
import edu.whu.web.support.StuDistributeControllerSupport;

public class StuDistributeServlet extends StuDistributeControllerSupport
{
	@Override
	public String execute() throws Exception
	{
		this.update("distributeStu","ѧ������");
		query();
		return "studentDistribute";
	}
}
