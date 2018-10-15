This is the front end for the Spotify assessment challenge. 

To run the the spring backend API, on your terminal run 
    mvn:spring-boot run 

Then start the client side server by 
changing to the "frontend" directory and running the following commands

 npm install
 yarn start


 Backend: 
 The backend server upon starting will make a call to the IEXApi and pull in the most up to date stocks while the market is open. Once the stocks are pulled in they will be loaded into a database for easier searching, sorting, paging etc. 
The backend also handles all user functionality via "POST" request to the api located in the controller directory.

Frontend: 
The frontend server upon starting the server, users will only have access the login and register page as an unauthorized user. Once a user registers, they will be redirected to the login page. Once a user logs in, they will have access to to Stocks, Portfolio and Transaction. 

Stocks: pulls all stocks from the backend's DB. Users may purchase a stock on the page by clicking the buy button. A modal will popup and ask the user how many shares. 

Portfolio: Shows a user how many stocks they have invested in. Each stock will change color to reflect the changes of the opening price and the current stocks price. Green if the current stock is greater, red if the current stock is lower and grey if it is unchanged. 

Transactions: Pulls a list of all of the users transactions. 


