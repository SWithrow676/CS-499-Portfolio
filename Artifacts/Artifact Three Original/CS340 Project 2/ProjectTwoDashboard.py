# Setup the Jupyter version of Dash
from dash import Dash

# Configure the necessary Python module imports for dashboard components
import dash_leaflet as dl
from dash import dcc
from dash import html
import plotly.express as px
from dash import dash_table
from dash.dependencies import Input, Output, State
import base64

# Configure OS routines
import os

# Configure the plotting routines
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

# Import CRUD Python module file and class
from aac import AnimalShelter

###########################
# Data Manipulation / Model
###########################

# Login information for aacuser to access database
username = ""
password = ""
host = 'localhost'
port = '27017'
database = 'AAC'
column = 'animals'

# Connect to database via CRUD Module
db = AnimalShelter(username, password, host, port, database, column)

# class read method must support return of list object and accept projection json input
# sending the read method an empty document requests all documents be returned
df = pd.DataFrame.from_records(db.read({}))

# MongoDB v5+ is going to return the '_id' column and that is going to have an 
# invlaid object type of 'ObjectID' - which will cause the data_table to crash - so we remove
# it in the dataframe here. The df.drop command allows us to drop the column. If we do not set
# inplace=True - it will reeturn a new dataframe that does not contain the dropped column(s)
# Commented out since aac.read() drops id
#df.drop(columns=['_id'],inplace=True)

## Debug
# print(len(df.to_dict(orient='records')))
# print(df.columns)


#########################
# Dashboard Layout / View
#########################
app = Dash(__name__)

# Imported Grazioso Salvare’s logo
image_filename = 'Grazioso_Salvare_Logo.png' # replace with your own image
encoded_image = base64.b64encode(open(image_filename, 'rb').read())

app.layout = html.Div([
    # Centered Image of Grazioso Salvare Logo
    html.A([
        html.Center(html.Img(src='data:image/png;base64,{}'.format(encoded_image.decode()),
                             height = 250, width = 251))], href = 'https://www.snhu.edu', target = "_blank"),
    html.Center(html.B(html.H1('CS-340 Dashboard'))),
    html.Hr(),
    html.Div(
        # Code for the interactive filtering options (Radio Buttons)
        dcc.RadioItems(
            id='filter-type',
            options=[
                {'label':'Water Rescue', 'value': 'Water'},
                {'label':'Mountain or Wilderness Rescue', 'value': 'Mountain'},
                {'label':'Disaster Rescue or Individual Tracking', 'value':'Disaster'},
                {'label': 'Reset', 'value': 'Reset'},
            ],
            value='Reset'
        )
    ),
    html.Hr(),
    dash_table.DataTable(id='datatable-id',
                         columns=[{"name": i, "id": i, "deletable": False, "selectable": True} for i in df.columns],
                         data=df.to_dict('records'),
                         # Features for the interactive data table to make it user-friendly
                         editable = False,
                         filter_action="native",
                         page_action="native",
                         sort_action="native",
                         sort_mode="multi",
                         # First row is selected by default to bring up geolocation chart
                         selected_rows=[0],
                         row_selectable = "single",
                         page_current=0,
                         page_size=10
                        ),
    html.Br(),
    html.Hr(),
    # This sets up the dashboard so that your chart and your geolocation chart are side-by-side
    html.Div(className='row',
         style={'display' : 'flex'},
             children=[
        html.Div(
            id='graph-id',
            className='col s12 m6',

            ),
        html.Div(
            id='map-id',
            className='col s12 m6',
            )
        ]),
    # Unique Identifier at the bottom of the page
    html.H4("Stewart Withrow CS-340 Project 2")
    
])

