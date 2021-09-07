package edu.whu.services.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import edu.whu.services.support.JdbcServicesSupport;

/**
 * �����ݿ�user����ȷ�ϵ�¼��Ϣ
 * ͬʱͨ�������ѯ����ȡ�û����ܡ���ɫ�嵥
 * @author Hzy
 */

public class LoginServicesImpl extends JdbcServicesSupport{
	

	
	public Map<String, Object> queryUser(String username,String password) throws Exception{
		//�ӱ��в�ѯuid,name
		String sql1="select uid,name,ustate from user where uname=? and upassword=?";
		Map<String,Object> map=new HashMap<String,Object>();
		Object[] list1=
		{
			username,password
		};
		Map<String,String> temp1 = this.queryForMap(sql1, list1);
		
		//��½ʧ�ܿ���
		if(temp1==null)
		{
			return null;
		}
		else if(temp1.get("ustate").equals("0"))
		{
			map.put("isActivated", false);
			return map;
		}
		else
		{
			map.put("isActivated", true);
		}
		for(Entry<String, String> e:temp1.entrySet())
		{
			map.put(e.getKey(), e.getValue());
		}
		//��ȡ��ѯ����uid
		String uid=temp1.get("uid");
		
		//ͨ��uid����rid�б�
		String sql2="select rid from u_r_relation where uid="+uid;
		List<Map<String,String>> temp2=this.queryForList(sql2);
		if(temp2.size()==0)
		{
			map.put("hasrole",false);
		}
		else
		{
			map.put("hasrole",true);
		}
		List<String> rid=new ArrayList<String>();
		for(Map<String,String> m:temp2)
		{
			rid.add(m.get("rid"));
		}
		//����rid
		
		//���뷵��ֵ
		map.put("rid",rid);
		System.out.println(map);
		return map;
	}
	
}
