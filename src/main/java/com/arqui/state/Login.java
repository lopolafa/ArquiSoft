package com.arqui.state;

import com.arqui.Core.Connection;
import com.arqui.DisplayState.DisplayLogin;
import com.arqui.Entities.Mailbox;
import com.arqui.IState;

public class Login implements IState {
    private Mailbox currentMailbox;
    private String accumulatedKeys="";
    Connection connection;
    Login(Connection connection){
        this.connection=connection;
        this.currentMailbox=connection.getCurrentMailbox();
        this.connection.setDisplay(new DisplayLogin());
    }
    public boolean dial(String command) {
        if (itIsANumeralCharacter(command))
        {
            return openMailboxMenu();
        }
        else {
            saveActualCommand(command);
            return true;
        }

    }

    private boolean openMailboxMenu() {
        if (isTheCorrectPasscodeOfCurrentMailbox())
        {
            changeToMailboxMenuState();
            return true;
        }
        else {
            showIncorrectPasscodeMessage();
            cleanAccumulatedKeys();
            return false;
        }
    }

    private void saveActualCommand(String command) {
        accumulatedKeys += command;
    }

    private void cleanAccumulatedKeys() {
        accumulatedKeys = "";
    }

    private void showIncorrectPasscodeMessage() {
        connection.setError("Invalid_Passcode");
        connection.show();
    }



    private void changeToMailboxMenuState() {
        connection.setStatus(new MailboxMenu(connection));
    }

    private boolean isTheCorrectPasscodeOfCurrentMailbox() {
        return currentMailbox.checkPasscode(accumulatedKeys);
    }

    public boolean hangup() {
        connection.resetConnection();
        return true;
    }

    private boolean itIsANumeralCharacter(String key) {
        return key.equals("#");
    }

}