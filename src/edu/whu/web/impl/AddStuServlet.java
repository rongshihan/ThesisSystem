package edu.whu.web.impl;

import edu.whu.web.support.StuControllerSupport;

public class AddStuServlet extends StuControllerSupport 
{

	@Override
	public String execute() throws Exception 
	{
		this.update("addStu", "���", "��ӳɹ�,��ѧ�������:", "uid");
		return "addStu";
	}

}
