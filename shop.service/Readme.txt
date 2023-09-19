This java spring project is one of the multiple services for a project called ShareAndSell
It uses postgres as database.

To run this program you just need to have docker installed and of course java at least 17.

To run you need to open the terminal in this directory and run:
    - docker-compose up --build
This code will build the shopservice container and the images for postgres and the java spring application.


If there is no volume stored of the respective database it will work just fine, but if there is volume of a previous attempt it may not create the database for this project, and it will show in the terminal, you can directly create the missing database, or just run this code to clean all previous volume:
    - docker-compose down -v
And finally run again:
    - docker-compose up --build