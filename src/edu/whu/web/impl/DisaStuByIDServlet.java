package edu.whu.web.impl;

import edu.whu.web.support.StuControllerSupport;

public class DisaStuByIDServlet extends StuControllerSupport {

	@Override
	public String execute() throws Exception {
		this.update("disableStuByID", "����");
		this.query();
		return "queryStu";
	}

}
