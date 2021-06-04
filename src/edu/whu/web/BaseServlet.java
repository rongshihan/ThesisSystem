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
		 String toPath=null;   //跳转的目标页面
         try
         {
        	 
        	/************************************************************
        	 *      解析访问路径,获取目标类的名称
        	 ************************************************************/
     		//拦截请求的访问路径
     		String uri=request.getRequestURI();
     		//获取请求资源的主文件名
     		String baseName=uri.substring(uri.lastIndexOf("/")+1).replace(".html", "");
     		//获取控制器的前缀名
     		String servletName=baseName.substring(0, 1).toUpperCase()+baseName.substring(1);
     		//定义变量,描述所有业务控制器的基础包名称
     		String packagePath="edu.whu.web.impl.";
     		String name=packagePath+servletName+"Servlet";
     		System.out.println(name);
     		/***********************************************************
     		 *                        实例化目标类---业务控制器
     		 ***********************************************************/
     		//实例化业务控制器
     		BaseController controller=(BaseController)Class.forName(name).newInstance();
     		/***********************************************************
     		 *                        向业务控制器,填充页面数据     i
     		 ***********************************************************/
     		//为业务控制器织入DTO切片
     		controller.setDto(this.createDto(request));
     		/***********************************************************
     		 *                        调用业务控制器的流程控制方法
     		 ***********************************************************/
     		//调用流程控制方法
     		toPath=controller.execute();
     		/***********************************************************
     		 *                      处理控制器向页面输出的数据     o
     		 ***********************************************************/
     		//解析属性
     		Map<String,Object> attri=controller.getAttribute();
     		//织入属性处理切片
     		this.parseRueqestAttribute(request, attri);
         }	
         catch(Exception ex)
         {
        	 request.setAttribute("msg", "提示:网络故障!");
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
		//1.还原所有的键值对,形成集合
		Set<Map.Entry<String, Object>> entrySet=rueqestAttribute.entrySet();
		//2.循环集合
		for(Map.Entry<String, Object> entry:entrySet)
		{
			//3.将map的每个键值对,转换成request的属性
			request.setAttribute(entry.getKey(), entry.getValue());
		}
		//清除所有的request级属性数据
		rueqestAttribute.clear();
	}
	
	private Map<String, Object> createDto(HttpServletRequest request) {
		//1.获取数据map
		Map<String, String[]> map=request.getParameterMap();
		//2.从map中获取键值对entry，构成集合entrySet
		Set<Entry<String, String[]>> entrySet=map.entrySet();
		//3.创建结果集map
		Map<String, Object> result=new HashMap<>();
		//4.若string数组长度为一，置为string放入result中
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
