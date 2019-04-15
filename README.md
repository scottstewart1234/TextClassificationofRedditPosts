# TextClassificationofRedditPosts
ANN Text Classification of Political Reddit Posts

In order to collect our data we used a tool called “redditDataExtractor” which can be found on github here: https://github.com/NSchrading/redditDataExtractor/releases

The data was the processed using the ReadInFiles.java found in the repository. When running this make sure the SemanticAnalysis.py file is in the correct place relative to it otherwise an error will be thrown.

Then, the data was analyzed by either running train.py or semanticanalysis.py 

# Requirements:
-skleran

-tensorflow

-keras

-textblob

-nklt

-pandas

-numpy

-various other python packages

# Recommended:
-a cuda enabled graphics card

-cuDNN

# By: Scott Stewart, Reid Holben, Caroline Hylton, Alex Foyer
The data we downloaded for it:


|Subreddit| Conservative or Liberal|Subscriber Count|Total Data |Cleaned Data|
|---------| -----------------------|----------------|-----------|------------|
|r/Liberal|Liberal|66,500|1,011|931|
|/r/Conservative|Conservative |203,413|1,833|1,731|
|/r/progressive|Liberal|55,608|1,043|912|
/r/The_Donald
Conservative
728,597
1,956
1,742
/r/EnoughTrumpSpam
Liberal
92,107
1,137
925
r/HillaryForPrison
Conservative
53,300
960
789
/r/democrats
Liberal
90,384
1,113
872
/r/Republican
Conservative
78,896


1,161
1,120
r/SandersForPresident
Liberal
241,731
1,422
1,189
/r/Libertarian
Conservative
292,375
1,556
1,473
/r/LateStageCapitalism
Liberal
402,900
1,258
1,153
/r/RonPaul
Conservative
30,000
973
930

