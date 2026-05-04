Note de cours Arduino



2- PIC 16FB77

Nous allons utiliser PIC 16FB77



1\. La configuration minimale du PIC 16F877

Le PIC 16F877 contient 5 broches

&#x20;Broche A : 6 port

&#x20;Broche B : 8 port

&#x20;Broche C : 8 port

&#x20;Broche D : 8 port

&#x20;Broche E : 3 port

Contient 8 suceptible d'accepter les entre analogique

les voici :



RA0-->AN0

RA1-->AN1

RA4-->AN2

RA4-->AN3

RA5-->AN4

RA0-->AN5

RE1-->AN6

RE2-->AN7





2\. la programmation de PIC 16F877

Les du langage C adapter au microcontrole PIC



Les fonction de langage C adaptees au microcontroleur PIC.



a- Affectation de sorties numeriques en peut utiliser plusieurs fonction

Fonction 1 :

b- temporisation

Pour faire de la temporisation ou temporiser en utilise la fonction Delay

exemple :

Delay\_ms(700) : Temporisation de 700 ms

Delay\_us(800); Temporisation de microseconde





Le microcontrôleur traite les information et donne les instruction au programme pour faire des action allumage éteindre.(Ces comme l'unite centrale)

La  resistance : Son role est de protéger LED, elle permet de limite l'intensité traversant la LED







Zone 1

1 ere zone zone de la directive du processeur

dans cette zone chaque ligne commence par #

Et se ne termine pas par un point virgule (;)



**Ligne #include<16F877.h>**

**Permet de introduire la libraire ou bibliothèque pour 16F877.h donc il permet de faire appelle au fonction du microcontrôleur utiliser .**



**Cette fonction permet d'introduire la fréquence d'oscillation**

**Ces le temps necessaire a un microcontrôleur donner a un microprocesseur pour comprendre les instructions.**



Ligne 3

Cette fonction permet de introduire la fréquence fusible

Ces la fréquence lie a l'alimentation du circuit

Lorsqu'on utilise le fusible de HS doit fox>= 16MHz



Ces  trois ligne son obligatoire pour chaque programme





La Zone 2 ses zone sont obligatoire



Zone de declaration de variable

Pour variable d'entrer comme les interrupteur en utilise la fonction int

Pour capteur, clavier, utilise la fonction float

Pour variable de type text, chaine de caractère ,... en utilise char





La zone 3 de programme principale :

Remarque :

Lorsque ce programme est televerser dans un microcontroleur , il execute une seul fois meme s'il ya des modification

. Pour resoudre ce probleme en doit ajouter une boucle qui est la boucle while le contenue de la boucle vas t'être répéter tant la condition est juste





Out fonction permet de activer ou désactiver plusieurs ports au meme moment

output\_B(0b10101011); permet d'activer les ports B0, B1, B3, B5, B7 et desactive B6, B4, B2



O desactiver

1 activer





