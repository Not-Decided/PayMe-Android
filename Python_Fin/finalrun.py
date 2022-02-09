import spacy
import json
import pandas as pd
import warnings
warnings.filterwarnings("ignore")
#Reading the Categories Dataset
df = pd.read_csv('Product_Categories.csv', index_col = 0)

#Creating a list of Product Name
prD = {}
for pr in df["Category"].unique():
    prD[pr] = df[df["Category"] == pr]["Product Name"].to_list()

# Loading the saved Spacy model
nlp = spacy.load(r"C:\Users\user\Documents\Cashflow Management\ML\Invoice-Text-Extraction-master\Invoice-Text-Extraction-master\Python_Fin")

def getOutput(textToPredict):
    # Sending textual data to Spacy model for NER
    doc = nlp(textToPredict)
    data = {}
    items_list = []
  # Iterating over every entitiy to create a dictionary
    for ent in doc.ents:
        # Saving only one instance of Total Bill Amount
        if (ent.label_ == "Total bill amount" or "total" in ent.label_.lower() or "eur" in ent.label_.lower() or "rs" in ent.label_.lower() or "cash" in ent.label_.lower()):
            try:
                amt = float(ent.text)
                data["Total"] = amt
            except Exception as e:
                pass
    # Creating a list of Items
        elif (ent.label_ == "Items"):
            try:
                items_list.append(ent.text)
            except Exception as e:
                pass
    # Checking if the detected key is already present in the key,
    # If yes then we create a new key to store that value instead of overwriting the previous one
        else:
            if ent.label_ not in data.keys():
                data[ent.label_] = ent.text

  #Settingthe Category according to Items list
    category = ""
    for item in items_list:
        for key,val in prD.items():
            for name in val:
                if name.lower() in item.lower():
                    category = key
                    break
            if category != "":
                break
        if category != "":
            break
    data["Category"] = category
  
    if "Total" not in data.keys():
        data["Total"] = ""

    
    ans = {}
    req = ["name","store","store name"]
    for key in data.keys():
        if key.lower() in req or key.lower() in ["category","total"]:
            ans[key.lower()] = data[key]
    c = 0
    for i in req:
        if i not in ans.keys():
            c+=1

    if c == 3:
        ans["store name"] = ""
# Sorting all the elements of the dictionary
    ans = dict(sorted(ans.items()))
  # Printing final result
    return json.dumps(ans)
