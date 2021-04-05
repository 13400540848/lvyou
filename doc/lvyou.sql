/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.0.182
 Source Server Type    : MySQL
 Source Server Version : 50562
 Source Host           : 192.168.0.182:12306
 Source Schema         : lvyou

 Target Server Type    : MySQL
 Target Server Version : 50562
 File Encoding         : 65001

 Date: 05/04/2021 20:46:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bs_advert
-- ----------------------------
DROP TABLE IF EXISTS `bs_advert`;
CREATE TABLE `bs_advert`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `link_project` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `link_type` int(11) NULL DEFAULT NULL,
  `link_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `location` int(11) NOT NULL DEFAULT 0,
  `show_order` int(11) NULL DEFAULT NULL,
  `start_time` datetime NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT 0,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_advert
-- ----------------------------

-- ----------------------------
-- Table structure for bs_college
-- ----------------------------
DROP TABLE IF EXISTS `bs_college`;
CREATE TABLE `bs_college`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `creator_id` int(11) NULL DEFAULT 0 COMMENT '创建人',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `school_id` int(11) NULL DEFAULT NULL COMMENT '学校id',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态，0正常 1禁用',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_college
-- ----------------------------

-- ----------------------------
-- Table structure for bs_config
-- ----------------------------
DROP TABLE IF EXISTS `bs_config`;
CREATE TABLE `bs_config`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置编码',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_config
-- ----------------------------

-- ----------------------------
-- Table structure for bs_money_type
-- ----------------------------
DROP TABLE IF EXISTS `bs_money_type`;
CREATE TABLE `bs_money_type`  (
  `type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种编号',
  `cash_broke_max` double(18, 8) NULL DEFAULT 1.00000000 COMMENT '提现手续费上限',
  `cash_broke_min` double(18, 8) NULL DEFAULT 1.00000000 COMMENT '提现手续费最小值',
  `cash_broke_percent` double(10, 2) NULL DEFAULT 1.00 COMMENT '提现手续费百分比',
  `cash_min` double(10, 2) NULL DEFAULT 1.00 COMMENT '提现下限数量',
  `dg_broke_percent` double(10, 2) NULL DEFAULT 0.00 COMMENT 'DG兑换手续费',
  `dg_scale` double(10, 2) NULL DEFAULT 0.00 COMMENT 'DG兑换比例',
  `qr_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '币种二维码',
  `recharge_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '币种充值地址',
  `recharge_broke_percent` double(10, 2) NULL DEFAULT 0.00 COMMENT '充值手续费百分比',
  `type_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '币种简称',
  `type_mode` int(1) NULL DEFAULT 0 COMMENT '币种类型：0代币 1投资币种 2系统',
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '币种名称',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_money_type
-- ----------------------------

-- ----------------------------
-- Table structure for bs_play_seven
-- ----------------------------
DROP TABLE IF EXISTS `bs_play_seven`;
CREATE TABLE `bs_play_seven`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `all_money` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '奖池',
  `count_time` int(11) NULL DEFAULT 1 COMMENT '一天多少期',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `end_time` int(11) NULL DEFAULT 0 COMMENT '截止时间 单位秒(300)',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `per_money` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '每注金额',
  `play_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `play_rate` int(4) NULL DEFAULT 10 COMMENT '开奖频率（分钟）',
  `play_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注说明',
  `start_time` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一期开始时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_play_seven
-- ----------------------------

-- ----------------------------
-- Table structure for bs_play_seven_result
-- ----------------------------
DROP TABLE IF EXISTS `bs_play_seven_result`;
CREATE TABLE `bs_play_seven_result`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `count_number` int(11) NULL DEFAULT 0 COMMENT '投注数量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `day_index` int(11) NULL DEFAULT 0 COMMENT '当天第几注',
  `end_time` datetime NULL DEFAULT NULL COMMENT '截止时间',
  `leave_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '利润',
  `one_number` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绿色选号',
  `per_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '单注金额',
  `play_time` double(16, 2) NULL DEFAULT 0.00 COMMENT '期数 20181031073',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '开奖时间',
  `reward_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '中奖总金额',
  `six_number` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '橘色选号',
  `status` int(1) NULL DEFAULT 0 COMMENT '状态（已开奖1、待开奖0）',
  `sum_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '总金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_play_seven_result
-- ----------------------------

-- ----------------------------
-- Table structure for bs_play_seven_result_log
-- ----------------------------
DROP TABLE IF EXISTS `bs_play_seven_result_log`;
CREATE TABLE `bs_play_seven_result_log`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `one1` int(11) NULL DEFAULT 0 COMMENT '一球1（累计未中次数）',
  `one10` int(11) NULL DEFAULT 0 COMMENT '一球10（累计未中次数）',
  `one11` int(11) NULL DEFAULT 0 COMMENT '一球11（累计未中次数）',
  `one12` int(11) NULL DEFAULT 0 COMMENT '一球12（累计未中次数）',
  `one13` int(11) NULL DEFAULT 0 COMMENT '一球13（累计未中次数）',
  `one14` int(11) NULL DEFAULT 0 COMMENT '一球14（累计未中次数）',
  `one15` int(11) NULL DEFAULT 0 COMMENT '一球15（累计未中次数）',
  `one16` int(11) NULL DEFAULT 0 COMMENT '一球16（累计未中次数）',
  `one2` int(11) NULL DEFAULT 0 COMMENT '一球2（累计未中次数）',
  `one3` int(11) NULL DEFAULT 0 COMMENT '一球3（累计未中次数）',
  `one4` int(11) NULL DEFAULT 0 COMMENT '一球4（累计未中次数）',
  `one5` int(11) NULL DEFAULT 0 COMMENT '一球5（累计未中次数）',
  `one6` int(11) NULL DEFAULT 0 COMMENT '一球6（累计未中次数）',
  `one7` int(11) NULL DEFAULT 0 COMMENT '一球7（累计未中次数）',
  `one8` int(11) NULL DEFAULT 0 COMMENT '一球8（累计未中次数）',
  `one9` int(11) NULL DEFAULT 0 COMMENT '一球9（累计未中次数）',
  `play_time` double(16, 2) NULL DEFAULT 0.00 COMMENT '期数 20181031073',
  `six1` int(11) NULL DEFAULT 0 COMMENT '六球1（累计未中次数）',
  `six10` int(11) NULL DEFAULT 0 COMMENT '六球10（累计未中次数）',
  `six11` int(11) NULL DEFAULT 0 COMMENT '六球11（累计未中次数）',
  `six12` int(11) NULL DEFAULT 0 COMMENT '六球12（累计未中次数）',
  `six13` int(11) NULL DEFAULT 0 COMMENT '六球13（累计未中次数）',
  `six14` int(11) NULL DEFAULT 0 COMMENT '六球14（累计未中次数）',
  `six15` int(11) NULL DEFAULT 0 COMMENT '六球15（累计未中次数）',
  `six16` int(11) NULL DEFAULT 0 COMMENT '六球16（累计未中次数）',
  `six17` int(11) NULL DEFAULT 0 COMMENT '六球17（累计未中次数）',
  `six18` int(11) NULL DEFAULT 0 COMMENT '六球18（累计未中次数）',
  `six19` int(11) NULL DEFAULT 0 COMMENT '六球19（累计未中次数）',
  `six2` int(11) NULL DEFAULT 0 COMMENT '六球2（累计未中次数）',
  `six20` int(11) NULL DEFAULT 0 COMMENT '六球20（累计未中次数）',
  `six21` int(11) NULL DEFAULT 0 COMMENT '六球21（累计未中次数）',
  `six22` int(11) NULL DEFAULT 0 COMMENT '六球22（累计未中次数）',
  `six23` int(11) NULL DEFAULT 0 COMMENT '六球23（累计未中次数）',
  `six24` int(11) NULL DEFAULT 0 COMMENT '六球24（累计未中次数）',
  `six25` int(11) NULL DEFAULT 0 COMMENT '六球15（累计未中次数）',
  `six26` int(11) NULL DEFAULT 0 COMMENT '六球26（累计未中次数）',
  `six27` int(11) NULL DEFAULT 0 COMMENT '六球27（累计未中次数）',
  `six28` int(11) NULL DEFAULT 0 COMMENT '六球28（累计未中次数）',
  `six29` int(11) NULL DEFAULT 0 COMMENT '六球29（累计未中次数）',
  `six3` int(11) NULL DEFAULT 0 COMMENT '六球3（累计未中次数）',
  `six30` int(11) NULL DEFAULT 0 COMMENT '六球30（累计未中次数）',
  `six31` int(11) NULL DEFAULT 0 COMMENT '六球31（累计未中次数）',
  `six32` int(11) NULL DEFAULT 0 COMMENT '六球32（累计未中次数）',
  `six33` int(11) NULL DEFAULT 0 COMMENT '六球33（累计未中次数）',
  `six4` int(11) NULL DEFAULT 0 COMMENT '六球4（累计未中次数）',
  `six5` int(11) NULL DEFAULT 0 COMMENT '六球5（累计未中次数）',
  `six6` int(11) NULL DEFAULT 0 COMMENT '六球6（累计未中次数）',
  `six7` int(11) NULL DEFAULT 0 COMMENT '六球7（累计未中次数）',
  `six8` int(11) NULL DEFAULT 0 COMMENT '六球8（累计未中次数）',
  `six9` int(11) NULL DEFAULT 0 COMMENT '六球9（累计未中次数）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_play_seven_result_log
-- ----------------------------

-- ----------------------------
-- Table structure for bs_play_seven_result_reward
-- ----------------------------
DROP TABLE IF EXISTS `bs_play_seven_result_reward`;
CREATE TABLE `bs_play_seven_result_reward`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `count_number` int(11) NULL DEFAULT 0 COMMENT '注数',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `per_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '每注金额',
  `play_time` double(16, 2) NULL DEFAULT 0.00 COMMENT '期数 20181031073',
  `reward_code` int(4) NULL DEFAULT 0 COMMENT '奖项标识(1-6)',
  `reward_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '奖金',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_play_seven_result_reward
-- ----------------------------

-- ----------------------------
-- Table structure for bs_play_seven_reward
-- ----------------------------
DROP TABLE IF EXISTS `bs_play_seven_reward`;
CREATE TABLE `bs_play_seven_reward`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `max_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '封顶金额(浮动金额)',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `require_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '最低要求奖池数量(浮动金额)',
  `reward_code` int(4) NULL DEFAULT 0 COMMENT '奖项标识(1-6)',
  `reward_mode` int(1) NULL DEFAULT 0 COMMENT '奖励类型（0固定金额、1浮动金额）',
  `reward_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '奖项名称',
  `reward_percent` double(10, 2) NULL DEFAULT 0.00 COMMENT '浮动奖金百分比(浮动金额)',
  `reward_times` double(10, 2) NULL DEFAULT 0.00 COMMENT '奖励倍数（固定金额）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_play_seven_reward
