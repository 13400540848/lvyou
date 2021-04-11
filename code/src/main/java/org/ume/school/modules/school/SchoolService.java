package org.ume.school.modules.school;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import org.ume.school.modules.utils.PageUtils;

import com.bluesimon.wbf.RequestPager;
import com.bluesimon.wbf.Response;
import com.bluesimon.wbf.enums.NormalStatusEnum;
import com.bluesimon.wbf.utils.FileUtil;
import com.bluesimon.wbf.utils.StringUtil;
import com.bluesimon.wbf.utils.Uploader;

/**
 * Created by Zz on 2021/3/21.
 */
@Service
public class SchoolService {

    @Resource
    private SchoolRepository schoolRepository;

    @SuppressWarnings("unchecked")
    public SchoolEntity getByCode(final String code) {
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<SchoolEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("code"), code));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return schoolRepository.findOne(specification);
    }
    
    @SuppressWarnings("unchecked")
    public SchoolEntity getByCodeNotId(final String code, final Long id) {
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<SchoolEntity>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("code"), code));
                predicates.add(cb.notEqual(root.get("id"), id));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return schoolRepository.findOne(specification);
    }
    
    @Transactional
    public List<SchoolEntity> getAll() {
        return schoolRepository.findAll();
    }
    
    /**
     * 查询并分页
     */
    @SuppressWarnings("unchecked")
    public Page<SchoolEntity> findAll(RequestPager<SchoolEntity> req) {
        final SchoolEntity condition = req.getCondition();
        @SuppressWarnings("rawtypes")
        Specification specification = new Specification<SchoolEntity>() {
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
        return schoolRepository.findAll(specification, pr);
    }
    
    @Transactional
    public Response<SchoolEntity> get(Long id) {
        SchoolEntity menu = schoolRepository.findOne(id);
        if(menu==null){
            return new Response<>(Response.NORMAL, "学校不存在");
        }
        return new Response<>(menu);
    }

    @Transactional
    public Response<SchoolEntity> add(SchoolEntity entity) {
        SchoolEntity menu = this.getByCode(entity.getCode());
        if(menu!=null){
            return new Response<>(Response.NORMAL, "编号已存在");
        }
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity = schoolRepository.saveAndFlush(entity);
        return new Response<>(entity);
    }
    
    @Transactional
    public Response<SchoolEntity> edit(SchoolEntity entity) {
        SchoolEntity menu = schoolRepository.findOne(entity.getId());
        if(menu==null){
            return new Response<>(Response.NORMAL, "学校不存在");
        }
        menu = this.getByCodeNotId(entity.getCode(), entity.getId());
        if(menu!=null){
            return new Response<>(Response.NORMAL, "编号已存在");
        }
        entity.setUpdateTime(new Date());
        entity = schoolRepository.saveAndFlush(entity);
        return new Response<SchoolEntity>(entity);
    }
    
    @Transactional
    public Response<SchoolEntity> delete(Long id) {
        SchoolEntity menu = schoolRepository.findOne(id);
        if(menu==null){
            return new Response<>(Response.NORMAL, "学校不存在");
        }
//        List<SchoolMenuEntity> rms = schoolMenuService.getBySchoolId(id);
//        if(!StringUtil.isEmpty(rms)){
//            return new Response<>(Response.NORMAL, "请先删除学校关联的菜单！");
//        }
        schoolRepository.delete(id);
        return new Response<SchoolEntity>();
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
                List<SchoolEntity> schoolDoList = new ArrayList<>();
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
                    SchoolEntity schoolDo = new SchoolEntity();
                    rowData = sheet.getRow(i);
                    if(rowData==null){
                        continue;
                    }
                    for(int j=0;j<columnNum;j++){
                        cellData = rowData.getCell(j);
                        String cellValue = FileUtil.GetCellValue(cellData);
                        if(headList.get(j).contains("名称")){
                            schoolDo.setName(cellValue);
                        }else if(headList.get(j).contains("编号")){
                            schoolDo.setCode(cellValue);
                        }else if(headList.get(j).contains("状态")){
                            if(cellValue.contains("正常")){
                                schoolDo.setStatus(NormalStatusEnum.ENABLED.getValue());
                            }
                            else{
                                schoolDo.setStatus(NormalStatusEnum.DISABLE.getValue());
                            }
                        }
                    }
                    check = true;
                    if(StringUtil.isEmpty(schoolDo.getName())){
                        err += "第"+i+" 行名称为空！";
                        check = false;
                        break;
                    }
                    if(StringUtil.isEmpty(schoolDo.getCode())){
                        err += "第"+i+" 行编号为空！";
                        check = false;
                        break;
                    }
                    if(check){
                        schoolDoList.add(schoolDo);
                    }
                }
                if(err.isEmpty() && !schoolDoList.isEmpty()){
                    schoolRepository.save(schoolDoList);
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
