drop database luguhu;
create database luguhu default charset utf8 COLLATE utf8_general_ci;
grant all on luguhu.* to 'lghuser'@'localhost' identified by 'pwd4lgh';
grant all on luguhu.* to 'lghuser'@'%' identified by 'pwd4lgh';

/*用户信息表*/
create table user_info (
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
	origin varchar(32) comment 'WECHAT,CMCC,QQ,WEB',
	active boolean default true comment '激活状态',
	verified boolean default false comment '审核状态',
	ctime timestamp not null,
	last_login_time datetime,
	primary key (id)
);
alter table user_info comment '用户信息表';
create table action_log (
	id bigint not null primary key auto_increment,	
	operator_id bigint not null comment '操作者',
	relate_id bigint null comment '关联id',
	relate_object varchar(50) null comment '关联对象',
	action	varchar(20)	not null comment '动作',
	title	varchar(30)	not null comment '动作名称',
	description	varchar(100)	null comment '操作说明',
	ctime timestamp not null
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

/*用户充值纪录*/
create table user_deposit (
	id bigint not null primary key auto_increment,	
	user_id bigint not null,
	amount float not null comment '充值金额',
	balance	float	null comment '充值前账户余额',
	entry	int(3)	null comment '充值方式',
	status	int(1) default 0 comment '充值状态，0：成功',
	ctime timestamp not null
);
alter table user_deposit comment '用户充值纪录表';
/*用户消费纪录*/
create table user_purchase (
	id bigint not null primary key auto_increment,
	user_id bigint not null,
	order_id bigint not null comment '订单id',
	amount float not null comment '消费金额',
	status	int(1) default 0 comment '消费状态，0：成功',
	ctime timestamp not null
);
alter table user_purchase comment '用户消费纪录表';
/*单品信息*/
drop table single_product; 
CREATE TABLE `single_product` (
  `id` bigint NOT NULL primary key AUTO_INCREMENT,
  `title` varchar(20) NOT NULL,	/*单品名称*/
  `producer_id` bigint NOT NULL,	/*生产者id*/
   place varchar(50)	NULL, /*产地*/
  `description` varchar(500) DEFAULT NULL,	/*说明信息*/
   operator_id bigint default 0  null,/*操作员*/ 
   ctime timestamp not null
);
/*产品规格定价*/
create table sale_unit (
	`id` bigint NOT NULL primary key AUTO_INCREMENT,
	product_id  bigint  not null,
	title varchar(50) not null comment '规格标题，如一级骏枣，个重70-90克猕猴桃',
	unit varchar(6) not null comment '规格，斤、克、公斤、箱、打、个、头',
	price float null comment '单价，可以为空',
	amount integer null comment '库存总数',
	operator_id bigint default 0  null,/*操作员*/ 
	ctime timestamp not null
);

/*产品销售包，直接面对最终用户的销售包*/
create table sale_pack (
	`id` bigint NOT NULL primary key AUTO_INCREMENT,
	title varchar(50) not null comment '产品标题',
	price float not null comment '价格',
	amount integer null comment '库存总数',
	sale_begin timestamp not null comment '开始销售日期',
	sale_end timestamp null comment '截止销售日期',
	description text null,
	operator_id bigint default 0  null,/*操作员*/ 
	ctime timestamp not null
);
/*产品销售包与产品规格的关联关系*/
create table sale_pack_unit (
	`id` bigint NOT NULL primary key AUTO_INCREMENT,
	pack_id bigint NOT NULL comment '产品销售包id',
	unit_id bigint NOT NULL comment '产品规格id',
	operator_id bigint default 0  null,/*操作员*/ 
	ctime timestamp not null
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
 ctime timestamp not null
);


