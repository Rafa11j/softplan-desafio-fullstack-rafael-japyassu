create table users (
    id uuid PRIMARY KEY,
    name character varying(255),
    email character varying(255) UNIQUE,
    password character varying(255),
    user_type integer,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);

create table process (
    id uuid PRIMARY KEY,
    process character varying(255) NOT NULL UNIQUE,
    lawyer character varying(255) NOT NULL,
    opinion text,
    process_type character varying(255) NOT NULL,
    state character varying(100) DEFAULT 'OPEN'::character varying,
    stick character varying(255) NOT NULL,
    subject text NOT NULL,
    value numeric(20,2) NOT NULL,
    created_at timestamp without time zone,
    updated_at timestamp without time zone
);

create table user_process (
    id uuid PRIMARY KEY,
    process_id uuid REFERENCES process(id),
    user_id uuid REFERENCES users(id)
);