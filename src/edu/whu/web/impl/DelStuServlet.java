package edu.whu.web.impl;

import edu.whu.web.support.StuControllerSupport;

public class DelStuServlet extends StuControllerSupport {

	@Override
	public String execute() throws Exception {
		this.update("deleteStu", "ɾ��");
		this.query();
		return "queryStu";
	}
}
