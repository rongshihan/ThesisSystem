package edu.whu.services.impl;

import java.util.List;
import java.util.Map;

import edu.whu.services.support.JdbcServicesSupport;

public class ExpertInfoServicesImpl extends JdbcServicesSupport
{
    /**
     * ר�Ҳ鿴�Լ������Ĵ����Ϣ
     * @return
     * @throws Exception
     */
	public List<Map<String,String>>query()throws Exception
	{
  		//��ԭҳ���ѯ����
    	Object uid=this.getFromDto("uid");
		//����SQL����
    	//��δ��дSQL�������,��Ϊ��簲��δȷ��,��test���ݿ����
		StringBuilder sql=new StringBuilder()
				.append("select s.fvalue sb302,u.name name1,b.b102,x.name name2")
				.append("  from b01 b,b03 c,a05 d,user u,user x,syscode s")
				.append(" where u.uid=b.uid1 and c.b101=b.b101")
				.append("   and x.uid=b.uid2 and c.uid=d.uid")
				.append("   and s.fname='b302' and s.fcode=c.b302 and c.uid=?")
				.append(" order by u.uid");
		Object args[]={
				uid
		};
  		return this.queryForList(sql.toString(), args);
	}
}
