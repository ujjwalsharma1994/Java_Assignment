# Java_Assignment

Task 1: CREATE A User

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
