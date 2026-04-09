create table document_revision_segments (
    segment_id varchar(16) primary key,
    document_id varchar(12) not null,
    revision_number integer not null,
    segment_type varchar(32) not null,
    segment_ordinal integer not null,
    token_estimate integer not null,
    locator varchar(255) not null,
    segment_text text not null,
    constraint fk_document_revision_segments_revision
        foreign key (document_id, revision_number) references document_revisions (document_id, revision_number)
        on delete cascade,
    constraint uq_document_revision_segments_ordinal
        unique (document_id, revision_number, segment_ordinal)
);
