package edu.whu.system.tools;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import edu.whu.system.db.DBUtils;

import java.security.MessageDigest;
import java.sql.*;

public class Tools {
	public Tools() {
	}

	public static String joinArray(Object o) {
		// ��ֵ����
		if (o == null || o.equals("")) {
			return "";
		}
		// �ж�element������ʲô?���ַ��������ַ�������
		if (o instanceof java.lang.String[]) {
			// ������ת�����ַ�������
			String arr[] = (String[]) o;
			// �������鳤��
			int len = arr.length;
			// ���춯̬�ַ�������,װ�������ÿ��Ԫ��
			StringBuilder tem = new StringBuilder(arr[0]);
			// ѭ����ȡ����ĺ��Ԫ��
			for (int i = 1; i < len; i++) {
				tem.append(",").append(arr[i]);
			}
			return tem.toString();
		} else {
			return o.toString();
		}
	}

	
	/**
	 * ��ȡԱ����ˮ��
	 * @return  String number="yyyy"+4λ��ˮ��
	 * @throws Exception
	 */
	// ��ȡ����
	private static String getCurrentYear() 
	{
//		//���������ڸ�ʽ
//		SimpleDateFormat spf= new SimpleDateFormat("yyyy");
//		//��������
//		Date date = new Date();
//		//�ü����ڸ�ʽ��������
//		String currentYear = spf.format(date);
//		return currentYear;

		return new SimpleDateFormat("yyyy").format(new Date());
	}

	// ��ȡβ��
	private static int getNumnberForYear(String firstName) throws Exception
	{
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet set = null;
		try {
			StringBuilder sql1 = new StringBuilder();
			sql1.append("select pkvalue from sequence where date_format(sdate,'%Y')"
					+ "=date_format(current_date(),'%Y') and pkname=?");
			ps1 = DBUtils.prepareStatement(sql1.toString());
			ps1.setObject(1, firstName);
			set = ps1.executeQuery();

			int currentVal = 0;
			StringBuilder sql2 = new StringBuilder();
			if (set.next()) {
				currentVal = set.getInt(1);
				sql2.append("update sequence ").append("   set pkvalue=?")
						.append(" where date_format(sdate,'%Y')=date_format(current_date,'%Y')")
						.append("   and pkname=?");
			} else {
				sql2.append("insert into sequence(pkvalue,pkname,sdate)")
						.append("              values(?,?,current_date)");
			}
			ps2 = DBUtils.prepareStatement(sql2.toString());
			ps2.setObject(1, ++currentVal);
			ps2.setObject(2, firstName);
			ps2.executeUpdate();
			return currentVal;
		} finally {
			DBUtils.close(set);
			DBUtils.close(ps1);
			DBUtils.close(ps2);
		}
	}
	
	// ��ȡ��λ
	public static String getFormatNumber(String rid,String firstName) throws Exception
	{
		// 1.��ȡ��ˮ��
		int lastNumber = Tools.getNumnberForYear(firstName);
		// 2.������ˮ�ŵĿ��
		int size = String.valueOf(lastNumber).length();

		return Tools.getCurrentYear() +rid+ new String("0000").substring(size) + lastNumber;
	}

	/***************************************************************************
	 * MD5Begin
	 ***************************************************************************/

	public static String getMd5(Object pwd) throws Exception
	{
		/**
		 * MD5���λ�������
		 */
		// ���ԭʼ����
		String md5pwd1 = Tools.MD5Encode(pwd);
		// ���ɻ�������
		String pwd2 = md5pwd1 + "�����ե㥥�����ɦ������ը㡦��������:�ԟo�ˤϤ�����èبר�Ŧؤ������з�,�ԟo��Ƨާ�٧��ޞ�����" + md5pwd1;
		String md5pwd2 = Tools.MD5Encode(pwd2);
		return md5pwd2;

	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	/**
	 * ת���ֽ�����Ϊ16�����ִ�
	 * 
	 * @param b �ֽ�����
	 * @return 16�����ִ�
	 */
	private static String byteArrayToHexString(byte[] b) 
	{
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * ת���ֽ�Ϊ16�����ַ���
	 * 
	 * @param b byte
	 * @return String
	 */
	private static String byteToHexString(byte b)
	{
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * �õ�MD5����������
	 * 
	 * @param origin String
	 * @throws Exception
	 * @return String
	 */
	private static String MD5Encode(Object origin) throws Exception
	{
		String resultString = null;
		try {
			resultString = new String(origin.toString());
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			return resultString;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public static void main(String[] arg)
	{
		try 
		{
			System.out.println(Tools.getMd5("201910001"));
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/***************************************************************************
	 * MD5End
	 ***************************************************************************/
	public static Date addDate(Date a) throws Exception
	{
		Format f = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(a);
		c.add(Calendar.DAY_OF_MONTH, 1);// ����+1��

		Date tomorrow = c.getTime();
		return tomorrow;
	}

	public static String outputDate(Date a) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		String format = sdf.format(a);
		return format;
	}

	public static long datesub(Date a, Date b) throws Exception
	{
		long betweenDate = (b.getTime() - a.getTime()) / (60 * 60 * 24 * 1000) + 1;

		return betweenDate;
	}
}
