/*
		PesterJar: testing edition
	This code exists only to test stuff and figure out ways to implement the actual features later. Think of it as a Mockup.
	
	Things covered in this build:
		*Login Screen
		*Chumlist Screen
			*can add chums
			*can delete chums
			*it doesn't store the entries yet (to do)
		*Options screen
			*doesnt register the clicked option (to do)
			
	Complete Rewrite count: 2 (update this every time you rewrite it from scratch)
*/
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class main extends MIDlet implements CommandListener{
	//all the stuff we're gonna use on this code should be declared here (citation needed)
	//these are the Commands, the buttons thatll be displayed at the bottom of the screen
	private Command Add, Cancel, Exit, Next, Remove;
	//this is the display object, still not sure why but its important. i think it handles switching between screens (will elaborate later)
	private Display display;
	//this is a Form object, we'll use it for the faux login screen
	private Form Login;
	//these are List Objects, they'll be used for the contact list. there's two of them bc one will be there just to display the contacts and the other will be to try and figure how to delete list entries, so it's useless for now.
	private List ChumList, ChumSelect;
	//this is a TextBox object which, unlike TextFields, occupies the entire screen and thus are considered their own screen. It'll be used to add entries to the lists declared earlier.
	private TextBox AddChum;
	//these are TextField objects, they'll be used in the form declared earlier for the nickname and password fields
	private TextField ChumHandle, Password;
	private int currentScreen, selection;
	
	public main(){
		//defines most of the objects declared earlier. In order for the chumlist to have the user's nick on the title, it has to be declared during a command action
		//commands: their arguments go as follows: label, type, priority.
		Add = new Command ("Add Chum", Command.SCREEN, 2);
		Cancel = new Command ("Cancel", Command.BACK, 1);
		Exit = new Command ("Exit", Command.EXIT, 1);
		Next = new Command ("==>", Command.OK, 1);
		Remove = new Command ("Remove Chum", Command.SCREEN, 3);
		//selection: menu that'll appear when you select "remove chum"on the Chumlist
		ChumSelect = new List ("Select a Chum", Choice.EXCLUSIVE);
		//login form: forms take a string for argument, it defines the form title
		Login = new Form ("PesterJar Login");
		//textBox: textbox take the following arguments: title, default text, character limit, data type.
		AddChum = new TextBox ("Add Chum","",30,TextField.ANY);
		//testFields: their arguments are similar, if not identical, to textBoxes
		ChumHandle = new TextField ("ChumHandle","",30,TextField.ANY);
		Password = new TextField ("NickServ Password (optional)","",256,TextField.PASSWORD);
	}
	
	//One of the 3 obligatory classes that must be present on a MIDlet. This one handles what happens as soon as the app opens.
	public void startApp(){
		display = Display.getDisplay(this);
		AddChum.addCommand(Next);
		AddChum.addCommand(Cancel);
		ChumSelect.addCommand(Next);
		ChumSelect.addCommand(Cancel);
		Login.append(ChumHandle);
		Login.append(Password);
		Login.addCommand(Next);
		Login.addCommand(Exit);
		Login.setCommandListener(this);
		display.setCurrent(Login);
		currentScreen = 0;
	}
	
	//One of the 3 obligatory classes that must be present on a MIDlet. Handles what happens when the app loses "focus" but isnt actually closed. its empty for now but its required to be here.
	public void pauseApp(){}
	
	//One of the 3 obligatory classes that must be present on a MIDlet. It executes the code inside it and closes the app when set to False.
	public void destroyApp(boolean unconditional){
		notifyDestroyed();
	}
	
	//sets the Chumlist as the current screen
	public void switchToChumList(){
		currentScreen = 1;
		display.setCurrent(ChumList);
		ChumList.setCommandListener(this);
	}
	
	//sets the "Add chum" textbox as the current screen
	public void switchToAddChum(){
		currentScreen = 2;
		display.setCurrent(AddChum);
		AddChum.setCommandListener(this);
	}
	
	public void switchToChumSelect(){
		currentScreen = 3;
		display.setCurrent(ChumSelect);
		ChumSelect.setCommandListener(this);
	}
	
	//builds the Chumlist's base list
	public void createChumList(){
		String nick = ChumHandle.getString();
			ChumList = new List(nick+"'s Chumlist", Choice.IMPLICIT);
			ChumList.addCommand(Add);
			ChumList.addCommand(Remove);
			ChumList.addCommand(Exit);
	}
	
	//appends a string to the list when the string isn't empty
	public void addChumIfNotEmpty(){
		String Chum = AddChum.getString();
		AddChum.setString("");
		if (!Chum.equals("")){
			ChumList.append(Chum, null);
			ChumSelect.append(Chum, null);
		}
	}
	
	public void nextScreenHandler(){
		if (currentScreen == 0){
			createChumList();
			switchToChumList();
		}
		else if (currentScreen == 2){
			addChumIfNotEmpty();
			switchToChumList();
		}
		else if (currentScreen == 3){
			removeChumHandler();
			switchToChumList();
		}
	}

	//handles removing a chum from the chumlist
	public void removeChumHandler(){
		selection = ChumSelect.getSelectedIndex();
		System.out.println("index: "+ selection + "\n" + "handle: " + ChumSelect.getString(selection));
		ChumList.delete(selection);
		ChumSelect.delete(selection);
		
	}
	
	//handles the cancel button's action based on the current screen
	public void cancelHandler(){
		if (currentScreen == 2){
			switchToChumList();
			AddChum.setString("");
		}
		if  (currentScreen == 3){
			switchToChumList();
		}
	}
	
	
	//This one determines what will each command do. I know the implementation is horrible but bear with me.
	//implementation cleaned :thumbsup:
	public void commandAction(Command c, Displayable s){
		//here it defines a string object and gives it the selected command's label as data.
		String label = c.getLabel();
		//and then proceeds to do a whole lot of IF/ELSE statements to check which command was pressed and react accordingly
		if (label.equals("Add Chum")){
			switchToAddChum();
		}
		else if (label.equals("Exit")){
			destroyApp(false);
		}
		else if (label.equals("==>")){
			nextScreenHandler();
		}
		else if (label.equals("Remove Chum")){
			switchToChumSelect();
		}
		else if (label.equals("Cancel")){
			cancelHandler();
		}
		else if (c == ChumSelect.SELECT_COMMAND && currentScreen == 3){
			removeChumHandler();
			switchToChumList();
		}
	}
}