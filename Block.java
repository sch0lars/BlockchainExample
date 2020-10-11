/*
 * 
 * 	A program to elucidate the conceptualization of the 
 * 	fundamental mechanisms of blockchains. This program generates
 * 	50 blocks based on a genesis block and writes the 
 * 	index, timestamp, data, and hash to a text file. 
 * 
 * 	Author: Tyler Hooks
 * 
 */

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Block 
{
	public static int blockIndex = 0;
	public int index;
	public String data;
	public String timestamp;
	public String hash;
	public String previousHash;
	
	public Block(String data, String previousHash)
	{
		this.index = Block.blockIndex;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.timestamp = sdf.format(new Date());
		Block.blockIndex++;
		this.data = data;
		this.previousHash = previousHash;
		this.hash = getHash();
		
	}
	
	public static Block genesisBlock()
	{
		return new Block("Genesis Block\t\t", "0");
	}
	
	private String getHash()
	{
		String message = this.index + this.timestamp + this.data + this.previousHash;
		try { 
			  
            // Declare MessageDigest.
            MessageDigest md = MessageDigest.getInstance("SHA-256"); 
  
            // Convert message to byte array.
            byte[] messageDigest = md.digest(message.getBytes()); 
  
            // Convert byte array into signum representation.
            BigInteger no = new BigInteger(1, messageDigest); 
            
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
  
            while (hashtext.length() < 32) 
            { 
                hashtext = "0" + hashtext; 
            } 
  
            return hashtext; 
        } 
  
        catch (Exception e) 
		{ 
            System.out.println(e); 
            return null; 
        } 
	}
		
	public String getInfo()
	{
		String info = String.format("Block %d:\t%s\t%s\t\t%s%n", this.index, this.timestamp, this.data, this.hash);
		return info;
	}
	
	public static void main(String[] args)
	{
		String filepath = "blockchain.txt";
		try
		{
			File file = new File(filepath);
			FileWriter writer = new FileWriter(file);
			Block oldBlock = Block.genesisBlock();
			writer.write(oldBlock.getInfo());
			for (int i = 0; i < 50; i++)
			{
				Block newBlock = new Block(String.format("Block %d has been created.", Block.blockIndex), oldBlock.hash);
				writer.write(newBlock.getInfo());
				oldBlock = newBlock;
			}
			writer.close();
			System.out.println("Process Complete.");
		}
		
		catch (Exception e)
		{
			System.out.println(e);
		}
		
	}
}
