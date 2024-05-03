import pickle
import pandas as pd

from sklearn.preprocessing import MinMaxScaler, StandardScaler, OneHotEncoder
from sklearn.pipeline import Pipeline
from sklearn.impute import SimpleImputer
from sklearn.compose import ColumnTransformer

from flask import Flask, request, jsonify
from flask_cors import CORS
# ------------------------------------------------------
app = Flask(__name__)
CORS(app)
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
# ------------------------------------------------------
@app.route('/test', methods=['POST',"GET"])
def test():
    return jsonify("Hello from Flask Test")
# ------------------------------------------------------
@app.route('/predict', methods=['POST'])
def predict():

    data = request.json.get('data', None)
    test_data = pd.DataFrame(data)

    # Load the model from the file
    with open('LR_best.pkl', 'rb') as file:
        model = pickle.load(file)

    # Transform the test data
    test = test_data.drop(['id','clock_speed','m_dep','n_cores'],axis= 1)

    scaler = MinMaxScaler()
    test_normalized = pd.DataFrame(scaler.fit_transform(test), columns=test.columns)

    numeric_features = ['battery_power', 'int_memory', 'mobile_wt', 'fc', 'pc', 'px_height', 'px_width', 'ram', 'sc_h', 'sc_w', 'talk_time']
    numeric_transformer = Pipeline(steps=[('imputer', SimpleImputer(strategy='median')),('scaler', StandardScaler())])

    categorical_features = ['blue', 'dual_sim', 'four_g', 'three_g', 'touch_screen', 'wifi']
    categorical_transformer = Pipeline(steps=[('imputer', SimpleImputer(strategy='constant', fill_value=0)),('onehot', OneHotEncoder(handle_unknown='ignore'))])

    preprocessor = ColumnTransformer(transformers=[('num', numeric_transformer, numeric_features),('cat', categorical_transformer, categorical_features)])
    test_preprocessed = preprocessor.fit_transform(test_normalized)

    # Use the model to make predictions
    predictions = model.predict(test_preprocessed)

    # Print the predictions
    print(predictions)

    # Create a device list to be sent
    devices_list = []
    for i in range(0,len(predictions)):
        device = {
            "deviceId": int(test_data.loc[i, 'deviceId']),
            "battery_power": int(test_data.loc[i, 'battery_power']),
            "blue": bool(test_data.loc[i, 'blue']),
            "clock_speed": float(test_data.loc[i, 'clock_speed']),
            "dual_sim": bool(test_data.loc[i, 'dual_sim']),
            "fc": int(test_data.loc[i, 'fc']),
            "four_g": bool(test_data.loc[i, 'four_g']),
            "int_memory": int(test_data.loc[i, 'int_memory']),
            "m_dep": float(test_data.loc[i, 'm_dep']),
            "mobile_wt": int(test_data.loc[i, 'mobile_wt']),
            "n_cores": int(test_data.loc[i, 'n_cores']),
            "pc": int(test_data.loc[i, 'pc']),
            "px_height": int(test_data.loc[i, 'px_height']),
            "px_width": int(test_data.loc[i, 'px_width']),
            "ram": int(test_data.loc[i, 'ram']),
            "sc_h": int(test_data.loc[i, 'sc_h']),
            "sc_w": int(test_data.loc[i, 'sc_w']),
            "talk_time": int(test_data.loc[i, 'talk_time']),
            "three_g": bool(test_data.loc[i, 'three_g']),
            "touch_screen": bool(test_data.loc[i, 'touch_screen']),
            "wifi": bool(test_data.loc[i, 'wifi']),
            "price_range": int(predictions[i])
        }

        devices_list.append(device)

    return jsonify(devices_list)
# ------------------------------------------------------
if __name__ == '__main__':
    app.run(debug=True)
