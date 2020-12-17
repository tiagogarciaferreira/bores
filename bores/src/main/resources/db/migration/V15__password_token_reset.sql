CREATE TABLE "password_reset" (
  "id" serial8,
  "token" varchar(50) NOT NULL,
  "usuario_id" int8 NOT NULL,
  "data_validade" timestamp(0) NOT NULL,
  PRIMARY KEY ("id")
);

ALTER TABLE "password_reset" ADD CONSTRAINT "fk_usuario_to_password_token" FOREIGN KEY ("usuario_id") REFERENCES "usuario" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;

