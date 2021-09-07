package edu.whu.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.whu.services.support.JdbcServicesSupport;
import edu.whu.system.db.DBUtils;
import edu.whu.system.tools.Tools;

public class TeaExcelServiceImpl extends JdbcServicesSupport 
{
	public boolean update(String utype) throws Exception 
    {
    	if(utype.equalsIgnoreCase("addEmp"))
    	{
    		return this.addEmp();
    	}
    	else
    	{
    		throw new Exception("����[ Ab01ServicesImpl ]�н�����δ����Ķ�������,����������  "+utype);
    	}	
    }
    
    public boolean addEmp()throws Exception
    {
    	String username = Tools.getFormatNumber("2","T");
    	boolean tag=false;
    	
    	DBUtils.beginTransaction();
    	try
    	{
    		String sql2 = "insert into user( uname,upassword,name,ustate) values(?,?,?,?)";
        	
        	Object args2[]= {
        			username,
        			Tools.getMd5(username),
        			this.getFromDto("����"),
        			"1"
        	};
        	this.executeUpdate(sql2.toString(), args2);
        	
        	String sql5  = "insert into a02(uid,a201) select user.uid,? from user where user.uname=?";
        	Object args5[]= 
        	{	
        		"",
        		username
        	};
        	this.executeUpdate(sql5.toString(), args5);
        	
        	
        	String sql3  = "insert into u_r_relation(uid,rid,u_r_state) select user.uid,?,? from user where user.uname=?";
        	Object args3[]= 
        	{	
        		"2",
        		"1",
        		username
        	};
        	this.executeUpdate(sql3.toString(), args3);
        	

        	
        	//1.��дSQL���
        	StringBuilder sql=new StringBuilder()
        			.append("insert into a06(uid,uid2,a601,a602,a603,a604,a605,")
        			.append("                 a606,a607,a608,a609)")
        			.append(" select user.uid,?,?,?,?,?,?,?,?,?,? from user  ")
        			.append(" where user.uname=?")
        			;
        	//2.��д��������
        	Object args[]={
        			null,
        			"�人��ѧ",
        			"1",
        			this.getFromDto("�о�����"),
        			this.getFromDto("ְ��").toString().substring(0,1),
        			this.getFromDto("���֤��"),
        			this.getFromDto("�����˻�"),
        			this.getFromDto("��������"),
        			this.getFromDto("�ֻ�����"),
        			this.getFromDto("����"),
        			username
        	               };
        	this.executeUpdate(sql.toString(), args);
    	DBUtils.commit();
    	}
    	catch(Exception ex)
    	{
    		DBUtils.rollback();
    		ex.printStackTrace();
    	}
    	finally
    	{
    		tag = true;
        	DBUtils.endTransaction();
    	}
    	
    	return tag;
    }
}

