package org.ume.school.modules.model;

/**
 * Created by Django on 2017/8/10.
 */
public enum ErrorTag {
    NOT_ALLOW_EDIT("1007", "不允许编辑"),
    NOT_ALLOW_THUMBUP("1002", "不允许点赞"),
    REPEAT_USER_NAME("1003", "用户名已存在"),
    REFERRE_USER_NOT_EXISTS("1013", "推荐人ID不存在"),
    VALEDATE_CODE_ERROR("1008", "验证码错误"),
    NOT_ALLOW_USERNAME_EMPTY("1004", "不允许用户名为空"),
    INVAILD_USER("-1000", "登录已过期，请先登录"),
    NOT_FIND_USER("1005", "找不到用户"), 
    LOGIN_FAILURE("1006", "用户名不存在或者密码错误"),
    OLD_PASSWORD_ERROR("1007", "原密码错误"),
    USER_CHECKED("1008", "您当前已审批通过"),
    IS_ADMIN("1098", "当前用户是管理员"),    
    NOT_ADMIN("1099", "当前用户不是管理员"),    
    PARAM_NULL("1000", "参数为空"),    
    USER_SMS_CODE_NULL("1010", "请先点击获取手机短信验证码"),
    USER_SMS_CODE_ERROR("1011", "手机短信验证码错误"),
    USER_DEAL_PASSWORD_ERROR("1012", "交易密码错误"),
    USER_NOT_CHECKED("1013", "您当前还未实名认证通过，请先到个人中心提交实名认证！"),
    
    PROJECT_NOT_EXISTS("1100", "项目不存在"),
    PROJECT_MONEY_EXISTS("1101", "请先删除该项目的币种设置"),
    PROJECT_MONEY_NOT_EXISTS("1102", "项目币种配置不存在"),
    PROJECT_HAS_MONEY("1103", "该币种已支持数量大于零，不能删除"),
    PROJECT_MONEY_NOT_ENOUTH("1104", "支持数量不能大于该项目币种剩余数量"),
    PROJECT_MONEY_NOT_LIMIT("1105", "支持数量不能大于该项目币种剩余数量"),
    PROJECT_MONEY_USER_ERROR("1106", "该项目的币种您当前余额不足，请先充值"),
    PROJECT_MONEY_USER_EXISTS("1107", "您已支持过该项目"),
    PROJECT_NOT_START("1108", "项目未开始"),
    PROJECT_IS_END("1109", "项目已结束"),    
    MONEY_TYPE_NOT_EXISTS("1110", "币种不存在"),
    MONEY_TYPE_USED("1111", "币种使用中，不能删除"),
    ADVERT_NOT_EXISTS("1120", "广告不存在"),
    CONFIG_NOT_EXISTS("1121", "配置不存在"),
    
    USER_MONEY_RECHARGE_EXISTS("1130", "您还有充值申请待支付"),
    USER_MONEY_RECHARGE_NOT_EXISTS("1131", "充值记录不存在"),
    USER_MONEY_CASH_EXISTS("1140", "您还有提现申请待管理员审核"),
    USER_MONEY_CASH_NOT_EXISTS("1141", "提现记录不存在"),
    USER_MONEY_CASH_MIN("1142", "提现小于最低数量"),
    USER_MONEY_CASH_NOT_ENOUGH("1143", "该币种您当前余额不足"),
    USER_MONEY_CHANGE_NOT_ENOUGH("1144", "该币种您当前余额不足"),
    USER_MONEY_CHANGE_NOT_MIN("1145", "兑换结果数量不能小于0.01"),
    
    
    USER_MONEY_SELL_TYPE_ERROR("1150", "卖出币种和支持币种不能相同"),
    USER_MONEY_SELL_NOT_EXISTS("1151", "卖出记录不存在"),
    USER_MONEY_SELL_CLOSE("1152", "卖单已撤销"),
    USER_MONEY_SELL_MINE("1153", "不能自己交易"),
    USER_MONEY_SELL_LEAVE_MONEY_ENOUGH("1154", "卖单剩余数量为0"),
    USER_MONEY_SELL_LEAVE_MONEY_MORE("1155", "不能超过卖单剩余数量"),
    USER_MONEY_SELL_NOT_USER("1156", "卖单不是当前用户的"),
    USER_MONEY_PROJECT_CLOSE("1160", "项目买单已撤销"),
    USER_MONEY_PROJECT_NOT_USER("1161", "项目买单不是当前用户的"),
    USER_MONEY_PROJECT_SEND_FINISHED("1162", "此项目已发币完成"),
    
    USER_PLAY_RESULT_TIME_OVER("1171", "已超过截止时间"),
    USER_PLAY_RESULT_ERROR("1173", "投注号码异常"),
    USER_PLAY_MONEY_NOT_ENOUTH("1174", "您当前余额不足，请先充值"),
	
	PLAY_REWARD_NOT_EXISTS("1170", "奖项不存在");

    private String message;
    private String code;

    private ErrorTag(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
