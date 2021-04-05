package com.bluesimon.wbf.utils;

import java.io.IOException;

import org.json.JSONException;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

/**
 * 短信辅助类
 */
public class SMSUtil {
 // 短信应用SDK AppID
    int appid = 1400144423; // 1400开头

    // 短信应用SDK AppKey
    String appkey = "d75d48cec30cadb4338ebcf36b904856";

    // 需要发送短信的手机号码
    String[] phoneNumbers = {"13400540848", "18950287119"};

    // 短信模板ID 
    int templateId = 199405;   
    // 签名
    String smsSign = "数字区块分享 "; 
    //5分钟失效
    String loseTime = "5";

    public SMSUtil() {
        
    }
    
    public void send(String phoneNumber) throws Exception {
        try {
            String[] params = {};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber, templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
    }

    public void send(String phoneNumber, String checkCode) throws Exception {
        try {
            String[] params = {checkCode, loseTime};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber, templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.println(result);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
    }
}
