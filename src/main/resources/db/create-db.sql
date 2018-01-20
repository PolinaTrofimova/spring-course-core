CREATE TABLE users (
  id INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(30),
  email  VARCHAR(50),
  birthday TIMESTAMP
);

CREATE TABLE tickets (
     id INTEGER IDENTITY PRIMARY KEY,
     user_id INTEGER,
     seat INTEGER,
     show INTEGER,
     price INTEGER,
     message VARCHAR(100)
);

CREATE TABLE events (
  id INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(30),
  price INTEGER,
  rating VARCHAR(10)
);

CREATE TABLE shows (
  id INTEGER IDENTITY PRIMARY KEY,
  event INTEGER,
  auditorium INTEGER,
  showtime TIMESTAMP
);

CREATE TABLE auditoriums (
  id         INTEGER PRIMARY KEY,
  name VARCHAR(30),
  email  VARCHAR(50)
);

CREATE TABLE vipseats (
  auditorium INTEGER,
  seat INTEGER
);

CREATE TABLE event_counters (
  event_id INTEGER,
  get_counter INTEGER,
  price_counter INTEGER,
  book_counter INTEGER
);

CREATE TABLE discount_counters (
  user_id INTEGER,
  tenth_counter INTEGER,
  birthday_counter INTEGER,

);
