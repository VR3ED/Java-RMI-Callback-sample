import java.rmi.Remote;
import java.rmi.RemoteException;

//11: si crea questa interfaccia
//questa va' messa dentro sia il client he il server, nel client viene implementata
interface RemoteListener extends Remote{
	//12: si crea metodo dal quale poi passeremo oggetti di tipo client 
	void RemoteEvent (Object param) throws RemoteException;
}
