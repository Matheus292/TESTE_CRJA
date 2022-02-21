# TESTE_CRJA

Requisitos:

- A API deve ser REST

- Cada pessoa terá um id, nome, departamento e  lista de tarefas

- Cada tarefa terá id, título, descrição, prazo, departamento, duração, pessoa alocada e se já foi finalizado.


Funcionalidades desejadas:


- Adicionar um pessoa (post/pessoas)


- Alterar um pessoa (put/pessoas/{id})


- Remover pessoa (delete/pessoas/{id})


- Adicionar um tarefa (post/tarefas)


- Alocar uma pessoa na tarefa que tenha o mesmo departamento (put/tarefas/alocar/{id})


- Finalizar a tarefa (put/tarefas/finalizar/{id})


- Listar pessoas trazendo nome, departamento, total horas gastas nas tarefas.(get/pessoas)


- Buscar pessoas por nome e período, retorna média de horas gastas por tarefa. (get/pessoas/gastos)


- Listar 3 tarefas que estejam sem pessoa alocada com os prazos mais antigos. (get/tarefas/pendentes)


- Listar departamento e quantidade de pessoas e tarefas (get/departamentos)


OBS: A linguagem para realização do desafio deve ser em Java.

Framework SprintBoot ou Quarkus.

Bancos de dados: MongoDB, PostgreSQL.


E lembre-se! Um bom software é um software bem testado.


Por último criar alguns select de consolidação de dados


- Montar select que retorne nome do departamento, quantidade de tarefas finalizadas e quantidade de tarefas não finalizadas


- Retornar a pessoa que mais gastou horas em janeiro de 2022


Select que retorne título da tarefa, prazo, se tiver pessoa alocada na tarefa exibir como “Encaminhado para + nome do pessoa” caso contrário “Pendente” e total de horas que essa pessoa já gastou. Ordenar por prazo decrescente. 

### Querys
As querys estão no arquivo querys.sql
