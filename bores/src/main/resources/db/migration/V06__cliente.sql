CREATE TABLE "cliente" (
  "id" serial8,
  "nome" varchar(80) NOT NULL,
  "telefone" varchar(20) NOT NULL,
  "email" varchar(40) NOT NULL,
  "cpf_cnpj" varchar(20) NOT NULL,
  "tipo_pessoa" varchar(10) NOT NULL,
  "version" int4 NOT NULL,
  PRIMARY KEY ("id")
);