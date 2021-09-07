package edu.whu.web;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import edu.whu.services.impl.OutputExcelServicesImpl;
import edu.whu.system.db.DBUtils;
import edu.whu.web.BaseServlet;

@WebServlet("/financialExcelForInner.xls")
public class FinancialExcelForInnerServlet extends BaseServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html;charset=utf-8");
		ResultSet rs=null;
        try 
        {
        	OutputExcelServicesImpl servicesImpl=new OutputExcelServicesImpl();
        	//������ݿ�����
            Workbook wb=new HSSFWorkbook();
            //Ԥ���趨����
            String headers[]={"ר������","����ԺУ","����������","���ų��"};
            //�õ������
            rs=servicesImpl.financialListForInner();
            servicesImpl.fillExcelData(rs,wb,headers);
            export(response,wb,"���񱨱�(У��).xls");
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally
        {
        	DBUtils.close(rs);
        }
	}

    /**
     * �����ݷ��뵽.xls�ļ��в����ص�����
     * 
     * @param response
     * @param wb
     * @param fileName
     * @throws Exception
     */
    public void export(HttpServletResponse response,Workbook wb,String fileName)throws Exception 
    {
    	//����ͷ��Ϣ
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String(fileName.getBytes("utf-8"), "iso8859-1"));
        response.setContentType("application/ynd.ms-excel;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        //������������ص�����
        wb.write(out);
        out.flush();
        out.close();
    }
	
}
