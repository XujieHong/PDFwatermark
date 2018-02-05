package ken.pdfwaterprint;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.dnd.DnDConstants;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.dnd.DropTarget; 
import java.awt.dnd.DropTargetDragEvent; 
import java.awt.dnd.DropTargetDropEvent; 
import java.awt.dnd.DropTargetEvent; 
import java.awt.dnd.DropTargetListener; 
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


public class PdfWP extends JFrame implements ActionListener, DropTargetListener {
	private static final long serialVersionUID = -3597288512867691569L;
	JTextField labfilepath;
	JPanel panel;
	JButton btn, btn2;
	String filepath;
	JLabel lbl;
	DropTarget dropTarget;

	public PdfWP() {
		filepath = "";
		labfilepath = new JTextField(30);
		btn = new JButton("Select PDF File:");
		btn2 = new JButton("Watermark it");
		btn.addActionListener(this);
		btn2.addActionListener(this);

		panel = new JPanel();
		panel.add(btn);
		panel.add(labfilepath);
		panel.add(btn2);
		
		lbl = new JLabel("...");
		panel.add(lbl);
		
		getContentPane().add(panel);
		setTitle("WIL PDF waterprint");
		setLocation(400,200);
		setSize(500, 150);
		setVisible(true);
	
		System.out.println("PdfWP");
		
		dropTarget = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE,this);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("actionPerformed");
		if(e.getSource() == btn) {
			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(false);
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setDialogTitle("Select your PDF file");
			int result = chooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					filepath = file.getAbsolutePath();
					labfilepath.setText( filepath);
				}
		}else if (e.getSource() == btn2) {
			//File file = new File(DEST);
	        //file.getParentFile().mkdirs();
			String filepathDst;
			filepathDst = filepath.substring(0, filepath.length() - 4);
			try{
				new TransparentWatermark().manipulatePdf(filepath, filepathDst + "_wp.pdf");
				//label.setText("Done!");
				lbl.setText("Done!");
			}catch (Exception ex){
				
			}
		}
	}


	public static void main(String args[]) {
		new PdfWP();
	}

	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		System.out.println("dragEnter");
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		System.out.println("dragOver");
		
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		System.out.println("dropActionChanged");
		
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub
		System.out.println("dragExit");
		
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		// TODO Auto-generated method stub
		System.out.println("drop");
		try{
           try {
                Transferable tr = dtde.getTransferable();

                if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    System.out.println("file cp");
                    List list = (List) (dtde.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor));
                    Iterator iterator = list.iterator();
                    while (iterator.hasNext()) {
                        File f = (File) iterator.next();
                        filepath = f.getAbsolutePath();
    					labfilepath.setText( filepath);
                        //this.setText(f.getAbsolutePath());
                    }
                    dtde.dropComplete(true);
                    //this.updateUI();
	            }else {
	                dtde.rejectDrop();
	            }
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        }
		}catch(Exception err){
			err.printStackTrace();
		}
	}
}