-- ----------------------------

-- ----------------------------
-- Table structure for bs_play_three
-- ----------------------------
DROP TABLE IF EXISTS `bs_play_three`;
CREATE TABLE `bs_play_three`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `all_money` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '奖池',
  `count_time` int(11) NULL DEFAULT 1 COMMENT '一天多少期',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `end_time` int(11) NULL DEFAULT 0 COMMENT '截止时间 单位秒(120)',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `per_money` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '每注金额',
  `play_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `play_rate` int(4) NULL DEFAULT 10 COMMENT '开奖频率（分钟）',
  `play_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注说明',
  `start_time` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第一期开始时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_play_three
-- ----------------------------

-- ----------------------------
-- Table structure for bs_play_three_result
-- ----------------------------
DROP TABLE IF EXISTS `bs_play_three_result`;
CREATE TABLE `bs_play_three_result`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `count_number` int(11) NULL DEFAULT 0 COMMENT '投注数量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `day_index` int(11) NULL DEFAULT 0 COMMENT '当天第几注',
  `end_time` datetime NULL DEFAULT NULL COMMENT '截止时间',
  `leave_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '利润',
  `mode` int(4) NULL DEFAULT 0 COMMENT '选号形态（三同号、三不同号、二同号）',
  `number1` int(1) NULL DEFAULT NULL COMMENT '选号1',
  `number2` int(1) NULL DEFAULT NULL COMMENT '选号2',
  `number3` int(1) NULL DEFAULT NULL COMMENT '选号3',
  `per_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '单注金额',
  `play_time` double(16, 2) NULL DEFAULT 0.00 COMMENT '期数 20181031073',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '开奖时间',
  `reward_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '中奖总金额',
  `status` int(1) NULL DEFAULT 0 COMMENT '状态（已开奖1、待开奖0）',
  `sum_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '总金额',
  `sum_number` int(11) NULL DEFAULT 0 COMMENT '和值（3-18）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_play_three_result
