""" La methode avec match-case """

solde_compte =0
print("")
print("===== Mini distributeur Bancaire =====")

choix_service = input("""
1- Voir son solde
2- Deposer de l'argent
3- Retirer de l'argent
4- Quitter le programme

Veuillez choisir un service :
""")

match choix_service:
    case "1":
        print("Votre solde est de : ", solde_compte)
    
    case "2":
        montant_depot = float(input("Entrez le montant à déposer : "))
        solde_compte += montant_depot
        print(f"Vous avez déposé {montant_depot} FCFA et votre dépôt a été effectué avec succès.")
    
    case "3":
        montant_retrait = float(input("Entrez le montant à retirer : "))
        if 0 < montant_retrait <= solde_compte:
            solde_compte -= montant_retrait
            print(f"Vous avez retiré {montant_retrait} FCFA et votre retrait a été effectué avec succès.")
        else:
            print("Montant insuffisant pour le retrait.")
    
    case "4":
        print("Merci d'avoir utilisé notre service. Au revoir!")
    
    case _:
        print("Choix invalide. Veuillez réessayer.")