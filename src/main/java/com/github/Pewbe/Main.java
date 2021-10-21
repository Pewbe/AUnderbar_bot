package com.github.Pewbe;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.*;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.awt.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String token = "NzIwMTk2MjAxMTQ3OTI0NDkw.XuCc-g.vnj7t04wH1LnDK11q0VHDba6LpI";
        DiscordApi api = new DiscordApiBuilder()
                .setToken(token)
                .setAllIntents()
                .login()
                .join();

        //StateMessage st = new StateMessage( api );
        //Thread stateUpdate = new Thread( st );

        System.out.println("디스코드에 로그인하였습니다.");

        //stateUpdate.setDaemon(true);
        //stateUpdate.run();

        //슬래시 명령어 등록

        api.addSlashCommandCreateListener(event -> {
            System.out.println("슬래시 명령어 리스너 작동");
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();

            if (slashCommandInteraction.getCommandName().equals("shaki")) {
                slashCommandInteraction.createImmediateResponder()
                        .setContent("ㅅ ㅂ")
                        .respond();
            }
            else if(slashCommandInteraction.getCommandName().equals("test")){
                slashCommandInteraction.createImmediateResponder()
                        .setContent("테스트")
                        .respond();
            }
        });

        api.addServerJoinListener(ev -> {//서버에 초대되었을시
            Server server = ev.getServer();
            System.out.println(server.getName() + " 서버에 초대됨, 권한 설정 완료");
        });

        api.addServerMemberJoinListener(ev -> {//서버에 누군가 들어왔을 때....?
            Server server = ev.getServer();
            User user = ev.getUser();

            System.out.println("서버에 누군가 가입했습니다.");

            if( server.getId() == 859982098377867294L ){//마늘오리
                System.out.println("마늘오리 서버에 누군가 가입했습니다.");
                Role role = user.isBot() ? server.getRoleById(860076412324413450L).get() : server.getRoleById(859985113068994600L).get();
                user.addRole( role );
            }
        });

        api.addMessageCreateListener(ev -> {//설정 명령어 이외의 단순 단답 명령어들
            Message message = ev.getMessage();
            String content = message.getContent();
            TextChannel channel = ev.getChannel();
            User user = message.getUserAuthor().get();
            EmbedBuilder embed = new EmbedBuilder();
            Server server = ev.getServer().get();

            if( !content.startsWith("=") )
                return;

            System.out.println( content + " (이)라는 메시지를 " + user.getName() + " 에게 받음" );

            String command = content.replace("=", "");//프리픽스를 제외한 명령어 부분
            String userMention = user.getMentionTag();

            embed.setColor( Color.white );

            if( command.startsWith("채금") ){
                if( command.endsWith("채금") ) {
                    embed.setTitle("❗ 채금할 사용자를 멘션해주세요.");
                    channel.sendMessage(embed);
                }else{
                    if( !user.isBotOwner() ){
                        embed.setTitle("❗ 서버 주인만 사용할 수 있는 명령어입니다.");
                        channel.sendMessage( embed );
                    }
                    else{
                        try{
                            String[] splited = command.split(" ");

                            if( !splited[1].startsWith("<@!") ){
                                embed.setTitle("❗ 유저만 멘션할 수 있습니다.");
                                channel.sendMessage( embed );

                                return;
                            }

                            String targetID = splited[1].replaceAll("\\W", "");

                            User targetUser = api.getUserById(targetID).get();
                            Role chatBan = api.getRoleById("900524434379640892").get();

                            System.out.println( targetID );

                            for ( Role r : server.getRoles( targetUser ) ) {
                                targetUser.removeRole( r );
                                System.out.println( targetUser.getName() + " 의 " + r.getName() + " 역할을 삭제했습니다." );
                            }

                            targetUser.addRole( chatBan );

                            channel.sendMessage( embed.setTitle("✅ 해당 유저가 채팅 금지 설정되었습니다.") );

                            System.out.println( targetUser.getName() + "에게 " + chatBan.getName() + " 역할을 부여했습니다.");
                        } catch ( Exception e ){ e.printStackTrace(); }
                    }
                }
            }
            else if( command.startsWith("석방") ){
                if( command.endsWith("석방") ) {
                    embed.setTitle("❗ 채금을 해제할 사용자를 멘션해주세요.");
                    channel.sendMessage(embed);
                }else{
                    if( !user.isBotOwner() ){
                        embed.setTitle("❗ 서버 주인만 사용할 수 있는 명령어입니다.");
                        channel.sendMessage( embed );
                    }
                    else{
                        try{
                            String[] splited = command.split(" ");

                            if( !splited[1].startsWith("<@!") ){
                                embed.setTitle("❗ 유저만 멘션할 수 있습니다.");
                                channel.sendMessage( embed );

                                return;
                            }

                            String targetID = splited[1].replaceAll("\\W", "");

                            User targetUser = api.getUserById(targetID).get();
                            Role banCancel = api.getRoleById("859985113068994600").get();

                            System.out.println( targetID );

                            for ( Role r : server.getRoles( targetUser ) ) {
                                if( !r.getName().equals("소원요정") ) {
                                    targetUser.removeRole(r);
                                    System.out.println(targetUser.getName() + " 의 " + r.getName() + " 역할을 삭제했습니다.");
                                }
                            }

                            targetUser.addRole( banCancel );

                            channel.sendMessage( embed.setTitle("✅ 해당 유저의 채팅 금지 설정이 해제되었습니다.") );
                            System.out.println( targetUser.getName() + "에게 " + banCancel.getName() + " 역할을 부여했습니다.");
                        } catch ( Exception e ){ e.printStackTrace(); }
                    }
                }
            }
            else {
                int rand = (int)(Math.random()*8)+1;

                switch ( rand ){
                    case 1: channel.sendMessage("응?"); break;
                    case 2: channel.sendMessage("[system]존재하지 않는 명령어입니다."); break;
                    case 3: channel.sendMessage("그런 말은 배운 적이 없는데.."); break;
                    case 4: channel.sendMessage("Zzz........"); break;
                    case 5: channel.sendMessage("배고프다.."); break;
                    case 6: channel.sendMessage("그런 명령어는 아무리 찾아봐도 없네요."); break;
                    case 7: channel.sendMessage("||들켰다!||"); break;
                    case 8: channel.sendMessage("몰?루"); break;
                }
            }
        });
    }
}
