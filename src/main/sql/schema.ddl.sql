drop user if exists 'eleven'@'%';
create user 'eleven'@'%' identified by 'eleven';

-- 创建开发环境
drop database if exists eleven_upms;
create database eleven_upms collate utf8mb4_bin;
GRANT all on * TO 'eleven'@'%';
