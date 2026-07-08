(**fait moi ces exercices : Exercice pratique OCaml — Fondamentaux
Exercice : Gestion des notes d’étudiants
Partie 1 — Premier programme
Créer un fichier etudiant.ml contenant le programme suivant :*)

print_endline "Bienvenue dans le programme de gestion des notes !";;
(**Travail demandé
    1. Compiler et exécuter le programme.
    2. Expliquer le rôle de :
        ◦ print_endline
        ◦ ;;

Partie 2 — Déclarations et variables
Déclarer les variables suivantes :
let nom = "Paul";;
let age = 20;;
let moyenne = 14.5;;
Travail demandé
    1. Afficher toutes les variables.
    2. Afficher la phrase :
L'étudiant Paul a 20 ans et une moyenne de 14.5

Partie 3 — Références
Créer une référence permettant de modifier une moyenne.
let moyenne = ref 10;;
Travail demandé
    1. Afficher la valeur de la moyenne.
    2. Modifier la moyenne à 15.
    3. Réafficher la nouvelle valeur.
Indication
:=     (* modification *)
!      (* lecture *)

Partie 4 — Expressions et instructions
Écrire un programme qui :
    1. demande deux notes ;
    2. calcule leur moyenne ;
    3. affiche si l’étudiant est :
        ◦ Admis (moyenne ≥ 10)
        ◦ Ajourné (moyenne < 10)
Exemple attendu
Entrez la note 1 :
12
Entrez la note 2 :
8
Moyenne = 10
Admis

Partie 5 — Variables locales
Écrire une fonction :
let calculer_moyenne n1 n2 =
qui utilise une variable locale pour calculer la moyenne.
Exemple attendu
let calculer_moyenne n1 n2 =
  let somme = n1 +. n2 in
  somme /. 2.0;;
Travail demandé
    1. Tester la fonction.
    2. Expliquer le rôle de in.

Partie 6 — Fonctions
6.1 Fonction simple
Créer une fonction :
let carre x = x * x;;
Travail demandé
Tester :
carre 5;;

6.2 Fonction de première classe
Créer :
let addition x y = x + y;;
Puis :
let operation f a b = f a b;;
Travail demandé
    1. Tester :
operation addition 2 3;;
    2. Créer une fonction multiplication et la tester avec operation.

Partie 7 — Fonctions récursives
Créer une fonction récursive calculant la factorielle.
Formule
n! = n \times (n-1)!
Code à compléter
let rec factorielle n =
  if n = 0 then 1
  else ...
;;
Travail demandé
    1. Compléter la fonction.
    2. Tester :
        ◦ factorielle 5
        ◦ factorielle 7

Partie 8 — Polymorphisme
Créer une fonction polymorphe qui retourne le premier élément d’une liste.
Exemple
let premier l =
  List.hd l;;
Travail demandé
Tester avec :
premier [1;2;3];;
premier ["a";"b";"c"];;
Question
Pourquoi cette fonction est-elle dite polymorphe ?
