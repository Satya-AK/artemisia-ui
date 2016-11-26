# --- !Ups

create table "user" ("id" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"email" VARCHAR(100) NOT NULL,"nickname" VARCHAR(40) NOT NULL,"first_name" VARCHAR(40) NOT NULL,"last_name" VARCHAR(40) NOT NULL,"password" VARCHAR(100) NOT NULL,"activated" BOOLEAN NOT NULL,"role_id" INTEGER NOT NULL);
create unique index "email_unique_key" on "user" ("email");
create table "user_role" ("id" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"name" VARCHAR(40) NOT NULL);
create table "user_password" ("id" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"password" VARCHAR(100) NOT NULL,"hasher" VARCHAR(100) NOT NULL,"salt" VARCHAR(100) NOT NULL);


# --- !Downs

drop table "user";
drop table "user_role";
drop table "user_password";