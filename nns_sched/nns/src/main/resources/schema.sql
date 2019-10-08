
drop table IF EXISTS nns_sched.message;

create table nns_sched.message
(
   id BIGINT not null AUTO_INCREMENT,
   message_type varchar(100) null,
   message BLOB null,
   load_timestamp timestamp,
   file_name varchar(500),
   primary key(id)
);

drop table IF EXISTS nns_sched.spam_stat;

create table nns_sched.spam_stat
(
   id BIGINT not null AUTO_INCREMENT,
   word varchar(200),
   word_counts integer,
   load_timestamp timestamp,
   file_name varchar(500),
   primary key(id)
);

drop table IF EXISTS nns_sched.spam_trend;

create table nns_sched.spam_trend
(
   id BIGINT not null AUTO_INCREMENT,
   ham_counts integer not null,
   spam_counts integer not null,
   load_timestamp timestamp,
   file_name varchar(500),
   primary key(id)
);

