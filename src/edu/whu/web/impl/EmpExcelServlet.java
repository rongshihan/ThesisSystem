package edu.whu.web.impl;

import edu.whu.web.support.ExpExcelControllerSupport;

public class EmpExcelServlet extends ExpExcelControllerSupport 
{

	@Override
	public String execute() throws Exception 
	{
		this.update("addEmp", "���", "��ӳɹ�,��Ա�������:", "uid");
		return "queryExp";
	}

}
