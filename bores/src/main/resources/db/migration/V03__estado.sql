CREATE TABLE "estado" (
  "id" serial8,
  "nome" varchar(30) NOT NULL,
  "sigla" varchar(5) NOT NULL,
  "version" int4 NOT NULL,
  PRIMARY KEY ("id")
);