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
        String str = String.valueOf(xt);

        // attach a file to FileWriter
        FileWriter fw=new FileWriter(Config.RESOURCES_PATH + "record.txt");

        // read character wise from string and write
        // into FileWriter
        for (int i = 0; i < str.length(); i++)
            fw.write(str.charAt(i));

        System.out.println("Writing successful");
        //close the file
        fw.close();
    }
    public static int ch;
    public static String recordValue = "";
    public static boolean recordMin = false;
    public static void checkTime() throws IOException
        {
            File file = new File(Config.RESOURCES_PATH + "record.txt");

            try (FileReader fr = new FileReader(file))
            {
                char[] chars = new char[(int) file.length()];
                fr.read(chars);

                String fileContent = new String(chars);
                System.out.println(fileContent);
                recordValue = fileContent;
            }

            catch (IOException e) {
                e.printStackTrace();
            }
            if(Integer.parseInt(recordValue)>60){
                recordMin =true;
            }
        }

}