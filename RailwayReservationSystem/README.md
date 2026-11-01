# Railway Reservation System

A Java Swing + MySQL desktop application for a basic railway reservation workflow.

## Features
- User registration and login
- Search trains by source and destination
- Passenger details and journey-date validation
- Book a seat and receive a seat number
- View personal booking history
- Cancel a confirmed booking and return the seat to availability
- Transaction-based booking/cancellation to reduce overbooking risk

## Requirements
- JDK 21 (or compatible JDK)
- MySQL Server 8.x
- MySQL Connector/J JAR
- VS Code with Java support, or another Java IDE

## Database setup
1. Open `database.sql` in MySQL Workbench and execute the complete script.
2. Copy `db.properties.example` to a new file named `db.properties` in the project root.
3. Edit `db.properties` with your MySQL username and password.
4. Copy your MySQL Connector/J JAR into the project's `lib` folder.

Do not commit `db.properties`; it is excluded by `.gitignore`.

## Run from Windows PowerShell (without Maven)
Run these commands from the project root. Replace the JAR filename if yours is different.

```powershell
New-Item -ItemType Directory -Force out
$jar = (Get-ChildItem .\lib\*.jar | Select-Object -First 1).FullName
javac -cp $jar -d out (Get-ChildItem -Recurse .\src\main\java\*.java | ForEach-Object { $_.FullName })
java -cp "out;$jar" in.sqrock.railway.Main
```

If your Connector/J JAR is stored elsewhere, copy it into `lib` first. The project cannot connect to MySQL without the Connector/J driver.

## Notes
- This is an educational internship project, not a production railway booking system.
- The sample train data is fictional and for demonstration only.
- Passwords are hashed with SHA-256 for this learning project. Production systems should use a dedicated password hashing algorithm such as Argon2id, bcrypt, or scrypt.
- The sample model keeps train availability as a shared counter; for a real booking platform, availability should be tracked per journey date and class with stronger concurrency controls.
