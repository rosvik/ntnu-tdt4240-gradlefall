[![Build Status](https://travis-ci.com/rosvik/ntnu-tdt4240-progark-project.svg?token=g6wDiqtebzTTKGgy2fiG&branch=master)](https://travis-ci.com/rosvik/ntnu-tdt4240-progark-project)

# ntnu-tdt4240-progark-project

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