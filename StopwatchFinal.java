import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class StopWatch implements ActionListener
{
	JFrame mainFrame=new JFrame();
	JPanel ptimer,pbutton;
	static JLabel lhr,lmin,lsec,lms;
	Button bstart,bstop,breset;
	Rstart rstart=new Rstart();
	Thread tstart;
	
	StopWatch()
	{
		mainFrame.setSize(120,120);
		mainFrame.setLayout(new BorderLayout());
		ptimer=new JPanel();
		pbutton=new JPanel();

		ptimer.setLayout(new GridLayout(1,3));
		lhr=new JLabel("00");
		lmin=new JLabel("00");
		lsec=new JLabel("00");
		lms=new JLabel("00");
		ptimer.add(lhr);ptimer.add(lmin);ptimer.add(lsec);ptimer.add(lms);

		pbutton.setLayout(new GridLayout(1,3));
		bstart=new Button("Start");
		bstop=new Button("Stop");
		breset=new Button("Reset");
		breset.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent ae) {
				breset.setEnabled(false);
				bstop.setEnabled(false);
			 }
		   }
		 );
		bstart.addActionListener(this);
		bstop.addActionListener(this);
		breset.addActionListener(this);
		pbutton.add(bstart);pbutton.add(bstop);pbutton.add(breset);
		
		mainFrame.add(ptimer,BorderLayout.NORTH);
		mainFrame.add(pbutton,BorderLayout.SOUTH);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainFrame.setVisible(true);
	}

	public void actionPerformed(ActionEvent a)
	{
		String bname=a.getActionCommand();
			if(bname.equals("Reset"))
			{
				lhr.setText("00");lmin.setText("00");lsec.setText("00");lms.setText("00");
			}

			if(bname.equals("Start"))
			{
				//if(!tstart.getName().equals("Thread-0"))
					bstart.setEnabled(false);
					bstop.setEnabled(true);
					breset.setEnabled(false);
					tstart=new Thread(rstart);
					tstart.start();
			}

			if(bname.equals("Stop"))
			{
					bstop.setEnabled(false);
					bstart.setEnabled(true);
					breset.setEnabled(true);
					tstart.stop();
			}
		
	}

	public static void main(String args[])
	{
		new StopWatch();
	}
}

class Rstart implements Runnable
{
	public void run()
	{
		for(;;)
		{
			int ms=Integer.parseInt(StopWatch.lms.getText());
			int sec=Integer.parseInt(StopWatch.lsec.getText());
			int min=Integer.parseInt(StopWatch.lmin.getText());
			int hr=Integer.parseInt(StopWatch.lhr.getText());
			ms++;
			if(ms==1000)
			{
				StopWatch.lms.setText("00");
				sec++;
				if(sec==60)
				{
					StopWatch.lsec.setText("00");
					min++;
					if(min==60)
					{
						StopWatch.lmin.setText("00");
						hr++;
						if(hr<10)
							StopWatch.lhr.setText("0"+Integer.toString(hr));
						else StopWatch.lhr.setText(Integer.toString(hr));
					}
					else if(min<10)
						StopWatch.lmin.setText("0"+Integer.toString(min));
					else StopWatch.lmin.setText(Integer.toString(min));
				}
				else if(sec<10)
					StopWatch.lsec.setText("0"+Integer.toString(sec));
				else StopWatch.lsec.setText(Integer.toString(sec));
			}
			else StopWatch.lms.setText(Integer.toString(ms));
			
			try{
			Thread.sleep(1);}
			catch(InterruptedException ie)
			{ie.printStackTrace();}
		}
	}
}