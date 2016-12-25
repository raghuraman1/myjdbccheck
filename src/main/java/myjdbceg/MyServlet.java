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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet {

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
		String url = req.getParameter("url");//"jdbc:mysql://localhost/test?user=minty&password=greatsqldb";
		Connection conn = DriverManager.getConnection(url );
		out.println("got connection...........<br/>");
		DatabaseMetaData md = conn.getMetaData();
		
		
		showRs(out, md.getCatalogs(),  "catalogs");
		showRs(out, md.getSchemas(),  "schemas");
	
		
	}

	private void showRs(PrintWriter out, ResultSet rs,  String label) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		out.println("<table>");
		out.println("<th colspan=\""+columnCount+"\">"+label+"</th>");
		
		for (int i = 1; i <= columnCount; i++) 
		{
			out.println("<th>");
			out.println(rsmd.getColumnName(i));
			out.println("</th>");
		}
		while(rs.next())
		{
			for (int i = 1; i <= columnCount; i++) 
			{
				out.println("<tr>");
				out.println(rs.getString(i));
				out.println("</tr>");
			}
		}
		out.println("</table>");
	}

}
