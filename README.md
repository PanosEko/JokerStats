# JokerStats

![ezgif com-gif-maker (2)](https://user-images.githubusercontent.com/93736094/171199419-ea28fd8f-dc28-489a-90ec-84c38ba39800.gif)

JokerStats is a desktop application that was developed as part of a software development team project at Hellenic Open University and was made by me and [Ioannis Kannakis](https://github.com/IoannisKanakis).
The desktop application draws data from OPAP's web service (https://www.opap.gr/web-services) and processes it in order to present it in an intelligible way.

## Features
- Data Fetching: Retrieves data from OPAP's web service using OkHttp.
- Data Processing: Processes and organizes fetched data efficiently.
- Data Visualization: Presents wichever data are selected in clear and informative charts (pie, bar, etc.) and tables.
- Data Persistence: Saves processed data to an Apache Derby database for future access.
- Data Export: Exports data in PDF format for convenient sharing.

## Technologies Used:
- Java: Main programming language for application development.
- Swing: User interface framework for desktop application.
- Apache Derby: Embedded database for data storage.
- JPA: Object-relational mapping for database interactions.
- OkHttp: HTTP client for fetching data from OPAP's web service.
- JFreeChart: Charting library used to generate informative and visually appealing charts for data visualization.
- iTextPDF: Open-source library for creating and manipulating PDFs.




![ezgif com-gif-maker (1)](https://user-images.githubusercontent.com/93736094/171197750-99a50d7b-1d72-4c10-b21f-7e53fb60cc0e.gif)

## Packages: 
- tzokerstat: It is the entry point of the application. It is that part of the application that prepares the execution environment for the code to follow. It also contains the new exceptions that were considered necessary during the development of the application (custom exceptions).
- model: Groups the POJO classes that were created using the Java Persistence API (JPA). JPA is a programming library used to manage a database easily and efficiently through Java code. 
- gui: contains the application graphical interface. It also contains helper classes (MenuOverlay) that have to do with the look and feel of the latter. The classes of this package make use of the helper classes of the other packages to satisfy the requirements of the application.
- dataIngestion: Contains all those helper classes that take on the task of hiding the complexity of communication with external systems (OPAP API, Derby Database). Thus, these classes process and return objects that can be consumed by the classes belonging to the gui package. In addition, it contains the POJO classes created to receive the responses of API Calls.


## UML Class diagram
![TzokerStats Class Diagram](https://user-images.githubusercontent.com/93736094/171047432-141da46f-e0c9-4acd-a18a-7491fdfa97f6.png)

## Application Screenshots

![image](https://user-images.githubusercontent.com/93736094/171200060-e0651401-6d0e-4c40-b5d7-efc2341fc5da.png)

![image](https://user-images.githubusercontent.com/93736094/171200314-c6667810-32e5-4646-818c-d53a51805362.png)

![image](https://user-images.githubusercontent.com/93736094/171200572-06c0d8fd-028b-4521-a69d-e538320a0022.png)

![image](https://user-images.githubusercontent.com/93736094/171201105-362c3dd8-188d-43b2-b17f-8279b97572a6.png)

![image](https://user-images.githubusercontent.com/93736094/171200741-cd24578b-8046-4ba3-914f-607638991979.png)

![image](https://user-images.githubusercontent.com/93736094/171200836-7cf8a958-02fd-4c8a-9c58-563bbc48162a.png)

![image](https://user-images.githubusercontent.com/93736094/171200970-6cbffdbb-9c00-41e3-9b26-23e644035086.png)




