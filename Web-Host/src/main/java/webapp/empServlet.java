package webapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/emp.do")

public class empServlet extends HttpServlet {

	
	private userValidationService service = new userValidationService();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String password = LoginServlet.empSSN;
		
		String BookName = request.getParameter("Book Name");
		
		if(BookName != null) {
			String empInfo = DatabaseConnector.getEmployeeInfo(password);
			request.setAttribute("info",empInfo);
			String result = DatabaseConnector.getAllBooksInfo();
			String curr = DatabaseConnector.getOrderList();
			request.setAttribute("curr",curr);
			DatabaseConnector.setOrder(BookName);
			request.setAttribute("set", result);
			request.getRequestDispatcher("/WEB-INF/views/EmployeePage.jsp").forward(request, response);
		}
		else {
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}
		

	}
	protected void doLogOff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);

	}
}