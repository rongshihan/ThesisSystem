package edu.whu.services.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import edu.whu.services.support.JdbcServicesSupport;
import edu.whu.system.db.DBUtils;
import edu.whu.system.tools.Tools;

public class StuDistributeServicesImpl extends JdbcServicesSupport 
{
	//ȫ��ѧ������
	public boolean distributeStu() throws Exception
	{
		//��ȡÿ����ʦ��uid��ÿһ��ѧ����������ɵ�Map
		Map<String,Integer> map=new HashMap<String,Integer>();
		String sql1="select uid2, count(uid2) num from a01 group by uid2;";
		List<Map<String,String>> list1=this.queryForList(sql1);
		for(Map<String,String> m:list1)
		{
			String key=m.get("uid2");
			Integer num=Integer.parseInt(m.get("num"));
			map.put(key, num);
		}
		//�õ��������е�List<Entry>
		List<Entry<String,Integer>> sortedlist=this.sortMap(map);
		int groupSize=sortedlist.size();
		int stuSize=0;
		for(Entry<String,Integer> e:sortedlist)
		{
			stuSize+=e.getValue();
		}
		//��ȡ����uid�б�
		String sql2="select uid from a03;";
		List<Map<String,String>> list2=this.queryForList(sql2);
		List<String> secIDList=new ArrayList<String>();
		for(Map<String,String> m:list2)
		{
			secIDList.add(m.get("uid"));
		}
		int secSize=secIDList.size();
		//����ÿ���������ѧ�������½�
//		int aveStuNum=stuSize/secSize;
//		int minStuNum=stuSize/secSize-5;
		int maxStuNum=stuSize/secSize+5;
		//Ϊÿ�����������½緶Χ�ڷ���ѧ��
		//��ǰ������
		int groupNum=0;
L1:		for(int i=0;i<secSize;i++)
		{
			int disNum=0;
			while(disNum<maxStuNum && groupNum<groupSize)
			{
				Entry<String,Integer> e=sortedlist.get(groupNum);
				if(disNum+e.getValue()<maxStuNum)
				{
					String uid2=e.getKey();
					disNum+=e.getValue();
					String tsql="update a01 set uid3="+secIDList.get(i)+" where uid2="+uid2;
					this.executeUpdate(tsql);
					groupNum++;
				}
				else
				{
					continue L1;
				}
			}
		}
		return true;
	}
	
	public Object showDisResult() throws Exception
	{
		String sql="select uid3,count(uid3) num from a01 group by uid3";
		List<Map<String,String>> list=this.queryForList(sql);
		Map<String,Integer> result=new HashMap<>();
		for(Map<String,String> m:list)
		{
			result.put(m.get("uid3"), Integer.parseInt(m.get("num")));
		}
		return result;
	}
	
	public List<Map<String,String>> query() throws Exception
	{
		String sql1="set @mycnt=0;";
		DBUtils.prepareStatement(sql1).executeQuery();
		String sql="select @mycnt:=@mycnt+1 as num,w.name aname,v.name bname,u.name cname,a.a101 "
				 + "from a01 a,user u,user v,user w where a.uid=u.uid and v.uid=a.uid2 and w.uid=a.uid3 ";
		return this.queryForList(sql);
	}
	
	//ѧ����������
	public ResultSet outPutConResult() throws SQLException, Exception
	{
		String sql="select w.name ��������,v.name ��ʦ����,u.name ѧ������ "
				+ "from a01 a,user u,user v,user w where a.uid=u.uid and v.uid=a.uid2 and w.uid=a.uid3;";
		return DBUtils.prepareStatement(sql).executeQuery();
	}
	
	//map���򷽷�
	private List<Entry<String,Integer>> sortMap(Map<String,Integer> map)
	{
        int size = map.size();
        // ͨ��map.entrySet()��mapת��Ϊ"key=number"��ʽ��list����
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(size);
        list.addAll(map.entrySet());
        // ͨ��Collections.sort()����
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2)
            {
                // compareTo���� (x < y) ? -1 : ((x == y) ? 0 : 1)
                return o1.getValue().compareTo(o2.getValue());
            };
        });
        Collections.reverse(list);
        return list;
	}
	
    /**
     * �����û�
     * 
     * @throws Exception
     */
    public void fillExcelData(ResultSet rs,Workbook wb,String[]headers)throws Exception 
    {
        int rowIndex=0; //��һ��
        Sheet sheet=wb.createSheet(); //����sheetҳ
        Row row=sheet.createRow(rowIndex++);
        //��������
        for (int i=0;i<headers.length;i++) 
        {
            row.createCell(i).setCellValue(headers[i]);
        }
        //�������ݿ��е�����
        while(rs.next())
        {
            row=sheet.createRow(rowIndex++);
            for (int i=0;i<headers.length;i++)
            {
            	//rs.getObject(i + 1)�õ�һ�����󣬼����ݿ���һ�еĽ����ÿһ�о������Դճ�һ�б�ɶ�����Ϊid�Ǵ�1��ʼ������Ҫ+1��
                row.createCell(i).setCellValue(rs.getObject(i+1).toString());
            }
        }
    }
}
