CREATE TABLE "grupo_permissao" (
  "grupo_id" int8 NOT NULL,
  "permissao_id" int8 NOT NULL
);

ALTER TABLE "grupo_permissao" ADD CONSTRAINT "fk_grupo_to_grupo_permissao" FOREIGN KEY ("grupo_id") REFERENCES "grupo" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE "grupo_permissao" ADD CONSTRAINT "fk_permissao_to_grupo_permissao" FOREIGN KEY ("permissao_id") REFERENCES "permissao" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;