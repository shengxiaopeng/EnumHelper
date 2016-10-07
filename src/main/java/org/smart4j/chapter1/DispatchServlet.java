package org.smart4j.chapter1;

import com.sxp.enumTest.Helper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/10/7.
 */

public class DispatchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
           // List<Credit> allEntity = Helper.getAllEntity(Credit.class);
            List allEntity = Helper.getAllBean("Credit");
            request.setAttribute("entitys",allEntity);

            request.getRequestDispatcher("/hello.vm").forward(request,response);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
