--controllo trigger check_employment_date()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Franco','Forte','1234567899','forte@gmail.com','address','2024-06-06',1000,'junior',null);

insert into azienda.employee(ssn,first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('123456781','Franco','Forte','1234567899','forte@gmail.com','address','2022-06-06',1000,'middle',null);

--controllo trigger check_employee_role_update()

insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Franco','Forte','1234567899','forte@gmail.com','address','2022-06-06',1000,'junior',null);

update azienda.employee set role = 'executive', salary = 3000 where ssn = '000000001';

--controllo trigger initialize_remaining_fund

insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2020-06-06',2500,'executive',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',50000,10000,'2022-12-12','2024-12-12','000000001','000000002');

--controllo trigger check_contratc_insert()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2020-06-06',2500,'executive',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',100000,100000,'2022-12-12','2024-12-12','000000001','000000002');

insert into azienda.employee(ssn,first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('123456791','Franco','Forte','1234567899','forte@gmail.com','address','2022-06-06',100000,'temporary',null),
('123456792','Luca','Bolla','1234567898','bolla@gmail.com','address','2022-06-06',1500,'temporary',null);

insert into azienda.temporary_contract(ssn,cup) 
values 
('123456791','123456789012345'),
('123456792','123456789012345');

--controllo trigger update_remaining_funds_contract_delete()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2020-06-06',2500,'executive',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',100000,100000,'2022-12-12','2024-12-12','000000001','000000002');

insert into azienda.employee(ssn,first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('123456792','Luca','Bolla','1234567898','bolla@gmail.com','address','2022-06-06',1500,'temporary',null);

insert into azienda.temporary_contract(ssn,cup) 
values 
('123456792','123456789012345');

delete from azienda.temporary_contract where ssn = '123456792';

--controllo trigger update_remaining_funds_employee_update()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2020-06-06',2500,'executive',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',100000,100000,'2022-12-12','2024-12-12','000000001','000000002');

insert into azienda.employee(ssn,first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('123456792','Luca','Bolla','1234567898','bolla@gmail.com','address','2022-06-06',1500,'temporary',null);

insert into azienda.temporary_contract(ssn,cup) 
values 
('123456792','123456789012345');

update azienda.employee set salary = 2000 where ssn = '123456792';

--controllo trigger check_remaining_funds_equipment_insert()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2020-06-06',2500,'executive',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',50000,50000,'2022-12-12','2024-12-12','000000001','000000002');

insert into azienda.equipment(id_equipment, name, description, price, purchase_date, dealer ,laboratory_name, project_cup)
values
('122','equipment2','descrizione',300,'2022-12-12','dealer',null,'123456789012345');

----controllo trigger check_remaining_funds_equipment_delete()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2020-06-06',2500,'executive',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',50000,50000,'2022-12-12','2024-12-12','000000001','000000002');

insert into azienda.equipment(id_equipment, name, description, price, purchase_date, dealer ,laboratory_name, project_cup)
values
('123','equipment1','descrizione',25001,'2022-12-12','dealer',null,'123456789012345');

--controllo trigger check_end_date_project_insert()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2020-06-06',2500,'executive',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',50000,50000,'2022-12-12','2023-1-1','000000001','000000002'),
('123456789012343','progetto2',50000,50000,'2022-12-12','2021-1-1','000000001','000000002');

--controllo trigger check_end_date_project_update()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2020-06-06',2500,'executive',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',50000,50000,'2022-12-12','2024-1-1','000000001','000000002');

update azienda.project set end_date = '2023-1-1' where cup = '123456789012345';

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',50000,50000,'2022-12-12','2024-1-1','000000001','000000002');

update azienda.project set end_date = '2021-1-1' where cup = '123456789012345';

--controllo trigger check_budget_update()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2020-06-06',2500,'executive',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',100000,100000,'2022-12-12','2024-12-12','000000001','000000002');

insert into azienda.employee(ssn,first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('123456791','Franco','Forte','1234567899','forte@gmail.com','address','2022-12-06',1000,'temporary',null);

insert into azienda.temporary_contract(ssn,cup) 
values 
('123456791','123456789012345');

update azienda.project set budget = 50000 where cup = '123456789012345';

--controllo trigger check_scientific_reference()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2020-06-06',2500,'executive',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2022-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',100000,100000,'2022-12-12','2024-12-12','000000001','000000002');

--controllo trigger check_scientific_responsable_lab()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2020-06-06',2500,'executive',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',100000,100000,'2022-12-12','2024-12-12','000000001','000000002');

insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000003','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.laboratory(name, topic, sresp, project)
values
('laboratorio1','topic','000000001','123456789012345');
--controllo trigger check_scientific_responsable_prj()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2022-06-06',2500,'junior',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',100000,100000,'2022-12-12','2024-12-12','000000001','000000002');

--controllo trigger check_max_lab_insert()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2022-06-06',2500,'executive',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',100000,100000,'2022-12-12','2024-12-12','000000001','000000002');

insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000003','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.laboratory(name, topic, sresp, project)
values
('laboratorio1','topic','000000003','123456789012345'),
('laboratorio2','topic','000000003','123456789012345'),
('laboratorio3','topic','000000003','123456789012345'),
('laboratorio4','topic','000000003','123456789012345');

--controllo trigger check_max_lab_update()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2022-06-06',2500,'executive',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',100000,100000,'2022-12-12','2024-12-12','000000001','000000002');

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012342','progetto2',100000,100000,'2022-12-12','2024-12-12','000000001','000000002');

insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000003','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.laboratory(name, topic, sresp, project)
values
('laboratorio1','topic','000000003','123456789012345'),
('laboratorio2','topic','000000003','123456789012345'),
('laboratorio3','topic','000000003','123456789012345'),
('laboratorio4','topic','000000003','123456789012342');

update azienda.laboratory set project = '123456789012345' where name = 'laboratorio4';

--controllo trigger check_remaining_funds_update()
insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2020-06-06',2500,'executive',null),
('000000002','Franco','Forte','1234567899','forte@gmail.com','address','2010-06-06',2500,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',100000,100000,'2022-12-12','2024-12-12','000000001','000000002');

insert into azienda.employee(ssn,first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('123456791','Franco','Forte','1234567899','forte@gmail.com','address','2022-12-06',1000,'temporary',null);

insert into azienda.temporary_contract(ssn,cup) 
values 
('123456791','123456789012345');

update azienda.project set remaining_funds = 50000 where cup = '123456789012345';


--popolamento

insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000001','Fausto','Leali','1234567898','leali@gmail.com','address','2020-06-06',2500,'executive',null),
('000000002','Amedeo','Sebastiani','1234567899','sebastiani@gmail.com','address','2010-06-06',2000,'junior',null);

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012345','progetto1',200000,200000,'2022-12-12','2024-12-12','000000001','000000002');

insert into azienda.laboratory(name, topic, sresp, project)
values
('laboratorio1','topic','000000002','123456789012345');

insert into azienda.employee(ssn,first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('123456791','Franco','Forte','1234567899','forte@gmail.com','address','2022-06-06',1500,'temporary','laboratorio1'),
('123456792','Luca','Bolla','1234567898','bolla@gmail.com','address','2022-06-06',1500,'temporary','laboratorio1');

insert into azienda.temporary_contract(ssn,cup) 
values 
('123456791','123456789012345'),
('123456792','123456789012345');

insert into azienda.equipment(id_equipment, name, description, price, purchase_date, dealer ,laboratory_name, project_cup)
values
('122','equipment2','descrizione',300,'2022-12-12','dealer','laboratorio1','123456789012345');

insert into azienda.employee(ssn, first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('000000004','Erensto','Bassi','1234567893','bassi@gmail.com','address','2020-06-06',2500,'executive','laboratorio1'),
('000000005','Luciano','Spalletti','1234567891','spalleti@gmail.com','address','2010-06-06',2000,'junior','laboratorio1');

insert into azienda.project(cup, name, budget, remaining_funds, start_date, end_date, sresp, sref)
values
('123456789012344','progetto2',250000,250000,'2022-12-12','2024-12-12','000000004','000000005');

insert into azienda.laboratory(name, topic, sresp, project)
values
('laboratorio2','topic','000000005','123456789012344');

insert into azienda.employee(ssn,first_name, last_name, phone_num, email, address, employment_date, salary, role, laboratory_name) 
values
('123456793','Matteo','Denaro','1234567899','denaro@gmail.com','address','2022-06-06',1500,'temporary','laboratorio2'),
('123456794','Franceso','Schettino','1234567892','schettino@gmail.com','address','2022-06-06',1500,'temporary','laboratorio2');

insert into azienda.temporary_contract(ssn,cup) 
values 
('123456793','123456789012344'),
('123456794','123456789012344');

insert into azienda.equipment(id_equipment, name, description, price, purchase_date, dealer ,laboratory_name, project_cup)
values
('123','equipment2','descrizione',300,'2022-12-12','dealer','laboratorio2','123456789012344');







