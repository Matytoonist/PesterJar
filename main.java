/*
		PesterJar: testing edition
	This code exists only to test stuff and figure out ways to implement the actual features later. Think of it as a Mockup.
	
	Things covered in this build:
		*Login Screen
		*Chumlist Screen
			*can add chums
			*can't delete chums (to do)
			*it doesn't store the entries yet (to do)
		*Select Chums screen
			*has the same entries as the Chumlist
			*it doesn't select suff (to do)
*/

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class main extends MIDlet implements CommandListener{
	//all the stuff we're gonna use on this code should be declared here (citation needed)
	//these are the Commands, the buttons thatll be displayed at the bottom of the screen
	private Command Add, Exit, Next, Ok, Select, View;
	//this is the display object, still not sure why but its important. i think it handles switching between screens (will elaborate later)
	private Display display;
	//this is a Form object, we'll use it for the faux login screen
	private Form Login;
	//these are List Objects, they'll be used for the contact list. there's two of them bc one will be there just to display the contacts and the other will be to try and figure how to delete list entries, so it's useless for now.
	private List Chumlist, ChumSelect;
	//this is a TextBox object which, unlike TextFields, occupies the entire screen and thus are considered their own screen. It'll be used to add entries to the lists declared earlier.
	private TextBox AddChum;
	//these are TextField objects, they'll be used in the form declared earlier for the nickname and password fields
	private TextField ChumHandle, Password;
	
	public main(){
		//defines most of the objects declared earlier. In order for the chumlist to have the user's nick on the title, it has to be declared during a command action
		//commands: their arguments go as follows: label, type, priority.
		Add = new Command ("Add Chum", Command.SCREEN, 2);
		Exit = new Command ("Exit", Command.EXIT, 1);
		Next = new Command ("==>", Command.SCREEN, 1);
		Ok = new Command ("Ok", Command.SCREEN, 2);
		Select = new Command ("Select Chums", Command.SCREEN, 2);
		View = new Command ("Done", Command.SCREEN, 2);
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
		Login.append(ChumHandle);
		Login.append(Password);
		Login.addCommand(Next);
		Login.addCommand(Exit);
		Login.setCommandListener(this);
		display.setCurrent(Login);
	}
	
	//One of the 3 obligatory classes that must be present on a MIDlet. Handles what happens when the app loses "focus" but isnt actually closed. its empty for now but its required to be here.
	public void pauseApp(){}
	
	//One of the 3 obligatory classes that must be present on a MIDlet. It executes the code inside it and closes the app when set to False.
	public void destroyApp(boolean unconditional){
		notifyDestroyed();
	}
	
	public void AddChum(){
		display.setCurrent(AddChum);
		AddChum.addCommand(View);
		AddChum.addCommand(Exit);
		AddChum.setCommandListener(this);
	}
	
	public void ChumList(){
		display.setCurrent(Chumlist);
		Chumlist.setCommandListener(this);
	}
	
	//This one determines what will each command do. I know the implementation is horrible but bear with me.
	public void commandAction(Command c, Displayable s){
		//here it defines a string object and gives it the selected command's label as data.
		String label = c.getLabel();
		//and then proceeds to do a whole lot of IF/ELSE statements to check which command was pressed and react accordingly
		if (label.equals("Add Chum")){
			AddChum();
		}
		else if (label.equals("Exit")){
			destroyApp(false);
		}
		else if (label.equals("==>")){
			String nick = ChumHandle.getString();
			Chumlist = new List(nick+"'s Chumlist", Choice.IMPLICIT);
			ChumSelect = new List ("Select Chums", Choice.MULTIPLE);
			Chumlist.addCommand(Add);
			Chumlist.addCommand(Exit);
			Chumlist.addCommand(Select);
			ChumSelect.addCommand(Ok);
			ChumSelect.addCommand(Exit);
			display.setCurrent(Chumlist);
			Chumlist.setCommandListener(this);
		}
		else if (label.equals("Ok")){
			ChumList();
		}
		else if (label.equals("Select Chums")){
			display.setCurrent(ChumSelect);
			ChumSelect.setCommandListener(this);
		}
		else if (label.equals("Done")){
			String Chum = AddChum.getString();
			AddChum.setString("");
			Chumlist.append(Chum, null);
			ChumSelect.append(Chum, null);
			display.setCurrent(Chumlist);
			Chumlist.setCommandListener(this);
		}
	}
}