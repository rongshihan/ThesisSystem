package edu.whu.web.impl;

import edu.whu.web.support.StuExcelControllerSupport;

public class StuExcelServlet extends StuExcelControllerSupport
{

	@Override
	public String execute() throws Exception 
	{
		this.update("addEmp", "���", "��ӳɹ�,��Ա�������:", "uid");
		return "queryStu";
	}

}
