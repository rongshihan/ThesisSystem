package edu.whu.web.impl;

import edu.whu.web.support.StaffControllerSupport;

public class AddStaffServlet extends StaffControllerSupport 
{

	@Override
	public String execute() throws Exception 
	{
		this.update("addStaff", "���", "��ӳɹ�,���û������:", "username");
		return "addStaff";
	}

}
