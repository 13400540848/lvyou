package org.ume.school.modules.college;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bluesimon.wbf.AdminLogined;
import com.bluesimon.wbf.IUser;
import com.bluesimon.wbf.RequestPager;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.ResponseErrorEnum;
import com.bluesimon.wbf.enums.NormalStatusEnum;
import com.bluesimon.wbf.utils.AuthValidate;
import com.bluesimon.wbf.utils.HttpServletUtil;
import com.bluesimon.wbf.utils.StringUtil;

/**
 * Created by Zz on 2021/3/20.
 */
@RestController
@RequestMapping("/v0.1/admin/college")
public class CollegeAdminController {

    @Resource
    private org.ume.school.modules.college.CollegeService collegeService;
    
    /**
     * 查询
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response<List<CollegeEntity>> getAdminMenus(@RequestBody RequestPager<CollegeEntity> req, @AdminLogined IUser user) {
        Response<List<CollegeEntity>> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        Response<List<CollegeEntity>> result = new Response<List<CollegeEntity>>();
        Page<CollegeEntity> projects = collegeService.findAll(req);
        result.setTotal(projects.getTotalElements());
        result.setRows(projects.getContent());
        return result;
    }
    
    /**
     * 详情
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response<CollegeEntity> get(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<CollegeEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        } 
        return collegeService.get(id);
    }
    
    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Response<CollegeEntity> submit(@RequestBody(required = true) CollegeEntity entity, @AdminLogined IUser user) {
        Response<CollegeEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        }
        if(StringUtil.isEmpty(entity.getName()) || StringUtil.isEmpty(entity.getCode())){
            return new Response<>(ResponseErrorEnum.PARAM_NULL);
        }
        if (StringUtil.isEmpty(entity.getId())) { //新增
            return collegeService.add(entity);
        }else{
            return collegeService.edit(entity);
        }
    }
    
    /**
     * 删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response<CollegeEntity> delete(@PathVariable("id") Long id, @AdminLogined IUser user) {
        Response<CollegeEntity> check = AuthValidate.checkAdmin(user);
        if (check != null) {
            return check;
        } 
        return collegeService.delete(id);
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ResponseBody
    public Response<String> export(HttpServletRequest request, HttpServletResponse response) {
        Response<String> result = new Response<>();
        //[{"field":"erpCode","title":"ERP出库单号","queryType":"Like","property":"erpCode","compareOperator":"Like","ignoreCase":false,"defaultValue":{},"dataType":{"type":"Auto"}},{"field":"code","title":"条码、编号","queryType":"Like","property":"code","compareOperator":"Like","ignoreCase":false,"defaultValue":{},"dataType":{"type":"Auto"}},{"field":"sendOrderType","title":"发货单类型","dict":"SEND_ORDER_TYPE","queryType":"Equal","dataType":{"type":"Number"},"property":"sendOrderType","compareOperator":"Equal","ignoreCase":false,"defaultValue":{}}]
        String strBody = HttpServletUtil.getReqBody(request);        
        RequestPager<CollegeEntity> pager = JSONObject.parseObject(strBody, new TypeReference<RequestPager<CollegeEntity>>(){});
        Page<CollegeEntity> dataList = collegeService.findAll(pager);
        try{
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();

            HSSFRow row;
            row=sheet.createRow(0);
            row.createCell(0).setCellValue("序号");
            row.createCell(1).setCellValue("学院名称");
            row.createCell(2).setCellValue("学院编号");
            row.createCell(3).setCellValue("所属学校");
            row.createCell(4).setCellValue("状态");

            if(!StringUtil.isEmpty(dataList.getContent())) {
                int i = 1;
                int j = 0;
                for (CollegeEntity data : dataList.getContent()) {
                    row = sheet.createRow(i);
                    row.createCell(j++).setCellValue(i);
                    row.createCell(j++).setCellValue(data.getName());
                    row.createCell(j++).setCellValue(data.getCode());
                    row.createCell(j++).setCellValue(data.getSchool()!=null ? data.getSchool().getName():"");
                    row.createCell(j++).setCellValue(data.getStatus().equals(NormalStatusEnum.DISABLE.getValue())?NormalStatusEnum.DISABLE.getText():NormalStatusEnum.ENABLED.getText());
                    i++;
                    j = 0;
                }
            }
            OutputStream out = null;
            try {
                File folder = new File(HttpServletUtil.getExportFolder(request));
                SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
                String fileName = "college-" + formater.format(new Date());
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
}
