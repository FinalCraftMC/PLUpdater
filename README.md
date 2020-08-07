# PLUpdater

In genereal, when you need to update a plugin.jar you need to stop the server, then replace the jar with the new one, and then start the server again. If you just replace the jar while the server is still turned on you will cause some weird problems to the server.

This coremod aims to fix this problem.
#### What it does?

This Core-Mod will create a folder called PLUpdater in your server directory, to use it it just drag the updates at this folder and your old plugins will be replaced with the new ones.

###### How it works?

This mod will look inside the folder "PLUpdates" and get the bukkit-plugin-name of every bukkit .jar that is inside it, and then search in the plugins folder for the jar with the same plugin-identifier to delete it and move the new one to the new location!


# Important !!!

This mod does not work on WINDOWS!
