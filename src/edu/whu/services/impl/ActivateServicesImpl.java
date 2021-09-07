package edu.whu.services.impl;

import java.util.Map;

import edu.whu.services.support.JdbcServicesSupport;

public class ActivateServicesImpl extends JdbcServicesSupport 
{
	public Boolean activateAcount() throws Exception 
	{
		String uname=(String)this.getFromDto("flag");
		if(uname==null) 
		{
			return null;
		}
		
		String sql1="select uid,name,ustate from user where uname="+uname;
		Map<String,String> map=this.queryForMap(sql1);
		if(map.get("ustate").equals("1"))
		{
			return false;
		}
		String uid=map.get("uid");
		String sql2="update user set ustate= '1' where uname="+uname;
		this.executeUpdate(sql2);
		this.putIntoDto("name", map.get("name"));
		Object[] list= {
				"�˺ż������",
				"��ϲ���ɹ����ϵͳ�˻������ڱ�ѧԺ����˶ʿ�����������ڼ佫ȫ���ɱ�ϵͳΪ���ṩ�����������������߲鿴֪ͨ��"
		};
		this.sendMessage(uid, list);
		return true;
	}
}
