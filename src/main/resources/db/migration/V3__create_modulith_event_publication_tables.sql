create table event_publication (
    id uuid primary key,
    publication_date timestamp not null,
    listener_id varchar(255) not null,
    serialized_event varchar(255) not null,
    event_type varchar(255) not null,
    completion_date timestamp,
    last_resubmission_date timestamp,
    completion_attempts integer not null default 0,
    status varchar(255)
);

create table event_publication_archive (
    id uuid primary key,
    publication_date timestamp not null,
    listener_id varchar(255) not null,
    serialized_event varchar(255) not null,
    event_type varchar(255) not null,
    completion_date timestamp,
    last_resubmission_date timestamp,
    completion_attempts integer not null default 0,
    status varchar(255)
);
