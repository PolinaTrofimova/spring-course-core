CREATE PROCEDURE count_get_event(ev_id integer)
MODIFIES SQL DATA
begin atomic
    declare num integer;
    set num = select get_counter from event_counters where event_id = ev_id;
    if num is null then
        insert into event_counters (event_id, get_counter, price_counter, book_counter) values (ev_id, 1, 0, 0);
    else
        update event_counters set get_counter = get_counter +1 where event_id = ev_id;
    end if;
end;
/;

CREATE PROCEDURE count_get_price_event(ev_id integer)
MODIFIES SQL DATA
begin atomic
    declare num integer;
    set num = select price_counter from event_counters where event_id = ev_id;
    if num is null then
        insert into event_counters (event_id, get_counter, price_counter, book_counter) values (ev_id, 0, 1, 0);
    else
        update event_counters set price_counter = price_counter +1 where event_id = ev_id;
    end if;
end;
/;

CREATE PROCEDURE count_book_event(ev_id integer)
MODIFIES SQL DATA
begin atomic
    declare num integer;
    set num = select book_counter from event_counters where event_id = ev_id;
    if num is null then
        insert into event_counters (event_id, get_counter, price_counter, book_counter) values (ev_id, 0, 0, 1);
    else
        update event_counters set book_counter = book_counter +1 where event_id = ev_id;
    end if;
end;
/;

CREATE PROCEDURE count_tenth_discount(u_id integer)
MODIFIES SQL DATA
begin atomic
    declare num integer;
    set num = select tenth_counter from discount_counters where user_id = u_id;
    if num is null then
        insert into discount_counters (user_id, tenth_counter, birthday_counter) values (u_id, 1, 0);
    else
        update discount_counters set tenth_counter = tenth_counter +1 where user_id = u_id;
    end if;
end;
/;

CREATE PROCEDURE count_birthday_discount(u_id integer)
MODIFIES SQL DATA
begin atomic
    declare num integer;
    set num = select birthday_counter from discount_counters where user_id = u_id;
    if num is null then
        insert into discount_counters (user_id, tenth_counter, birthday_counter) values (u_id, 0, 1);
    else
        update discount_counters set birthday_counter = birthday_counter +1 where user_id = u_id;
    end if;
end;
/;