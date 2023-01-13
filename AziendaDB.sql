create schema azienda;

create domain azienda.role_type as varchar(9) constraint role_domain check (
   value like 'junior' or 
   value like 'middle' or 
   value like 'senior' or 
   value like 'executive' or 
   value like 'temporary'
);

create domain azienda.ssn_type as char(15) constraint ssn_domain check(value similar to '\d+');

create table azienda.employee(
    ssn azienda.ssn_type,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    phone_num varchar(10) not null,
    email varchar(50),
    address varchar(50),
    employment_date date not null,
    salary float not null,
    role azienda.role_type not null,
    laboratory_name varchar(30),
    constraint employee_pk primary key(ssn)
);

create table azienda.career_development(
    old_role azienda.role_type not null,
    new_role azienda.role_type not null,
    role_change_date date not null,
    salary_change float not null,
    ssn azienda.ssn_type,
    constraint cd_emp_fk foreign key(ssn) references azienda.employee(ssn)
    on update cascade on delete cascade 
);

create table azienda.laboratory(
    name varchar(30),
    topic varchar(50),
    sresp azienda.ssn_type,
    project char(15),
    constraint lab_pk primary key(name),
    constraint prj_lab_fk foreign key(project) references azienda.project(cup) on update cascade on delete set null,
    constraint lsresp_fk foreign key(sresp) references azienda.employee(ssn) on update cascade on delete cascade
);

create table azienda.project(
    cup char(15),
    name varchar(30) not null,
    budget float not null,
    remaining_funds float not null,
    start_date date not null,
    end_date date not null,
    sresp azienda.ssn_type,
    sref azienda.ssn_type,
    constraint project_pk primary key(cup),
    constraint psresp_fk foreign key(sresp) references azienda.employee(ssn) on update cascade on delete cascade,
    constraint psref_fk foreign key(sref) references azienda.employee(ssn) on update cascade on delete cascade
);

create table azienda.equipment(
    id_equipment serial,
    name varchar(30) not null,
    description varchar(200),
    price float not null,
    purchase_date date not null,
    dealer varchar(30) not null,
    laboratory_name varchar(30),
    project_cup char(15),
    constraint equipment_pk primary key(id_equipment),
    constraint lab_equipment_fk foreign key(laboratory_name) references azienda.laboratory(name) on update cascade on delete set null,
    constraint project_equipment_fk foreign key(project_cup) references azienda.project(cup) on update cascade on delete set null
);

create table azienda.temporary_contract(
    ssn azienda.ssn_type,
    cup char(15),
    constraint temp_ssn_fk foreign key(ssn) references azienda.employee(ssn) on update cascade on delete cascade,
    constraint temp_cup_fk foreign key(cup) references azienda.project(cup) on update cascade on delete cascade
);

alter table azienda.employee 
add constraint emp_lab_fk foreign key(laboratory_name) references azienda.laboratory(name);

insert into azienda.employee values('123456789123451','io','sempre io','3465013137','manuelmignogna@alice.it','casa mia','2012-12-12',1,'junior',null);


-----------------------------------------------------------------------------------------------
create function check_employment_date() returns trigger as $check_employment_date_trigger$
begin
if new.role = 'junior' or new.role = 'middle' or new.role = 'senior' then
    if current_date - new.employment_date<3*365 and new.role<>'junior' then
        update azienda.employee
        set role='junior'
        where ssn=new.ssn;
     elsif current_date-new.employment_date>=3*365 and current_date-new.employment_date<7*365 and new.role<>"middle" then
        update azienda.employee
        set role='middle'
        where ssn=new.ssn;
    elsif current_date-new.employment_date>=7*365 and new.role<>"senior" then
        update azienda.employee
        set role='senior'
        where ssn=new.ssn;
    end if;
end if;
end;
$check_employment_date_trigger$ LANGUAGE plpgsql;

create trigger check_employment_date_trigger after insert or update of role on azienda.employee
for each row
execute function check_employment_date();
---------------------------------

create function add_career_development() returns trigger as $add_career_development_trigger$
begin
if new.role<>old.role then
	insert into azienda.career_development values(old.role, new.role, current_date, new.salary-old.salary, new.ssn);
end if;
end;

$add_career_development_trigger$ LANGUAGE plpgsql;

create trigger add_career_development_trigger after update of role on azienda.employee
for each row
execute function add_career_development();
---------------------------------


create function check_remaining_funds() returns trigger as $check_remaining_funds_trigger$
begin
update azienda.project
set remaining_funds=new.budget
where cup=new.cup;
end;
$check_remaining_funds_trigger$ LANGUAGE plpgsql;

create trigger check_remaining_funds_trigger after insert on azienda.project
for each row
execute function check_remaining_funds();
---------------------------------

create function update_remaining_funds_employee_insert() returns trigger as $update_remaining_funds_employee_insert_trigger$
declare
role_emp azienda.employee.role%type;
emp_salary azienda.employee.salary%type;
end_date_prj azienda.project.end_date%type;
begin
select role, salary into role_emp, emp_salary from azienda.employee where ssn=new.ssn;
if role_emp='temporary' then
	select end_date into end_date_prj from azienda.project where cup=new.cup;
	update azienda.project
	set remaining_funds=remaining_funds-(emp_salary*(end_date-current_date/30));

