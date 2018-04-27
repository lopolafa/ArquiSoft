package controller;

public class Connect implements IState {
    private Mailbox currentMailbox;
    private MailSystem system;
    private String accumulatedKeys="";
    private Connection connection;
    Connect(Connection connection) {
        this.connection=connection;
        this.system=connection.getMailboxSystem();
        connection.updateObservables(INITIAL_PROMPT);
    }

    @Override
    public void start(String command) {

        if (itIsANumeralCharacter(command))
            openMailbox();
        else
            saveActualCommand(command);
    }

    private void openMailbox() {
        setupMailbox();
        if (isAValidMailbox())
        {
            changeToRecordingState();
            setCurrentMailboxToConnection();
            showGreetingMessage();
        }
        else {
            showIncorrectMailboxMessage();
            cleanAccumulatedKeys();
        }
    }

    private void saveActualCommand(String command) {
        accumulatedKeys += command;
    }

    private void cleanAccumulatedKeys() {
        accumulatedKeys = "";
    }

    private void setupMailbox() {
        currentMailbox = system.findMailbox(accumulatedKeys);
    }

    private void showIncorrectMailboxMessage() {
        connection.updateObservables(INCORRECT_MAILBOX_MESSAGE);
    }

    private void showGreetingMessage() {
        connection.updateObservables(currentMailbox.getGreeting());
    }

    private void setCurrentMailboxToConnection() {
        connection.setMailbox(currentMailbox);
    }

    private void changeToRecordingState() {
        connection.setStatus(new Recording(connection));
    }

    private boolean isAValidMailbox() {
        return currentMailbox != null;
    }

    @Override
    public void hangup() {
        connection.resetConnection();
    }

    private boolean itIsANumeralCharacter(String key) {
        return key.equals("#");
    }
    private String INCORRECT_MAILBOX_MESSAGE = "Incorrect mailbox number. Try again!";
    private static final String INITIAL_PROMPT =
            "Enter mailbox number followed by #";
}
