/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Social;

import java.sql.Timestamp;

/**
 *
 * @author SONY
 */
public class Msg {

    private String toUser;
    private String fromUser;
    private String messageText;
    private Timestamp period;

    public Msg(String toUser, String fromUser, String messageText, Timestamp period) {
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.messageText = messageText;
        this.period = period;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Timestamp getPeriod() {
        return period;
    }

    public void setPeriod(Timestamp period) {
        this.period = period;
    }
}
