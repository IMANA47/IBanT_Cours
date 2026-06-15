type produit = {
  nom : string;
  quantite : int;
}

let stock = ref []

let ajouter_produit () =
  print_string "Nom du produit : ";
  let nom = read_line () in

  print_string "Quantité : ";
  let quantite = read_int () in
  ignore (read_line ());

  stock := { nom; quantite } :: !stock;
  print_endline "Produit ajouté !"

let afficher_stock () =
  if !stock = [] then
    print_endline "Stock vide."
  else begin
    print_endline "\n=== STOCK ===";
    List.iter
      (fun p ->
        Printf.printf "Produit : %s | Quantité : %d\n"
          p.nom p.quantite)
      !stock
  end

let rechercher_produit () =
  print_string "Nom du produit à rechercher : ";
  let nom = read_line () in

  match List.find_opt (fun p -> p.nom = nom) !stock with
  | Some p ->
      Printf.printf
        "Produit trouvé : %s | Quantité : %d\n"
        p.nom p.quantite
  | None ->
      print_endline "Produit introuvable."

let rec menu () =
  print_endline "\n===== GESTION DE STOCK =====";
  print_endline "1. Ajouter produit";
  print_endline "2. Afficher stock";
  print_endline "3. Rechercher produit";
  print_endline "4. Quitter";

  print_string "Choix : ";
  let choix = read_int () in
  ignore (read_line ());

  match choix with
  | 1 ->
      ajouter_produit ();
      menu ()
  | 2 ->
      afficher_stock ();
      menu ()
  | 3 ->
      rechercher_produit ();
      menu ()
  | 4 ->
      print_endline "Au revoir !"
  | _ ->
      print_endline "Choix invalide.";
      menu ()

let () =
  menu ()