
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

### Pipeline d’entraînement ###
print("---Training pipeline ---")
print() 

# Encodage des variables catégorielles et standardisation des variables numériques
print("#### X_train AVANT le prétraitement ####")
print(df.head())
print()

print("Encodage des variables catégorielles et standardisation des variables numériques...")
## On commence par importer les bibliothèques
## StandardScaler pour mettre les données à l’échelle (Z-score)
## OneHotEncoder pour encoder les variables catégorielles

numeric_features = [1] # Choisissez l’index de la colonne numérique à standardiser
numeric_transformer = StandardScaler()
categorical_features = [0]
categorical_transformer = OneHotEncoder()
# Application de ColumnTransformer pour créer un pipeline de prétraitement

feature_encoder=ColumnTransformer(
transformers=[
('cat',categorical_transformer,categorical_features),
('num',numeric_transformer,numeric_features)
]
)
X_train=feature_encoder.fit_transform(df.iloc[:, :-1]) # Appliquer le prétraitement à toutes les colonnes sauf la dernière (la cible)
print("...Done.")
print("#### X_train APRÈS le prétraitement ####")
print(X_train[0:5, :]) # Afficher les 5 premières lignes (X_train est maintenant un tableau numpy)
print()

# Entraîner le modèle
print("Entraînement du modèle...")
regressor=LinearRegression()
regressor.fit(X_train, y_train) # Cette étape correspond à l’entraînement réel
print("...Terminé.")