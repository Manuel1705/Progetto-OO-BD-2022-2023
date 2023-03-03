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
--Laboratory
create table azienda.laboratory(
    name varchar(30),
    topic varchar(50),
    sresp azienda.ssn_type,
    project char(15),
--vincoli referenziali
    constraint lab_pk primary key(name),
    constraint prj_lab_fk foreign key(project) references azienda.project(cup) on update cascade on delete set null, --se la primary key di project viene aggiornata vengono aggiornate anche le tuple in laboratory che la referenziano. 
    --Se invece essa viene eliminata, l’attributo project in laboratory, in tutte le tuple che lo referenziano, viene settato a null
    constraint lsresp_fk foreign key(sresp) references azienda.employee(ssn) on update cascade on delete cascade  --se l’ssn di un impiegato viene aggiornato/eliminato 
    --vengono aggiornate/eliminate tutte tuple in laboratory che lo referenziano
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


-----------------------------------------------------------------------------------------------
--FUNZIONI E TRIGGER
create function azienda.check_employment_date() returns trigger as $check_employment_date_trigger$
begin
if new.role = 'junior' or new.role = 'middle' or new.role = 'senior' then --si assicura che l’impiegato partecipa agli scatti di carriera 
--(gli impiegati con contratto determinato non sono contemplati)
    --gli impiegati che lavorano per l’azienda da meno di 3 anni sono ‘junior’
    if current_date - new.employment_date<3*365 and new.role not like 'junior' then
        update azienda.employee
        set role='junior'
        where ssn=new.ssn;
     --se l’impiegato lavora per l’azienda da più di 3 anni ma da meno di 7, diventa ‘middle’
     elsif current_date-new.employment_date>=3*365 and current_date-new.employment_date<7*365 and new.role not like 'middle' then
        update azienda.employee
        set role='middle'
        where ssn=new.ssn;
    --se l’impiegato lavora per l’azienda da più di 7 anni diventa ‘senior’
    elsif current_date-new.employment_date>=7*365 and new.role not like 'senior' then
        update azienda.employee
        set role='senior'
        where ssn=new.ssn;
    end if;
end if;
return null;
end;
$check_employment_date_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger check_employment_date_trigger after insert or update of role on azienda.employee
--attivato ad ogni inserimento o modifica del ruolo della tabella employee
for each row
execute function azienda.check_employment_date();

---------------------------------

create function azienda.add_career_development() returns trigger as $add_career_development_trigger$
begin
--aggiunge le informazioni relative al cambio di ruolo e stipendio dell’impiegato
if new.role<>old.role then --si assicura che ci sia stato un cambio di ruolo
	insert into azienda.career_development values(old.role, new.role, current_date, new.salary-old.salary, new.ssn);
end if;
end;
$add_career_development_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger add_career_development_trigger after update of role on azienda.employee
--attivato ad ogni modifica del ruolo nella tabella employee
for each row
execute function azienda.add_career_development();
---------------------------------

create function azienda.check_remaining_funds() returns trigger as $check_remaining_funds_trigger$
begin
update azienda.project
set remaining_funds=new.budget --il valore di remaining_funds viene inizializzato con il valore del budget
where cup=new.cup;
end;
$check_remaining_funds_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger check_remaining_funds_trigger after insert on azienda.project
--viene attivato ad ogni inserimento nella tabella project
for each row
execute function azienda.check_remaining_funds();
---------------------------------

create function azienda.update_remaining_funds_employee_insert() returns trigger as $update_remaining_funds_employee_insert_trigger$
declare
role_emp azienda.employee.role%type;
emp_salary azienda.employee.salary%type;
end_date_prj azienda.project.end_date%type;

begin
select role, salary into role_emp, emp_salary from azienda.employee where ssn=new.ssn;
if role_emp='temporary' then
	select end_date into end_date_prj from azienda.project where cup=new.cup;
	update azienda.project
	set remaining_funds=remaining_funds-(emp_salary*(end_date-current_date/30)); --manteniamo consistente il valore di remaining_funds 
	--in seguito all’assunzione di un impiegato con contratto temporaneo
end if;
end;
$update_remaining_funds_employee_insert_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger update_remaining_funds_employee_insert_trigger after insert on azienda.temporary_contract
--viene attivato ad ogni inserimento nella tabella temporary_contract
for each row
execute function azienda.update_remaining_funds_employee_insert();
---------------------------------

create function azienda.update_remaining_funds_employee_delete() returns trigger as $update_remaining_funds_employee_delete_trigger$
declare
role_emp azienda.employee.role%type;
emp_salary azienda.employee.salary%type;
end_date_prj azienda.project.end_date%type;

begin
select role, salary into role_emp, emp_salary from azienda.employee where ssn=new.ssn;
if role_emp='temporary' then
	select end_date into end_date_prj from azienda.project where cup=new.cup;
	update azienda.project
	set remaining_funds=remaining_funds+(emp_salary*(end_date-current_date/30)); --manteniamo consistente il valore di remaining_funds 
	--a seguito del licenziamento di un impiegato con contratto temporaneo
end if;
end;
$update_remaining_funds_employee_delete_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger update_remaining_funds_employee_delete_trigger before delete on azienda.temporary_contract
--viene attivato prima di ogni eliminazione nella tabella temporary_contract
for each row
execute function azienda.update_remaining_funds_employee_delete();
---------------------------------

create function azienda.update_remaining_funds_equipment() returns trigger as $update_remaining_funds_equipment_trigger$
begin
update azienda.project
set remaining_funds=remaining_funds-new.price --manteniamo consistente il valore di remaining_funds a seguito dell’acquisto di nuovo equipaggiamento
where cup=new.project_cup;
end;
$update_remaining_funds_equipment_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger update_remaining_funds_equipment_trigger after insert on azienda.temporary_contract
--viene attivato ad ogni inserimento nella tabella equipment
for each row
execute function azienda.update_remaining_funds_equipment();
---------------------------------

create function azienda.check_end_date() returns trigger as $check_end_date_trigger$
begin
if new.start_date>=new.end_date then --controlla che la data iniziale sia successiva alla data finale
	--se ciò accade la tupla viene eliminata
	delete from azienda.PROJECT
	WHERE cup=new.cup;
end if;
end;
$check_end_date_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger check_end_date_trigger after insert on azienda.project
--viene attivato ad ogni inserimento nella tabella project
for each row
execute function azienda.check_end_date();
---------------------------------

create function azienda.check_salary_temporary() returns trigger as $check_salary_temporary_trigger$
declare 
sum_salary azienda.employee.salary%type;
cup_new_emp azienda.project.cup%type;
project_budget azienda.project.budget%type;
remaining_months integer;
end_date_prj azienda.project.end_date%type;

begin
if new.role = 'temporary' then
--prendiamo tutte le cup dei progetti a cui lavorano dipendenti temporanei
select cup into cup_new_emp
from azienda.temporary_contract
where ssn=new.ssn;

--prendiamo la somma del salario dei dipendenti temporanei
select sum(emp.salary) into sum_salary
from azienda.employee emp join azienda.temporary_contract temp on emp.ssn=temp.ssn
where temp.cup=cup_new_emp and emp.role like 'temporary';

--selezioniamo il budget e la end_date di tali progetti
select budget, end_date into project_budget, end_date_prj
from azienda.project 
where cup=cup_new_emp;

--calcoliamo i mesi rimanenti perché avendo già dei dipendenti temporanei che lavorano al progetto,
--bisogna garantire loro lo stipendio fino al termine del progetto 
--(il calcolo funziona sempre perché l’attributo end_date in project è vincolato a non essere null)
remaining_months:= end_date_prj-current_date;

--se la somma dei salari dei dipendenti temporanei supera il 50% del budget del progetto, 
--non viene consentita l’assunzione di ulteriori dipendenti
if sum_salary*remaining_months>project_budget/2 then
	delete from employee
	where ssn=new.ssn;

end if;
end if;
return null;
end;
$check_salary_temporary_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger check_salary_temporary_trigger after insert on azienda.employee 
--viene attivato a ogni inserimento nella tabella employee
for each row
execute function azienda.check_salary_temporary();
---------------------------------

create function azienda.check_price_equipment() returns trigger as $check_price_equipment_trigger$
declare 
sum_price azienda.equipment.price%type;
project_budget azienda.project.budget%type;

begin
--memorizziamo il budget del progetto in project_budget
select budget into project_budget
from azienda.project 
where cup=new.cup;

--prendiamo la somma dei prezzi di ogni equipaggiamento acquistato per tale progetto
select sum(price) into sum_price
from azienda.equipment
where project_cup=new.cup;

--se la somma dei prezzi dell’attrezzatura supera il 50% del budget del progetto con cui sono stati acquistati, 
--non viene consentito l’acquisto di ulteriore attrezzatura 
if sum_price>project_budget/2 then
	delete from azienda.equipment
	where id_equipment=new.id_equipment;
end if;
end;
$check_price_equipment_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger check_price_equipment_trigger after insert on azienda.equipment
--viene attivato a ogni inserimento della tabella equipment
for each row
execute function azienda.check_price_equipment();
---------------------------------

create function azienda.check_budget() returns trigger as $check_budget_trigger$
declare
sum_salary azienda.employee.salary%type;
sum_price azienda.equipment.price%type;
remaining_months int;

begin
--prendiamo la somma dei prezzi dell’attrezzatura acquistata per un progetto 
select sum(price) into sum_price
from azienda.equipment
where project_cup=new.cup;

--se più del 50% del budget del progetto è stato già speso per l’acquisto di attrezzatura per lo stesso, 
--non viene consentita la modifica del budget
if new.budget/2<sum_price then
	update azienda.project
	set budget=old.budget
	where cup=new.cup;
end if;

--prendiamo la somma dei salari degli impiegati temporanei che lavorano a tale progetto
select sum(emp.salary) into sum_salary
from azienda.employee emp join azienda.temporary_contract temp on emp.ssn=tmp
where temp.cup=cup_new_emp and emp.role like 'temporary';

--teniamo conto anche dei mesi non ancora retribuiti 
--ma che devono essere garantiti ai dipendenti temporanei che già lavorano a questo progetto
remaining_months=new.end_date-current_date;

--se più del 50% del budget è stato già speso per l’assunzione di dipendenti temporanei, 
--non viene consentita la modifica del budget
if sum_salary*remaining_months>new.budget/2 then
	update azienda.project
	set budget=old.budget
	where cup=new.cup;
end if;
end;
$check_budget_trigger$ LANGUAGE plpgsql; 

--trigger corrispondente
create trigger check_budget_trigger after update of budget on azienda.project
--viene attivato a ogni modifica di budget nella tabella project
for each row
execute function azienda.check_budget();
---------------------------------

create function azienda.check_scientific_reference() returns trigger as $check_scientific_reference_trigger$
declare
role_emp azienda.employee.role%type;

begin
--selezioniamo il ruolo dell’impiegato che vogliamo che sia il nuovo referente scientifico del progetto
select role into role_emp from azienda.employee where ssn=new.sref;
--se l’impiegato non ha ruolo ‘senior’ non può essere il nuovo referente scientifico
if role_emp<>'senior' then
	update project
	set sref=old.sref; --il referente scientifico resta il precedente
end if;
end;
$check_scientific_reference_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger check_scientific_reference_trigger after insert or update of sref on azienda.project
--viene attivato ad ogni inserimento o modifica di sref nella tabella project
for each row
execute function azienda.check_scientific_reference();
---------------------------------

create function azienda.check_scientific_responsable_lab() returns trigger as $check_scientific_respnsable_lab_trigger$
declare
role_emp azienda.employee.role%type;

begin
--selezioniamo il ruolo dell’impiegato che vogliamo che sia il nuovo responsabile scientifico del laboratorio
select role into role_emp from azienda.employee where ssn=new.sresp;
--se l’impiegato ha un ruolo diverso da ‘senior’ non può avere tale carica
if role_emp<>'senior' then
	update laboratory
	set sresp=old.sresp; --il responsabile scientifico del laboratorio resterà invariato rispetto al precedente
end if;
end;
$check_scientific_respnsable_lab_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger check_scientific_respnsable_lab_trigger after insert or update of sresp on azienda.laboratory
--viene attivato ad ogni inserimento o modifica di sresp nella tabella laboratory
for each row
execute function azienda.check_scientific_responsable_lab();
-----------------------------------------

create function azienda.check_scientific_responsable_prj() returns trigger as $check_scientific_responsable_prj_trigger$
declare
role_emp azienda.employee.role%type;

begin
--selezioniamo il ruolo dell’impiegato che vogliamo che sia il nuovo responsabile scientifico del progetto
select role into role_emp from azienda.employee where ssn=new.sref;
--se il ruolo di tale impiegato non è ‘executive’ non potrà essere il nuovo responsabile scientifico
if role_emp<>'executive' then
	update project
	set sref=old.sref; --il responsabile rimarrà il precedente
end if;
end;
$check_scientific_responsable_prj_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger check_scientific_responsable_prj_trigger after insert or update of sref on azienda.project
--viene attivato a ogni inserimento o modifica di sref nella tabella project
for each row
execute function azienda.check_scientific_responsable_prj();
------------------------------------------

create function azienda.check_max_lab_insert() returns trigger as $check_max_lab_insert_trigger$
declare 
count_project integer;
   
begin
--viene contato il numero di laboratori che si occupano del progetto a cui vogliamo aggiungere un laboratorio
select count(project) into count_project from azienda.laboratory where project=new.project;
--non più di 3 laboratori possono occuparsi dello stesso progetto 
--(quindi il nome del suddetto progetto dovrà venir contato un massimo di tre volte)
if count_project>3 then
--ma se ciò accade viene eliminata la tupla che si voleva inserire
   delete from azienda.laboratory where name=new.name; 
end if;
end;
$check_max_lab_insert_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger check_max_lab_insert_trigger after insert or update on azienda.laboratory
--viene attivato ad ogni inserimento dell’attributo project in laboratory
for each row
execute function azienda.check_max_lab_insert();
-------------------------------------------

create function azienda.check_max_lab_update() returns trigger as $check_max_lab_update_trigger$
declare 
count_project integer;

begin
--viene contato il numero di laboratori che si occupano di un progetto che vorremmo sia preso in carica da un ulteriore laboratorio
select count(project) into count_project from azienda.laboratory where project=new.project;
--ma se esistono già tre laboratori che hanno a carico quel progetto...
if count_project>3 then
   --...viene impedita la modifica
   update azienda.laboratory set project=old.project where name=new.name; 
end if;
end;
$check_max_lab_update_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger check_max_lab_update_trigger after update of project on azienda.laboratory
--viene attivato ad ogni modifica dell'attributo project in laboratory
for each row
execute function azienda.check_max_lab_update();  
------------------------------

create function azienda.check_expired_project() returns trigger as $check_expired_project_trigger$
declare
end_date_prj azienda.project.end_date%type;
   
begin
--viene selezionata la end_date del progetto a cui siamo interessati
select end_date into end_date_prj from azienda.project where cup=new.cup;

--se il progetto è scaduto non è consentita l’assunzione di un ulteriore dipendente temporaneo
if end_date_prj<current_date then --si confronta la end_date del progetto con quella attuale
  --viene eliminata la nuova tupla inserita in temporary_contract
  delete from azienda.temporary_contract where ssn=new.ssn;
  --viene eliminata anche la tupla dell’impiegato che si voleva assumere dalla tabella employee
  delete from azienda.employee where ssn=new.ssn; 
end if;
end;
$check_expired_project_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger check_expired_project_trigger after insert on azienda.temporary_contract
--viene attivato ad ogni inserimento nella tabella temporary_contract
for each row
execute function azienda.check_expired_project();
----------------------
 
create function azienda.check_resp() returns trigger as $check_resp_trigger$
declare
emp_exists integer;
  
begin
--contiamo il numero di impiegati che sono responsabili scientifici di uno specifico progetto
select count(ssn) into emp_exists from azienda.project where sresp=new.ssn;

if emp_exists >= 1 then --se ne esiste almeno uno...
    if new.role <>'executive' then --...e la modifica non prevede che sia 'executive'...
    update azienda.employee set role=old.role where ssn=new.ssn; --...la modifica non è consentita
  end if;
end if;

--contiamo il numero di impiegati che sono referenti scientifici di uno specifico progetto
select count(ssn) into emp_exists from azienda.project where sref=new.ssn;

if emp_exists >= 1 then --se ne esiste almeno uno...
    if new.role <>'senior' then --...e tentiamo di cambiare il ruolo in uno diverso da 'senior'...
    update azienda.employee set role=old.role where ssn=new.ssn; --...la modifica non è consentita
  end if;
end if;

--contiamo il numero di impiegati che sono responsabili scientifici di uno specifico laboratorio
select count(ssn) into emp_exists from azienda.laboratory where sref=new.ssn;

if emp_exists >= 1 then --se ne esiste almeno uno...
    if new.role <>'senior' then --...e tentiamo di cambiare il ruolo in uno diverso da 'senior'...
    update azienda.employee set role=old.role where ssn=new.ssn; --...la modifica non è consentita
  end if;
end if;
end;
$check_resp_trigger$ LANGUAGE plpgsql;

--trigger corrispondente
create trigger check_resp_trigger after update of role on azienda.employee
--viene attivato ad ogni modifica nella tabella employee
for each row
execute function azienda.check_resp();

----------------------------
--PROCEDURE
create or replace procedure azienda.update_role_check_date() as
$$
declare
--il cursore scorre le tuple dei progetti scaduti
   expired_project cursor for select cup from azienda.project where end_date<current_date;
   ssn_expired azienda.employee.ssn%type;
   
begin
for cup_expired in expired_project loop
--prendiamo ogni impiegato temporaneo che ha lavorato per quel progetto
select ssn into ssn_expired from azienda.temporary_contract where cup=cup_expired;
--eliminiamo, dalle tabelle employee e temporary_contract, le tuple relative a tale impiegato che, 
--con il termine del progetto, è da considerare licenziato
delete from azienda.employee where ssn=ssn_expired;
delete from azienda.temporary_contract where ssn=ssn_expired;
end loop;
end; 
$$ LANGUAGE plpgsql;
------------------------------

create procedure azienda.check_employment_date_procedure()as $$
begin
if new.role = 'junior' or new.role = 'middle' or new.role = 'senior' then --si assicura che l’impiegato partecipa agli scatti di carriera 
--(gli impiegati con contratto determinato non sono contemplati)
    --gli impiegati che lavorano per l’azienda da meno di 3 anni sono ‘junior’
    if current_date-new.employment_date<3*365 and new.role<>'junior' then --confrontiamo la data di assunzione dell’impiegato con la data corrente
        update azienda.employee
        set role='junior'
        where ssn=new.ssn;
     --se l’impiegato lavora per l’azienda da più di 3 anni ma da meno di 7, diventa ‘middle’
     elsif current_date-new.employment_date>=3*365 and current_date-new.employment_date<7*365 and new.role<>"middle" then
        update azienda.employee
        set role='middle'
        where ssn=new.ssn;
    --se l’impiegato lavora per l’azienda da più di 7 anni diventa ‘senior’
    elsif current_date-new.employment_date>=7*365 and new.role<>"senior" then
        update azienda.employee
        set role='senior'
        where ssn=new.ssn;
	end if;
end if;
end;
$$ LANGUAGE plpgsql;
