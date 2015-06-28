# Execute this in your terminal to initially allocate more memory
export MAVEN_OPTS=-Xmx1024m -XX:MaxPermSize=128M

# To run the application locally
mvn spring-boot:run


# Database settings
db: production
user: Sylvain
pass: dubai

psql -U postgres
psql -d production -U sylvain --password



# Please obtain a Google maps developer key (https://developers.google.com/maps/) and replace the one in /public/index.html

# Credits
The landing page uses the template from http://startbootstrap.com/template-overviews/grayscale/
The app uses the base template from http://startbootstrap.com/template-overviews/simple-sidebar/


# TODO
- Remove TaskAlreadyExistsException
- Check Task/Project repo custom hibernate functions and optimize
- Add server IP to html/javascript files dynamically

- Remove unnecessary html files
- Format HTML files and remove commented out code

- Comment java code
- Comment html code
- Comment javascript code

- Write instructions on how to setup database and run application
