package edu.whu.web.impl;

import edu.whu.web.support.FinancialControllerSupport;

public final class FinancialAddServlet extends FinancialControllerSupport
{
	@Override
	public String execute()throws Exception
	{
		this.update("financialAdd","���");
		query();
		return "outputExcel";
	}
}
