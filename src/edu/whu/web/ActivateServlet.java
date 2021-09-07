package edu.whu.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.whu.services.impl.ActivateServicesImpl;


@WebServlet("/activate")
public class ActivateServlet extends BaseServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ActivateServicesImpl services=new ActivateServicesImpl();
		services.setDto(this.createDto(request));
		try
		{
			Boolean result=services.activateAcount();
			if(result==true) 
			{
				request.setAttribute("msg", "����ɹ���");
				request.setAttribute("redirect", true);
			}
			else if(result==false)
			{
				request.setAttribute("msg", "�˻��ѱ�����");
				request.setAttribute("redirect", true);
			}
			else
			{
				request.setAttribute("msg", "δ����ɵķ��ʣ�");
			}
			request.getRequestDispatcher("redirect.jsp").forward(request, response);
		} 
		catch (Exception e) 
		{
			request.setAttribute("msg", "������ϣ�");
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}

}
