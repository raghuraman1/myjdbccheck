package myjdbceg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Check")
public class MyServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html><body><form method=\"POST\">");
		out.println("<table>");
		out.println("<tr>");
		
		out.println("<td>");
		out.println("user");
		out.println("</td>");
		out.println("<td>");
		out.println("<input name=\"user\" />");
		out.println("</td>");
		
		out.println("<td>");
		out.println("password");
		out.println("</td>");
		out.println("<td>");
		out.println("<input name=\"password\" type=\"password\"/>");
		out.println("</td>");
		
		out.println("<td>");
		out.println("host");
		out.println("</td>");
		out.println("<td>");
		out.println("<input name=\"host\" />");
		out.println("</td>");
		
		out.println("<td>");
		out.println("port");
		out.println("</td>");
		out.println("<td>");
		out.println("<input name=\"port\" />");
		out.println("</td>");
		
		out.println("</tr>");
		out.println("</table>");

		out.println("<input type=\"submit\" value=\"submit\"/>");
		out.println("</form></body></html>");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html><body>");
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

	private void process(PrintWriter out, HttpServletRequest req) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		out.println("processing...........<br/>");
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String host = req.getParameter("host");
		String port = req.getParameter("port");
		String url="jdbc:mysql://"+host+":"+port;
		String u=req.getParameter("user");
		String p=req.getParameter("password");
		
		Connection conn = DriverManager.getConnection(url, u, p );
		out.println("got connection...........<br/>");
		DatabaseMetaData md = conn.getMetaData();
		
		
		showRs(out, md.getCatalogs(),  "catalogs");
		showRs(out, md.getSchemas(),  "schemas");
	
		
	}

	private void showRs(PrintWriter out, ResultSet rs,  String label) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		out.println("<table>");
		
		out.println("<th><td colspan=\""+columnCount+"\">"+label+"</td></th>");
		out.println("<th>");
		for (int i = 1; i <= columnCount; i++) 
		{
			out.println("<td>");
			out.println(rsmd.getColumnName(i));
			out.println("</td>");
		}
		out.println("</th>");
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
