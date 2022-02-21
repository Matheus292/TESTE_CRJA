-- Montar select que retorne o nome do departamento, 
-- quantidade de tarefas finalizadas e 
-- quantidade de tarefas não finalizadas

SELECT d.titulo as DEPARTAMENTO,
(SELECT COUNT(tf.id) FROM tarefa tf WHERE tf.id_departamento = d.id AND tf.finalizado = true ) as QTD_FINALIZADA,
(SELECT COUNT(tf.id) FROM tarefa tf WHERE tf.id_departamento = d.id AND tf.finalizado = false ) AS QTD_NAO_FINALIZADA
FROM departamento d;

--------------------------------------------------------------


-- Retornar a pessoa que mais gastou horas em janeiro de 2022
SELECT person.nome as NOME, SUM(tf.duracao) AS QTD_GASTA FROM PESSOA person with(nolock)
INNER JOIN TAREFA tf
ON person.id = tf.id_pessoa
WHERE EXTRACT(MONTH FROM tf.prazo) = 1 AND EXTRACT(YEAR FROM tf.prazo) = 2022
GROUP BY person.id
ORDER BY QTD_GASTA DESC
limit 1

--------------------------------------------------------------


-- Select que retorne título da tarefa, prazo, se tiver pessoa alocada na tarefa
-- exibir como "Encaminhado para + nome da pessoa" caso contrário "Pendente" e total
-- de horas que essa pessoa já gastou. Ordenar por prazo decrescente

SELECT tf.titulo as TITULO,
tf.prazo as PRAZO,
tf.duracao as TEMPO_GASTO,
CASE
	WHEN tf.id_pessoa ISNULL THEN 'Pendente'
	ELSE CONCAT('Encaminhado para ', (SELECT person.nome from pessoa person where person.id = tf.id_pessoa limit 1) )
END AS RESPONSAVEL
FROM TAREFA tf
ORDER BY PRAZO DESC