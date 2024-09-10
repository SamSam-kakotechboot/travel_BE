USE ktb_samsam;

alter table review add delete_yn varchar(1) default 'N' not null comment '삭제 여부' after rating;