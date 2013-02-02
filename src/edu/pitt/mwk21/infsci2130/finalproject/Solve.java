package edu.pitt.mwk21.infsci2130.finalproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Solve
 */
public class Solve extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Solve() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Hardware.HardwareType hw_type = Hardware.HardwareType.valueOf(request.getParameter("type"));
		Price max_price = new Price(Double.parseDouble(request.getParameter("max_price")));
		Price reliability = new Price(Double.parseDouble(request.getParameter("reliability")));

		Model model = new Model(getServletContext());
		Results results = model.solve(hw_type, max_price, reliability);
		
		if (request.getParameter("download") == null) {
			response.setContentType("text/html");
			request.setAttribute("results", results);
			String reqURL = request.getRequestURL() + "?" + request.getQueryString() + "&download=1";
			request.setAttribute("downloadURL", reqURL);
			request.getRequestDispatcher("/results.jsp").forward(request, response);
		} else {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\"model.xdsl\"");
			OutputStream out = response.getOutputStream();
			InputStream in = results.getInputStream();
			
			byte[] buf = new byte[1024];
			int bytesRead;
			
			while((bytesRead = in.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}
		}
	}

}
