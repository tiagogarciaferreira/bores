CREATE TABLE "pedido" (
  "id" serial8,
  "status" varchar(15) NOT NULL,
  "data_criacao" timestamp(0) NOT NULL,
  "valor_frete" numeric(10,2) NOT NULL,
  "valor_desconto" numeric(10,2) NOT NULL,
  "valor_total" numeric(10,2) NOT NULL,
  "forma_pagamento" varchar(30) NOT NULL,
  "data_entrega" timestamp(0) NOT NULL,
  "cliente_id" int8 NOT NULL,
  "vendedor_id" int8 NOT NULL,
  "observacao" varchar(100),
  "uf" varchar(30) NOT NULL,
  "cep" varchar(12) NOT NULL,
  "cidade_id" int8 NOT NULL,
  "logradouro" varchar(80) NOT NULL,
  "numero" varchar(10) NOT NULL,
  "complemento" varchar(100),
  "version" int4 NOT NULL,
  PRIMARY KEY ("id")
);

ALTER TABLE "pedido" ADD CONSTRAINT "fk_cliente_to_pedido" FOREIGN KEY ("cliente_id") REFERENCES "cliente" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE "pedido" ADD CONSTRAINT "fk_usuario_to_pedido" FOREIGN KEY ("vendedor_id") REFERENCES "usuario" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE "pedido" ADD CONSTRAINT "fk_cidade_to_pedido" FOREIGN KEY ("cidade_id") REFERENCES "cidade" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;