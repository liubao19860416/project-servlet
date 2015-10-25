package com.saick.base.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.commons.beanutils.BeanUtils;

import com.saick.base.entity.User;
import com.saick.base.entity.UserFormBean;
import com.saick.base.utils.WebUtil;

/**
 * 一个总的转发请求转发控制的servlet
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
@SuppressWarnings({"serial"})
public class MasterServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String op = request.getParameter("op");
        try {
            if ("showUserDetail".equals(op)) {
                showUserDetail(request, response);
            } else if ("delUserBatch".equals(op)) {
                delUserBatch(request, response);
            } else if ("deUserUI".equals(op)) {
                deUserUI(request, response);
            } else if ("editUser".equals(op)) {
                editUser(request, response);
            } else if ("editUserUI".equals(op)) {
                editUserUI(request, response);
            } else if ("addUser".equals(op)) {
                addUser(request, response);
            } else if ("showAllUsers".equals(op)) {
                showAllUsers(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unused")
    private void showUserDetail(HttpServletRequest request,HttpServletResponse response) throws Exception, IOException {
        String userId = request.getParameter("userId");
        User user = null;
        //进行数据库查询
        request.setAttribute("user", user);
        request.getRequestDispatcher("/showUserDetail.jsp").forward(request,response);
    }

    @SuppressWarnings("unused")
    private void delUserBatch(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String[] ids = request.getParameterValues("ids");
        if (ids != null && ids.length > 0) {
            for (String userId : ids) {
                //执行数据库操作
            }
        }
        response.sendRedirect(request.getContextPath());
    }

    private void deUserUI(HttpServletRequest request,HttpServletResponse response) throws Exception {
        //执行数据库操作
        response.sendRedirect(request.getContextPath());
    }

    @SuppressWarnings({ "unused" })
    private void editUser(HttpServletRequest request,HttpServletResponse response) throws IOException, Exception {
        //方式1
        UserFormBean formBean1 = WebUtil.fillFormBean(request,UserFormBean.class);
        UserFormBean formBean2 = new UserFormBean();
        //方式2
        BeanUtils.populate(formBean2, request.getParameterMap());
        if (!formBean2.validate()) {
            request.setAttribute("formBean", formBean2);
            request.getRequestDispatcher("/addCustomer.jsp").forward(request,response);
            return;
        }
        User user = new User();
        //注册转换器
        //ConvertUtils.register(new DateLocaleConverter(), Date.class);
        //拷贝属性
        BeanUtils.copyProperties(user, formBean2); 
        //执行数据库操作
        // request.getRequestDispatcher("/listAllCustomer.jsp").forward(request,response);
        response.sendRedirect(request.getContextPath());
    }

    @SuppressWarnings("unused")
    private void editUserUI(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        User user = null;
        //执行数据库操作
        request.setAttribute("user", user);
        request.getRequestDispatcher("/editCustomer.jsp").forward(request,response);
    }

    private void addUser(HttpServletRequest request,HttpServletResponse response) throws Exception, IOException {
        UserFormBean formBean = new UserFormBean();
        BeanUtils.populate(formBean, request.getParameterMap());
        request.setAttribute("formBean", formBean);
        if (!formBean.validate()) {
            request.setAttribute("formBean", formBean);
            request.getRequestDispatcher("/addCustomer.jsp").forward(request,response);
            return;
        }
        User user= new User();
        //ConvertUtils.register(new DateLocaleConverter(), Date.class);
        BeanUtils.copyProperties(user, formBean);
        //执行数据库操作
        response.sendRedirect(request.getContextPath());
    }

    @SuppressWarnings("unused")
    private void showAllUsers(HttpServletRequest request,HttpServletResponse response) throws Exception, IOException {
        String num = request.getParameter("num");
        //执行数据库操作
        request.setAttribute("page", "");
        request.getRequestDispatcher("/listAllCustomer.jsp").forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
