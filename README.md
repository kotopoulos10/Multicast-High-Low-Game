# Mutlicast High Low Game

### Overview

I developed this game for my Computer Networks Course at Boston College. The assignment was to create a Multicast high low game and chat room for each corresponding game room. There is a Middleware Server that acts as a broker. A client can either be a player or a chatter. 

### Middleware Server

The server (for simplicity) only handles 5 games at a time. When a player or chatter first connects to the server, the server will find the next avalible open game. It will then pass the corresponding IP and Port of the game multicast room and the IP and Port of the chat room associated with that game room to the player or chatter. The server does nothing more than keep track of the game and chat rooms. Each game room has a max of 2 players and each chat room can have unlimited members. From here on out the players and chatters talk with each other though the multicast room. The only time they will contact the server is when they want to end the game or switch rooms.

##### Changing Rooms

Players are not allowed to change game rooms. They must quit their current game and contact the server again to be assigned another game room. Chatters on the other hand, are allowed to switch rooms. If a chatter types "@CHANGE" they can enter the new chatroom index number that they want to switch to. When they do so they leave the corresponding game room and move to the new game room associated with the new chat room index they just entered. 

### Player

The player class requests to join a game room from the middleware server. The server will respond with a PairData object (IP and Port Address of the game and chat room). The player class has 2 threads running. The first thread is responsible for reading the incoming messages to the game multicast room. The second thread is responsible for reading keyboard input and writing to the multicast game room. 

### Chatter

The chatter has 3 threads running. The first is to read the data from the game multicast room. The second is to read the incoming messages from the chat mutlicast room. The third is to read input from the keyboard and write messages to the chat multicast room.

### Setup

There are 3 files that need to be run to play this game. The first is the MiddleWareServer.java. This file has 1 argument which is the port number to listen on. The second is Player.java. This has two arguments the IP and port address of the server. The third is Chatter.java. This also has two arguments the IP and port address of the server. There are no dependicies to install. 