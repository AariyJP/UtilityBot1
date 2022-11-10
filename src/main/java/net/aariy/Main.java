package net.aariy;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args)
    {
        System.out.println("Bot is starting up.\n(c) 2022 Aariy.NET");
        JDA jda = JDABuilder.createDefault(args[0])
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(new Main())
                .build();
        jda.updateCommands()
                .addCommands(
                        Commands.slash("gban", "グローバルBANを管理します。")
                                .addSubcommands(
                                        new SubcommandData("add", "グローバルBANを追加します。"),
                                        new SubcommandData("remove", "グローバルBANを削除します。")
                                )
                                .addOption(OptionType.STRING, "パスワード", "パスワード", true)
                                .addOption(OptionType.USER, "ユーザー", "グローバルBANするユーザー", true),
                        Commands.slash("verify", "認証ボタンを設置します。").addOption(OptionType.ROLE, "ロール", "認証ロール"),
                        Commands.slash("help", "ヘルプを表示します。"),
                        Commands.slash("allrole", "メンバー全員にロールを付与します。")
                                .addOption(OptionType.ROLE, "ロール", "付与するロール", true)
                ).queue();
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent e)
    {
        boolean success = false;
        switch (e.getName())
        {
            case "gban" -> {
                switch (e.getSubcommandName())
                {
                    case "add" -> {
                        if(e.getOption("パスワード").getAsString().equalsIgnoreCase("azytji"))
                            for (Guild guild : e.getJDA().getGuilds()) {
                                try
                                {
                                    guild.ban(e.getOption("ユーザー").getAsUser(), 0, TimeUnit.DAYS).queue();
                                }
                                catch (Exception ignored) {}
                                e.reply("✅ 処理に成功しました。").setEphemeral(true).queue();
                            }
                    }
                    case "remove" -> {
                        if(e.getOption("パスワード").getAsString().equalsIgnoreCase("azytji"))
                            for (Guild guild : e.getJDA().getGuilds()) {
                                try
                                {
                                    guild.unban(e.getOption("ユーザー").getAsUser()).queue();
                                }
                                catch (Exception ignored) {}
                                e.reply("✅ 処理に成功しました。").setEphemeral(true).queue();
                            }
                    }
                }
            }
            case "verify" -> {
                e.getChannel().sendMessageComponents(ActionRow.of(Button.success("verify_do", "認証する"))).queue();
                e.reply("✅ 処理に成功しました。").setEphemeral(true).queue();
            }
            case "help" -> {
                e.reply("**💡ヘルプ**" +
                        "`/gban add ユーザー パスワード`：グローバルBANを追加します。" +
                        "`/gban remove ユーザー パスワード`：グローバルBANを削除します。" +
                        "`/verify ロール`:認証ボタンを設置します。" +
                        "`/help`：ヘルプを表示します。" +
                        "`/allrole`：メンバー全員にロールを付与します。").setEphemeral(true).queue();
            }
        }
    }

    public void onMessageReceived(MessageReceivedEvent e)
    {
        String[] a = e.getMessage().getContentRaw().substring(1).split(" ");
        switch (a[0])
        {
            case "gban" -> {
                switch (a[1])
                {
                    case "add" -> {
                        if(a[3].equalsIgnoreCase("azytji"))
                            for (Guild guild : e.getJDA().getGuilds()) {
                                try
                                {
                                    guild.ban(User.fromId(a[2]), 0, TimeUnit.DAYS).queue();
                                }
                                catch (Exception ignored) {}
                                e.getMessage().reply("✅ 処理に成功しました。").queue();
                            }
                    }
                    case "remove" -> {

                    }
                }
            }
        }
    }
}