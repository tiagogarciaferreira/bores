CREATE TABLE "subcategoria" (
  "id" serial8,
  "nome" varchar(50) NOT NULL,
  "categoria_id" int8 NOT NULL,
  "version" int8 NOT NULL,
  PRIMARY KEY ("id")
);

ALTER TABLE "subcategoria" ADD CONSTRAINT "categoria_to_subcategoria" FOREIGN KEY ("categoria_id") REFERENCES "categoria" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;


