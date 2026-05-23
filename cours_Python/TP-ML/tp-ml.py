
#Import
import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler, OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.linear_model import LinearRegression
import seaborn as sns
import matplotlib.pyplot as plt

# Importer et visualiser le Dataset (Jeu de donnée)
df = pd.read_csv("src/employees_data.csv")
df.head()

# Afficher la dimension du dataset sous la forme(#lignes, #colonnes)
print(df.shape)

# Afficher les statistiques principales du dataset
print(df.describe(include="all"))

# Séparer la variable cible Y des variables explicatives X
print("Séparation de la variable cible des variables explicatives...")

# Choisissez les colonnes que vous souhaitez utitliser comme variables explicatives
features_list = ["Country","YearsExperience"]


X = df.loc[:,features_list] # Nous ajoutons features_list dans notre loc
y = df.loc[:,"Salary"] # Nous définissons « Salary » comme variable cible
print("...Done.")
print()