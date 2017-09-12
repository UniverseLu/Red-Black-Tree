/**
 * Created by Universe_Lu on 3/21/16.
 */
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.Scanner;


public class bbst {
    public static void main(String[] args) {
    	 RBtree tree =new RBtree();
    	if (0>=args.length){
    		System.out.println("Please enter an test file name.");
    	} else{
    		try{ 
                String fileName = args[0];
		        File test = new File(fileName);
			    FileReader     testfile = new FileReader(test);
                BufferedReader fileIn = new BufferedReader(testfile);
                String Content = fileIn.readLine();
                
                int size = Integer.parseInt(Content);                      // get the quantity of the node

                int IDArray[] =  new int[size];
                int CountArray[]= new int[size];
                
                for ( int i = 0; i < size; i++){
                    Content = fileIn.readLine();
                    String nodeContent[] = Content.split(" ");
                    
                    int ID = Integer.parseInt(nodeContent[0]);              //get the ID array
                    int Count = Integer.parseInt(nodeContent[1]);           //get the count array
                    
                    IDArray[i] = ID;
                    CountArray[i] = Count;
                }
                tree.ArrayToRBTree(IDArray,CountArray,size);                // initialize the red-black tree with the sorted array
                             
               //read the commands input
                Scanner sc = new Scanner(System.in);       
                String ContentCom = sc.nextLine();                                // read the command and numbers first line
                while(!"quit".equals(ContentCom)){                                // when it comes to "quit", end reading the command file
                    String commands[] = ContentCom.split(" ");
                    String command = commands[0];                                 // get the command , and then take the accurate methods
                    switch (command) {
                        case "increase":
                            tree.increase(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
                            break;
                        case "reduce":
                            tree.reduce(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
                            break;
                        case "count":
                            tree.count(Integer.parseInt(commands[1]));
                            break;
                        case "inrange":
                            tree.inrange(Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
                            break;
                        case "next":
                            tree.next(Integer.parseInt(commands[1]));
                            break;
                        case "previous":
                            tree.previous(Integer.parseInt(commands[1]));
                            break;
                        default:
                            System.out.println("Invalid command!");
                            break;
                   }
                    ContentCom = sc.nextLine();                                // red the next line
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
