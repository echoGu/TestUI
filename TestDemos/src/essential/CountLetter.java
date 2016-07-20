package essential;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 * @author irblir Write an example that counts the number of times a particular
 *         character, such as e, appears in a file. The character can be
 *         specified at the command line. You can use xanadu.txt as the input
 *         file.
 *
 */
public class CountLetter
{
	private char lookFor;
	private Path file;

	CountLetter(char lookFor, Path file)
	{
		this.lookFor = lookFor;
		this.file = file;
	}

	public int count() throws IOException
	{
		int count = 0;
		try (InputStream in = Files.newInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in)))
		{
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				for (int i = 0; i < line.length(); i++)
				{
					if (lookFor == line.charAt(i))
					{
						count++;
					}
				}
			}
		} catch (IOException x)
		{
			System.err.println(x);
			System.out.println(x);
		}
		return count;
	}

	static void usage()
	{
		System.out.println("usage: java CountLetter <letter>");
		System.exit(-1);
	}

	public static void main(String[] args) throws IOException
	{

		if (args.length != 1 || args[0].length() != 1)
		{
			System.out.println(args.length);
			usage();
		}
			

		char lookFor = args[0].charAt(0);
		Path file = Paths.get("xanadu.txt");
		int count = new CountLetter(lookFor, file).count();
		System.out.format("File '%s' has %d instances of letter '%c'.%n", file, count, lookFor);
	}
}