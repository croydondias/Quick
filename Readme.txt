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
