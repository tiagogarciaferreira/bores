CREATE TABLE "endereco" (
  "id" serial8,
  "cep" varchar(12) NOT NULL,
  "uf" varchar(30) NOT NULL,
  "cidade_id" int8 NOT NULL,
  "logradouro" varchar(80) NOT NULL,
  "numero" varchar(10) NOT NULL,
  "complemento" varchar(100),
  "cliente_id" int8 NOT NULL,
  "version" int4 NOT NULL,
  PRIMARY KEY ("id")
);

ALTER TABLE "endereco" ADD CONSTRAINT "fk_cliente_to_endereco" FOREIGN KEY ("cliente_id") REFERENCES "cliente" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE "endereco" ADD CONSTRAINT "fk_cidade_to_endereco" FOREIGN KEY ("cidade_id") REFERENCES "cidade" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;