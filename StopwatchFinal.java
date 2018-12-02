import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.util.concurrent.*;

class StopWatch implements ActionListener
{
	JFrame mainFrame=new JFrame();
	JPanel ptimer,pbutton;
	static JLabel lhr,lmin,lsec,lms;
	JButton bstart,bstop,breset;
	Rstart rstart=new Rstart();
	Thread tstart;
	ScheduledThreadPoolExecutor execService;
	
	StopWatch()
	{
		mainFrame.setSize(400,200);
		mainFrame.setLayout(new BorderLayout());
		ptimer=new JPanel();
		pbutton=new JPanel();

		ptimer.setLayout(new GridLayout(1,3));
		lhr=new JLabel("00", SwingConstants.CENTER);
		lmin=new JLabel("00", SwingConstants.CENTER);
		lsec=new JLabel("00", SwingConstants.CENTER);
		lms=new JLabel("00", SwingConstants.CENTER);
		//printout borders
		//lhr.setBorder(new LineBorder(Color.BLACK));
		//lmin.setBorder(new LineBorder(Color.BLACK));
		//lsec.setBorder(new LineBorder(Color.BLACK));
		//lms.setBorder(new LineBorder(Color.BLACK));
		//set label size(not font size)
		//lhr.setPreferredSize(new Dimension(60, 60));
		//lmin.setPreferredSize(new Dimension(60, 60));
		//lsec.setPreferredSize(new Dimension(60, 60));
		//lms.setPreferredSize(new Dimension(60, 60));
		//set font size
		lhr.setFont(new Font(lhr.getFont().getName(), lhr.getFont().getStyle(), 30));
		lmin.setFont(new Font(lmin.getFont().getName(), lmin.getFont().getStyle(), 30));
		lsec.setFont(new Font(lsec.getFont().getName(), lsec.getFont().getStyle(), 30));
		lms.setFont(new Font(lms.getFont().getName(), lms.getFont().getStyle(), 30));
		ptimer.add(lhr);ptimer.add(lmin);ptimer.add(lsec);ptimer.add(lms);

		pbutton.setLayout(new GridLayout(1,3));
		bstart=new JButton("Start");
		bstop=new JButton("Stop");
		breset=new JButton("Reset");
		breset.addActionListener(this);
		bstart.addActionListener(this);
		bstop.addActionListener(this);
		breset.addActionListener(this);
		//set font size of button text
		bstart.setFont(new Font(bstart.getFont().getName(), bstart.getFont().getStyle(), 20));
		bstop.setFont(new Font(bstop.getFont().getName(), bstop.getFont().getStyle(), 20));
		breset.setFont(new Font(breset.getFont().getName(), breset.getFont().getStyle(), 20));
		//disabling stop and reset buttons at the start
		breset.setEnabled(false);
		bstop.setEnabled(false);
		pbutton.add(bstart);pbutton.add(bstop);pbutton.add(breset);
		
		mainFrame.add(ptimer,BorderLayout.NORTH);
		mainFrame.add(pbutton,BorderLayout.SOUTH);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainFrame.setVisible(true);
		
		//scheduling our timer
		execService = new ScheduledThreadPoolExecutor(1);
        execService.scheduleAtFixedRate(rstart, 0, 1, TimeUnit.MILLISECONDS);
		execService.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
		execService.shutdown();
	}

	public void actionPerformed(ActionEvent a)
	{
		String bname=a.getActionCommand();
			if(bname.equals("Reset"))
			{
				lhr.setText("00");lmin.setText("00");lsec.setText("00");lms.setText("00");
				breset.setEnabled(false);
				bstop.setEnabled(false);
			}

			if(bname.equals("Start"))
			{
				bstart.setEnabled(false);
				bstop.setEnabled(true);
				breset.setEnabled(false);
				rstart.restart();
			}

			if(bname.equals("Stop"))
			{
				bstop.setEnabled(false);
				bstart.setEnabled(true);
				breset.setEnabled(true);
				//tstart.stop();
				rstart.terminate();
			}
		
	}

	public static void main(String args[])
	{
		new StopWatch();
	}
}

class Rstart implements Runnable
{
	private volatile boolean running = false;
	
	public void restart(){
		
		running = true;
	}
	
	public void terminate(){
		
		running = false;
	}
	
	public void run()
	{
		if(running){
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
							StopWatch.lhr.setText("0"+hr);
						else StopWatch.lhr.setText(""+hr);
					}
					else if(min<10)
						StopWatch.lmin.setText("0"+min);
					else StopWatch.lmin.setText(""+min);
				}
				else if(sec<10)
					StopWatch.lsec.setText("0"+sec);
				else StopWatch.lsec.setText(""+sec);
			}
			else StopWatch.lms.setText(""+ms);
		}
	}
}