end if;
end;
$update_remaining_funds_employee_insert_trigger$ LANGUAGE plpgsql;

create trigger update_remaining_funds_employee_insert_trigger after insert on azienda.temporary_contract
for each row
execute function update_remaining_funds_employee_insert();
---------------------------------
create function update_remaining_funds_equipment_delete() returns trigger as $update_remaining_funds_equipment_delete_trigger$
declare
role_emp azienda.employee.role%type;
emp_salary azienda.employee.salary%type;
end_date_prj azienda.project.end_date%type;
begin
select role, salary into role_emp, emp_salary from azienda.employee where ssn=new.ssn;
if role_emp='temporary' then
select end_date into end_date_prj from azienda.project where cup=new.cup;
update azienda.project
set remaining_funds=remaining_funds+(emp_salary*(end_date-current_date/30));

end if;
end;
$update_remaining_funds_equipment_delete_trigger$ LANGUAGE plpgsql;

create trigger update_remaining_funds_equipment_delete_trigger before delete on azienda.equipment
for each row
execute function update_remaining_funds_equipment_delete();
---------------------------------
create function update_remaining_funds_equipment() returns trigger as $update_remaining_funds_equipment_trigger$
begin
update azienda.project
set remaining_funds=remaining_funds-new.price
where cup=new.project_cup;
end;
$update_remaining_funds_equipment_trigger$ LANGUAGE plpgsql;

create trigger update_remaining_funds_equipment_trigger after insert on azienda.temporary_contract
for each row
execute function update_remaining_funds_equipment();
---------------------------------

create function check_end_date() returns trigger as $check_end_date_trigger$
begin
if new.start_date>=new.end_date then
	delete from azienda.PROJECT
	WHERE cup=new.cup;
end if;

end;
$check_end_date_trigger$ LANGUAGE plpgsql;

create trigger check_end_date_trigger after insert on azienda.project
for each row
execute function check_end_date();
---------------------------------
create function check_salary_temporary() returns trigger as $check_salary_temporary_trigger$
declare 
sum_salary azienda.employee.salary%type;
cup_new_emp azienda.project.cup%type;
project_budget azienda.project.budget%type;
remaining_months integer;
end_date_prj azienda.project.end_date%type;

begin

select cup into cup_new_emp
from azienda.temporary_contract
where ssn=new.ssn;

select sum(emp.salary) into sum_salary
from azienda.employee emp join azienda.temporary_contract temp on emp.ssn=temp.ssn
where temp.cup=cup_new_emp and emp.role like 'temporary';

select budget, end_date into project_budget, end_date_prj
from azienda.project 
where cup=cup_new_emp;

remaining_months:= end_date_prj-current_date;

if sum_salary*remaining_months>project_budget/2 then
	delete from employee
	where ssn=new.ssn;

end if;
end;
$check_salary_temporary_trigger$ LANGUAGE plpgsql;

create trigger check_salary_temporary_trigger after insert on azienda.employee
for each row
execute function check_salary_temporary();

---------------------------------

create function check_price_equipment() returns trigger as $check_price_equipment_trigger$
declare 

sum_price azienda.equipment.price%type;
project_budget azienda.project.budget%type;
begin
select budget into project_budget
from azienda.project 
where cup=new.cup;

select sum(price) into sum_price
from azienda.equipment
where project_cup=new.cup;

if sum_price>project_budget/2 then
	delete from azienda.equipment
	where id_equipment=new.id_equipment;

end if;
end;
$check_price_equipment_trigger$ LANGUAGE plpgsql;

create trigger check_price_equipment_trigger after insert on azienda.equipment
for each row
execute function check_price_equipment();
---------------------------------
create function check_budget() returns trigger as $check_budget_trigger$
declare
sum_salary azienda.employee.salary%type;
sum_price azienda.equipment.price%type;
remaining_months int;

begin
select sum(price) into sum_price
from azienda.equipment
where project_cup=new.cup;
if new.budget/2<sum_price then
	update azienda.project
	set budget=old.budget
	where cup=new.cup;

end if;

select sum(emp.salary) into sum_salary
from azienda.employee emp join azienda.temporary_contract temp on emp.ssn=tmp
where temp.cup=cup_new_emp and emp.role like 'temporary';

remaining_months=new.end_date-current_date;
if sum_salary*remaining_months>new.budget/2 then
	update azienda.project
	set budget=old.budget
	where cup=new.cup;

end if;
end;
$check_budget_trigger$ LANGUAGE plpgsql; 

create trigger check_budget_trigger after update of budget on azienda.project
for each row
execute function check_budget();
---------------------------------
create function check_scientific_reference() returns trigger as $check_scientific_reference_trigger$
declare
role_emp azienda.employee.role%type;
begin
select role into role_emp from azienda.employee where ssn=new.sref;
if role_emp<>'senior' then
	update project
	set sref=old.sref;
