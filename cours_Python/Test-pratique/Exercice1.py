"""
4. Convertisseur de températures
Menu : 1. Celsius → Fahrenheit, 2. Fahrenheit → Celsius.
L’utilisateur choisit, entre la valeur, affiche le résultat.
Boucle jusqu’à ce que l’utilisateur tape « quitter ».
"""

def celsius_to_fahrenheit(celsius):
    return (celsius * 9/5) + 32

def fahrenheit_to_celsius(fahrenheit):
    return (fahrenheit - 32) * 5/9

choix_valeur = input("Entre une valeurs entier : ")
choix_valeur_float = float(choix_valeur)



while True:
    choix_menu = int(input("""
                Menu choix pour convertir :
                1. Celsius → Fahrenheit
                2. Fahrenheit → Celsius.
                """))
    if choix_menu == 1:
        celsius = celsius_to_fahrenheit(choix_valeur_float)
        print(f"La valeurs de converstion de 1. Celsius → Fahrenheit est {celsius}")
        continue
        
    elif choix_menu == 2:
        fahrenheit = fahrenheit_to_celsius(choix_valeur_float)
        print(f"La valeurs de converstion de 2. Fahrenheit → Celsius est {fahrenheit}")
        continue
        
    elif choix_menu == "quitter":
        break
    else:
        return choix_menu

