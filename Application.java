import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*; // file handling
import java.util.*; // linked list
class LoginData implements Serializable
{
	String id;
	String pwd;
	
	LoginData()
	{
		id = "";
		pwd = "";
	}
	
	LoginData(String a, String b)
	{
		id = a;
		pwd = b;
	}
	
	static boolean isValidPassword(String str)
	{
		int n = str.length();
		if(n<8)
			return false;
		int i=0;
		int uc = 0;
		int lc = 0;
		int dc = 0;
		int sc = 0;
		while(i<n)
		{
			char ch = str.charAt(i); // return ith pos char from string
			if(ch>=65 && ch<=90)
				uc++;
			else 
			{
				if(ch>=97 && ch<=122)
					lc++;
				else
				{
					if(ch>=48 && ch<=57)
						dc++;
					else
						sc++;
				}
			}
			i++;
		}
		return (uc>0 && lc>0 && dc>0 && sc>0);
	}
}

class IPD
{
	LinkedList<LoginData>ls;
	LoginData obj;
	
	IPD()
	{
		ls = new LinkedList<LoginData>();
	}
	
	void load()
	{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try
		{
			fis = new FileInputStream("login.dat");
			ois = new ObjectInputStream(fis);
			ls = (LinkedList<LoginData>) ois.readObject(); // copying file data to ls
			ois.close();
			fis.close();
		}
		catch(Exception e) {e.printStackTrace();}
	}
	
	void save()
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try
		{
			fos = new FileOutputStream("login.dat");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ls); // writing ll to the file
			oos.close();
			fos.close();
		}
		catch(Exception e) {}
	}
	
	void search(String sid)
	{
		int i = 0, n = ls.size();
		while(i<n)
		{
			obj = ls.get(i);
			if((sid.equals(obj.id)) == true)
					break;
			else
				i++;
		}
		if(i==n)
			obj = null; // record not found
	}
	
	boolean isValidUser(String id)
	{
		search(id);
		if(obj==null)
			return false;
		if(id.equals(obj.id))
			return true;
		else
			return false;
	}
	
	boolean isValidUserPassword(String id, String pw)
	{
		search(id);
		if(obj==null)
			return false;
		if(id.equals(obj.id) && pw.equals(obj.pwd))
			return true;
		else
			return false;
	}
}

class NewUser extends JDialog implements ActionListener, FocusListener
{
	JLabel l1, l2, l3, l4, l5;
	JTextField t1, t2, t5;
	JPasswordField t4;
	JButton b1, b2;
	Choice year;
	String fnm, lnm, byear;
	String id;
	IPD upobj; // to make ll available
	
	NewUser(JFrame frm, String title, boolean state, IPD aobj)
	{
		super(frm, title, state);
		
		upobj = aobj; 
		
		l1 = new JLabel("First name");
		l2 = new JLabel("Last name");
		l3 = new JLabel("Birth year");
		l4 = new JLabel("Type password");
		l5 = new JLabel("Retype password");
		t1 = new JTextField(20);
		t2 = new JTextField(20);
		t4 = new JPasswordField();
		t5 = new JTextField(20);
		year = new Choice();
		
		for(int i=1923; i<=2023; i++)
			year.add(""+i);
		
		t1.addFocusListener(this);
		t2.addFocusListener(this);
		t4.addFocusListener(this);
		t5.addFocusListener(this);
		b1 = new JButton("Add");
		b2 = new JButton("Back");
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		
		setLayout(new GridLayout(6, 2, 5, 5));
		
		add(l1);
		add(t1);
		add(l2);
		add(t2);
		add(l3);
		add(year);
		add(l4);
		add(t4);
		add(l5);
		add(t5);
		add(b1);
		add(b2);
		
		setSize(300, 500);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b==b1)
		{
			id = fnm+lnm;
			if(year.getSelectedIndex()==-1)// if choice is unselected
			{
				year.requestFocus();
				return;
			}
			byear = "" + year.getSelectedItem();
			id = id+byear;
			if(upobj.isValidUser(id)==true)
			{
				JOptionPane.showMessageDialog(null, "Record exist");
			}
			else
			{
				String pwd =new String(t4.getPassword());
				LoginData obj = new LoginData(id, pwd);
				upobj.ls.add(obj);
			}
		}
		setVisible(false);
	}
	
	public void focusGained(FocusEvent e)
	{
		
	}
	
	public void focusLost(FocusEvent e)
	{
		JTextField t = (JTextField)e.getSource();
		if(t==t1)
		{
			fnm = (t1.getText()).trim();
		}
		if(t==t2)
		{
			lnm = (t2.getText()).trim();
		}
		if(t==t4)
		{
			String a = new String(t4.getPassword());
			if(LoginData.isValidPassword(a))
			{
				t5.requestFocus();
				return;
			}
		}
		if(t==t5)
		{
			String a = new String(t4.getPassword());
			String b = t5.getText();
			if(a.equals(b)==false)
			{
				t5.requestFocus();
				return;
			}
		}
	}
}

