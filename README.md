# Lab Report REST API

This project is a REST API built using Java Spring Boot, Maven, Spring Data JPA and MySQL.
It provides endpoints for managing lab reports.

## How to Run

1.  **Clone the project** from GitHub or download it as a zip file and extract it to your computer.

2.  Open a terminal or command prompt and **navigate to the root directory** of the project.

3.  **Import the SQL script** located in the `sql-script` folder to set up database schema and sample data.

4.  **Configure MySQL database settings** in `src/main/resources/application.properties`. Replace the url, username and
    password properties with your MySQL configuration.

5.  After configuring database, **use the Maven Wrapper to compile the project** and download dependencies. Use the following commands to compile the project:

    For Unix/Linux/MacOS:

    ```
    ./mvnw clean install
    ```

    For Windows:

    ```
    mvnw.cmd clean install
    ```

6.  After Maven compiles the project and downloads necessary dependencies, you can **start the application** by using
    the following command:

    ```
    ./mvnw spring-boot:run
    ```

7.  Once the app is running, you can test the API using tools like **Postman**. Here are **_some endpoints_** and their usage:

    | HTTP Method | Endpoint            | Description                                                |
    | ----------- | ------------------- | ---------------------------------------------------------- |
    | GET         | /api/reports        | Retrieve all reports                                       |
    | GET         | /api/reports/asc    | Retrieve all reports in ascending order by report date     |
    | GET         | /api/reports/{id}   | Retrieve a specific report by its ID                       |
    | POST        | /api/reports        | Create a new report                                        |
    | PUT         | /api/reports/{id}   | Update an existing report                                  |
    | DELETE      | /api/reports/{id}   | Delete a specific report by its ID                         |
    | GET         | /api/patients/asc   | Retrieve all patients in ascending order by full names     |
    | GET         | /api/patient/search | Search for patients by first name and last name parameters |

    - Sample **GET** request - `localhost:7070/api/reports/3`
    - Response with HTTP status 200:

    ```json
    {
      "id": 3,
      "reportCode": "RP3401-4814",
      "diagnosisTitle": "Üçüncü tanı başlığı",
      "patient": {
        "id": 1,
        "firstName": "Ali",
        "lastName": "Yılmaz",
        "patientDetail": {
          "id": 1,
          "identityNumber": 39593859155,
          "email": "ali@ornek.com",
          "phoneNumber": "+902345678910"
        }
      },
      "reportDetail": {
        "id": 3,
        "diagnosisDetails": "Üçüncü rapor için tanı detayları",
        "reportDate": "2024-02-25",
        "reportImage": null
      }
    }
    ```
    
    - [Click here for more endpoints](#endpoints)

## ERD

<p align = "center">
    <img src="https://github.com/yusufemrebilgin/lab-report-api/blob/main/images/erd.png">
</p>

## Endpoints

- [Report](#report)
- [ReportDetail](#reportdetail)
- [ReportImage](#reportimage)
- [Patient](#patient)
- [PatientDetail](#patientdetail)
- [LabTechnician](#labtechnician)

### Report

| HTTP Method | Endpoint          | Description                                            |
| ----------- | ----------------- | ------------------------------------------------------ |
| GET         | /api/reports      | Retrieve all reports                                   |
| GET         | /api/reports/asc  | Retrieve all reports in ascending order by report date |
| GET         | /api/reports/{id} | Retrieve a specific report by its ID                   |
| POST        | /api/reports      | Create a new report                                    |
| PUT         | /api/reports/{id} | Update an existing report                              |
| DELETE      | /api/reports/{id} | Delete a specific report by its ID                     |

### ReportDetail

| HTTP Method | Endpoint                        | Description                                  |
| ----------- | ------------------------------- | -------------------------------------------- |
| GET         | /api/reports/{reportId}/details | Retrieve a specific detail by report ID      |
| POST        | /api/reports/{reportId}/details | Create a new report detail                   |
| PUT         | /api/reports/{reportId}/details | Update an existing report detail             |
| DELETE      | /api/reports/{reportId}/details | Delete a specific report detail by report ID |

### ReportImage

| HTTP Method | Endpoint                              | Description                                 |
| ----------- | ------------------------------------- | ------------------------------------------- |
| GET         | /api/reports/{reportId}/details/image | Retrieve a report image by report Id        |
| POST        | /api/reports/{reportId}/details/image | Upload a new report image                   |
| DELETE      | /api/reports/{reportId}/details/image | Delete a specific report image by report ID |

### Patient

| HTTP Method | Endpoint                      | Description                                                |
| ----------- | ----------------------------- | ---------------------------------------------------------- |
| GET         | /api/patients                 | Retrieve all patients                                      |
| GET         | /api/patients/{id}            | Retrieve a specific patient by its ID                      |
| GET         | /api/patients/asc             | Retrieve all patients in ascending order by full names     |
| GET         | /api/patient/search           | Search for patients by first name and last name parameters |
| GET         | /api/patient/{identityNumber} | Search for patients by identity number                     |
| POST        | /api/patients                 | Create a new patient                                       |
| PUT         | /api/patients/{id}            | Update an existing patient                                 |
| DELETE      | /api/patients/{id}            | Delete a specific patient by its ID                        |

### PatientDetail

| HTTP Method | Endpoint                  | Description                                      |
| ----------- | ------------------------- | ------------------------------------------------ |
| GET         | /api/patients/{patientId} | Retrieve a specific patient detail by patient ID |
| POST        | /api/patients/{patientId} | Create a new patient detail                      |
| PUT         | /api/patients/{patientId} | Update an existing patient detail                |
| DELETE      | /api/patients/{patientId} | Delete a specific patient detail by patient ID   |

### LabTechnician

| HTTP Method | Endpoint                     | Description                                                   |
| ----------- | ---------------------------- | ------------------------------------------------------------- |
| GET         | /api/lab                     | Retrieve all lab technicians                                  |
| GET         | /api/lab/{id}                | Retrieve a specific lab technician by its ID                  |
| GET         | /api/lab/search              | Search for technicians by first name and last name parameters |
| GET         | /api/lab/search/{hospitalId} | Search for technicians by hospital ID                         |
| POST        | /api/lab/                    | Create a new lab technician                                   |
| PUT         | /api/lab/{id}                | Update an existing lab technician                             |
| DELETE      | /api/lab/{id}                | Delete a specific lab technician by its ID                    |
