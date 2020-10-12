import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TCPServer
{
	public static void main(String[] inargs)
	{
		int	port = 8570;

		try
		{
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("wait the client message");

			try
			{
				while(true)
				{
					Socket wait = serverSocket.accept();
					ServerThread t = new ServerThread(wait);
					t.start();
				}
			}
			catch(IOException e)
			{
				System.err.println(e);
			}

		}catch(Exception e){
			System.err.println(e);
		}
	}
}



class ServerThread extends Thread{
	private Socket socket;

	public ServerThread(Socket socket){
		this.socket = socket;
	}

	public void run(){

		byte []	inbuf = new byte[100];
		byte [] outbuf = null;
		InputStream in = null;
		OutputStream out = null;

		try {
			while(true) {
				in = socket.getInputStream();
				in.read(inbuf);
				int inbufInt = ByteBuffer.wrap(inbuf).getInt();
				System.out.println("Receive number: " + inbufInt);

				if (inbufInt == 0) {
					out = socket.getOutputStream();
					outbuf = ByteBuffer.allocate(100).putInt(0).array();
					out.write(outbuf);
					break;
				} else {
					out = socket.getOutputStream();
					inbufInt -= 1;
					System.out.println("Send numbers: " + inbufInt);
					outbuf = ByteBuffer.allocate(100).putInt(inbufInt).array();
					out.write(outbuf);
				}
			}

		}catch(Exception e){

			System.err.println(e);

		}finally{
			try {
				if(in != null) {
					in.close();
				}
				if(out != null) {
					out.close();
				}
				if(socket != null) {

				}
			}catch(Exception e){

				System.err.println(e);

			}
		}
	}

}