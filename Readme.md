# To run the application locally
1) Execute this in your terminal to initially allocate more memory
```
export MAVEN_OPTS=-Xmx1024m -XX:MaxPermSize=128M
```

2) Build and install all dependencies 
```
mvn install
``` 

2) Start the application
```
cd quick-web/
mvn spring-boot:run
```
The app runs on [localhost:8080/](http://localhost:8080/)

If you want to change the port that the server runs on, you will need to make
changes in [application.yml](quick-web/src/main/resources/application.yml) and [app.js](quick-web/public/js/app.js)

# Database settings
You can change these properties in [application.yml](quick-web/src/main/resources/application.yml)

Run [production.sql](db/production.sql) to create the initial tables and some data.

psql -U postgres
psql -d production -U sylvain --password

# Technology Stack
- [Spring boot](http://projects.spring.io/spring-boot/) is used which makes writing server side code a piece of cake and it handles a lot of the menial tasks with ease.
-- REST api
- UI Developed completely using Bootstrap and jquery.
- Angular JS is used in the UI to talk to the backend via the REST api provided by the server.
- Postgres used for the database. Since Hibernate is used for querying the database, it should be quite a simple change to swap the underlying database to something else like MySQL, Oracle, etc.
- Maven is used for dependency management.

# Credits
The landing page uses the template from http://startbootstrap.com/template-overviews/grayscale/
The app uses the base template from http://startbootstrap.com/template-overviews/simple-sidebar/


# TODO
- [x] Ability to register new users
- [x] Authentication
- [x] User sign in and redirect
- [x] Prevent unauthorised access
- [x] Convert to a multi module maven project
- [x] Resize images to a smaller size for faster loading
- [x] Remove unnecessary html files
- [x] Format HTML files and remove commented out code
- [x] Write instructions on how to setup database and run application

# Future tasks
- Sanitize register/sign in form to ensure a correct email is entered
- Ability to delete categories and projects
- SSL support
- Use flywayDB for management of database sql changes
- Checks for sql injection
- Check for OWASP vulnerabilities
- Ability to delete Categories and Projects
- Document the API
- Status page for users to check current/past system status (https://status.status.io/)
- Responsive website that works on mobile/tablets
- iOS/Android apps using the REST API

