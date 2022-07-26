package com.car.collection.details;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.print.DocFlavor.INPUT_STREAM;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;



public class CarRecords extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
    	
		String no=req.getParameter("no");
		int no1=Integer.parseInt(no);
		String name=req.getParameter("cn");
		String model=req.getParameter("cm");                                                                                         
		String price=req.getParameter("cp");
		String img=req.getParameter("img");
		File theFile=new File(img);
		
		FileInputStream input=new FileInputStream(theFile);
		PrintWriter ref=resp.getWriter();
		
		System.out.println("CarRecord servlet start working");
		
		if(no!="" && name!="" && model!="" && price!="") {
			            
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_details","root","root");
					PreparedStatement pstmt=con.prepareStatement("INSERT INTO Details(Number,Name,Model,Price,Image) VALUES(?,?,?,?,?)");
					pstmt.setInt(1,no1);
					pstmt.setString(2, name);
					pstmt.setString(3, model);
					pstmt.setString(4, price);
					pstmt.setBlob(5, input);
					System.out.println("Reading input file: "+theFile.getAbsolutePath());
					pstmt.executeUpdate();
					System.out.println("All values are taken by client");
			        ref.print("<html><body bgcolor='skyblue'><h1>Car Details are:-"+no1+".       | Name: "+name+"      | Model: "+model+"       | Price: "
					+ ""+price+"</h1><h2>Information Submitted Successfully</h2><p>Do another work</p></body></html>");
			        RequestDispatcher rd=req.getRequestDispatcher("car.html");
				    rd.include(req,resp);
					System.out.println("Information submitted");
				} catch (Exception e) {
					
					ref.print("<html><body bgcolor='skyblue'><font color=black><h1>This Information Already exist!! Use another Number.</h1></body></html>");
				    RequestDispatcher rd=req.getRequestDispatcher("car.html");
				    rd.include(req,resp);
					e.printStackTrace();
				}
		}else {
			ref.print("<html><body bgcolor='skyblue'><font color=black><h1>Please Fill All Details Properly</h1></body></html>");
	    RequestDispatcher rd=req.getRequestDispatcher("car.html");
	    rd.include(req,resp);
		}
			
				
			
	}
	
	
}
