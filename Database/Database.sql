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
    name varchar(30) unique not null,
    budget float not null,
    remaining_funds float not null,
    start_date date not null,
    end_date date not null,
    sresp azienda.ssn_type not null,
    sref azienda.ssn_type not null,
--vincoli referenziali
    constraint project_pk primary key(cup),
    constraint psresp_fk foreign key(sresp) references azienda.employee(ssn) on update cascade on delete restrict, --se l’ssn di un impiegato viene aggiornato 
    --vengono aggiornate tutte le tuple in project che lo referenziano. Se invece esso viene eliminato, non viene permessa l'operazione
    constraint psref_fk foreign key(sref) references azienda.employee(ssn) on update cascade on delete restrict  --se l’ssn di un impiegato viene aggiornato 
    --vengono aggiornate tutte le tuple in project che lo referenziano. Se invece esso viene eliminato, non viene permessa l'operazione
);
--Laboratory
create table azienda.laboratory(
    name varchar(30),
    topic varchar(50),
    sresp azienda.ssn_type not null,
    project char(15) not null, --controllare
--vincoli referenziali
    constraint lab_pk primary key(name),
    constraint prj_lab_fk foreign key(project) references azienda.project(cup) on update cascade on delete cascade, --se la primary key di project viene aggiornata vengono aggiornate anche le tuple in laboratory che la referenziano. 
    --Se invece essa viene eliminata, l’attributo project in laboratory, in tutte le tuple che lo referenziano, viene settato a null
    constraint lsresp_fk foreign key(sresp) references azienda.employee(ssn) on update cascade on delete restrict  --se l’ssn di un impiegato viene aggiornato 
    --vengono aggiornate tutte tuple in laboratory che lo referenziano. Se invece esso viene eliminato, non viene permessa l'operazione
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
add constraint emp_lab_fk foreign key(laboratory_name) references azienda.laboratory(name) on update cascade on delete set null ;  --se il nome di un laboratorio viene aggiornato vengono aggiornate anche le tuple in employee che lo referenziano.
--Se invece esso viene eliminato, l’attributo laboratory_name in employee, in tutte le tuple che lo referenziano, viene settato a null


-----------------------------------------------------------------------------------------------
--FUNZIONI E TRIGGER
create function azienda.check_employment_date() returns trigger as $check_employment_date_trigger$
begin

    if current_date - new.employment_date > 0 then

        if new.role like 'junior' or new.role like 'middle' or new.role like 'senior' then

            --gli impiegati che lavorano per l’azienda da meno di 3 anni devono essere ‘junior’
            if current_date - new.employment_date < 3*365 and new.role not like 'junior' then

                update azienda.employee
                set role = 'junior'
                where ssn = new.ssn;
                
                delete from azienda.career_development 
                where ssn = new.ssn 
                and new_role not like 'junior' 
                and new_role not like 'executive' 
                and role_change_date = (
                    select max(role_change_date) 
                    from azienda.career_development 
                    where ssn = new.ssn);
                
                RAISE NOTICE 'Questo impiegato (%) lavora da meno di 3 anni quindi non può essere %, da adesso è junior.', new.ssn, new.role;

            --se l’impiegato lavora per l’azienda da più di 3 anni ma da meno di 7, diventa ‘middle’
            elsif current_date - new.employment_date >= 3*365 and current_date-new.employment_date < 7*365 and new.role not like 'middle' then
                
                update azienda.employee
                set role = 'middle'
                where ssn = new.ssn;

                delete from azienda.career_development
                where ssn = new.ssn 
                and new_role not like 'middle' 
                and new_role not like 'executive' 
                and role_change_date = (
                    select max(role_change_date) 
                    from azienda.career_development 
                    where ssn = new.ssn);

                RAISE NOTICE 'Questo impiegato (%) lavora da più di 3 anni e meno di 7 quindi non può essere %, da adesso è middle.', new.ssn, new.role;

            --se l’impiegato lavora per l’azienda da più di 7 anni diventa ‘senior’
            elsif current_date - new.employment_date >= 7*365 and new.role not like 'senior' then
                update azienda.employee
                set role = 'senior'
                where ssn = new.ssn;

                delete from azienda.career_development 
                where ssn = new.ssn 
                and new_role not like 'senior' 
                and new_role not like 'executive' 
                and role_change_date = (
                    select max(role_change_date) 
                    from azienda.career_development 
                    where ssn = new.ssn);

                RAISE NOTICE 'Questo impiegato (%) lavora da più di 7 anni quindi non può essere %, da adesso è senior.', new.ssn, new.role;

            end if;
        end if;

    elsif current_date - new.employment_date <= 0 then

        delete from azienda.employee 
        where ssn = new.ssn;

        RAISE NOTICE 'La data di assunzione non può essere maggiore della data odierna, impiegato (%) non aggiunto.', new.ssn;

    end if;

return null;
end;
$check_employment_date_trigger$ LANGUAGE plpgsql;

--trigger
create trigger check_employment_date_trigger after insert or update of role on azienda.employee
for each row
execute function azienda.check_employment_date();

-------------------------------------------------------------------------

create function azienda.check_employee_role_update() returns trigger as $check_employee_role_update_trigger$
declare
emp_exists integer;
begin

    if old.role not like 'temporary' and new.role not like 'temporary' then
        --se stiamo tentando di far essere quell'impiegato il nuovo responsabile scientifico di un progetto...
        select count(cup) into emp_exists 
        from azienda.project 
        where sresp = new.ssn;

        if emp_exists >= 1 then
            --...ma il suo ruolo è diverso da executive...
            if new.role not like 'executive' then
                --...non viene permessa la modifica
                update azienda.employee 
                set role = old.role 
                where ssn = new.ssn;

                return null;
            
            end if;
        
        end if;
        --se stiamo tentando di far essere quell'impiegato il nuovo referente scientifico...
        select count(cup) into emp_exists 
        from azienda.project 
        where sref=new.ssn;

        if emp_exists >= 1 then 
            --...ma il suo ruolo è diverso da senior...
            if new.role not like 'senior' then
                --...non viene permessa la modifica
                update azienda.employee 
                set role = old.role 
                where ssn = new.ssn;

                return null;
            
            end if;
        
        end if;

        --se stiamo tentando di far essere quell'impiegato il nuovo responsabile scientifico di un laboratorio...
        select count(name) into emp_exists 
        from azienda.laboratory
        where sresp = new.ssn;

        if emp_exists >= 1 then
            --...ma il suo ruolo è diverso da senior...
            if new.role not like 'senior' then
                --...non viene permessa la modifica
                update azienda.employee 
                set role = old.role 
                where ssn = new.ssn;

                return null;

            end if;

        end if;
        --se il cambio ruolo è lecito aggiunge le nuove informazioni 
	    insert into azienda.career_development values (old.role, new.role, current_date, new.salary-old.salary, new.ssn);

        RAISE NOTICE 'Scatto di carriera aggiunto.';

    elsif new.role == 'temporary' then
        
        RAISE NOTICE 'Un impiegato non può diventare temporary ma deve essere assunto come tale.';
    
    elsif old_role == 'temporary' then

        RAISE NOTICE 'Un impiegato temporary per cambiare ruolo deve essere riassunto.';
    
    end if;

return null;
end;
$check_employee_role_update_trigger$ LANGUAGE plpgsql;

--trigger
create trigger check_employee_role_update_trigger after update of role on azienda.employee
for each row
execute function azienda.check_employee_role_update();

---------------------------------

create function azienda.initialize_remaining_funds() returns trigger as $initialize_remaining_funds_trigger$
begin

    if new.budget > 0 then 
        --inizializza il valore di remaining_funds
        update azienda.project
        set remaining_funds = new.budget
        where cup=new.cup;
    
    elsif new.budget <= 0 then

        delete from azienda.project
        where cup = new.cup; 

        RAISE NOTICE 'Budget progetto non valido, deve essere > 0.';
    
    end if;

return null;
end;
$initialize_remaining_funds_trigger$ LANGUAGE plpgsql;

--trigger
create trigger initialize_remaining_funds_trigger after insert on azienda.project
for each row
execute function azienda.initialize_remaining_funds();

---------------------------------

create function azienda.check_contratc_insert() returns trigger as $check_contratc_insert_trigger$
declare
    check_exists integer;
    role_emp azienda.employee.role%type;
    emp_salary azienda.employee.salary%type;
    end_date_prj azienda.project.end_date%type;
    remaining_months integer;
    sum_salary float;
    new_remaining_funds float;
    sum_price azienda.equipment.price%type;
    project_budget azienda.project.budget%type;

begin

    select count(ssn) into check_exists
    from azienda.temporary_contract
    where ssn = new.ssn;

    --controllo che l'impiegato non abbia già un contratto attivo
    if check_exists = 1 then

        select role, salary into role_emp, emp_salary 
        from azienda.employee 
        where ssn = new.ssn;

            
        if role_emp like 'temporary' then
            
            select end_date, budget into end_date_prj, project_budget
            from azienda.project 
            where cup = new.cup;

            --si calcola se è possibile garantire lo stipendio al nuovo impiegato fino al termine del proggetto
            SELECT (DATE_PART('year',end_date_prj::date) - DATE_PART('year', current_date::date)) * 12 +
                    (DATE_PART('month', end_date_prj::date) - DATE_PART('month', current_date::date)) into remaining_months;
                
            select remaining_funds into new_remaining_funds
            from azienda.project
            where cup = new.cup;

            new_remaining_funds := new_remaining_funds - (emp_salary * remaining_months);

            if new_remaining_funds >= 0 then 

                --prendiamo la somma dei salari degli impiegati temporanei che lavorano a tale progetto
                select sum(emp.salary) into sum_salary
                from azienda.employee emp join azienda.temporary_contract temp 
                on emp.ssn = temp.ssn
                where temp.cup = new.cup;

                --solo se non si supera il 50% del budget è consentita l'assunzione
                if sum_salary * remaining_months <= project_budget/2 then
                    
                    update azienda.project
                    set remaining_funds = new_remaining_funds
                    where cup = new.cup;

                    RAISE NOTICE 'Contratto inserito.';

                    return null;
                    
                elsif sum_salary * remaining_months > project_budget/2 then

                    delete from azienda.temporary_contract
                    where cup = new.cup and ssn = new.ssn;

                    update azienda.project
                    set remaining_funds = remaining_funds - (emp_salary * remaining_months)
                    where cup = new.cup;

                    RAISE NOTICE 'Contratto non inserito. I soldi utilizzati per gli stipendi supererebbero la metà del budget.';

                    return null;
                    
                end if;

            elsif new_remaining_funds <= 0 then 

                delete from azienda.temporary_contract
                where cup = new.cup and ssn = new.ssn;

                update azienda.project
                set remaining_funds = remaining_funds - (emp_salary * remaining_months)
                where cup = new.cup;

                RAISE NOTICE 'Non ci sono abbastanza soldi. %', remaining_months;


                return null;

            end if;

        elsif role_emp not like 'temporary' then
                
            delete from azienda.temporary_contract
            where cup = new.cup and ssn = new.ssn;

            update azienda.project
            set remaining_funds = remaining_funds - (emp_salary * remaining_months)
            where cup = new.cup;

            RAISE NOTICE 'Un impiegato non temporary non può avere un contratto a progetto.';

        end if;
    
    elsif check_exists > 1 then

        delete from azienda.temporary_contract
        where cup = new.cup
        and ssn = new.ssn;

        update azienda.project
        set remaining_funds = remaining_funds - (emp_salary * remaining_months)
        where cup = new.cup;

        RAISE NOTICE 'Un impiegato non può avere più di un contratto.';
    
    end if;

return null;
end;
$check_contratc_insert_trigger$ LANGUAGE plpgsql;

--trigger
create trigger check_contratc_insert_trigger after insert on azienda.temporary_contract
for each row
execute function azienda.check_contratc_insert();

---------------------------------------------------------------

create function azienda.update_remaining_funds_contract_delete() returns trigger as $update_remaining_funds_contract_delete_trigger$
declare
emp_salary float;
remaining_months integer;
end_date_prj azienda.project.end_date%type;
begin
    --se un impiegato temporaneo viene licenziato prima del termine del proggetto, i remaining_funds di quel progetto devono rimanere consistenti
    select end_date into end_date_prj
    from azienda.project
    where cup = old.cup;

     SELECT (DATE_PART('year',end_date_prj::date) - DATE_PART('year', current_date::date)) * 12 +
                    (DATE_PART('month', end_date_prj::date) - DATE_PART('month', current_date::date)) into remaining_months;

    select salary into emp_salary
    from azienda.employee
    where ssn = old.ssn;

    update azienda.project
    set remaining_funds = remaining_funds + (emp_salary * remaining_months)
    where cup = old.cup;

return null;
end;
$update_remaining_funds_contract_delete_trigger$ LANGUAGE plpgsql;

--trigger
create trigger update_remaining_funds_contract_delete_trigger after delete on azienda.temporary_contract
for each row
execute function azienda.update_remaining_funds_contract_delete();

----------------------------------------------------------------

create function azienda.check_remaining_funds_employee_update() returns trigger as $check_remaining_funds_employee_update_trigger$
declare

    end_date_prj azienda.project.end_date%type;
    remaining_months integer;
    sum_salary float;
    new_remaining_funds float;
    sum_price azienda.equipment.price%type;
    project_cup azienda.project.cup%type;
    project_budget azienda.project.budget%type;

begin

        if new.role like 'temporary' then

            select cup into project_cup
            from azienda.temporary_contract
            where ssn = new.ssn;
        
            select end_date into end_date_prj 
            from azienda.project 
            where cup = project_cup;

            --se tentiamo di modificare il salario di un impiegato temporaneo bisogna verificare che 
            --il nuovo stipendio venga garantito fino al termine del progetto
            SELECT (DATE_PART('year',end_date_prj::date) - DATE_PART('year', current_date::date)) * 12 +
                    (DATE_PART('month', end_date_prj::date) - DATE_PART('month', current_date::date)) into remaining_months;
            
            select remaining_funds , budget into new_remaining_funds , project_budget
            from azienda.project
            where cup = project_cup;

            new_remaining_funds := new_remaining_funds + (old.salary * remaining_months) - (new.salary * remaining_months);

            if new_remaining_funds >= 0 then 

                --prendiamo la somma dei salari degli impiegati temporanei che lavorano a tale progetto
                select sum(emp.salary) into sum_salary
                from azienda.employee emp join azienda.temporary_contract temp 
                on emp.ssn = temp.ssn
                where temp.cup = project_cup;

                --controllo che non si superi il 50% del budget
                if sum_salary * remaining_months <= project_budget/2 then
                
                    update azienda.project
                    set remaining_funds = new_remaining_funds
                    where cup = project_cup;

                    RAISE NOTICE 'Stipendio modificato.';

                    return null;
                --se si supera il 50% del budget la modifica non è consentita
                elsif sum_salary * remaining_months > new.budget/2 then

                    update azienda.employee
                    set salary = old.salary
                    where ssn = new.ssn;

                    RAISE NOTICE 'Stipendio non modificato. I soldi utilizzati per gli stipendi supererebbero la metà del budget.';

                    return null;
                
                end if;
            --se non ci sono abbastanza fondi la modifica non viene consentita
            elsif new_remaining_funds <= 0 then 

                update azienda.employee
                set salary = old.salary
                where ssn = new.ssn;

                RAISE NOTICE 'Stipendio non modificato. Non ci sono abbastanza soldi.';

                return null;

            end if;

        end if;

return null;
end;
$check_remaining_funds_employee_update_trigger$ LANGUAGE plpgsql;

--trigger
create trigger check_remaining_funds_employee_update_trigger after update of salary on azienda.employee
for each row
execute function azienda.check_remaining_funds_employee_update();

-------------------------------------------------------------------

create function azienda.check_remaining_funds_equipment_insert() returns trigger as $check_remaining_funds_equipment_insert_trigger$
declare
project_budget azienda.project.budget%type;
project_remaining_funds azienda.project.remaining_funds%type;
sum_equipment_cost float;
begin

    select budget, remaining_funds into project_budget, project_remaining_funds
    from azienda.project
    where cup = new.project_cup;

    select sum(price) into sum_equipment_cost
    from azienda.equipment
    where project_cup = new.project_cup;

    --controllo che ci sono dei fondi rimanenti
    if project_remaining_funds - sum_equipment_cost > 0 then 

        --il nuovo acquisto non deve superare il 50% del budget
        if  sum_equipment_cost <= project_budget/2 then

            update azienda.project
            set remaining_funds = remaining_funds - new.price
            where cup = new.project_cup;

            RAISE NOTICE 'Acquisto effettuato %.', sum_equipment_cost;

        --altrimenti l'acquisto non viene consentito
        elsif sum_equipment_cost > project_budget/2 then 

            delete from azienda.equipment
            where id_equipment = new.id_equipment;

            update azienda.project
            set remaining_funds = remaining_funds - new.price
            where cup = new.project_cup;

            RAISE NOTICE 'Acquisto non effettuato. I soldi utilizzati per gli equipaggiamenti supererebbero la metà del budget.';
        
        end if;
    
    elsif project_remaining_funds - sum_equipment_cost <= 0 then

        delete from azienda.equipment
        where id_equipment = new.id_equipment;

        RAISE NOTICE 'Acquisto non effettuato. Non ci sono abbastanza soldi.';
    
    end if;

return null;
end;
$check_remaining_funds_equipment_insert_trigger$ LANGUAGE plpgsql;

--trigger
create trigger check_remaining_funds_equipment_insert_trigger after insert on azienda.equipment
for each row
execute function azienda.check_remaining_funds_equipment_insert();

-------------------------------------------------------------------

create function azienda.update_remaining_funds_equipment_delete() returns trigger as $update_remaining_funds_equipment_delete_trigger$
begin
    --si mantiene consistente il valore di remaining_funds quando un equipment viene venduto
    update azienda.project
    set remaining_funds = remaining_funds + old.price
    where cup = old.project_cup;

    RAISE NOTICE 'Equipaggiamento venduto';

return null;
end;
$update_remaining_funds_equipment_delete_trigger$ LANGUAGE plpgsql;

--trigger
create trigger update_remaining_funds_equipment_delete_trigger after delete on azienda.equipment
for each row
execute function azienda.update_remaining_funds_equipment_delete();

-------------------------------------------------

create function azienda.check_end_date_project_insert() returns trigger as $check_end_date_project_insert_trigger$
begin
    --controlla che la data finale del proggetto non sia successiva alla data iniziale
    if new.start_date >= new.end_date then

        delete from azienda.PROJECT
        WHERE cup=new.cup;

        RAISE NOTICE 'Progetto non inserito. La data di fine progetto non può essere minore o uguale a quella di inzio.';
    --controlla che la data finale del proggetto sia successiva alla data iniziale
    elsif new.start_date < new.end_date then 
        --controlla che la data finale del proggetto non sia successiva alla data odierna
        if new.end_date <= current_date then

            delete from azienda.PROJECT
            WHERE cup=new.cup;

            RAISE NOTICE 'Progetto non inserito. La data di fine progetto non può essere minore o uguale a quella odierna.';
        --altrimenti il progetto viene inserito
        elsif new.end_date > current_date then

            RAISE NOTICE 'Progetto inserito.';
        
        end if;
        
    end if;

return null;
end;
$check_end_date_project_insert_trigger$ LANGUAGE plpgsql;

--trigger
create trigger check_end_date_project_insert_trigger after insert on azienda.project
for each row
execute function azienda.check_end_date_project_insert();

-------------------------------------------------------------

create function azienda.check_end_date_project_update() returns trigger as $check_end_date_project_update_trigger$
declare
sum_salary float;
remaining_months integer;
begin
    --controlla che la data finale del proggetto non sia successiva alla data iniziale
    if new.start_date >= new.end_date then

        delete from azienda.PROJECT
        WHERE cup = new.cup;

        RAISE NOTICE 'Progetto non aggiornato. La data di fine progetto non può essere minore o uguale a quella di inzio.';
    --controlla che la data finale del proggetto sia successiva alla data iniziale
    elsif new.start_date < new.end_date then 
        --controlla che la data finale del proggetto non sia successiva alla data odierna
        if new.end_date <= current_date then

            delete from azienda.PROJECT
            WHERE cup = new.cup;

            RAISE NOTICE 'Progetto non aggiornato. La data di fine progetto non può essere minore o uguale a quella odierna.';
        --altrimenti si controlla che ci sia budget sufficiente per la modifica
        elsif new.end_date > current_date then
        
            select sum(salary) into sum_salary
            from azienda.employee emp join azienda.temporary_contract temp
            on emp.ssn = temp.ssn
            where temp.cup = new.cup;

            SELECT (DATE_PART('year',new.end_date::date) - DATE_PART('year', current_date::date)) * 12 +
                        (DATE_PART('month', new.end_date::date) - DATE_PART('month', current_date::date)) into remaining_months;

            if sum_salary * remaining_months <= new.budget/2 then 

                RAISE NOTICE 'Progetto aggiornato.';
            --se non ci sono abbastanza soldi per sostenere gli stipendi, la modifica non viene consentita
            elsif sum_salary * remaining_months > new.budget/2 then

                update azienda.project
                set end_date = old.end_date
                where cup = new.cup;

                RAISE NOTICE 'No ci sono abbastanza soldi per finanziare il progetto fino al %', new.end_date;

             end if;
        
        end if;
    
    end if;

return null;
end;
$check_end_date_project_update_trigger$ LANGUAGE plpgsql;

--trigger
create trigger check_end_date_project_update_trigger after update of end_date on azienda.project
for each row
execute function azienda.check_end_date_project_update();

---------------------------------------------------------------

create function azienda.check_budget_update_before() returns trigger as $check_budget_update_before_trigger$
declare
sum_salary azienda.employee.salary%type;
sum_price azienda.equipment.price%type;
remaining_months integer;
check_exists integer;

begin

    sum_salary := 0;
    sum_price := 0;

    select count(id_equipment) into check_exists
    from azienda.equipment
    where project_cup = new.cup;

    if check_exists >= 1 then
    --prendiamo la somma dei prezzi dell’attrezzatura acquistata per un progetto 
    select sum(price) into sum_price
    from azienda.equipment
    where project_cup = new.cup;

    --se più del 50% del budget del progetto è stato già speso per l’acquisto di attrezzatura per lo stesso, 
    --non viene consentita la modifica del budget
        if sum_price > new.budget/2 then
            
            -- update azienda.project
            -- set budget = old.budget 
            -- where cup = new.cup;

            RAISE NOTICE 'Budget non aggiornato. I soldi spesi in attrezzatura supererebbero la metà del budget.';

            return old;

        end if;

    end if;

    if check_exists = 0 or sum_price <= new.budget/2 then

        select count(emp.ssn) into check_exists
        from azienda.employee emp join azienda.temporary_contract temp
        on emp.ssn = temp.ssn
        where temp.cup = new.cup;

        if check_exists >= 1 then

            --prendiamo la somma dei salari degli impiegati temporanei che lavorano a tale progetto
            select sum(emp.salary) into sum_salary
            from azienda.employee emp join azienda.temporary_contract temp
            on emp.ssn = temp.ssn
            where temp.cup = new.cup;

            --teniamo conto anche dei mesi non ancora retribuiti 
            --ma che devono essere garantiti ai dipendenti temporanei che già lavorano a questo progetto
                SELECT (DATE_PART('year',new.end_date::date) - DATE_PART('year', current_date::date)) * 12 +
                        (DATE_PART('month', new.end_date::date) - DATE_PART('month', current_date::date)) into remaining_months;

            --se più del 50% del budget è stato già speso per l’assunzione di dipendenti temporanei, 
            --non viene consentita la modifica del budget
            if sum_salary * remaining_months > new.budget/2 then
            
                -- update azienda.project
                -- set budget = old.budget
                -- where cup = new.cup;

                RAISE NOTICE 'Budget non aggiornato. I soldi spesi per gli stipendi dipendeti temporaney supererebbero la metà del budget.';

                return old;
            
            end if;
        
        end if;
 

    end if;

return new;
end;
$check_budget_update_before_trigger$ LANGUAGE plpgsql; 

--trigger
create trigger check_budget_update_before_trigger before update of budget on azienda.project
for each row
execute function azienda.check_budget_update_before();

-----------------------------------------------------------

create function azienda.check_budget_update_after() returns trigger as $check_budget_update_after_trigger$

begin
    --manteniamo remaining_funds consistente
    update azienda.project
    set remaining_funds = (new.remaining_funds - (old.budget - new.budget))
    where cup = new.cup;

    RAISE NOTICE 'Budget aggiornato.';
                
return null;
end;
$check_budget_update_after_trigger$ LANGUAGE plpgsql; 

--trigger
create trigger check_budget_update_after_trigger after update of budget on azienda.project
for each row
execute function azienda.check_budget_update_after();

---------------------------------

create function azienda.check_scientific_reference() returns trigger as $check_scientific_reference_trigger$
declare
role_emp azienda.employee.role%type;

begin
    
    select role into role_emp 
    from azienda.employee 
    where ssn = new.sref;

    if role_emp not like 'senior' then

        if old.sref is null then
            --se il progetto non aveva già un referente scientifico e new.sref<>'senior',
            --viene eliminato il progetto
            delete from azienda.project
            where cup = new.cup;

            RAISE NOTICE 'Progetto non aggiunto. Il referente scientifico deve essere necessariamente senior.';
        
        elsif old.sref is not null then
            --manteniamo il vecchio referente scientifico
            update azienda.project
            set sref = old.sref
            where cup = new.cup;

            RAISE NOTICE 'Referente scientifico non aggiornato. Il referente scientifico deve essere necessariamente senior.';
    
        end if;
    
    end if;

return null;
end;
$check_scientific_reference_trigger$ LANGUAGE plpgsql;

--trigger
create trigger check_scientific_reference_trigger after insert or update of sref on azienda.project
for each row
execute function azienda.check_scientific_reference();

-----------------------------------------------------

create function azienda.check_scientific_responsable_lab() returns trigger as $check_scientific_respnsable_lab_trigger$
declare
role_emp azienda.employee.role%type;

begin

    select role into role_emp 
    from azienda.employee 
    where ssn = new.sresp;
    
    if role_emp not like 'senior' then
        --se il laboratorio non aveva già un responsabile scientifico e new.sresp<>'senior',
        --viene eliminato il laboratorio
        if old.sresp is null then 

             delete from azienda.laboratory
             where name = new.name;

              RAISE NOTICE 'Laboratorio non aggiunto. Il responsabile scientifico deve essere necessariamente senior.';
        
        elsif old.sresp is not null then
            --manteniamo il vecchio responsabile scientifico
            update azienda.laboratory
            set sresp = old.sresp
            where cup = new.cup;

            RAISE NOTICE 'Responsabile scientifico non aggiornato. Il responsabile scientifico deve essere necessariamente senior.';

        end if;

    end if;

return null;
end;
$check_scientific_respnsable_lab_trigger$ LANGUAGE plpgsql;

--trigger
create trigger check_scientific_respnsable_lab_trigger after insert or update of sresp on azienda.laboratory
for each row
execute function azienda.check_scientific_responsable_lab();

----------------------------------------------------------

create function azienda.check_scientific_responsable_prj() returns trigger as $check_scientific_responsable_prj_trigger$
declare
role_emp azienda.employee.role%type;

begin
    
    select role into role_emp 
    from azienda.employee 
    where ssn = new.sresp;

    if role_emp not like 'executive' then

        if old.sresp is null then
            --se il progetto non aveva già un responsabile scientifico e new.sresp<>'executive',
            --viene eliminato il progetto
            delete from azienda.project
            where cup = new.cup;

            RAISE NOTICE 'Progetto non aggiunto. Il responsabile scientifico deve essere necessariamente executive.';
        
        elsif old.sresp is not null then
            --manteniamo il vecchio responsabile scientifico
            update azienda.project
            set sresp = old.sresp
            where cup = new.cup;

            RAISE NOTICE 'Responsabile scientifico non aggiornato. Il responsabile scientifico deve essere necessariamente executive.';

        end if;

    end if;

return null;
end;
$check_scientific_responsable_prj_trigger$ LANGUAGE plpgsql;

--trigger
create trigger check_scientific_responsable_prj_trigger after insert or update of sresp on azienda.project
for each row
execute function azienda.check_scientific_responsable_prj();

--------------------------------------------------------------------

create function azienda.check_max_lab_insert() returns trigger as $check_max_lab_insert_trigger$
declare 
count_project integer;
   
begin
    
    select count(project) into count_project
    from azienda.laboratory 
    where project = new.project;
    
    --verifica se esistono già tre laboratori che lavorano a quel progetto
    if count_project > 3 then
        --se è così, il quarto (l'attuale preso in considerazione) non viene inserito
        delete from azienda.laboratory
        where name = new.name;

        RAISE NOTICE 'Laboratorio non inserito. Il progetto ha già tre laboratori asseganti.'; 

    elsif count_project <= 3 then
        --se al massimo due laboratori lavoravano già a quel progetto,
        --è consentito l'inserimento di un terzo
        RAISE NOTICE 'Laboratorio inserito.'; 

    end if;

return null;
end;
$check_max_lab_insert_trigger$ LANGUAGE plpgsql;

--trigger
create trigger check_max_lab_insert_trigger after insert on azienda.laboratory
for each row
execute function azienda.check_max_lab_insert();

--------------------------------------------

create function azienda.check_max_lab_update() returns trigger as $check_max_lab_update_trigger$
declare 
count_project integer;
   
begin
    
    select count(project) into count_project
    from azienda.laboratory 
    where project = new.project;
    
    if count_project > 3 then
        --se new.project ha già tre laboratori che lavorano su di esso,
        --viene mantenuto old.project per la tupla del laboratorio modificata
        update azienda.laboratory
        set project = old.project 
        where name = new.name;

        RAISE NOTICE 'Modifica del progetto non consentita. Il progetto ha già tre laboratori assegnati.'; 

    elsif count_project <= 3 then

        RAISE NOTICE 'Modifica apportata.'; 

    end if;

return null;
end;
$check_max_lab_update_trigger$ LANGUAGE plpgsql;

--trigger
create trigger check_max_lab_update_trigger after update of project on azienda.laboratory
for each row
execute function azienda.check_max_lab_update();

---------------------------------------

create function azienda.check_expired_project() returns trigger as $check_expired_project_trigger$

begin
   --se il progetto è scaduto...
   if new.end_date < current_date then
      --viene eliminato
      delete from azienda.project
      where cup = new.cup;

      RAISE NOTICE 'Il progetto è stato eliminato perché scaduto';

    end if;   

return null;
end;
$check_expired_project_trigger$ LANGUAGE plpgsql;

--trigger
create trigger check_expired_project_trigger after update on azienda.project
for each row
execute function azienda.check_expired_project(); 

-----------------------------------------------------------

create function azienda.check_remaining_funds_update() returns trigger as $check_remaining_funds_update_trigger$
declare
sum_salary azienda.employee.salary%type;
sum_price azienda.equipment.price%type;
check_exists integer;
remaining_months integer;
begin

    sum_salary := 0;
    sum_price := 0;

    --vengono ricalcolati tutti i valori che servono per calcolare remaining_funds
    select count(emp.ssn) into check_exists
    from azienda.employee emp join azienda.temporary_contract temp
    on emp.ssn = temp.ssn
    where temp.cup = new.cup;

    if check_exists >= 1 then

        select sum(emp.salary) into sum_salary
        from azienda.employee emp join azienda.temporary_contract temp
        on emp.ssn = temp.ssn
        where temp.cup = new.cup;

        select count(id_equipment) into check_exists
        from azienda.equipment
        where project_cup = new.cup;

        if check_exists >= 1 then 

            select sum(price) into sum_price
            from azienda.equipment
            where project_cup = new.cup;
        
        end if;

        SELECT (DATE_PART('year',new.end_date::date) - DATE_PART('year', current_date::date)) * 12 +
                        (DATE_PART('month', new.end_date::date) - DATE_PART('month', current_date::date)) into remaining_months;

        --se la modifica e il valore attuale di remainig_funds non corrispondono allora la modifica non è consentita
        if new.remaining_funds <> new.budget - (sum_salary * remaining_months) - sum_price then

            update azienda.project
            set remaining_funds = new.budget - (sum_salary * remaining_months) - sum_price
            where cup = new.cup;

            RAISE NOTICE 'I fondi rimanenti non possono essere modificati manualmente';

        end if;

    end if;

return null;
end;
$check_remaining_funds_update_trigger$ LANGUAGE plpgsql;

--trigger
create trigger check_remaining_funds_update_trigger after update of remaining_funds on azienda.project
for each row
execute function azienda.check_remaining_funds_update();





