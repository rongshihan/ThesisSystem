package edu.whu.web.impl;

import edu.whu.web.support.StaffControllerSupport;

public class DisaStaffByIDServlet extends StaffControllerSupport {

	@Override
	public String execute() throws Exception {
		this.update("disaStaffByID", "����");
		this.query();
		return "queryStaff";
	}

}
