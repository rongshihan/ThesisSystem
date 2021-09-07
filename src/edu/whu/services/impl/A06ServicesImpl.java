package edu.whu.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.whu.services.support.JdbcServicesSupport;
import edu.whu.system.db.DBUtils;
import edu.whu.system.tools.Tools;

import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class A06ServicesImpl extends JdbcServicesSupport 
{
	public boolean addStaff() throws Exception
	{
		String username = Tools.getFormatNumber("2", "Sta");
		boolean tag = false;
		System.out.println(username);
		// ��DTO���Ա�����
		DBUtils.beginTransaction();
		try {
			String sql1 = "insert into user( uname,upassword,name,ustate) values(?,?,?,?)";

			Object args2[] = { 
					username, 
					Tools.getMd5(username), 
					this.getFromDto("name"), 
					"0" 
					};
			this.executeUpdate(sql1.toString(), args2);
			// 1.��дSQL���
			StringBuilder sql = new StringBuilder()
					.append("insert into a06(uid,a601,a602,a603,a604,a605,")
					.append("                 a606,a607,a608,a609)")
					.append(" select user.uid,?,?,?,?,?,?,?,?,?  ")
					.append(" from user     ")
			        .append(" where user.uname=?  ");
			// 2.��д��������
			Object args[] = {
					this.getFromDto("a601"), 
					this.getFromDto("a602"),
					this.getFromDto("a603"),
					this.getFromDto("a604"), 
					this.getFromDto("a605"),
					this.getFromDto("a606"),
					this.getFromDto("a607"),
					this.getFromDto("a608"), 
					this.getFromDto("a609"),
					username
					};
			this.executeUpdate(sql.toString(), args);
			//u_r_relation�����
			String sql2="insert into u_r_relation (uid,rid,u_r_state) select user.uid,?,? from user where user.uname=?";	
			String[] rolelist=null;
			if(this.getFromDto("roles") instanceof String[])
			{
				rolelist=(String[])this.getFromDto("roles");
			}
			else
			{
				rolelist=((String)this.getFromDto("roles")).split(" ");
			}
			for(String s:rolelist)
			{
				Object[] args3={
							s,
							"1",
							username
					};
				this.executeUpdate(sql2, args3);
				//�ж��Ƿ���뵼ʦ����Ϣ
				if(s.equals("2"))
				{
					String sql3="insert into a02 (uid,a201) select user.uid,null from user where user.uname="+username;
					String sql4="update user set ustate='1' where uname="+username;
					this.executeUpdate(sql3);
					this.executeUpdate(sql4);
				}
			}
			DBUtils.commit();
		} catch (Exception ex) {
			DBUtils.rollback();
			ex.printStackTrace();
		} finally {
			tag = true;
			DBUtils.endTransaction();
		}
		this.putIntoDto("username", username);
		return tag;
	}


	public Map<String, String> queryByID() throws Exception 
	{
		Map<String,String> map=new HashMap<String, String>();
		// 1.��дSQL���
		StringBuilder sql1 = new StringBuilder()
				.append("select b.name,a.uid,a.a601,a.a602,a.a603,a.a604,")
				.append("       a.a605,a.a606,a.a607,a.a608,a.a609")
				.append("  from a06 a,user b")
				.append(" where a.uid=b.uid")
				.append("   and a.uid=?");
		// ִ�в�ѯ
		map=this.queryForMap(sql1.toString(), this.getFromDto("uid"));
		String uid=map.get("uid");
		//��ȡroles
		String sql2="select rid from u_r_relation where uid="+uid;
		List<Map<String,String>> maplist=this.queryForList(sql2);
		StringBuilder roles=new StringBuilder();
		for(Map<String,String> m:maplist)
		{
			roles.append(m.get("rid")+",");
		}
		map.put("roles", roles.toString().substring(0,roles.toString().lastIndexOf(",")));
		System.out.println(roles.toString().substring(0,roles.toString().lastIndexOf(",")));
		return map;
	}

	/**
	 * ����������ѯ
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> query() throws Exception
	{
		//��ȡ���ã����ã�ɾ������
		String canDel="0";
		// ��ԭҳ���ѯ����
		Object name = this.getFromDto("qname");
		Object a601 = this.getFromDto("qa601");
		Object a602 = this.getFromDto("qa602");
		Object a605 = this.getFromDto("qa605");
		
		// ����SQL����
		StringBuilder sql = new StringBuilder()
				.append("select b.name,b.ustate,a.a601,x.fvalue a602,a.a603,s.fvalue a604,")
				.append("       a.a605,a.a608,a.a609,a.uid")
				.append("  from a06 a,user b,syscode s,syscode x")
				.append(" where a.uid=b.uid and s.fcode=a.a604 and s.fname='a604' and x.fcode=a.a602 and x.fname='a602' ")
				;
		
		// �����б�
		List<Object> paramList = new ArrayList<>();
		// ��һ�жϲ�ѯ�����Ƿ�¼��,ƴ��AND����
		if (this.isNotNull(name)) {
			sql.append(" and b.name like ?");
			paramList.add("%" + name + "%");
		}
		if (this.isNotNull(a601)) {
			sql.append(" and a.a601=?");
			paramList.add(a601);
		}
		if (this.isNotNull(a602)) {
			sql.append(" and a.a602=?");
			paramList.add(a602);
		}
		if (this.isNotNull(a605)) {
			sql.append(" and a.a605=?");
			paramList.add(a605);
		}

		List<Map<String,String>> maplist=this.queryForList(sql.toString(), paramList.toArray());
		
		//����setȷ���û���ɫ��Χ
		Set<String> roleset=new HashSet<>( );
		roleset.add("2");
		roleset.add("4");
		roleset.add("5");
		roleset.add("7");
		
		Iterator<Map<String,String>> it=maplist.iterator();
		while(it.hasNext())
		{
			//��ȡ��ɫ�б�
			Map<String,String> m=it.next();
			String uid=m.get("uid");
			String sql2="select u.rid,r.rname from u_r_relation u,role r where r.rid=u.rid and u.uid="+uid;
			List<Map<String,String>> temp=this.queryForList(sql2);
			boolean flag=false;
			StringBuilder roles=new StringBuilder();

			for(Map<String,String> tm:temp)
			{
				if(roleset.contains(tm.get("rid")))
				{
					roles.append(tm.get("rname")+" ");
					flag=true;
				}
			}
			if(!flag)
			{
				it.remove();
			}
			else
			{
				m.put("roles", roles.toString().trim());
				//�Ƿ��ɾ������ȡ
				//�Ƿ�Ϊѧ����ʦ
				String sql3="select uid from a01 where uid2="+uid;
				Map<String,String> c1=this.queryForMap(sql3);
				//�Ƿ������󣬴�����������
				String sql4="select uid from a04 where uid="+uid;
				Map<String,String> c2=this.queryForMap(sql4);
				String sql5="select uid from a05 where uid="+uid;
				Map<String,String> c3=this.queryForMap(sql5);
				if(c1==null&&c2==null&c3==null)
				{
					canDel="1";
					
				}
				m.put("canDel", canDel);
			}
		}
		
		//������밴syscodeת��
		for(Map<String,String> m:maplist)
		{
			String uid=m.get("uid");
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
				m.put("a603",str.toString());
			}
		}
		
		return maplist;
	}
	
	public boolean modifyStaff() throws Exception {
		String uid=(String)this.getFromDto("uid");
		//�޸�a01��Ϣ
		StringBuilder sql1 = new StringBuilder()
				.append("update a06 a, user b")
				.append("   set a.a601=?,a.a602=?,a.a603=?,a.a604=?,a.a605=?,a.a606=?,")
				.append("       a.a607=?,a.a608=?,a.a609=?,b.name=?")
				.append(" where a.uid=b.uid")
				.append("   and a.uid=?");
		Object args[] = { 
				this.getFromDto("a601"), 
				this.getFromDto("a602"), 
				this.getFromDto("a603"), 
				this.getFromDto("a604"), 
				this.getFromDto("a605"), 
				this.getFromDto("a606"), 
				this.getFromDto("a607"), 
				this.getFromDto("a608"), 
				this.getFromDto("a609"), 
				this.getFromDto("name"),
				uid
				};
		
		//�����ɫ���
		//��ȡ���кͽ�Ҫ������û���ɫ���
		//����rid����Ķ�Ӧmap��-1Ϊɾ����0Ϊ���ı䣬1Ϊ���
		Map<String,Integer> map=new HashMap<String,Integer>();
		String[] nlist=null;
		if(this.getFromDto("roles") instanceof String[])
		{
			nlist=(String[])this.getFromDto("roles");
		}
		else
		{
			nlist=((String)this.getFromDto("roles")).split(" ");
		}
		
		for(String s:nlist)
		{
			map.put(s,1);
		}
		
		String sql2="select rid from u_r_relation where uid="+uid;
		List<Map<String,String>> maplist=this.queryForList(sql2);
		for(Map<String,String> m:maplist)
		{
			String rid=m.get("rid");
			if(map.containsKey(rid))
			{
				map.replace(rid, 0);
			}
			else
			{
				map.put(rid, -1);
			}
		}
		
		for(String s:map.keySet())
		{
			switch(map.get(s)) {
				case -1:
					String sql3="delete from u_r_relation where uid="+uid+" and rid="+s;
					
					//�ж��Ƿ�ɾ����ʦ����Ϣ
					if(s.equals("2"))
					{
						String sql4="delete from a02 where uid="+uid;
						String sql5="update user set ustate='0' where uid="+uid;
						this.executeUpdate(sql4);
						this.executeUpdate(sql5);
					}
					this.executeUpdate(sql3);
					break;
				case 0:
					break;
				case 1:
					String sql5="insert into u_r_relation (uid,rid,u_r_state) values("+uid+","+s+",'1')";
					//�ж��Ƿ���뵼ʦ����Ϣ
					if(s.equals("2"))
					{
						String sql4="insert into a02 (uid,a201) values ("+uid+",null)";
						String sql6="update user set ustate='1' where uid="+uid;
						this.executeUpdate(sql4);
						this.executeUpdate(sql6);
					}
					this.executeUpdate(sql5);
					break;
			}
		}
		return this.executeUpdate(sql1.toString(), args) > 0;
	}
	
	

	
	/******************************************************************************/
	//ɾ������
	public boolean deleteByID() throws Exception 
	{
		
		boolean tag = false;
		DBUtils.beginTransaction();
		try
		{
			//��ȡ��ɫ�б� ɾ����Ӧ��ɫ����Ϣ����ʦ��
			String uid=(String)this.getFromDto("uid");
			String sql1="select rid from u_r_relation where uid="+uid;
			List<Map<String,String>> maplist=this.queryForList(sql1);
			for(Map<String,String> m:maplist)
			{
				if(m.get("rid").equals("2"))
				{
					String sql2="delete from a02 where uid="+uid;
					this.executeUpdate(sql2);
				}
			}
			String sql3 = "delete from u_r_relation where uid=?";
			this.executeUpdate(sql3);
			String sql4 = "delete from a06 where uid="+uid;
			this.executeUpdate(sql4);
			String sql5 = "delete from user where uid="+uid;
			this.executeUpdate(sql5);
		} 
		catch (Exception ex) 
		{
			DBUtils.rollback();
			ex.printStackTrace();
		} 
		finally
		{
			DBUtils.endTransaction();
		}
		return tag;
	}

	public boolean batchDelete() throws Exception {
		boolean tag = false;
		DBUtils.beginTransaction();
		try
		{
			String[] list=this.getIdList("idlist");
			//��ȡ��ɫ�б� ɾ����Ӧ��ɫ����Ϣ����ʦ��
			for(String uid:list)
			{
				String sql1="select rid from u_r_relation where uid="+uid;
				List<Map<String,String>> maplist=this.queryForList(sql1);
				for(Map<String,String> m:maplist)
				{
					if(m.get("rid").equals("2"))
					{
						String sql2="delete from a02 where uid="+uid;
						this.executeUpdate(sql2);
					}
				}
				String sql3 = "delete from u_r_relation where uid=?";
				this.executeUpdate(sql3);
				String sql4 = "delete from a06 where uid="+uid;
				this.executeUpdate(sql4);
				String sql5 = "delete from user where uid="+uid;
				this.executeUpdate(sql5);
			}
		} 
		catch (Exception ex) 
		{
			DBUtils.rollback();
			ex.printStackTrace();
		} 
		finally
		{
			DBUtils.endTransaction();
		}
		return tag;
	}
	
	//���÷���
	public boolean disaStaffByID() throws Exception
	{
		Object uid=this.getFromDto("uid");
		String sql="update user set ustate = '0' where uid="+uid;
		return this.executeUpdate(sql)>0;
	}
	
	public boolean disaStaff() throws Exception
	{
		Object[] idlist=this.getIdList("idlist");
		String sql="update user set ustate='0' where uid = ?";
		return this.batchUpdate(sql, idlist);
	}
	
	//���÷���
	public boolean ableStaffByID() throws Exception
	{
		Object uid=this.getFromDto("uid");
		String sql="update user set ustate = '1' where uid="+uid;
		return this.executeUpdate(sql)>0;
	}
	
	public boolean ableStaff() throws Exception
	{
		Object[] idlist=this.getIdList("idlist");
		String sql="update user set ustate='1' where uid = ?";
		return this.batchUpdate(sql, idlist);
	}
	
	public boolean email() throws Exception
	{
		boolean tag = false;
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");// ����Э��
		properties.put("mail.smtp.host", "smtp.qq.com");// ������
		properties.put("mail.smtp.port", 465);// �˿ں�
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.enable", "true");// �����Ƿ�ʹ��ssl��ȫ���� ---һ�㶼ʹ��
		properties.put("mail.debug", "true");// �����Ƿ���ʾdebug��Ϣ true ���ڿ���̨��ʾ�����Ϣ
		// �õ��ػ�����
		Session session = Session.getInstance(properties);
		// ��ȡ�ʼ�����
		Message message = new MimeMessage(session);
		// ���÷����������ַ
		message.setFrom(new InternetAddress("2892466166@qq.com"));
		// �����ռ��������ַ
		// message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new
		
		// �����ʼ�����
		message.setSubject("�人��ѧ���Ĵ������");

		//��ȡ��ɫ��Ϊδע��ר�ҵ�idlist
		String sql1="select uid from u_r_relation where rid='7'";
		List<Map<String,String>> maplist=this.queryForList(sql1);
		List<String> idlist=new ArrayList<>();
		for(Map<String,String> m:maplist)
		{
			idlist.add(m.get("uid"));
		}
		//ȥ�����б�Ľ�ɫ���û�id
		Iterator it=idlist.iterator();
		while(it.hasNext())
		{
			String tuid=(String)it.next();
			String sql2="select rid from u_r_relation where uid="+tuid;
			if(this.queryForList(sql2).size()>1)
			{
				it.remove();
			}
		}
		
		for (String id : idlist)
		{
			StringBuilder sql3 = new StringBuilder()
					.append("SELECT b.name, a.a609")
					.append("  from a06 a, user b")
					.append(" where a.uid=b.uid")
					.append(" and a.uid=?")
					;
			Map<String, String> mailinfo = this.queryForMap(sql3.toString(), Integer.parseInt(id));
			StringBuilder mess = new StringBuilder()
					.append("�𾴵�"+mailinfo.get("name")+",����\n")
					.append("�人��ѧ�����ѧԺ��ϵ����������������о������Ĵ��.")
					.append("������ѡ�����������̻������.")
					.append("��������ڰ�æ֮�н�������,���¼������ַ����ע��\n")
					.append(this.getFromDto("path").toString()+"/activate.jsp?flag="+Integer.parseInt(id)+"\n")
					.append("�人��ѧ�����ѧԺ��ӭ���ĵ���")
					;
			// InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com"),new
			// InternetAddress("xxx@qq.com")});
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailinfo.get("a609")));//һ���ռ���
			// �����ʼ�����
			message.setText(mess.toString());
			// �õ��ʲ����
			Transport transport = session.getTransport();
			// �����Լ��������˻�
			transport.connect("2892466166@qq.com", "lhfqetcmysttdcij");// ����ΪQQ���俪ͨ��stmp�����õ��Ŀͻ�����Ȩ��
			// �����ʼ�
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		return tag;
	}
}