package com.bluesimon.wbf.modules.file;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bluesimon.wbf.utils.Uploader;

/**
 * Created by Zz on 2021/3/21.
 */
@RestController
public class UploadController {

    /**
     * 上传文件
     *
     * @param request
     * @param folder
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v0.1/upload", method = RequestMethod.POST)
    public UploadResp upload(HttpServletRequest request, @RequestParam(value = "folder", defaultValue = "attachment") String folder) throws Exception {
        Uploader up = new Uploader(request);
        String ROOT_PATH = "/statics/upload";
        up.setSavePath(ROOT_PATH + "/" + folder);
        String[] fileType = {".gif", ".png", ".jpg", ".jpeg", ".bmp"};
        up.setAllowFiles(fileType);
        up.setMaxSize(10000); //单位KB
        up.upload();
        return new UploadResp(up.getFileName(),
                up.getOriginalName(),
                up.getSize(),
                up.getState(),
                up.getType(),
                up.getUrl());
    }
}
