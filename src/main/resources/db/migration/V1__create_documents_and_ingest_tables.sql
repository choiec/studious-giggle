create table documents (
    document_id varchar(12) primary key,
    title varchar(255) not null,
    status varchar(32) not null,
    updated_at timestamp not null
);

create table document_revisions (
    document_id varchar(12) not null,
    revision_number integer not null,
    source_id varchar(255) not null,
    content_hash varchar(64) not null,
    content text not null,
    primary key (document_id, revision_number),
    constraint fk_document_revisions_document
        foreign key (document_id) references documents (document_id)
        on delete cascade
);

create table ingest_sources (
    source_id varchar(255) primary key,
    format varchar(32) not null,
    title varchar(255) not null,
    payload text not null,
    stored_at timestamp not null
);
