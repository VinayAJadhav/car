package com.car.collection.details;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/ud")
public class UpdateIMG extends HttpServlet {
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //int num=(int) req.getAttribute("Num");
	PrintWriter ref=resp.getWriter();
	//System.out.println("VALUE"+num);
	String no=req.getParameter("no");
	System.out.println(no+"updateimgcall");
	if(no=="") {
		System.out.println("number not passed");
		
		RequestDispatcher rd=req.getRequestDispatcher("car.html");
		//ref.println(" You not put Number of car");
		rd.include(req, resp);
	}
	int no1=Integer.parseInt(no);
	System.out.println(no1);
System.out.println("Update Servlet called");
     String update=req.getParameter("updt");
     System.out.println(update);
     String file=req.getParameter("upld");
     PreparedStatement pstmt;
    if(update.equalsIgnoreCase("DeleteIMG")) {
	
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_details","root","root");
			pstmt=con.prepareStatement("UPDATE  Details SET Image=NULL WHERE Number=?");
			pstmt.setInt(1,no1);
			pstmt.executeUpdate();
			System.out.println("Image deleted by client from UpdateIMG");
			  try {String base64img=null;
	        	pstmt=con.prepareStatement("SELECT * FROM details WHERE Number=?");
				pstmt.setInt(1, no1);
				ResultSet rs=pstmt.executeQuery();
			
				PrintWriter pw=resp.getWriter();
		    	resp.setContentType("text/html");
				while(rs.next()){
					int no2=rs.getInt("Number");
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
		    				+ "         <td>"+no2+"</td>\r\n"
		    				+ "         <td>"+name+"</td>\r\n"
		    				+ "         <td>"+model+"</td>\r\n"
		    				+ "         <td>"+price+"</td>\r\n"
		    				+ "         <td><img alt=ImgNotFound src=data:image/jpg;base64,"+base64img+" width=500/><br>"
		    			    + "<form action=ud method=post><input type=number name=no placeholder=Number><input type=file name=upld><br><input type=submit value=Update name=updt><input type=submit value=DeleteIMG name=updt><input type=submit value=DeleteAllRecord name=updt></form>"
		    				+"</td>\r\n"
		    				+ "</tr></table>\r\n"
		    				+ "</div><br><hr></div>");
		    		base64img=null;
				}

	        }catch(Exception e) {
	        	
	        }
	        //ref.print("<html><body bgcolor='skyblue'><h2>Image Deleted Successfully</h2><p>Do another work</p></body></html>");
	        RequestDispatcher rd=req.getRequestDispatcher("car.html");
		   rd.include(req,resp);
		} catch (Exception e) {
			
			ref.print("<html><body bgcolor='skyblue'><font color=black><h1>Please Try again</h1></body></html>");
		    RequestDispatcher rd=req.getRequestDispatcher("car.html");
		    rd.include(req,resp);
			e.printStackTrace();
		
		}
		}else if(update.equalsIgnoreCase("Update")) {
			
		     File theFile=new File(file);
				
				FileInputStream input=new FileInputStream(theFile);
				
				
				System.out.println("UpdateIMG servlet start working");
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_details","root","root");
					pstmt=con.prepareStatement("UPDATE Details SET Image=? WHERE Number=?");
					
					pstmt.setBlob(1, input);
					pstmt.setInt(2,no1);
					System.out.println("Reading input file: "+theFile.getAbsolutePath());
					pstmt.executeUpdate();
					System.out.println("Image taken by client");
			       // ref.print("<html><body bgcolor='skyblue'><h2>Image Updated Successfully</h2><p>Do another work</p></body></html>");
			        try {String base64img=null;
			        	pstmt=con.prepareStatement("SELECT * FROM details WHERE Number=?");
						pstmt.setInt(1, no1);
						ResultSet rs=pstmt.executeQuery();
					
						PrintWriter pw=resp.getWriter();
				    	resp.setContentType("text/html");
						while(rs.next()){
							int no2=rs.getInt("Number");
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
				    				+ "         <td>"+no2+"</td>\r\n"
				    				+ "         <td>"+name+"</td>\r\n"
				    				+ "         <td>"+model+"</td>\r\n"
				    				+ "         <td>"+price+"</td>\r\n"
				    				+ "         <td><img alt=ImgNotFound src=data:image/jpg;base64,"+base64img+" width=500/><br>"
				    			    + "<form action=ud method=post><input type=number name=no placeholder=Number><input type=file name=upld><br><input type=submit value=Update name=updt><input type=submit value=DeleteIMG name=updt><input type=submit value=DeleteAllRecord name=updt></form>"
				    				+"</td>\r\n"
				    				+ "</tr></table>\r\n"
				    				+ "</div><br><hr></div>");
				    		base64img=null;
						}

			        }catch(Exception e) {
			        	
			        }
			       RequestDispatcher rd=req.getRequestDispatcher("car.html");
				   rd.include(req,resp);
					System.out.println("Information submitted");
				} catch (Exception e) {
					
					ref.print("<html><body bgcolor='skyblue'><font color=black><h1>Please Try again</h1></body></html>");
				    RequestDispatcher rd=req.getRequestDispatcher("car.html");
				    rd.include(req,resp);
					e.printStackTrace();
				}
		     }else if(update.equalsIgnoreCase("DeleteAllRecord")) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_details","root","root");
					pstmt=con.prepareStatement("Delete FROM Details WHERE Number=?");
					pstmt.setInt(1,no1);
					pstmt.executeUpdate();
					System.out.println("Deleted taken by client");
			       // ref.print("<html><body bgcolor='skyblue'><h2>Image Updated Successfully</h2><p>Do another work</p></body></html>");
			        try {String base64img=null;
			        	pstmt=con.prepareStatement("SELECT * FROM details");
						//pstmt.setInt(1, no1);
						ResultSet rs=pstmt.executeQuery();
					
						PrintWriter pw=resp.getWriter();
				    	resp.setContentType("text/html");
						while(rs.next()){
							int no2=rs.getInt("Number");
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
				    				+ "         <td>"+no2+"</td>\r\n"
				    				+ "         <td>"+name+"</td>\r\n"
				    				+ "         <td>"+model+"</td>\r\n"
				    				+ "         <td>"+price+"</td>\r\n"
				    				+ "         <td><img alt=ImgNotFound src=data:image/jpg;base64,"+base64img+" width=500/><br>"
				    			    + "<form action=ud method=post><input type=number name=no placeholder=Number><input type=file name=upld><br><input type=submit value=Update name=updt><input type=submit value=DeleteIMG name=updt><input type=submit value=DeleteAllRecord name=updt></form>"
				    				+"</td>\r\n"
				    				+ "</tr></table>\r\n"
				    				+ "</div><br><hr></div>");
				    		base64img=null;
						}

			        }catch(Exception e) {
			        	
			        }
			       RequestDispatcher rd=req.getRequestDispatcher("car.html");
				   rd.include(req,resp);
					System.out.println("Information submitted");
				} catch (Exception e) {
					
					ref.print("<html><body bgcolor='skyblue'><font color=black><h1>Please Try again</h1></body></html>");
				    RequestDispatcher rd=req.getRequestDispatcher("car.html");
				    rd.include(req,resp);
					e.printStackTrace();
				}
		     }
    }

}
