
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
df = pd.read_csv("src/salary_Data.csv")
df.head()

# Afficher la dimension du dataset sous la forme(#lignes, #colonnes)
print(df.shape)
# Afficher les statistiques principales du dataset
print(df.describe(include="all"))