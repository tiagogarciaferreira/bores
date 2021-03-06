CREATE TABLE "cidade" (
  "id" serial8,
  "nome" varchar(35) NOT NULL,
  "estado_id" int8 NOT NULL,
  "version" int4 NOT NULL,
  PRIMARY KEY ("id")
);

ALTER TABLE "cidade" ADD CONSTRAINT "fk_estado_to_cidade" FOREIGN KEY ("estado_id") REFERENCES "estado" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;