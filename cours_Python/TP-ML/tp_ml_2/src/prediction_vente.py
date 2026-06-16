# import bibliotheque
import pandas as pd
import numpy as np

from sklearn.preprocessing import StandardScaler, OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.pipeline import Pipeline
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import train_test_split

import seaborn as sns
import matplotlib.pyplot as plt


# chargement dataset
df = pd.read_csv("../data/SalesDataset.csv", index_col=0)

# vérification des colonnes (sécurité)
required_cols = ['Total Amount', 'Quantity', 'Price per Unit']
missing_cols = [col for col in required_cols if col not in df.columns]

if missing_cols:
    raise ValueError(f"Colonnes manquantes dans le dataset : {missing_cols}")

# conversion en numérique (sécurise les calculs)
df['Total Amount'] = pd.to_numeric(df['Total Amount'], errors='coerce')
df['Quantity'] = pd.to_numeric(df['Quantity'], errors='coerce')
df['Price per Unit'] = pd.to_numeric(df['Price per Unit'], errors='coerce')

# calcul attendu
expected_total = df['Quantity'] * df['Price per Unit']

# comparaison robuste (float safe + NaN safe)
result = np.isclose(df['Total Amount'], expected_total, equal_nan=True)

print("Total == Quantity * Price per Unit :", result.all())