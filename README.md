# Elevator Core System

A simulation of an elevator system to handle elevator events, calculate optimal paths, and manage user interactions.
The system is designed to be extensible with support for different types of elevators.

## How to Run The App and Test

Navigate to the root directory and run the following command:

```bash
mvn spring-boot:run  
mvn test
```

Note: application.properties file contains all the possible configurations for the elevator system

## API Endpoints

### Elevator Management

The application exposes a set of Restful API endpoints to manage the position of the elevator.
After calling to these endpoints, you can check the elevator actions in the terminal.

#### 1. Call elevator

**Endpoint**: `POST /api/elevator/calls`

  ```json
  [
  {
    "fromFloor": 5,
    "elevatorType": "PUBLIC | FREIGHT"
  },
  {
    "fromFloor": 25,
    "elevatorType": "PUBLIC | FREIGHT"
  }
]
```
Note: You can combine PUBLIC and FREIGHT call in the same input list

#### 2. Select floors

**Endpoint**: `POST /api/elevator/select_floors`

  ```json
  {
  "toFloors": [
    36,
    -1
  ],
  "elevatorType": "PUBLIC | FREIGHT",
  "accessKey": 4
}

```
Note: The accessKey is the userId, and it is an optional attribute for restricted floors in the PUBLIC elevator,
and not necessary for the FREIGHT elevator.

#### 2. Update weight

**Endpoint**: `POST /api/elevator/weight`

  ```json
 {
  "measure": 1500,
  "elevatorType": "PUBLIC | FREIGHT"
}

```
Note: if the weight is exceeded, the elevator will lock until the weight drops.

### User Management

The application exposes a set of Restful API endpoints to manage users within the elevator system.


#### 1. Add User

**Endpoint**: `POST /api/users`

**Input**: JSON representation of a User.

Sample Input: 
```json
{
"firstname": "Santiago",
"admin": true
}
```

Sample Output:

```json
{
  "id": 7,
  "firstname": "Santiago",
  "admin": true
}
```

#### Get User by ID

**Endpoint**: `GET /api/users/{id}`

**Path Variable**: id (Integer) - The ID of the user to retrieve

Retrieves user details by their ID.

#### Delete User by ID

**Endpoint**: `DELETE /api/users/{id}`

**Path Variable**: id (Integer) - The ID of the user to delete






