# Bitcoin Transaction management

### Installing Java 8, Spring Tool Suit 4 & MySQL Server 5.6 & Docker & RabbitMQ
- Java 8 : https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html
- Spring Tool Suit download link : https://spring.io/tools
- MySQL Server 5.6 : https://downloads.mysql.com/archives/community/?version=5.6.23
- MySQL Workbench 8.0 CE : https://dev.mysql.com/downloads/workbench/
- Docker : https://docs.docker.com/desktop/install/windows-install/

### Running Examples
- Download the zip or clone of btctransactioncommand-service and btctransactionquery-service the Git repository.
- Unzip the zip file
- After installing Docker, run docker-compose.yml in the unzip folder for RabbitMQ
- Get 2 SQL files in db folder in unzipped file and import in MySQL Server
- Open Spring Tool Suit 4 IDE
   - File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip of btctransactioncommand-service
   - Select the right project
- Right Click on the project file and Run As -> Maven Build... -> enter "package" in Goals text box and Click "Run".
- You will see the jar file of target file in your project.
- Please import btctransactionquery-service and follow the instruction like btctransactioncommand-service
- All you all set, run command box and change your directory to target folder of your btctransactioncommand-service microservice 
- Type "java -jar jar-name"
- You will see btctransactioncommand-service microservice is running
- Please run btctransactionquery-service as btctransactioncommand-service microservice
- You all are ready to test

### API call & results
- You can then save the the transaction by calling that link http://localhost:8081/restapi/btctranscommand-service/transactions with body using PostMan
![image](https://user-images.githubusercontent.com/46620054/230017922-538b40ed-5286-4237-acae-a01813dc1348.png)

- You can call the the transaction for each 1 hour by calling that link http://localhost:8080/restapi/btctransquery-service/transactions with body using PostMan
![image](https://user-images.githubusercontent.com/46620054/230018430-d9581e13-cb03-444f-b857-815bc5878d28.png)
