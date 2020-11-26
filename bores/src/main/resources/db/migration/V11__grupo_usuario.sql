CREATE TABLE "usuario_grupo" (
  "grupo_id" int8 NOT NULL,
  "usuario_id" int8 NOT NULL
);



ALTER TABLE "usuario_grupo" ADD CONSTRAINT "fk_usuario_to_usuario_grupo" FOREIGN KEY ("usuario_id") REFERENCES "usuario" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE "usuario_grupo" ADD CONSTRAINT "fk_grupo_to_usuario_grupo" FOREIGN KEY ("grupo_id") REFERENCES "grupo" ("id") ON DELETE RESTRICT ON UPDATE CASCADE;

