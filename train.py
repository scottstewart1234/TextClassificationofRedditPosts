import numpy
import matplotlib.pyplot as plt
from keras.layers import Dropout
from keras.layers import Flatten
from keras.constraints import maxnorm
from keras.optimizers import SGD, RMSprop, Adam
from keras.layers import Conv1D
from keras.layers.convolutional import MaxPooling1D
from keras.layers import BatchNormalization
from keras.utils import np_utils
from keras import backend as K
import load_data
from keras.models import Sequential
from keras.layers import Dense
from sklearn.model_selection import train_test_split

K.set_image_dim_ordering('tf')
# fix random seed for reproducibility
seed = 7
numpy.random.seed(seed)

def pre_process(X):

    # normalize inputs from 0-255 to 0.0-1.0
    X=X.astype('float32')
    #X = X / 255.0
    return X

def one_hot_encode(y):

    # one hot encode outputs
    y = np_utils.to_categorical(y)
    num_classes = y.shape[1]
    return y,num_classes

def define_model(num_classes,epochs, dim):
    # Create the model
    model = Sequential()
    #added these
   
    model.add(Dense(512,input_dim=dim, activation='sigmoid', kernel_constraint=BatchNormalization(axis=-1,  epsilon=0.001, center=True, scale=True, beta_initializer='zeros', gamma_initializer='ones', moving_mean_initializer='zeros', moving_variance_initializer='ones', beta_regularizer=None, gamma_regularizer=None, beta_constraint=None, gamma_constraint=None)))
  
    model.add(Dropout(0.25))
    
   # model.add(Dense(200, activation='sigmoid',kernel_constraint=BatchNormalization(axis=-1,  epsilon=0.001, center=True, scale=True, beta_initializer='zeros', gamma_initializer='ones', moving_mean_initializer='zeros', moving_variance_initializer='ones', beta_regularizer=None, gamma_regularizer=None, beta_constraint=None, gamma_constraint=None)))
    model.add(Dense(120, activation='sigmoid',kernel_constraint=BatchNormalization(axis=-1,  epsilon=0.001, center=True, scale=True, beta_initializer='zeros', gamma_initializer='ones', moving_mean_initializer='zeros', moving_variance_initializer='ones', beta_regularizer=None, gamma_regularizer=None, beta_constraint=None, gamma_constraint=None)))
    
    model.add(Dense(20, activation='sigmoid',kernel_constraint=BatchNormalization(axis=-1,  epsilon=0.001, center=True, scale=True, beta_initializer='zeros', gamma_initializer='ones', moving_mean_initializer='zeros', moving_variance_initializer='ones', beta_regularizer=None, gamma_regularizer=None, beta_constraint=None, gamma_constraint=None)))
    #model.add(Dropout(0.25))
    model.add(Dense(num_classes, activation='sigmoid'))
    # Compile model
    lrate = 0.0001
    decay = lrate/epochs
    sgd = RMSprop(lr=lrate, rho=0.9, epsilon=None, decay=decay) #SGD(lr=lrate, momentum=0.0, decay=0.0, nesterov=False) #
    model.compile(loss='categorical_crossentropy', optimizer=sgd, metrics=['accuracy'])
    print(model.summary())
    return model

#%%
# load data
X,y=load_data.load_datasets()

# pre process
X=pre_process(X)
#%%
#one hot encode
y,num_classes=one_hot_encode(y)

#%%
X = X.values;

#%%
#split dataset
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=7)

epochs = 1000
#define model
model=define_model(num_classes,epochs, X.shape[1])


# Fit the model
history=model.fit(X_train, y_train, validation_data=(X_test, y_test), epochs=epochs, batch_size=512)

# list all data in history
print(history.history.keys())
# summarize history for accuracy
plt.plot(history.history['acc'])
plt.plot(history.history['val_acc'])
plt.title('model accuracy')
plt.ylabel('accuracy')
plt.xlabel('epoch')
plt.legend(['train', 'test'], loc='upper left')
plt.show()
# summarize history for loss
plt.plot(history.history['loss'])
plt.plot(history.history['val_loss'])
plt.title('model loss')
plt.ylabel('loss')
plt.xlabel('epoch')
plt.legend(['train', 'test'], loc='upper left')
plt.show()




# Final evaluation of the model
scores = model.evaluate(X_test, y_test, verbose=0)
print("Accuracy: %.2f%%" % (scores[1]*100))

# serialize model to JSONx
model_json = model.to_json()
with open("model_face.json", "w") as json_file:
    json_file.write(model_json)
# serialize weights to HDF5
model.save_weights("model_face.h5")
print("Saved model to disk")
#%%
from keras.utils import plot_model
plot_model(model, to_file='model.png')