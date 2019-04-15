# -*- coding: utf-8 -*-
"""
Created on Wed Apr  3 17:13:33 2019

@author: sstew
"""
#%%

from sklearn.naive_bayes import MultinomialNB  
from sklearn.model_selection import train_test_split  
from sklearn.metrics import classification_report, confusion_matrix,log_loss, accuracy_score
import load_data
#SVM TRAIN
X,y=load_data.load_datasets()
X = X.values;
print('start')
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=7)
#svclassifier = LinearSVC(C=1.0, class_weight=None, dual=True, fit_intercept=True,
#    intercept_scaling=1, loss='squared_hinge', max_iter=1000,
#     multi_class='ovr', penalty='l2', random_state=0, tol=1e-05, verbose=0)
svclassifier = MultinomialNB()
svclassifier.fit(X_train, y_train)  
y_pred = svclassifier.predict(X_test)  
print(log_loss(y_test,y_pred))
print(accuracy_score(y_test,y_pred))
print(confusion_matrix(y_test,y_pred))  
print(classification_report(y_test,y_pred))  