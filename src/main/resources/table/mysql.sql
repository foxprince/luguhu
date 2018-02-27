drop database luguhu;
create database luguhu default charset utf8mb4 COLLATE utf8mb4_general_ci;
grant all on luguhu.* to 'lghuser'@'localhost' identified by 'pwd4lgh';
grant all on luguhu.* to 'lghuser'@'%' identified by 'pwd4lgh';
create database happytimecn DEFAULT CHARACTER SET gbk COLLATE gbk_chinese_ci;
grant all on happytimecn.* to 'happytimecn_f'@'%' identified by '20080501';
flush privileges;

ALTER DATABASE luguhu CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;
ALTER TABLE wx_user CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
ALTER TABLE wx_user CHANGE nickname column_name VARCHAR(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
/*用户信息表*/
drop table user;
create table user (
	id bigint not null primary key auto_increment,
	email varchar(64),
	login_type tinyint comment '1:手机号码，2：邮箱，3：自定义用户名',
	password varchar(32),
	name varchar(32),
	nickname varchar(32),
	sex tinyint comment '0-未知，1-男，2-女',
	age tinyint,
	phone varchar(11),
	adress varchar(100),
	level tinyint default 0 comment '0-普通用户，1：份额用户,2：股东用户',
	origin varchar(32) comment '用户来源WECHAT,CMCC,QQ,WEB',
	active boolean default true comment '激活状态',
	verified boolean default false comment '审核状态',
	ctime timestamp not null default current_timestamp() on update current_timestamp(),
	`wx_user_id` bigint(20)  NULL,//微信用户id
	`account_id` bigint(20)  NULL,//账户id
	last_login datetime
);
alter table user_info comment '用户信息表';
CREATE TABLE `wx_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ctime` datetime NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `head_img_url` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `open_id` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `sex_id` int(11) DEFAULT NULL,
  `subscribe` bit(1) DEFAULT NULL,
  `subscribe_time` datetime DEFAULT NULL,
  `union_id` varchar(255) DEFAULT NULL,
  `level` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8mb4

create table action_log (
	id bigint not null primary key auto_increment,	
	operator_id bigint not null comment '操作者',
	relate_id bigint null comment '关联id',
	relate_object varchar(50) null comment '关联对象',
	action	varchar(20)	not null comment '动作',
	title	varchar(30)	not null comment '动作名称',
	description	varchar(100)	null comment '操作说明',
	ctime timestamp not null default current_timestamp() on update current_timestamp()
);
alter table action_log modify action varchar(20) not null comment '动作';
create table user_thirdparty (
	user_id bigint not null,
	origin_code varchar(10) not null comment 'CMCC,WECHAT,QQ',
	origin_user_id varchar(32) not null,
	origin_user_name varchar(64),
	orgin_mobile varchar(32),
	origin_icon varchar(128),
	reserve varchar(256),
	primary key (user_id)
);
alter table user_thirdparty comment '第三方登录信息表';
/*用户账户*/
drop table user_account;
create table user_account (
	id bigint not null primary key auto_increment,	
	main_user_id bigint not null,
	balance	int	null comment '账户余额',
	pre_paied int null,/*预付费金额*/
	status	varchar(10) default '正常' comment '账户状态，0：正常',
	ctime timestamp not null default current_timestamp() on update current_timestamp()
);
alter table user_deposit modify entry varchar(10); default '正常';
/*用户充值纪录*/
drop table user_deposit;
create table user_deposit ( 
	id bigint not null primary key auto_increment,	
	user_id bigint not null,
	account_id bigint not null,
	amount int not null comment '充值金额，分',
	balance	int	null comment '充值前账户余额，分',
	entry	varchar(10)	null comment '充值方式,1:微信',
	relate_id	bigint  null,/*根据充值方式不同关联不同对象，1:wx_pay_order*/
	status	varchar(10) default '正常' comment '充值状态，0：成功,1:超时失败，2:接口失败，3:用户取消',
	notes	varchar(100) null,
	ctime timestamp not null default current_timestamp() on update current_timestamp()
);
/*微信支付订单纪录*/
drop table wx_pay_order;
create table wx_pay_order (
	id bigint not null primary key auto_increment,
	user_id bigint not null,
	account_id bigint null,
	open_id varchar(32) null,
	trade_no varchar(32) null,
	body varchar(255) null,
	device_info varchar(50) null,
	fee int null,
	trade_type varchar(10) null,
	client_ip varchar(15) null,
	attach varchar(255) null,
	return_code varchar(16) null,/*SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断*/
	result_code varchar(16) null,
	err_code varchar(32) null,
	prepay_id varchar(64) null,/*微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时*/
	order_time timestamp  null,
	notify_result varchar(16) null,
	notify_err_code varchar(16) null,
	transaction_id varchar(32) null,/*微信支付订单号*/
	time_end varchar(14) null,/*支付完成时间，格式为yyyyMMddHHmmss*/
	notify_time timestamp  null,
	ctime timestamp not null default current_timestamp() on update current_timestamp()
);

alter table user_deposit comment '用户充值纪录表';
/*用户消费纪录*/
create table user_purchase (
	id bigint not null primary key auto_increment,
	user_id bigint not null,
	pack_id bigint null comment '产品销售包id',
	price int not null comment '消费金额，分',
	order_time timestamp not null,
	express_time timestamp not null,
	status	int(1) default 0 comment '消费状态，0：成功',
	ctime timestamp not null default current_timestamp() on update current_timestamp()
);
create table purchase_unit (
	purchase_id bigint not null,
	unit_id bigint not null,
	amount short not null
); 
alter table user_purchase comment '用户消费纪录表';
/*单品信息*/
drop table single_product; 
CREATE TABLE `single_product` (
  `id` bigint NOT NULL primary key AUTO_INCREMENT,
  `title` varchar(20) NOT NULL,	/*单品名称*/
  `producer_id` bigint NOT NULL,	/*生产者id*/
   place varchar(50)	NULL, /*产地*/
  `content` text DEFAULT NULL,	/*说明信息*/
   operator_id bigint default 0  null,/*操作员*/ 
   ctime timestamp not null default current_timestamp() on update current_timestamp()
);
/*产品规格定价*/
drop table sale_unit;
create table sale_unit (
	`id` bigint NOT NULL primary key AUTO_INCREMENT,
	product_id  bigint  not null,
	title varchar(50) not null comment '规格标题，如一级骏枣，个重70-90克猕猴桃',
	unit varchar(6) not null comment '规格，斤、克、公斤、箱、打、个、头',
	price int null comment '单价，可以为空，分',
	amount integer null comment '库存总数',
	saleable boolean not null default true ,/*是否可单独销售*/
	asset_id		int null,
	operator_id bigint default 0  null,/*操作员*/ 
	ctime timestamp not null default current_timestamp() on update current_timestamp()
);

/*产品销售包，直接面对最终用户的销售包*/
drop table sale_pack;
create table sale_pack (
	`id` bigint NOT NULL primary key AUTO_INCREMENT,
	ctime timestamp not null default current_timestamp() on update current_timestamp(),
	pack_type	int(1) not null default 0,/*包类型，1:单品，2:组合包*/
	price_type	int(1) not null default 1,/*价格类型，1:固定价格，2:不定价格*/
	title varchar(50) not null comment '产品标题',
	price int  null comment '单价，可以为空，分',
	amount integer null comment '库存总数',
	min_batch integer null comment '最小起售数量',
	max_batch integer null,/*最大数量*/
	min_price integer null comment '最小金额',
	sale_begin timestamp  null comment '开始销售日期',
	sale_end timestamp null comment '截止销售日期',
	asset_id		int null,
	intro	varchar(255) null,/*简介*/
	content text null,/*详情*/
	operator_id bigint default 0  null/*操作员*/ 
);
/*产品销售包与产品规格的关联关系*/
create table sale_pack_unit (
	pack_id bigint NOT NULL comment '产品销售包id',
	unit_id bigint NOT NULL comment '产品规格id',
);

/*单品文章*/
CREATE TABLE product_article (
 id  bigint   primary key not null AUTO_INCREMENT,
 product_id  bigint  not null,
 title varchar(50)  not null,
 author	varchar(20)	not null,
 logo varchar(100) null,
 content  text  not null,
 admin_id integer(11)  null,/*操作员*/
 ctime timestamp not null default current_timestamp() on update current_timestamp()
);


