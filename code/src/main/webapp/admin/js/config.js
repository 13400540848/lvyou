var Config = {
    "DEBUG" : true,
    "WEB_SERVER_API": "/v0.1/admin/",
    "URI": {
        "LOGIN": "user/login",
        "LOGIN_OUT": "user/logout",
        "LOGIN_INFO": "user/info",
        "USER_MENU": "user/menus",        
        "DICT_ITEMS":"dict/item/list",

        "DICT_SEARCH":"dict/search",
        "DICT_DELETE":"dict/",
        "DICT_INFO":"dict/",
        "DICT_SAVE":"dict/",
        
        "DICT_ITEM_SEARCH":"dict/item/search",
        "DICT_ITEM_DELETE":"dict/item/",
        "DICT_ITEM_INFO":"dict/item/",
        "DICT_ITEM_SAVE":"dict/item/",
        
        "MENU_SEARCH":"menu/search",
        "MENU_NAVS":"menu/navs",
        "MENU_DELETE":"menu/",
        "MENU_INFO":"menu/",
        "MENU_SAVE":"menu/",
        "MENU_ROLE_SET":"menu/role/set/",
        "MENU_ROLE_SAVE":"menu/role/save",
        
        "ROLE_SEARCH":"role/search",
        "ROLE_DELETE":"role/",
        "ROLE_INFO":"role/",
        "ROLE_SAVE":"role/",
        
        "ROLE_MENU_SEARCH":"role/menu/",
        "ROLE_MENU_SET":"role/menu/set/",
        "ROLE_MENU_SAVE":"role/menu/save",
        
        "USER_SEARCH":"user/search",
        "USER_INFO": "user/",
        "USER_SAVE": "user/save",
        "USER_DELETE": "user/",
        "USER_SAVE_INFO": "user/saveInfo",
        "USER_MODIFY_PASSWORD" : "user/password",
        "USER_PASSWORD_RESET":"user/password/reset/",
        "USER_ROLE_INFO":"user/role/",
        "USER_ROLE_SET":"user/role/set/",
        "USER_ROLE_SAVE":"user/role/save",

        "SCHOOL_SEARCH":"school/search",
        "SCHOOL_ALL":"school/all",
        "SCHOOL_DELETE":"school/",
        "SCHOOL_INFO":"school/",
        "SCHOOL_SAVE":"school/",
        "SCHOOL_IMPORT":"school/import",
        "SCHOOL_EXPORT":"school/export",
        
        "COLLEGE_SEARCH":"college/search",
        "COLLEGE_DELETE":"college/",
        "COLLEGE_INFO":"college/",
        "COLLEGE_SAVE":"college/",
        "COLLEGE_IMPORT":"college/import",
        "COLLEGE_EXPORT":"college/export",

        "CHECK_TOKEN": "WebToken/CheckToken",
        "UPLOAD_IMAGE": "/v0.1/upload",
        "DOWNLOAD_FILE": "/v0.1/download",

        "PROJECT_SEARCH": "project",
        "PROJECT_INFO": "project/",
        "PROJECT_SAVE": "project/",
        "PROJECT_DELETE": "project/",        

        "PROJECT_MONEY_SEARCH": "project/money",
        "PROJECT_MONEY_INFO": "project/money/",
        "PROJECT_MONEY_SAVE": "project/money",
        "PROJECT_MONEY_DELETE": "project/money/",

        "PROJECT_MONEY_SEND_SEARCH": "project/money/send",
        "PROJECT_MONEY_SEND_SAVE": "project/money/send",

        "MONEY_TYPE_SEARCH": "moneytype",
        "MONEY_TYPE_INFO": "moneytype/",
        "MONEY_TYPE_SAVE": "moneytype",
        "MONEY_TYPE_DELETE": "moneytype/",

        "CONFIG_SEARCH": "config",
        "CONFIG_INFO": "config/",
        "CONFIG_SAVE": "config",
        "CONFIG_DELETE": "config/",

        "ADVERT_SEARCH": "advert",
        "ADVERT_INFO": "advert/",
        "ADVERT_SAVE": "advert/",
        "ADVERT_DELETE": "advert/",
        "ADVERT_LOCATION_SEARCH": "advert/location", 
        "ADVERT_STATUS_SEARCH": "advert/status", 
        "ADVERT_LINK_TYPE_SEARCH": "advert/link_type",        
               

     

        "USER_MONEY_RECHARGE_SEARCH":"user/money/recharge",
        "USER_MONEY_RECHARGE_CHECK":"user/money/recharge",
        "USER_MONEY_CASH_SEARCH":"user/money/cash",
        "USER_MONEY_CASH_CHECK":"user/money/cash",

        "USER_MONEY_RECHARGE_LIST": "user/money/recharge/list",
        "USER_MONEY_RECHARGE_SAVE": "user/money/recharge",
        "USER_MONEY_CASH_LIST": "user/money/cash/list",
        "USER_MONEY_CASH_SAVE": "user/money/cash",
        "USER_MONEY_SELL_LIST": "user/money/sell",
        "USER_MONEY_BUY_LIST": "user/money/buy",
        
        "USER_MONEY_PROJECT_LIST": "user/money/project/",
        "USER_MONEY_PROJECT_SAVE": "user/money/project",
        "USER_MONEY_PROJECT_CANCEL": "user/money/project/cancel",
        "USER_MONEY_LIST": "user/money",
        "USER_MONEY_LOG_LIST": "user/money/log",

        "USER_PLAY_SEVEN_LIST": "user/play/seven",
        "USER_PLAY_THREE_LIST": "user/play/three",

        "PLAY_SEVEN": "play/seven",        
        "PLAY_SEVEN_START_SERVICE": "play/seven/start",        
        "PLAY_SEVEN_REWRAD": "play/seven/reward",
        "PLAY_SEVEN_RESULT": "play/seven/result",
        "PLAY_SEVEN_RESULT_REWRAD": "play/seven/result/reward",

        "PLAY_THREE": "play/three",        
        "PLAY_THREE_START_SERVICE": "play/three/start",        
        "PLAY_THREE_REWRAD": "play/three/reward",
        "PLAY_THREE_RESULT": "play/three/result",
        "PLAY_THREE_RESULT_REWRAD": "play/three/result/reward"
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