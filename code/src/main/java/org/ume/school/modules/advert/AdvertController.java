package org.ume.school.modules.advert;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ume.school.modules.model.entity.Advert;

import com.bluesimon.wbf.Response;

/**
 * Created by Zz on 2018/8/30.
 */
@RestController
@RequestMapping("/v0.1/advert")
public class AdvertController {

    @Resource
    private AdvertService advertService;

//    /**
//     * 获取首页轮播
//     */
//    @RequestMapping(value = "/index", method = RequestMethod.GET)
//    public Response<List<Advert>> getIndexAdverts() {
//        List<Advert> list = advertService.findIndexAdverts();
//        return new Response<>(list);
//    }
//    
//    /**
//     * 获取首页通知
//     */
//    @RequestMapping(value = "/notice", method = RequestMethod.GET)
//    public Response<List<Advert>> getNoticeAdverts() {
//        List<Advert> list = advertService.findNoticeAdverts();
//        return new Response<>(list);
//    }
//    
//    /**
//     * 获取官方公告
//     */
//    @RequestMapping(value = "/official_notice", method = RequestMethod.GET)
//    public Response<List<Advert>> getOfficialNoticeAdverts() {
//        List<Advert> list = advertService.findOfficialNoticeAdverts();
//        return new Response<>(list);
//    }
//    
//    /**
//     * 获取最新活动
//     */
//    @RequestMapping(value = "/latest_active", method = RequestMethod.GET)
//    public Response<List<Advert>> getLatestActiveAdverts() {
//        List<Advert> list = advertService.findLatestActiveAdverts();
//        return new Response<>(list);
//    }
//    
//    /**
//     * 获取友情链接
//     */
//    @RequestMapping(value = "/link", method = RequestMethod.GET)
//    public Response<List<Advert>> getLinkAdverts() {
//        List<Advert> list = advertService.findLinkAdverts();
//        return new Response<>(list);
//    }
//    
//    
//    
//    /**
//     * 获取关于我们
//     */
//    @RequestMapping(value = "/aboutus", method = RequestMethod.GET)
//    public Response<Advert> getAboutUsAdverts() {
//        Advert item = advertService.findAboutUsAdvert();
//        return new Response<>(item);
//    }
//    
//    /**
//     * 获取详情
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public Response<Advert> getAdvertById(@PathVariable("id") String id) {
//        Advert model = advertService.findById(id);
//        return new Response<>(model);
//    }
}
