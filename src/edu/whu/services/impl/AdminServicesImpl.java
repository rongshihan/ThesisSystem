package edu.whu.services.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import edu.whu.system.db.DBUtils;
import edu.whu.system.tools.Tools;
import edu.whu.services.support.JdbcServicesSupport;

public class AdminServicesImpl extends JdbcServicesSupport
{
	
	/*
	 * ����Ա�ֶ��������
	 */
	public boolean secAdd()throws Exception
	{
		DBUtils.beginTransaction();
		boolean tag=false;
		try
		{
			//��ȡ��ǰԱ���û���//2��ʾΪ��ְ��
			String uname=Tools.getFormatNumber("2","Sta");
	    	//��DTO���Ա���û���R
			this.putIntoDto("uname", uname);
//			System.out.println(this.getFromDto("name"));
	    	//1.��дSQL���
			StringBuilder sql=new StringBuilder()
	    			.append("insert into user(uname,upassword,name,ustate)")
	    			.append("          values(?,?,?,'1')");
	    			;
	    	//2.��д��������
			Object args[]={
					uname,
	    			Tools.getMd5(uname),
	    			this.getFromDto("name")
			};
			this.executeUpdate(sql.toString(), args);
		
			//��uname��user���ȡuid
			String sql2="select uid from user where uname=?";
			Map<String, String> map=new HashMap<>();
			map=this.queryForMap(sql2.toString(), uname);
			
			//��ר�ұ�(a06)�������
			//1.��дSQL���
			StringBuilder sql3=new StringBuilder()
	    			.append("insert into a06(uid,uid2,a601,a602,a603,a604,a605,a606,a607,a608,a609)")
	    			.append("         values(?,null,?,'1',?,")
	    			.append("                ?,?,null,null,?,")
	    			.append("                ?);")
	    			;
	    	//2.��д��������
			Object args3[]={map.get("uid"),
					this.getFromDto("a601"),//����
					//1,//a602Ĭ��ΪУ��ר��(ר�����)
	    			this.getFromDto("a603"),//�о�����
	    			this.getFromDto("a604"),//ְ��
	    			this.getFromDto("a605"),//���֤����
	    			this.getFromDto("a608"),//�ֻ�����
	    			this.getFromDto("a609"),//����
			};
			this.executeUpdate(sql3.toString(), args3);
			
			//�������(a03)�������
			String sql4="insert into a03(uid) values(?);";
			Object[] args4={map.get("uid")};
			this.executeUpdate(sql4, args4);
			
			//���û�-��ɫ��(u_r_relation)�������
			String sql5="insert into u_r_relation (uid,rid,u_r_state) values(?,?,'1');";
			Object args5[]={map.get("uid"),"3"};//3��ʾ��ɫΪ����
			this.executeUpdate(sql5, args5);
			
			//���������ɫ��Ϣ
			String[] rolelist=null;
			if(this.getFromDto("roles") instanceof String[])
			{
				rolelist=(String[])this.getFromDto("roles");
				for(String s:rolelist)
				{
					Object[] args6={
							map.get("uid"),
							s
					};
					this.executeUpdate(sql5, args6);
					//�ж��Ƿ���뵼ʦ����Ϣ
					if(s.equals("2"))
					{
						String sql6="insert into a02 (uid,a201) values("+map.get("uid")+",null)";
						this.executeUpdate(sql6);
					}
				}
			}
			else if(this.getFromDto("roles") instanceof String)
			{
				rolelist=((String)this.getFromDto("roles")).split(" ");
				for(String s:rolelist)
				{
					Object[] args6={
							map.get("uid"),
							s
					};
					this.executeUpdate(sql5, args6);
					//�ж��Ƿ���뵼ʦ����Ϣ
					if(s.equals("2"))
					{
						String sql6="insert into a02 (uid,a201) values("+map.get("uid")+",null)";
						this.executeUpdate(sql6);
					}
				}
			}

			//���֤����
			if(this.isAdded()==true)
			{
				DBUtils.rollback();
			}
			else
			{
				DBUtils.commit();
				tag=true;
				
			}
		}
		catch (Exception e) 
		{
			DBUtils.rollback();
			e.printStackTrace();
		}
		finally
		{
			DBUtils.endTransaction();
		}
		return tag;
	}
	
	
	public List<Map<String,String>> query()throws Exception
	{
		String canDel="0";
		StringBuilder sql1=new StringBuilder()
				.append("select u.uid,u.name,a.a601,a.a603,s.fvalue xa604,a.a605,a.a608,a.a609")
				.append("  from user u,a06 a,syscode s,u_r_relation r")
				.append(" where s.fcode=a.a604 and u.uid=a.uid")
				.append("   and s.fname='a604' and u.uid=r.uid and r.rid='3' ");
		List<Map<String,String>> maplist=this.queryForList(sql1.toString());
		System.out.println(maplist.size());
		
		//ȷ���û���ɫ��Χ
		for(Map<String,String> m:maplist)
		{
			String uid=m.get("uid");
			String sql2="select u.rid,r.rname from u_r_relation u,role r where r.rid=u.rid and u.uid="+uid;
			List<Map<String,String>> temp=this.queryForList(sql2);
			StringBuilder roles=new StringBuilder();
			for(Map<String,String> tm:temp)
			{
				roles.append(tm.get("rname")+" ");
			}
			m.put("roles", roles.toString().trim());
			//�Ƿ�Ϊѧ����ʦ
			String sql3="select uid from a01 where uid2="+uid;
			Map<String,String> c1=this.queryForMap(sql3);
			//�Ƿ������󣬴�����������
			String sql4="select uid from a04 where uid="+uid;
			Map<String,String> c2=this.queryForMap(sql4);
			String sql5="select uid from a05 where uid="+uid;
			Map<String,String> c3=this.queryForMap(sql5);
			//�Ƿ��ѷ���ѧ��
			String sql6="select uid from a01 where uid3="+uid;
			Map<String,String> c4=this.queryForMap(sql6);
			if(c1==null&&c2==null&c3==null&&c4==null)
			{
				canDel="1";
			}
			m.put("canDel", canDel);
		}
		return maplist;
	}
	

	
	public Map<String,String> queryByID()throws Exception
	{
    	//��дSQL���
		StringBuilder sql=new StringBuilder()
    			.append("select u.name,a.a601,a.a603,a.a604,a.a605,")
    			.append("       a.a608,a.a609,u.uid")
    			.append("  from user u,a06 a")
    			.append(" where a.uid=u.uid")
    			.append("   and u.uid=?")
    			;
    	//ִ�в�ѯ
		Map<String,String> map= this.queryForMap(sql.toString(),this.getFromDto("uid"));
		//��ȡroles
		String uid=map.get("uid");
		String sql2="select rid from u_r_relation where uid="+uid;
		List<Map<String,String>> maplist=this.queryForList(sql2);
		StringBuilder roles=new StringBuilder();
		for(Map<String,String> m:maplist)
		{
			if(!m.get("rid").equals("3"))
			{
				roles.append(m.get("rid")+",");
			}
		}
		if(roles.length()!=0)
		{
			map.put("roles", roles.toString().substring(0,roles.toString().lastIndexOf(",")));
		}
		return map;
	}
	
