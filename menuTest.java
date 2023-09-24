import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

public class menuTest extends MIDlet implements CommandListener{
	private Display display;
	private Command Add, Cancel, Delete, Exit, Ok;
	private List ChumList, ChumSelect;
	private TextBox AddChum;
	private int chum, currentScreen;
	
	public menuTest(){
		Add = new Command ("Add Chum", Command.SCREEN, 1);
		Cancel = new Command ("Cancel", Command.BACK, 1);
		Delete = new Command ("Remove chum", Command.SCREEN, 2);
		Exit = new Command ("Exit", Command.EXIT, 0);
		Ok = new Command ("Ok", Command.OK, 1);
		ChumList = new List ("Chumlist", Choice.IMPLICIT);
		ChumSelect = new List ("Select Chum", Choice.IMPLICIT);
		AddChum = new TextBox ("add chum", "", 30, TextField.ANY);
	}
	
	public void startApp(){
		display = Display.getDisplay(this);
		ChumList.addCommand(Add);
		ChumList.addCommand(Delete);
		ChumList.addCommand(Exit);
		switchToChumList();
		AddChum.addCommand(Ok);
		AddChum.addCommand(Cancel);
		ChumSelect.addCommand(Cancel);
	}
	
	public void pauseApp(){}
	
	public void destroyApp(boolean unconditional){
		notifyDestroyed();
	}
	
	public void switchToChumList(){
		currentScreen = 0;
		display.setCurrent(ChumList);
		ChumList.setCommandListener(this);
	}
	
	public void switchToSelect(){
		currentScreen = 1;
		display.setCurrent(ChumSelect);
		ChumSelect.setCommandListener(this);
	}
	
	public void addChumIfNotEmpty(){
		String Chum = AddChum.getString();
			AddChum.setString("");
			if (!Chum.equals("")){
				ChumList.append(Chum, null);
				ChumSelect.append(Chum, null);
			}
	}
	
	public void commandAction(Command c, Displayable S){
		String label = c.getLabel();
		if (label.equals("Add Chum")){
			currentScreen = 2;
			display.setCurrent(AddChum);
			AddChum.setCommandListener(this);
		}
		else if (label.equals("Cancel")){
			if(currentScreen == 1){
				switchToChumList();
				System.out.println("switched from ChumSelect to ChumList");
			}
			else if (currentScreen == 2){
				System.out.println("switched from AddChum to ChumList");
				switchToChumList();
			}
		}
		else if (label.equals("Remove chum")){
			switchToSelect();
		}
		else if (label.equals("Exit")){
			destroyApp(false);
		}
		else if (label.equals("Ok")){
			addChumIfNotEmpty();
			switchToChumList();
		}
		if (c == ChumSelect.SELECT_COMMAND){
			chum = ChumSelect.getSelectedIndex();
			ChumList.delete(chum);
			switchToChumList();
		ChumSelect.delete(chum);
		}
	
	}
	
}
