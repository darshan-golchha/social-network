runFDTests: 
	javac -cp ../junit5.jar *.java
	java -jar ../junit5.jar --class-path=. --select-class=FrontendDeveloperTests
compile:
	javac -cp .:../junit5.jar *.java
run: compile
	java Frontend
clean:
	rm -f *.class

runBDTests:
	javac FrontendInterface.java
	javac BackendInterface.java
	javac ShortestPathInterface.java
	javac -cp ../junit5.jar:. DijkstraGraph.java
	javac -cp ../junit5.jar:. Backend.java
	javac -cp ../junit5.jar:. BackendDeveloperTests.java
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests 
