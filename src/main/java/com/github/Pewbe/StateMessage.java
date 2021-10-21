package com.github.Pewbe;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;

import java.util.Collection;

public class StateMessage implements Runnable{
    DiscordApi api;

    public StateMessage(DiscordApi api){ this.api = api; }

    @Override
    public void run() {

        while( true ){
            try{
                api.updateActivity("=안녕");
                Thread.sleep(3000 );
                api.updateActivity( api.getServers().size() + " 개의 서버와 함께" );
                Thread.sleep(3000 );
            }
            catch( InterruptedException e ){}
        }
    }
}
