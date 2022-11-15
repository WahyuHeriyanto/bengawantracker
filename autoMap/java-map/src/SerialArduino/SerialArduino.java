package SerialArduino;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.jxmapviewer.viewer.GeoPosition;
import test.Main;
import java.io.IOException;

public class SerialArduino {
    // fungsi read data
    SerialPort activePort;
    SerialPort[] ports = SerialPort.getCommPorts();
    public double myLat, counter=0.5;
    
    public SerialArduino() {
        int i = 0;
		for(SerialPort port : ports) {
                        activePort = port;
			}
        try{
            
             GeoPosition pos = new GeoPosition(-8.56,110.8590408);
             Main app = new Main();
             app.updateCoordinate(pos);
             app.setVisible(true);
             if (activePort.openPort())
		
		activePort.addDataListener(new SerialPortDataListener() {
			
			@Override
			public void serialEvent(SerialPortEvent event) {
				int size = event.getSerialPort().bytesAvailable();
				byte[] buffer = new byte[size];
				event.getSerialPort().readBytes(buffer, size);
				for(byte b:buffer)
					myLat = ((double)b);
                                        myLat = (myLat+counter);
                                        GeoPosition pos = new GeoPosition(myLat,110.8590408);
                                        System.out.println(myLat);
                                        try{
                                            app.updateCoordinate(pos);
                                            counter += 0.5;
                                        }catch (IOException e){
                                            System.out.println(e);
                                        }
                                        
                                
				}

			@Override
			public int getListeningEvents() { 
				return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;  
				}
			});
        }catch(IOException e){
            System.out.println(e);
        }
   
}
    
}
