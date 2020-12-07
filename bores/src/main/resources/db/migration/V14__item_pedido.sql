CREATE TABLE "item_pedido" (
  "id" serial8,
  "quantidade" int4 NOT NULL,
  "valor_unitario" numeric(10,2) NOT NULL,
  "produto_id" int8 NOT NULL,
  "pedido_id" int8 NOT NULL,
  "version" int4 NOT NULL,
  PRIMARY KEY ("id")
);

ALTER TABLE "item_pedido" ADD CONSTRAINT "fk_produto_to_item_pedido" FOREIGN KEY ("produto_id") REFERENCES "produto" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE "item_pedido" ADD CONSTRAINT "fk_pedido_to_item_pedido" FOREIGN KEY ("pedido_id") REFERENCES "pedido" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;