package edu.whu.web.support;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.whu.services.support.BaseServices;

public abstract class BaseControllerSupport implements BaseController{
	
	/**
	 * ��װ���з���
	 */
	
	/*****************************************
	 * 	        ҵ���߼�������ܹ�ע��
	 *****************************************/
	private BaseServices services=null;
	
	/**
	 * ����ͨ���÷���,ΪServices�����������õľ��������
	 * @param services
	 */
	
	protected void setServices(BaseServices services) {
		this.services=services;
	}
	
	protected BaseServices getServices() {
		return this.services;
	}
	
	/*****************************************
	 * 	        ����ҵ�����̷�װ
	 *****************************************/
	
	/*****************************************
	 * 	        ��ѯ����
	 *****************************************/
	
	/**
	 * ����������ѯ
	 * @throws Exception
	 */
	
	protected final void query()throws Exception{
		List<Map<String,String>> dataList=this.services.query();
		if(dataList.size()>0) {
			System.out.println(dataList);
			this.saveAttribute("dataList", dataList);
		}else {
			this.saveAttribute("msg", "û�з������������ݣ�");
		}
	}
	
	/**
	 * ��һʵ�� ��ѯ
	 * @throws Exception
	 */
	
	protected final void queryByID()throws Exception{
		Map<String,String> dataMap=this.services.queryByID();
		if(dataMap!=null) {
			this.saveAttribute("dataMap", dataMap);
		}else {
			this.saveAttribute("msg", "��������ɾ�����ֹ���ʣ�");
		}
	}

	/*****************************************
	 * 	       ���·���
	 *****************************************/
	
	/**
	 * ������Ϊ���ܿ���
	 * <
	 *   ����Ϣ��ʾ
	 * >
	 * @param utype
	 * @param msgText
	 * @throws Exception
	 */
	
	/**
	 * ͨ������ִ�и��·���
	 * @param methodName
	 * @return
	 * @throws Exception
	 */

	private boolean executeUpdateMethod(String methodName)throws Exception{
		//1.��ȡ��Ҫ���õķ�������
		Method method=this.services.getClass().getDeclaredMethod(methodName);
		method.setAccessible(true);
		//2.���÷���
		return (boolean)method.invoke(services);
	}
	
	
	
	
	
	protected final void update(String actionName,String msgText)throws Exception
	{
		String msg=this.executeUpdateMethod(actionName)?"�ɹ�!":"ʧ��!";
		this.saveAttribute("msg", msgText+msg);
	}
	
	/**
	 * ���б�ŵ���Ϣ��ʾ�ĸ�����Ϊ
	 * @param utype
	 * @param typeText
	 * @param msgText
	 * @param key
	 * @throws Exception
	 */
	
	protected final void update(String actionName,String actionText,String msgText,String key)throws Exception
	{
    	String msg=actionText+"ʧ��!";
    	if(this.executeUpdateMethod(actionName))
    	{
    		msg=msgText+"[ <msg> "+this.dto.get(key)+" </msg> ]";
    	}
    	//Servlet��ҳ���������
    	this.saveAttribute("msg", msg);
	}

	/*****************************************
	 * 	        ����������
	 *****************************************/
	protected Map<String,Object> dto=null;
    @Override
    public final void setDto(Map<String, Object> dto) 
    {
        this.dto=dto;
        this.services.setDto(dto);
    }
    
    protected final void showDto()
    {
    	System.out.println(this.dto);
    }

	/*****************************************
	 * 	        ���������
	 *****************************************/
    private Map<String,Object> attribute=new HashMap<>();
    protected final void  saveAttribute(String key,Object value)
    {
    	this.attribute.put(key, value);
    }
    
    @Override
    public final Map<String, Object> getAttribute() 
    {
    	return this.attribute;
    }
}
