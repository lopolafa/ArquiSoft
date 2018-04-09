
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ConnectionTest {
    MailSystem mockedMailsystem;
    Telephone mockedTelephone;
    Connection connection;

    @Before
	public void init() {
	    mockedMailsystem = mock(MailSystem.class);
	    mockedTelephone = mock(Telephone.class);
	    connection = new Connection(mockedMailsystem, mockedTelephone);
	    connection.AddObservable(mockedTelephone);
	}

	@Test
	public void shouldBeInConnectedStatus() {
	    assertTrue(connection.isConnected());
	}

	@Test
	public void shouldShowInitialMessage() {
        connection.StartConnection();
		verify(mockedTelephone, times(1)).Update("Enter mailbox number followed by #");
	}
	
	@Test
	public void shouldChooseValidMailBox() {
		String idMailBox = "1";
		Mailbox chosenMailbox = new Mailbox(idMailBox, "Hola, como estas?");

		when(mockedMailsystem.findMailbox(idMailBox)).thenReturn(chosenMailbox);
		connection.dial(idMailBox);
		connection.dial("#");

		assertTrue(connection.isRecording());
		verify(mockedTelephone).Update("Hola, como estas?");
	}
	
	@Test
	public void shouldGetIntoMailBoxMenu() {
		String idMailBox = "1";
		String keyMailBox = "1";
		Mailbox chosenMailbox = new Mailbox(idMailBox, "Hola, como estas?");

		when(mockedMailsystem.findMailbox(idMailBox)).thenReturn(chosenMailbox);
		connection.dial(idMailBox);
		connection.dial("#");
		connection.dial(keyMailBox);
		connection.dial("#");

		assertTrue(connection.isMailBoxMenu());
	}
	
	@Test
	public void shouldGetIntoMessageMenu() {
		String idMailBox = "1";
		String keyMailBox = "1";
		String mailBoxMenuOption = "1";
		Mailbox chosenMailbox = new Mailbox(idMailBox, "Hola, como estas?");

		when(mockedMailsystem.findMailbox(idMailBox)).thenReturn(chosenMailbox);
		connection.dial(idMailBox);
		connection.dial("#");
		connection.dial(keyMailBox);
		connection.dial("#");
		connection.dial(mailBoxMenuOption);
		connection.dial("#");

		assertTrue(connection.isMessageMenu());
	}
	
	@Test
	public void shouldGetIntoChangePassCodeMenu() {
		String idMailBox = "1";
		String keyMailBox = "1";
		String mailBoxMenuOption = "2";
		Mailbox chosenMailbox = new Mailbox(idMailBox, "Hola, como estas?");

		when(mockedMailsystem.findMailbox(idMailBox)).thenReturn(chosenMailbox);
		connection.dial(idMailBox);
		connection.dial("#");
		connection.dial(keyMailBox);
		connection.dial("#");
		connection.dial(mailBoxMenuOption);

		assertTrue(connection.isChangePassCode());
	}

	@Test
	public void shouldChangePassCodeFrom1To2() {
		String idMailBox = "1";
		String keyMailBox = "1";
		String newKeyMailBox = "2";
		String mailBoxMenuOption = "2";
		String hangDown = "h";
		Mailbox chosenMailbox = new Mailbox(idMailBox, "Hola, como estas?");

		when(mockedMailsystem.findMailbox(idMailBox)).thenReturn(chosenMailbox);
		connection.dial(idMailBox);
		connection.dial("#");
		connection.dial(keyMailBox);
		connection.dial("#");
		connection.dial(mailBoxMenuOption);
		
		connection.dial(newKeyMailBox);
		connection.dial("#");
		connection.dial(hangDown);
		
		connection.dial(idMailBox);
		connection.dial("#");
		connection.dial(newKeyMailBox);
		connection.dial("#");
		
		assertTrue(connection.isMessageMenu());
	}
	
	@Test
	public void shouldGetIntoChangeGreetingMenu() {
		String idMailBox = "1";
		String keyMailBox = "1";
		String mailBoxMenuOption = "3";
		Mailbox chosenMailbox = new Mailbox(idMailBox, "Hola, como estas?");

		when(mockedMailsystem.findMailbox(idMailBox)).thenReturn(chosenMailbox);
		connection.dial(idMailBox);
		connection.dial("#");
		connection.dial(keyMailBox);
		connection.dial("#");
		connection.dial(mailBoxMenuOption);

		assertTrue(connection.isChangeGreeting());
	}
	
	/*@Test
	public void shouldChangeGreetingFromStockOneToNewOne() {
		String idMailBox = "1";
		String keyMailBox = "1";
		String optionForChangeGreeting = "3";
		String stockGreeting="Hola, como estas?";
		String newGreeting="kha xdxdxd";
		Mailbox chosenMailbox = new Mailbox(idMailBox, stockGreeting);

		when(mockedMailsystem.findMailbox(idMailBox)).thenReturn(chosenMailbox);
		connection.dial(idMailBox);
		connection.dial("#");
		connection.dial(keyMailBox);
		connection.dial("#");
		connection.dial(optionForChangeGreeting);
		
		connection.dial(newGreeting);
		connection.dial("#");
		connection.dial("h");
		
		connection.dial(idMailBox);
		connection.dial("#");
		
		verify(mockedTelephone).speak(newGreeting);
	}*/
	
}