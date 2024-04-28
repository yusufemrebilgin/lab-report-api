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

7.  Once the app is running, you can test the API using tools like **Postman**. Here are **_some endpoints and their samples:_**

## Sample Requests

- **GET** request - localhost:7070/api/reports/10
- Response with HTTP status 404 Not Found

```json
{
    "timestamp": "2024-04-29T01:21:52.1524944",
    "message": "Report with id 10 not found",
    "details": "uri=/api/reports/10"
}
```

- **GET** request - localhost:7070/api/patients/1
- Response with HTTP status 200 OK

```json
{
    "id": 1,
    "firstName": "Ali",
    "lastName": "Yılmaz",
    "patientDetail": {
        "id": 1,
        "identityNumber": 39593859155,
        "email": "ali@ornek.com",
        "phoneNumber": "+902345678910"
    },
    "reports": [
        {
            "id": 1,
            "reportCode": "RP3491-5830",
            "diagnosisTitle": "Birinci tanı başlığı",
            "reportDetail": {
                "id": 1,
                "diagnosisDetails": "Birinci rapor için tanı detayları",
                "reportDate": "2024-01-15",
                "reportImage": null
            }
        },
        {
            "id": 2,
            "reportCode": "RP3460-5810",
            "diagnosisTitle": "İkinci tanı başlığı",
            "reportDetail": {
                "id": 2,
                "diagnosisDetails": "İkinci rapor için tanı detayları",
                "reportDate": "2023-04-24",
                "reportImage": null
            }
        },
        {
            "id": 3,
            "reportCode": "RP3401-4814",
            "diagnosisTitle": "Üçüncü tanı başlığı",
            "reportDetail": {
                "id": 3,
                "diagnosisDetails": "Üçüncü rapor için tanı detayları",
                "reportDate": "2024-02-25",
                "reportImage": null
            }
        }
    ]
}
```

- **GET** request - localhost:7070/patients/search?name=B
- Response with HTTP status 200 OK

```json
[
    {
        "id": 2,
        "firstName": "Beyza",
        "lastName": "Kaya",
        "patientDetail": {
            "id": 2,
            "identityNumber": 39591867107,
            "email": "beyza@ornek.com",
            "phoneNumber": "+901234567890"
        },
        "reports": [
            {
                "id": 4,
                "reportCode": "RP3414-1851",
                "diagnosisTitle": "Dördüncü tanı başlığı",
                "reportDetail": {
                    "id": 4,
                    "diagnosisDetails": "Dördüncü rapor için tanı detayları",
                    "reportDate": "2023-11-28",
                    "reportImage": null
                }
            }
        ]
    },
    {
        "id": 4,
        "firstName": "Begüm",
        "lastName": "Demir",
        "patientDetail": {
            "id": 4,
            "identityNumber": 37869947353,
            "email": "begum@ornek.com",
            "phoneNumber": "+901853486736"
        },
        "reports": [
            {
                "id": 7,
                "reportCode": "RP3401-3361",
                "diagnosisTitle": "Yedinci tanı başlığı",
                "reportDetail": {
                    "id": 7,
                    "diagnosisDetails": "Yedinci rapor için tanı detayları",
                    "reportDate": "2024-03-29",
                    "reportImage": null
                }
            }
        ]
    }
]
```

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
| GET         | /api/reports/{id} | Retrieve a specific report by its ID                   |
| GET         | /api/reports/asc  | Retrieve all reports in ascending order by report date |
| GET         | /api/reports/desc | Retrieve all reports in descending order by report date|
| POST        | /api/reports      | Create a new report                                    |
| PUT         | /api/reports/{id} | Update an existing report                              |
| DELETE      | /api/reports/{id} | Delete a specific report by its ID                     |

### ReportDetail

| HTTP Method | Endpoint                  | Description                                  |
| ----------- | ------------------------- | -------------------------------------------- |
| GET         | /api/reports/{id}/details | Retrieve a specific detail by report ID      |
| POST        | /api/reports/{id}/details | Create a new report detail                   |
| PUT         | /api/reports/{id}/details | Update an existing report detail             |
| DELETE      | /api/reports/{id}/details | Delete a specific report detail by report ID |

### ReportImage

| HTTP Method | Endpoint                        | Description                                 |
| ----------- | ------------------------------- | ------------------------------------------- |
| GET         | /api/reports/{id}/details/image | Retrieve a report image by report Id        |
| POST        | /api/reports/{id}/details/image | Upload a new report image                   |
| DELETE      | /api/reports/{id}/details/image | Delete a specific report image by report ID |

### Patient

| HTTP Method | Endpoint                       | Description                                            |
| ----------- | ------------------------------ | ------------------------------------------------------ |
| GET         | /api/patients                  | Retrieve all patients                                  |
| GET         | /api/patients/{id}             | Retrieve a specific patient by its ID                  |
| GET         | /api/patients/asc              | Retrieve all patients in ascending order by full names |
| GET         | /api/patients/search           | Search for patients by first name parameter            |
| POST        | /api/patients                  | Create a new patient                                   |
| PUT         | /api/patients/{id}             | Update an existing patient                             |
| DELETE      | /api/patients/{id}             | Delete a specific patient by its ID                    |

### PatientDetail

| HTTP Method | Endpoint           | Description                                      |
| ----------- | ------------------ | ------------------------------------------------ |
| GET         | /api/patients/{id} | Retrieve a specific patient detail by patient ID |
| POST        | /api/patients/{id} | Create a new patient detail                      |
| PUT         | /api/patients/{id} | Update an existing patient detail                |
| DELETE      | /api/patients/{id} | Delete a specific patient detail by patient ID   |

### LabTechnician

| HTTP Method | Endpoint                       | Description                                                  |
| ----------- | ------------------------------ | -------------------------------------------------------------|
| GET         | /api/techs                     | Retrieve all lab technicians                                 |
| GET         | /api/techs/{id}                | Retrieve a specific lab technician by its ID                 |
| GET         | /api/techs/search              | Search for technician by first name and last name parameters |
| GET         | /api/techs/search/{hospitalId} | Search for technician by hospital ID                         |
| POST        | /api/techs                     | Create a new lab technician                                  |
| PUT         | /api/techs/{id}                | Update an existing lab technician                            |
| DELETE      | /api/techs/{id}                | Delete a specific lab technician by its ID                   |
