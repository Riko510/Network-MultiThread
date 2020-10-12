import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

class TCPClient
{
	public static void main(String[] inargs)
	{
		Socket client = null;
		InputStream in = null;
		OutputStream out = null;
		int	port = 8570;
		byte []	inbuf = new byte[100];
		byte [] outbuf = new byte[100];
		int dataIn = 0;
		int inbufInt = 0;
		Scanner scanner = null;

		if(inargs.length != 0)
		{
			try
			{
				client = new Socket(inargs[0], port);
				scanner = new Scanner(System.in);
				System.out.println("Please input a number which is > 0：");
				dataIn = scanner.nextInt();


				if(dataIn != 0) {

					out = client.getOutputStream();
					outbuf = ByteBuffer.allocate(100).putInt(dataIn).array();
					out.write(outbuf);

					while(true) {
						in = client.getInputStream();
						in.read(inbuf);
						inbufInt = ByteBuffer.wrap(inbuf).getInt();
						System.out.println("Receive numbers: " + inbufInt);

						if(inbufInt==0){
							out = client.getOutputStream();
							outbuf = ByteBuffer.allocate(100).putInt(0).array();
							out.write(outbuf);
							break;
						}else {
							out = client.getOutputStream();
							inbufInt -= 1;
							System.out.println("Send numbers: " + inbufInt);
							outbuf = ByteBuffer.allocate(100).putInt(inbufInt).array();
							out.write(outbuf);
						}
					}

					client.close();
				}else{
					client.close();
				}
			}
			catch(UnknownHostException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}