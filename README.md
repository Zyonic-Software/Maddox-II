# Maddox V2
![lines of code](https://img.shields.io/tokei/lines/github.com/Zyonic-Software/Maddox-V2?style=for-the-badge)

## Index

- [What it's for](https://github.com/Zyonic-Software/Maddox-V2#what-its-for)
- [Why Maddox?](https://github.com/Zyonic-Software/Maddox-V2#why-use-maddox)
- [Getting started](https://github.com/Zyonic-Software/Maddox-V2#getting-started)
- [Developing with Maddox](https://github.com/Zyonic-Software/Maddox-V2#developing-with-maddox)

## What it's for

Maddox is a generic Discord-Moderation bot that is open for anyone to use and Modify.

Maddox is also built to be a great foundation for any type of Discord-Bot.

If you want to contribute / suggest features feel free to open up an Issue or a Pull-Request.
I will try to review your contibutions / suggestions as fast as possible.

## Why use Maddox?

As stated, Maddox is primerily built to be a foundation for the creation of other Discord bots so it is taylor-built for this task.
It includes a Custom Command-Event and Guild / Member object optimized for use with Customprefix- & Language- Systems.

You can also just download Maddox and use it as it is for normal Moderation-Purposes if you like the look of a Bot special to your Server.

## Getting started

### I - Installing the necessary Software

You will need to install the following Pieces of Software to Correctly use Maddox:
- MySQL
- Java (14)

### II - Downloading Maddox

To download you will have to navigate to the "Releases" Section which is located [here](https://github.com/Zyonic-Software/Maddox-V2/releases)


![Location for Releases](https://image.sv-studios.net/4270aec26507643453471aaddef316edb.png)


After that you should see a List of Items to download under the Release's Description. You only need to Download the .jar File.


![Location for Jar](https://image.sv-studios.net/4f484cbda2a8b5e7821132593465ab64a.png)


After klicking on the File the download should commence automatically.
Next, navigate to your Downloads-Folder where you should find the .jar File.
Move the file to a Location where you're comfortable with it Creating various different Files.

### III - Getting up & running

After navigating to your File open a Command promt in the designated location by clicking the address-bar in the Windows-Explorer.


![Location-Address Bar](https://image.sv-studios.net/4b34ced850f30946c6fc486fad788e358.png)


Enter cmd into this Bar.


![cmd](https://image.sv-studios.net/41ee23e069ed5860739d93a1f2f66714e.png)


After hitting enter you should see a Command-Promt which should look something like this:


![Commandprompt](https://image.sv-studios.net/4d4f6373d819bd549a7ed5b4f41c460a1.png)


Enter 'java -jar Maddox-V2-X.X.X.jar' or 'java -Xmx256M -jar Maddox-V2-X.X.X-jar' into the commandpromt. The -Xmx option Caps the RAM that Maddox can use to the specefied value.
After Executing this Command Maddox Should generate a Config-File called 'config.yml'


![ConfigLocation](https://image.sv-studios.net/48f9a9302f4e5234d6786561e14781b67.png)


Next, Head to your Discord-Developer Dashboard and get a Bot token by Creating a new Application (or using an exsisting one), creating a Bot and copying the bot-secret.
Also, enable all Privileged Gateway-Intents.

Paste this token here.


![TokenLocation](https://image.sv-studios.net/4d1bf40313494d1fb4d6b7789c26ee100.png)


Now enter the same Command as before into your command-promt, and now you should be done!


![Done!](https://image.sv-studios.net/48a1e0420d00bf7612a3b174a914d0307.png)



## Developing with Maddox 

### I - Platform

Maddox is written in Java 14 with [Bellsoft's 'Liberica JDK'](https://bell-sw.com/) so I suggest running it on that plattform. It will probably also work with
other JDK's and Java versions, but it is possible that bugs & problems may accur that I have not accounted for.

### II - Custom Objects

Maddox uses custom objects like 'CommandEvent', 'MaddoxGuild' and 'MaddoxMember'. These objects were created so that I could implement Maddox-Specific features
But this also means, that they are not suitable for every purpose, especially not if you are creating a Musicbot.
For that reason you can either Cutomize these Objects, or work around them outright by just getting the "Normal" [JavaDiscordAPI-Objects](https://github.com/DV8FromTheWorld/JDA)

```Java
@Override
protected void execute(CommandEvent event, MaddoxMember sender, MaddoxGuild server) {
    sender.getMember() //this returns JDA's Member-Object
    
    server.getGuild() //this returns JDA's Guild-Object
}
 ```

### III - Command Handling

My way of handling Commands is probably a bit different to what you are used to.
Maddox uses the Command-Class to identify commands.

To add a Command you will first need to extend the Command-Class in your desired Class

```Java
public class TestCommand extends Command {

}
```

Because Command is an abstract class you will have to implement the abstract method withon command. This is the "execute" method, which will be run upon your command
being triggered.
Most IDE's will show you which methods to implement, so you can just klick to add them.

after this your Class should look something like this:

```Java
public class TestCommand extends Command {

  @Override
  protected void execute(CommandEvent event, MaddoxMember sender, MaddoxGuild server) {
  
  }
}
```

Now you will need to define what the name of your command is.
If you want your command to run on ``!test`` the name of your command is "test"
You will have to register the name of your command in the constructor of your class by referencing the "name" variable from the parent-class.

after doing this your Class should look something like this:

```Java
public class TestCommand extends Command {

  public TestCommand(){
    this.setName("test");
  }

  @Override
  protected void execute(CommandEvent event, MaddoxMember sender, MaddoxGuild server) {
  
  }
}
```

To make your command runnable you will need to register it in the CommandHandler.
You can do this by running the this at startup:

```Java
CommandHandler commandHandler = new CommandHandler(maddox); //Replace "maddox" with the name of your Maddox-object

commandHandler.registerCommand(new TestCommand());
```
If you are forking Maddox, just enter ``commandHandler.registerCommand(new TestCommand());`` into the [CommandManager](https://github.com/Zyonic-Software/Maddox-V2/blob/master/src/main/java/com/zyonicsoftware/maddox/core/management/CommandManager.java) class

### IV - Help Building

Maddox has a helpbuilder that will automatically create the help-menu with all registered Commands.
To make the command ``!help <your command>`` actually show some useful Information you will have to define a few things to the command in the Constructor of your class.
These things are:

- The category the command should be listed under in the complete Help-menu
- A description telling the users of your bot what your command is doing
- A Syntax-Depiction to show how your command should be used.

All this will be defined in the constructor and should look something like this:

```Java
public TestCommand() {
        this.setName("test");
        this.setCategory("Testing...");
        this.setDescription("Just a Command for Testing");
        this.setSyntax("<PREFIX>test"); //"<PREFIX>" will be replaced with the set Prefix.
        this.setShowInHelp(true); //Enables you to hide your command from the help menu - enabled by default
        this.setAllowExecutionOnMessageEdit(true);//Enables the Command to be run when the Message is edited
        this.setCommandHelpViewPermission(CommandHelpViewPermission.EVERYONE); //defines who can see the command
    }
```

### V - Database

To use Maddox to its full extend you will need a MySQL Database, so it can store the required data.
So, how should your Database be configured so Maddox can work with it?.

You will need **five** Tables with the following Columns:

#### Server_Settings

- id
- prefix
- language

#### Server_Automatic_Roles

- id
- roles

#### Server_Join_Messages & Server_Leave_Messages (They are the Same)

- id
- message
- channel
- enabled

#### Private_Join_Message

- id
- message
- enabled

#### Server_Commands_Toggle

- id
- enabled_commands


(Stuff *will* be added)

## FAQ

### Why V2?
Simple: Because V1 is almost 2 Years old by now and Discord has changed a lot (Yes V1 is private because I don't want anyone to see my
*bad* **BAD** code) Also: Discord has changed a lot of Stuff in the meantime, so it was necessary for me to redo my Engine anyways, especially for
my other Project, Herukan.

## Credits

Maddox is developed & maintained by [AzraAnimating](https://github.com/AzraAnimating)

API's by [DV8FromTheWorld](https://github.com/DV8FromTheWorld), [Carleslc](https://github.com/Carleslc) and [Daschi1](https://github.com/Daschi1)

Code contributions by [Spark61](https://github.com/Spark61)
