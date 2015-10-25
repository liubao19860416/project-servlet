package com.saick.base.derby;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;

/**
 * 依赖ant的sql脚本执行代码
 * 
 * @author Liubao
 * @2014年12月25日
 * 
 */
public class SQLExecTest {
    /**
     * 执行数据库sql脚本文件方法
     */
    public static void execSQL(String scriptPath, String driveClass,String url, String user, String passwd) {
        SQLExec sqlExec = new SQLExec();
        sqlExec.setDriver(driveClass);
        sqlExec.setUrl(url);
        sqlExec.setUserid(user);
        sqlExec.setPassword(passwd);
        sqlExec.setSrc(new File(scriptPath));
        sqlExec.setOnerror((SQLExec.OnError) (EnumeratedAttribute.getInstance(SQLExec.OnError.class, "abort")));
        sqlExec.setPrint(false);
        //下面的这一行不能少！！！
        sqlExec.setProject(new Project());
        //执行sql脚本文件
        sqlExec.execute();
    }
}