end if;
end;
$check_scientific_reference_trigger$ LANGUAGE plpgsql;

create trigger check_scientific_reference_trigger after insert or update of sref on azienda.project
for each row
execute function check_scientific_reference();

---------------------------------

create function check_scientific_responsable_lab() returns trigger as $check_scientific_respnsable_lab_trigger$
declare
role_emp azienda.employee.role%type;
begin
select role into role_emp from azienda.employee where ssn=new.sresp;
if role_emp<>'senior' then
	update laboratory
	set sresp=old.sresp;

end if;
end;
$check_scientific_respnsable_lab_trigger$ LANGUAGE plpgsql;

create trigger check_scientific_respnsable_lab_trigger after insert or update of sresp on azienda.laboratory
for each row
execute function check_scientific_responsable_lab();
-----------------------------------------

create function check_scientific_responsable_prj() returns trigger as $check_scientific_responsable_prj_trigger$
declare
role_emp azienda.employee.role%type;
begin
select role into role_emp from azienda.employee where ssn=new.sref;
if role_emp<>'executive' then
	update project
	set sref=old.sref;

end if;
end;
$check_scientific_responsable_prj_trigger$ LANGUAGE plpgsql;

create trigger check_scientific_responsable_prj_trigger after insert or update of sref on azienda.project
for each row
execute function check_scientific_responsable_prj();
------------------------------------------

create function check_max_lab_insert() returns trigger as $check_max_lab_insert_trigger$
declare 
   count_project integer;
begin
select count(project) into count_project from azienda.laboratory where project=new.project;
if count_project>3 then
   delete from azienda.laboratory where name=new.name; 
end if;
end;
$check_max_lab_insert_trigger$ LANGUAGE plpgsql;

create trigger check_max_lab_insert_trigger after insert or update of project on azienda.laboratory
for each row
execute function check_max_lab_insert();
-------------------------------------------
create function check_max_lab_update() returns trigger as $check_max_lab_update_trigger$
declare 
   count_project integer;
begin
select count(project) into count_project from azienda.laboratory where project=new.project;
if count_project>3 then
   update azienda.laboratory set project=old.project where name=new.name; 
end if;
end;
$check_max_lab_update_trigger$ LANGUAGE plpgsql;

create trigger check_max_lab_update_trigger after insert or update of project on azienda.laboratory
for each row
execute function check_max_lab_update();  
------------------------------
create function check_expired_project() returns trigger as $check_expired_project_trigger$
declare
   end_date_prj azienda.project.end_date%type;
begin
select end_date into end_date_prj from azienda.project where cup=new.cup;
if end_date_prj<current_date then
  delete from azienda.temporary_contract where ssn=new.ssn;
  delete from azienda.employee where ssn=new.ssn; 
end if;
end;
$check_expired_project_trigger$ LANGUAGE plpgsql;

create trigger check_expired_project_trigger after insert on azienda.temporary_contract
for each row
execute function check_expired_project();
----------------------

create function check_resp() returns trigger as $check_resp_trigger$
declare
  emp_exists integer;
begin
select count(ssn) into emp_exists from azienda.project where sresp=new.ssn;
if emp_exists > 1 then
    if new.role <>'executive' then
    update azienda.project set sresp=old.sresp where sresp=new.ssn;
	end if;
end if;

select count(ssn) into emp_exists from azienda.project where sref=new.ssn;
if emp_exists > 1 then
    if new.role <>'senior' then
    update azienda.project set sref=old.sref where sref=new.ssn;
	end if;
end if;

select count(ssn) into emp_exists from azienda.laboratory where sref=new.ssn;
if emp_exists > 1 then
    if new.role <>'senior' then
    update azienda.laboratory set sref=old.sref where sref=new.ssn;
	end if;
end if;
end;
$check_resp_trigger$ LANGUAGE plpgsql;

create trigger check_resp_trigger after update of role on azienda.employee
for each row
execute function check_resp();

----------------------------

create or replace procedure update_role_check_date() as
$$
declare
   expired_project cursor for select cup from azienda.project where end_date<current_date;
   ssn_expired azienda.employee.ssn%type;
begin
for cup_expired in expired_project loop
select ssn into ssn_expired from azienda.temporary_contract where cup=cup_expired;
delete from azienda.employee where ssn=ssn_expired;
delete from azienda.temporary_contract where ssn=ssn_expired;
end loop;
end; 
$$ LANGUAGE plpgsql;

------------------------------

create procedure check_employment_date_procedure()as $$
begin
if new.role = 'junior' or new.role = 'middle' or new.role = 'senior' then
    if current_date-new.employment_date<3*365 and new.role<>'junior' then
        update azienda.employee
        set role='junior'
        where ssn=new.ssn;
     elsif current_date-new.employment_date>=3*365 and current_date-new.employment_date<7*365 and new.role<>"middle" then
        update azienda.employee
        set role='middle'
        where ssn=new.ssn;
    elsif current_date-new.employment_date>=7*365 and new.role<>"senior" then
        update azienda.employee
        set role='senior'
        where ssn=new.ssn;
	end if;
end if;
end;
$$ LANGUAGE plpgsql;

