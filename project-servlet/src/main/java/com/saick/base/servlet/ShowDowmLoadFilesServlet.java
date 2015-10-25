package com.saick.base.servlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ShowDowmLoadFilesServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> fileListMap = new HashMap<String, String>();
        String fileDir = getServletContext().getRealPath("/WEB-INF/files");
        File file = new File(fileDir);
        // 遍历
        treeWalk(file, fileListMap);
        request.setAttribute("fileListMap", fileListMap);
        request.getRequestDispatcher("/downLoad.jsp").forward(request, response);
    }

    /**
     * 遍历文件夹
     */
    private void treeWalk(File file, Map<String, String> map) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (File file2 : listFiles) {
                treeWalk(file2, map);
            }
        } else {
            String uuidName = file.getName();
            String dispName = uuidName.substring(uuidName.indexOf("_") + 1);
            map.put(uuidName, dispName);
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
