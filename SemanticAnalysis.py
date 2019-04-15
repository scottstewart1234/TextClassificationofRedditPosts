# -*- coding: utf-8 -*-
"""
Created on Thu Apr 11 09:37:36 2019

@author: sstew
"""


import numpy as np
import pandas as pd



#%%
AllData = pd.read_csv("C:\\Users\\sstew\\Desktop\\Spring_2019\\DataMining\\DataMining Workspace\\DataMiningJavaPortion\\AllData.csv");
#%%
from textblob import TextBlob
from nltk.stem.snowball import SnowballStemmer
AllData = AllData.values;
post = []
subreddit =[]
semantic = []  
stemmer = SnowballStemmer("english");
for i in range(0, 13741):  
    temp = '';
    words = AllData[i][0].split();
    for word in words:
        temp = temp + ' '+ stemmer.stem(word);
    post.append(temp);
    subreddit.append(AllData[i][1]);
    semantic.append([(TextBlob(AllData[i][0]).sentiment.polarity)])

SentimentFile= open('C:\\Users\\sstew\\Desktop\\Spring_2019\\DataMining\\DataMining Workspace\\DataMiningJavaPortion\\SentimentAnalysis.csv', mode='w+')
for i in range(0, 13741):  
    SentimentFile.write(str(post[i]+','+subreddit[i]+','+str(semantic[i]))+'\n');
SentimentFile.close();