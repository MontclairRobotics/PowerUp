# FRCBase

A blank FRC GradleRIO project for internal use by Team 555. The only real improvements of this quickstart over GradleRIO is having a libs folder which this project automatically reads from. We've also done some tweaks to make set up easier, including this step-by-step documentation.

## Table of Contents
1. [IntelliJ Set Up](#intellij-set-up)
2. [Eclipse Set Up](#eclipse-set-up)
3. [GradleRIO Command Reference](#gradlerio-command-reference)

## IntelliJ Set Up
1. Clone the project.
2. Double click `setup_intellij.bat`. This will generate files all the IntelliJ specific files for the project. Alternatively, you can run `./gradlew idea` in the project directory on either Powershell or a Linux shell.
3. Double click the `FRCBase.ipr` file which was just generated. IntelliJ should open the project.
4. Once IntelliJ is opened, a tooltip should open in the bottom right which says `Unlinked gradle project?`. Click `Import gradle project`.
5. Make sure `Use default gradle wrapper` is selected, then click `Finish`.
6. From the menu bar, click View > Tool Windows > Project. (Or press Alt+1).

Now your project should be set up. It's recommended you add some build tasks to IntelliJ to make building easy:

7. From the menu bar, click Run > Edit Configurations.

From here we'll repeat a couple steps to add all the build tasks we'd reasonably need:

8. Click + > Gradle.
9. Fill in Name: `Build`.
10. Fill in Gradle project by clicking on the folder icon and selecting `FRCBase`.
11. Fill in Tasks: `build`.

That's all you need to add a build task. You should repeat steps 8-11 for the following build tasks as well (replacing name and tasks with the appropriate name):
- Deploy (deploy)
- Smart Dashboard (smartDashboard)
- Offline deploy (build deploy --offline)

## Eclipse Set Up
1. Clone the project. This does not have to be in your Eclipse workspace.
2. Double click `setup_eclipse.bat`. This will generate files all the Eclipse specific files for the project. Alternatively, you can run `./gradlew eclipse` in the project directory on either Powershell or a Linux shell.
3. Open Eclipse.
4. From the menu bar, click File > Import.
5. From the drop-down menu, expand Gradle and click `Existing Gradle Projects`.
6. Click through the intro screen.
7. For the project root directory, navigate to where you cloned the project. Then click Finish.

Now your project should be set up. It's recommended you add some build tasks to Eclipse to make building easy:

8. From the menu bar, click Run > Run Configurations.

From here we'll repeat a couple steps to add all the build tasks we'd reasonably need:

9. Click `Gradle Project` in the menu, and then click on the new icon (on the top left of the dialog).
10. Fill in Name: `Build`
11. Fill in Gradle Tasks: `build`
12. Fill in Working Directory by clicking on Workspace and selecting FRCBase.
13. Click 'Apply'.

That's all you need to add a build task. You should repeat steps 9-13 for the following build tasks as well (replacing name and tasks with the appropriate name):
- Deploy (deploy)
- Smart Dashboard (smartDashboard)
- Offline Deploy (build deploy --offline)

## GradleRIO Command Reference
Here's a collection of some useful GradleRIO commands and tips for development. Reading the full documentation [here](https://github.com/Open-RIO/GradleRIO) is recommended.

- `build` will build your code.
- `deploy` will build and deploy your code.
- `deploy --offline` will build and deploy your code over ethernet.
- `riolog` will display the RoboRIO console output.
- `smartDashboard` will launch Smart Dashboard

You can chain multiple commands in your build configuration if you so desire by separating your build tasks with a space like `deploy smartDashboard` or `deploy riolog`.
