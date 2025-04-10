insert into person(id, notes) values('user1', 'Usuário 1');
insert into person(id, notes) values('user2', 'Usuário 2');
insert into person(id, notes) values('user3', 'Usuário 3');
insert into person(id, notes) values('user4', 'Usuário 4');
insert into person(id, notes) values('admin1', 'Administrador 1');

select next value for invernada_seq;
insert into invernada(id, name) values(1, 'Invernada Mirim');
insert into invernada_member(invernada_id, person_id) values (1, 'user1');
insert into invernada_administrator(invernada_id, person_id) values (1, 'admin1');

select next value for invernada_seq;
insert into invernada(id, name) values(2, 'Invernada Adulta');
insert into invernada_member(invernada_id, person_id) values (2, 'user2');
insert into invernada_member(invernada_id, person_id) values (2, 'user3');
insert into invernada_administrator(invernada_id, person_id) values (2, 'admin1');

select next value for pilcha_seq;
insert into pilcha(id, description, pilcha_type, tag, notes) values (1, 'bombacha larga', 1, 'tag01', 'bombacha campeira marrom');
insert into pilcha_owner(pilcha_id, person_id) values (1,'user1');

select next value for pilcha_seq;
insert into pilcha(id, description, pilcha_type, tag, notes) values (2, 'vestido azul', 7, 'tag02', 'vestido de baile azul');
insert into pilcha_owner(pilcha_id, person_id) values (2,'user2');

select next value for event_seq;
insert into event(id, name, event_date, event_place, invernada_id) values (1, 'evento 1', '2024-12-05 13:45:03', 'rancho da saudade', 1);
insert into event_participant(event_id, person_id) values (1, 'user1');
