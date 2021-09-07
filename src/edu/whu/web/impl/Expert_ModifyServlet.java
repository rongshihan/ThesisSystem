package edu.whu.web.impl;

import java.util.Map;

import edu.whu.services.impl.B02ImplServices;
import edu.whu.web.support.B02ControllerSupport;

public class Expert_ModifyServlet extends B02ControllerSupport 
{
    private B02ImplServices B02ImplServices = new B02ImplServices();
    
    public Expert_ModifyServlet()
    {
		this.setServices(B02ImplServices);
	}
    
	@Override
	public String execute() throws Exception 
	{
		Map<String,String> dataMap=B02ImplServices.modify();
		if(dataMap!=null) 
		{
			this.saveAttribute("dataMap", dataMap);
		}
		else
		{
			this.saveAttribute("msg", "û�з������������ݣ�");
		}
		return "thesisDetails_e";
	}

}
