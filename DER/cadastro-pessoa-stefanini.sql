-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.7.0
-- PostgreSQL version: 9.3
-- Project Site: pgmodeler.com.br
-- Model Author: ---

SET check_function_bodies = false;
-- ddl-end --


-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: "cadastro-pessoa-stefanini" | type: DATABASE --
-- -- DROP DATABASE "cadastro-pessoa-stefanini";
-- CREATE DATABASE "cadastro-pessoa-stefanini"
-- ;
-- -- ddl-end --
-- 

-- object: public.pessoa | type: TABLE --
-- DROP TABLE public.pessoa;
CREATE TABLE public.pessoa(
	id bigint,
	nome varchar(200) NOT NULL,
	sexo integer,
	email varchar(200),
	data_nascimento date NOT NULL,
	naturalidade varchar(200),
	nacionalidade varchar(200),
	cpf varchar(11),
	data_cadastro timestamp with time zone,
	data_atualizacao timestamp with time zone,
	id_endereco bigint,
	CONSTRAINT pessoa_pk PRIMARY KEY (id)

);
-- ddl-end --
-- object: public.pessoa_seq | type: SEQUENCE --
-- DROP SEQUENCE public.pessoa_seq;
CREATE SEQUENCE public.pessoa_seq
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --

-- object: public.endereco | type: TABLE --
-- DROP TABLE public.endereco;
CREATE TABLE public.endereco(
	id bigint,
	logradouro varchar(100),
	bairro varchar(100),
	cidade varchar(100),
	cep varchar(8),
	uf integer,
	numero integer,
	data_cadastro timestamp with time zone,
	data_atualizacao timestamp with time zone,
	CONSTRAINT endereco_pk PRIMARY KEY (id)

);
-- ddl-end --
-- object: endereco_fk | type: CONSTRAINT --
-- ALTER TABLE public.pessoa DROP CONSTRAINT endereco_fk;
ALTER TABLE public.pessoa ADD CONSTRAINT endereco_fk FOREIGN KEY (id_endereco)
REFERENCES public.endereco (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --


-- object: pessoa_uq | type: CONSTRAINT --
-- ALTER TABLE public.pessoa DROP CONSTRAINT pessoa_uq;
ALTER TABLE public.pessoa ADD CONSTRAINT pessoa_uq UNIQUE (id_endereco);
-- ddl-end --


-- object: public.endereco_seq | type: SEQUENCE --
-- DROP SEQUENCE public.endereco_seq;
CREATE SEQUENCE public.endereco_seq
	INCREMENT BY 1
	MINVALUE 0
	MAXVALUE 2147483647
	START WITH 1
	CACHE 1
	NO CYCLE
	OWNED BY NONE;
-- ddl-end --


