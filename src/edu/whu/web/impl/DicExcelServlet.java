package edu.whu.web.impl;

import edu.whu.web.support.DicExcelControllerSupport;

public class DicExcelServlet extends DicExcelControllerSupport 
{

	@Override
	public String execute() throws Exception 
	{
		this.update("addEmp", "���", "��ӳɹ�,��Ա�������:", "uid");
		return "dicResult";
	}

}
