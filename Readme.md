# Execute this in your terminal to initially allocate more memory
export MAVEN_OPTS=-Xmx1024m -XX:MaxPermSize=128M

# To run the application locally

1) Build and install all dependencies 
mvn install 

2) Start the application
cd quick-web/
mvn spring-boot:run

The app runs on localhost:8080/

# Database settings
You can change these properties in [application.yml](quick-web/src/main/resources/application.yml)

Run [production.sql](db/production.sql) to create the initial tables and some data.

psql -U postgres
psql -d production -U sylvain --password

# Technology Stack
- Spring boot server side
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
- [] Sanitize register form
- [] Sanitize sign in form
- [] Remove TaskAlreadyExistsException
- [] Check Task/Project repo custom hibernate functions and optimize
- [] Add server IP to html/javascript files dynamically

- [] Resize images to a smaller size for faster loading
- [] Remove unnecessary html files
- [] Format HTML files and remove commented out code

- [] Comment java code
- [] Comment html code
- [] Comment javascript code

- [x] Write instructions on how to setup database and run application

# Future tasks
- SSL support
- Use flywayDB for management of database sql changes
- Checks for sql injection
- Check for OWASP vulnerabilities
- Ability to delete Categories and Projects
- Document the API
- Status page for users to check current/past system status (https://status.status.io/)
- Responsive website that works on mobile/tablets
- iOS/Android apps using the REST API

