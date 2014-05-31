package br.com.dsoo.facebook.view;

import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class LoadingDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	
	public LoadingDialog(JFrame parent, String msg){
		super(parent, msg, true);
		
		final JPanel p = new JPanel();
		
		final JProgressBar bar = new JProgressBar();
		bar.setIndeterminate(true);
		final JLabel lb = new JLabel(msg);
		
		p.setLayout(new FlowLayout(FlowLayout.LEADING));
		p.add(lb);
		p.add(bar);
	    p.setSize(300, 75);
	    setContentPane(p);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    pack();
	    setLocationRelativeTo(parent);
	    	    
	    Runnable run = new Runnable(){
			@Override
			public void run(){
				setVisible(true);
				try{
					Thread.sleep(500);
				}catch(InterruptedException e){
					Alert.showError(e);
				}
			}
		};
		new Thread(run).start();
	}

}
