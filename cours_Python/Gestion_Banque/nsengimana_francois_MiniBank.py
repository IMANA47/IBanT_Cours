"""
Autor : Nsengimana Francois
Level : Licence 2
Speciality : Developpement Application
Date : 14/05/2026
Titre : Mini distributeur Bancaire

Bon pour le francais bien soigner j'ai utiliser reverso pour la correction expression.

Voici les ressources que j'ai utilisé pour la réalisation de ce projet :
1- Documentation Python : https://docs.python.org/3/
2- Reverso pour la correction de l'expression française : https://www.reverso.net/orthographe/correcteur-francais/
3- Et la lecture et relecture de votre cours de Python pour m'assurer de bien comprendre les concepts nécessaires à la réalisation de ce projet.
4- Donc si vous avez des questions voici mon adresse email : francknsengimana@gmail.com  

"""
"""
Vos consignes : 
Exercises :   
1- réflechissez sur la position d'incrémentation dans une boucle while avec l'expression break ou continue.
2 -Mini distributeur Bancaire                                                
 objectif : crée un mini système Bancaire ou l'utilisateur peut:   
1- voir son solde                                                                                                                           
2 - Deposer de l'argent                                                                           
3- Retirer de l'argent                                                                                  
4- Quitter le programme                                                                                        
*  à respecter Personne à le droit de faire recourt à chatgpt ou autres IA                                                                                     
    Ex: nom du fichier: votre_nom_MiniBank.format

Juste une information supplémentaire concernant l’exercice « Mini distributeur bancaire » : veuillez utiliser les deux méthodes :
1- La structure conditionnelle classique ;
2- Les match-case.

"""

solde_compte =0
print("Mini distributeur Bancaire")
choix_service = input("""
1- voir son solde
2- Deposer de l'argent
3- Retirer de l'argent
4- Quitter le programme

Veuillez choisir un service :
""")

while True:
    if choix_service == "1":
        print("Votre solde est de : ", solde_compte)

# si l'utilisateur choisit 2
    elif choix_service == "2":
        montant_depot = float(input("Entrez le montant à déposer : "))
        solde_compte += montant_depot
        print(f"Vous avez déposé {montant_depot} FCFA et votre dépôt a été effectué avec succès.")

# si l'utilisateur choisit 3
    elif choix_service == "3":
        montant_retrait = float(input("Entrez le montant à retirer : "))
        if 0 < montant_retrait <= solde_compte:
            solde_compte -= montant_retrait
            print(f"Vous avez retiré {montant_retrait} FCFA et votre retrait a été effectué avec succès.")
        else:
            print("Montant insuffisant pour le retrait.")

# si l'utilisateur choisit 4
    elif choix_service == "4":
        print("Merci d'avoir utilisé notre service. Au revoir!")
        break
    else:
        print("Choix invalide. Veuillez réessayer.")
    
# Demander à l'utilisateur de choisir un service à nouveau
    choix_service = input("""1- voir son solde
2- Deposer de l'argent
3- Retirer de l'argent
4- Quitter le programme

Veuillez choisir un service : """)

"""Fin de la méthode avec la structure conditionnelle classique""" 