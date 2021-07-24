# Tower Defence Shooter Game Thing
This is the source code for a game that combines the tower defence genre with shooter mechanics. 

- You can find the released versions on itch here: https://agoogaloo.itch.io/untitled-tower-shooty-game-thingy?password=1
- If you want to stay up to date with new updates and stuff you can  join the discord here: https://discord.gg/CQbasxGFzF 

# Compiling
All the code is in java and was written in eclipse, so compiling shouldn't be that complicated but I may as well write the steps here.

### Getting the jar file
I use eclipse to export the code to a runnable jar file, but there is probably a bunch of other things you could use instead if you don't want to download it. 
To get the jar in eclipse dowload the source code, and import the project into eclipse. Once the project is imported you should be able to go file > export > runnable jar file. 
if the game doesn't show up try going to the main class and running it before exporting.

### Getting the assets
The jar file doesn't include the assets, so make a new folder somewhere and copy the entire res folder into it, as well as the jar file made in the previous step

### Making it a .exe
If you have java on your computer the jar should run the game fine as it is and you don't really need this step, but if you want to have
it as a exe so you can run it on a different computer or something you can. I'm sure there are different ways fo doing this, but I use launch4j, and a folder with the 
java runtime in it. First you need to get launch4j, and get a version of the java runtime. Make another folder inside of the game folder, and name it JRE so that the exe 
can find a version of java to run no matter what computer its on, and start up launch4j. set the output file, to be inside the game folder, the jar to the jar file, 
the bundled jre path to JRE, and the search option to be private JDK runtimes. now just push the build button at the top, and there should be an exe of the game that you can run.

\*note the exe only runs if it is in the same folder as the JRE and res folder, so move the entire folder not just the exe\*
