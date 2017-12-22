##Power Up [![Build Status](https://travis-ci.org/MontclairRobotics/PowerUp.svg?branch=i2)](https://travis-ci.org/MontclairRobotics/PowerUp)

This is the second implementation of GradleRIO and the test of the IntelliJ platorm on Power Up Code for 2018

## GradleRIO Command Reference
Here's a collection of some useful GradleRIO commands and tips for development. Reading the full documentation [here](https://github.com/Open-RIO/GradleRIO) is recommended.

- `build` will build your code.
- `deploy` will build and deploy your code.
- `deploy --offline` will build and deploy your code over ethernet.
- `riolog` will display the RoboRIO console output.
- `smartDashboard` will launch Smart Dashboard

You can chain multiple commands in your build configuration if you so desire by separating your build tasks with a space like `deploy smartDashboard` or `deploy riolog`.
