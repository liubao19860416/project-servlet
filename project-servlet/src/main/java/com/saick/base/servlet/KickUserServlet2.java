package com.saick.base.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings({ "unchecked", "serial" })
public class KickUserServlet2 extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        // username = new String(username.getBytes("ISO-8859-1"),"UTF-8");
        Map<String, HttpSession> map = (Map<String, HttpSession>) getServletContext().getAttribute("map");
        HttpSession userSession = map.get(username);
        if (userSession != null) {
            userSession.invalidate();
        }
        map.remove(username);
        getServletContext().setAttribute("map", map); 
        response.getWriter().write(" !");
        //response.setHeader("Refresh","2;url="+request.getContextPath()+"/listAllOnLine.jsp");
        response.sendRedirect(request.getContextPath() + "/listAllOnLine.jsp");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
