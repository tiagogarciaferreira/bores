CREATE TABLE "usuario" (
  "id" serial8,
  "nome" varchar(50) NOT NULL,
  "email" varchar(50) NOT NULL,
  "senha" varchar(100) NOT NULL,
  "ativo" bool NOT NULL,
  "version" int8 NOT NULL,
  PRIMARY KEY ("id")
);