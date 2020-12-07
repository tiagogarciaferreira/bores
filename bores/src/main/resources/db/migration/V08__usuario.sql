CREATE TABLE "usuario" (
  "id" serial8,
  "nome" varchar(80) NOT NULL,
  "email" varchar(40) NOT NULL,
  "senha" varchar(100) NOT NULL,
  "ativo" bool NOT NULL,
  "version" int4 NOT NULL,
  PRIMARY KEY ("id")
);