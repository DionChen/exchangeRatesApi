create table Currency (
                      code NCHAR(3) not null,
                      ch_name varchar(100),
                      rate decimal(10, 4),
                      description varchar(100),
                      active boolean,
                      created_date date,
                      last_modified_date date,
                      PRIMARY KEY (code)
);