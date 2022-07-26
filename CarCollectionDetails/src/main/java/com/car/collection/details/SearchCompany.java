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
import java.sql.SQLException;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/srch")
public class SearchCompany extends HttpServlet{

//	@Override
//		public void init() throws ServletException {
//			System.out.println("Initialization init() call");
//		}
//	@Override
//	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
//		System.out.println("service() call");
//	}
//	
	
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String sr=req.getParameter("Search");
	String md=req.getParameter("model");
	
	
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_details","root","root");
		String base64img=null;
		if(md==""){
				
				PreparedStatement pstmt=con.prepareStatement("SELECT * FROM details WHERE Name=?");
				pstmt.setString(1, sr);
				ResultSet rs=pstmt.executeQuery();
			
				PrintWriter pw=resp.getWriter();
		    	resp.setContentType("text/html");
				while(rs.next()){
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
		    		base64img=Base64.getEncoder().encodeToString(imgbyte);
				}
		    		
		    		
		    		//pw.println("<html><body bgcolor='skyblue'><p><font color='black' size=20px>"+ no+" "+name+" "+model+" "+price+" <img src=data:image/jpg;base64,"+base64img+" width=500px display=flex/></font></p></body></html>");
		    		pw.println("<div align=center>\r\n"
		    				+ "<table border=5px style='width:600px'>\r\n"
		    				+ "<tr>\r\n"
		    				+ "		<th>Number</th>\r\n"
		    				+ "		<th>Name</th>\r\n"
		    				+ "		<th>Model</th>\r\n"
		    				+ "		<th>Price</th>\r\n"
		    				+ "		<th>Image</th>\r\n"
		    				+ "</tr>\r\n"
		    				+ "<tr>\r\n"
		    				+ "         <td>"+no+"</td>\r\n"
		    				+ "         <td>"+name+"</td>\r\n"
		    				+ "         <td>"+model+"</td>\r\n"
		    				+ "         <td>"+price+"</td>\r\n"
		    				+ "         <td><img alt=ImgNotFound src=data:image/jpg;base64,"+base64img+" width=500/><br>"
		    			    + "<form action=ud method=post><input type=number name=no placeholder=Number><input type=file name=upld><br><input type=submit value=Update name=updt><input type=submit value=DeleteIMG name=updt><input type=submit value=DeleteAllRecord name=updt>"
		    			    + "<div class=white-button scroll-to-section> <a href=ud?update=DeleteIMG && no=vinay >Delete</a></div></form>"
		    				+"</td>\r\n"
		    				+ "</tr></table>\r\n"
		    				+ "</div><br><hr></div>");
		    		base64img=null;
				}
		}else if(sr==""){
			PreparedStatement pstmt=con.prepareStatement("SELECT * FROM details WHERE Model=?");
			pstmt.setString(1, md);
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
	    		base64img=Base64.getEncoder().encodeToString(imgbyte);

				
	    		}
	    		pw.println("<div align=center>\r\n"
	    				+ "<table border=5px style='width:600px'>\r\n"
	    				+ "<tr>\r\n"
	    				+ "		<th>Number</th>\r\n"
	    				+ "		<th>Name</th>\r\n"
	    				+ "		<th>Model</th>\r\n"
	    				+ "		<th>Price</th>\r\n"
	    				+ "		<th>Image</th>\r\n"
	    				+ "</tr>\r\n"
	    				+ "<tr>\r\n"
	    				+ "         <td>"+no+"</td>\r\n"
	    				+ "         <td>"+name+"</td>\r\n"
	    				+ "         <td>"+model+"</td>\r\n"
	    				+ "         <td>"+price+"</td>\r\n"
	    				+ "         <td><img alt=ImgNotFound src=data:image/jpg;base64,"+base64img+" width=500/>"
	    				+ "<form action=ud method=post ><input type=number name=no placeholder=Number><input type=file name=upld><br><input type=submit value=Update name=updt><input type=submit value=DeleteIMG name=updt><input type=submit value=DeleteAllRecord name=updt></form>"
	    				+ "</form></td>"
	    				+ "</tr></table>\r\n"
	    				+ "</div><br><hr></div>");
	    		//pw.println("<html><body bgcolor='skyblue'><p><font color='black' size=20px>"+ no+" "+name+" "+model+" "+price+" <img src=data:image/jpg;base64,"+base64img+" width=500px display=flex/></font></p></body></html>");
	    		//RequestDispatcher rd;
			   //req.setAttribute("no2", no);
			  
			   //rd=req.getRequestDispatcher("UpdateIMG.java");
			   //rd.forward(req, resp);
			}	
		}else {
			PreparedStatement pstmt1=con.prepareStatement("SELECT * FROM details WHERE Name=? && Model=?");
			pstmt1.setString(1, sr);
			pstmt1.setString(2, md);
			ResultSet rs1=pstmt1.executeQuery();
			PrintWriter pw1=resp.getWriter();
	    	resp.setContentType("text/html");
			if(rs1.next()) {
				int no=rs1.getInt("Number");
				String name=rs1.getString("Name");
	    		String model=rs1.getString("Model");                                                                                         
	    		String price=rs1.getString("Price");
	    		Blob bl=rs1.getBlob("Image");
	    		if(bl!=null) {
	    		InputStream is=bl.getBinaryStream();
	    		ByteArrayOutputStream bao=new ByteArrayOutputStream();
	    		byte [] buffer=new byte[4096];
	    		int bytesRead=-1;
	    		while((bytesRead=is.read(buffer))!=-1) {
	    			bao.write(buffer,0,bytesRead);
	    		}
	    		byte[] imgbyte=bao.toByteArray();
	    		base64img=Base64.getEncoder().encodeToString(imgbyte);

				
	    		}
	    		pw1.println("<div align=center>\r\n"
	    				+ "<table border=5px style='width:600px'>\r\n"
	    				+ "<tr>\r\n"
	    				+ "		<th>Number</th>\r\n"
	    				+ "		<th>Name</th>\r\n"
	    				+ "		<th>Model</th>\r\n"
	    				+ "		<th>Price</th>\r\n"
	    				+ "		<th>Image</th>\r\n"
	    				+ "</tr>\r\n"
	    				+ "<tr>\r\n"
	    				+ "         <td>"+no+"</td>\r\n"
	    				+ "         <td>"+name+"</td>\r\n"
	    				+ "         <td>"+model+"</td>\r\n"
	    				+ "         <td>"+price+"</td>\r\n"
	    				+ "         <td><img alt=ImgNotFound src=data:image/jpg;base64,"+base64img+" width=500/>"
	    				+ "<form action=ud method=post ><input type=number name=no placeholder=Number><input type=file name=upld><br><input type=submit value=Update name=updt><input type=submit value=DeleteIMG name=updt><input type=submit value=DeleteAllRecord name=updt>"
	    				+ "</td>\r\n"
	    				+ "</tr></table>\r\n"
	    				+ "</div><br><hr></div>");
				//pw1.println("<html><body bgcolor='skyblue'><p><font color='black' size=20px>"+ no+" "+name+" "+model+" "+price+"  <img src=data:image/jpg;base64,"+base64img+" width=500px display=flex/></font></p></body></html>");
				
			}else {
			    pw1.println("<html><body bgcolor='skyblue'><p><font color='black' size=20px><h1>Company and Model name not match</h1></font></p></body></html>");
		    }
		}
		
	} catch (Exception e){
		System.out.println("Exception catch");
		PrintWriter pw2=resp.getWriter();
    	resp.setContentType("text/html");
		pw2.println("<html><body bgcolor='skyblue'><p><font color='black'><h1>This Company Information Not Available!! Try Different </h1></body></html>");
		e.printStackTrace();
		}finally {
			//resp.sendRedirect("UpdateIMG?num=no");
			RequestDispatcher rd=req.getRequestDispatcher("car.html");
			rd.include(req, resp);
		}

   }


@Override
public void destroy() {
	System.out.println("clean up activity done");
}

}
