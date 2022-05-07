package com.admin;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/FileUploadServlet")
@MultipartConfig(
        fileSizeThreshold   = 1024 * 1024 * 1,  // 1 MB
        maxFileSize         = 1024 * 1024 * 10, // 10 MB
        maxRequestSize      = 1024 * 1024 * 15 // 15 MB
        //location            = "D:/Uploads"
)
public class Admin_Controller extends HttpServlet {
	//private static final long serialVersionUID = 1L;

	public Admin_Controller() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			String fileName = "";
		
			 for (Part part : request.getParts()) {
		            fileName = extractFileName(part);
		            fileName = new File(fileName).getName();
		        }
			
			 String fullPath = getServletContext().getRealPath("/txt_file/");
			
			    for (Part part : request.getParts()) {
			      part.write(fullPath + fileName);
			    }
			 
		    RequestDispatcher dispatcher = request.getRequestDispatcher("Home");
	        dispatcher.forward(request, response);

		} catch (Exception ex) {
			ex.getMessage();
		}

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}
	
	private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }

}
