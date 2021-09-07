package edu.whu.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.whu.services.support.JdbcServicesSupport;

public class A06ImplServices extends JdbcServicesSupport 
{
	public Map<String, String> queryByID() throws Exception
	{
		Map<String,String>map =new HashMap<>();
    	StringBuilder sql=new StringBuilder()
    			.append("select x.uid,x.uid2,x.a601,a.fvalue a602,x.a603,")
    			.append("       b.fvalue a604,x.a605,x.a606,x.a607,x.a608,")
    			.append("       x.a609,u.`name`")
    			.append("  from syscode a,syscode b,a06 x,`user` u")
    			.append(" where x.a602=a.fcode and a.fname='a602'")
                .append("   and x.a604=b.fcode and b.fname='a604'")
    			.append("   and x.uid=u.uid")
                .append("   and x.uid=?")
    			;
    	map=this.queryForMap(sql.toString(), this.getFromDto("cuid"));
    	
    	String uid=map.get("uid");
		List<Map<String,String>> list_1=new ArrayList<>();
		List<Map<String,String>> list_2=new ArrayList<>();
		//����uid����������
		String sql_1="select a603 from a06 where uid="+uid;
		list_1=this.queryForList(sql_1);
		for(Map<String,String> map_1:list_1)
		{
			//������������ַ���
			String a603=map_1.get("a603");
			//syscodeת��
			String sql_2="select fvalue from syscode where fname='a603' and fcode in("+a603+")";
			list_2=this.queryForList(sql_2);
			//���õ��Ķ������ݣ�ճ�ϳ�һ��
			StringBuilder str=new StringBuilder();
			for(Map<String,String> fvalueMap:list_2)
			{
				str.append(fvalueMap.get("fvalue"));//��������---������
				str.append(" ");//�ո�ָ�
			}
			map.put("a603",str.toString());
		}
    	return map;
	}
}
