package edu.whu.services.impl;

import java.sql.PreparedStatement;
import edu.whu.system.db.DBUtils;
import edu.whu.system.tools.News;

public class TimeSetImpl
{
	  public void timeclear()throws Exception
	  {	  	
		 PreparedStatement pstm=null;

		 String sql = "update a02,a03,a05 set a02.a201=null,a03.a301=null,a05.a504=null ";

		 pstm=DBUtils.prepareStatement(sql.toString());
		 pstm.execute();
		 String title = "���ʱ���޸�";
		 String text = "����ʱ���Ѿ��޸�,���λ��ʦ���찲�ſ���ʱ��";
		 News.sendNews(title, text, "allsec");
		 News.sendNews(title, text, "alltea");
		 News.sendNews(title, text, "allexp");
		 
	  }
    
}