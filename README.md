# Maddox V2

## What it's for

Maddox is a generic Discord-Moderation bot that is open for anyone to use and Modify.

Maddox is also built to be a great foundation for any type of Discord-Bot.

If you want to contribute / suggest features feel free to open up an Issue or a Pull-Request.
I will try to review your contibutions / suggestions as fast as possible.

## Why use Maddox?

As stated, Maddox is primerily built to be a foundation for the creation of other Discord bots so it is taylor-built for this task.
It includes a Custom Command-Event and Guild / Member object optimized for use with Customprefix- & Language- Systems.

You can also just download Maddox and use it as it is for normal Moderation-Purposes if you like the look of a Bot special to your Server.

## Developing with Maddox 

### I - Plattform

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
    this.name = "test";
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
        this.setSyntax("test"); //The Prefix that is set for that guild will automatically be shown infront of your Syntax
        this.setShowInHelp(true); //Enables you to hide your command from the help menu - enabled by default
        this.setCommandHelpViewPermission(CommandHelpViewPermission.EVERYONE); //defines who can see the command
    }
```

(Stuff *will* be added)

## FAQ

### Why V2?
Simple: Because V1 is almost 2 Years old by now and Discord has changed a lot (Yes V1 is private because I don't want anyone to see my
*bad* **BAD** code)

### Why do you directly push to the master-Branch?
I am currently developing the base-structure for the bot and I am also the only developer currently working on Maddox. I will stop upon the first "real" Release of Maddox,
but you'll always be free to see the beta-branches

## Credits

Maddox is developed & maintained by [AzraAnimating](https://github.com/AzraAnimating)

API's by [DV8FromTheWorld](https://github.com/DV8FromTheWorld) and [Daschi1](https://github.com/Daschi1)

Code contributions by [Spark61](https://github.com/Spark61)
