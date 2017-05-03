/**
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * You may not modify, use, reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://java.net/projects/javaeetutorial/pages/BerkeleyLicense
 */
package websocketbot;

import org.springframework.context.annotation.Scope;
import websocketbot.decoders.MessageDecoder;
import websocketbot.encoders.ChatMessageEncoder;
import websocketbot.encoders.InfoMessageEncoder;
import websocketbot.encoders.JoinMessageEncoder;
import websocketbot.encoders.UsersMessageEncoder;
import websocketbot.messages.*;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Websocket endpoint */
@ServerEndpoint(
        value = "/websocketbot",
        decoders = { MessageDecoder.class },
        encoders = { JoinMessageEncoder.class, ChatMessageEncoder.class, 
                     InfoMessageEncoder.class, UsersMessageEncoder.class }
        )
/* There is a BotEndpoint instance per connetion */
@Scope("singleton")
public class BotEndpoint {
    private static final List<String> userList =new ArrayList<String>();
    private static final Set<Session> sessionSet =new HashSet<Session>();
    private static final Logger logger = Logger.getLogger("BotEndpoint");
    
    @OnOpen
    public void openConnection(Session session) {
        sessionSet.add(session);
        logger.log(Level.INFO, "Connection opened.");
    }
    
    @OnMessage
    public void message(final Session session, Message msg) {
        logger.log(Level.INFO, "Received: {0}", msg.toString());
        
        if (msg instanceof JoinMessage) {
            /* Add the new user and notify everybody */
            JoinMessage jmsg = (JoinMessage) msg;
            userList.add(jmsg.getName());
            session.getUserProperties().put("name", jmsg.getName());
            //session.getUserProperties().put("active", true);
            logger.log(Level.INFO, "Received: {0}", jmsg.toString());
            sendAll(session, new InfoMessage(jmsg.getName() + " has joined the chat"));
            sendAll(session, new ChatMessage("C.c", jmsg.getName(), "Hi there!!"));
            sendAll(session, new UsersMessage(userList));
            
        } else if (msg instanceof ChatMessage) {
            /* Forward the message to everybody */
            final ChatMessage cmsg = (ChatMessage) msg;
            logger.log(Level.INFO, "Received: {0}", cmsg.toString());
            sendAll(session, cmsg);
//            if (cmsg.getTarget().compareTo("Duke") == 0) {
//                /* The bot replies to the message */
//            	System.out.println("this is Duke'msg");
//                mes.submit(new Runnable() {
//                    public void run() {
//                        String resp = "Duke";//botstockbean.respond(cmsg.getMessage());
//                        sendAll(session, new ChatMessage("Duke", cmsg.getName(), resp));
//                    }
//                });
//            }
        }
    }
    
    @OnClose
    public void closedConnection(Session session) {
        /* Notify everybody */
        //session.getUserProperties().put("active", false);
        sessionSet.remove(session);
        if (session.getUserProperties().containsKey("name")) {
            String name = session.getUserProperties().get("name").toString();
            userList.remove(name);
            sendAll(session, new InfoMessage(name + " has left the chat"));
            sendAll(session, new UsersMessage(userList));
        }
        logger.log(Level.INFO, "Connection closed.");
    }
    
    @OnError
    public void error(Session session, Throwable t) {
        logger.log(Level.INFO, "Connection error ({0})", t.toString());
    }
    
    /* Forward a message to all connected clients
     * The endpoint figures what encoder to use based on the message type */
    public synchronized void sendAll(Session session, Object msg) {
        try {
            for (Session s : sessionSet) {
                if (s.isOpen()) {
                    s.getBasicRemote().sendObject(msg);
                    logger.log(Level.INFO, "Sent: {0}", msg.toString());
                }
            }
        } catch (IOException e) {
            logger.log(Level.INFO, e.toString());
        }catch (EncodeException e){
            logger.log(Level.INFO, e.toString());
        }
    }
    
    /* Returns the list of users from the properties of all open sessions */
    public List<String> getUserList(Session session) {
        List<String> users = new ArrayList();
        //users.add("Duke");
        for (Session s : session.getOpenSessions()) {
            if (s.isOpen() && s.getUserProperties().get("active").toString().equals("true"))
                users.add(s.getUserProperties().get("name").toString());
        }
        return users;
    }
}
