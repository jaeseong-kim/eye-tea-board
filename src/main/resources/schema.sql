/*
create table user (email varchar(255) not null, address varchar(255) not null, auth_dt datetime(6), auth_key varchar(255) not null, auth_yn bit default false not null, birth varchar(255) not null, detail_address varchar(255) not null, status bit default true not null, login_type varchar(255), name varchar(255) not null, password varchar(255) not null, reg_dt datetime(6) not null, role varchar(255) not null, primary key (email)) engine=InnoDB;
create table post (post_id bigint not null auto_increment, edit_dt datetime(6), reg_dt datetime(6), category varchar(255) not null, content TEXT not null, like_num integer default 0 not null, title varchar(255) not null, email varchar(255), primary key (post_id)) engine=InnoDB;
create table comment (comment_id bigint not null auto_increment, edit_dt datetime(6), reg_dt datetime(6), comment TEXT not null, like_num integer default 0 not null, post_id bigint, email varchar(255), primary key (comment_id)) engine=InnoDB;
create table post_like (post_like_id bigint not null auto_increment, edit_dt datetime(6), reg_dt datetime(6), email varchar(255), post_id bigint, primary key (post_like_id)) engine=InnoDB;
create table comment_like (comment_like_id bigint not null auto_increment, edit_dt datetime(6), reg_dt datetime(6), email varchar(255), comment_id bigint, primary key (comment_like_id)) engine=InnoDB;

alter table post add constraint foreign key (email) references user (email);
alter table comment add constraint foreign key (email) references user (email);
alter table comment add constraint foreign key (post_id) references post (post_id);
alter table post_like add constraint foreign key (email) references user (email);
alter table post_like add constraint foreign key (post_id) references post (post_id);
alter table comment_like add constraint foreign key (email) references user (email);
alter table comment_like add constraint foreign key (comment_id) references comment (comment_id);
*/
