## Volleyball Tournament Scoreboard

### Opportunity 
Collecting scores from volleyball courts and posting them on scoreboard.
![image](images/courts.png)

---
### Solution Overview
Automated scorecard collection and posting scores on scoreboard using IoT devices and Kafka
![image](images/architecture.png)

---
### Development Stack
- Scala and sbt (Compiler and Build tool)
- Apache Kafka Java Client library (Producer and Consumer)
- Akka-Http Scala library (Http and WebSocket server)
- Apache Spark Streaming (Tournamnet statistics)
- D3.js (Browser visualization)

---
### Solution - Score Collection
**Scorecard** data is collected from volleyball courts and posted on Scoreboard
![image](images/scorecard.png)

---
### Solution - Score Visualization
**Scoreboard** displays scores from all courts in real-time
![image](images/scoreboard.png)

---
### Code structure (Classes and Objects)
![image](images/package.png)
