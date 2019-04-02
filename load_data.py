import pickle
from sklearn.model_selection import train_test_split
from scipy import misc
import numpy as np
import os
import pandas as pd

# Loading dataset
def load_datasets():
    

    Dataframe = pd.read_csv("F:\\DataMining\\text-classification-simple\\outputNoUniques.csv")
    X = Dataframe.iloc[:,:(Dataframe.shape[1]-1)];
    y= Dataframe.iloc[:,(Dataframe.shape[1]-1):];
    #X = X.values;
    #y= y.values;
    y=y[list(y.columns.values)[0]].map({'conservative': 0,'hillaryforprison':0,'libertarian':0,'republican':0,'ronpaul':0,'the_donald':0,
       'sandersforpresident':1,'progressive':1, 'liberal':1, 'latestagecapitalism':1, 'democrats': 1, 'enoughtrumpspam': 1})
    return X,y

# Save int2word dict
a,b= load_datasets();