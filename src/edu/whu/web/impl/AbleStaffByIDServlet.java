package edu.whu.web.impl;

import edu.whu.web.support.StaffControllerSupport;

public class AbleStaffByIDServlet extends StaffControllerSupport {

	@Override
	public String execute() throws Exception {
		this.update("ableStaffByID","����");
		this.query();
		return "queryStaff";
	}

}
