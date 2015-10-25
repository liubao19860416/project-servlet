package com.saick.base.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 创建一张图片验证码信息的servlet
 * 
 * @author Liubao
 * @2014年12月18日
 *
 */
public class ImageServlet extends HttpServlet {

    private static final long serialVersionUID = -7494430794743553939L;
    
    private static final int WIDTH = 140;
    private static final int HEIGHT = 30;
    private static final int NUMBER = 10;
    private static final int SPACE = 20;
    private static final String baseStr = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pargma", "no-cache");

        //创建一张图片信息
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.GREEN);
        graphics.drawRect(0, 0, WIDTH, HEIGHT);
        graphics.setColor(Color.YELLOW);
        graphics.fillRect(1, 1, WIDTH - 2, HEIGHT - 2);
        graphics.setColor(Color.GRAY);

        Random random = new Random();
        for (int i = 0; i < NUMBER; i++) {
            graphics.drawLine(random.nextInt(WIDTH - 1), random.nextInt(HEIGHT - 1),
                    random.nextInt(WIDTH - 1), random.nextInt(HEIGHT - 1));
        }

        char[] chr = new char[baseStr.length()];
        for (int i = 0; i < chr.length; i++) {
            chr[i] = baseStr.charAt(i);
        }

        graphics.setColor(Color.RED);
        graphics.setFont(new Font("myFont", Font.BOLD | Font.ITALIC, 25));
        int x = 30;

        for (int i = 0; i < 4; i++) {
            graphics.drawChars(chr, random.nextInt(baseStr.length()), 1, x, 23);
            x = x + SPACE;
        }
        
        //可以在这里提示客户端下载信息，对文件名进行URLEncoder编码
        //String fileName="1.jpg";
        //response.setHeader("Content-Disposition", "attachment;filename="+
        //        URLEncoder.encode(fileName,"UTF-8"));
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "bmp", out);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

}
