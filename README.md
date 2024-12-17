# Java 21 Word Simplifier
An API that is used to take a sample of text from a .txt file, 
and simplify the passage using the top 1000 words according to Google. Note that this project was created
using **Java 21.** Please make sure that you have at most the Java 21 JDK installed before attempting to compile
and / or run this program. Additionally, this simplifier is only compatible with `.txt` files. 

**Note: This assignment was done to satisfy the requirements for the Software Design & Data Structures
module for the H.Dip. in Science (Software Development) from the Atlantic Technological University**

### Possible CLI Actions
1. Choose similarity algorithm (Cosine or Dot)
2. Choose relative path for word embeddings 
    >   This API was created with 50d Gl0ve word embeddings in mind, but should work for different sized embeddings. 
        Your mileage may vary.
3. Choose relative path for simplified word embeddings
    >    Only supports files where words are listed line-by-line (i.e. no weights associated with it) 
4. Choose relative output path 
    >   i.e. where your simplified text file will go
5. Choose relative input path 
    >   i.e. the location of the text file you would like to simplify
6. Exit Program

### How to run it
When running this program you have two options:
1) Run the included .jar file using `java -cp ./simplifier.jar ie.atu.sw.Runner` to begin the program
2) Open the src folder in a new project, compile, and run from there. 

The steps I used to generate a `.jar` artefact in Intellij can be found 
[here](https://stackoverflow.com/questions/2025607/how-to-create-a-jar-file-or-export-jar-in-intellij-idea-like-eclipse-java-arch)

### Additional information 
Additional information can be found in the included UML Diagram under `design.png`, as well as the 
accompanying `javadocs` directory which provides information on all class and method setups. 