# Java_Assignment

Task 1: **CREATE A User**

HTTP TYPE: POST
URL: http://localhost:8080/users/create

Request Body:

    {
      "username": "varun123",
      "firstName": "Varun",
      "lastName": "Sharma",
      "contactEmail": "varun@gmail.com",
      "contactPhone": "91718371837"
    }

Response from Server

    {
        "statusCode": 200,
        "message": "User created successfully",
        "output": {
            "JWT_Token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2YXJ1bjEyMzQiLCJzY29wZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV0sImlzcyI6IlVqandhbCBTaGFybWEiLCJpYXQiOjE2NDQ5ODk3OTQsImV4cCI6MTY0NTAyNTc5NH0.PL_HTNOGvJNNt2e9t7uqMwhDuUOavYUSWniQDzppi6s",
            "user_id": 33,
            "created_timestamp": "Wednesday,February 16,2022 11:06,AM"
        },
        "success": true
    }

Task 2: **Create, Read and Delete Tweets**

    1. **Http Type: POST**
        URL: http://localhost:8080/tweets/create
        Authorization: **Bearer Token** : pass the token that got generated when user was created
        
        **Request Body**:
                {
                    "userId": 1,
                    "tweet": "the moment is critical...."
                }
        **Server Response**
                {
                    "statusCode": 200,
                    "message": "Tweet created successfully.",
                    "output": {
                        "tweet_id": 1,
                        "created_timestamp": "Wednesday,February 16,2022 10:26,AM"
                    },
                    "success": true
                }
                
    2. **Fetch All Tweets**
    
        **HTTP GET request**
        URL: http://localhost:8080/tweets/fetch?username=varun1234&date=1644819748447
        
        **Server Response**
        
                    { // in case user has not posted any tweet
                        "statusCode": 200,
                        "message": "Tweet(s) fetched successfully.",
                        "output": {
                            "No Tweets found": "0 tweets posted by user."
                        },
                        "success": true
                    }
                    
                    { // in case data exist
                        "statusCode": 200,
                        "message": "Tweet(s) fetched successfully.",
                        "output": {
                            "Number of tweet(s) found": 1,
                            "Tweet(s) list": [
                                {
                                    "tweetId": 2,
                                    "userId": 1,
                                    "timeStamp": 1644995834184,
                                    "tweet": "the moment is critical...."
                                }
                            ]
                        },
                        "success": true
                    }
    
