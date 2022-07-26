package com.car.collection.details;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class carDetails extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	      String crs=req.getParameter("crs");
	      if(crs.equals("Result")) {
		try {
			System.out.println("star getting details of car from database");
			Class.forName("com.mysql.jdbc.Driver");
			Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/car_details","root","root");
			PreparedStatement pstmt=con.prepareStatement("SELECT * FROM Details");
			ResultSet rs=pstmt.executeQuery();
			PrintWriter pw=resp.getWriter();
	    	resp.setContentType("text/html");
	    	while(rs.next()) {
	    		int no=rs.getInt("Number");
	    		
	    		String name=rs.getString("Name");
	    		String model=rs.getString("Model");                                                                                         
	    		String price=rs.getString("Price");
	    		
	    		Blob bl=rs.getBlob("Image");
	    		if(bl!=null) {
	    		InputStream is=bl.getBinaryStream();
	    		ByteArrayOutputStream bao=new ByteArrayOutputStream();
	    		byte [] buffer=new byte[4096];
	    		int bytesRead=-1;
	    		while((bytesRead=is.read(buffer))!=-1) {
	    			bao.write(buffer,0,bytesRead);
	    		}
	    		byte[] imgbyte=bao.toByteArray();
	    		String base64img=Base64.getEncoder().encodeToString(imgbyte);
	    		//byte b[]=bl.getBytes(7, (int)bl.length());
	    		//fos.write(b);
	    		
			pw.println("<body bgcolor='cyan' align='center'><div align=center><table align='center'>\r\n"
					+ "      <tr>\r\n"
					+ "        <th>Number</th>\r\n"
					+ "        <th>C.NAME</th>\r\n"
					+ "        <th>MODEL</th>\r\n"
					+ "        <th>PRICE</th>\r\n"
					+ "      </tr>\r\n"
					+ "      <tr>\r\n"
					+ "        <td>"+ no+"</td>\r\n"
					+ "        <td>"+name+"</td>\r\n"
					+ "        <td>"+model+"</td>\r\n"
					+ "        <td>"+price+"</td>\r\n"
					+ "      </tr>\r\n"
					+ "    </table> <img src=data:image/jpg;base64,"+base64img+" width=500px/></div> </body>");
			}
	    	}
		}catch(Exception e) {
			System.out.println(e);
		}finally {
			RequestDispatcher rd=req.getRequestDispatcher("car.html");
			rd.include(req, resp);
		}
	      }
	      
	}
	@Override
	public void destroy() {
		System.out.println("cleanup activities");
	}
}
