CREATE TABLE "produto" (
  "id" serial8,
  "nome" varchar(50) NOT NULL,
  "sku" varchar(10) NOT NULL,
  "valor_unitario" numeric(10,2) NOT NULL,
  "quantidade_estoque" int4 NOT NULL,
  "subcategoria_id" int8 NOT NULL,
  "version" int8 NOT NULL,
  PRIMARY KEY ("id")
);

ALTER TABLE "produto" ADD CONSTRAINT "fk_categoria_to_subcategoria" FOREIGN KEY ("subcategoria_id") REFERENCES "subcategoria" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;