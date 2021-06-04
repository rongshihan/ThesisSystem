package edu.whu.system.tools;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import edu.whu.system.db.DBUtils;

import java.sql.*;


public class Tools {
	public Tools() {}
	
	public static String joinArray(Object o) {
		//��ֵ����
		if(o==null || o.equals(""))
		{
			return "";
		}
		//�ж�element������ʲô?���ַ��������ַ�������
		if(o instanceof java.lang.String[])
		{
			//������ת�����ַ�������
			String arr[]=(String[])o;
			//�������鳤��
			int len=arr.length;
			//���춯̬�ַ�������,װ�������ÿ��Ԫ��
			StringBuilder tem=new StringBuilder(arr[0]);
			//ѭ����ȡ����ĺ��Ԫ��
			for(int i=1;i<len;i++)
			{
				tem.append(",").append(arr[i]);
			}
			return tem.toString();
		}
		else
		{
			return o.toString();
		}	
	}
	
	
	//��ȡ����
	private static String getCurrentYear() {
//		//���������ڸ�ʽ
//		SimpleDateFormat spf= new SimpleDateFormat("yyyy");
//		//��������
//		Date date = new Date();
//		//�ü����ڸ�ʽ��������
//		String currentYear = spf.format(date);
//		return currentYear;
		
		return new SimpleDateFormat("yyyy").format(new Date());
	}
	
	//��ȡβ��
	private static int getNumnberForYear(String firstName) throws Exception{
		PreparedStatement ps1=null;
		PreparedStatement ps2=null;
		ResultSet set=null;
		try {
			StringBuilder sql1 = new StringBuilder();
			sql1.append("select pkvalue from sequence where date_format(sdate,'%Y')"
						+ "=date_format(current_date(),'%Y') and pkname=?");
			ps1=DBUtils.prepareStatement(sql1.toString());
			ps1.setObject(1, firstName);
			set=ps1.executeQuery();
			
			int currentVal=0;
			StringBuilder sql2=new StringBuilder();
			if(set.next())
			{
				currentVal=set.getInt(1);
				sql2.append("update sequence ")
				    .append("   set pkvalue=?")
				    .append(" where date_format(sdate,'%Y')=date_format(current_date,'%Y')")
				    .append("   and pkname=?");
			}
			else
			{
				sql2.append("insert into sequence(pkvalue,pkname,sdate)")
				    .append("              values(?,?,current_date)")
				;
			}
			ps2=DBUtils.prepareStatement(sql2.toString());
			ps2.setObject(1, ++currentVal);
			ps2.setObject(2, firstName);
			ps2.executeUpdate();
			return currentVal;
		}finally{
			DBUtils.close(set);
			DBUtils.close(ps1);
			DBUtils.close(ps2);
		}
	}
	
	//��ȡ��λ
	public static String getFormatNumber(String firstName) throws Exception{
		//1.��ȡ��ˮ��
		int lastNumber=Tools.getNumnberForYear(firstName);
		//2.������ˮ�ŵĿ��
		int size=String.valueOf(lastNumber).length();
		
		return Tools.getCurrentYear()+new String("0000").substring(size)+lastNumber;
	}
}
