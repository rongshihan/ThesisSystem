package edu.whu.web.impl;

import edu.whu.web.support.TeaExcelControllerSupport;

public class TeaExcelServlet extends TeaExcelControllerSupport 
{

	@Override
	public String execute() throws Exception 
	{
		this.update("addEmp", "���", "��ӳɹ�,��Ա�������:", "uid");
		return "queryTea";
	}

}
