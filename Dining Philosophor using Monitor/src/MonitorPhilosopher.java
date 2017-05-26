import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.String;
import java.util.ArrayList;
import java.util.Scanner;
public class MonitorPhilosopher{
	
	private enum state {THINKING, HUNGRY, EAT}; //limiting every thinker's state to these three
	private state[] pstate;
	public PrintWriter writer;
	public void setphilosophoreMon(int i) throws FileNotFoundException, UnsupportedEncodingException
	{
		pstate= new state[i];
		writer = new PrintWriter("output.txt", "UTF-8"); //printing output to the file
		for(int j=0;j<pstate.length;j++)
		{
			pstate[j]=state.THINKING; // initializing every philosopher to THINKING
		}
		
		
	}

	public boolean checkNeighbour(int k) //check if neighbor is eating
	{
		if((pstate[(k+pstate.length-1)%pstate.length]==state.EAT)||(pstate[(k+1)%pstate.length]==state.EAT))
		{
			return true;
		}
		else
			return false;
	}
	public synchronized void dpWanttoEat(int i) throws InterruptedException
	{
		pstate[i]=state.HUNGRY; //setting state to HUNGRY when want to eat
		System.out.println("\nThe philosopher "+(i+1)+" is hungry now");
		writer.println("\nThe philosopher "+(i+1)+" is hungry now");
		boolean p= checkNeighbour(i);
		while(p==true)
		{
			System.out.println("\nSorry philosopher " + (i+1) + ", your neighbour is eating , you will sleep until your neighbour wakes you up");
			writer.println("\nSorry philosopher " + (i+1) + ", your neighbour is eating , you will sleep until your neighbour wakes you up");
			wait();  //when neighbor is eating then go to sleep
			p = checkNeighbour(i); // once sleep is over check the neighbor again 
		}
	
		pstate[i]=state.EAT; // As none of the neighbor is eating , state status to eating and start eating
		System.out.println("\nHey philosopher "+(i+1)+" got the sticks and eating now!!");
		writer.println("\nHey philosopher "+(i+1)+" got the sticks and eating now!!");
		
		this.wait(5000); //this the time taken by philosopher to eat which may vary in reality.
		
	
	}
	public synchronized void doneEating(int p) throws InterruptedException //enter when done eating
	{
		pstate[p]=state.THINKING; // putting down chop-sticks and thinking again
		System.out.println("\n Philosopher " + (p+1) + " done eating");
		writer.println("\n Philosopher " + (p+1) + " done eating");
		notifyAll(); //wake up all who are sleeping as chop-sticks are available 
	}

	public static void main(String[] args) throws InterruptedException, FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
		MonitorPhilosopher mp= new MonitorPhilosopher();
		System.out.println("\nEnter the total number of dining philosophers");
		Scanner scan = new Scanner(System.in); //get total number of philosophers on the table
		int total= scan.nextInt();
		mp.setphilosophoreMon(total);
		
		dining[] d = new dining[total];
		for(int i=0; i<total; i++)
		{
			d[i] = new dining(mp, i);
			d[i].start(); // spawning threads for each philosopher
		}
		
		for(int i=0; i<total; i++)
		{
			d[i].join(); //waits for threads to finish and join
		}
		
		mp.writer.close();
	
    }
}

class dining extends Thread
{
	MonitorPhilosopher m;
	int phi;
	dining(MonitorPhilosopher mp, int i)
	{
		m = mp;
		phi = i;
	}
	
	public void run()
	{
		try {
			m.dpWanttoEat(phi); //running threads and calling want to eat function to pick up the chop-sticks
			m.doneEating(phi);  //running threads and calling want to put down the chop-sticks 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}


