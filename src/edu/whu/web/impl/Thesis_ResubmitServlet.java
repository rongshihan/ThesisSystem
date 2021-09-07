package edu.whu.web.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import edu.whu.services.impl.B01ImplServices;

@WebServlet("/Thesis_Resubmit.com")
public class Thesis_ResubmitServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	// �ϴ��ļ��洢Ŀ¼
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // �ϴ�����
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    /**
           * �ؽ�����
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
        
        B01ImplServices b01ImplServices = new B01ImplServices();
 
        Map<String, Object> dto=new HashMap<>();
        
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
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
                        //String fileName = new File(item.getName()).getName();
                    	String newFileName = uuid + ".pdf";
                        String newFilePath = uploadPath + File.separator + newFileName;
                        File storeFile = new File(newFilePath);
                        // �ڿ���̨����ļ����ϴ�·��
                        System.out.println("�ϴ��ɹ���·��Ϊ("+newFilePath+")");
                        // �����ļ���Ӳ��
                        item.write(storeFile);
                        // ��file input��name���ļ�����ӵ�dto      
                        dto.put(item.getFieldName().toString(), newFileName);
                    }
                    // �����ı��������
                    else 
                    {
                    	// �ѿؼ���name��value��ӵ�dto
                    	dto.put(item.getFieldName().toString(),item.getString(request.getCharacterEncoding()));
					}
                }
            }
            
            /**
             * ------------------------ɾ���ļ�����----------------------
             */
        	//��ѯ���ı�
        	Map<String, String> thesis=new HashMap<>();
        	thesis=b01ImplServices.queryByID(dto);
        	//�õ��ļ���	
        	String fileName=thesis.get("b106").toString();
        	//����ļ�·��
        	String filePath=uploadPath + File.separator + fileName;
        	//ɾ���ļ�
        	File file=new File(filePath);
        	System.out.println(filePath);
        	if(file.exists()&&file.isFile())
        	{
        		if(file.delete())
        		{
        			System.out.println("ɾ���ɹ���·��Ϊ("+filePath+")");
        		}
        		else 
        		{
					System.out.println("ɾ��ʧ��");
					request.setAttribute("message","�ļ�ɾ��ʧ��!");
					request.getRequestDispatcher("studentUpload.jsp").forward(request, response);
					return;
				}
        	}

            /**
             * ------------------------�޸����ı����----------------------
             */
            Map<String,String> dataMap=b01ImplServices.modify(dto);
    		if(dataMap!=null)
    		{
    			request.setAttribute("msg","�ļ��ϴ��ɹ�!");
    			request.setAttribute("dataMap",dataMap);
    		}
    		else 
    		{
    			request.setAttribute("msg","�ļ��ϴ�ʧ��!");
    		}
        } 
        catch (Exception ex) 
        {
            request.setAttribute("message","������Ϣ: " + ex.getMessage());
            ex.printStackTrace();
        }
        request.getRequestDispatcher("studentUpload.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		doGet(request, response);
	}
}
