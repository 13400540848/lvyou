package com.bluesimon.wbf.utils;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

/**
 * @author zz
 * @date 2021/4/3
 */
public class FileUtil {
    static String[] ExcelAllowFile = {".xls", ".xlsx"}; 
    public static void downloadFile(String fileName, String fieldUrl, HttpServletResponse response, boolean canDel) throws Exception {
        File file = new File(fieldUrl);
        downloadFile(fileName, file, response);
        if (canDel) {
            try {
                file.delete();
            } catch (Exception var6) {
                throw new Exception("文件删除失败", var6);
            }
        }

    }
    public static void downloadFile(String fileName, File file, HttpServletResponse response) throws Exception {
        if (file.isFile() && file.exists()) {
            String name = file.getName();
            if (StringUtil.isEmpty(fileName)) {
                fileName = name;
            }
            String ext = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
            if (!fileName.endsWith(ext)) {
                fileName = fileName + "." + ext;
            }
            FileInputStream is = null;
            try {
                is = new FileInputStream(file);
                downloadFile(fileName, is, ContentType.get(ext), response);
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        } else {
            throw new Exception("附件下载失败：不是有效的文件，路径【" + file.getCanonicalPath() + "】");
        }
    }
    public static void downloadFile(String fileName, InputStream is, String contentType, HttpServletResponse response) throws Exception {
        if (is == null) {
            throw new Exception("文件流为空");
        } else {
            fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            response.reset();
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            if (!StringUtil.isEmpty(contentType)) {
                response.setContentType(contentType);
            } else {
                response.setContentType("application/octet-stream");
            }

            OutputStream os = null;
            byte[] buffer = new byte[1024];

            try {
                os = response.getOutputStream();
                int b;
                while((b = is.read(buffer)) != -1) {
                    os.write(buffer, 0, b);
                }
            } catch (Exception var11) {
                throw new Exception("附件下载异常", var11);
            } finally {
                if (os != null) {
                    os.flush();
                    os.close();
                }
            }
        }
    }

    public static boolean checkExcelFile(String fileName) {
        Iterator<String> type = Arrays.asList(ExcelAllowFile).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
    public static boolean checkFileType(String[] allowFiles, String fileName) {
        Iterator<String> type = Arrays.asList(allowFiles).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 按不同单元格格式获得数据
     * @param cell
     * @return
     */
    public static String GetCellValue(Cell cell){
        String temp ;
        if (cell == null||"".equals(cell)) {
            temp = null;
        } else {
            CellType cellType = cell.getCellTypeEnum();
            switch (cellType) {
                case STRING:
                    temp = cell.getStringCellValue().trim();
                    temp = StringUtil.isEmpty(temp) ? null : temp;
                    break;
                case BOOLEAN:
                    temp = String.valueOf(cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    temp = String.valueOf(cell.getCellFormula().trim());
                    break;
                case NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                        temp = sdf.format(date);
                    } else {
                        temp = new DecimalFormat("#.######").format(cell.getNumericCellValue());
                    }
                    break;
                case BLANK:
                    temp = null;
                    break;
                case ERROR:
                    temp = "ERROR";
                    break;
                default:
                    temp = cell.toString().trim();
                    break;
            }
        }
        return temp;
    }
}
