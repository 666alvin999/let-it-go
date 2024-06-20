# Let it go

---

## Set up:

To set up your project :
- Get the database wallet
- Name it 'oracle-wallet'
- Put it at the root of the project
- Go in the src/main/resources/application.properties
- Paste the config :

```properties
spring.application.name=let-it-go
logging.level.org.letitgo=debug

spring.datasource.url=jdbc:oracle:thin:@letitgo_high?TNS_ADMIN=./oracle-wallet
spring.datasource.username={username}
spring.datasource.password={password}
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.jpa.hibernate.ddl-auto=update
```

Now, the app is ready to start.

---

## Troubleshoot

- When trying to start the app, you receive an error talking about ACL, it means your ip address is not registered in the authorized ip addresses list. Find it, give it to the administrator and it will add your ip address.