package com.car.collection.details;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/rs1")
public class carDetailsOne extends HttpServlet {
	
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			System.out.println("Proccess start");
		      String crs=req.getParameter("crs1");
		      int i=Integer.parseInt(crs);
		     
			try {
				System.out.println("star getting details of car from database");
				Class.forName("com.mysql.jdbc.Driver");
				Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/car_details","root","root");
				PreparedStatement pstmt=con.prepareStatement("SELECT * FROM details WHERE Number=?");
				pstmt.setInt(1, i);
				ResultSet rs=pstmt.executeQuery();
				System.out.println("complete");
				PrintWriter pw=resp.getWriter();
		    	resp.setContentType("text/html");
		    	if(rs.next()) {
		    		int no=rs.getInt("Number");
		    		
		    		String name=rs.getString("Name");
		    		String model=rs.getString("Model");                                                                                         
		    		String price=rs.getString("Price");
		    		
				pw.println("<body bgcolor='cyan' align='center'><p><font color='black'><h1> "+ no+" "+name+" "+model+" "+price+" </body>");
				}else {
					pw.println("<body bgcolor='skyblue'><h1><font color='black'> Information not available !! Please use another Id number. </h1></body>");
					RequestDispatcher rd=req.getRequestDispatcher("car.html");
					rd.include(req,resp);
				}
			}catch(Exception e) {
				System.out.println(e);
			}
		      
		}
}
