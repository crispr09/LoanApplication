# LoanApplication

my todo list

1 add more custome exception in my global @ControllerAdvice class
2 add more logging 
3 add unit test cases
4 use privlages
5 add all CRUD operations for appropriate entities
6 add integration test cases with test database
7 actuator , prometheous and grephana integration
8 different profiles 
9 move the data to test H2 database for integration testing
10 provide proper CRUD operations for checkListItems
11 modify updatechecklist to be a postMapping method



silent features 

test data created and setup using code can be found in (SetupData.java)
custime exception
spring security to check user and team authorization
custom authentication from DB tables
user specific authorization using spring security expression
logging is added

two user added
 username: sachin 
 password: 111
 username: kumar
 password: 222
 
 usages
 IMPORTANT MSG- teamId is added to shocase spring secutiry authorization done based on user-team authorization
 
 open loan:
 /openloan/teamId/loanId
 e.g
 /openloan/1/1235


close loan:
 /closeloan/teamId/loanId
 e.g
 /closeloan/1/1235
 
 updatechecklist:
 
 /updatechecklist/{teamId}/{checklistId}
 /updatechecklist/2/3
 
 get all loan applications:
  http://localhost:8080/loans/teamId
 http://localhost:8080/loans/1
 
 
 /checklist/{teamId}/{checklistId}
 
 
 
 


