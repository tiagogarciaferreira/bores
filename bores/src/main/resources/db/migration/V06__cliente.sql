CREATE TABLE "cliente" (
  "id" serial8,
  "nome" varchar(100) NOT NULL,
  "telefone" varchar(20) NOT NULL,
  "email" varchar(50) NOT NULL,
  "cpf_cnpj" varchar(20) NOT NULL,
  "tipo_pessoa" varchar(10) NOT NULL,
  "version" int8 NOT NULL,
  PRIMARY KEY ("id")
);