
---

````markdown
# 📘 GUIDE COMPLET — LOGS SOUS LINUX

## 🎯 Objectif
Comprendre, analyser et exploiter les logs Linux pour :
- diagnostiquer des problèmes
- surveiller un système
- sécuriser un serveur
- devenir autonome comme un admin système

---

# 1. 🧠 DÉFINITION

## 🔹 Qu’est-ce qu’un log ?
Un **log (journal)** est un **fichier qui enregistre les événements du système et des applications**.

### 📌 Exemples d’événements :
- connexion utilisateur
- erreur système
- démarrage d’un service
- tentative de hack

---

# 2. ❓ POURQUOI LES LOGS SONT IMPORTANTS

## 🔐 Sécurité
- détecter des intrusions
- analyser des tentatives SSH
- tracer les actions utilisateurs

## 🛠️ Debug / Maintenance
- comprendre une erreur
- diagnostiquer une panne
- analyser un crash

## 📊 Monitoring
- suivre l’activité du système
- surveiller les services

---

# 3. 📂 OÙ SE TROUVENT LES LOGS

### 📍 Dossier principal :
```bash
/var/log/
````

## 📁 Fichiers importants

| Fichier  | Description                  |
| -------- | ---------------------------- |
| syslog   | logs généraux                |
| auth.log | authentification (SSH, sudo) |
| kern.log | noyau Linux                  |
| dmesg    | démarrage                    |
| nginx/   | logs web                     |
| apache2/ | logs Apache                  |

---

# 4. 📚 TYPES DE LOGS

## 🔹 1. Logs système

* fonctionnement global
* services
* erreurs système

## 🔹 2. Logs sécurité

* connexions SSH
* sudo
* authentification

## 🔹 3. Logs applicatifs

* nginx
* mysql
* docker

---

# 5. 🧾 STRUCTURE D’UN LOG

### Exemple :

```
Apr 26 12:00:01 server sshd[2222]: Failed password for root from 192.168.1.10 port 54321 ssh2
```

### Analyse :

* Date : Apr 26 12:00:01
* Machine : server
* Service : sshd
* PID : 2222
* Action : Failed password
* Utilisateur : root
* IP : 192.168.1.10

---

# 6. 🧰 COMMANDES À CONNAÎTRE PAR CŒUR

## 📖 Lecture

```bash
cat /var/log/syslog
less /var/log/syslog
head /var/log/syslog
tail /var/log/syslog
tail -n 20 /var/log/syslog
```

## 🔴 Temps réel

```bash
tail -f /var/log/syslog
```

---

## 🔍 Recherche

```bash
grep "error" /var/log/syslog
grep -i "error" /var/log/syslog
grep "ssh" /var/log/syslog
```

---

## 🔗 Combinaisons puissantes

```bash
tail -f /var/log/syslog | grep ssh
tail -f /var/log/syslog | grep error
```

---

## ⚙️ journalctl (systemd)

```bash
journalctl
journalctl -n 50
journalctl -f
journalctl -u ssh
journalctl --since today
journalctl --since "1 hour ago"
```

---

# 7. 🔐 ANALYSE SÉCURITÉ

## ❌ Tentatives échouées

```bash
grep "Failed password" /var/log/auth.log
```

## ✅ Connexions réussies

```bash
grep "Accepted" /var/log/auth.log
```

## 🔑 Commandes sudo

```bash
grep "sudo" /var/log/auth.log
```

---

# 8. 🔄 ROTATION DES LOGS

## 📌 Pourquoi ?

* éviter disque plein

## 📌 Outil :

```bash
logrotate
```

## 📌 Configuration :

```bash
/etc/logrotate.conf
```

---

# 9. 🧠 VOCABULAIRE ESSENTIEL

| Terme      | Définition            |
| ---------- | --------------------- |
| Log        | fichier de trace      |
| Event      | événement             |
| Monitoring | surveillance          |
| Debug      | analyse d’erreur      |
| Audit      | traçabilité           |
| Syslog     | logs système          |
| Auth       | authentification      |
| Service    | programme système     |
| PID        | identifiant processus |
| Real-time  | temps réel            |

---

# 10. ⚡ WORKFLOW ADMIN (RÉEL)

Quand un problème survient :

1. Identifier le service (ssh, nginx…)
2. Aller dans `/var/log/`
3. Lancer :

   ```bash
   tail -f fichier.log
   ```
4. Filtrer :

   ```bash
   grep erreur
   ```
5. Analyser
6. Corriger

---

# 11. 🧪 TP QUOTIDIEN (PRATIQUE)

## 🔰 Jour 1

* lire syslog avec `less`
* chercher "error"

## 🔰 Jour 2

* utiliser `tail -n 50`
* utiliser `head`

## 🔰 Jour 3

* tester `tail -f`
* ouvrir 2 terminaux

## 🔰 Jour 4

* filtrer avec `grep`
* tester maj/min (-i)

## 🔰 Jour 5

* analyser auth.log
* trouver connexions SSH

## 🔰 Jour 6

* identifier tentative hack
* repérer IP suspecte

## 🔰 Jour 7

* utiliser `journalctl`
* filtrer par service

---

# 12. 🎯 EXERCICES

## Niveau 1

1. Définir un log
2. Donner 3 types de logs
3. Où sont stockés les logs ?

## Niveau 2

4. Voir les 20 dernières lignes
5. Chercher "error"
6. Ouvrir avec navigation

## Niveau 3

7. Voir logs SSH en temps réel
8. Trouver connexions échouées
9. Trouver connexions réussies

## Niveau 4 (PRO)

10. Identifier une attaque brute force
11. Trouver IP suspecte
12. Expliquer un log complet

---

# 13. 🚀 OBJECTIF FINAL

Tu dois être capable de :

✔ comprendre un log
✔ détecter une erreur
✔ surveiller un système
✔ analyser une attaque
✔ travailler comme un sysadmin

---

# 14. 🔥 RÉSUMÉ FINAL

* log = trace du système
* `/var/log/` = emplacement
* `tail -f` = temps réel
* `grep` = filtre
* `journalctl` = analyse avancée

---

# 💡 CONSEIL FINAL

👉 Ne lis pas seulement
👉 **PRATIQUE TOUS LES JOURS**

C’est comme ça que tu deviens fort en Linux.

