# -*- coding: utf-8 -*-
"""
Created on Wed Apr  3 17:13:33 2019

@author: sstew
"""
#%%

from sklearn.svm import SVC  
from sklearn.model_selection import train_test_split  
from sklearn.metrics import classification_report, confusion_matrix  
import load_data
#SVM TRAIN
X,y=load_data.load_datasets()
X = X.values;
print('start')
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.5, random_state=7)
svclassifier = SVC(kernel='rbf')  
svclassifier.fit(X_train, y_train)  
y_pred = svclassifier.predict(X_test)  
print(confusion_matrix(y_test,y_pred))  
print(classification_report(y_test,y_pred))  