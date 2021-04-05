package com.bluesimon.wbf.utils;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HttpServletUtil {

    static String charset = "utf-8";

    public static String TempDir = "temp";

    public static String getReqBody(HttpServletRequest request) {
        try {
            return new String(getReqBodyByte(request), charset);
        } catch (UnsupportedEncodingException var3) {
            //throw new Exception("9905");
            return null;
        }
    }

    public static byte[] getReqBodyByte(HttpServletRequest request) {
        try {
            request.setCharacterEncoding(charset);
            byte[] result = IOUtils.toByteArray(request.getInputStream());
            return result;
        } catch (IOException var4) {
            return null;
        }
    }

    /**
     * 获取导出文件的路径
     *
     * @return
     */
    public static String getExportFolder(HttpServletRequest request) {
        String physicalPath = getPhysicalPath(request, "export_file");
        File dir = new File(physicalPath);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                return "";
            }
        }
        return physicalPath;
    }

    /**
     * 根据字符串创建本地目录 并按照日期建立子目录返回
     *
     * @param path
     * @return
     */
    public static String getFolder(HttpServletRequest request,String path) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        path += "/" + formater.format(new Date());
        String physicalPath = getPhysicalPath(request, path);
        File dir = new File(physicalPath);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                return "";
            }
        }
        return path;
    }

    /**
     * 根据传入的虚拟路径获取物理路径
     *
     * @param path
     * @return
     */
    public static String getPhysicalPath(HttpServletRequest request, String path) {
//		String servletPath = this.request.getServletPath();
        String realPath = request.getSession().getServletContext().getRealPath(path);
        return realPath;
    }
}