create schema azienda;
--DOMINI
--dominio con tutti i possibili ruoli che prevede l’azienda
create domain azienda.role_type as varchar(9) constraint role_domain check (
   value like 'junior' or 
   value like 'middle' or 
   value like 'senior' or 
   value like 'executive' or 
   value like 'temporary'
);
--dominio per SSN
create domain azienda.ssn_type as char(9) constraint ssn_domain check(value similar to '\d+');
--dominio per phone_num
create domain azienda.phone_num_type as char(10) constraint phone_num_domain check(value similar to '\d+');
--END DOMINI
----------------
--SCHEMA FISICO
--Employee
create table azienda.employee(
    ssn azienda.ssn_type,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    phone_num azienda.phone_num_type not null,
    email varchar(50),
    address varchar(50),
    employment_date date not null,
    salary float not null,
    role azienda.role_type not null,
    laboratory_name varchar(30),
--vincoli referenziali
    constraint employee_pk primary key(ssn)
);
--Career_Development
create table azienda.career_development(
    old_role azienda.role_type not null, --serve soprattutto nel caso in cui un impiegato sia stato promosso a ‘executive'
    new_role azienda.role_type not null,
    role_change_date date not null,
    salary_change float not null,
    ssn azienda.ssn_type,
--vincoli referenziali
    constraint cd_emp_fk foreign key(ssn) references azienda.employee(ssn)
    on update cascade on delete cascade --se l’ssn di un impiegato viene aggiornato/eliminato 
    --vengono aggiornate/eliminate anche le tuple in career_development che lo referenziano
);
--Laboratory
create table azienda.laboratory(
    name varchar(30),
    topic varchar(50),
    sresp azienda.ssn_type,
    project char(15),
--vincoli referenziali
    constraint lab_pk primary key(name),
    constraint lsresp_fk foreign key(sresp) references azienda.employee(ssn) on update cascade on delete cascade  --se l’ssn di un impiegato viene aggiornato/eliminato 
    --vengono aggiornate/eliminate tutte tuple in laboratory che lo referenziano
);
--Project
create table azienda.project(
    cup char(15),
    name varchar(30) not null,
    budget float not null,
    remaining_funds float not null,
    start_date date not null,
    end_date date not null,
    sresp azienda.ssn_type,
    sref azienda.ssn_type,
--vincoli referenziali
    constraint project_pk primary key(cup),
    constraint psresp_fk foreign key(sresp) references azienda.employee(ssn) on update cascade on delete cascade, --se l’ssn di un impiegato viene aggiornato/eliminato 
    --vengono aggiornate/eliminate tutte le tuple in project che lo referenziano
    constraint psref_fk foreign key(sref) references azienda.employee(ssn) on update cascade on delete cascade  --se l’ssn di un impiegato viene aggiornato/eliminato 
    --vengono aggiornate/eliminate tutte le tuple in project che lo referenziano
);
--Equipment
create table azienda.equipment(
    id_equipment serial,
    name varchar(30) not null,
    description varchar(200),
    price float not null,
    purchase_date date not null,
    dealer varchar(30) not null,
    laboratory_name varchar(30),
    project_cup char(15),
--vincoli referenziali
    constraint equipment_pk primary key(id_equipment),
    constraint lab_equipment_fk foreign key(laboratory_name) references azienda.laboratory(name) on update cascade on delete set null,  --se il nome di un laboratorio viene aggiornato vengono aggiornate anche le tuple in equipment che lo referenziano.
    --Se invece il nome di un laboratorio viene eliminato, l’attributo laboratory_name, in tutte le tuple che lo referenziano, viene settato a null
    constraint project_equipment_fk foreign key(project_cup) references azienda.project(cup) on update cascade on delete set null  --se la primary key di un progetto viene aggiornata vengono aggiornati i corrispondenti attributi project_cup in equipment che lo referenziano. 
    --Se invece essa viene eliminata, tali attributi vengono settati a null
);
--Temporary_contract
create table azienda.temporary_contract(
    ssn azienda.ssn_type,
    cup char(15),
--vincoli referenziali
    constraint temp_ssn_fk foreign key(ssn) references azienda.employee(ssn) on update cascade on delete cascade,  --se un impiegato viene licenziato, o il suo ssn modificato, 
    --vengono eliminate o modificate anche le tuple in temporary_contract che lo referenziano
    constraint temp_cup_fk foreign key(cup) references azienda.project(cup) on update cascade on delete cascade --se un progetto si conclude, o la sua cup viene modificata, 
    --vengono eliminate o modificate anche le tuple in temporary_contract che lo referenziano
);
--aggiunta del vincolo referenziale mancante
alter table azienda.employee 
add constraint emp_lab_fk foreign key(laboratory_name) references azienda.laboratory(name);  --se il nome di un laboratorio viene aggiornato vengono aggiornate anche le tuple in employee che lo referenziano.
--Se invece esso viene eliminato, l’attributo laboratory_name in employee, in tutte le tuple che lo referenziano, viene settato a null
alter table azienda.laboratory
add constraint prj_lab_fk foreign key(project) references azienda.project(cup) on update cascade on delete set null; --se la primary key di project viene aggiornata vengono aggiornate anche le tuple in laboratory che la referenziano. 
    --Se invece essa viene eliminata, l’attributo project in laboratory, in tutte le tuple che lo referenziano, viene settato a null
-----------------------------------------------------------------------------------------------
--FUNZIONI E TRIGGER

create function azienda.add_career_development() returns trigger as $add_career_development_trigger$
begin
--aggiunge le informazioni relative al cambio di ruolo e stipendio dell’impiegato
if new.role<>old.role then --si assicura che ci sia stato un cambio di ruolo
	insert into azienda.career_development values(old.role, new.role, current_date, new.salary-old.salary, new.ssn);
end if;
return null;
end;
$add_career_development_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger add_career_development_trigger after update of role on azienda.employee
--attivato ad ogni modifica del ruolo nella tabella employee
for each row
execute function azienda.add_career_development();
---------------------------------