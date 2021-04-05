var Config = {
    "DEBUG" : true,
    "WEB_SERVER_API": "/v0.1/",
    "URI": {
        "LOGIN": "user/login",
        "REG": "user/register",
        "LOGIN_OUT": "user/logout",
        "USER_INFO": "user/info",
        
        "VERIFICATION_CODE":"verificationcode/generate",
        "SMS_SEND": "sms",
        "MODIFY_PASSWORD" : "user/pw",
        "MODIFY_DEAL_PASSWORD" : "user/deal_pw",
        
        "USER_INFO_SAVE": "user/info",
        "USER_INFO_CHECK": "user/check",
        "USER_SMS_SEND": "user/sms",
        "USER_MOBILE_SAVE": "user/mobile",
        
        "CHECK_TOKEN": "WebToken/CheckToken",
        "UPLOAD_IMAGE": "/v0.1/upload",  

        "ADVERT_INDEX": "advert/index",
        "ADVERT_NOTICE": "advert/notice",
        "ADVERT_OFFICIAL_NOTICE": "advert/official_notice",
        "ADVERT_LATEST_ACTIVE": "advert/latest_active",
        "ADVERT_LINK": "advert/link",
        "ADVERT_ABOUT_US": "advert/aboutus",

        
        "ADVERT_INFO": "advert/",

        "PROJECT_RECOMMEND": "project/recommend",
        "PROJECT_LIST": "project/list",
        "PROJECT_INFO": "project/",

        "PROJECT_MONEY_SEARCH": "project/money",
        "PROJECT_MONEY_INFO": "project/money/",
        "PROJECT_MONEY_SAVE": "project/money",
        "PROJECT_MONEY_DELETE": "project/money/",

        "MONEY_TYPE_SEARCH": "moneytype/list",
        "MONEY_TYPE_INFO": "moneytype/",
        "MONEY_TYPE_SAVE": "moneytype",
        "MONEY_TYPE_DELETE": "moneytype/",

        "CONFIG_LIST": "config/list",
        "CONFIG_INFO": "config/",

        "PLAY_SEVEN": "play/seven",
        "PLAY_SEVEN_REWARD": "play/seven/reward",
        "PLAY_SEVEN_RESULT_TODAY_LAST": "play/seven/result/today/last",
        "PLAY_SEVEN_RESULT_FRONT": "play/seven/result/front",
        "PLAY_SEVEN_RESULT_JOIN": "play/seven/result/join",
        "PLAY_SEVEN_RESULT_WAIT": "play/seven/result/wait",
        "PLAY_SEVEN_RESULT_FINISHED": "play/seven/result/finished",
        "PLAY_SEVEN_RESULT_DAY": "play/seven/result/day",

        "PLAY_SEVEN_RESULT_LOG": "play/seven/result/log",
        "PLAY_SEVEN_RESULT_LOG_DAY": "play/seven/result/log/day",

        "PLAY_THREE": "play/three",
        "PLAY_THREE_REWARD": "play/three/reward",
        "PLAY_THREE_RESULT_TODAY_LAST": "play/three/result/today/last",
        "PLAY_THREE_RESULT_FRONT": "play/three/result/front",
        "PLAY_THREE_RESULT_JOIN": "play/three/result/join",
        "PLAY_THREE_RESULT_WAIT": "play/three/result/wait",
        "PLAY_THREE_RESULT_WAIT_END": "play/three/result/wait/end",
        "PLAY_THREE_RESULT_FINISHED": "play/three/result/finished",
        "PLAY_THREE_RESULT_DAY": "play/three/result/day",
        
        "PLAY_THREE_RESULT_LOG": "play/three/result/log",
        "PLAY_THREE_RESULT_LOG_DAY": "play/three/result/log/day",
        

        "USER_MONEY_RECHARGE_LIST": "user/money/recharge/list",
        "USER_MONEY_RECHARGE_SAVE": "user/money/recharge",
        "USER_MONEY_RECHARGE_PAY": "user/money/recharge/pay",
        "USER_MONEY_RECHARGE_CANCLE": "user/money/recharge/cancle",
        "USER_MONEY_CASH_LIST": "user/money/cash/list",
        "USER_MONEY_CASH_SAVE": "user/money/cash",
        "USER_MONEY_SELL_LIST": "user/money/sell/list",
        
        "USER_MONEY_SELL_OTHER": "user/money/sell/other",
        "USER_MONEY_CELL_INFO": "user/money/sell/",
        "USER_MONEY_SELL_SAVE": "user/money/sell",
        "USER_MONEY_SELL_CANCEL": "user/money/sell/cancel",
        
        "USER_MONEY_BUY_LIST": "user/money/buy/list",
        "USER_MONEY_BUY_SAVE": "user/money/buy",
        
        "USER_MONEY_PROJECT_LIST": "user/money/project",
        "USER_MONEY_PROJECT_SAVE": "user/money/project",
        "USER_MONEY_PROJECT_CANCEL": "user/money/project/cancel",
        "USER_MONEY_LIST": "user/money/list",
       
        "USER_MONEY_INFO": "user/money/",
        "USER_MONEY_LOG_LIST": "user/money/log/list",

        "USER_MONEY_CHANGE_LIST": "user/money/change",
        "USER_MONEY_CHANGE_SAVE": "user/money/change",

        "USER_INIVITE_REWARD_LIST": "user/invite/reward/list",
        "USER_PLAY_SEVEN_LIST": "user/play/seven/list",
        "USER_PLAY_SEVEN_REWARD_LIST": "user/play/seven/reward/list",
        "USER_PLAY_SEVEN_SAVE": "user/play/seven",

        "USER_PLAY_THREE_LIST": "user/play/three/list",
        "USER_PLAY_THREE_REWARD_LIST": "user/play/three/reward/list",
        "USER_PLAY_THREE_SAVE": "user/play/three"
    },
    "QUERY_KEY":{
        "USER_SEARCH": "%B/S%P_USER_ARCH_NO_QUERY",
        "USER_SEARCH_D": "%B/S%P_USER_QUERY_BY_ARCHNO",
        "USER_SEARCH_USE": "%B/S%P_WATER_USE_QUERY",
        "USER_SEARCH_DEBTS": "%B/S%P_USER_DEBTS_INFO_QUERY",
        "USER_SEARCH_RECODE": "%B/S%P_USER_PAY_RECORD_QUERY",
        "USER_SEARCH_CHANGE": "%B/S%P_USER_CHANGE_DAILY_QUERY",
        "USER_SUM_GROUP_QUERY": "%B/S%P_USER_SUM_GROUP_QUERY",
        "READ_METER_STATISTIC": "%B/S%P_READ_METER_STATISTICS",
        "CHARGE_SORT_STATISTIC": "%B/S%P_CHARGE_SORT_STATISTICS",
        "DEBTS_SORT_STATISTIC": "%B/S%P_DEBTS_SORT_STATISTICS"
    }
};