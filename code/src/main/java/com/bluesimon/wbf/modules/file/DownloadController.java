package com.bluesimon.wbf.modules.file;

import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.utils.FileUtil;
import com.bluesimon.wbf.utils.HttpServletUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Created by Zz on 2021/4/3
 */
@Controller
public class DownloadController {

    @RequestMapping(value = "/v0.1/download", method = RequestMethod.GET)
    @ResponseBody
    public Response<String> downloadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam String url) {
        try{
            if (url == null) {
                throw new Exception("未找到附件");
            } else {
                File folder = new File(HttpServletUtil.getExportFolder(request));
                File f = new File(folder, url);
                FileUtil.downloadFile(url, f, response);
                return new Response<>();
            }
        } catch (Exception e){
            String msg = "下载文件异常：" + e.getMessage();
            return new Response<>(msg);
        }
    }
}
