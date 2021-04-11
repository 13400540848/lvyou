package org.ume.school.modules.college;

import com.bluesimon.wbf.RequestPager;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.enums.NormalStatusEnum;
import com.bluesimon.wbf.utils.FileUtil;
import com.bluesimon.wbf.utils.StringUtil;
import com.bluesimon.wbf.utils.Uploader;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ume.school.modules.school.SchoolEntity;
import org.ume.school.modules.school.SchoolService;
import org.ume.school.modules.utils.PageUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Zz on 2021/3/21.
 */
@Service
public class CollegeService {

    @Resource
    private CollegeRepository collegeRepository;
    @Resource
    private SchoolService schoolService;
    
    @Transactional
    public List<CollegeEntity> getAll() {
        return collegeRepository.findAll();
    }
    
    public int countBySchoolId(Long schoolId){
        return collegeRepository.countBySchoolId(schoolId);
    }
    
    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<CollegeEntity> findAll(RequestPager<CollegeEntity> req) {
        final CollegeEntity condition = req.getCondition();
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<CollegeEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotEmpty((condition.getName()))) {
                    predicates.add(cb.like(root.get("name"), "%" + condition.getName() + "%"));
                }
                if (StringUtils.isNotEmpty((condition.getCode()))) {
                    predicates.add(cb.like(root.get("code"), "%" + condition.getCode() + "%"));
                }
                if (condition.getStatus()!=null) {
                    predicates.add(cb.equal(root.get("status"), condition.getStatus()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        PageRequest pr = PageUtils.compositePage(req.getPageIndex(), req.getPageSize(), "", "");
        return collegeRepository.findAll(specification, pr);
    }
    
    @Transactional
    public Response<CollegeEntity> get(Long id) {
        CollegeEntity menu = collegeRepository.findOne(id);
        if(menu==null){
            return new Response<>(Response.NORMAL, "学院不存在");
        }
        return new Response<>(menu);
    }

    @Transactional
    public Response<CollegeEntity> add(CollegeEntity entity) {
        int count = collegeRepository.countByCode(entity.getCode());
        if(count > 0){
            return new Response<>(Response.NORMAL, "编号已存在");
        }
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity = collegeRepository.saveAndFlush(entity);
        return new Response<>(entity);
    }
    
    @Transactional
    public Response<CollegeEntity> edit(CollegeEntity entity) {
        CollegeEntity menu = collegeRepository.findOne(entity.getId());
        if(menu==null){
            return new Response<>(Response.NORMAL, "学院不存在");
        }
        int count = collegeRepository.countByCodeAndIdNot(entity.getCode(), entity.getId());
        if(count > 0){
            return new Response<>(Response.NORMAL, "编号已存在");
        }
        entity.setUpdateTime(new Date());
        entity = collegeRepository.saveAndFlush(entity);
        return new Response<CollegeEntity>(entity);
    }
    
    @Transactional
    public Response<CollegeEntity> delete(Long id) {
        CollegeEntity menu = collegeRepository.findOne(id);
        if(menu==null){
            return new Response<>(Response.NORMAL, "学院不存在");
        }
//        List<CollegeMenuEntity> rms = collegeMenuService.getByCollegeId(id);
//        if(!StringUtil.isEmpty(rms)){
//            return new Response<>(Response.NORMAL, "请先删除学院关联的菜单！");
//        }
        collegeRepository.delete(id);
        return new Response<CollegeEntity>();
    }
    
    @SuppressWarnings("resource")
    @Transactional
    public Response<String> importFile(Uploader up) {
        String fileName = up.getFileName();
        OutputStream output=null;
        Response<String> result = new Response<>(Response.OK, "导入成功！");
        try {
            String filePath = up.getPhysicalPath(up.getUrl());
            InputStream inputStream = new FileInputStream(filePath);
            //Excel的文档对象
            Workbook workbook = null;
            String xls = ".xls";
            String xlsx = ".xlsx";
            if(fileName.toLowerCase().endsWith(xls)){
                workbook = new HSSFWorkbook(inputStream);
            }else if(fileName.toLowerCase().endsWith(xlsx)){
                workbook = new XSSFWorkbook(inputStream);
            }else{
                return new Response<>(Response.NORMAL, "请导入Excel文件！");
            }
            String err = "";
            Sheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            if(rowNum>0){
                List<CollegeEntity> collegeDoList = new ArrayList<>();
                boolean check = true;
                Row rowData;
                Cell cellData;
                rowData = sheet.getRow(0);
                //获取总列数
                int columnNum=rowData.getPhysicalNumberOfCells();
                List<String> headList =  new ArrayList<>();
                for(int i=0;i<columnNum;i++){
                    headList.add(FileUtil.GetCellValue(rowData.getCell(i)));
                }
                for (int i = 1; i <= rowNum; i++) {
                    CollegeEntity collegeDo = new CollegeEntity();
                    rowData = sheet.getRow(i);
                    if(rowData==null){
                        continue;
                    }
                    for(int j=0;j<columnNum;j++){
                        cellData = rowData.getCell(j);
                        String cellValue = FileUtil.GetCellValue(cellData);
                        if(headList.get(j).contains("学院名称")){
                            collegeDo.setName(cellValue);
                        }else if(headList.get(j).contains("学院编号")){
                            collegeDo.setCode(cellValue);
                        }else if(headList.get(j).contains("所属学校")){
                            if(StringUtil.isEmpty(cellValue)){
                                err += "第"+i+" 行学校名称为空！";
                                check = false;
                                break;
                            }
                            SchoolEntity school = schoolService.getByName(cellValue);
                            if(school==null){
                                err += "第"+i+" 行学校名称不存在！";
                                check = false;
                                break;
                            }
                            collegeDo.setSchoolId(school.getId());
                        }else if(headList.get(j).contains("状态")){
                            if(cellValue.contains("正常")){
                                collegeDo.setStatus(NormalStatusEnum.ENABLED.getValue());
                            }
                            else{
                                collegeDo.setStatus(NormalStatusEnum.DISABLE.getValue());
                            }
                        }
                    }
                    check = true;
                    if(StringUtil.isEmpty(collegeDo.getName())){
                        err += "第"+i+" 行名称为空！";
                        check = false;
                        break;
                    }
                    if(StringUtil.isEmpty(collegeDo.getCode())){
                        err += "第"+i+" 行编号为空！";
                        check = false;
                        break;
                    }
                    if(check){
                        collegeDoList.add(collegeDo);
                    }
                }
                if(err.isEmpty() && !collegeDoList.isEmpty()){
                    collegeRepository.save(collegeDoList);
                }else{
                    result = new Response<>(Response.NORMAL, err);
                }
            }else{
                result = new Response<>(Response.NORMAL, "导入的Excel内容为空！");
            }
        }catch (IOException e){
            result = new Response<>("9909", "导入异常：" + e.getMessage());
        }catch (Exception e){
            result = new Response<>("9909", "导入异常：" + e.getMessage());
        }finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
