insert into main.role_table(role)
values
('ANONYMOUS'),
('USER'),
('ADMIN'),
('MODERATOR');


-- login admin, password admin
insert into main.account_table(name,last_name, email ,password, phone_number, account_registered, account_confirmed)
select 'admin', 'admin', 'testuser@gmail.com', '$2a$10$1i5mo49OJoBpEr9DTnYVvOmM0gqo1hg63syUP8lq6OEoGVjEYAYzq', '+3801234567890', true, true
from main.role_table r where r.role='ADMIN';

insert into main.role_account_table(account_id,role_id)
select a.id, r.id
from main.role_table r
inner join main.account_table a on a.email = 'testuser@gmail.com'
