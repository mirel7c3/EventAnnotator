package de.tuc.miteinander.data.event.gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import de.tuc.miteinander.data.event.model.AppState;
import de.tuc.miteinander.data.event.model.InteractionType;
import de.tuc.miteinander.data.event.model.UserType;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Spinner;

public class EventAnnotator {

	protected Shell shlEventannotator;
	private Text playtimeText;
	private Text currentEventText;
	private Text movieStartTimeText;
	private Text consoleText;
	private Text videoText;
	private Text userDescriptionText;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			EventAnnotator window = new EventAnnotator();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlEventannotator.open();
		shlEventannotator.layout();
		while (!shlEventannotator.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlEventannotator = new Shell();
		shlEventannotator.setSize(621, 771);
		shlEventannotator.setText("EventAnnotator");
		shlEventannotator.setLayout(new GridLayout(4, false));
		
		Label lblVideoFile = new Label(shlEventannotator, SWT.NONE);
		lblVideoFile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVideoFile.setText("Video File:");
		
		videoText = new Text(shlEventannotator, SWT.BORDER);
		videoText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnLoad = new Button(shlEventannotator, SWT.NONE);
		btnLoad.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shlEventannotator, SWT.OPEN);
				   dialog.setFilterExtensions(new String [] {"*.mp4","*.*"});
				   dialog.setFilterPath("c:\\temp");
				   String result = dialog.open();
				   if (result!=null) { 
					   videoText.setText(result);
				   }
			}
		});
		btnLoad.setText("Load");
		
		Button btnOpenInQt = new Button(shlEventannotator, SWT.NONE);
		btnOpenInQt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Runtime runtime = Runtime.getRuntime();
			    
			    String applescriptCommand =  "tell app \"QuickTime Player\"\n" +
			    								"activate\n" +
			                                   "open \""+videoText.getText()+"\"\n"+
			                                 "end tell";
			 
			    String[] args2 = { "osascript", "-e", applescriptCommand };
			 
			    try
			    {
			      runtime.exec(args2);
			    }
			    catch (IOException exception)
			    {
			    	exception.printStackTrace();
			    }
			}
		});
		btnOpenInQt.setText("Open in QT");
		
		Group grpTime = new Group(shlEventannotator, SWT.NONE);
		grpTime.setText("Time");
		grpTime.setLayout(new GridLayout(4, false));
		grpTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 4, 1));
		
		Label lblMovieStartTime = new Label(grpTime, SWT.NONE);
		lblMovieStartTime.setText("Movie start time:");
		
		movieStartTimeText = new Text(grpTime, SWT.BORDER);
		movieStartTimeText.setText("1970-01-01 00:00:00,000");
		movieStartTimeText.setToolTipText("yyyy-MM-dd HH:mm:ss,SSS");
		movieStartTimeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button checkBtn = new Button(grpTime, SWT.NONE);
		
		checkBtn.setText("Check");
		
		Label lblPressCheck = new Label(grpTime, SWT.NONE);
		lblPressCheck.setText("press Check");
		
		checkBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
				try {
					df.parse(movieStartTimeText.getText());
					lblPressCheck.setText("accepted");
					currentEventText.setText(movieStartTimeText.getText());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					lblPressCheck.setText("failed");
				}
			}
		});
		
		Label lblTime = new Label(grpTime, SWT.NONE);
		lblTime.setText("Playtime:");
		
		playtimeText = new Text(grpTime, SWT.BORDER);
		playtimeText.setText("0");
		playtimeText.setToolTipText("in seconds");
		playtimeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnFetchTime = new Button(grpTime, SWT.NONE);
		btnFetchTime.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnFetchTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Runtime runtime = Runtime.getRuntime();
			    
			    String applescriptCommand =  "tell app \"QuickTime Player\"\n" +
			                                   "return the current time of the front document\n"+
			                                 "end tell";
			 
			    String[] args2 = { "osascript", "-e", applescriptCommand };
			 
			    try
			    {
			      Process process = runtime.exec(args2);
			      BufferedReader bufferedReader = new BufferedReader(
			    		    new InputStreamReader(process.getInputStream()));
			      String lsString;
			      String result = ""; 
			      while ((lsString = bufferedReader.readLine()) != null) {
			    	  result+=lsString;
			      }
			      
			      if (result.contains(".")) {
			    	  Double d = Double.valueOf(result);
			    	  result=""+d.intValue();
			      }
			      playtimeText.setText(result);
			    }
			    catch (IOException exception)
			    {
			    	exception.printStackTrace();
			    }
			    
			    
			}
		});
		btnFetchTime.setText("Fetch Time From QT");
		new Label(grpTime, SWT.NONE);
		new Label(grpTime, SWT.NONE);
		new Label(grpTime, SWT.NONE);
		
		Button addTimeBtn = new Button(grpTime, SWT.NONE);
		addTimeBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String movieStart = movieStartTimeText.getText();
				String playTime = playtimeText.getText();
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
				try {
					Date startDate = df.parse(movieStart);
					int seconds = Integer.parseInt(playTime);
					
					Calendar cl = Calendar. getInstance();
				    cl.setTime(startDate);
				    cl.add(Calendar.SECOND, seconds);
				    
				    currentEventText.setText(df.format(cl.getTime()) + currentEventText.getText());
				    
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		addTimeBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		addTimeBtn.setText("Add");
		
		Group grpUser = new Group(shlEventannotator, SWT.NONE);
		grpUser.setText("User");
		grpUser.setLayout(new GridLayout(8, false));
		grpUser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		
		Group grpCreateUser = new Group(grpUser, SWT.NONE);
		grpCreateUser.setText("create User");
		grpCreateUser.setLayout(new GridLayout(3, false));
		grpCreateUser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 8, 1));
		
		Label lblUserId = new Label(grpCreateUser, SWT.NONE);
		lblUserId.setText("User ID:");
		
		Spinner idSpinner = new Spinner(grpCreateUser, SWT.BORDER);
		new Label(grpCreateUser, SWT.NONE);
		
		Label lblType = new Label(grpCreateUser, SWT.NONE);
		lblType.setText("Type:");
		
		Combo userTypeCombo = new Combo(grpCreateUser, SWT.NONE);
		userTypeCombo.setItems(UserType.getTypeNames());
		userTypeCombo.select(0);
		new Label(grpCreateUser, SWT.NONE);
		
		Label lblUserDescription = new Label(grpCreateUser, SWT.NONE);
		lblUserDescription.setText("User Description:");
		
		userDescriptionText = new Text(grpCreateUser, SWT.BORDER);
		userDescriptionText.setToolTipText("Some text that helps you to identify the person");
		userDescriptionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnCreateUser = new Button(grpCreateUser, SWT.NONE);
		
		btnCreateUser.setText("Create User");
		
		Label lblFormerIds = new Label(grpUser, SWT.NONE);
		lblFormerIds.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblFormerIds.setText("Select User:");
		
		Combo selectUserCombo = new Combo(grpUser, SWT.NONE);
		selectUserCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		selectUserCombo.select(0);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		
		Label lblInteraction = new Label(grpUser, SWT.NONE);
		lblInteraction.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInteraction.setText("Interaction:");
		
		Combo userInteractionCombo = new Combo(grpUser, SWT.NONE);
		userInteractionCombo.setItems(InteractionType.getInteractionNames());
		userInteractionCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		userInteractionCombo.select(0);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		
		Label lblPosition = new Label(grpUser, SWT.NONE);
		lblPosition.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPosition.setText("Position:");
		
		Combo userPositionCombo = new Combo(grpUser, SWT.NONE);
		userPositionCombo.setItems(new String[] {"K1", "K2", "L1", "L2", "R1", "R2"});
		userPositionCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		userPositionCombo.select(0);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		new Label(grpUser, SWT.NONE);
		
		Button addUserBtn = new Button(grpUser, SWT.CENTER);
		addUserBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				currentEventText.append(" "+
					extractUserData(selectUserCombo.getText()) +
					userInteractionCombo.getText() + " " +
					userPositionCombo.getText());

			}

			private String extractUserData(String text) {
				return text.split("–")[0];
			}
		});
		addUserBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 6, 1));
		addUserBtn.setText("Add");
		new Label(shlEventannotator, SWT.NONE);
		new Label(shlEventannotator, SWT.NONE);
		new Label(shlEventannotator, SWT.NONE);
		new Label(shlEventannotator, SWT.NONE);
		
		btnCreateUser.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean contained = false;
				
				String potNewUser = "user "+idSpinner.getText()+" "+userTypeCombo.getText()+" – "+userDescriptionText.getText();
				
				for (String s:selectUserCombo.getItems()) {
					if (s.equals(idSpinner.getText())) contained = true;
				}
				if (!contained) {
					selectUserCombo.add(potNewUser);
				}
					
			}
		});
		
		Group grpAppstate = new Group(shlEventannotator, SWT.NONE);
		grpAppstate.setLayout(new GridLayout(2, false));
		grpAppstate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		grpAppstate.setText("AppState");
		
		Label lblApplication = new Label(grpAppstate, SWT.NONE);
		lblApplication.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblApplication.setText("Application:");
		
		Combo stateCombo = new Combo(grpAppstate, SWT.NONE);
		stateCombo.setItems(AppState.getTitles());
		stateCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		stateCombo.select(0);
		new Label(grpAppstate, SWT.NONE);
		
		Button addStateBtn = new Button(grpAppstate, SWT.NONE);
		addStateBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				currentEventText.append( " " + stateCombo.getText());
			}
		});
		addStateBtn.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		addStateBtn.setText("Add");
		
		Label lblCurrentEvent = new Label(shlEventannotator, SWT.NONE);
		lblCurrentEvent.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCurrentEvent.setText("Current Event");
		
		currentEventText = new Text(shlEventannotator, SWT.BORDER);
		currentEventText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Button saveButton = new Button(shlEventannotator, SWT.NONE);
		
		saveButton.setText("Save");
		
		saveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				consoleText.append(currentEventText.getText()+"\n");
				currentEventText.setText("");
			}
		});
		
		Label lblNewEvents = new Label(shlEventannotator, SWT.NONE);
		lblNewEvents.setText("New Events:");
		new Label(shlEventannotator, SWT.NONE);
		new Label(shlEventannotator, SWT.NONE);
		new Label(shlEventannotator, SWT.NONE);
		
		consoleText = new Text(shlEventannotator, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_consoleText = new GridData(SWT.FILL, SWT.FILL, true, true, 3, 3);
		gd_consoleText.heightHint = 63;
		consoleText.setLayoutData(gd_consoleText);
		
		Button btnSaveToLogfile = new Button(shlEventannotator, SWT.NONE);
		btnSaveToLogfile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				 FileDialog dialog = new FileDialog(shlEventannotator, SWT.SAVE);
			    dialog
			        .setFilterNames(new String[] { "Log Files", "All Files (*.*)" });
			    dialog.setFilterExtensions(new String[] { "*.log", "*.*" }); // Windows
			                                    // wild
			                                    // cards
			    dialog.setFilterPath("~/"); // Windows path
			    dialog.setFileName("event.log");
			    String filename = dialog.open();
			    
			    if (filename != null){
				    List<String> lines = Arrays.asList(consoleText.getText().split("\n"));
				    Path file = Paths.get(filename);
				    try {
						Files.write(file, lines, Charset.forName("UTF-8"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }
			}
		});
		btnSaveToLogfile.setText("Save Log");
		
		Button btnLoadLog = new Button(shlEventannotator, SWT.NONE);
		btnLoadLog.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				 FileDialog dialog = new FileDialog(shlEventannotator, SWT.OPEN);
				    dialog
				        .setFilterNames(new String[] { "Log Files", "All Files (*.*)" });
				    dialog.setFilterExtensions(new String[] { "*.log", "*.*" });
				    dialog.setFilterPath("~/");
				    String filename = dialog.open();
				    
				    if (filename != null){
					    Path file = Paths.get(filename);
					    
					    consoleText.setText("");
					    
					    try (BufferedReader reader = Files.newBufferedReader(file)) {
					        String line = null;
					        while ((line = reader.readLine()) != null) {
					           consoleText.append(line+"\n");
					        }
					    } catch (IOException x) {
					        System.err.format("IOException: %s%n", x);
					    }
				    }
			}
		});
		btnLoadLog.setText("Load Log");
		
		Button btnClear = new Button(shlEventannotator, SWT.NONE);
		btnClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				consoleText.setText("");
			}
		});
		btnClear.setText("Clear");

	}
}
