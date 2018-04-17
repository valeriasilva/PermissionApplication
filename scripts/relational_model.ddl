-- Gerado por Oracle SQL Developer Data Modeler 17.4.0.355.2121
--   em:        2018-02-12 03:16:21 BRST
--   site:      Oracle Database 11g
--   tipo:      Oracle Database 11g



DROP TABLE feature CASCADE CONSTRAINTS;

DROP TABLE permission_ CASCADE CONSTRAINTS;

DROP TABLE plugin CASCADE CONSTRAINTS;

DROP TABLE user_ CASCADE CONSTRAINTS;

CREATE TABLE feature (
    id             NUMBER NOT NULL,
    name           VARCHAR2(50) NOT NULL,
    description    VARCHAR2(500),
    creationdate   TIMESTAMP WITH LOCAL TIME ZONE,
    plugin_id      NUMBER NOT NULL
);

ALTER TABLE feature ADD CONSTRAINT feature_pk PRIMARY KEY ( id );

CREATE TABLE permission_ (
    id          NUMBER NOT NULL,
    userid      NUMBER NOT NULL,
    featureid   NUMBER NOT NULL
);

ALTER TABLE permission_ ADD CONSTRAINT permission_pk PRIMARY KEY ( id );

CREATE TABLE plugin (
    id             NUMBER NOT NULL,
    name           VARCHAR2(50) NOT NULL,
    description    VARCHAR2(500),
    creationdate   TIMESTAMP WITH LOCAL TIME ZONE
);

ALTER TABLE plugin ADD CONSTRAINT plugin_pk PRIMARY KEY ( id );

CREATE TABLE user_ (
    id                  NUMBER NOT NULL,
    fullname            VARCHAR2(100) NOT NULL,
    status              NUMBER,
    currentmanagement   VARCHAR2(100),
    login               VARCHAR2(4) NOT NULL
);

ALTER TABLE user_ ADD CONSTRAINT user__pk PRIMARY KEY ( id );

ALTER TABLE feature
    ADD CONSTRAINT feature_plugin_fk FOREIGN KEY ( plugin_id )
        REFERENCES plugin ( id );

ALTER TABLE permission_
    ADD CONSTRAINT permission_feature_fk FOREIGN KEY ( featureid )
        REFERENCES feature ( id );

ALTER TABLE permission_
    ADD CONSTRAINT permission_user__fk FOREIGN KEY ( userid )
        REFERENCES user_ ( id );

CREATE SEQUENCE feature_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER feature_id_trg BEFORE
    INSERT ON feature
    FOR EACH ROW
    WHEN ( new.id IS NULL )
BEGIN
    :new.id := feature_id_seq.nextval;
END;
/

CREATE SEQUENCE permission__id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER permission__id_trg BEFORE
    INSERT ON permission_
    FOR EACH ROW
    WHEN ( new.id IS NULL )
BEGIN
    :new.id := permission__id_seq.nextval;
END;
/

CREATE SEQUENCE plugin_id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER plugin_id_trg BEFORE
    INSERT ON plugin
    FOR EACH ROW
    WHEN ( new.id IS NULL )
BEGIN
    :new.id := plugin_id_seq.nextval;
END;
/

CREATE SEQUENCE user__id_seq START WITH 1 NOCACHE ORDER;

CREATE OR REPLACE TRIGGER user__id_trg BEFORE
    INSERT ON user_
    FOR EACH ROW
    WHEN ( new.id IS NULL )
BEGIN
    :new.id := user__id_seq.nextval;
END;
/



-- Relatório do Resumo do Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                             4
-- CREATE INDEX                             0
-- ALTER TABLE                              7
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           4
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          4
-- CREATE MATERIALIZED VIEW                 0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
