package com.saick.base.wrapper;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 方式2:添加行号的自定义包装类
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class MyLineNumberdBufferedReader extends BufferedReader {

    private int lineNO = 1;

    // 方式1
    // private BufferedReader bufferedReader;

    public MyLineNumberdBufferedReader(BufferedReader bufferedReader) {
        super(bufferedReader);
        // this.bufferedReader = bufferedReader;
    }

    @Override
    public String readLine() {
        // String line = buf.readLine();
        try {
            // 方式2
            String line =null;
            line=super.readLine();
            if (line != null) {
                return lineNO++ + ":" + line;
            }
            return super.readLine();
        } catch (IOException e) {
            throw new RuntimeException(" !");
        }
    }

}
