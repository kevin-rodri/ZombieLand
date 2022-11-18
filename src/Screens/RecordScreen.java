package Screens;
// Creating a text File using FileWriter
import Engine.Config;

import java.io.*;

class CreateFile
{
    public static void record() throws IOException
    {
        // Accept a string
        int xt = PlayLevelScreen.fullGameTime;
        int xm = PlayLevelScreen.fullGameMin;
        String str = String.valueOf(xt);


        // attach a file to FileWriter
        FileWriter fw=new FileWriter(Config.RESOURCES_PATH + "record.txt");

        // read character wise from string and write
        // into FileWriter
        for (int i = 0; i < str.length(); i++)
            fw.write(str.charAt(i));

        //System.out.println("Writing successful");
        //close the file
        fw.close();
    }
    public static void recordMin() throws IOException
    {
        // Accept a string
        int xm = PlayLevelScreen.fullGameMin;
        String strm = String.valueOf(xm);


        // attach a file to FileWriter
        FileWriter fwm=new FileWriter(Config.RESOURCES_PATH + "recordMin.txt");

        // read character wise from string and write
        // into FileWriter
        for (int i = 0; i < strm.length(); i++)
            fwm.write(strm.charAt(i));

        //System.out.println("Writing successful");
        //close the file
        fwm.close();
    }
    public static int ch;
    public static String recordValue = "";
    public static String recordMinStr ="";
    public static void checkTime() throws IOException
        {
            File file = new File(Config.RESOURCES_PATH + "record.txt");

            try (FileReader fr = new FileReader(file))
            {
                char[] chars = new char[(int) file.length()];
                fr.read(chars);

                String fileContent = new String(chars);
                System.out.println(fileContent);
                recordMinStr = fileContent;
            }

            catch (IOException e) {
                e.printStackTrace();
            }

        }

    public static void checkMin() throws IOException
    {
        File file = new File(Config.RESOURCES_PATH + "recordMin.txt");

        try (FileReader frm = new FileReader(file))
        {
            char[] charsm = new char[(int) file.length()];
            frm.read(charsm);

            String fileContent = new String(charsm);
            System.out.println(fileContent);
            recordValue = fileContent;
        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }

}