package nosi.core.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

@WebServlet
@MultipartConfig
public class IgrpServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	public IgrpServlet() { super(); }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	nosi.core.webapp.Igrp.getInstance().init(this, request, response).run();
    }

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.processRequest(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}