package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WelocomeServlet
 */
@WebServlet(name="Welcome")
public class WelocomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WelocomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
//		String city=request.getQueryString();
//		String kv[]=city.split("&");
//		out.println("Hello"+kv[0]+" "+kv[1]);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		String username=request.getParameter("username");
		String psd=request.getParameter("password");
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		out.println("<h1>Welcome to our world! Dear "+username+"</h1>");
		// getting connection values from servletcontext using contex-param
		ServletContext context=getServletContext();
		
		ServletConfig config=getServletConfig();
		String url=context.getInitParameter("dburl");
		//String url=config.getInitParameter("url");
		String user=config.getInitParameter("dbuser");
		String pwd=config.getInitParameter("dbpwd");
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url,user,pwd);
			System.out.println("connection created");
			if(request.getParameter("register")!=null  && request.getParameter("register").equalsIgnoreCase("register")) {
				System.out.println("registration");
			PreparedStatement ps=con.prepareStatement("insert into appUser values(?,?,?,?)");
			ps.setString(1, username);
			ps.setString(2,psd);
			ps.setString(3, email);
			ps.setString(4,phone);
			ps.executeUpdate();
			}
			else if(request.getParameter("update").equalsIgnoreCase("update")) {
				System.out.println("Updation");
				PreparedStatement ps=con.prepareStatement("update appUser set email=? where name=?");
				ps.setString(1,email);
				ps.setString(2,username);
				ps.executeUpdate();
			}
			
		}
		catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

}