class LoginUser extends JDialog implements ActionListener
{
	JLabel l1, l2;
	JTextField t1;
	JPasswordField t2;
	JButton b1, b2;
	JButton bm;
	IPD upobj;
	LoginData obj;
	String id;
	String pw;
	ModifyPassword mobj;
	LoginUser(JFrame frm, String title, boolean state, IPD tobj)
	{
		super(frm, title, state);
		
		upobj = tobj;
		obj = null;
		l1 = new JLabel("ID");
		l2 = new JLabel("Password");
		t1 = new JTextField(20);
		t2 = new JPasswordField(20);
		b1 = new JButton("Login");
		b2 = new JButton("Back");
		bm=new JButton("Modify Password");
		b1.addActionListener(this);
		b2.addActionListener(this);
		bm.addActionListener(this);
		
		setLayout(new GridLayout(4, 2, 5, 5));
		add(l1);
		add(t1);
		add(l2);
		add(t2);
		add(b1);
		add(b2);
		add(bm);
		
		setSize(400, 400);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		
		if(b==b1)
		{
			id = t1.getText();
			pw = new String(t2.getPassword());
			if(upobj.isValidUserPassword(id, pw)==true)
			{
				JOptionPane.showMessageDialog(null, "Login valid");
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Login invalid");
			}
		}
		if(b==bm)
		{  
          mobj = new ModifyPassword(null, "ModifyPassword", true, upobj); 
		}
		setVisible(false);
	}
}
class ModifyPassword extends JDialog implements FocusListener,ActionListener
{
    JLabel l1,l2,l3;
    JTextField t1,t3;
    JPasswordField t2;
    JButton b1,b2;
    String s1,s2,s3;
    IPD upobj;
    LoginData obj;
    ModifyPassword(JFrame frm,String title,boolean state,IPD iobj)
    {
        super(frm,title,state);
        upobj=iobj;
        obj=null;
        l1=new JLabel("Id");
        l2=new JLabel("Password");
        l3=new JLabel("Retype Password");
        t1=new JTextField(20);
        t2=new JPasswordField(20);
        t3=new JTextField(20);

        t1.addFocusListener(this);
        t2.addFocusListener(this);
        t3.addFocusListener(this);
        
        b1=new JButton("Ok");
		b1.setBackground(Color.MAGENTA);
        b2=new JButton("Back");
		b2.setBackground(Color.MAGENTA);
        b1.addActionListener(this);
        b2.addActionListener(this);
		
        setLayout(new GridLayout(4,2,5,5));
        add(l1);
        add(t1);
        add(l2);
        add(t2);
        add(l3);
        add(t3);
        add(b1);
        add(b2);

        setSize(400,300);
        setVisible(true);
    }
    public void focusGained(FocusEvent e){}
    public void focusLost(FocusEvent e)
    {
        JTextField t=(JTextField)e.getSource();
        if(t==t1)
        {
            s1=t1.getText();//collect id
            if(!(upobj.isValidUser(s1)))
            {
                JOptionPane.showMessageDialog(null,"Invalid User ID");
                t1.requestFocus();
                return;
            }
        }
        if(t==t2)
        {
            s2=t2.getText();
            if(!(LoginData.isValidPassword(s2)))
            {
                t2.requestFocus();
                return;
            }
        }
        if(t==t3)
        {
            s2 = new String(t2.getPassword());
            s3=t3.getText();
            if(s2.equals(s3)==false)
            {
               t3.requestFocus();
               return;
            }
        }
    }
    public void actionPerformed(ActionEvent e)
    {
        JButton b=(JButton)e.getSource();
        if(b==b1)
        {
            upobj.obj.pwd=s2;

        }
        setVisible(false);
    }
}

public class Application extends JFrame implements ActionListener
{
	IPD upobj;
	NewUser nobj;
	
	JButton bn; // neW User
	JButton bl; // login
	
	Application()
	{
		super("Application");
		
		bn = new JButton("New User");
		bn.setBackground(Color.BLUE);
		bl = new JButton("Login");
		bl.setBackground(Color.GREEN);
		
		bn.addActionListener(this);
		bl.addActionListener(this);
		
		setLayout(null);
		
		bn.setBounds(50, 100, 150, 20);
		bl.setBounds(250, 100, 150, 20);
		
		add(bn);
		add(bl);
		
		setSize(450, 200);
		upobj = new IPD();
		
		addWindowListener(new WindowAdapter() 
		{
					public void windowOpened(WindowEvent e)
					{
						upobj.load();
					}
					public void windowClosing(WindowEvent e)
					{
						upobj.save();
						System.exit(0);
					}});
		setVisible(true);
		}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton b = (JButton)e.getSource();
		if(b==bn)
			nobj = new NewUser(null, "new User", true, upobj);
		if(b==bl)
		{
			LoginUser lobj = new LoginUser(null, "Login", true, upobj);
		}
	}
	
	public static void main(String[]args)
	{
		Application a = new Application();
	}
}