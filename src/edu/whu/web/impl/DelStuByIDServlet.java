package edu.whu.web.impl;

import edu.whu.web.support.StuControllerSupport;

public class DelStuByIDServlet extends StuControllerSupport {

	@Override
	public String execute() throws Exception {
		this.update("deleteStuByID","ɾ��");
		this.query();
		return "queryStu";
	}

}
