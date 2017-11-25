package org.smart4j.chapter1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/9/14.
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        req.setAttribute("time",new Date());
        req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req,resp);
       // getServletContext().getInitParameter()
       // String initParameter = getServletConfig().getServletContext().getInitParameter();
        //String initParameter = getInitParameter();
    }
}
