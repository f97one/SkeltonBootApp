-- ユーザー
create table APP_USER (
  user_name NVARCHAR(32) not null
  , password NVARCHAR(128) not null
  , account_enabled BIT default 1
  , account_expiration_date DATETIME default '2199-12-31 23:59:59.999'
  , password_expiration_date DATETIME default '2199-12-31 23:59:59.999'
  , lock_reason_code INT default 0
  , display_name NVARCHAR(128)
  , mail_address NVARCHAR(128)
  , former_password NVARCHAR(128) default '' not null
  , authority NVARCHAR(64) default '' not null
) ;

create unique index APP_USER_PKI
  on APP_USER(user_name);

alter table APP_USER
  add constraint APP_USER_PKC primary key (user_name);

-- 組み込み管理者ユーザーを追加

INSERT INTO APP_USER (
    user_name,
    password,
    account_enabled,
    account_expiration_date,
    password_expiration_date,
    lock_reason_code,
    display_name,
    former_password,
    mail_address,
    authority
) VALUES (
    'root',
    '',
    1,
    '2199-12-31 23:59:59.999',
    '2199-12-31 23:59:59.999',
    0,
    'ビルトイン管理者',
    '',
    'root@example.com',
    'ADMIN'
);
