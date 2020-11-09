CREATE TABLE "estado" (
  "id" serial8,
  "nome" varchar(100) NOT NULL,
  "sigla" varchar(5) NOT NULL,
  "version" int8 NOT NULL,
  PRIMARY KEY ("id")
);