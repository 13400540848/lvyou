package org.ume.school.modules.school;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bluesimon.wbf.*;
import com.bluesimon.wbf.enums.NormalStatusEnum;
import com.bluesimon.wbf.modules.file.UploadResp;
import com.bluesimon.wbf.modules.user.enums.UserStatusEnum;
import com.bluesimon.wbf.utils.AuthValidate;
import com.bluesimon.wbf.utils.HttpServletUtil;
import com.bluesimon.wbf.utils.StringUtil;
import com.bluesimon.wbf.utils.Uploader;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Zz on 2021/3/20.
 */
@RestController
@RequestMapping("/v0.1/admin/school")
public class SchoolAdminController {

    @Resource
    private SchoolService schoolService;
    
    /**
     * 查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response<List<SchoolEntity>> getAdminMenus(@RequestBody RequestPager<SchoolEntity> req, @AdminLogined IUser user) {
        Response<List<SchoolEntity>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<SchoolEntity>> result = new Response<List<SchoolEntity>>();
        Page<SchoolEntity> projects = schoolService.findAll(req);
        result.setTotal(projects.getTotalElements());
        result.setRows(projects.getContent());
        return result;
    }
    
    /**
     * 详情
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<SchoolEntity> get(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<SchoolEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        } 
        return schoolService.get(id);
    }
    
    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<SchoolEntity> submit(@RequestBody(required = true) SchoolEntity entity, @AdminLogined IUser user) {
        Response<SchoolEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        if(StringUtil.isEmpty(entity.getName()) || StringUtil.isEmpty(entity.getCode())){
            return new Response<>(ResponseErrorEnum.PARAM_NULL);
        }
        if (StringUtil.isEmpty(entity.getId())) { //新增
            return schoolService.add(entity);
        }else{
            return schoolService.edit(entity);
        }
    }
    
    /**
     * 删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<SchoolEntity> delete(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<SchoolEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        } 
        return schoolService.delete(id);
    }

    /**
     * 导出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ResponseBody
    public Response<String> export(HttpServletRequest request, HttpServletResponse response) {
        Response<String> result = new Response<>();
        //[{"field":"erpCode","title":"ERP出库单号","queryType":"Like","property":"erpCode","compareOperator":"Like","ignoreCase":false,"defaultValue":{},"dataType":{"type":"Auto"}},{"field":"code","title":"条码、编号","queryType":"Like","property":"code","compareOperator":"Like","ignoreCase":false,"defaultValue":{},"dataType":{"type":"Auto"}},{"field":"sendOrderType","title":"发货单类型","dict":"SEND_ORDER_TYPE","queryType":"Equal","dataType":{"type":"Number"},"property":"sendOrderType","compareOperator":"Equal","ignoreCase":false,"defaultValue":{}}]
        String strBody = HttpServletUtil.getReqBody(request);
        RequestPager<SchoolEntity> pager = JSONObject.parseObject(strBody, new TypeReference<RequestPager<SchoolEntity>>(){});
        Page<SchoolEntity> dataList = schoolService.findAll(pager);
        try{
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();

            HSSFRow row;
            row=sheet.createRow(0);
            row.createCell(0).setCellValue("序号");
            row.createCell(1).setCellValue("学校名称");
            row.createCell(2).setCellValue("学校编号");
            row.createCell(3).setCellValue("状态");

            if(!StringUtil.isEmpty(dataList.getContent())) {
                int i = 1;
                int j = 0;
                for (SchoolEntity data : dataList.getContent()) {
                    row = sheet.createRow(i);
                    row.createCell(j++).setCellValue(i);
                    row.createCell(j++).setCellValue(data.getName());
                    row.createCell(j++).setCellValue(data.getCode());
                    row.createCell(j++).setCellValue(data.getStatus().equals(NormalStatusEnum.DISABLE.getValue())?NormalStatusEnum.DISABLE.getText():NormalStatusEnum.ENABLED.getText());
                    i++;
                    j = 0;
                }
            }
            OutputStream out = null;
            try {
                File folder = new File(HttpServletUtil.getExportFolder(request));
                SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
                String fileName = "school-" + formater.format(new Date());
                String ext = "xls";
                out = new FileOutputStream(new File(folder, fileName + "." + ext));
                wb.write(out);
                result.setRows(fileName + "." + ext);
            } catch (IOException e) {
                String msg = "导出IO异常 - " + e.getMessage();
                result = new Response<>(Response.OK, msg);
            } finally {
                if(out !=null)
                    out.close();
            }
        } catch (Exception e){
            String msg = "导出表格异常 - " + e.getMessage();
            result = new Response<>(Response.OK, msg);
        }
        return result;
    }

    /**
     * 导入
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public Response<String> importData(HttpServletRequest request) throws Exception {
//        MultipartHttpServletRequest mureq = (MultipartHttpServletRequest)request;
//        Map<String, MultipartFile> files = mureq.getFileMap();
//        if(files==null || files.size()<=0){
//            return new Response<>(Response.NORMAL, "文件为空"); 
//        }
//        Map.Entry<String, MultipartFile> f = files.entrySet().iterator().next(); 
//        MultipartFile file = f.getValue();
        
        Uploader up = new Uploader(request);
        up.setSavePath(HttpServletUtil.TempDir);
        String[] fileType = {".xls", ".xlsx"};
        up.setAllowFiles(fileType);
        up.setMaxSize(10000); //单位KB
        up.upload();
        return schoolService.importFile(up);
    }
}