-- ----------------------------

-- ----------------------------
-- Table structure for bs_play_three_result_log
-- ----------------------------
DROP TABLE IF EXISTS `bs_play_three_result_log`;
CREATE TABLE `bs_play_three_result_log`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `n1` int(11) NULL DEFAULT 0 COMMENT '选号1（累计未中次数）',
  `n2` int(11) NULL DEFAULT 0 COMMENT '选号2（累计未中次数）',
  `n3` int(11) NULL DEFAULT 0 COMMENT '选号3（累计未中次数）',
  `n4` int(11) NULL DEFAULT 0 COMMENT '选号4（累计未中次数）',
  `n5` int(11) NULL DEFAULT 0 COMMENT '选号5（累计未中次数）',
  `n6` int(11) NULL DEFAULT 0 COMMENT '选号6（累计未中次数）',
  `number1` int(1) NULL DEFAULT 0 COMMENT '开奖号码1',
  `number2` int(1) NULL DEFAULT 0 COMMENT '开奖号码2',
  `number3` int(1) NULL DEFAULT 0 COMMENT '开奖号码3',
  `play_time` double(16, 2) NULL DEFAULT 0.00 COMMENT '期数 20181031073',
  `sum10` int(11) NULL DEFAULT 0 COMMENT '和值10（累计未中次数）',
  `sum11` int(11) NULL DEFAULT 0 COMMENT '和值11（累计未中次数）',
  `sum12` int(11) NULL DEFAULT 0 COMMENT '和值12（累计未中次数）',
  `sum13` int(11) NULL DEFAULT 0 COMMENT '和值13（累计未中次数）',
  `sum14` int(11) NULL DEFAULT 0 COMMENT '和值14（累计未中次数）',
  `sum15` int(11) NULL DEFAULT 0 COMMENT '和值15（累计未中次数）',
  `sum16` int(11) NULL DEFAULT 0 COMMENT '和值16（累计未中次数）',
  `sum17` int(11) NULL DEFAULT 0 COMMENT '和值17（累计未中次数）',
  `sum18` int(11) NULL DEFAULT 0 COMMENT '和值18（累计未中次数）',
  `sum3` int(11) NULL DEFAULT 0 COMMENT '和值3（累计未中次数）',
  `sum4` int(11) NULL DEFAULT 0 COMMENT '和值4（累计未中次数）',
  `sum5` int(11) NULL DEFAULT 0 COMMENT '和值5（累计未中次数）',
  `sum6` int(11) NULL DEFAULT 0 COMMENT '和值6（累计未中次数）',
  `sum7` int(11) NULL DEFAULT 0 COMMENT '和值7（累计未中次数）',
  `sum8` int(11) NULL DEFAULT 0 COMMENT '和值8（累计未中次数）',
  `sum9` int(11) NULL DEFAULT 0 COMMENT '和值9（累计未中次数）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_play_three_result_log
-- ----------------------------

-- ----------------------------
-- Table structure for bs_play_three_result_reward
-- ----------------------------
DROP TABLE IF EXISTS `bs_play_three_result_reward`;
CREATE TABLE `bs_play_three_result_reward`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `count_number` int(11) NULL DEFAULT 0 COMMENT '注数',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `per_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '每注金额',
  `play_time` double(16, 2) NULL DEFAULT 0.00 COMMENT '期数 20181031073',
  `reward_code` int(4) NULL DEFAULT 0 COMMENT '奖项标识',
  `reward_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '奖金',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_play_three_result_reward
-- ----------------------------

-- ----------------------------
-- Table structure for bs_play_three_reward
-- ----------------------------
DROP TABLE IF EXISTS `bs_play_three_reward`;
CREATE TABLE `bs_play_three_reward`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `reward_code` int(4) NULL DEFAULT 0 COMMENT '奖项标识(3……)',
  `reward_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '奖项名称',
  `reward_times` double(10, 2) NULL DEFAULT 0.00 COMMENT '奖励倍数（固定金额）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_play_three_reward
-- ----------------------------

-- ----------------------------
-- Table structure for bs_project
-- ----------------------------
DROP TABLE IF EXISTS `bs_project`;
CREATE TABLE `bs_project`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `admin_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `admin_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `broke_percent` float NULL DEFAULT 0 COMMENT '手续费',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT NULL,
  `description` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `end_time` datetime NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面',
  `is_recommend` int(11) NULL DEFAULT 0,
  `money_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '投资币种',
  `progress` double(10, 2) NULL DEFAULT 0.00 COMMENT '当前进度',
  `star` int(11) NULL DEFAULT 1 COMMENT '评级 1-5 星',
  `start_time` datetime NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT 0,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `update_time` datetime NULL DEFAULT NULL,
  `user_count` int(11) NULL DEFAULT 0 COMMENT '投资用户数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_project
-- ----------------------------

