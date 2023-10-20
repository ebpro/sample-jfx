# A sample JavaFX App

## Usage

Look at the two minimal javaFX App :
  
  * fr.univtln.bruno.samples.jfx.fxapp1.App
  * fr.univtln.bruno.samples.jfx.fxapp2.App

The application can be packaged and executed with the JavaFx Maven plugin :

```bash
mvn package
mvn javafx:run
```

but in case of a non-modular dependency this plugin doesn't work.
So a dedicated profile has been added to release the application with
the needed parts of the dependencies and the jre (using `jdeps` and `jlink`). 

```bash
mvn -P jlink package
./target/image/bin/myapp 
```