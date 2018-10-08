#!/usr/bin/env python3

# Nathanael Bayard
# Reseaux 2, L3 Info, 2018/2019

from math import floor

# version qui renvoie un dictionnaire
# elle marche mais elle est moins pratique
# pour l'affichage graphique
def occur_dict(string):
  string = string.upper()
  out = {}
  for letter in string:
    if letter in out:
      out[letter] += 1
    else:
      out[letter] = 1
  return out


# version qui renvoie un vecteur:
# out[0] == nombre d'occurence de A, etc
def occur_arr(string):
  string = string.upper()
  out = [0 for _ in range(26)]
  A = ord('A') # ascii valeur du caractere A pour reference
  for letter in string:
    i = ord(letter) - A # letter == A donne i == 0, etc
    out[i] += 1
  return out


# affiche un graphe en colonnes verticales
# representant les valeurs dans arr
# (en style ASCII evidemment)
def print_occur(arr):
  maxVal = floor(max(arr))
  for i in range(maxVal):
    # pour chaque ligne de haut en bas
    limit = maxVal - i
    for j in arr:
      # pour chaque colonne == chaque valeur dans arr
      if j >= limit:
        print("|", end="")
      else:
        print(" ", end="")
    print() # newline
  # legende des colonnes:
  for i in range(len(arr)):
    print(chr(i + ord('A')), end="")
  print()
  
# fonction qui chiffre/dechiffre une chaine cryptee
# avec un algorithme de type ROT
# par exemple ROT("ABC", 3) == "DEF"
# marche aussi pour rot <= 0
# cad ROT("DEF", -3) == "ABC"
def ROT(string, rot):
  out = ""
  string = string.upper()
  A = ord("A")
  for letter in string:
    i = ord(letter) - A
    newLetterAscii = (i + rot) % 26
    out += chr(newLetterAscii + A)
  return out



# ---------------------------------------------------
# ---------------------------------------------------
# ---------------------------------------------------
# ---------------------------------------------------



test_str = ("QZSINRFYNSQJRUJWJZWXFKJRRJJYQJUJYNYUWNSHJXTSYAJSZXHMJER"
  + "TNUTZWRJXJWWJWQFUNSHJRFNXHTRRJOJYFNXUFWYNQJUJYNYUWNSHJFINYUZNXVZJHJXYF"
  + "NSXNSTZXWJANJSIWTSXRFWIN")
print("\n-- Message secret :")
print(test_str)

# test de occur_dict()
# print(occur_dict(test_str))

results = occur_arr(test_str)
print("\n-- Valeurs d'occurrences pour le message secret :")
print(results)

# valeurs de reference de la langue francaise,
# pour comparaison graphique :
reference_occur = [
  8.25, 1.25, 3.25, 3.75, 17.75, 1.25, 1.25, 1.25, # a, b, c, d, e, f, g, h
  7.25, 0.75, 0.00, 5.75,  3.25, 7.25, 5.75, 3.25, # i, j, k, l, m, n, o, p
  1.25, 7.25, 8.25, 7.25,  6.25, 1.75, 0.00, 0.00, # q, r, s, t, u, v, w, x
  0.75, 0.00]                                      # y, z

print("\n-- Valeurs d'occurrences de reference pour la langue francaise :")
print(reference_occur)

# on affiche graphiquement les resultats obtenus
# en les comparant avec les valeurs de reference
print("\n-- Courbe d'occurrence pour le message secret :")
print_occur(results)
print("\n-- Courbe d'occurrence de reference pour la langue francaise :")
print_occur(reference_occur)

# manifestement, par observation graphique sur les appels de fonctions precedents,
# on deduit que test_str avait probablement ete chiffre avec l'algorithme ROT(5)
# on applique donc ROT(-5) == ROT(21) pour retrouver le message d'origine
clair = ROT(test_str, -5)
print("\n-- Message secret apres passage au travers de ROT(-5) == ROT(21) :")
print(clair)

# affichage des valeurs d'occurrences pour le message decrypte
# pour comparaison avec les valeurs de reference
print("\n-- Courbe d'occurrence pour le message secret apres dechiffrement :")
print_occur(occur_arr(clair))
