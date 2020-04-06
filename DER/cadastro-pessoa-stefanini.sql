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
	logradouro varchar(100) NOT NULL,
	bairro varchar(100) NOT NULL,
	cidade varchar(100) NOT NULL,
	cep varchar(8) NOT NULL,
	uf integer NOT NULL,
	numero integer NOT NULL,
	data_cadastro timestamp with time zone,
	data_atualizacao timestamp with time zone,
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




