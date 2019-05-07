[![Build Status](https://travis-ci.com/rosvik/ntnu-tdt4240-progark-project.svg?token=g6wDiqtebzTTKGgy2fiG&branch=master)](https://travis-ci.com/rosvik/ntnu-tdt4240-progark-project)

# Gradlefall

Gradlefall is an Android game built to for the software architecture course (TDT4240) at NTNU. 

## Contributing

### Plugins
#### Checkstyle
Install the `Checkstyle-IDEA` plugin in intellij:

1. `Settings` > `Plugins` > `Browse Repositories...`
2. Search `Checkstyle-IDEA` and install
3. Restart Android Studio
4. `Settings` > Search `Checkstyle`, pick `Other Settings` > `Checkstyle`
5. Add configuration file: `+`
    1. Description: `Project xml`
    2. `Local file` > `Browse` > `/game/config/checkstyle/checkstyle.xml`
    3. `Store relative to project location`
    4. `Next` untill done
6. Select the newly added xml file

Enable checkstyle as code style:

1. `Settings` > `Code style` > `Java`
2. Click the Cogwheel > `Import Scheme` > `Checkstyle` > `/game/config/checkstyle/checkstyle.xml`
3. Rename the style (Cogwheel > `Rename`) to Checkstyle
4. `OK`

Running checkstyle manually with gradle:

1. Open gradle menu
2. Run `checkstyleMain` task

#### Git pre-commit hook

Copy the file `/game/config/gitHooks/pre-commit` to `/.git/hooks/`

Alternatively, enable `Run checkstyle` checkbox in Android Studio commit dialog.

#### Lombok

Lombok can generate code with *annotation processing*: it reads your source code and writes code.
We use it to generate
```java
private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ServerMain.class)
```

by adding `@SLF4J` before the class definition:

```java
@Slf4j
public final class ServerMain {
}
```

Install lombok:

1. `Settings` > `Plugins` > `Browse Repositories...`
2. Search for `Lombok`
3. Install and restart Android Studio.
