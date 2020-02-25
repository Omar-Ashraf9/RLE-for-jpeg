import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Vector;


public class Main 
{
	public static String input;
	public static Node node1;
	public static Node node2;
	public static Node root;
	public static String asBinary;
	public static int count = 0;
	public static Vector<Node> TagsFromInput = new Vector<Node>();
	public static Vector<Node> TagsFromCategory = new Vector<Node>();
	public static HashMap<String, Integer> map = new HashMap<String, Integer>();
	public static HashMap<String, String> HuffmanCodes = new HashMap<String, String>();
	public static PriorityQueue<Node> q;
	

	
	public static void CreateTagsFromInput(String[] values)
	{
		for(int i = 0; i < values.length; i++)
		{
			if(!values[i].equals("0") && i == 0)
			{
				node1 = new Node();
				node1.symbol = values[i];
				node1.numOfZeroesBefore = 0;
				TagsFromInput.add(node1);
			}
			
			else if(values[i].equals("0"))
			{
				count++;
			}
			else
			{
				node1 = new Node();
				node1.symbol = values[i];
				node1.numOfZeroesBefore = count;
				count = 0;
				TagsFromInput.add(node1);
			}
		}
		node1 = new Node();
		node1.symbol = "EOB";
		node1.freq = 1;
		node1.numOfZeroesBefore = 0;
		TagsFromInput.add(node1);
	}
	public static void CreateTagsFromCategory()
	{
		for(int i = 0; i < TagsFromInput.size(); i++)
		{
			if(TagsFromInput.get(i).symbol.charAt(0) == '-')
			{
				asBinary  = (Integer.toBinaryString(Integer.parseInt(TagsFromInput.get(i).symbol.substring(1, TagsFromInput.get(i).symbol.length()))));
				node2 = new Node();
				node2.symbol = Integer.toString(asBinary.length());
				node2.numOfZeroesBefore = TagsFromInput.get(i).numOfZeroesBefore;
				TagsFromCategory.add(node2);
			}
			else if(!TagsFromInput.get(i).symbol.equals("EOB"))
			{
				asBinary = (Integer.toBinaryString(Integer.parseInt(TagsFromInput.get(i).symbol)));
				node2 = new Node();
				node2.symbol = Integer.toString(asBinary.length());
				node2.numOfZeroesBefore = TagsFromInput.get(i).numOfZeroesBefore;
				TagsFromCategory.add(node2);
			}
		}
		node2 = new Node();
		node2.symbol = "EOB";
		node2.freq = 1;
		node2.numOfZeroesBefore = 0;
		TagsFromCategory.add(node2);
	}
	public static String flip(String s)
	{
		String out = "";
		for(int i = 0; i < s.length(); i++)
		{
			if(s.charAt(i) == '1')
			{
				out += '0';
			}else
			{
				out += '1';			}
		}
		return out;
	}
	public static void Calculatefreq()
	{
		for(int i = 0; i < TagsFromCategory.size(); i++)
		{
			String toCheck = TagsFromCategory.get(i).numOfZeroesBefore + "/" + TagsFromCategory.get(i).symbol;
			if(!map.containsKey(toCheck))
			{
				map.put(toCheck, 1);
			}
			else
			{
				int value = map.get(toCheck);
				map.put(toCheck, ++value);
			}
		}
	}
	public static void ConstructLeafs()
	{
		q = new PriorityQueue<Node>(map.size(),new Compare());
		for (HashMap.Entry<String, Integer> entry : map.entrySet()) 
		{
			String k = entry.getKey();
			int v = entry.getValue();
			
			Node n = new Node();
			n.symbol = k;
			n.freq = v;
			n.left = null;
			n.right = null;
			q.add(n);
		}
		
		
	}
	
