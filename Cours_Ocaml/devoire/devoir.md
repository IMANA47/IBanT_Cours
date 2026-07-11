(* ==========================================================
   TP OCaml - Gestion des notes d'étudiants
   Auteur : Eliote Youris
   L2 - Licence Développement d'Applications, A HCG Kongo
   Fichier complet : etudiant_complet.ml
   ========================================================== *)

(* ========== PARTIE 1 : Premier programme ========== *)
print_endline "Bienvenue dans le programme de gestion des notes !!";;

(* ========== PARTIE 2 : Déclarations et variables ========== *)
let nom = "Paul";;
let age = 20;;
let moyenne = 14.5;;

(* 1. Afficher toutes les variables *)
let _ = Printf.printf "Nom : %s\nAge : %d\nMoyenne : %f\n" nom age moyenne;;

(* 2. Afficher la phrase complète *)
let _ = Printf.printf "L'étudiant %s a %d ans et une moyenne de %g\n" nom age moyenne;;

(* ========== PARTIE 3 : Références (mutabilité) ========== *)
let moyenne_ref = ref 10;;

(* 1. Afficher la valeur *)
let _ = Printf.printf "Valeur initiale de la référence : %d\n" !moyenne_ref;;

(* 2. Modifier la moyenne à 15 *)
let _ = moyenne_ref := 15;;

(* 3. Réafficher la nouvelle valeur *)
let _ = Printf.printf "Nouvelle valeur de la référence : %d\n" !moyenne_ref;;

(* ========== PARTIE 4 : Expressions, instructions et condition ========== *)
(* Fonction qui demande deux notes, calcule la moyenne et affiche Admis/Ajourné *)
let programme_notes () =
  print_string "Entrez la note 1 : ";
  let n1 = float_of_string (read_line ()) in
  print_string "Entrez la note 2 : ";
  let n2 = float_of_string (read_line ()) in
  let moy = (n1 +. n2) /. 2.0 in
  Printf.printf "Moyenne = %g\n" moy;
  if moy >= 10.0 then
    print_endline "Admis"
  else
    print_endline "Ajourné"
;;

(* Exécution automatique de la Partie 4 (décommentez la ligne ci-dessous pour tester) *)
(* let _ = programme_notes ();; *)

(* ========== PARTIE 5 : Variables locales ========== *)
let calculer_moyenne n1 n2 =
  let somme = n1 +. n2 in   (* Variable locale *)
  somme /. 2.0
;;

(* Test de la fonction *)
let _ = Printf.printf "Test calculer_moyenne (12.0, 8.0) = %g\n" (calculer_moyenne 12.0 8.0);;

(* ========== PARTIE 6 : Fonctions ========== *)
(* 6.1 Fonction simple *)
let carre x = x * x;;
let _ = Printf.printf "carre 5 = %d\n" (carre 5);;

(* 6.2 Fonction de première classe *)
let addition x y = x + y;;
let operation f a b = f a b;;

(* Test avec addition *)
let _ = Printf.printf "operation addition 2 3 = %d\n" (operation addition 2 3);;

(* Création de multiplication et test *)
let multiplication x y = x * y;;
let _ = Printf.printf "operation multiplication 2 3 = %d\n" (operation multiplication 2 3);;

(* ========== PARTIE 7 : Fonctions récursives (Factorielle) ========== *)
let rec factorielle n =
  if n = 0 then 1
  else n * factorielle (n - 1)
;;

(* Tests *)
let _ = Printf.printf "factorielle 5 = %d\n" (factorielle 5);;
let _ = Printf.printf "factorielle 7 = %d\n" (factorielle 7);;

(* ========== PARTIE 8 : Polymorphisme ========== *)
let premier l =
  List.hd l
;;

(* Tests *)
let _ = Printf.printf "premier [1;2;3] = %d\n" (premier [1;2;3]);;
let _ = Printf.printf "premier [\"a\";\"b\";\"c\"] = %s\n" (premier ["a";"b";"c"]);;

(* Fin du programme *)
let _ = print_endline "\n--- Tous les tests sont terminés avec succès. ---";;