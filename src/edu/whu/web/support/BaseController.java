package edu.whu.web.support;

import java.util.Map;

public interface BaseController {
	//dto��װ
	void setDto(Map<String,Object> dto);
	
    String execute()throws Exception;
    
    Map<String,Object> getAttribute();
}
