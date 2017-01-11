package myjdbceg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
@WebServlet("/Check1")
@MultipartConfig
public class MyServlet1 extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html><body><form method=\"POST\"  enctype=\"multipart/form-data\">");
		out.println("<table>");
		out.println("<tr>");
		
		out.println("<td>");
		out.println("user");
		out.println("</td>");
		out.println("<td>");
		out.println("<input name=\"user\" />");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td>");
		out.println("password");
		out.println("</td>");
		out.println("<td>");
		out.println("<input name=\"password\" type=\"password\"/>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td>");
		out.println("host");
		out.println("</td>");
		out.println("<td>");
		out.println("<input name=\"host\" />");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td>");
		out.println("port");
		out.println("</td>");
		out.println("<td>");
		out.println("<input name=\"port\" />");
		out.println("</td>");
		
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td>");
		out.println("db name");
		out.println("</td>");
		out.println("<td>");
		out.println("<input name=\"db\" />");
		out.println("</td>");
		
		out.println("<tr>");
		out.println("<td>");
		out.println("file");
		out.println("</td>");
		out.println("<td>");
		out.println("<input name=\"file\" type=\"file\"/>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");

		out.println("<input type=\"submit\" value=\"submit\"/>");
		out.println("</form></body></html>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html><body>");
		out.println("<head>");
		out.println("<style>");
		out.println("table {");
		out.println("    border-collapse: collapse;");
		out.println("}");
		out.println("");
		out.println("table, td, th {");
		out.println("    border: 1px solid black;");
		out.println("}");
		out.println("</style>");
		out.println("</head>");
		try {
			process(out, req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(out);
			
		}
		finally
		{
			out.println("</body></html>");
		}
	}
	
	private void process(PrintWriter out, HttpServletRequest req) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException, ServletException {
		
		out.println("processing...........<br/>");
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String host = req.getParameter("host");
		String port = req.getParameter("port");
		String db = req.getParameter("db");
		String url="jdbc:mysql://"+host+":"+port+"/"+db;
		String u=req.getParameter("user");
		String p=req.getParameter("password");
		final Part filePart = req.getPart("file");
	    
	    
	    InputStream inputStream = filePart.getInputStream();
	    InputStreamReader isr= new InputStreamReader(inputStream);
	    BufferedReader br= new BufferedReader(isr);
	    StringBuilder sb= new StringBuilder();
	    for (String line=br.readLine(); line!=null; line=br.readLine()) 
	    {
	    	out.println(line+"<br/>");
	    	if(line!=null && line.length()>0)
	    	{
	    		sb.append(line);
	    	}
		}
		br.close();
		String[] split = sb.toString().split(";");
		Connection conn = DriverManager.getConnection(url, u, p );
		out.println("got connection...........<br/>");
		Statement st = conn.createStatement();
		for (String sql : split) {
			if(sql!=null)
			{
				sql=sql.trim();
				if(sql.length()>0)
				{
					out.println(sql+"<br/>");
					int execute = st.executeUpdate(sql);
					
					out.println("executed="+execute+"..........<br/>");
				}
				
			}
			
		}
		DatabaseMetaData md = conn.getMetaData();
		
		
		showRs(out, md.getCatalogs(),  "catalogs");
		showRs(out, md.getSchemas(),  "schemas");
		showRs(out, md.getTables(null, null, "%", null),  "tables");
	
		
	}

	private void showRs(PrintWriter out, ResultSet rs,  String label) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		out.println(label+"<hr/>");
		out.println("<table>");
		
		out.println("<tr><th colspan=\""+columnCount+"\">"+label+"</th></tr>");
		out.println("<tr>");
		for (int i = 1; i <= columnCount; i++) 
		{
			out.println("<th>");
			out.println(rsmd.getColumnName(i));
			out.println("</th>");
		}
		out.println("</tr>");
		while(rs.next())
		{
			out.println("<tr>");
			for (int i = 1; i <= columnCount; i++) 
			{
				out.println("<td>");
				out.println(rs.getString(i));
				out.println("</td>");
			}
			out.println("</tr>");
		}
		out.println("</table>");
	}

}