	public static void ConstructTree()
	{
		while(q.size() > 1)
		{
			Node Child1 = q.peek();
			q.poll();
			
			Node Child2 = q.peek();

			q.poll();
			
			Node parent = new Node();
			parent.symbol = "-";
			parent.freq = Child1.freq + Child2.freq;
			parent.left = Child1;
			parent.right = Child2;
			
			root = parent;
			q.add(parent);
		}
	}
	public static void getcodes(Node root, String result)
	{
		if((root.left == null) && (root.right == null) && (!root.symbol.equals("-")))
		{
			HuffmanCodes.put(root.symbol, result);
			return;
		}
		
		getcodes(root.left, result + "1");
		getcodes(root.right, result + "0");

	}
	public static String result()
	{
		String output = "";
		for(int i = 0; i < TagsFromInput.size(); i++)
		{
			if(TagsFromInput.get(i).symbol.charAt(0) == '-')
			{
				asBinary  = (Integer.toBinaryString(Integer.parseInt(TagsFromInput.get(i).symbol.substring(1, TagsFromInput.get(i).symbol.length()))));
				asBinary = flip(asBinary);
				String find = Integer.toString(TagsFromInput.get(i).numOfZeroesBefore) + "/" + Integer.toString(asBinary.length());
				output += HuffmanCodes.get(find) + asBinary;
			}
			else if(!TagsFromInput.get(i).symbol.equals("EOB"))
			{
				asBinary  = (Integer.toBinaryString(Integer.parseInt(TagsFromInput.get(i).symbol)));
				String find = Integer.toString(TagsFromInput.get(i).numOfZeroesBefore) + "/" + Integer.toString(asBinary.length());
				output += HuffmanCodes.get(find) + asBinary;
			}
			else
			{
				output += HuffmanCodes.get("0/EOB");
			}
		}
		return output;
	}
	public static String compression(String[] values) throws IOException
	{
		
		String output;
		
		CreateTagsFromInput(values);
		CreateTagsFromCategory();
		Calculatefreq();
		ConstructLeafs();
		ConstructTree();
		getcodes(root, "");
		/// 2/2
		output = result();
		FileOutputStream fileOutputStream = new FileOutputStream("G:\\College\\java\\Huffman Encoding For jpeg\\CompressionOutput.txt");
	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

	    objectOutputStream.writeObject(output); 
	    objectOutputStream.writeObject(HuffmanCodes); 
	    objectOutputStream.close();
	    HuffmanCodes.clear();
	    return output;
	}

	public static String decompression() throws IOException, ClassNotFoundException
	{
		InputStream file = new FileInputStream("G:\\College\\java\\Huffman Encoding For jpeg\\CompressionOutput.txt");
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);
        String outputFromCompression = (String) input.readObject();
        HashMap<String, String> HF = (HashMap<String, String>) input.readObject(); 
       
        String result = "";
        String Check = "";
		String k = "";
		String v = "";
        for(int i = 0; i < outputFromCompression.length(); i++)
        {
        	Check += outputFromCompression.charAt(i);
        	if(HF.containsValue(Check))
        	{
        		
        		for (HashMap.Entry<String, String> entry : HF.entrySet()) 
        		{
        			k = entry.getKey();
        			v = entry.getValue();
        			if(v.equals(Check) && !k.equals("0/EOB"))
					{
        				String[] fromCategory = k.split("/");
        				/// fill the output with leading zeroes
        				for(int f = 0; f < Integer.parseInt(fromCategory[0]); f++)
        				{
        					result += ",";
        					result += "0";
        				}
        				int dist = Integer.parseInt(fromCategory[1]);
        				String taken = "";
        				for(int j = 1 + i; j < dist + i + 1; j++)
        				{
        					taken += outputFromCompression.charAt(j);
        				}
        				i = i + dist;
        				//String taken = outputFromCompression.substring(i+1, i+dist);

						if(taken.charAt(0) == '0')
						{
							taken = flip(taken);
							int decimal = Integer.parseInt(taken,2);  
							taken = String.valueOf(decimal);
							result += ",";
							result += "-";
							result += taken;
							//i = i + taken.length() + 1;
							Check = "";
							break;
						}
						else
						{
							int decimal = Integer.parseInt(taken,2);
							taken = String.valueOf(decimal);
							result += ",";
							result += taken;
							//i = i + taken.length() + 1;
							Check = "";
							break;
						}
        			
					}

        		}
        	}
        	
        }
        return (result.substring(1, result.length()));
        
	}
	
}
