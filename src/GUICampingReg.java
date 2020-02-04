import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;

public class GUICampingReg extends JFrame implements ActionListener{

	private Timer timer;
	private saveTimer autosave;
	private int counter;

	private JMenuBar menus;
	private JMenu fileMenu;
	private JMenu checkIn;

	private JMenuItem delete;
	private JMenuItem undo;
	private JMenuItem openSer;
	private JMenuItem saveSer;
	private JMenuItem saveTxt;
	private JMenuItem openTxt;
	private JMenuItem exitItm;
	private JMenuItem checkInTnt;
	private JMenuItem checkInRv;

	private JTable siteList;
	private SiteModel sList;

	private RV rv;
	private Tent tents;

	public GUICampingReg() {

		autosave = new saveTimer();
		timer = new Timer(60000, autosave);
		counter = 0;

		fileMenu = new JMenu("File");
		checkIn = new JMenu("Checking In");
				
		delete = new JMenuItem("Delete Reserv.");
		undo = new JMenuItem("Undo");
		openSer = new JMenuItem("Open Serial");
		saveSer = new JMenuItem("Save Serial");
		exitItm = new JMenuItem("Exit");
		openTxt = new JMenuItem("Open Text");
		saveTxt = new JMenuItem("Save Text");
		checkInTnt = new JMenuItem("Check-In Tent Site");
		checkInRv = new JMenuItem("Check-In RV Site");
		
		fileMenu.add(openSer);
		fileMenu.add(saveSer);
		fileMenu.addSeparator();
		fileMenu.add(openTxt);
		fileMenu.add(saveTxt);
		fileMenu.addSeparator();
		fileMenu.add(undo);
		fileMenu.add(delete);
		fileMenu.addSeparator();
		fileMenu.add(exitItm);
		
		checkIn.add(checkInTnt);
		checkIn.add(checkInRv);

		openSer.addActionListener(this);
		saveSer.addActionListener(this);
		exitItm.addActionListener(this);
		openTxt.addActionListener(this);
		saveTxt.addActionListener(this);
		checkInTnt.addActionListener(this);
		checkInRv.addActionListener(this);
		undo.addActionListener(this);
		delete.addActionListener(this);

		menus = new JMenuBar();

		menus.add(fileMenu);
		menus.add(checkIn);
		
		setJMenuBar(menus);

		sList = new SiteModel();
		siteList = new JTable(sList);

		add(siteList);

		setVisible(true);
		setSize(700,500);	

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		timer.start();
	}

	public static void main (String[] args) {
		new GUICampingReg();
	}

	private String promptSave() {
		try {
			JFrame frame = new JFrame("Display File");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JFileChooser chooser = new JFileChooser();

			int chooserStatus = chooser.showSaveDialog(null);
			if (chooserStatus == JFileChooser.APPROVE_OPTION) {
				return chooser.getSelectedFile().getAbsolutePath();
			}
		} 
		catch (Exception e2) {
			JOptionPane.showMessageDialog(null,"Save failed..., please try again.");
		}
		return "";
	}

	private String promptLoad() {
		try {

			JFrame frame = new JFrame("Display File");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JFileChooser chooser = new JFileChooser();
			int chooserStatus = chooser.showOpenDialog(null);
			if (chooserStatus == JFileChooser.APPROVE_OPTION) {
				return chooser.getSelectedFile().getAbsolutePath();
			}
		}
		catch (Exception e2) {
			JOptionPane.showMessageDialog(null,"Load failed..., please try again.");
		}
		return "";
	}

	public void actionPerformed(ActionEvent e) {

		if (exitItm == e.getSource()) 
			System.exit(0);

		if(checkInRv == e.getSource()) {
			rv = new RV();
			DialogCheckInRv x1 = new DialogCheckInRv(this, rv);
			x1.setVisible(true);
			if(x1.closeChk()) {
				try {
					sList.addSite(rv);
					sList.addIndexUndo(rv);
					JOptionPane.showMessageDialog(null, ("You owe: " + rv.getCost() + " Dollars"));
				}
				catch(IllegalArgumentException e2) {
					for(int i = 0; i < 5; i++)
					{					
						System.out.println("The taken dates are: " + sList.getBtwnD(rv.checkIn,sList.checkOut(sList.getSite(i))).get(i));
						
					}
						
				}
			}
			repaint();
		}

		if(checkInTnt == e.getSource()) {
            tents = new Tent();
            DialogCheckInTent x2 = new DialogCheckInTent(this, tents);
            x2.setVisible(true);
            if(x2.closeChk()) {
                try {
                    sList.addSite(tents);
                    sList.addIndexUndo(tents);
                    JOptionPane.showMessageDialog(null, ("You owe: " + tents.getCost() + " Dollars"));
                }
                catch(IllegalArgumentException e2) { 
                	for(int i = 0; i < 5; i++)
					{					
						System.out.println("The taken dates are: " + sList.getBtwnD(rv.checkIn,sList.checkOut(sList.getSite(i))).get(i));
						
					}
                    }
                              
            }
            repaint();
        }

		if(openSer == e.getSource()) {
            try {
                String temp = promptLoad();
                if (temp != null) {
                    sList.loadAsSerialized(temp);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

		if(saveSer == e.getSource()) {
            try {
                sList.saveAsSerialized(promptSave());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

		if(openTxt == e.getSource()) {
            try {
                String temp = promptLoad();
                if (temp != null) {
                    sList.loadAsText(temp);
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Corrupt Data: Unable to load file");
                remove(siteList);
                sList = new SiteModel();
                siteList = new JTable(sList);
                add(siteList);
                e1.printStackTrace();
                repaint();
            }
        }

		if(saveTxt == e.getSource()) {
            try {
                sList.saveAsText(promptSave());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
		
		if(delete == e.getSource()) {
			
			try {
				DialogDelete delete = new DialogDelete(this,sList);	
				delete.setVisible(true);
				repaint();
			}
			catch (Exception ee) {
				JOptionPane.showMessageDialog(null,"Unable to delete the data.");
			}
		}
		
		if(undo == e.getSource()) {
            try {
            if(sList.getUndoRecent().charAt(0) == 'I') {
                sList.reAddSite(Integer.parseInt(sList.getUndoRecent().substring(1)));
                repaint();
            }
            else if(sList.getUndoRecent().charAt(0) == 'D') {
                sList.removeSiteOffUndo(Integer.parseInt(sList.getUndoRecent().substring(1)));
                repaint();
            }
            }
            catch(IndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "There is nothing to undo");
            }
        }
	}

	private class saveTimer implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			switch(counter) {
			case 0:
				sList.saveAsText("Saves\\Save1");
				sList.saveAsSerialized("Saves\\\\Save1");
				break;
			case 1:
				sList.saveAsText("Saves\\Save2");
				sList.saveAsSerialized("Saves\\\\Save2");
				break;
			case 2:
				sList.saveAsText("Saves\\Save3");
				sList.saveAsSerialized("Saves\\\\Save3");
				break;
			case 3:
				sList.saveAsText("Saves\\Save4");
				sList.saveAsSerialized("Saves\\\\Save4");
				break;
			case 4:
				sList.saveAsText("Saves\\Save5");
				sList.saveAsSerialized("Saves\\\\Save5");
				break;
			default:
				counter=0;
				break;
			}
			counter++;
			System.out.println("Save " + (counter) + " overwritten on " + new GregorianCalendar().getTime());
		}

	}

}
