(** nom = "Nsengimana Francois";;
let age = 20;;
let moyenne = 14.5;;

Printf.printf "Nom : %s\nAge : %d\nMoyenne : %f\n" nom age moyenne;;
Printf.printf "L'étudiant %s a %d ans et une moyenne de %g\n" nom age moyenne;;


let moyenne = ref 10;;

moyenne := 15;;

print_int !moyenne;;

let () =
  print_string "Entrez la note 1 : ";
  let n1 = float_of_string (read_line ()) in
  print_string "Entrez la note 2 : ";
  let n2 = float_of_string (read_line ()) in
  let moyenne = (n1 +. n2) /. 2.0 in
  Printf.printf "Moyenne = %g\n" moyenne;
  if moyenne >= 10.0 then
    print_endline "Admis"
  else
    print_endline "Ajourné"
;;
**)

let calculer_moyenne n1 n2 =
  let somme = n1 +. n2 in
  somme /. 2.0
;;

let resultat = calculer_moyenne 12.0 8.0;;
Printf.printf "Résultat du test : %g\n" resultat;;