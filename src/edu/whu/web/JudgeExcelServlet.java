package edu.whu.web;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import edu.whu.services.impl.ExpExcelServiceImpl;
import edu.whu.services.impl.JudgeExcelServicesImpl;
import edu.whu.system.tools.News;
import edu.whu.web.support.BaseController;
import sun.misc.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("*.htmn")
public class JudgeExcelServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		List<Map<String, Object>> dtos = this.createExcel(request);
		String toPath = null; // ��ת��Ŀ��ҳ��
		try 
		{

			/************************************************************
			 * ��������·��,��ȡĿ���������
			 ************************************************************/
			// ��������ķ���·��
			String uri = request.getRequestURI();
			// ��ȡ������Դ�����ļ���
			String baseName = uri.substring(uri.lastIndexOf("/") + 1).replace(".htmn", "");

			// �������,��������ҵ��������Ļ���������
			String basePackageName = "edu.whu.web.impl.";
			// ��ȡ��������ǰ׺��
			String controllerFirstName = baseName.substring(0, 1).toUpperCase() + baseName.substring(1);

			/***********************************************************
			 * ʵ����Ŀ����---ҵ�������
			 ***********************************************************/
			// ʵ����ҵ�������
			System.out.println(basePackageName + controllerFirstName + "Servlet");
			BaseController controller = (BaseController) Class
					.forName(basePackageName + controllerFirstName + "Servlet").newInstance();

			/***********************************************************
			 * ��ҵ�������,���ҳ������ i
			 ***********************************************************/
			JudgeExcelServicesImpl jesi = new JudgeExcelServicesImpl();
			jesi.deletejudge();
			for (Map<String, Object> dto : dtos) 
			{
				controller.setDto(dto);

				/***********************************************************
				 * ����ҵ������������̿��Ʒ���
				 ***********************************************************/
				// �������̿��Ʒ���
				toPath = controller.execute();
			}
			String title = "���������ѷ���";
			String text = "������Ҫ����������Ѿ��������,���λ����ר�Ҿ����������";
			News.sendNews(title, text, "alljexp");
		} 
		catch (Exception ex) 
		{
			request.setAttribute("msg", "��ʾ:�������!");
			ex.printStackTrace();
		}
		request.getRequestDispatcher("/" + toPath + ".jsp").forward(request, response);
	}

	private List<Map<String, Object>> createExcel(HttpServletRequest request)
	{
		List<Map<String, Object>> dtos = new ArrayList<Map<String, Object>>();
		try 
		{
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);

			request.setCharacterEncoding("GBK");
			// �ļ����������봦��Ҳ�������д
//	       upload.setHeaderEncoding("utf-8");

			// ���û�������С����ʱ�ļ�Ŀ¼
			factory.setSizeThreshold(1024 * 1024 * 10);
			File uploadTemp = new File("e:\\uploadTemp");
			uploadTemp.mkdirs();
			factory.setRepository(uploadTemp);

			// ���õ����ļ���С����
			upload.setFileSizeMax(1024 * 1024 * 10);
			// ���������ļ��ܺʹ�С����
			upload.setSizeMax(1024 * 1024 * 30);
			String filName = null;
			String uploadPath = null;
			try
			{
				List<FileItem> list = upload.parseRequest(request);
				for (FileItem fileItem : list)
				{
					if (!fileItem.isFormField() && fileItem.getName() != null && !"".equals(fileItem.getName())) 
					{
						filName = fileItem.getName();
						if(filName.contains("\\"))
						{
							filName = filName.substring(filName.lastIndexOf("\\"));
						}
						// ��ȡ�ļ��ϴ�Ŀ¼·��������Ŀ����·���µ�uploadĿ¼����������������ֱ�ӷ��ʵ�ͼƬ�����Է���WEB-INF��
						uploadPath = request.getSession().getServletContext().getRealPath("/upload");
						System.out.println(uploadPath + filName);
						File file = new File(uploadPath);
						file.mkdirs();
						// д���ļ������̣�����ִ����Ϻ����и���ʱ�ļ��������Զ�ɾ��
						fileItem.write(new File(uploadPath, filName));

					}
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			/*
			 * ����excel
			 */
			String filePath = uploadPath + "\\" + filName;
			InputStream excelfile = new FileInputStream(filePath);// ��ȡָ��λ���е��ļ������Ǹղű�����ָ���ļ����е��ϴ��ļ�

			Workbook wb = null;
			// if(filePath.endsWith(".xlsx")){
			// System.out.println(filePath.endsWith(".xlsx"));
			// wb = new XSSFWorkbook(excelfile);//07��
			// System.out.println("\n111111111111111111111");

			// }else{

			wb = new HSSFWorkbook(excelfile);// 03��

			// }

			// ��ʼ��������
			Sheet sheet = null; // excel������sheet
			Row row = null; // excel��������

			sheet = wb.getSheet("ѧ����"); // ��ȡxssfworkbook����--�Դ˵õ�sheet(������)���󣺱������������������
			// �ڶ���
			// �жϻ�ȡ�Ĺ������������������������Ƿ�Ϊ��
			// ʹ�ù�����֮ǰҪ����ж����Ƿ�Ϊnull������ᱨ��ָ���쳣
			sheet = wb.getSheetAt(0);// ��ȡ�±�Ϊ0�ı�(Ҳ���ǵ�һ�ű�sheet1)
			if (sheet == null) 
			{
				System.out.println("������0��Ĭ����Ϊ Sheet1 ��Ϊ��");
				try 
				{
					throw new Exception("������Ϊ�գ�");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// ��������������ڶ����Ļ����Ͻ��У�:��ȡһ��������������
			// ����
			int allRowNum = sheet.getLastRowNum(); // ��ȡһ��������,��Ϊ��0�п�ʼ����ģ����Բ�+1����һ��

			// ����
			row = sheet.getRow(0);
			// System.out.println("��⣺"+row); // ��ʼ��: A1,B1,C1,,,M1

			int allCellNum1 = row.getLastCellNum();
			// int allCellNum2 = Row.getPhysicalNumberOfCells();

			// System.out.println("��Ч�����У�"+allCellNum2);
			// ��ʼһ��һ�н���---һ�н��������ľ��Ǹ�����----------ȫ������һ��list����
			// ������
			List<String> paras = new ArrayList<String>();
			List<String> eparas = new ArrayList<String>();
			Map<String, Object> dto = null;

			for (int j1 = row.getFirstCellNum(); j1 < allCellNum1; j1++) 
			{
				Cell cell1 = row.getCell(j1);// ��ȡ��ǰ�еĵ�Ԫ��
				paras.add(cell1.toString());

			}
			System.out.println("paras = "+paras);
			for (int i2 = 1; i2 <= sheet.getLastRowNum(); i2++) 
			{
				
				// ��ȡ��ǰ��
				row = sheet.getRow(i2);

				if (null == row) 
				{
					// row="";
					continue;
				}
				// ��Ԫ���б�
				Cell ecell = row.getCell(0);
				
				eparas.add(getValue(ecell));
				System.out.println(eparas);
				for (int j1 = row.getFirstCellNum()+1; j1 < allCellNum1; j1++) 
				{
					
					Cell cell2 = row.getCell(j1);// ��ȡ��ǰ�еĵ�Ԫ��
					if(cell2==null)
					{
						continue;
					}	
					if(getValue(cell2).equals("��"))
					{
						dto = new HashMap<>();
						dto.put("b102",eparas.get(i2-1));
						dto.put("ename",paras.get(j1));
						dtos.add(dto);
					}
					
				}
				System.out.println("dto = "+dto);
				
			}

			System.out.println("dtos = "+dtos);
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		return dtos;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		doPost(request, response);
	}

	private String getValue(Cell hssfCell) 
	{
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BLANK) 
		{
			// ������ֵ���͵�ֵ
			// hssfCell.getNumericCellValue()����double���ͣ���Ŀ������ɿ�ѧ����
			return " ";
		} 
		else
		{
			// �����ַ������͵�ֵ
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	private String toVarchar(double d) 
	{
		BigDecimal bigDecimal = new BigDecimal(d);
		return bigDecimal.toString();
	}

}