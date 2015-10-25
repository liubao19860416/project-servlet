package com.saick.base.wrapper;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 方式1:添加行号的自定义包装类
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class MyLineNumberdBufferedReader2 extends BufferedReader {
    private Integer lineNO = 0;
    private BufferedReader br;

    public MyLineNumberdBufferedReader2(BufferedReader br) {
        super(br);
        this.br = br;
    }

    public String readLine() throws IOException {
        String line = null;
        line = br.readLine();
        if (line != null && line.trim().length() > 0) {
            lineNO++;
            return lineNO + ":" + line;
        } else {
            return null;
        }
    }

}
