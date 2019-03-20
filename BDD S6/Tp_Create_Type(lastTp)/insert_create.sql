insert into TP10_Quiz values (10, 'Quel genre de casse-pieds êtes-vous ?',
								sysdate,
								symboles_type('Vous êtes autoritaire',
								'VOus êtes victime', 'etc' , 'etc'),
								ens_questions_type()
								);

Select ques.le_quiz.id_quiz , ques.le_quiz.titre, ques.numero, ques.enonce
from TP10_question ques;