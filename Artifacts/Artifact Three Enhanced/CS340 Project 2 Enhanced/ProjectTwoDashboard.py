import base64

import dash_leaflet as dl
import pandas as pd
import plotly.express as px
from dash import Dash, dcc, html, dash_table
from dash.dependencies import Input, Output

from aac import AnimalShelter

# Database connection parameters
DATABASE_HOST = 'localhost'
DATABASE_PORT = '27017'
DATABASE_NAME = 'AAC'
COLLECTION_NAME = 'animals'

# Initialize database connection
animal_shelter_db = AnimalShelter(DATABASE_HOST, DATABASE_PORT, DATABASE_NAME, COLLECTION_NAME)

try:
    animal_shelter_db.create_indexes()
except Exception as e:
    print(f"Warning: Index creation failed - {e}")

# Initial empty DataFrame for layout setup - actual data loaded via callbacks
initial_dataframe = pd.DataFrame(columns=['animal_id', 'name', 'breed', 'animal_type'])

# Initialize Dash app
app = Dash(__name__)

# Load and encode logo
image_filename = 'Grazioso_Salvare_Logo.png' # replace with your own image
encoded_image = base64.b64encode(open(image_filename, 'rb').read())

app.layout = html.Div([
    html.A([
        html.Center(html.Img(
            src=f'data:image/png;base64,{encoded_image.decode()}',
            height=250, width=251
        ))
    ], href='https://www.snhu.edu', target="_blank"),
    
    html.Center(html.B(html.H1('CS-340 Dashboard'))),
    html.Hr(),
    
    html.Div([
        html.Label("Database Filters:", style={'margin-right': '10px'}),
        dcc.Dropdown(
            id='filter-type',
            options=[
                {'label': 'All Animals (Reset)', 'value': 'Reset'},
                {'label': 'Water Rescue', 'value': 'Water'},
                {'label': 'Mountain or Wilderness Rescue', 'value': 'Mountain'},
                {'label': 'Disaster Rescue or Individual Tracking', 'value': 'Disaster'}
            ],
            value='Reset',
            style={'width': '300px', 'display': 'inline-block'}
        )
    ], style={'margin': '10px 0'}),
    html.Div([
        html.Label("Records to Display:", style={'margin-right': '10px'}),
        dcc.Dropdown(
            id='record-limit',
            options=[
                {'label': '500 Records', 'value': 500},
                {'label': '1000 Records', 'value': 1000},
                {'label': '2500 Records', 'value': 2500},
                {'label': '5000 Records', 'value': 5000},
                {'label': 'All Records (May Be Slow)', 'value': 999999}
            ],
            value=1000,
            style={'width': '275px', 'display': 'inline-block'}
        )
    ], style={'margin': '10px 0'}),
    html.Hr(),
    dash_table.DataTable(id='datatable-id',
                         columns=[{"name": column, "id": column, "deletable": False, "selectable": True} for column in initial_dataframe.columns],
                         data=initial_dataframe.to_dict('records'),
                         editable=False,
                         filter_action="native",
                         filter_options={"case": "insensitive", "placeholder_text": "Filter column..."},
                         page_action="native",
                         sort_action="native",
                         sort_mode="multi",
                         selected_rows=[0],
                         row_selectable="single",
                         page_current=0,
                         page_size=10
                        ),
    html.Br(),
    html.Hr(),
    
    html.Div(className='row',
         style={'display': 'flex'},
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
    html.H4("Stewart Withrow: CS-340 Project 2 Enhanced")
    
])

@app.callback(
    [Output('datatable-id', 'data'),
     Output('datatable-id', 'columns')],
    [Input('filter-type', 'value'),
     Input('record-limit', 'value')]
)
def update_dashboard(filter_type, record_limit):
    """Update the data table based on selected filters."""
    limit = None if record_limit >= 999999 else record_limit
    if filter_type == 'Reset':
        query = {}
    elif filter_type == 'Water':
        query = {
            "animal_type": "Dog",
            "breed": {"$in": ["Labrador Retriever Mix", "Chesapeake Bay Retriever", "Newfoundland"]},
            "sex_upon_outcome": "Intact Female",
            "age_upon_outcome_in_weeks": {"$gte": 26.0, "$lte": 156.0}
        }
    elif filter_type == 'Mountain':
        query = {
            "animal_type": "Dog",
            "breed": {"$in": ["German Shepard", "Alaskan Malamute", "Old English Sheepdog",
                              "Siberian Husky", "Rottweiler"]},
            "sex_upon_outcome": "Intact Male",
            "age_upon_outcome_in_weeks": {"$gte": 26.0, "$lte": 156.0}
        }
    elif filter_type == 'Disaster':
        query = {
            "animal_type": "Dog",
            "breed": {"$in": ["Doberman Pinscher", "German Shepard", "Golden Retriever",
                              "Bloodhound", "Rottweiler"]},
            "sex_upon_outcome": "Intact Male",
            "age_upon_outcome_in_weeks": {"$gte": 20.0, "$lte": 300.0}
        }
    else:
        raise Exception("Unknown filter")
    
    results_dataframe = pd.DataFrame.from_records(animal_shelter_db.read(query, limit=limit))
    columns = [{"name": column, "id": column, "deletable": False, "selectable": True} for column in results_dataframe.columns]
    data = results_dataframe.to_dict('records')        
        
    return (data, columns)
@app.callback(
    Output('graph-id', "children"),
    [Input('datatable-id', "derived_virtual_data")]
)
def update_graphs(view_data):
    """Update breed distribution chart based on filtered data."""
    chart_dataframe = pd.DataFrame.from_dict(view_data)
    if chart_dataframe.empty or 'breed' not in chart_dataframe.columns:
        return [
            html.Div([
                html.H4("No Chart Data", style={'color': '#888', 'text-align': 'center'}),
                html.P("No data available for breed distribution chart.", 
                       style={'text-align': 'center', 'color': '#aaa', 'font-size': '14px'}),
                html.P("Double check your filter/search query and ensure it's correct.", 
                       style={'text-align': 'center', 'color': '#666', 'font-size': '12px', 'font-style': 'italic'}),
            ], style={'padding': '40px', 'text-align': 'center', 'background-color': '#f8f9fa', 
                     'border': '1px dashed #ddd', 'border-radius': '5px', 'margin': '20px'})
        ]
    
    breed_counts = chart_dataframe['breed'].value_counts()
    top_breeds = breed_counts.head(8)
    other_count = breed_counts.tail(-8).sum() if len(breed_counts) > 8 else 0
    
    chart_names = list(top_breeds.index)
    chart_values = list(top_breeds.values)
    if other_count > 0:
        chart_names.append('Other Breeds')
        chart_values.append(other_count)
    fig = px.pie(values=chart_values, names=chart_names, title='Top Breed Distribution')
    fig.update_traces(textposition='inside', textinfo='percent+label')
    fig.update_layout(
        legend=dict(orientation="v", yanchor="middle", y=0.5, xanchor="left", x=1.01),
        margin=dict(l=20, r=120, t=60, b=20),
        font=dict(size=11)
    )
    
    return [dcc.Graph(figure=fig)]
    
@app.callback(
    Output('datatable-id', 'style_data_conditional'),
    [Input('datatable-id', 'selected_columns')]
)
def update_styles(selected_columns):
    """Highlight selected columns in the data table."""
    if selected_columns is None:
        return []
    return [{
        'if': {'column_id': column_id},
        'background_color': '#D2F3FF'
    } for column_id in selected_columns]


@app.callback(
    Output('map-id', "children"),
    [Input('datatable-id', "derived_virtual_data"),
     Input('datatable-id', "derived_virtual_selected_rows")])
def update_map(view_data, index):
    """Update map display based on selected animal record."""
    if view_data is None:
        return
    elif index is None:
        return
    
    map_dataframe = pd.DataFrame.from_dict(view_data)
    
    # Check if there's no data available
    if map_dataframe.empty:
        return [
            html.Div([
                html.H4("No Map Data", style={'color': '#888', 'text-align': 'center'}),
                html.P("No location data available to display on map.", 
                       style={'text-align': 'center', 'color': '#aaa', 'font-size': '14px'}),
                html.P("Double check your filter/search query and ensure it's correct.", 
                       style={'text-align': 'center', 'color': '#666', 'font-size': '12px', 'font-style': 'italic'}),
            ], style={'padding': '40px', 'text-align': 'center', 'background-color': '#f8f9fa', 
                     'border': '1px dashed #ddd', 'border-radius': '5px', 'margin': '20px'})
        ]
    
    selected_row_index = index[0] if len(index) > 0 else 0
        
    latitude = map_dataframe.iloc[selected_row_index]['location_lat'] if 'location_lat' in map_dataframe.columns else None
    longitude = map_dataframe.iloc[selected_row_index]['location_long'] if 'location_long' in map_dataframe.columns else None
    
    # Fallback to Austin, TX coordinates if location data is missing
    if latitude is None or longitude is None or pd.isna(latitude) or pd.isna(longitude):
        latitude, longitude = 30.75, -97.48
    else:
        latitude, longitude = float(latitude), float(longitude)
    
    animal_breed = map_dataframe.iloc[selected_row_index]['breed'] if 'breed' in map_dataframe.columns else 'Unknown'
    animal_name = map_dataframe.iloc[selected_row_index]['name'] if 'name' in map_dataframe.columns else 'Unknown'
    
    return [
        dl.Map(style={'width': '1000px', 'height': '500px'}, center=[30.75, -97.48], zoom=10, children=[
            dl.TileLayer(id="base-layer-id"),
            dl.Marker(position=[latitude, longitude], children=[
                dl.Tooltip(animal_breed),
                dl.Popup([
                    html.H1("Animal Name"),
                    html.P(animal_name)
                ])
            ])
        ])
    ]

app.run(debug=True)