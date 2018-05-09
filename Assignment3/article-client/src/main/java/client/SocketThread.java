package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import requests.Request;
import user.User;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.Socket;

public class SocketThread implements Runnable {

    private Socket socket;
    private ObjectInput in;
    private ObjectOutput out;
    private ObjectMapper mapper;
    private boolean active;

    public SocketThread() {
        this.socket = ClientUtils.getServerCon();
        this.in = ClientUtils.getSocketIn();
        this.out = ClientUtils.getSocketOut();
        this.mapper = new ObjectMapper();
        this.active = true;
    }

    @Override
    public void run() {
        while(active) {
            try {
                Request req = mapper.readValue((String)in.readObject(), Request.class);
                switch(req.getRequest()) {
                    case "LOG_IN_RESPONSE" : processLogin(req.getContent()); break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void processLogin(Object o) {
        User user = mapper.convertValue(o, User.class);
        if(user != null) {
            ClientUtils.setCurrentUser(user);
            ClientUtils.userIdProperty().set(user.getId());
        } else {
            ClientUtils.userIdProperty().set(-1);   // signal an error
        }
    }
}
