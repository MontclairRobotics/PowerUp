# FRCBase #

A blank FRC GradleRIO project for internal use by Team 555. The only real improvements of this quickstart over GradleRIO is having a libs folder which this project automatically reads from. We've also done some tweaks to make set up easier, including this step-by-step documentation.

## IntelliJ Set Up ##
1. Clone the project
2. Double click `setup_intellij.bat`. This will generate files all the IntelliJ specific files for the project.
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
