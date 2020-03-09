package webapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;


@WebServlet(urlPatterns = "/login.do")
public class LoginServlet extends HttpServlet {

	public static int empId;
	public static String empSSN;
	
	private userValidationService service = new userValidationService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		request.setAttribute("name",request.getParameter("name"));
		request.setAttribute("password",request.getParameter("password"));
		
		//sqlmenuFinal.main(args);
		
		//request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		//String[] args = new String[1];
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		System.out.println(name + password);

		boolean valid = false;

		if((name != null) && (name != "")) {
			try {
			System.out.println("mem not null");
			int i = Integer.parseInt(name);
			empId = i;
			valid = true;
			//valid = DatabaseConnector.validateMember(i);
			if(valid) {
			//	String memInfo = DatabaseConnector.getMemberInfo(i);
			//	request.setAttribute("info",memInfo);
			//	String own = DatabaseConnector.getOwnedBooks(i);
			//	request.setAttribute("owend",own);
			//	String result = DatabaseConnector.getAllBooksInfo();
			//	request.setAttribute("set", result);
				System.out.println("Running Member Page");
				request.getRequestDispatcher("/WEB-INF/views/MemberPage.jsp").forward(request, response);

			}
			}catch(Exception c) {
				request.setAttribute("errorMessage", "Invalid Credentials");
				request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
			}

		}else if(password != null && (password != "")) {
			try {
			System.out.println("ssn not null");
			valid = DatabaseConnector.validateEmployee(password);
			if(valid) {
				String empInfo = DatabaseConnector.getEmployeeInfo(password);
				empSSN = empInfo;
				request.setAttribute("info",empInfo);
				String result = DatabaseConnector.getAllBooksInfo();
				String curr = DatabaseConnector.getOrderList();
				request.setAttribute("curr",curr);
				request.setAttribute("set", result);
				request.getRequestDispatcher("/WEB-INF/views/EmployeePage.jsp").forward(request, response);
			}
			else {
				request.setAttribute("errorMessage", "Invalid Credentials");
				request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
			}
		}catch(Exception c) {
			request.setAttribute("errorMessage", "Invalid Credentials");
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}
		}
		

	}
	
	protected void doStuff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);

	}

}