	public boolean modifySecretary()throws Exception
	{
		DBUtils.beginTransaction();
		boolean tag=false;
		try
		{
			StringBuilder sql=new StringBuilder()    			
					.append("update a06 a,user u")
	    			.append("   set u.name=?,a.a601=?,a.a603=?,a.a604=?,a.a605=?,")
	    			.append("       a.a608=?,a.a609=?")
	    			.append(" where u.uid=?")
	    			.append("   and a.uid=?")
	    			;
	    	Object args[]={
	    			this.getFromDto("name"),
	    			this.getFromDto("a601"),
	    			this.getFromDto("a603"),
	    			this.getFromDto("a604"),
	    			this.getFromDto("a605"),
	    			this.getFromDto("a608"),
	    			this.getFromDto("a609"),
	    			this.getFromDto("uid"),
	    			this.getFromDto("uid")
	    	};
	    	this.executeUpdate(sql.toString(), args);
	    	//��ɫ״̬�޸�
	    	String uid=(String)this.getFromDto("uid");
			String sql2="delete from u_r_relation where uid="+uid;
			String sql4="insert into u_r_relation (uid,rid,u_r_state) values ("+uid+",3,1)";
			if(this.getFromDto("roles")!=null)
			{
				String[] nlist=null;
				if(this.getFromDto("roles") instanceof String[])
				{
					nlist=(String[])this.getFromDto("roles");
				}
				else
				{
					nlist=((String)this.getFromDto("roles")).split(" ");
				}
				this.executeUpdate(sql2);
				for(String s:nlist)
				{
					String sql3="insert into u_r_relation (uid,rid,u_r_state) values ("+uid+","+s+",1)";
					this.executeUpdate(sql3);
				}
				this.executeUpdate(sql4);
			}
			else
			{
				this.executeUpdate(sql2);
				this.executeUpdate(sql4);
			}
			
	    	
	    	if(isAdded()==true)
	    	{
	    		DBUtils.rollback();
	    	}
	    	else
	    	{
	    		DBUtils.commit();
	    		tag=true;
	    	}
		}
		catch(Exception e)
		{
			DBUtils.rollback();
			e.printStackTrace();
		}
		finally
		{
			DBUtils.endTransaction();
		}
    	return tag;
	}
	