-- ----------------------------
-- Table structure for bs_project_money
-- ----------------------------
DROP TABLE IF EXISTS `bs_project_money`;
CREATE TABLE `bs_project_money`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `all_money` double(10, 2) NOT NULL COMMENT '目标金额',
  `max` double(10, 2) NOT NULL DEFAULT 100.00 COMMENT '上限',
  `min` double(10, 2) NOT NULL DEFAULT 1.00 COMMENT '下限',
  `money` double(10, 2) NOT NULL COMMENT '已投资金额',
  `money_scale` double(10, 2) NOT NULL DEFAULT 0.00 COMMENT '比例',
  `project_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目编号',
  `type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_project_money
-- ----------------------------

-- ----------------------------
-- Table structure for bs_project_money_send
-- ----------------------------
DROP TABLE IF EXISTS `bs_project_money_send`;
CREATE TABLE `bs_project_money_send`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `all_money` double(10, 2) NOT NULL COMMENT '发币金额',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `project_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目编号',
  `send_money` double(10, 2) NOT NULL COMMENT '已发金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_project_money_send
-- ----------------------------

-- ----------------------------
-- Table structure for bs_school
-- ----------------------------
DROP TABLE IF EXISTS `bs_school`;
CREATE TABLE `bs_school`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学校' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bs_school
-- ----------------------------
INSERT INTO `bs_school` VALUES (11, '福大', 'fzedu', 0, NULL, '2021-04-04 21:26:17', '2021-04-04 21:28:23');

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键标识',
  `object_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务对象',
  `business_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '业务主键',
  `module_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模块名',
  `folder` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件存储文件夹',
  `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件文件名',
  `save_filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件系统生成文件名',
  `extension` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展名',
  `file_size` double NULL DEFAULT NULL COMMENT '文件大小',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '附件管理' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典管理' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, '是否', 'YES_NO', '是否', '2017-02-06 17:54:55', '2017-02-06 17:54:55');
