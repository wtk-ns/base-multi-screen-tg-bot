# Telegram bot with multiple strings
This is the base of telegram multi-screen bot with inline keyboard. You can use it
to simply develop you own bot by implementing existing pattern. 

## How to use
Bot has 3 steps to implement screen: 
- Create screen in screens folder by extending BotScreen abstract class
- Create .html file in resources/screens folder with the same name as screen class you created
- Add your screen in ScreenFactory in static block

## Screens 
Screen would be compiled and filled in getScreenContent() method in your screen class. 
All markdown can be filled up in .html file you created for screen. 

In case of dynamic content you can use any template engine you like to fill placeholders in html file

## Versions 
Java 8
Telegram Bots API 6.8.0
Lombok 1.18.39
Snake Yaml 2.2 (in case of connecting spring boot just rm the dependency and use nested spring boot props)
Logback 1.18.30 (as Slf4j provider in lombok. Use logback.xml to configure output format)
Jackson 2.16.1



