import java.io.IOException;

/**
 * Created by teej107 on 4/15/17.
 */
public class Test
{
	public static void main(String[] args) throws IOException
	{
		String hex = toHexString("AC/\\?:<>|\"DC");
		System.out.println(hex);
		System.out.println(fromHexString(hex));
	}

	public static String toHexString(String str)
	{
		StringBuilder sb = new StringBuilder();
		for (char c : str.toCharArray())
		{
			sb.append(Integer.toHexString(c));
		}
		return sb.toString();
	}

	public static String fromHexString(String hex)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < hex.length() - 1; i += 2)
		{
			sb.append((char) Integer.parseInt(hex.substring(i, (i + 2)), 16));
		}
		return sb.toString();
	}
}
