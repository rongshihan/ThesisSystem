package edu.whu.web.impl;

import java.util.Map;

import edu.whu.services.impl.B01ImplServices;
import edu.whu.web.support.UserControllerSupport;

public class Student_ThesisServlet extends UserControllerSupport
{
	private B01ImplServices b01ImplServices = new B01ImplServices();
	
	public Student_ThesisServlet()
	{
		this.setServices(b01ImplServices);
	}
	@Override
	public String execute() throws Exception 
	{
		Map<String,String> dataMap=b01ImplServices.stuquery();
		
		if(dataMap!=null)
		{
			this.saveAttribute("dataMap", dataMap);
		}
		else 
		{
			this.saveAttribute("msg", "��ѯʧ��");
		}
		return "studentThesis";
	}

}
