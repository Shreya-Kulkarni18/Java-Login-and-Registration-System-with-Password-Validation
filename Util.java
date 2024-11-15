package mypkg;
import javax.swing.*;
public class Util
{
    public static void display(String str)
    {
        JOptionPane.showMessageDialog(null,str);
    }
    public static int iInput(String str)    //integer type input
    {
        int no=0;
        try 
        {
            no=Integer.parseInt(JOptionPane.showInputDialog(null,str));
        } catch (Exception e) 
        {
            // TODO: handle exception
        }
        return no;
    }
    public static double dInput(String str) //double type input
    {
        double no=0.0;
        try
        {
            no=Double.parseDouble(JOptionPane.showInputDialog(null,str));
        }
        catch(Exception e){}
        return no;
    }
    public static String sInput(String str) //string type input
    {
        String s=JOptionPane.showInputDialog(null,str);
        return s;
    }
    public static int oInput(String msg,String title,String[]choice)
    {
        int opt=0;
        opt=JOptionPane.showOptionDialog(null,msg,title,JOptionPane.YES_OPTION,JOptionPane.INFORMATION_MESSAGE,null,choice,0);
        return opt;
    }
}