#############################################
# Interaction Between Components / Controller
#############################################

    
@app.callback(
    [Output('datatable-id','data'),
     Output('datatable-id','columns')],
    [Input('filter-type', 'value')]
)
def update_dashboard(filter_type):
    # Reset filter
    if filter_type == 'Reset':
        df = pd.DataFrame.from_records(db.read({})) 
    
    # Filter for Water Rescue
    elif filter_type == 'Water':
        df = pd.DataFrame.from_records(db.read({
            "animal_type": "Dog",
            "breed": {"$in": ["Labrador Retriever Mix","Chesapeake Bay Retriever", "Newfoundland"]},
            "sex_upon_outcome": "Intact Female",
            "age_upon_outcome_in_weeks": {"$gte":26.0, "$lte":156.0}
        })) 
    
    # Filter for Mountain or Wilderness Rescue
    elif filter_type == 'Mountain':
        df = pd.DataFrame.from_records(db.read({
            "animal_type": "Dog",
            "breed": {"$in": ["German Shepard","Alaskan Malamute","Old English Sheepdog",
                              "Siberian Husky", "Rottweiler"]},
            "sex_upon_outcome": "Intact Male",
            "age_upon_outcome_in_weeks": {"$gte":26.0, "$lte":156.0}
        }))
    
    # Filter for Disaster Rescue
    elif filter_type == 'Disaster':
        df = pd.DataFrame.from_records(db.read({
            "animal_type": "Dog",
            "breed": {"$in": ["Doberman Pinscher","German Shepard","Golden Retriever",
                              "Bloodhound","Rottweiler"]},
            "sex_upon_outcome": "Intact Male",
            "age_upon_outcome_in_weeks": {"$gte":20.0, "$lte":300.0}
        })) 
    
    # Potential error catcher
    else:
        raise Exception("Unknown filter")
    
    columns=[{"name": i, "id": i, "deletable": False, "selectable": True} for i in df.columns]
    data=df.to_dict('records')        
        
    return (data,columns)

# Display the breeds of animal based on quantity represented in
# the data table
@app.callback(
    Output('graph-id', "children"),
    [Input('datatable-id', "derived_virtual_data")]
)
def update_graphs(viewData):
    # Variable to hold Filtered Data
    dffChart = pd.DataFrame.from_dict(viewData)
    
    return [
       dcc.Graph(
           #Pie chart to display filtered list of breeds
           figure = px.pie(dffChart, names='breed', title='Breeds')
           
           # Other Option: Histogram
           #figure = px.histogram(dffChart, x='breed', title='Preferred Breeds')
       )    
    ]
    
#This callback will highlight a cell on the data table when the user selects it
@app.callback(
    Output('datatable-id', 'style_data_conditional'),
    [Input('datatable-id', 'selected_columns')]
)
def update_styles(selected_columns):
    return [{
        'if': { 'column_id': i },
        'background_color': '#D2F3FF'
    } for i in selected_columns]


# This callback will update the geo-location chart for the selected data entry
# derived_virtual_data will be the set of data available from the datatable in the form of 
# a dictionary.
# derived_virtual_selected_rows will be the selected row(s) in the table in the form of
# a list. For this application, we are only permitting single row selection so there is only
# one value in the list.
# The iloc method allows for a row, column notation to pull data from the datatable
@app.callback(
    Output('map-id', "children"),
    [Input('datatable-id', "derived_virtual_data"),
     Input('datatable-id', "derived_virtual_selected_rows")])
def update_map(viewData, index):  
    if viewData is None:
        return
    elif index is None:
        return
    
    dff = pd.DataFrame.from_dict(viewData)
    # Because we only allow single row selection, the list can be converted to a row index here
    row = index[0] if len(index) > 0 else 0
        
    # Austin TX is at [30.75,-97.48]
    return [
        dl.Map(style={'width': '1000px', 'height': '500px'}, center=[30.75,-97.48], zoom=10, children=[
            dl.TileLayer(id="base-layer-id"),
            # Marker with tool tip and popup
            # Column 13 and 14 define the grid-coordinates for the map
            # Column 4 defines the breed for the animal
            # Column 9 defines the name of the animal
            dl.Marker(position=[dff.iloc[row,13],dff.iloc[row,14]], children=[
                dl.Tooltip(dff.iloc[row,4]),
                dl.Popup([
                    html.H1("Animal Name"),
                    html.P(dff.iloc[row,9])
                ])
            ])
        ])
    ]

app.run(debug=True)