	public boolean batchDelete()throws Exception
	{
		//����SQL���
		//ѧ�����ڴ�����id��0
		String sql1="update a01 set uid3='0' where uid=?;";
		//ɾ�����û����������Ϣ
		String sql2="delete from a03 where uid=?";
		//ɾ�����û��������ɫ�û�����Ϣ
		String sql3="delete from u_r_relation where rid='3' and uid=?;";
		Object idList[]=this.getIdList("idList");
    	//ִ��
		this.batchUpdate(sql1, idList);
		this.batchUpdate(sql2, idList);
		this.batchUpdate(sql3, idList);
		for(Object id:idList)
		{
			//��ѯ�Ƿ���������ɫ
			String sql4="select rid from u_r_relation where uid="+id;
			List<Map<String,String>> list=this.queryForList(sql4);
			if(list.size()==0)
			{
				//û��������ɫ����ר�ұ���ɾ�����û����û�����ɾ�����û�
				String sql5="delete from a06 where uid="+id;
				String sql6="delete from user where uid="+id;
				this.executeUpdate(sql5);
				this.executeUpdate(sql6);
			}
		}
		return true;
	}
	
	public boolean deleteById()throws Exception
	{
		DBUtils.beginTransaction();
		boolean tag=false;
		Object uid=this.getFromDto("uid");
		try
		{
			//���ĳ�û�������ͬʱ����������ɫ��ɾ�����û��������ɫ��������������Ϣ
			String sql1="delete from a03 where uid="+uid;
			String sql2="delete from u_r_relation where rid='3' and uid="+uid;
			String sql3="update a01 set uid3='0' where uid3="+uid;
			this.executeUpdate(sql1);
			this.executeUpdate(sql2);
			this.executeUpdate(sql3);
			//��ѯ���û��Ƿ���������ɫ
			String sql4="select rid from u_r_relation where uid="+uid;
			List<Map<String,String>> list=this.queryForList(sql4);
			String sql5="delete from a06 where uid="+uid;
			String sql6="delete from user where uid="+uid;
			if(list.size()!=0)
			{
				//����������ɫ����ɾ��������ɫ��Ӧu_r_relation�����ݱ�(��ʦ)
				for(Map<String,String> m:list)
				{
					if(m.get("rid").equals("2"))
					{
						//ɾ��a02����
						String sql7="delete from a02 where uid="+uid;
						this.executeUpdate(sql7);
					}
				}
				String sql8="delete from u_r_relation where uid="+uid;
				//ɾ��u_r_relation����
				this.executeUpdate(sql8);
			}
			//ɾ��a06,user������
			this.executeUpdate(sql5);
			this.executeUpdate(sql6);
			DBUtils.commit();
			tag=true;
		}
		catch (Exception e) 
		{
			DBUtils.rollback();
			e.printStackTrace();
		}
		finally
		{
			DBUtils.endTransaction();
		}
		return tag;
	}

	
	/*****************************************************************************/
	/*****************************************************************************/
	/*****************************************************************************/
	
	//����Excel
	public boolean secAddByExcel(String path) throws Exception 
	{
		//��������
		DBUtils.beginTransaction();
		boolean tag=false;
		Map<String,String>map=new HashMap<>();
		List<Map<String,String>>list=readXls(path);
		try
		{
			for (int i = 0; i < list.size(); i++) 
			{
				map=list.get(i);
		    	//��ȡ��ǰԱ���û���//2��ʾΪ��ְ��
				String uname=Tools.getFormatNumber("2","Sta");
				map.put("uname", uname);
				
				//��DTO���Ա���û���
				StringBuilder sql2=new StringBuilder()
						.append(" insert into user(uname,upassword,name,ustate)")
						.append(" values(?,?,?,'1')");
				//��ʼ�������û�����ͬ����һ�ε�¼ʱ�޸�
				Object args2[]={uname,uname,map.get("name")};
				
				this.executeUpdate(sql2.toString(), args2);
				
				String sqlForId="select uid from user where uname=?";
				Map<String,String> m=this.queryForMap(sqlForId.toString(), map.get("uname"));
			    
				//��ר�ұ�(a06)�������
				StringBuilder sql3=new StringBuilder()
		    			.append("insert into a06(uid,a601,a602,a603,a604,a605,a608,a609)")
		    			.append("         values(?,?,?,?,")
		    			.append("                ?,?,?,")
		    			.append("                ?)")
		    			;
				Object args3[]={
						m.get("uid"),
						map.get("a601"),//����
						"1",//a602Ĭ��ΪУ��ר��(ר�����)
						map.get("a603"),//�о�����
						map.get("a604"),//ְ��
						map.get("a605"),//���֤����
						map.get("a608"),//�ֻ�����
						map.get("a609"),//����
				};
				this.executeUpdate(sql3.toString(),args3);
				
				//�������(a03)�������
				String sql4="insert into a03(uid) select uid from user where uname=?";
				this.executeUpdate(sql4,map.get("uname"));
				
				//���û�-��ɫ��(u_r_relation)�������
				String sql5="insert into u_r_relation (uid,rid,u_r_state) values(?,?,'1')";
				Object args[]={m.get("uid"),"3"};
				//3��ʾ��ɫΪ����
				this.executeUpdate(sql5,args);
				
				//���֤����
				if(this.isAdded()==true)
				{
					DBUtils.rollback();
				}
				else
				{
					//�ύ����
					DBUtils.commit();
					tag=true;
				}
			}
		}
		catch(Exception e)
		{
			//�ع�����
			DBUtils.rollback();
			e.printStackTrace();
		}
		finally
		{
			//��������
			DBUtils.endTransaction();
		}
		return tag;
	}
	

