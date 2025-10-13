from pymongo import MongoClient
from pymongo.errors import DuplicateKeyError, WriteError
from bson.objectid import ObjectId


class AnimalShelter:
    """CRUD operations for Animal collection in MongoDB."""
    
    def __init__(self, host, port, database_name, collection_name):
        """Initialize MongoDB connection for animal shelter database.
        
        Args:
            host: MongoDB server hostname
            port: MongoDB server port
            database_name: Name of the database
            collection_name: Name of the collection
            
        Raises:
            ValueError: If connection parameters are missing
            ConnectionError: If unable to connect to MongoDB
        """
        if not all([host, port, database_name, collection_name]):
            raise ValueError("All connection parameters must be provided and non-empty")
        
        try:
            self.client = MongoClient(f'mongodb://{host}:{port}/', serverSelectionTimeoutMS=5000)
            self.client.admin.command('ping')
            self.database = self.client[database_name]
            self.collection = self.database[collection_name]
        except Exception as e:
            raise ConnectionError(f"Failed to connect to MongoDB. Please check if MongoDB is running. Error: {e}")

    def _validator(self, data, param_name, allow_empty=False):
        """Validate parameters and check for dangerous MongoDB operators."""
        if data is None:
            raise ValueError(f"{param_name} parameter is empty")
        if not isinstance(data, dict):
            raise ValueError(f"{param_name} must be a dictionary")
        if not allow_empty and len(data) == 0:
            raise ValueError(f"{param_name} dictionary cannot be empty")
        
        dangerous_operators = ['$where', '$expr', '$function', '$javascript']
        for key in data:
            if key in dangerous_operators:
                raise ValueError(f"Dangerous operator not allowed in {param_name}: {key}")
        return True

    def _convert_id(self, document_id):
        """Convert string ID to ObjectId if needed."""
        try:
            if isinstance(document_id, str):
                return ObjectId(document_id)
            else:
                return document_id
        except Exception as e:
            raise ValueError(f"Invalid document ID format: {e}")

    def _validate_schema(self, data):
        """Validate required fields and data types for animal records."""
        required_fields = ['animal_id', 'animal_type', 'name', 'breed']
        
        for field in required_fields:
            if field not in data:
                raise ValueError(f"Required field missing: {field}")
            if data[field] is None or data[field] == "":
                raise ValueError(f"Required field cannot be empty: {field}")
        
        if 'age_upon_outcome_in_weeks' in data:
            if not isinstance(data['age_upon_outcome_in_weeks'], (int, float)):
                raise ValueError("Age must be a number")
            if data['age_upon_outcome_in_weeks'] < 0:
                raise ValueError("Age cannot be negative")
        
        if 'sex_upon_outcome' in data:
            valid_sex_outcome_values = ['Intact Male', 'Intact Female', 'Neutered Male', 'Spayed Female']
            if data['sex_upon_outcome'] not in valid_sex_outcome_values:
                raise ValueError("Invalid sex_upon_outcome value")
        
        if 'location_lat' in data:
            if not isinstance(data['location_lat'], (int, float)):
                raise ValueError("Location latitude must be a number")
        if 'location_long' in data:
            if not isinstance(data['location_long'], (int, float)):
                raise ValueError("Location longitude must be a number")
        
        return True
    
    def create(self, data):
        """Insert a new animal record into the database.
        
        Args:
            data: Dictionary containing animal data
            
        Returns:
            bool: True if successful, False otherwise
            
        Raises:
            ValueError: If data validation fails or duplicate key
            RuntimeError: If database operation fails
        """
        self._validator(data, "Data")
        self._validate_schema(data)
        
        try:
            insert_result = self.database.animals.insert_one(data)
            if insert_result.inserted_id:
                return True
            else:
                return False
        except DuplicateKeyError as e:
            raise ValueError(f"Document with this key already exists: {e}")
        except WriteError as e:
            raise RuntimeError(f"Database write error: {e}")
        except Exception as e:
            raise RuntimeError(f"Failed to create document: {e}")

    def read(self, search_criteria, limit=None):
        """Query animal records from the database.
        
        Args:
            search_criteria: MongoDB query dict or None for all records
            limit: Maximum number of records to return
            
        Returns:
            pymongo.cursor.Cursor: Query results
            
        Raises:
            RuntimeError: If database operation fails
        """
        if search_criteria is not None:
            self._validator(search_criteria, "Search data", allow_empty=True)
        
        try:
            query = search_criteria if search_criteria is not None else {}
            data = self.database.animals.find(query, {"_id": False})
            
            if limit is not None:
                data = data.limit(limit)
                
            return data
        except Exception as e:
            raise RuntimeError(f"Failed to read from database: {e}")
    
    def update(self, document_id, updated):
        """Update an existing animal record.
        
        Args:
            document_id: ID of document to update
            updated: Dictionary of fields to update
            
        Returns:
            dict or str: Update result or "No document found"
            
        Raises:
            ValueError: If parameters are invalid
            RuntimeError: If database operation fails
        """
        if document_id is None:
            raise ValueError("Nothing to update, document_id parameter is empty")
        if updated is None:
            raise ValueError("Nothing to update, updated parameter is empty")
        if not isinstance(updated, dict):
            raise ValueError("Updated must be a dictionary")
        
        object_id = self._convert_id(document_id)
        
        try:
            if self.database.animals.count_documents({"_id": object_id}, limit=1) == 0:
                return "No document found"
            
            update_result = self.database.animals.update_one({"_id": object_id}, {"$set": updated})
            result = update_result.raw_result
            return result
        except WriteError as e:
            raise RuntimeError(f"Database write error during update: {e}")
        except Exception as e:
            raise RuntimeError(f"Failed to update document: {e}")
    
    def delete(self, document_id):
        """Delete an animal record from the database.
        
        Args:
            document_id: ID of document to delete
            
        Returns:
            dict or str: Delete result or "No document found"
            
        Raises:
            ValueError: If document_id is invalid
            RuntimeError: If database operation fails
        """
        if document_id is None:
            raise ValueError("Nothing to delete, document_id parameter is empty")
        
        object_id = self._convert_id(document_id)
        
        try:
            if self.database.animals.count_documents({"_id": object_id}, limit=1) == 0:
                return "No document found"
            
            delete_result = self.database.animals.delete_one({"_id": object_id})
            result = delete_result.raw_result
            return result
        except WriteError as e:
            raise RuntimeError(f"Database write error during delete: {e}")
        except Exception as e:
            raise RuntimeError(f"Failed to delete document: {e}")
    
    def create_indexes(self):
        """Create database indexes for optimized query performance.
        
        Returns:
            bool: True if successful
            
        Raises:
            RuntimeError: If index creation fails
        """
        try:
            self.collection.create_index("animal_id")
            self.collection.create_index("animal_type")
            self.collection.create_index("breed")
            self.collection.create_index("sex_upon_outcome")
            self.collection.create_index("age_upon_outcome_in_weeks")
            
            self.collection.create_index([
                ("animal_type", 1), ("sex_upon_outcome", 1), 
                ("breed", 1), ("age_upon_outcome_in_weeks", 1)
            ])
            
            self.collection.create_index([("location_lat", 1), ("location_long", 1)])
            
            return True
        except Exception as e:
            raise RuntimeError(f"Failed to create indexes: {e}")