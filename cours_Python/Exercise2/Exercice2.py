"""
Autor : Nsengimana Francois
Level : Licence 2
Speciality : Developpement Application
Date : 22/05/2026

SUPER U propose une réduction sur le montant total d'un achat selon
les conditions suivantes :


Écrire un programme qui :

1. Demande à l'utilisateur de saisir le nombre d'articles achetés.
2.Utilise une boucle for pour saisir le prix de chaque article.
3.Calcule le montant total.
4.Utilise une structure if / elif / else pour déterminer le taux de
réduction applicable.

5.Affiche :
le montant total avant réduction ;
le pourcentage de réduction appliqué ;
le montant à payer après réduction.


"""

montant_total = 0
nombre_articles_achete = int(input("Saisir le nombre d'articles achetés : "))

for i in range(nombre_articles_achete):
    prix_articles = float(input(f"Donne le prix de chaque article {i + 1}: "))
    montant_total += prix_articles

    if montant_total < 5000:
        pourcentage_reduction = 0
    elif montant_total >= 5000 <= 10000:
        pourcentage_reduction = 5
                
    elif 10100 <= montant_total <=20000:
        pourcentage_reduction = 10
        
    elif 20100<= montant_tota <=50000:
        pourcentage_reduction = 15
        
    elif somme >50000:
        pourcentage_reduction = 20
    
    montant_apres_reduction = montant_total - (montant_total * pourcentage_reduction / 100)   


# Affichage
print(f"Montant total avant réduction : {montant_total}")
print(f"Pourcentage de réduction appliqué : {pourcentage_reduction}%")
print(f"Montant à payer après réduction : {montant_apres_reduction}")