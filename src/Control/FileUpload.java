package Control;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * Created by ikiler on 2019/2/26.
 * Email : ikiler@126.com
 */
public class FileUpload {

    HttpServletRequest request;
    DiskFileItemFactory factory;
    ServletFileUpload upload;
    String mJson, path;

    public FileUpload(HttpServletRequest request) {
        this.request = request;
        factory = new DiskFileItemFactory();
        upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        upLoad();
    }
    public boolean upLoad(){
        List<FileItem> list = null;
        try {
            list = upload.parseRequest(request);
            for (FileItem item : list) {
                if (item.isFormField()) {
                    if (item.getFieldName().equals("json")) {
                        mJson = item.getString("utf-8");
                    }
                } else {
                    String name = item.getName();
                    if (name == null || name.isEmpty())
                        continue;
                    String savePath = request.getSession().getServletContext().getRealPath("/assets/userImage");
                    String uuid = System.currentTimeMillis() + "";//生成uuid
                    String fileName = uuid + name;
                    File file = new File(savePath, fileName);
                    item.write(file);
                    path = "assets/userImage/"+fileName;
                    System.out.println("新文件上传：" + savePath + "/" + fileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getmJson() {
        return mJson;
    }

    public String getPath() {
        return path;
    }
}
