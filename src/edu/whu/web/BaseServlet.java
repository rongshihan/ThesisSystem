package edu.whu.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.whu.services.impl.MessageServicesImpl;
import edu.whu.web.support.BaseControllerSupport;


@WebServlet("*.html")
public class BaseServlet extends HttpServlet 
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		 String toPath=null;   //��ת��Ŀ��ҳ��
         try
         {
        	 
        	/************************************************************
        	 *      ��������·��,��ȡĿ���������
        	 ************************************************************/
     		//��������ķ���·��
     		String  uri=request.getRequestURI();
     		//��ȡ������Դ�����ļ���
     		String baseName=uri.substring(uri.lastIndexOf("/")+1).replace(".html", "");
     		
     		//�������,��������ҵ��������Ļ���������
     		String basePackageName="edu.whu.web.impl.";
     		//��ȡ��������ǰ׺��
     		String controllerFirstName=baseName.substring(0,1).toUpperCase()+baseName.substring(1);
     		
     		/***********************************************************
     		 *                        ʵ����Ŀ����---ҵ�������
     		 ***********************************************************/
     		//ʵ����ҵ�������
     		BaseControllerSupport controller=(BaseControllerSupport)Class.forName(basePackageName+controllerFirstName+"Servlet").newInstance();
     		
     		/***********************************************************
     		 *                        ��ҵ�������,���ҳ������     i
     		 ***********************************************************/
     		//Ϊҵ�������֯��DTO��Ƭ
     		request.setAttribute("cuid", request.getSession().getAttribute("cuid"));
     		request.setAttribute("crolelist",request.getSession().getAttribute("crolelist"));

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
     		Map<String,Object> rueqestAttribute=controller.getAttribute();
     		//֯�����Դ�����Ƭ
     		this.parseRueqestAttribute(request, rueqestAttribute);
         }	
         catch(Exception ex)
         {
        	 request.setAttribute("msg", "��ʾ:�������!");
        	 ex.printStackTrace();
         }
		request.getRequestDispatcher("/"+toPath+".jsp").forward(request, response);
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
	
	
	/**
	 *  DTO��Ƭ
	 * @param request
	 * @return
	 */
	protected  Map<String,Object> createDto(HttpServletRequest request)
	{
		//1.��ȡҳ������
		Map<String,String[]> tem=request.getParameterMap();

		int initSize=((int)(tem.size()/0.75))+1;
		//2.�������м�ֵ��,�γɼ�ֵ�Լ���
		Set<Entry<String,String[]>> entrySet=tem.entrySet();
		//3.��������,��ʾEnetry��value����
		String value[]=null;
		
		//4.����DTO����
		Map<String,Object> dto=new HashMap<>(initSize);
		//5.ѭ����ȡentrySet,��ȡÿ����ֵ��
		for(Entry<String,String[]> entry :entrySet)
		{
			//��ȡvalue���ֵ�����
			value=entry.getValue();
			//���ݳ����ж�ҳ��ؼ������
			if(value.length==1)  //��checkbox��ؼ�
			{
		        //���˵�ҳ��δ�����Ŀ
				if(value[0]!=null && !value[0].equals(""))
				{
					dto.put(entry.getKey(), value[0]);
				}
			}
			else     //checkbox��ؼ�
			{
				dto.put(entry.getKey(), value);
			}	
		}
		//System.out.println(dto);
		
		dto.put("cuid", request.getAttribute("cuid"));
		dto.put("crolelist", request.getAttribute("crolelist"));
		
		return dto;
	}

	protected void getMessage(HttpServletRequest request) throws Exception
	{
		MessageServicesImpl services=new MessageServicesImpl();
		
		Map<String,List<String>> messageContent=new HashMap<String,List<String>>();
		
		List<Map<String,String>> messageList=new ArrayList<>();
		String uid=(String) request.getSession().getAttribute("cuid");
		Map<String,List<String>> map=services.getMessage(uid);
		
		for(String key:map.keySet())
		{
			Map<String,String> temp=new HashMap<String,String>();
			
			temp.put("time", map.get(key).get(1));
			
			temp.put("href", map.get(key).get(0));
			//�Ƿ��Ѷ�
			System.out.println(map.get(key).get(4));
			temp.put("state", (map.get(key).get(4).equals("1"))?"�Ѷ�":"δ��");
			List<String> list=new ArrayList<String>();
			//ʱ��
			list.add(map.get(key).get(1));
			//����
			list.add(map.get(key).get(2));
			//����
			list.add(map.get(key).get(3));
			//�Ƿ��Ѷ�
			list.add(map.get(key).get(4));
			messageList.add(temp);
			messageContent.put(key, list);
			System.out.println("href="+temp.get("href"));
		}
		//��Ϣ�б�
		request.getSession().setAttribute("messageList", messageList);
		//��Ϣ����
		request.getSession().setAttribute("messageContent", messageContent);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}

}
