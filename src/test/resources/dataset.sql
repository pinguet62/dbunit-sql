insert into profile (id, key) values ('1st', 'first');
insert into profile (id, key) values ('2nd', 'second');

insert into user (id, name, email, profile_id) values (1, 'AAA', 'first@domain.org', '1st');
insert into user (id, name, email, profile_id) values (2, 'BBB', 'second@domain.org', '2nd');