	protected List<Map<String,String>>readXls(String path)throws Exception 
	{
		//��Ҫ�����Excel�ļ����ȴ����WEB-INF�ļ����£���׺��ʱֻ��xls
		//String path="D:/work/project/WebRoot/WEB-INF/sec_info.xls";
		InputStream is=new FileInputStream(path);
		HSSFWorkbook hssfWorkbook=new HSSFWorkbook(is);
		List<Map<String,String>> list=new ArrayList<>();
		//ѭ��������Sheet
		for (int numSheet=0;numSheet<hssfWorkbook.getNumberOfSheets();numSheet++)
		{
			HSSFSheet hssfSheet=hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet==null) 
			{
				continue;
			}
			//ѭ����Row
			for (int rowNum=1;rowNum<=hssfSheet.getLastRowNum();rowNum++) 
			{
				HSSFRow hssfRow=hssfSheet.getRow(rowNum);
				//��ÿһ�ж������ݱ�����list��
				if (hssfRow!=null)
				{
					Map<String,String> map=new HashMap<>();
					//�����Ҫ����������������
					HSSFCell name=hssfRow.getCell(0);
					HSSFCell a601=hssfRow.getCell(1);
					HSSFCell a603=hssfRow.getCell(2);
					HSSFCell a604=hssfRow.getCell(3);
					HSSFCell a605=hssfRow.getCell(4);
					HSSFCell a608=hssfRow.getCell(5);
					HSSFCell a609=hssfRow.getCell(6);
					//��Excel��ͷ��ȡ���ݣ��ϸ�Ҫ���ͷ�����ݿ��������һһ��Ӧ
					map.put("name", getValue(name));
					map.put("a601", getValue(a601));
					map.put("a603", getValue(a603));
					map.put("a604", toChar(getValue(a604)));
					map.put("a605", getValue(a605));
					map.put("a608", getValue(a608));
					map.put("a609", getValue(a609));
					list.add(map);
			 	}
			}
		}
		return list;
	}
	
	//��syscodeת��ְ��
	private String toChar(String s)
	{
		if(s.equals("����"))
		{
			return "1";
		}
		else if(s.equals("������"))
		{
			return "2";
		}
		else
		{
			return "3";
		}
	}

	@SuppressWarnings("static-access")
	private String getValue(HSSFCell hssfCell)
	{
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) 
	    {
	        // ������ֵ���͵�ֵ
			//hssfCell.getNumericCellValue()����double���ͣ���Ŀ������ɿ�ѧ����
	        return toVarchar(hssfCell.getNumericCellValue());
	    }
		else
	    {
	        // �����ַ������͵�ֵ
	        return String.valueOf(hssfCell.getStringCellValue());
	    }
	}	
	
	//��Doubleת��ΪString�����ұ����ѧ����
	private String toVarchar(double d)
	{
		BigDecimal bigDecimal = new BigDecimal(d);
		return bigDecimal.toString();
	}
	
	//�������
	private boolean isAdded()throws Exception
	{
		PreparedStatement pstm1=null;
		PreparedStatement pstm2=null;
		ResultSet rs1=null;
		ResultSet rs2=null;
		try
		{
			//�����֤�������
			String sql1="select count(a605) as a from a06;";
			String sql2="select count(distinct a605) as a from a06;";
			
			pstm1=DBUtils.prepareStatement(sql1);
			pstm2=DBUtils.prepareStatement(sql2.toString());
			rs1=pstm1.executeQuery();
			rs2=pstm2.executeQuery();
			rs1.next();
			rs2.next();
			//ͨ��distinctɾ���ظ����ݣ��ж��������count��ֵ�Ƿ���ͬ����ͬ��˵��û���ظ�����
			if(rs1.getObject(1).equals(rs2.getObject(1)))
			{
				//û�����ظ�
				return false;
			}
			else
			{
				return true;
			}
		}
		finally
		{
			DBUtils.close(rs2);
			DBUtils.close(rs1);
			DBUtils.close(pstm2);
			DBUtils.close(pstm1);
		}
		
	}
}
