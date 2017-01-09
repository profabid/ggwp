package c.checker;
import java.awt.Desktop;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
    
public class CChecker 
{
    public static void main(String[] args) 
    {
        ArrayList<String> al = new ArrayList<String>();
        try 
        {
            StringBuilder htmlStringBuilder=new StringBuilder();
            htmlStringBuilder.append("<html><head><title>C-Checker</title></head>");
            htmlStringBuilder.append("<body>");
            BufferedReader in = new BufferedReader(new FileReader("Hello.c"));
            String str;
            while ((str = in.readLine()) != null) {
                al.add(str);
            }
            in.close();
        
            String s,header="sagar";
            int o=-1,p=-1;
            Iterator itr = al.iterator();
            while(itr.hasNext())
            {
                s=(String)itr.next();
              
                boolean flag=false,flagh1=false,flagh2=false,flager=false;
                for(int i=0;i<s.length();i++)
                {
                    //Commnet
                    if(s.charAt(i)=='/' && s.charAt(i+1)=='/')
                    {
                    
                        flager=true;
                        flag=true;       
                      
                    }
                    
                    //for stdio.h
                    if((s.charAt(i)=='<'))
                    {
                        flagh1=true;
                        o=i;
                    }
                    if((s.charAt(i)=='>'))
                    {
                        flagh2=true;
                        p=i;
                    }
                    
                    //for goto
                    if((i+4)<s.length())
                    {
                        String as = s.substring(i,i+4);
                        //System.out.println(ass);
                        if(as.equals("goto"))
                        {
                            flager = flag = true; 
                           
                        }
                    }
                    
                    //for continue
                    if((i+8)<s.length())
                    {
                        String as = s.substring(i,i+8);
                        //System.out.println(as);
                        if(as.equals("continue"))
                        {
                            flager = flag = true;
                            
                        }
                    }
                    
                    //for BitWise AND (&)
                    if((s.charAt(i)=='&') && ((s.charAt(i+1)!='&') && (s.charAt(i-1)!='&') ) && (i+1)<s.length() && (i-1)>=0)
                    {
                        flager = flag = true;
                    }
                    
                    //for BitWise OR (|)
                    if((s.charAt(i)=='|') && ((s.charAt(i+1)!='|') && (s.charAt(i-1)!='|') ) && (i+1)<s.length() && (i-1)>=0)
                    {
                        flager = flag = true;
                    }
                    
                    //for BitWise XOR (^)
                    if((s.charAt(i)=='^') && ((s.charAt(i+1)!='^') && (s.charAt(i-1)!='^') ) && (i+1)<s.length() && (i-1)>=0)
                    {
                        flager = flag = true;
                    }
                    
                    //for BitWise NOT (~)
                    if((s.charAt(i)=='~') && ((s.charAt(i+1)!='~') && (s.charAt(i-1)!='~') ) && (i+1)<s.length() && (i-1)>=0)
                    {
                        flager = flag = true;
                    }
                    
                    //for BitWise RS (>>)
                    /*if((s.charAt(i)=='>') && ((s.charAt(i+1)=='>') || (s.charAt(i-1)=='>') ) && (i+1)<s.length() && (i-1)>=0)
                    {
                        flager = flag = true;
                    }
                    
                    //for BitWise LS (<<)
                    if((s.charAt(i)=='<') && ((s.charAt(i+1)=='<') || (s.charAt(i-1)=='<') ) && (i+1)<s.length() && (i-1)>=0)
                    {
                        flager = flag = true;
                    }*/
                    
                    //for html writing 
                    //append errorfull code
                    if(flager==true)
                    {
                        System.out.println(s.trim());
                        htmlStringBuilder.append("<span style= \"background-color: #FFFF00\">");
                        htmlStringBuilder.append("<font color= \"red\">");
                        htmlStringBuilder.append("<b>");
                        htmlStringBuilder.append(s);
                        htmlStringBuilder.append("</b>");
                        htmlStringBuilder.append("</font>");
                        htmlStringBuilder.append("</span>");
                        flager=false;
                        break;
                    }
                }//endOfFor
                
                //stdio.h substring
                if(o!=-1 && p!=-1)
                {
                    header=s.substring(o+1,p);                   
                    o=-1;
                    p=-1;
                }
                
                //append errorless code
                if(!flag)
                    htmlStringBuilder.append(s);
                
                //append <
                if(flagh1==true)
                {
                    htmlStringBuilder.append("&lt");                    
                }
                //append stdio.h
                if(header!="sagar")
                {
                    htmlStringBuilder.append(header);
                    header="sagar";
                }
                //appned >
                if(flagh2==true)
                    htmlStringBuilder.append("&gt");
                flag=false;
                flagh1=false;
                flagh2=false;
                htmlStringBuilder.append("<br/>");
            }//endOfWhile
            
            //close body and html file
            htmlStringBuilder.append("</body></html>");
            
            //copy to html file from stringbuilder
            WriteToFile(htmlStringBuilder.toString(),"testfile.html");            
            
            //open browser
            File html = new File("testfile.html");
            Desktop.getDesktop().browse(html.toURI());
        }
        catch (IOException e) 
        {
            System.out.println(e);
        }           
    }
    public static void WriteToFile(String fileContent, String fileName) throws IOException 
    {
        String projectPath = System.getProperty("user.dir");
        String tempFile = projectPath + File.separator+fileName;
        File file = new File(tempFile);
        // if file does exists, then delete and create a new file
        if (file.exists()) 
        {
            try 
            {
                File newFileName = new File(projectPath + File.separator+ "backup_"+fileName);
                file.renameTo(newFileName);
                file.createNewFile();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
        //write to file with OutputStreamWriter
        OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
        Writer writer=new OutputStreamWriter(outputStream);
        writer.write(fileContent);
        writer.close();
    }
}