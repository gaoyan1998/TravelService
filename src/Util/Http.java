package Util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by ikiler on 2019/2/22.
 * Email : ikiler@126.com
 */
public class Http {
    public static String getStringFromReq(HttpServletRequest request){
        String str, wholeStr = "";
        BufferedReader br = null;
        try {
            br = request.getReader();
            while((str = br.readLine()) != null){
                wholeStr += str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return wholeStr;
        }
    }
}
