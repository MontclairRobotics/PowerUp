# PowerUp [![Build Status](https://travis-ci.org/MontclairRobotics/PowerUp.svg?branch=master)](https://travis-ci.org/MontclairRobotics/PowerUp)
2018 FRC PowerUp Code

## Table of Contents
1. [IntelliJ set up](#intellij-set-up)
2. [Eclipse set up](#eclipse-set-up)
3. [GradleRIO command reference](#gradlerio-command-reference)

## IntelliJ set up
1. Make sure IntelliJ is closed if it's open, then clone the project.
2. Double click `setup_intellij.bat`. This will generate files all the IntelliJ specific files for the project. Alternatively, you can run `./gradlew idea` in the project directory on either Powershell or a Linux shell.
3. Double click the `PowerUp.ipr` file which was just generated. IntelliJ should open the project.
4. Once IntelliJ is opened, a tooltip should open in the bottom right which says `Unlinked gradle project?`. Click `Import gradle project`.
5. Make sure `Use default gradle wrapper` is selected, then click `Finish`.
6. From the menu bar, click View > Tool Windows > Project. (Or press Alt+1).

Now your project should be set up. It's recommended you add some build tasks to IntelliJ to make building easy:

7. From the menu bar, click Run > Edit Configurations.

From here we'll repeat a couple steps to add all the build tasks we'd reasonably need:

8. Click + > Gradle.
9. Fill in Name: `Build`.
10. Fill in Gradle project by clicking on the folder icon and selecting `PowerUp`.
11. Fill in Tasks: `build`.

That's all you need to add a build task. You should repeat steps 8-11 for the following build tasks as well (replacing name and tasks with the appropriate name):
- Deploy (deploy)
- Smart Dashboard (smartDashboard)
- Offline deploy (build deploy --offline)

Once you've finished, these should all appear in the run drop down menu on the toolbar.

## Eclipse set up
1. Make sure Eclipse is closed if it's not already, then clone the project. This does not have to be in your Eclipse workspace.
2. Double click `setup_eclipse.bat`. This will generate files all the Eclipse specific files for the project. Alternatively, you can run `./gradlew eclipse` in the project directory on either Powershell or a Linux shell.
3. Open Eclipse.
4. From the menu bar, click File > Import.
5. From the drop-down menu, expand Gradle and click `Existing Gradle Projects`.
6. Click through the intro screen.
7. For the project root directory, navigate to where you cloned the project. Then click Finish.

Now your project should be set up. It's recommended you add some build tasks to Eclipse to make building easy. (Eclipse saves build tasks between projects so you can skip these steps if you've done it before):

8. From the menu bar, click Run > Run Configurations.

From here we'll repeat a couple steps to add all the build tasks we'd reasonably need:

9. Click `Gradle Project` in the menu, and then click on the new icon (on the top left of the dialog).
10. Fill in Name: `Build`
11. Fill in Gradle Tasks: `build`
12. Fill in Working Directory by clicking on Workspace and selecting PowerUp.
13. Click 'Apply'.

That's all you need to add a build task. You should repeat steps 9-13 for the following build tasks as well (replacing name and tasks with the appropriate name):
- Deploy (deploy)
- Smart Dashboard (smartDashboard)
- Offline Deploy (build deploy --offline)

To get these to show in Eclipse's run dropdown, you either need to have ran the configuration from the set up menu or you need to specify them as a "favorite". Here's the steps to do this:

1. Head to run configurations.
2. Click on the task you want to pin.
3. Go over to the `Common` tab
4. Tick the box in `Display in favorites menu` next to `Run`.

## GradleRIO command reference
Here's a collection of some useful GradleRIO commands and tips for development. Reading the full documentation [here](https://github.com/Open-RIO/GradleRIO) is recommended.

- `build` will build your code.
- `deploy` will build and deploy your code.
- `deploy --offline` will build and deploy your code over ethernet.
- `riolog` will display the RoboRIO console output.
- `smartDashboard` will launch Smart Dashboard

You can chain multiple commands in your build configuration if you so desire by separating your build tasks with a space like `deploy smartDashboard` or `deploy riolog`.
