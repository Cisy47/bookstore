package functional.Log;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 47 on 2017/5/7.
 */
@Component
public class LogWriter {
    public void write(String str){
        SimpleDateFormat s1= new SimpleDateFormat("yyyy_MM_dd");
        SimpleDateFormat s2=new SimpleDateFormat("HH:mm:ss");
        String d=s1.format(new Date());
        String fileName= Paths.get("Log")+d+".txt";
        try{
            File file=new File(fileName);
            if(!file.exists())
                file.createNewFile();
            FileOutputStream out=new FileOutputStream(file,true); //如果追加方式用true
            StringBuffer sb=new StringBuffer();
            sb.append(s2.format(new Date())+"   ");
            sb.append(str+"\n");
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        }catch (IOException ex){
            System.out.println(ex.getStackTrace());
        }
    }
}
