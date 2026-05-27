"""
Autor : Nsengimana Francois
Level : Licence 2
Speciality : Developpement Application
Date : 22/05/2026

IBANT attribue une mention à ses étudiants selon la moyenne obtenue :

Écrire un programme qui :

1. Demande à l'utilisateur de saisir le nombre d'étudiants.
2.Utilise une boucle for pour saisir, pour chaque étudiant :
son nom ;
sa moyenne.

3.Utilise une structure match-case (basée sur la partie entière de la
moyenne, par exemple int(moyenne)) pour déterminer la mention
correspondante.

4.Affiche un tableau récapitulatif par exemple :
ressemble a ca : +----------------+----------+-------------+
| Nom            
| Moyenne  | Mention     |
+----------------+----------+-------------+
| Jean         
| 15.5     
| Synthia         
| Bien        
|
| 9.75     | Ajourné     |
| Eden        
| 17.25    | Très bien   |
+----------------+----------+-------------
Indice : dans le match, vous pouvez utiliser des motifs
comme case 10 | 11: pour regrouper plusieurs valeurs

"""

#1. Demande à l'utilisateur de saisir le nombre d'étudiants.
nombre_etudiants = int(input("Veillez de saisir le nombre d'étudiants : "))

#2. Utilise une boucle for pour saisir, pour chaque étudiant : son nom ; sa moyenne.
info_etudiants = []
for i in range(nombre_etudiants):
    name_student = input(f"Saisir le nom de l'étudiant {i + 1}: ")
    moyen_student = float(input(f"Saisir la moyenne de l'étudiant {i + 1}: "))
    info_etudiant = [name_student, moyen_student]
    info_etudiants.append(info_etudiant)

"""
3.Utilise une structure match-case (basée sur la partie entière de la
moyenne, par exemple int(moyenne)) pour déterminer la mention
correspondante.

"""

liste_mentions = []

for name, moyen_student in info_etudiants:
    match moyen_student:
        case x if x < 10:
            mention = "Ajourné"
        case x if 10 <= x <= 11.99:
            mention = "Passable"
        case x if 12 <= x <= 13.99:
            mention = "Assez bien"
        case x if 14 <= x <= 15.99:
            mention = "Bien"
        case x if 16 <= x <= 17.99:
            mention = "Très bien"
        case x if 18 <= x <= 20:
            mention = "Excellent"
    liste_mentions.append([name, moyen_student, mention])

#4. Affiche un tableau récapitulatif.
print("+----------------+----------+-------------+")
print("| Nom             | Moyenne| Mention     |")
print("+----------------+----------+-------------+")
for name, moyen_student, mention in liste_mentions:
    print(f"| {name:<16} | {moyen_student:<10} | {mention:<9} |")
print("+----------------+----------+-------------+")