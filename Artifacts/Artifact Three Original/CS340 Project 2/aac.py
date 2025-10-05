from pymongo import MongoClient
from bson.objectid import ObjectId
from bson.json_util import dumps

class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """
    
    def __init__(self, user, passwd, host, port, db, col):
        # Initializing the MongoClient. This helps to 
        # access the MongoDB databases and collections.
        # This is hard-wired to use the aac database, the 
        # animals collection, and the aac user.
        # Definitions of the connection string variables are
        # unique to the individual Apporto environment.
        
        # Connection Variables
        USER = user
        PASS = passwd
        HOST = host
        PORT = port
        DB = db
        COL = col
        
        # Initialize Connection
        self.client = MongoClient('mongodb://localhost:27017/')
        self.database = self.client['%s' % (DB)]
        self.collection = self.database['%s' % (COL)]
        
    # Complete this create method to implement the C in CRUD.
    def create(self, data):
        # Runs data into database if data has content
        if data is not None:
            # Inserts data into database
            insert = self.database.animals.insert_one(data)  # data should be dictionary   
            # Returns true if insert was successful, false if not
            if insert != 0:
                return True
            else:
                return False         
        # Returns an exception if data is empty
        else:
            raise Exception("Nothing to save, data parameter is empty")

    # Create method to implement the R in CRUD.
    def read(self, search_data):
        # Runs a search query if search_data has params
        if search_data is not None:
            # Search function with params and DROPS ID
            data = self.database.animals.find(search_data, {"_id": False})
            # Used to prind documents to console (Debug)
            #for document in data:
                #print(document)        
        # Runs an empty query if search_data is empty
        else:
            data = self.database.animals.find({}, {"_id": False})
        # Returns search query as data
        return data
    
    # Create method to implement the U in CRUD.
    def update(self, original, updated):
        # Runs update process if "original" has a param
        if original is not None:
            # Checks for entries in database that match "original's" requirements
            if self.database.animals.count_documents(original, limit = 1) != 0:
                # Mongo command to update all individual entries that meet "original's" requirements
                update_result = self.database.animals.update_many(original, {"$set": updated})
                # Assigns raw result to be returned
                result = update_result.raw_result
            # Returns statement if there is no document that meets requirements
            else:
                result = "No document found"
            # Returns result based on results from the if statement
            return result
        # Returns exception if original is empty
        else:
            raise Exception("Nothing to update, data parameter is empty")
    
    # Create method to implement the D in CRUD.
    def delete(self, remove):
        # Runs removal process if "remove" has a param
        if remove is not None:
            # Checks for entries in database that match "remove's" requirements
            if self.database.animals.count_documents(remove, limit = 1) != 0:
                # Mongo command to remove all entries that meet "removes's" requirements
                delete_result = self.database.animals.delete_many(remove)
                # Assigns raw result to be returned
                result = delete_result.raw_result
            # Returns statement if there is no document that meets requirements
            else:
                result = "No documentfound"
            return result
        # Returns exception if remove is empty
        else:
            raise Exception("Nothing to update, data parameter is empty")