INSERT INTO `sys_dict` VALUES (2, '菜单类型', 'MENU_TYPE', '菜单类型', '2017-02-06 17:54:55', '2017-02-06 17:54:55');
INSERT INTO `sys_dict` VALUES (3, '菜单打开方式', 'MENU_OPEN_MODE', '菜单打开方式', '2021-03-26 22:46:23', '2021-03-26 22:46:27');
INSERT INTO `sys_dict` VALUES (4, '常用状态', 'NORMAL_STATUS', '常用状态', '2021-03-26 23:34:00', '2021-03-26 23:34:04');
INSERT INTO `sys_dict` VALUES (5, '用户类型', 'USER_TYPE', '用户类型', '2021-03-27 22:21:36', '2021-03-27 22:21:38');
INSERT INTO `sys_dict` VALUES (6, '性别', 'SEX', '性别', '2021-03-27 22:28:48', '2021-03-27 22:28:52');

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_id` bigint(20) NOT NULL COMMENT '所属字典',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'code',
  `value` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值',
  `sort` smallint(6) NULL DEFAULT NULL COMMENT '序号',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典项' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES (1, 1, '是', 'yes', '1', 1, '', '2017-04-06 09:00:00', '2017-04-06 09:00:00');
INSERT INTO `sys_dict_item` VALUES (2, 1, '否', 'no', '0', 2, NULL, '2017-04-06 09:00:00', '2017-04-06 09:00:00');
INSERT INTO `sys_dict_item` VALUES (3, 2, '门户', 'nav', '0', 1, NULL, '2017-04-06 09:00:00', '2017-04-06 09:00:00');
INSERT INTO `sys_dict_item` VALUES (4, 2, '菜单', 'menu', '1', 2, NULL, '2017-04-06 09:00:00', '2017-04-06 09:00:00');
INSERT INTO `sys_dict_item` VALUES (5, 3, '默认', 'default', '0', 1, NULL, '2021-03-26 22:47:20', '2021-03-26 22:47:23');
INSERT INTO `sys_dict_item` VALUES (6, 3, '弹出新页面', 'open', '1', 2, NULL, '2021-03-26 22:47:49', '2021-03-26 22:47:52');
INSERT INTO `sys_dict_item` VALUES (7, 4, '正常', 'normal', '0', 1, NULL, '2021-03-26 23:34:37', '2021-03-26 23:34:40');
INSERT INTO `sys_dict_item` VALUES (8, 4, '禁用', 'delete', '1', 2, NULL, '2021-03-26 23:35:11', '2021-03-26 23:35:14');
INSERT INTO `sys_dict_item` VALUES (9, 5, '学生', 'student', '0', 1, NULL, '2021-03-27 22:22:55', '2021-03-27 22:22:57');
INSERT INTO `sys_dict_item` VALUES (10, 5, '超级管理员', 'super_admin', '1', 2, NULL, '2021-03-27 22:24:06', '2021-03-27 22:24:09');
INSERT INTO `sys_dict_item` VALUES (11, 5, '管理员', 'admin', '2', 3, NULL, '2021-03-27 22:25:35', '2021-03-27 22:25:37');
INSERT INTO `sys_dict_item` VALUES (12, 6, '保密', 'password', '0', 1, NULL, '2021-03-27 22:29:42', '2021-03-27 22:29:45');
INSERT INTO `sys_dict_item` VALUES (13, 6, '男', 'man', '1', 2, NULL, '2021-03-27 22:30:19', '2021-03-27 22:30:21');
INSERT INTO `sys_dict_item` VALUES (14, 6, '女', 'woman', '2', 3, NULL, '2021-03-27 22:30:36', '2021-03-27 22:30:40');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问路径',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `open_mode` tinyint(1) NULL DEFAULT NULL COMMENT '打开方式，0 默认 1 弹出新页面',
  `is_hide` tinyint(1) NULL DEFAULT NULL COMMENT '是否隐藏',
  `p_id` bigint(20) NULL DEFAULT NULL COMMENT '上级菜单',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '类型，0 门户 1菜单',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `sort` smallint(6) NULL DEFAULT NULL COMMENT '序号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单资源' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '基础管理', 'base.manage', NULL, NULL, 0, 0, 0, 0, NULL, 99, '2021-03-23 21:41:32');
INSERT INTO `sys_menu` VALUES (2, '角色管理', 'role.manage', 'module/sys/role_manage.html', 'agency', 0, 0, 1, 1, NULL, 1, '2021-03-23 21:44:14');
INSERT INTO `sys_menu` VALUES (3, '用户管理', 'user.manage', 'module/sys/user_manage.html', 'user', 0, 0, 1, 1, '', 2, '2021-03-23 22:29:24');
INSERT INTO `sys_menu` VALUES (4, '菜单管理', 'menu.manage', 'module/sys/menu_manage.html', 'gongwen', 0, 0, 1, 1, NULL, 3, '2021-03-23 22:30:10');
INSERT INTO `sys_menu` VALUES (5, '字典管理', 'dict.manage', 'module/sys/dict_manage.html', 'konwledge', 0, 0, 1, 1, NULL, 4, '2021-03-23 22:30:44');
INSERT INTO `sys_menu` VALUES (6, '学生管理', 'student.manage', '', '', 0, 0, 0, 0, NULL, 80, '2021-03-23 22:47:00');
INSERT INTO `sys_menu` VALUES (7, '学校管理', 'school.manage', 'module/school/school_manage.html', 'nav-info', 0, 0, 6, 1, NULL, 1, '2021-03-23 22:48:25');
INSERT INTO `sys_menu` VALUES (103, 'test', 'test', '', 'office', 0, 1, 1, 0, '', 1, '2021-04-03 17:36:34');

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_org
-- ----------------------------

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
  `o_level` tinyint(4) NULL DEFAULT NULL COMMENT '级别，1 机构、2 部门、3 岗位 ',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '上级',
  `parent_ids` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '递归上级',
  `sort` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织机构' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO `sys_organization` VALUES (1, '龙盛科技有限公司', '0001', 1, NULL, NULL, 1, '2019-05-30 15:52:06', NULL);
INSERT INTO `sys_organization` VALUES (2, '总经办', '001', 2, 1, '1', NULL, '2012-01-01 00:00:00', NULL);
INSERT INTO `sys_organization` VALUES (3, '财务部', '002', 2, 1, '1', NULL, '2012-01-01 00:00:00', NULL);
INSERT INTO `sys_organization` VALUES (4, '综管部', '003', 2, 1, '1', NULL, '2012-01-01 00:00:00', NULL);
INSERT INTO `sys_organization` VALUES (5, '生产部', '004', 2, 1, '1', NULL, '2012-01-01 00:00:00', NULL);
INSERT INTO `sys_organization` VALUES (6, '品管部', '005', 2, 1, '1', NULL, '2012-01-01 00:00:00', NULL);
INSERT INTO `sys_organization` VALUES (7, '仓储部', '006', 2, 1, '1', NULL, '2012-01-01 00:00:00', NULL);
INSERT INTO `sys_organization` VALUES (8, '维修部', '007', 2, 1, '1', NULL, '2012-01-01 00:00:00', NULL);
INSERT INTO `sys_organization` VALUES (9, '生管部', '008', 2, 1, '1', NULL, '2012-08-01 00:00:00', NULL);
INSERT INTO `sys_organization` VALUES (10, '采购部', '009', 2, 1, '1', NULL, '2012-08-01 00:00:00', NULL);
INSERT INTO `sys_organization` VALUES (11, '技术部', '010', 2, 1, '1', NULL, '2012-08-01 00:00:00', NULL);
INSERT INTO `sys_organization` VALUES (12, '市场部', '011', 2, 1, '1', NULL, '2012-08-01 00:00:00', NULL);
INSERT INTO `sys_organization` VALUES (55, '东湖招待中心', '012', 2, 1, NULL, NULL, '2012-08-01 00:00:00', NULL);
INSERT INTO `sys_organization` VALUES (56, '检测中心', '013', 2, 1, NULL, NULL, '2014-11-01 00:00:00', NULL);
INSERT INTO `sys_organization` VALUES (57, '智恒科技股份有限公司', '014', 2, 1, NULL, NULL, '2016-08-17 00:00:00', NULL);
INSERT INTO `sys_organization` VALUES (58, '福州售后部', '015', 2, 1, NULL, NULL, '2018-01-31 00:00:00', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'administrator', 0, '超级管理员', NULL, '2016-05-01 12:07:42', '2016-05-01 12:07:42');
INSERT INTO `sys_role` VALUES (3, '学生会主席', 'student.admin', 0, '学生会主席', NULL, '2016-05-01 12:07:42', '2021-03-27 21:46:16');
INSERT INTO `sys_role` VALUES (5, '仓库管理员', 'stockmanager', 0, '仓库管理员', 2, '2019-06-19 17:15:35', '2019-06-19 17:15:35');
INSERT INTO `sys_role` VALUES (6, '生产管理员', 'producemanager', 0, '生产管理员', 2, '2019-06-19 17:15:58', '2019-06-19 17:15:58');
INSERT INTO `sys_role` VALUES (7, '品质管理员', 'qualitymanager', 0, '品质管理员', 2, '2019-06-19 17:16:48', '2019-10-17 13:28:48');
INSERT INTO `sys_role` VALUES (9, '综合管理员', 'allmanager', 0, '综管部管理员，管理组织和用户', 2, '2019-10-17 13:54:03', '2019-10-17 13:56:25');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 114 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1, 1, '2016-05-01 12:07:42');
INSERT INTO `sys_role_menu` VALUES (11, 206, 5, '2019-07-30 17:11:37');
INSERT INTO `sys_role_menu` VALUES (12, 206, 6, '2019-07-30 17:11:37');
INSERT INTO `sys_role_menu` VALUES (13, 206, 7, '2019-07-30 17:11:37');
INSERT INTO `sys_role_menu` VALUES (14, 31, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_menu` VALUES (15, 33, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_menu` VALUES (16, 34, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_menu` VALUES (17, 36, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_menu` VALUES (18, 37, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_menu` VALUES (19, 39, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_menu` VALUES (20, 40, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_menu` VALUES (21, 43, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_menu` VALUES (22, 44, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_menu` VALUES (23, 46, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_menu` VALUES (24, 47, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_menu` VALUES (25, 48, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_menu` VALUES (26, 50, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_menu` VALUES (27, 51, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_menu` VALUES (28, 52, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_menu` VALUES (29, 53, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_menu` VALUES (30, 56, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_menu` VALUES (31, 65, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_menu` VALUES (32, 66, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_menu` VALUES (33, 71, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_menu` VALUES (34, 73, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_menu` VALUES (35, 75, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_menu` VALUES (36, 76, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_menu` VALUES (37, 77, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_menu` VALUES (38, 121, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (39, 126, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (40, 129, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (41, 130, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (42, 134, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (43, 135, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (44, 136, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (45, 137, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (46, 138, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (47, 142, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (48, 144, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (49, 153, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (50, 156, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (51, 158, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (52, 160, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (53, 161, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (54, 167, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (55, 170, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (56, 171, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (57, 172, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (58, 173, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (59, 174, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (60, 179, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (61, 180, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (62, 181, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (63, 182, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (64, 183, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (65, 184, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (66, 185, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (67, 186, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (68, 187, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (69, 188, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (70, 189, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (71, 190, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (72, 191, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (73, 192, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (74, 193, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (75, 194, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (76, 195, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (77, 196, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (78, 197, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (79, 198, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (80, 199, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (81, 200, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (82, 201, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (83, 202, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (84, 203, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_menu` VALUES (85, 204, 3, '2019-10-17 14:03:06');
INSERT INTO `sys_role_menu` VALUES (86, 205, 3, '2019-10-17 14:03:06');
INSERT INTO `sys_role_menu` VALUES (87, 207, 9, '2019-10-25 08:42:34');
INSERT INTO `sys_role_menu` VALUES (88, 208, 3, '2019-10-25 10:56:57');
INSERT INTO `sys_role_menu` VALUES (89, 209, 3, '2019-10-25 11:05:55');
INSERT INTO `sys_role_menu` VALUES (90, 210, 3, '2019-10-25 11:15:11');
INSERT INTO `sys_role_menu` VALUES (91, 205, 10, '2019-10-25 13:11:29');
INSERT INTO `sys_role_menu` VALUES (92, 73, 6, '2019-10-25 15:34:03');
INSERT INTO `sys_role_menu` VALUES (93, 211, 5, '2019-10-28 14:14:17');
INSERT INTO `sys_role_menu` VALUES (94, 211, 6, '2019-10-28 14:14:17');
INSERT INTO `sys_role_menu` VALUES (95, 211, 7, '2019-10-28 14:14:17');
INSERT INTO `sys_role_menu` VALUES (96, 211, 9, '2019-10-28 14:14:17');
INSERT INTO `sys_role_menu` VALUES (97, 211, 10, '2019-10-28 14:14:17');
INSERT INTO `sys_role_menu` VALUES (98, 212, 6, '2019-11-12 16:53:42');
INSERT INTO `sys_role_menu` VALUES (99, 213, 10, '2019-11-13 14:01:06');
INSERT INTO `sys_role_menu` VALUES (100, 214, 6, '2019-11-15 09:28:41');
INSERT INTO `sys_role_menu` VALUES (101, 215, 3, '2019-11-21 10:01:06');
INSERT INTO `sys_role_menu` VALUES (102, 216, 3, '2019-11-21 10:01:20');
INSERT INTO `sys_role_menu` VALUES (108, 2, 1, '2021-03-27 21:38:22');
INSERT INTO `sys_role_menu` VALUES (109, 3, 1, '2021-03-27 21:38:30');
INSERT INTO `sys_role_menu` VALUES (110, 7, 3, '2021-03-28 00:29:28');
INSERT INTO `sys_role_menu` VALUES (111, 5, 7, '2021-04-03 16:53:51');
INSERT INTO `sys_role_menu` VALUES (112, 7, 5, '2021-04-03 16:56:06');

-- ----------------------------
-- Table structure for sys_role_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_organization`;
CREATE TABLE `sys_role_organization`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色',
  `organization_id` bigint(20) NULL DEFAULT NULL COMMENT '组织机构',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色组织机构' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_organization
-- ----------------------------
INSERT INTO `sys_role_organization` VALUES (1, 5, 7, '2019-06-19 17:19:08');
INSERT INTO `sys_role_organization` VALUES (2, 6, 9, '2019-06-19 17:19:24');
INSERT INTO `sys_role_organization` VALUES (3, 7, 14, '2019-06-19 17:19:46');
INSERT INTO `sys_role_organization` VALUES (4, 7, 6, '2019-06-19 17:20:10');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (1, 1, 1, '2016-05-01 12:07:42');
INSERT INTO `sys_role_user` VALUES (4, 3, 1, '2019-06-03 09:30:35');
INSERT INTO `sys_role_user` VALUES (7, 2, 1, '2019-06-06 11:17:32');
INSERT INTO `sys_role_user` VALUES (11, 206, 5, '2019-07-30 17:11:37');
INSERT INTO `sys_role_user` VALUES (12, 206, 6, '2019-07-30 17:11:37');
INSERT INTO `sys_role_user` VALUES (13, 206, 7, '2019-07-30 17:11:37');
INSERT INTO `sys_role_user` VALUES (14, 31, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_user` VALUES (15, 33, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_user` VALUES (16, 34, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_user` VALUES (17, 36, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_user` VALUES (18, 37, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_user` VALUES (19, 39, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_user` VALUES (20, 40, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_user` VALUES (21, 43, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_user` VALUES (22, 44, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_user` VALUES (23, 46, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_user` VALUES (24, 47, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_user` VALUES (25, 48, 3, '2019-10-17 14:01:44');
INSERT INTO `sys_role_user` VALUES (26, 50, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_user` VALUES (27, 51, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_user` VALUES (28, 52, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_user` VALUES (29, 53, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_user` VALUES (30, 56, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_user` VALUES (31, 65, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_user` VALUES (32, 66, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_user` VALUES (33, 71, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_user` VALUES (34, 73, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_user` VALUES (35, 75, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_user` VALUES (36, 76, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_user` VALUES (37, 77, 3, '2019-10-17 14:02:11');
INSERT INTO `sys_role_user` VALUES (38, 121, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (39, 126, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (40, 129, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (41, 130, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (42, 134, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (43, 135, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (44, 136, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (45, 137, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (46, 138, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (47, 142, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (48, 144, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (49, 153, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (50, 156, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (51, 158, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (52, 160, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (53, 161, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (54, 167, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (55, 170, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (56, 171, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (57, 172, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (58, 173, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (59, 174, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (60, 179, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (61, 180, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (62, 181, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (63, 182, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (64, 183, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (65, 184, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (66, 185, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (67, 186, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (68, 187, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (69, 188, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (70, 189, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (71, 190, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (72, 191, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (73, 192, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (74, 193, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (75, 194, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (76, 195, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (77, 196, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (78, 197, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (79, 198, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (80, 199, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (81, 200, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (82, 201, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (83, 202, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (84, 203, 3, '2019-10-17 14:02:55');
INSERT INTO `sys_role_user` VALUES (85, 204, 3, '2019-10-17 14:03:06');
INSERT INTO `sys_role_user` VALUES (86, 205, 3, '2019-10-17 14:03:06');
INSERT INTO `sys_role_user` VALUES (87, 207, 9, '2019-10-25 08:42:34');
INSERT INTO `sys_role_user` VALUES (88, 208, 3, '2019-10-25 10:56:57');
INSERT INTO `sys_role_user` VALUES (89, 209, 3, '2019-10-25 11:05:55');
INSERT INTO `sys_role_user` VALUES (90, 210, 3, '2019-10-25 11:15:11');
INSERT INTO `sys_role_user` VALUES (91, 205, 10, '2019-10-25 13:11:29');
INSERT INTO `sys_role_user` VALUES (92, 73, 6, '2019-10-25 15:34:03');
INSERT INTO `sys_role_user` VALUES (93, 211, 5, '2019-10-28 14:14:17');
INSERT INTO `sys_role_user` VALUES (94, 211, 6, '2019-10-28 14:14:17');
INSERT INTO `sys_role_user` VALUES (95, 211, 7, '2019-10-28 14:14:17');
INSERT INTO `sys_role_user` VALUES (96, 211, 9, '2019-10-28 14:14:17');
INSERT INTO `sys_role_user` VALUES (97, 211, 10, '2019-10-28 14:14:17');
INSERT INTO `sys_role_user` VALUES (98, 212, 6, '2019-11-12 16:53:42');
INSERT INTO `sys_role_user` VALUES (99, 213, 10, '2019-11-13 14:01:06');
INSERT INTO `sys_role_user` VALUES (100, 214, 6, '2019-11-15 09:28:41');
INSERT INTO `sys_role_user` VALUES (101, 215, 3, '2019-11-21 10:01:06');
INSERT INTO `sys_role_user` VALUES (102, 216, 3, '2019-11-21 10:01:20');
INSERT INTO `sys_role_user` VALUES (103, 218, 3, '2021-03-28 00:48:46');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `mail` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile_phone` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `type` tinyint(4) NULL DEFAULT 0 COMMENT ' 1、超级管理员（所有权限）；2、一般内部用户 ；  0: 外部注册用户 ; 3 外部关联用户(微信等)',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '删除标记0：可用；1、已删除；2、禁用',
  `sex` tinyint(4) NULL DEFAULT NULL COMMENT '0：保密；1.男，2女',
  `head_image` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `org_id` bigint(20) NULL DEFAULT NULL COMMENT '所属组织机构',
  `creator_id` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 219 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (217, 'admin', 'E10ADC3949BA59ABBE56E057F20F883E', '超级管理员', '超级管理员', '', '13400540848', 1, 0, 0, NULL, 0, NULL, NULL, '2021-04-03 14:39:03', NULL);
INSERT INTO `sys_user` VALUES (218, 'zz', 'E10ADC3949BA59ABBE56E057F20F883E', NULL, '詹振', '', '13400540848', 2, 0, 1, NULL, 0, NULL, '2021-03-28 00:29:51', '2021-04-02 23:47:03', NULL);

-- ----------------------------
-- Table structure for uc_user_invite_reward
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_invite_reward`;
CREATE TABLE `uc_user_invite_reward`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `money` double(10, 2) NULL DEFAULT 0.00 COMMENT '奖励金额',
  `referrer_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推荐者id',
  `status` tinyint(4) NULL DEFAULT -1 COMMENT '状态 0：待实名认证；1：已奖励',
  `type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '奖励币种编号',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of uc_user_invite_reward
-- ----------------------------

-- ----------------------------
-- Table structure for uc_user_money
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_money`;
CREATE TABLE `uc_user_money`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '剩余金额',
  `type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of uc_user_money
-- ----------------------------

-- ----------------------------
-- Table structure for uc_user_money_buy
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_money_buy`;
CREATE TABLE `uc_user_money_buy`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `broke_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '手续费',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '充提金额',
  `real_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '实际金额',
  `sell_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卖单号',
  `type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种编号',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of uc_user_money_buy
-- ----------------------------

-- ----------------------------
-- Table structure for uc_user_money_cash
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_money_cash`;
CREATE TABLE `uc_user_money_cash`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `admin_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员ID',
  `admin_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员名称',
  `broke_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '手续费',
  `check_reason` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `check_status` tinyint(4) NULL DEFAULT -1 COMMENT '审核标记 0：待审核；1、通过；2、不通过',
  `check_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '金额',
  `money_address` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接币地址',
  `order_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `real_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '实际到账金额',
  `type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of uc_user_money_cash
-- ----------------------------

-- ----------------------------
-- Table structure for uc_user_money_change
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_money_change`;
CREATE TABLE `uc_user_money_change`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `broke_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '手续费',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `from_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '兑换金额',
  `from_type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种编号',
  `real_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '实际到账金额',
  `to_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '兑换结果金额',
  `to_type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '兑换结果币种编号',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of uc_user_money_change
-- ----------------------------

-- ----------------------------
-- Table structure for uc_user_money_log
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_money_log`;
CREATE TABLE `uc_user_money_log`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `admin_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员ID',
  `admin_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员名称',
  `after_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '交易后金额',
  `before_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '交易前金额',
  `create_time` datetime NULL DEFAULT NULL COMMENT '交易时间',
  `log_type` int(11) NULL DEFAULT 1 COMMENT '交易类型：10充值  11提现  20交易买入  21交易卖出  30购买项目  31撤销购买项目  40发币',
  `log_type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '充提金额',
  `type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of uc_user_money_log
-- ----------------------------

-- ----------------------------
-- Table structure for uc_user_money_project
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_money_project`;
CREATE TABLE `uc_user_money_project`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `broke_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '投资金额',
  `create_time` datetime NULL DEFAULT NULL COMMENT '投资时间',
  `money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '投资金额',
  `order_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `project_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目编号',
  `project_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '项目币种金额',
  `real_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '投资金额',
  `send_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发币编号',
  `send_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '发币金额',
  `send_time` datetime NULL DEFAULT NULL COMMENT '发币时间',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '状态 0：正常；1、已发币；',
  `type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种编号',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of uc_user_money_project
-- ----------------------------

-- ----------------------------
-- Table structure for uc_user_money_recharge
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_money_recharge`;
CREATE TABLE `uc_user_money_recharge`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `admin_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员ID',
  `admin_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员名称',
  `broke_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '手续费',
  `check_reason` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `check_status` tinyint(4) NULL DEFAULT -1 COMMENT '审核标记 -2：已取消 -1：待支付 0：待审核；1：成功；2：失败',
  `check_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '金额',
  `order_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `real_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '实际到账金额',
  `type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of uc_user_money_recharge
-- ----------------------------

-- ----------------------------
-- Table structure for uc_user_money_sell
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_money_sell`;
CREATE TABLE `uc_user_money_sell`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `buy_type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支持币种',
  `create_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  `leave_money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '剩下金额',
  `money` double(18, 8) NULL DEFAULT 0.00000000 COMMENT '卖出总金额',
  `status` tinyint(4) NULL DEFAULT -1 COMMENT '状态 0：正常；1、已取消',
  `type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '币种编号',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of uc_user_money_sell
-- ----------------------------

-- ----------------------------
-- Table structure for uc_user_play_seven
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_play_seven`;
CREATE TABLE `uc_user_play_seven`  (
  `order_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `count_number` int(11) NULL DEFAULT 0 COMMENT '投注数量',
  `count_reward` int(11) NULL DEFAULT 0 COMMENT '中奖数量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '投注时间',
  `mode` int(1) NULL DEFAULT 0 COMMENT '投注类型  0单式 1复式',
  `one_number` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '绿色选号',
  `per_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '单注金额',
  `play_time` double(16, 2) NULL DEFAULT 0.00 COMMENT '期数 20181031073',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '开奖时间',
  `reward_code` int(4) NULL DEFAULT 0 COMMENT '中奖奖项（几等奖）',
  `reward_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '中奖金额',
  `six_number` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '橘色选号',
  `status` int(2) NULL DEFAULT 0 COMMENT '中奖情况（待开奖0 未中奖 -1 中奖1）',
  `sum_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '总金额',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of uc_user_play_seven
-- ----------------------------

-- ----------------------------
-- Table structure for uc_user_play_seven_info
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_play_seven_info`;
CREATE TABLE `uc_user_play_seven_info`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '投注时间',
  `money` double(10, 2) NULL DEFAULT 0.00 COMMENT '金额',
  `one_number` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '绿色选号',
  `order_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `play_time` double(16, 2) NULL DEFAULT 0.00 COMMENT '期数 20181031073',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '开奖时间',
  `reward_code` int(4) NULL DEFAULT 0 COMMENT '中奖奖项（几等奖）',
  `reward_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '中奖金额',
  `six_number` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '橘色选号',
  `status` int(2) NULL DEFAULT 0 COMMENT '中奖情况（待开奖0 未中奖 -1 中奖1）',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of uc_user_play_seven_info
-- ----------------------------

-- ----------------------------
-- Table structure for uc_user_play_three
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_play_three`;
CREATE TABLE `uc_user_play_three`  (
  `order_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `count_number` int(11) NULL DEFAULT 0 COMMENT '投注数量',
  `count_reward` int(11) NULL DEFAULT 0 COMMENT '中奖数量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '投注时间',
  `mode` int(1) NULL DEFAULT 0 COMMENT '投注类型',
  `number` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '选号',
  `per_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '单注金额',
  `play_time` double(16, 2) NULL DEFAULT 0.00 COMMENT '期数 20181031073',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '开奖时间',
  `reward_code` int(4) NULL DEFAULT 0 COMMENT '中奖奖项',
  `reward_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '中奖金额',
  `status` int(2) NULL DEFAULT 0 COMMENT '中奖情况（待开奖0 未中奖 -1 中奖1）',
  `sum_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '总金额',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of uc_user_play_three
-- ----------------------------

-- ----------------------------
-- Table structure for uc_user_play_three_info
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_play_three_info`;
CREATE TABLE `uc_user_play_three_info`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号',
  `create_time` datetime NULL DEFAULT NULL COMMENT '投注时间',
  `mode` int(1) NULL DEFAULT 0 COMMENT '投注类型',
  `money` double(10, 2) NULL DEFAULT 0.00 COMMENT '金额',
  `number` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '选号',
  `order_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `play_time` double(16, 2) NULL DEFAULT 0.00 COMMENT '期数 20181031073',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '开奖时间',
  `reward_code` int(4) NULL DEFAULT 0 COMMENT '中奖奖项',
  `reward_money` double(10, 2) NULL DEFAULT 0.00 COMMENT '中奖金额',
  `status` int(2) NULL DEFAULT 0 COMMENT '中奖情况（待开奖0 未中奖 -1 中奖1）',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of uc_user_play_three_info
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
