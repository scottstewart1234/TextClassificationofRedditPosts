# TextClassificationofRedditPosts
ANN Text Classification of Political Reddit Posts

In order to collect our data we used a tool called “redditDataExtractor” which can be found on github here: https://github.com/NSchrading/redditDataExtractor/releases

The data was the processed using the ReadInFiles.java found in the repository. When running this make sure the SemanticAnalysis.py file is in the correct place relative to it otherwise an error will be thrown.

Then, the data was analyzed by either running train.py (for keras models) or SVMtrain.py (for sklearn models) 

# Requirements:

|Requirements|
|-------------------------------------------|
|sklearn|
|tensorflow|
|keras|
|textblob|
|nklt|
|pandas|
|numpy|
|various other python packages|

# Recommended:

|Recommendations|
|-------------------------------------------|
|a cuda enabled graphics card|
|cuDNN|

# By: Scott Stewart, Reid Holben, Caroline Hylton and Alex Foyer


# The Data
The data we downloaded using the reddit data extractor can be seen below. The cleaned data removing duplicates (reposts, crossposts) has a split favoring conservative posts, 57% to 43% (7785 posts to 5982 posts). 

|Subreddit| Conservative or Liberal|Subscriber Count|Total Data |Cleaned Data|
|---------| -----------------------|----------------|-----------|------------|
|r/Liberal|Liberal|66,500|1,011|931|
|/r/Conservative|Conservative |203,413|1,833|1,731|
|/r/progressive|Liberal|55,608|1,043|912|
|/r/The_Donald|Conservative|728,597|1,956|1,742|
|/r/EnoughTrumpSpam|Liberal|92,107|1,137|925|
|r/HillaryForPrison|Conservative|53,300|960|789|
|/r/democrats|Liberal|90,384|1,113|872|
|/r/Republican|Conservative|78,896|1,161|1,120|
|r/SandersForPresident|Liberal|241,731|1,422|1,189|
|/r/Libertarian|Conservative|292,375|1,556|1,473|
|/r/LateStageCapitalism|Liberal|402,900|1,258|1,153|
|/r/RonPaul|Conservative|30,000|973|930|

The data was cleaned using the ReadInFiles.java, which has a method for removing repost and crossposts. The data was downloaded using the reddit data extractor.
 

# The Features
Inside the ReadInFiles.java we were able to process the data in a few different ways, and they have been described below. The booleans near the top of the file make this possible, and by changing them between true and false you can automatically do this for your program.

|Feature set name|Description|
|--------------------------------------|-------------------------------------------------------------------------------------------|
|Traditional bag of words|Each word used represents a feature. If the post contains a word n times its input has a n at that feature, and a 0 otherwise|
|Bag of words with bigrams|Each pair of words represent a feature. works the same as the traditional bag of words.|
|Bag of words with bigrams and nltk root word processing|Same as bag of words with bigrams, but all words are modified to be their “root” word. (For example, abolished->abolish, cats->cat)|
|Bag of words with bigrams, nltk root word processing, and semantic analysis|Same as the bag of words with bigrams and nltk root word processing, but if TextBlob thought the sentence had a negative connotation every element of the input was multiplied by -1. it should be noted that this had an extreemly negative affect on performance|

We found that the bag of words with bigrams gave the best results for all of our models
# The Models
We used a couple different models. They are described below.

|Model|Description|Best Accuracy|
|--------------|-------------------|----------------|
|Simple Neural Network (2 layers)| A very simple neural network made with Keras| 75.05%|
|Neural Network (5 Layers)| A deep(er) neural network made with keras| 74.69%|
|Linear SVM| A linear SVM made with SKLearn| 69.67% |
|Gaussian Naive Bayes | A gaussian naive bayes classifier made with SKLearn| 60% |
|Multinomial Naive Bayes|A Multinomial naive bayes classifier made with SKLearn| 73.31%|
|Always Choose “Conservative”| Literally just pick conservative (what we had slightly more data for)| 56.5%|

# Analysis of results
Our results show that a simple neural network (with 2 layers and one reshape layer) was able to outperform our other models by a small margin. By changing our feature set to include a bigram model we were able to slightly increase our accuracy. It should be noted that the bigram model was built on top of the already existing feature set, and both were used in making the final model. Oddly, one of the models actually performed worse than guessing “conservative” for every post.


