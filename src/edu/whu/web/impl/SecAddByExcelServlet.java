package edu.whu.web.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import edu.whu.services.impl.AdminServicesImpl;

@WebServlet("/SecAddByExcel.com")
public final class SecAddByExcelServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	// �ϴ��ļ��洢Ŀ¼
    private static final String UPLOAD_DIRECTORY = "excel";
 
    // �ϴ�����
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    /**
     * �ϴ�����
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		// �����ϴ�����
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // ������ʱ�洢Ŀ¼
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // ��������ļ��ϴ�ֵ
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // �����������ֵ (�����ļ��ͱ�����)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        
        // ���Ĵ���
        upload.setHeaderEncoding("GBK"); 

        // ������ʱ·�����洢�ϴ����ļ�
        // ���·����Ե�ǰӦ�õ�Ŀ¼
        String uploadPath = getServletContext().getRealPath("/") + UPLOAD_DIRECTORY;
              
        // ���Ŀ¼�������򴴽�
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) 
        {
            uploadDir.mkdir();
        }

        AdminServicesImpl adminServicesImpl = new AdminServicesImpl();
        try 
        {
            // ���������������ȡ�ļ�����
            List<FileItem> formItems = upload.parseRequest(request);
 
            if (formItems != null && formItems.size() > 0) 
            {
                // ����������
                for (FileItem item : formItems)
                {
                    // ������file���е��ֶ�
                    if (!item.isFormField()) 
                    {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // �ڿ���̨����ļ����ϴ�·��
                        System.out.println(filePath);
                        // �����ļ���Ӳ��
                        item.write(storeFile);
                        // ��������
                        adminServicesImpl.secAddByExcel(filePath);
                        // �����ɾ��excel
                        storeFile.delete();
                    }
                }
            }
            List<Map<String,String>> dataList = adminServicesImpl.query();
            if(dataList.size()>0)
            {
            	request.setAttribute("dataList", dataList);
            }
            else 
            {
            	request.setAttribute("msg", "û�з������������ݣ�");
			}
        } 

        catch (Exception ex) 
        {
            request.setAttribute("message","������Ϣ: " + ex.getMessage());
            ex.printStackTrace();
        }
        request.getRequestDispatcher("secAddManage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
