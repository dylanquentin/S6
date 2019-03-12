create table Election (
  liste VARCHAR(20) constraint election_pkey PRIMARY KEY,
  nbVoix integer not null,
  nbSieges integer default 0

);