package edu.whu.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.whu.web.support.BaseController;


@WebServlet("*.html")
public class BaseServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		 String toPath=null;   //��ת��Ŀ��ҳ��
         try
         {
        	 
        	/************************************************************
        	 *      ��������·��,��ȡĿ���������
        	 ************************************************************/
     		//��������ķ���·��
     		String uri=request.getRequestURI();
     		//��ȡ������Դ�����ļ���
     		String baseName=uri.substring(uri.lastIndexOf("/")+1).replace(".html", "");
     		//��ȡ��������ǰ׺��
     		String servletName=baseName.substring(0, 1).toUpperCase()+baseName.substring(1);
     		//�������,��������ҵ��������Ļ���������
     		String packagePath="edu.whu.web.impl.";
     		String name=packagePath+servletName+"Servlet";
     		System.out.println(name);
     		/***********************************************************
     		 *                        ʵ����Ŀ����---ҵ�������
     		 ***********************************************************/
     		//ʵ����ҵ�������
     		BaseController controller=(BaseController)Class.forName(name).newInstance();
     		/***********************************************************
     		 *                        ��ҵ�������,���ҳ������     i
     		 ***********************************************************/
     		//Ϊҵ�������֯��DTO��Ƭ
     		controller.setDto(this.createDto(request));
     		/***********************************************************
     		 *                        ����ҵ������������̿��Ʒ���
     		 ***********************************************************/
     		//�������̿��Ʒ���
     		toPath=controller.execute();
     		/***********************************************************
     		 *                      �����������ҳ�����������     o
     		 ***********************************************************/
     		//��������
     		Map<String,Object> attri=controller.getAttribute();
     		//֯�����Դ�����Ƭ
     		this.parseRueqestAttribute(request, attri);
         }	
         catch(Exception ex)
         {
        	 request.setAttribute("msg", "��ʾ:�������!");
        	 //toPath="Error";
        	 ex.printStackTrace();
         }
		request.getRequestDispatcher("/"+toPath+".jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}
	
	private void parseRueqestAttribute(HttpServletRequest request,Map<String,Object> rueqestAttribute)
	{
		//1.��ԭ���еļ�ֵ��,�γɼ���
		Set<Map.Entry<String, Object>> entrySet=rueqestAttribute.entrySet();
		//2.ѭ������
		for(Map.Entry<String, Object> entry:entrySet)
		{
			//3.��map��ÿ����ֵ��,ת����request������
			request.setAttribute(entry.getKey(), entry.getValue());
		}
		//������е�request����������
		rueqestAttribute.clear();
	}
	
	private Map<String, Object> createDto(HttpServletRequest request) {
		//1.��ȡ����map
		Map<String, String[]> map=request.getParameterMap();
		//2.��map�л�ȡ��ֵ��entry�����ɼ���entrySet
		Set<Entry<String, String[]>> entrySet=map.entrySet();
		//3.���������map
		Map<String, Object> result=new HashMap<>();
		//4.��string���鳤��Ϊһ����Ϊstring����result��
		for(Entry<String, String[]> entry:entrySet) {
			if(entry.getValue().length==1){
				result.put(entry.getKey(), entry.getValue()[0]);
			}else {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		return result;
	}
	
}
