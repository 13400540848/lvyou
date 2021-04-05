package org.ume.school.modules.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Created by Django on 2017/7/30.
 */
public class PageUtils {

    /**
     * 组成PageRequest
     *
     * @param pageIndex      ，  第一页为1 ，MySQL内部为0
     * @param pageSize
     * @param orderby
     * @param direction
     * @return
     */
    public static PageRequest compositePage(Integer pageIndex, Integer pageSize, String orderby, String direction) {
        Sort sort = null;
        if (!StringUtils.isEmpty(orderby)) {
            Sort.Direction dir = Sort.Direction.ASC;
            if (!StringUtils.isEmpty(direction)) {
                if ("DESC".equals(direction.toUpperCase())) {
                    dir = Sort.Direction.DESC;
                }
            }
            sort = new Sort(dir, orderby);
        }
        Integer offset = pageIndex;
        if (offset == null || offset <= 0) {
            offset = 1;
        }
        Integer size = pageSize;
        if (size == null || size <= 0) {
            size = 10;
        }
        PageRequest pr = new PageRequest(offset - 1, pageSize, sort);
        return pr;
    }
    
    public static PageRequest compositePage(String pageIndex, String pageSize, String orderby, String direction) {
        return compositePage(Integer.parseInt(pageIndex), Integer.parseInt(pageSize), orderby, direction);
    }
}
