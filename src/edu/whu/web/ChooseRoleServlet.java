package edu.whu.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.whu.services.impl.ChooseRoleServicesImpl;

@WebServlet("/chooseRole")
public class ChooseRoleServlet extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		ChooseRoleServicesImpl services=new ChooseRoleServicesImpl();
		
		String[] list=request.getParameterValues("roles");
		List<String> rolelist=new ArrayList<String>();
		for(String s:list)
		{
			rolelist.add(s);
		}
		String uid=(String)request.getSession().getAttribute("cuid");
		System.out.println(uid);
		//�ж��û��Ƿ�ѡ���˳е��Ľ�ɫ
		if(list==null) 
		{
			request.setAttribute("msg", "������ѡ��һ��");
			request.getRequestDispatcher("chooseRole.jsp").forward(request,response);
		}
		else
		{
			try
			{
				//����u_r_relation
				services.chooseRole(list,uid);
				//���ɹ����б�
				Map<String,List<String>> rfmap=(Map<String, List<String>>) this.getServletContext().getAttribute("rfmap");
				List<String> funclist=new ArrayList<String>();
				for(String s:list) 
				{
					for(String s1:rfmap.get(s))
					{
						if(!funclist.contains(s1))
						{
							funclist.add(s1);
						}
					}
				}
				//ת��main.jsp
				request.getSession().setAttribute("funclist", funclist);
				request.getSession().setAttribute("rolelist", rolelist);
				this.getMessage(request);
				request.getRequestDispatcher("main.jsp").forward(request, response);
			}
			catch (Exception e) 
			{
				request.setAttribute("msg", "������ϣ�");
				e.printStackTrace();
				request.getRequestDispatcher("chooseRole.jsp").forward(request,response);
			}
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		this.doGet(request, response);
	}
	
}
